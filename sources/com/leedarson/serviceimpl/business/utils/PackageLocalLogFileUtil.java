package com.leedarson.serviceimpl.business.utils;

import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.w;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import io.reactivex.f;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.reactivestreams.a;

public class PackageLocalLogFileUtil {
    public static ChangeQuickRedirect changeQuickRedirect;
    String buglyLogDirPath;
    String crashLogDirPath;
    String elkLogBackUpDirPath;
    String elkUploadDirPath;
    String feedbackLogRawDir;
    String feedbackLogWorkSpaceDir;
    String runTimeLogRootDirPath;

    public PackageLocalLogFileUtil() {
        Locale locale = Locale.US;
        this.runTimeLogRootDirPath = String.format(locale, "%s/web/log", new Object[]{BaseApplication.b().getFilesDir().getPath()});
        StringBuilder sb = new StringBuilder();
        sb.append(BaseApplication.b().getCacheDir());
        String str = File.separator;
        sb.append(str);
        sb.append("elkLog");
        this.elkUploadDirPath = sb.toString();
        this.elkLogBackUpDirPath = BaseApplication.b().getCacheDir() + str + "elkLogBackup";
        this.feedbackLogWorkSpaceDir = BaseApplication.b().getCacheDir() + str + "feedback";
        this.feedbackLogRawDir = BaseApplication.b().getCacheDir() + str + "feedback" + str + "raw_file";
        this.buglyLogDirPath = String.format(locale, "%s/buglyLog", new Object[]{BaseApplication.b().getFilesDir().getPath()});
        this.crashLogDirPath = String.format(locale, "%s/crashLog", new Object[]{BaseApplication.b().getFilesDir().getPath()});
    }

    public e<String> createPackageLogJob() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7323, new Class[0], e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        return checkFolderExits().o(new a(this)).o(new j(this)).o(new d(this)).o(new c(this)).o(new f(this)).o(new i(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPackageLogJob$0 */
    public /* synthetic */ a b(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 7345, new Class[]{String.class}, a.class);
        return proxy.isSupported ? (a) proxy.result : createPackageLogJobOfRunTime();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPackageLogJob$1 */
    public /* synthetic */ a c(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 7344, new Class[]{String.class}, a.class);
        return proxy.isSupported ? (a) proxy.result : createPackageLogJobOfELKLogDir();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPackageLogJob$2 */
    public /* synthetic */ a d(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 7343, new Class[]{String.class}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        return createPackageLogToZipDir(this.buglyLogDirPath);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPackageLogJob$3 */
    public /* synthetic */ a e(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 7342, new Class[]{String.class}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        return createPackageLogToZipDir(this.crashLogDirPath);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPackageLogJob$4 */
    public /* synthetic */ a f(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 7341, new Class[]{String.class}, a.class);
        return proxy.isSupported ? (a) proxy.result : createPackageLogJobOfElkBackUpDir();
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPackageLogJob$5 */
    public /* synthetic */ a g(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 7340, new Class[]{String.class}, a.class);
        return proxy.isSupported ? (a) proxy.result : zipPackageLogFile();
    }

    private e<String> checkFolderExits() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7324, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new g(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$checkFolderExits$6 */
    public /* synthetic */ void a(f emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 7339, new Class[]{f.class}, Void.TYPE).isSupported) {
            File fileWorkSpaceDir = new File(this.feedbackLogWorkSpaceDir);
            if (!fileWorkSpaceDir.exists()) {
                fileWorkSpaceDir.mkdir();
            }
            File fileRawDir = new File(this.feedbackLogRawDir);
            if (!fileRawDir.exists()) {
                fileRawDir.mkdir();
            } else {
                File[] raw_listFile = fileRawDir.listFiles();
                if (raw_listFile != null || raw_listFile.length > 0) {
                    for (File delete : raw_listFile) {
                        delete.delete();
                    }
                }
            }
            emitter.onNext("");
            emitter.onComplete();
        }
    }

    private e<String> createPackageLogJobOfRunTime() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7325, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new k(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPackageLogJobOfRunTime$7 */
    public /* synthetic */ void j(f emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 7338, new Class[]{f.class}, Void.TYPE).isSupported) {
            File[] runtimeDirList = new File(this.runTimeLogRootDirPath).listFiles();
            if (runtimeDirList != null) {
                for (int i = 0; i < runtimeDirList.length; i++) {
                    if (checkLastModifyLess7Days(runtimeDirList[i])) {
                        String absolutePath = runtimeDirList[i].getAbsolutePath();
                        copyTo(absolutePath, this.feedbackLogRawDir + File.separator + runtimeDirList[i].getName());
                    }
                }
            }
            emitter.onNext("");
            emitter.onComplete();
        }
    }

    private e<String> createPackageLogJobOfELKLogDir() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7326, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new l(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPackageLogJobOfELKLogDir$8 */
    public /* synthetic */ void h(f emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 7337, new Class[]{f.class}, Void.TYPE).isSupported) {
            File[] runtimeDirList = new File(this.elkUploadDirPath).listFiles();
            if (runtimeDirList != null) {
                for (int i = 0; i < runtimeDirList.length; i++) {
                    if (checkLastModifyLess7Days(runtimeDirList[i])) {
                        String absolutePath = runtimeDirList[i].getAbsolutePath();
                        copyTo(absolutePath, this.feedbackLogRawDir + File.separator + runtimeDirList[i].getName());
                    }
                }
            }
            emitter.onNext("");
            emitter.onComplete();
        }
    }

    private e<String> createPackageLogJobOfElkBackUpDir() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7327, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new b(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPackageLogJobOfElkBackUpDir$9 */
    public /* synthetic */ void i(f emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 7336, new Class[]{f.class}, Void.TYPE).isSupported) {
            File[] runtimeDirList = new File(this.elkLogBackUpDirPath).listFiles();
            if (runtimeDirList != null) {
                for (int i = 0; i < runtimeDirList.length; i++) {
                    if (checkLastModifyLess7Days(runtimeDirList[i])) {
                        String absolutePath = runtimeDirList[i].getAbsolutePath();
                        copyTo(absolutePath, this.feedbackLogRawDir + File.separator + runtimeDirList[i].getName());
                    }
                }
            }
            emitter.onNext("");
            emitter.onComplete();
        }
    }

    private e<String> createPackageLogToZipDir(String targetFolderDirPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{targetFolderDirPath}, this, changeQuickRedirect, false, 7328, new Class[]{String.class}, e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new h(this, targetFolderDirPath), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$createPackageLogToZipDir$10 */
    public /* synthetic */ void k(String targetFolderDirPath, f emitter) {
        Class[] clsArr = {String.class, f.class};
        if (!PatchProxy.proxy(new Object[]{targetFolderDirPath, emitter}, this, changeQuickRedirect, false, 7335, clsArr, Void.TYPE).isSupported) {
            try {
                File[] runtimeDirList = new File(targetFolderDirPath).listFiles();
                if (runtimeDirList != null) {
                    for (int i = 0; i < runtimeDirList.length; i++) {
                        if (checkLastModifyLess7Days(runtimeDirList[i])) {
                            String absolutePath = runtimeDirList[i].getAbsolutePath();
                            copyTo(absolutePath, this.feedbackLogRawDir + File.separator + runtimeDirList[i].getName());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            emitter.onNext("");
            emitter.onComplete();
        }
    }

    private e<String> zipPackageLogFile() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7329, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new e(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$zipPackageLogFile$11 */
    public /* synthetic */ void l(f emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 7334, new Class[]{f.class}, Void.TYPE).isSupported) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.US);
            String zipTargetFilePath = this.feedbackLogWorkSpaceDir + File.separator + "feedback_" + dateFormat.format(new Date()) + ".zip";
            zipFolder(this.feedbackLogRawDir, zipTargetFilePath);
            emitter.onNext(zipTargetFilePath);
            File[] runtimeDirList = new File(this.feedbackLogRawDir).listFiles();
            if (runtimeDirList != null) {
                for (File delete : runtimeDirList) {
                    delete.delete();
                }
            }
            emitter.onComplete();
        }
    }

    private void copyTo(String fromFilePath, String toNewPath) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{fromFilePath, toNewPath}, this, changeQuickRedirect, false, 7330, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            w.j(BaseApplication.b(), fromFilePath, toNewPath, false);
        }
    }

    private boolean checkLastModifyLess7Days(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, this, changeQuickRedirect, false, 7331, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return System.currentTimeMillis() - file.lastModified() <= 259200000;
    }

    private void zipFolder(String sourceFolderPath, String zipFilePath) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{sourceFolderPath, zipFilePath}, this, changeQuickRedirect, false, 7332, clsArr, Void.TYPE).isSupported) {
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ZipOutputStream zos = new ZipOutputStream(bos);
            File sourceFolder = new File(sourceFolderPath);
            zipFiles(sourceFolder, sourceFolder.getName(), zos);
            zos.close();
            bos.close();
            fos.close();
        }
    }

    private void zipFiles(File sourceFile, String parentPath, ZipOutputStream zos) {
        if (!PatchProxy.proxy(new Object[]{sourceFile, parentPath, zos}, this, changeQuickRedirect, false, 7333, new Class[]{File.class, String.class, ZipOutputStream.class}, Void.TYPE).isSupported) {
            if (sourceFile.isDirectory()) {
                File[] files = sourceFile.listFiles();
                if (files != null && files.length > 0) {
                    for (File file : files) {
                        zipFiles(file, parentPath + "/" + file.getName(), zos);
                    }
                    return;
                }
                return;
            }
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            zos.putNextEntry(new ZipEntry(parentPath));
            while (true) {
                int read = bis.read(buffer);
                int bytesRead = read;
                if (read != -1) {
                    zos.write(buffer, 0, bytesRead);
                } else {
                    bis.close();
                    fis.close();
                    return;
                }
            }
        }
    }
}
