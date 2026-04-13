package com.leedarson.base.utils;

import android.content.Context;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* compiled from: FileSystem */
public abstract class l {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean f(Context context, String fileName, Object obj) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, fileName, obj}, (Object) null, changeQuickRedirect2, true, 474, new Class[]{Context.class, String.class, Object.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        File file = new File(context.getFilesDir(), fileName);
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

    public static Object c(Context context, String fileName) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, fileName}, (Object) null, changeQuickRedirect2, true, 475, new Class[]{Context.class, String.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        File file = new File(context.getFilesDir(), fileName);
        if (!file.exists()) {
            return null;
        }
        ObjectInputStream ois = null;
        try {
            ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(file));
            Object result = ois2.readObject();
            try {
                ois2.close();
            } catch (Exception e) {
            }
            return result;
        } catch (IOException | ClassNotFoundException e2) {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e3) {
                }
            }
            return null;
        } catch (Throwable th) {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
    }

    public static File a(File dir, String filename, String content) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir, filename, content}, (Object) null, changeQuickRedirect, true, 477, new Class[]{File.class, cls, cls}, File.class);
        return proxy.isSupported ? (File) proxy.result : h(dir, filename, content, true);
    }

    public static File h(File dir, String filename, String content, boolean append) {
        Class<String> cls = String.class;
        Object[] objArr = {dir, filename, content, new Byte(append ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 478, new Class[]{File.class, cls, cls, Boolean.TYPE}, File.class);
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
            FileOutputStream fos = new FileOutputStream(file, append);
            fos.write(content.getBytes());
            fos.flush();
            fos.close();
            return file;
        } catch (IOException e) {
            return file;
        }
    }

    public static String d(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 479, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (!file.exists()) {
            return "";
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
                    sb.append("\n");
                } else {
                    br.close();
                    fr.close();
                    return sb.toString();
                }
            }
        } catch (IOException e) {
            return "";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x00c8 A[SYNTHETIC, Splitter:B:47:0x00c8] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00d4 A[SYNTHETIC, Splitter:B:52:0x00d4] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00e0 A[SYNTHETIC, Splitter:B:59:0x00e0] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00ec A[SYNTHETIC, Splitter:B:64:0x00ec] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00f5 A[SYNTHETIC, Splitter:B:69:0x00f5] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0101 A[SYNTHETIC, Splitter:B:74:0x0101] */
    /* JADX WARNING: Removed duplicated region for block: B:83:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:84:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:44:0x00c3=Splitter:B:44:0x00c3, B:56:0x00db=Splitter:B:56:0x00db} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void g(okhttp3.e0 r18, java.io.File r19, com.leedarson.base.http.listener.b r20) {
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
            r5 = 480(0x1e0, float:6.73E-43)
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
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x00d8, IOException -> 0x00c0, all -> 0x00bb }
            r0.<init>(r3)     // Catch:{ FileNotFoundException -> 0x00d8, IOException -> 0x00c0, all -> 0x00bb }
            r6 = r0
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]     // Catch:{ FileNotFoundException -> 0x00d8, IOException -> 0x00c0, all -> 0x00bb }
        L_0x0064:
            int r12 = r7.read(r0)     // Catch:{ FileNotFoundException -> 0x00d8, IOException -> 0x00c0, all -> 0x00bb }
            r13 = r12
            r14 = -1
            if (r12 == r14) goto L_0x0094
            r6.write(r0, r8, r13)     // Catch:{ FileNotFoundException -> 0x00d8, IOException -> 0x00c0, all -> 0x00bb }
            long r14 = (long) r13
            long r4 = r4 + r14
            if (r9 == 0) goto L_0x008e
            r14 = 100
            long r16 = r4 * r14
            r18 = r9
            long r8 = r16 / r10
            int r8 = (int) r8     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x00a0 }
            r2.onProgress(r8)     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x00a0 }
            long r14 = r14 * r4
            long r14 = r14 / r10
            int r8 = (int) r14     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x00a0 }
            r9 = 100
            if (r8 != r9) goto L_0x0090
            java.lang.String r8 = r3.getAbsolutePath()     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x00a0 }
            r2.onFinish(r8)     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x00a0 }
            goto L_0x0090
        L_0x008e:
            r18 = r9
        L_0x0090:
            r8 = 0
            r9 = r18
            goto L_0x0064
        L_0x0094:
            r18 = r9
            if (r18 != 0) goto L_0x00a4
            java.lang.String r8 = r3.getAbsolutePath()     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x00a0 }
            r2.onFinish(r8)     // Catch:{ FileNotFoundException -> 0x00a2, IOException -> 0x00a0 }
            goto L_0x00a4
        L_0x00a0:
            r0 = move-exception
            goto L_0x00c3
        L_0x00a2:
            r0 = move-exception
            goto L_0x00db
        L_0x00a4:
            r6.close()     // Catch:{ IOException -> 0x00a9 }
            goto L_0x00af
        L_0x00a9:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r0.printStackTrace()
        L_0x00af:
            r7.close()     // Catch:{ IOException -> 0x00b4 }
        L_0x00b3:
            goto L_0x00f0
        L_0x00b4:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r0.printStackTrace()
            goto L_0x00b3
        L_0x00bb:
            r0 = move-exception
            r18 = r9
            r8 = r0
            goto L_0x00f3
        L_0x00c0:
            r0 = move-exception
            r18 = r9
        L_0x00c3:
            r0.printStackTrace()     // Catch:{ all -> 0x00f1 }
            if (r6 == 0) goto L_0x00d2
            r6.close()     // Catch:{ IOException -> 0x00cc }
            goto L_0x00d2
        L_0x00cc:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r0.printStackTrace()
        L_0x00d2:
            if (r7 == 0) goto L_0x00f0
            r7.close()     // Catch:{ IOException -> 0x00b4 }
            goto L_0x00b3
        L_0x00d8:
            r0 = move-exception
            r18 = r9
        L_0x00db:
            r0.printStackTrace()     // Catch:{ all -> 0x00f1 }
            if (r6 == 0) goto L_0x00ea
            r6.close()     // Catch:{ IOException -> 0x00e4 }
            goto L_0x00ea
        L_0x00e4:
            r0 = move-exception
            r8 = r0
            r0 = r8
            r0.printStackTrace()
        L_0x00ea:
            if (r7 == 0) goto L_0x00f0
            r7.close()     // Catch:{ IOException -> 0x00b4 }
            goto L_0x00b3
        L_0x00f0:
            return
        L_0x00f1:
            r0 = move-exception
            r8 = r0
        L_0x00f3:
            if (r6 == 0) goto L_0x00ff
            r6.close()     // Catch:{ IOException -> 0x00f9 }
            goto L_0x00ff
        L_0x00f9:
            r0 = move-exception
            r9 = r0
            r0 = r9
            r0.printStackTrace()
        L_0x00ff:
            if (r7 == 0) goto L_0x010b
            r7.close()     // Catch:{ IOException -> 0x0105 }
            goto L_0x010b
        L_0x0105:
            r0 = move-exception
            r9 = r0
            r0 = r9
            r0.printStackTrace()
        L_0x010b:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.utils.l.g(okhttp3.e0, java.io.File, com.leedarson.base.http.listener.b):void");
    }

    public static void e(File zipFile, String outputDirectory, boolean isReWrite) {
        if (!PatchProxy.proxy(new Object[]{zipFile, outputDirectory, new Byte(isReWrite ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 481, new Class[]{File.class, String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            File file = new File(outputDirectory);
            if (!file.exists()) {
                file.mkdirs();
            }
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
            byte[] buffer = new byte[1048576];
            for (ZipEntry zipEntry = zipInputStream.getNextEntry(); zipEntry != null; zipEntry = zipInputStream.getNextEntry()) {
                if (zipEntry.isDirectory()) {
                    File file2 = new File(outputDirectory + File.separator + zipEntry.getName());
                    if (isReWrite || !file2.exists()) {
                        file2.mkdir();
                    }
                } else {
                    File file3 = new File(outputDirectory + File.separator + zipEntry.getName());
                    if (isReWrite || !file3.exists()) {
                        file3.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file3);
                        while (true) {
                            int read = zipInputStream.read(buffer);
                            int count = read;
                            if (read <= 0) {
                                break;
                            }
                            fileOutputStream.write(buffer, 0, count);
                        }
                        fileOutputStream.close();
                    }
                }
            }
            zipInputStream.close();
        }
    }

    public static String b(String name) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{name}, (Object) null, changeQuickRedirect, true, 482, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        int i = name.lastIndexOf(".");
        if (i == -1 || i == name.length() - 1) {
            return name;
        }
        return name.substring(0, i);
    }
}
