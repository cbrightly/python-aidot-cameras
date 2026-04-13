package androidx.multidex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipFile;

public final class MultiDex {
    private static final String CODE_CACHE_NAME = "code_cache";
    private static final String CODE_CACHE_SECONDARY_FOLDER_NAME = "secondary-dexes";
    private static final boolean IS_VM_MULTIDEX_CAPABLE = isVMMultidexCapable(System.getProperty("java.vm.version"));
    private static final int MAX_SUPPORTED_SDK_VERSION = 20;
    private static final int MIN_SDK_VERSION = 4;
    private static final String NO_KEY_PREFIX = "";
    private static final String OLD_SECONDARY_FOLDER_NAME = "secondary-dexes";
    static final String TAG = "MultiDex";
    private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
    private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;
    private static final Set<File> installedApk = new HashSet();

    private MultiDex() {
    }

    public static void install(Context context) {
        Log.i(TAG, "Installing application");
        if (IS_VM_MULTIDEX_CAPABLE) {
            Log.i(TAG, "VM has multidex support, MultiDex support library is disabled.");
            return;
        }
        int i = Build.VERSION.SDK_INT;
        if (i >= 4) {
            try {
                ApplicationInfo applicationInfo = getApplicationInfo(context);
                if (applicationInfo == null) {
                    Log.i(TAG, "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
                    return;
                }
                doInstallation(context, new File(applicationInfo.sourceDir), new File(applicationInfo.dataDir), "secondary-dexes", "", true);
                Log.i(TAG, "install done");
            } catch (Exception e) {
                Log.e(TAG, "MultiDex installation failure", e);
                throw new RuntimeException("MultiDex installation failed (" + e.getMessage() + ").");
            }
        } else {
            throw new RuntimeException("MultiDex installation failed. SDK " + i + " is unsupported. Min SDK version is " + 4 + ".");
        }
    }

    public static void installInstrumentation(Context instrumentationContext, Context targetContext) {
        Log.i(TAG, "Installing instrumentation");
        if (IS_VM_MULTIDEX_CAPABLE) {
            Log.i(TAG, "VM has multidex support, MultiDex support library is disabled.");
            return;
        }
        int i = Build.VERSION.SDK_INT;
        if (i >= 4) {
            try {
                ApplicationInfo instrumentationInfo = getApplicationInfo(instrumentationContext);
                if (instrumentationInfo == null) {
                    Log.i(TAG, "No ApplicationInfo available for instrumentation, i.e. running on a test Context: MultiDex support library is disabled.");
                    return;
                }
                ApplicationInfo applicationInfo = getApplicationInfo(targetContext);
                if (applicationInfo == null) {
                    Log.i(TAG, "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
                    return;
                }
                String instrumentationPrefix = instrumentationContext.getPackageName() + ".";
                File dataDir = new File(applicationInfo.dataDir);
                doInstallation(targetContext, new File(instrumentationInfo.sourceDir), dataDir, instrumentationPrefix + "secondary-dexes", instrumentationPrefix, false);
                doInstallation(targetContext, new File(applicationInfo.sourceDir), dataDir, "secondary-dexes", "", false);
                Log.i(TAG, "Installation done");
            } catch (Exception e) {
                Log.e(TAG, "MultiDex installation failure", e);
                throw new RuntimeException("MultiDex installation failed (" + e.getMessage() + ").");
            }
        } else {
            throw new RuntimeException("MultiDex installation failed. SDK " + i + " is unsupported. Min SDK version is " + 4 + ".");
        }
    }

    private static void doInstallation(Context mainContext, File sourceApk, File dataDir, String secondaryFolderName, String prefsKeyPrefix, boolean reinstallOnPatchRecoverableException) {
        Set<File> set = installedApk;
        synchronized (set) {
            if (!set.contains(sourceApk)) {
                set.add(sourceApk);
                int i = Build.VERSION.SDK_INT;
                if (i > 20) {
                    Log.w(TAG, "MultiDex is not guaranteed to work in SDK version " + i + ": SDK version higher than " + 20 + " should be backed by " + "runtime with built-in multidex capabilty but it's not the " + "case here: java.vm.version=\"" + System.getProperty("java.vm.version") + "\"");
                }
                ClassLoader loader = getDexClassloader(mainContext);
                if (loader != null) {
                    try {
                        clearOldDexDir(mainContext);
                    } catch (Throwable t) {
                        Log.w(TAG, "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.", t);
                    }
                    File dexDir = getDexDir(mainContext, dataDir, secondaryFolderName);
                    MultiDexExtractor extractor = new MultiDexExtractor(sourceApk, dexDir);
                    IOException closeException = null;
                    try {
                        installSecondaryDexes(loader, dexDir, extractor.load(mainContext, prefsKeyPrefix, false));
                    } catch (IOException e) {
                        if (reinstallOnPatchRecoverableException) {
                            Log.w(TAG, "Failed to install extracted secondary dex files, retrying with forced extraction", e);
                            installSecondaryDexes(loader, dexDir, extractor.load(mainContext, prefsKeyPrefix, true));
                        } else {
                            throw e;
                        }
                    } catch (Throwable th) {
                        try {
                            extractor.close();
                        } catch (IOException e2) {
                            IOException closeException2 = e2;
                        }
                        throw th;
                    }
                    try {
                        extractor.close();
                    } catch (IOException e3) {
                        closeException = e3;
                    }
                    if (closeException != null) {
                        throw closeException;
                    }
                }
            }
        }
    }

    private static ClassLoader getDexClassloader(Context context) {
        try {
            ClassLoader loader = context.getClassLoader();
            if (Build.VERSION.SDK_INT >= 14) {
                if (loader instanceof BaseDexClassLoader) {
                    return loader;
                }
            } else if ((loader instanceof DexClassLoader) || (loader instanceof PathClassLoader)) {
                return loader;
            }
            Log.e(TAG, "Context class loader is null or not dex-capable. Must be running in test mode. Skip patching.");
            return null;
        } catch (RuntimeException e) {
            Log.w(TAG, "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.", e);
            return null;
        }
    }

    private static ApplicationInfo getApplicationInfo(Context context) {
        try {
            return context.getApplicationInfo();
        } catch (RuntimeException e) {
            Log.w(TAG, "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
            return null;
        }
    }

    static boolean isVMMultidexCapable(String versionString) {
        boolean isMultidexCapable = false;
        if (versionString != null) {
            StringTokenizer tokenizer = new StringTokenizer(versionString, ".");
            String minorToken = null;
            String majorToken = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
            if (tokenizer.hasMoreTokens()) {
                minorToken = tokenizer.nextToken();
            }
            if (!(majorToken == null || minorToken == null)) {
                try {
                    int major = Integer.parseInt(majorToken);
                    int minor = Integer.parseInt(minorToken);
                    boolean z = true;
                    if (major <= 2 && (major != 2 || minor < 1)) {
                        z = false;
                    }
                    isMultidexCapable = z;
                } catch (NumberFormatException e) {
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("VM with version ");
        sb.append(versionString);
        sb.append(isMultidexCapable ? " has multidex support" : " does not have multidex support");
        Log.i(TAG, sb.toString());
        return isMultidexCapable;
    }

    private static void installSecondaryDexes(ClassLoader loader, File dexDir, List<? extends File> files) {
        if (!files.isEmpty()) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 19) {
                V19.install(loader, files, dexDir);
            } else if (i >= 14) {
                V14.install(loader, files);
            } else {
                V4.install(loader, files);
            }
        }
    }

    /* access modifiers changed from: private */
    public static Field findField(Object instance, String name) {
        Class cls = instance.getClass();
        while (cls != null) {
            try {
                Field field = cls.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    /* access modifiers changed from: private */
    public static Method findMethod(Object instance, String name, Class<?>... parameterTypes) {
        Class cls = instance.getClass();
        while (cls != null) {
            try {
                Method method = cls.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }

    /* access modifiers changed from: private */
    public static void expandFieldArray(Object instance, String fieldName, Object[] extraElements) {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length);
        System.arraycopy(original, 0, combined, 0, original.length);
        System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);
        jlrField.set(instance, combined);
    }

    private static void clearOldDexDir(Context context) {
        File dexDir = new File(context.getFilesDir(), "secondary-dexes");
        if (dexDir.isDirectory()) {
            Log.i(TAG, "Clearing old secondary dex dir (" + dexDir.getPath() + ").");
            File[] files = dexDir.listFiles();
            if (files == null) {
                Log.w(TAG, "Failed to list secondary dex dir content (" + dexDir.getPath() + ").");
                return;
            }
            for (File oldFile : files) {
                Log.i(TAG, "Trying to delete old file " + oldFile.getPath() + " of size " + oldFile.length());
                if (!oldFile.delete()) {
                    Log.w(TAG, "Failed to delete old file " + oldFile.getPath());
                } else {
                    Log.i(TAG, "Deleted old file " + oldFile.getPath());
                }
            }
            if (!dexDir.delete()) {
                Log.w(TAG, "Failed to delete secondary dex dir " + dexDir.getPath());
                return;
            }
            Log.i(TAG, "Deleted old secondary dex dir " + dexDir.getPath());
        }
    }

    private static File getDexDir(Context context, File dataDir, String secondaryFolderName) {
        File cache = new File(dataDir, CODE_CACHE_NAME);
        try {
            mkdirChecked(cache);
        } catch (IOException e) {
            cache = new File(context.getFilesDir(), CODE_CACHE_NAME);
            mkdirChecked(cache);
        }
        File dexDir = new File(cache, secondaryFolderName);
        mkdirChecked(dexDir);
        return dexDir;
    }

    private static void mkdirChecked(File dir) {
        dir.mkdir();
        if (!dir.isDirectory()) {
            File parent = dir.getParentFile();
            if (parent == null) {
                Log.e(TAG, "Failed to create dir " + dir.getPath() + ". Parent file is null.");
            } else {
                Log.e(TAG, "Failed to create dir " + dir.getPath() + ". parent file is a dir " + parent.isDirectory() + ", a file " + parent.isFile() + ", exists " + parent.exists() + ", readable " + parent.canRead() + ", writable " + parent.canWrite());
            }
            throw new IOException("Failed to create directory " + dir.getPath());
        }
    }

    public static final class V19 {
        private V19() {
        }

        static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries, File optimizedDirectory) {
            IOException[] dexElementsSuppressedExceptions;
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            MultiDex.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions));
            if (suppressedExceptions.size() > 0) {
                Iterator<IOException> it = suppressedExceptions.iterator();
                while (it.hasNext()) {
                    Log.w(MultiDex.TAG, "Exception in makeDexElement", it.next());
                }
                Field suppressedExceptionsField = MultiDex.findField(dexPathList, "dexElementsSuppressedExceptions");
                IOException[] dexElementsSuppressedExceptions2 = (IOException[]) suppressedExceptionsField.get(dexPathList);
                if (dexElementsSuppressedExceptions2 == null) {
                    dexElementsSuppressedExceptions = (IOException[]) suppressedExceptions.toArray(new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined = new IOException[(suppressedExceptions.size() + dexElementsSuppressedExceptions2.length)];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(dexElementsSuppressedExceptions2, 0, combined, suppressedExceptions.size(), dexElementsSuppressedExceptions2.length);
                    dexElementsSuppressedExceptions = combined;
                }
                suppressedExceptionsField.set(dexPathList, dexElementsSuppressedExceptions);
                IOException exception = new IOException("I/O exception during makeDexElement");
                exception.initCause(suppressedExceptions.get(0));
                throw exception;
            }
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) {
            return (Object[]) MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
        }
    }

    public static final class V14 {
        private static final int EXTRACTED_SUFFIX_LENGTH = ".zip".length();
        private final ElementConstructor elementConstructor;

        public interface ElementConstructor {
            Object newInstance(File file, DexFile dexFile);
        }

        public static class ICSElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            ICSElementConstructor(Class<?> elementClass) {
                Constructor<?> constructor = elementClass.getConstructor(new Class[]{File.class, ZipFile.class, DexFile.class});
                this.elementConstructor = constructor;
                constructor.setAccessible(true);
            }

            public Object newInstance(File file, DexFile dex) {
                return this.elementConstructor.newInstance(new Object[]{file, new ZipFile(file), dex});
            }
        }

        public static class JBMR11ElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            JBMR11ElementConstructor(Class<?> elementClass) {
                Constructor<?> constructor = elementClass.getConstructor(new Class[]{File.class, File.class, DexFile.class});
                this.elementConstructor = constructor;
                constructor.setAccessible(true);
            }

            public Object newInstance(File file, DexFile dex) {
                return this.elementConstructor.newInstance(new Object[]{file, file, dex});
            }
        }

        public static class JBMR2ElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            JBMR2ElementConstructor(Class<?> elementClass) {
                Constructor<?> constructor = elementClass.getConstructor(new Class[]{File.class, Boolean.TYPE, File.class, DexFile.class});
                this.elementConstructor = constructor;
                constructor.setAccessible(true);
            }

            public Object newInstance(File file, DexFile dex) {
                return this.elementConstructor.newInstance(new Object[]{file, Boolean.FALSE, file, dex});
            }
        }

        static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries) {
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            Object[] elements = new V14().makeDexElements(additionalClassPathEntries);
            try {
                MultiDex.expandFieldArray(dexPathList, "dexElements", elements);
            } catch (NoSuchFieldException e) {
                Log.w(MultiDex.TAG, "Failed find field 'dexElements' attempting 'pathElements'", e);
                MultiDex.expandFieldArray(dexPathList, "pathElements", elements);
            }
        }

        private V14() {
            ElementConstructor constructor;
            Class<?> elementClass = Class.forName("dalvik.system.DexPathList$Element");
            try {
                constructor = new ICSElementConstructor(elementClass);
            } catch (NoSuchMethodException e) {
                try {
                    constructor = new JBMR11ElementConstructor(elementClass);
                } catch (NoSuchMethodException e2) {
                    constructor = new JBMR2ElementConstructor(elementClass);
                }
            }
            this.elementConstructor = constructor;
        }

        private Object[] makeDexElements(List<? extends File> files) {
            Object[] elements = new Object[files.size()];
            for (int i = 0; i < elements.length; i++) {
                File file = (File) files.get(i);
                elements[i] = this.elementConstructor.newInstance(file, DexFile.loadDex(file.getPath(), optimizedPathFor(file), 0));
            }
            return elements;
        }

        private static String optimizedPathFor(File path) {
            File optimizedDirectory = path.getParentFile();
            String fileName = path.getName();
            return new File(optimizedDirectory, fileName.substring(0, fileName.length() - EXTRACTED_SUFFIX_LENGTH) + ".dex").getPath();
        }
    }

    public static final class V4 {
        private V4() {
        }

        static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries) {
            int extraSize = additionalClassPathEntries.size();
            Field pathField = MultiDex.findField(loader, "path");
            StringBuilder path = new StringBuilder((String) pathField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            ListIterator<? extends File> iterator = additionalClassPathEntries.listIterator();
            while (iterator.hasNext()) {
                File additionalEntry = (File) iterator.next();
                String entryPath = additionalEntry.getAbsolutePath();
                path.append(':');
                path.append(entryPath);
                int index = iterator.previousIndex();
                extraPaths[index] = entryPath;
                extraFiles[index] = additionalEntry;
                extraZips[index] = new ZipFile(additionalEntry);
                extraDexs[index] = DexFile.loadDex(entryPath, entryPath + ".dex", 0);
            }
            pathField.set(loader, path.toString());
            MultiDex.expandFieldArray(loader, "mPaths", extraPaths);
            MultiDex.expandFieldArray(loader, "mFiles", extraFiles);
            MultiDex.expandFieldArray(loader, "mZips", extraZips);
            MultiDex.expandFieldArray(loader, "mDexs", extraDexs);
        }
    }
}
