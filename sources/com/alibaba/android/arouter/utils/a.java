package com.alibaba.android.arouter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;
import com.alibaba.android.arouter.thread.b;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: ClassUtils */
public class a {
    private static final String a = ("code_cache" + File.separator + "secondary-dexes");

    private static SharedPreferences b(Context context) {
        return context.getSharedPreferences("multidex.version", Build.VERSION.SDK_INT < 11 ? 0 : 4);
    }

    public static Set<String> a(Context context, String packageName) {
        Set<String> classNames = new HashSet<>();
        List<String> paths = c(context);
        CountDownLatch parserCtl = new CountDownLatch(paths.size());
        for (String path : paths) {
            b.a().execute(new C0014a(path, packageName, classNames, parserCtl));
        }
        parserCtl.await();
        Log.d("ARouter::", "Filter " + classNames.size() + " classes by packageName <" + packageName + ">");
        return classNames;
    }

    /* renamed from: com.alibaba.android.arouter.utils.a$a  reason: collision with other inner class name */
    /* compiled from: ClassUtils */
    public static final class C0014a implements Runnable {
        final /* synthetic */ String c;
        final /* synthetic */ String d;
        final /* synthetic */ Set f;
        final /* synthetic */ CountDownLatch q;

        C0014a(String str, String str2, Set set, CountDownLatch countDownLatch) {
            this.c = str;
            this.d = str2;
            this.f = set;
            this.q = countDownLatch;
        }

        public void run() {
            DexFile dexfile;
            DexFile dexfile2 = null;
            try {
                if (this.c.endsWith(".zip")) {
                    String str = this.c;
                    dexfile = DexFile.loadDex(str, this.c + ".tmp", 0);
                } else {
                    dexfile = new DexFile(this.c);
                }
                Enumeration<String> dexEntries = dexfile.entries();
                while (dexEntries.hasMoreElements()) {
                    String className = dexEntries.nextElement();
                    if (className.startsWith(this.d)) {
                        this.f.add(className);
                    }
                }
                try {
                    dexfile.close();
                } catch (Throwable th) {
                }
            } catch (Throwable th2) {
            }
            this.q.countDown();
        }
    }

    public static List<String> c(Context context) {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        File sourceApk = new File(applicationInfo.sourceDir);
        List<String> sourcePaths = new ArrayList<>();
        sourcePaths.add(applicationInfo.sourceDir);
        String extractedFilePrefix = sourceApk.getName() + ".classes";
        if (!d()) {
            int totalDexNumber = b(context).getInt("dex.number", 1);
            File dexDir = new File(applicationInfo.dataDir, a);
            int secondaryNumber = 2;
            while (secondaryNumber <= totalDexNumber) {
                File extractedFile = new File(dexDir, extractedFilePrefix + secondaryNumber + ".zip");
                if (extractedFile.isFile()) {
                    sourcePaths.add(extractedFile.getAbsolutePath());
                    secondaryNumber++;
                } else {
                    throw new IOException("Missing extracted secondary dex file '" + extractedFile.getPath() + "'");
                }
            }
        }
        if (com.alibaba.android.arouter.launcher.a.b() != 0) {
            sourcePaths.addAll(f(applicationInfo));
        }
        return sourcePaths;
    }

    private static List<String> f(ApplicationInfo applicationInfo) {
        String[] strArr;
        List<String> instantRunSourcePaths = new ArrayList<>();
        if (Build.VERSION.SDK_INT < 21 || (strArr = applicationInfo.splitSourceDirs) == null) {
            try {
                File instantRunFilePath = new File((String) Class.forName("com.android.tools.fd.runtime.Paths").getMethod("getDexFileDirectory", new Class[]{String.class}).invoke((Object) null, new Object[]{applicationInfo.packageName}));
                if (instantRunFilePath.exists() && instantRunFilePath.isDirectory()) {
                    for (File file : instantRunFilePath.listFiles()) {
                        if (file != null && file.exists() && file.isFile() && file.getName().endsWith(".dex")) {
                            instantRunSourcePaths.add(file.getAbsolutePath());
                        }
                    }
                    Log.d("ARouter::", "Found InstantRun support");
                }
            } catch (Exception e) {
                Log.e("ARouter::", "InstantRun support error, " + e.getMessage());
            }
        } else {
            instantRunSourcePaths.addAll(Arrays.asList(strArr));
            Log.d("ARouter::", "Found InstantRun support");
        }
        return instantRunSourcePaths;
    }

    private static boolean d() {
        boolean isMultidexCapable = false;
        String vmName = null;
        try {
            boolean z = false;
            if (e()) {
                vmName = "'YunOS'";
                if (Integer.valueOf(System.getProperty("ro.build.version.sdk")).intValue() >= 21) {
                    z = true;
                }
                isMultidexCapable = z;
            } else {
                vmName = "'Android'";
                String versionString = System.getProperty("java.vm.version");
                if (versionString != null) {
                    Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
                    if (matcher.matches()) {
                        try {
                            int major = Integer.parseInt(matcher.group(1));
                            int minor = Integer.parseInt(matcher.group(2));
                            if (major > 2 || (major == 2 && minor >= 1)) {
                                z = true;
                            }
                            isMultidexCapable = z;
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        } catch (Exception e2) {
        }
        StringBuilder sb = new StringBuilder();
        sb.append("VM with name ");
        sb.append(vmName);
        sb.append(isMultidexCapable ? " has multidex support" : " does not have multidex support");
        Log.i("ARouter::", sb.toString());
        return isMultidexCapable;
    }

    private static boolean e() {
        try {
            String version = System.getProperty("ro.yunos.version");
            String vmName = System.getProperty("java.vm.name");
            if ((vmName == null || !vmName.toLowerCase().contains("lemur")) && (version == null || version.trim().length() <= 0)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
