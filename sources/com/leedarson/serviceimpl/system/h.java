package com.leedarson.serviceimpl.system;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.leedarson.base.http.observer.l;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.tbruyelle.rxpermissions2.a;
import io.reactivex.disposables.b;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* compiled from: AppRunTimeLogBackUpUtil */
public class h {
    static b a;
    static boolean b = false;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void j(Activity activity, boolean z) {
        if (!PatchProxy.proxy(new Object[]{activity, new Byte(z ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 8799, new Class[]{Activity.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            b bVar = a;
            if (bVar != null && !bVar.isDisposed()) {
                a.dispose();
            }
            a = new com.tbruyelle.rxpermissions2.b(activity).l("android.permission.WRITE_EXTERNAL_STORAGE").Y(new b(activity), a.c);
        }
    }

    static /* synthetic */ void h(Activity activity, a permission) {
        Class[] clsArr = {Activity.class, a.class};
        if (!PatchProxy.proxy(new Object[]{activity, permission}, (Object) null, changeQuickRedirect, true, 8807, clsArr, Void.TYPE).isSupported && permission.b) {
            c(activity);
        }
    }

    static /* synthetic */ void i(Throwable throwable) {
    }

    private static void c(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 8800, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            if (b) {
                Toast.makeText(activity, "is backing up", 0).show();
                return;
            }
            Toast.makeText(activity, "now is backup runing", 0).show();
            Handler _handler = new Handler(Looper.getMainLooper());
            b = true;
            l.l.execute(new d(_handler, activity));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0219  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0222  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x022b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void g(android.os.Handler r17, android.app.Activity r18) {
        /*
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r17
            r9 = 1
            r1[r9] = r18
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.os.Handler> r0 = android.os.Handler.class
            r6[r8] = r0
            java.lang.Class<android.app.Activity> r0 = android.app.Activity.class
            r6[r9] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r2 = 0
            r4 = 1
            r5 = 8805(0x2265, float:1.2338E-41)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r1 = r17
            r2 = r18
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = android.os.Environment.DIRECTORY_DOWNLOADS
            java.io.File r3 = android.os.Environment.getExternalStoragePublicDirectory(r3)
            java.lang.String r3 = r3.getAbsolutePath()
            r0.append(r3)
            java.lang.String r3 = java.io.File.separator
            r0.append(r3)
            java.lang.String r4 = "ldsBackUp"
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            java.io.File r0 = new java.io.File
            r0.<init>(r4)
            r5 = r0
            boolean r0 = r5.exists()
            if (r0 != 0) goto L_0x0057
            r5.mkdir()
        L_0x0057:
            java.util.Locale r0 = java.util.Locale.US
            java.lang.Object[] r6 = new java.lang.Object[r9]
            com.leedarson.base.application.BaseApplication r7 = com.leedarson.base.application.BaseApplication.b()
            java.io.File r7 = r7.getFilesDir()
            java.lang.String r7 = r7.getPath()
            r6[r8] = r7
            java.lang.String r7 = "%s/web/log"
            java.lang.String r6 = java.lang.String.format(r0, r7, r6)
            com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r4)
            r7.append(r3)
            java.lang.String r9 = "runtime"
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            com.leedarson.base.utils.w.j(r0, r6, r7, r8)
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r4)
            r7.append(r3)
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            r0.<init>(r7)
            r7 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            com.leedarson.base.application.BaseApplication r10 = com.leedarson.base.application.BaseApplication.b()
            java.io.File r10 = r10.getCacheDir()
            r0.append(r10)
            r0.append(r3)
            java.lang.String r10 = "elkLog"
            r0.append(r10)
            java.lang.String r11 = r0.toString()
            com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r4)
            r12.append(r3)
            r12.append(r10)
            java.lang.String r12 = r12.toString()
            com.leedarson.base.utils.w.j(r0, r11, r12, r8)
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r4)
            r12.append(r3)
            r12.append(r10)
            java.lang.String r12 = r12.toString()
            r0.<init>(r12)
            r12 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            com.leedarson.base.application.BaseApplication r13 = com.leedarson.base.application.BaseApplication.b()
            java.io.File r13 = r13.getCacheDir()
            r0.append(r13)
            r0.append(r3)
            java.lang.String r13 = "elkLogBackup"
            r0.append(r13)
            java.lang.String r14 = r0.toString()
            com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r4)
            r15.append(r3)
            r15.append(r13)
            java.lang.String r15 = r15.toString()
            com.leedarson.base.utils.w.j(r0, r14, r15, r8)
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r4)
            r15.append(r3)
            r15.append(r13)
            java.lang.String r15 = r15.toString()
            r0.<init>(r15)
            r15 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0209 }
            r0.<init>()     // Catch:{ Exception -> 0x0209 }
            r0.append(r4)     // Catch:{ Exception -> 0x0209 }
            r0.append(r3)     // Catch:{ Exception -> 0x0209 }
            java.lang.String r8 = "runtime.zip"
            r0.append(r8)     // Catch:{ Exception -> 0x0209 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0209 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0209 }
            r8.<init>()     // Catch:{ Exception -> 0x0209 }
            r8.append(r4)     // Catch:{ Exception -> 0x0209 }
            r8.append(r3)     // Catch:{ Exception -> 0x0209 }
            r17 = r5
            java.lang.String r5 = "elkLog.zip"
            r8.append(r5)     // Catch:{ Exception -> 0x0203 }
            java.lang.String r5 = r8.toString()     // Catch:{ Exception -> 0x0203 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0203 }
            r8.<init>()     // Catch:{ Exception -> 0x0203 }
            r8.append(r4)     // Catch:{ Exception -> 0x0203 }
            r8.append(r3)     // Catch:{ Exception -> 0x0203 }
            r18 = r6
            java.lang.String r6 = "elkLogBackup.zip"
            r8.append(r6)     // Catch:{ Exception -> 0x01ff }
            java.lang.String r6 = r8.toString()     // Catch:{ Exception -> 0x01ff }
            e(r0)     // Catch:{ Exception -> 0x01ff }
            e(r5)     // Catch:{ Exception -> 0x01ff }
            e(r6)     // Catch:{ Exception -> 0x01ff }
            java.io.File r8 = new java.io.File     // Catch:{ Exception -> 0x01ff }
            r16 = r11
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01fd }
            r11.<init>()     // Catch:{ Exception -> 0x01fd }
            r11.append(r4)     // Catch:{ Exception -> 0x01fd }
            r11.append(r3)     // Catch:{ Exception -> 0x01fd }
            r11.append(r9)     // Catch:{ Exception -> 0x01fd }
            java.lang.String r9 = r11.toString()     // Catch:{ Exception -> 0x01fd }
            r8.<init>(r9)     // Catch:{ Exception -> 0x01fd }
            java.lang.String r8 = r8.getAbsolutePath()     // Catch:{ Exception -> 0x01fd }
            java.io.File r9 = new java.io.File     // Catch:{ Exception -> 0x01fd }
            r9.<init>(r0)     // Catch:{ Exception -> 0x01fd }
            java.lang.String r9 = r9.getAbsolutePath()     // Catch:{ Exception -> 0x01fd }
            b(r8, r9)     // Catch:{ Exception -> 0x01fd }
            java.io.File r8 = new java.io.File     // Catch:{ Exception -> 0x01fd }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01fd }
            r9.<init>()     // Catch:{ Exception -> 0x01fd }
            r9.append(r4)     // Catch:{ Exception -> 0x01fd }
            r9.append(r3)     // Catch:{ Exception -> 0x01fd }
            r9.append(r10)     // Catch:{ Exception -> 0x01fd }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x01fd }
            r8.<init>(r9)     // Catch:{ Exception -> 0x01fd }
            java.lang.String r8 = r8.getAbsolutePath()     // Catch:{ Exception -> 0x01fd }
            java.io.File r9 = new java.io.File     // Catch:{ Exception -> 0x01fd }
            r9.<init>(r5)     // Catch:{ Exception -> 0x01fd }
            java.lang.String r9 = r9.getAbsolutePath()     // Catch:{ Exception -> 0x01fd }
            b(r8, r9)     // Catch:{ Exception -> 0x01fd }
            java.io.File r8 = new java.io.File     // Catch:{ Exception -> 0x01fd }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01fd }
            r9.<init>()     // Catch:{ Exception -> 0x01fd }
            r9.append(r4)     // Catch:{ Exception -> 0x01fd }
            r9.append(r3)     // Catch:{ Exception -> 0x01fd }
            r9.append(r13)     // Catch:{ Exception -> 0x01fd }
            java.lang.String r3 = r9.toString()     // Catch:{ Exception -> 0x01fd }
            r8.<init>(r3)     // Catch:{ Exception -> 0x01fd }
            java.lang.String r3 = r8.getAbsolutePath()     // Catch:{ Exception -> 0x01fd }
            java.io.File r8 = new java.io.File     // Catch:{ Exception -> 0x01fd }
            r8.<init>(r6)     // Catch:{ Exception -> 0x01fd }
            java.lang.String r8 = r8.getAbsolutePath()     // Catch:{ Exception -> 0x01fd }
            b(r3, r8)     // Catch:{ Exception -> 0x01fd }
            goto L_0x0213
        L_0x01fd:
            r0 = move-exception
            goto L_0x0210
        L_0x01ff:
            r0 = move-exception
            r16 = r11
            goto L_0x0210
        L_0x0203:
            r0 = move-exception
            r18 = r6
            r16 = r11
            goto L_0x0210
        L_0x0209:
            r0 = move-exception
            r17 = r5
            r18 = r6
            r16 = r11
        L_0x0210:
            r0.printStackTrace()
        L_0x0213:
            boolean r0 = r7.exists()
            if (r0 == 0) goto L_0x021c
            d(r7)
        L_0x021c:
            boolean r0 = r12.exists()
            if (r0 == 0) goto L_0x0225
            d(r12)
        L_0x0225:
            boolean r0 = r15.exists()
            if (r0 == 0) goto L_0x022e
            d(r15)
        L_0x022e:
            com.leedarson.serviceimpl.system.c r0 = new com.leedarson.serviceimpl.system.c
            r0.<init>(r2, r4)
            r1.post(r0)
            r3 = 0
            b = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.system.h.g(android.os.Handler, android.app.Activity):void");
    }

    static /* synthetic */ void f(Activity activity, String sdCardBackUpDirPath) {
        if (!PatchProxy.proxy(new Object[]{activity, sdCardBackUpDirPath}, (Object) null, changeQuickRedirect, true, 8806, new Class[]{Activity.class, String.class}, Void.TYPE).isSupported) {
            Toast.makeText(activity, "back runtime data success,you can check in belowpath:\n" + sdCardBackUpDirPath, 0).show();
        }
    }

    static void e(String filePath) {
        if (!PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 8801, new Class[]{String.class}, Void.TYPE).isSupported) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    static void d(File file) {
        File[] files;
        if (!PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 8802, new Class[]{File.class}, Void.TYPE).isSupported && file.isDirectory() && (files = file.listFiles()) != null) {
            for (File delete : files) {
                delete.delete();
            }
        }
    }

    public static void b(String srcFileString, String zipFileString) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{srcFileString, zipFileString}, (Object) null, changeQuickRedirect, true, 8803, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
            File file = new File(srcFileString);
            timber.log.a.i("---- " + file.getParent() + "===" + file.getAbsolutePath(), new Object[0]);
            StringBuilder sb = new StringBuilder();
            sb.append(file.getParent());
            sb.append(File.separator);
            a(sb.toString(), file.getName(), outZip);
            outZip.finish();
            outZip.close();
        }
    }

    private static void a(String folderString, String fileString, ZipOutputStream zipOutputSteam) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, ZipOutputStream.class};
        if (!PatchProxy.proxy(new Object[]{folderString, fileString, zipOutputSteam}, (Object) null, changeQuickRedirect, true, 8804, clsArr, Void.TYPE).isSupported && zipOutputSteam != null) {
            File file = new File(folderString + fileString);
            if (file.isFile()) {
                ZipEntry zipEntry = new ZipEntry(fileString);
                FileInputStream inputStream = new FileInputStream(file);
                zipOutputSteam.putNextEntry(zipEntry);
                byte[] buffer = new byte[4096];
                while (true) {
                    int read = inputStream.read(buffer);
                    int len = read;
                    if (read != -1) {
                        zipOutputSteam.write(buffer, 0, len);
                    } else {
                        zipOutputSteam.closeEntry();
                        return;
                    }
                }
            } else {
                String[] fileList = file.list();
                if (fileList.length <= 0) {
                    zipOutputSteam.putNextEntry(new ZipEntry(fileString + File.separator));
                    zipOutputSteam.closeEntry();
                }
                for (int i = 0; i < fileList.length; i++) {
                    a(folderString + fileString + "/", fileList[i], zipOutputSteam);
                }
            }
        }
    }
}
