package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.PosixFilePermission;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public final class NativeLibraryLoader {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DELETE_NATIVE_LIB_AFTER_LOADING = SystemPropertyUtil.getBoolean("io.netty.native.deleteLibAfterLoading", true);
    private static final String NATIVE_RESOURCE_HOME = "META-INF/native/";
    private static final File WORKDIR;
    private static final InternalLogger logger;

    static {
        InternalLogger instance = InternalLoggerFactory.getInstance((Class<?>) NativeLibraryLoader.class);
        logger = instance;
        String workdir = SystemPropertyUtil.get("io.netty.native.workdir");
        if (workdir != null) {
            File f = new File(workdir);
            f.mkdirs();
            try {
                f = f.getAbsoluteFile();
            } catch (Exception e) {
            }
            WORKDIR = f;
            InternalLogger internalLogger = logger;
            internalLogger.debug("-Dio.netty.native.workdir: " + f);
        } else {
            File tmpdir = PlatformDependent.tmpdir();
            WORKDIR = tmpdir;
            instance.debug("-Dio.netty.native.workdir: " + tmpdir + " (io.netty.tmpdir)");
        }
    }

    public static void loadFirstAvailable(ClassLoader loader, String... names) {
        List<Throwable> suppressed = new ArrayList<>();
        int length = names.length;
        int i = 0;
        while (i < length) {
            String name = names[i];
            try {
                load(name, loader);
                return;
            } catch (Throwable t) {
                suppressed.add(t);
                logger.debug("Unable to load the library '{}', trying next name...", name, t);
                i++;
            }
        }
        IllegalArgumentException iae = new IllegalArgumentException("Failed to load any of the given libraries: " + Arrays.toString(names));
        ThrowableUtil.addSuppressedAndClear(iae, suppressed);
        throw iae;
    }

    private static String calculatePackagePrefix() {
        String maybeShaded = NativeLibraryLoader.class.getName();
        String expected = "io!netty!util!internal!NativeLibraryLoader".replace('!', '.');
        if (maybeShaded.endsWith(expected)) {
            return maybeShaded.substring(0, maybeShaded.length() - expected.length());
        }
        throw new UnsatisfiedLinkError(String.format("Could not find prefix added to %s to get %s. When shading, only adding a package prefix is supported", new Object[]{expected, maybeShaded}));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x011f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0163, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        r6.add(r0);
        logger.debug("Error checking if {} is on a file store mounted with noexec", r13, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0175, code lost:
        closeQuietly((java.io.Closeable) null);
        closeQuietly((java.io.Closeable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0187, code lost:
        r13.deleteOnExit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x018a, code lost:
        throw r0;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:41:0x0122, B:47:0x0144] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void load(java.lang.String r18, java.lang.ClassLoader r19) {
        /*
            r1 = r19
            java.lang.String r2 = ".jnilib"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = calculatePackagePrefix()
            r4 = 46
            r5 = 95
            java.lang.String r3 = r3.replace(r4, r5)
            r0.append(r3)
            r3 = r18
            r0.append(r3)
            java.lang.String r5 = r0.toString()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r6 = r0
            r7 = 0
            loadLibrary(r1, r5, r7)     // Catch:{ all -> 0x002c }
            return
        L_0x002c:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r6.add(r0)
            io.netty.util.internal.logging.InternalLogger r8 = logger
            r9 = 3
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r9[r7] = r5
            java.io.File r10 = WORKDIR
            r11 = 1
            r9[r11] = r10
            r10 = 2
            r9[r10] = r0
            java.lang.String r10 = "{} cannot be loaded from java.libary.path, now trying export to -Dio.netty.native.workdir: {}"
            r8.debug((java.lang.String) r10, (java.lang.Object[]) r9)
            java.lang.String r8 = java.lang.System.mapLibraryName(r5)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r9 = "META-INF/native/"
            r0.append(r9)
            r0.append(r8)
            java.lang.String r9 = r0.toString()
            r10 = 0
            r12 = 0
            r13 = 0
            if (r1 != 0) goto L_0x0067
            java.net.URL r0 = java.lang.ClassLoader.getSystemResource(r9)
            r14 = r0
            goto L_0x006c
        L_0x0067:
            java.net.URL r0 = r1.getResource(r9)
            r14 = r0
        L_0x006c:
            if (r14 != 0) goto L_0x00c3
            boolean r0 = io.netty.util.internal.PlatformDependent.isOsx()     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            if (r0 == 0) goto L_0x00b9
            boolean r0 = r9.endsWith(r2)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            java.lang.String r15 = "META-INF/native/lib"
            if (r0 == 0) goto L_0x008d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r0.<init>()     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r0.append(r15)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r0.append(r5)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            java.lang.String r2 = ".dynlib"
            r0.append(r2)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            goto L_0x009b
        L_0x008d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r0.<init>()     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r0.append(r15)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r0.append(r5)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r0.append(r2)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
        L_0x009b:
            java.lang.String r0 = r0.toString()     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            if (r1 != 0) goto L_0x00a7
            java.net.URL r2 = java.lang.ClassLoader.getSystemResource(r0)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r14 = r2
            goto L_0x00ac
        L_0x00a7:
            java.net.URL r2 = r1.getResource(r0)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r14 = r2
        L_0x00ac:
            if (r14 == 0) goto L_0x00af
            goto L_0x00c3
        L_0x00af:
            java.io.FileNotFoundException r2 = new java.io.FileNotFoundException     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r2.<init>(r0)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            io.netty.util.internal.ThrowableUtil.addSuppressedAndClear(r2, r6)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            throw r2     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
        L_0x00b9:
            java.io.FileNotFoundException r0 = new java.io.FileNotFoundException     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r0.<init>(r9)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            io.netty.util.internal.ThrowableUtil.addSuppressedAndClear(r0, r6)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            throw r0     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
        L_0x00c3:
            int r0 = r8.lastIndexOf(r4)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            java.lang.String r2 = r8.substring(r7, r0)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            int r4 = r8.length()     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            java.lang.String r4 = r8.substring(r0, r4)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            java.io.File r15 = WORKDIR     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            java.io.File r15 = java.io.File.createTempFile(r2, r4, r15)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r13 = r15
            java.io.InputStream r15 = r14.openStream()     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r10 = r15
            java.io.FileOutputStream r15 = new java.io.FileOutputStream     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r15.<init>(r13)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r12 = r15
            r15 = 8192(0x2000, float:1.14794E-41)
            byte[] r15 = new byte[r15]     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
        L_0x00e9:
            int r16 = r10.read(r15)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r17 = r16
            if (r16 <= 0) goto L_0x00f8
            r11 = r17
            r12.write(r15, r7, r11)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r11 = 1
            goto L_0x00e9
        L_0x00f8:
            r11 = r17
            r12.flush()     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            closeQuietly(r12)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r12 = 0
            java.lang.String r7 = r13.getPath()     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            r17 = r0
            r0 = 1
            loadLibrary(r1, r7, r0)     // Catch:{ UnsatisfiedLinkError -> 0x0140, Exception -> 0x0121 }
            closeQuietly(r10)
            closeQuietly(r12)
            boolean r0 = DELETE_NATIVE_LIB_AFTER_LOADING
            if (r0 == 0) goto L_0x011b
            boolean r0 = r13.delete()
            if (r0 != 0) goto L_0x011e
        L_0x011b:
            r13.deleteOnExit()
        L_0x011e:
            return
        L_0x011f:
            r0 = move-exception
            goto L_0x0175
        L_0x0121:
            r0 = move-exception
            java.lang.UnsatisfiedLinkError r2 = new java.lang.UnsatisfiedLinkError     // Catch:{ all -> 0x011f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x011f }
            r4.<init>()     // Catch:{ all -> 0x011f }
            java.lang.String r7 = "could not load a native library: "
            r4.append(r7)     // Catch:{ all -> 0x011f }
            r4.append(r5)     // Catch:{ all -> 0x011f }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x011f }
            r2.<init>(r4)     // Catch:{ all -> 0x011f }
            r2.initCause(r0)     // Catch:{ all -> 0x011f }
            io.netty.util.internal.ThrowableUtil.addSuppressedAndClear(r2, r6)     // Catch:{ all -> 0x011f }
            throw r2     // Catch:{ all -> 0x011f }
        L_0x0140:
            r0 = move-exception
            r2 = r0
            if (r13 == 0) goto L_0x016f
            boolean r0 = r13.isFile()     // Catch:{ all -> 0x0163 }
            if (r0 == 0) goto L_0x016f
            boolean r0 = r13.canRead()     // Catch:{ all -> 0x0163 }
            if (r0 == 0) goto L_0x016f
            boolean r0 = io.netty.util.internal.NativeLibraryLoader.NoexecVolumeDetector.canExecuteExecutable(r13)     // Catch:{ all -> 0x0163 }
            if (r0 != 0) goto L_0x016f
            io.netty.util.internal.logging.InternalLogger r0 = logger     // Catch:{ all -> 0x0163 }
            java.lang.String r4 = "{} exists but cannot be executed even when execute permissions set; check volume for \"noexec\" flag; use -Dio.netty.native.workdir=[path] to set native working directory separately."
            java.lang.String r7 = r13.getPath()     // Catch:{ all -> 0x0163 }
            r0.info((java.lang.String) r4, (java.lang.Object) r7)     // Catch:{ all -> 0x0163 }
            goto L_0x016f
        L_0x0163:
            r0 = move-exception
            r6.add(r0)     // Catch:{ all -> 0x011f }
            io.netty.util.internal.logging.InternalLogger r4 = logger     // Catch:{ all -> 0x011f }
            java.lang.String r7 = "Error checking if {} is on a file store mounted with noexec"
            r4.debug(r7, r13, r0)     // Catch:{ all -> 0x011f }
            goto L_0x0170
        L_0x016f:
        L_0x0170:
            io.netty.util.internal.ThrowableUtil.addSuppressedAndClear(r2, r6)     // Catch:{ all -> 0x011f }
            throw r2     // Catch:{ all -> 0x011f }
        L_0x0175:
            closeQuietly(r10)
            closeQuietly(r12)
            if (r13 == 0) goto L_0x018a
            boolean r2 = DELETE_NATIVE_LIB_AFTER_LOADING
            if (r2 == 0) goto L_0x0187
            boolean r2 = r13.delete()
            if (r2 != 0) goto L_0x018a
        L_0x0187:
            r13.deleteOnExit()
        L_0x018a:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.NativeLibraryLoader.load(java.lang.String, java.lang.ClassLoader):void");
    }

    private static void loadLibrary(ClassLoader loader, String name, boolean absolute) {
        Throwable suppressed = null;
        try {
            loadLibraryByHelper(tryToLoadClass(loader, NativeLibraryUtil.class), name, absolute);
            logger.debug("Successfully loaded the library {}", (Object) name);
        } catch (UnsatisfiedLinkError e) {
            Throwable suppressed2 = e;
            logger.debug("Unable to load the library '{}', trying other loading mechanism.", name, e);
            NativeLibraryUtil.loadLibrary(name, absolute);
            logger.debug("Successfully loaded the library {}", (Object) name);
        } catch (Exception e2) {
            suppressed = e2;
            try {
                logger.debug("Unable to load the library '{}', trying other loading mechanism.", name, e2);
                NativeLibraryUtil.loadLibrary(name, absolute);
                logger.debug("Successfully loaded the library {}", (Object) name);
            } catch (UnsatisfiedLinkError ule) {
                ThrowableUtil.addSuppressed((Throwable) ule, suppressed);
                throw ule;
            }
        }
    }

    private static void loadLibraryByHelper(final Class<?> helper, final String name, final boolean absolute) {
        Object ret = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    Method method = helper.getMethod("loadLibrary", new Class[]{String.class, Boolean.TYPE});
                    method.setAccessible(true);
                    return method.invoke((Object) null, new Object[]{name, Boolean.valueOf(absolute)});
                } catch (Exception e) {
                    return e;
                }
            }
        });
        if (ret instanceof Throwable) {
            Throwable t = (Throwable) ret;
            if (!(t instanceof UnsatisfiedLinkError)) {
                Throwable cause = t.getCause();
                if (cause instanceof UnsatisfiedLinkError) {
                    throw ((UnsatisfiedLinkError) cause);
                }
                UnsatisfiedLinkError ule = new UnsatisfiedLinkError(t.getMessage());
                ule.initCause(t);
                throw ule;
            }
            throw new AssertionError(t + " should be a wrapper throwable");
        }
    }

    private static Class<?> tryToLoadClass(final ClassLoader loader, final Class<?> helper) {
        try {
            return Class.forName(helper.getName(), false, loader);
        } catch (ClassNotFoundException e1) {
            if (loader != null) {
                try {
                    final byte[] classBinary = classToByteArray(helper);
                    return (Class) AccessController.doPrivileged(new PrivilegedAction<Class<?>>() {
                        public Class<?> run() {
                            Class<ClassLoader> cls = ClassLoader.class;
                            try {
                                Class cls2 = Integer.TYPE;
                                Method defineClass = cls.getDeclaredMethod("defineClass", new Class[]{String.class, byte[].class, cls2, cls2});
                                defineClass.setAccessible(true);
                                return (Class) defineClass.invoke(loader, new Object[]{helper.getName(), classBinary, 0, Integer.valueOf(classBinary.length)});
                            } catch (Exception e) {
                                throw new IllegalStateException("Define class failed!", e);
                            }
                        }
                    });
                } catch (ClassNotFoundException e2) {
                    ThrowableUtil.addSuppressed((Throwable) e2, (Throwable) e1);
                    throw e2;
                } catch (RuntimeException e22) {
                    ThrowableUtil.addSuppressed((Throwable) e22, (Throwable) e1);
                    throw e22;
                } catch (Error e23) {
                    ThrowableUtil.addSuppressed((Throwable) e23, (Throwable) e1);
                    throw e23;
                }
            } else {
                throw e1;
            }
        }
    }

    private static byte[] classToByteArray(Class<?> clazz) {
        String fileName = clazz.getName();
        int lastDot = fileName.lastIndexOf(46);
        if (lastDot > 0) {
            fileName = fileName.substring(lastDot + 1);
        }
        URL classUrl = clazz.getResource(fileName + ".class");
        if (classUrl != null) {
            byte[] buf = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            try {
                InputStream in = classUrl.openStream();
                while (true) {
                    int read = in.read(buf);
                    int r = read;
                    if (read != -1) {
                        out.write(buf, 0, r);
                    } else {
                        byte[] byteArray = out.toByteArray();
                        closeQuietly(in);
                        closeQuietly(out);
                        return byteArray;
                    }
                }
            } catch (IOException ex) {
                throw new ClassNotFoundException(clazz.getName(), ex);
            } catch (Throwable th) {
                closeQuietly((Closeable) null);
                closeQuietly(out);
                throw th;
            }
        } else {
            throw new ClassNotFoundException(clazz.getName());
        }
    }

    private static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }

    private NativeLibraryLoader() {
    }

    public static final class NoexecVolumeDetector {
        /* access modifiers changed from: private */
        public static boolean canExecuteExecutable(File file) {
            if (PlatformDependent.javaVersion() < 7 || file.canExecute()) {
                return true;
            }
            Set<PosixFilePermission> existingFilePermissions = Files.getPosixFilePermissions(file.toPath(), new LinkOption[0]);
            Set<PosixFilePermission> executePermissions = EnumSet.of(PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OTHERS_EXECUTE);
            if (existingFilePermissions.containsAll(executePermissions)) {
                return false;
            }
            Set<PosixFilePermission> newPermissions = EnumSet.copyOf(existingFilePermissions);
            newPermissions.addAll(executePermissions);
            Files.setPosixFilePermissions(file.toPath(), newPermissions);
            return file.canExecute();
        }

        private NoexecVolumeDetector() {
        }
    }
}
