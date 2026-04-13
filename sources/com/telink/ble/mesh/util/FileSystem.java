package com.telink.ble.mesh.util;

import android.content.Context;
import android.text.TextUtils;
import com.leedarson.serviceimpl.reporters.k;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import timber.log.a;

public abstract class FileSystem {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean c(Context context, String path, String fileName, Object obj) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, path, fileName, obj}, (Object) null, changeQuickRedirect, true, 13393, new Class[]{Context.class, cls, cls, Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        File file = new File(path, fileName);
        FileOutputStream fos = null;
        ObjectOutputStream ops = null;
        boolean success = false;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos2 = new FileOutputStream(file);
            ObjectOutputStream ops2 = new ObjectOutputStream(fos2);
            ops2.writeObject(obj);
            ops2.flush();
            success = true;
            try {
                ops2.close();
                fos2.close();
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            k.a("writeAsObject持久化meshinfo到文件失败:" + e2.getMessage());
            if (ops != null) {
                ops.close();
            }
            if (ops != null) {
                fos.close();
            }
        } catch (Throwable th) {
            if (ops != null) {
                try {
                    ops.close();
                } catch (Exception e3) {
                    throw th;
                }
            }
            if (ops != null) {
                fos.close();
            }
            throw th;
        }
        return success;
    }

    public static Object[] a(Context context, String path, String fileName) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, path, fileName}, (Object) null, changeQuickRedirect, true, 13394, new Class[]{Context.class, cls, cls}, Object[].class);
        if (proxy.isSupported) {
            return (Object[]) proxy.result;
        }
        Object[] res = new Object[2];
        if (TextUtils.isEmpty(path)) {
            res[0] = 0;
            res[1] = null;
            return res;
        }
        File file = new File(path, fileName);
        PrintStream printStream = System.out;
        printStream.println("cfg:" + file.getAbsolutePath());
        if (!file.exists()) {
            res[0] = 0;
            res[1] = null;
            return res;
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            res[0] = 1;
            res[1] = ois.readObject();
            try {
                ois.close();
            } catch (Exception e) {
            }
        } catch (IOException | ClassNotFoundException e2) {
            MeshLogger.b("read object error : " + e2.toString());
            res[0] = -1;
            res[1] = null;
            if (ois != null) {
                ois.close();
            }
        } catch (Throwable th) {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e3) {
                }
            }
            throw th;
        }
        return res;
    }

    public static File e(File dir, String filename, String content) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir, filename, content}, (Object) null, changeQuickRedirect2, true, 13396, new Class[]{File.class, cls, cls}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file, false);
            fos.write(content.getBytes());
            fos.flush();
            fos.close();
            return file;
        } catch (IOException e) {
            a.d(e);
            return null;
        }
    }

    public static Object[] b(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 13397, new Class[]{File.class}, Object[].class);
        if (proxy.isSupported) {
            return (Object[]) proxy.result;
        }
        Object[] res = new Object[2];
        if (!file.exists()) {
            res[0] = 0;
            res[1] = null;
            return res;
        }
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = br.readLine();
                String line = readLine;
                if (readLine != null) {
                    sb.append(line);
                } else {
                    res[0] = 1;
                    res[1] = sb.toString();
                    br.close();
                    fr.close();
                    return res;
                }
            }
        } catch (Exception e) {
            MeshLogger.b("read json string error : " + e.toString());
            a.d(e);
            res[0] = -1;
            res[1] = null;
            return res;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x00f7 A[SYNTHETIC, Splitter:B:46:0x00f7] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0103 A[SYNTHETIC, Splitter:B:51:0x0103] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x010c A[SYNTHETIC, Splitter:B:56:0x010c] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0118 A[SYNTHETIC, Splitter:B:61:0x0118] */
    /* JADX WARNING: Removed duplicated region for block: B:70:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void d(okhttp3.e0 r18, java.io.File r19, com.leedarson.base.http.listener.b r20) {
        /*
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r18
            r9 = 1
            r1[r9] = r19
            r2 = 2
            r1[r2] = r20
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<okhttp3.e0> r0 = okhttp3.e0.class
            r6[r8] = r0
            java.lang.Class<java.io.File> r0 = java.io.File.class
            r6[r9] = r0
            java.lang.Class<com.leedarson.base.http.listener.b> r0 = com.leedarson.base.http.listener.b.class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r2 = 0
            r4 = 1
            r5 = 13398(0x3456, float:1.8775E-41)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002b
            return
        L_0x002b:
            r1 = r18
            r2 = r20
            r3 = r19
            boolean r0 = r3.exists()
            if (r0 == 0) goto L_0x0044
            r3.delete()
            r3.createNewFile()     // Catch:{ IOException -> 0x003e }
            goto L_0x0044
        L_0x003e:
            r0 = move-exception
            r4 = r0
            r0 = r4
            r0.printStackTrace()
        L_0x0044:
            r2.onStart()
            r4 = 0
            r6 = 0
            java.io.InputStream r7 = r1.byteStream()
            long r10 = r1.contentLength()
            r12 = -1
            int r0 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r0 == 0) goto L_0x0059
            goto L_0x005a
        L_0x0059:
            r9 = r8
        L_0x005a:
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00be, all -> 0x00b9 }
            r0.<init>(r3)     // Catch:{ Exception -> 0x00be, all -> 0x00b9 }
            r6 = r0
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x00be, all -> 0x00b9 }
        L_0x0064:
            int r12 = r7.read(r0)     // Catch:{ Exception -> 0x00be, all -> 0x00b9 }
            r13 = r12
            r14 = -1
            if (r12 == r14) goto L_0x0094
            r6.write(r0, r8, r13)     // Catch:{ Exception -> 0x00be, all -> 0x00b9 }
            long r14 = (long) r13
            long r4 = r4 + r14
            if (r9 == 0) goto L_0x008e
            r14 = 100
            long r16 = r4 * r14
            r18 = r9
            long r8 = r16 / r10
            int r8 = (int) r8     // Catch:{ Exception -> 0x00a0 }
            r2.onProgress(r8)     // Catch:{ Exception -> 0x00a0 }
            long r14 = r14 * r4
            long r14 = r14 / r10
            int r8 = (int) r14     // Catch:{ Exception -> 0x00a0 }
            r9 = 100
            if (r8 != r9) goto L_0x0090
            java.lang.String r8 = r3.getAbsolutePath()     // Catch:{ Exception -> 0x00a0 }
            r2.onFinish(r8)     // Catch:{ Exception -> 0x00a0 }
            goto L_0x0090
        L_0x008e:
            r18 = r9
        L_0x0090:
            r8 = 0
            r9 = r18
            goto L_0x0064
        L_0x0094:
            r18 = r9
            if (r18 != 0) goto L_0x00a2
            java.lang.String r8 = r3.getAbsolutePath()     // Catch:{ Exception -> 0x00a0 }
            r2.onFinish(r8)     // Catch:{ Exception -> 0x00a0 }
            goto L_0x00a2
        L_0x00a0:
            r0 = move-exception
            goto L_0x00c1
        L_0x00a2:
            r6.close()     // Catch:{ IOException -> 0x00a7 }
            goto L_0x00ad
        L_0x00a7:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r0.printStackTrace()
        L_0x00ad:
            r7.close()     // Catch:{ IOException -> 0x00b2 }
        L_0x00b1:
            goto L_0x0107
        L_0x00b2:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r0.printStackTrace()
            goto L_0x00b1
        L_0x00b9:
            r0 = move-exception
            r18 = r9
            r8 = r0
            goto L_0x010a
        L_0x00be:
            r0 = move-exception
            r18 = r9
        L_0x00c1:
            r0.printStackTrace()     // Catch:{ all -> 0x0108 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0108 }
            r8.<init>()     // Catch:{ all -> 0x0108 }
            java.lang.String r9 = "MESH OTA writeFile2Disk fail:"
            r8.append(r9)     // Catch:{ all -> 0x0108 }
            java.lang.String r9 = r0.getMessage()     // Catch:{ all -> 0x0108 }
            r8.append(r9)     // Catch:{ all -> 0x0108 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0108 }
            meshsdk.MeshLog.e(r8)     // Catch:{ all -> 0x0108 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0108 }
            r8.<init>()     // Catch:{ all -> 0x0108 }
            java.lang.String r9 = "writeFile2Disk:"
            r8.append(r9)     // Catch:{ all -> 0x0108 }
            java.lang.String r9 = r0.toString()     // Catch:{ all -> 0x0108 }
            r8.append(r9)     // Catch:{ all -> 0x0108 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0108 }
            r2.onFailure(r8)     // Catch:{ all -> 0x0108 }
            if (r6 == 0) goto L_0x0101
            r6.close()     // Catch:{ IOException -> 0x00fb }
            goto L_0x0101
        L_0x00fb:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r0.printStackTrace()
        L_0x0101:
            if (r7 == 0) goto L_0x0107
            r7.close()     // Catch:{ IOException -> 0x00b2 }
            goto L_0x00b1
        L_0x0107:
            return
        L_0x0108:
            r0 = move-exception
            r8 = r0
        L_0x010a:
            if (r6 == 0) goto L_0x0116
            r6.close()     // Catch:{ IOException -> 0x0110 }
            goto L_0x0116
        L_0x0110:
            r0 = move-exception
            r9 = r0
            r0 = r9
            r0.printStackTrace()
        L_0x0116:
            if (r7 == 0) goto L_0x0122
            r7.close()     // Catch:{ IOException -> 0x011c }
            goto L_0x0122
        L_0x011c:
            r0 = move-exception
            r9 = r0
            r0 = r9
            r0.printStackTrace()
        L_0x0122:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.util.FileSystem.d(okhttp3.e0, java.io.File, com.leedarson.base.http.listener.b):void");
    }
}
