package org.glassfish.grizzly.http.server.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ExpandJar {
    public static String expand(URL jar) {
        return expand(jar, System.getProperty("java.io.tmpdir"));
    }

    public static String expand(URL jar, String workFolder) {
        String pathname = jar.toString().replace('\\', '/');
        if (pathname.endsWith("!/")) {
            pathname = pathname.substring(0, pathname.length() - 2);
        }
        int period = pathname.lastIndexOf(46);
        if (period >= pathname.length() - 4) {
            pathname = pathname.substring(0, period);
        }
        int slash = pathname.lastIndexOf(47);
        if (slash >= 0) {
            pathname = pathname.substring(slash + 1);
        }
        return expand(jar, pathname, workFolder);
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x00a6 A[SYNTHETIC, Splitter:B:41:0x00a6] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String expand(java.net.URL r17, java.lang.String r18, java.lang.String r19) {
        /*
            java.io.File r0 = new java.io.File
            r1 = r19
            r0.<init>(r1)
            r2 = r0
            java.io.File r0 = new java.io.File
            r3 = r18
            r0.<init>(r2, r3)
            r4 = r0
            boolean r0 = r4.mkdir()
            r5 = 1
            java.lang.String r6 = "Unable to create directory: %s"
            r7 = 0
            if (r0 == 0) goto L_0x00ad
            java.net.URLConnection r0 = r17.openConnection()
            r8 = r0
            java.net.JarURLConnection r8 = (java.net.JarURLConnection) r8
            r8.setUseCaches(r7)
            r9 = 0
            r10 = 0
            java.util.jar.JarFile r0 = r8.getJarFile()     // Catch:{ IOException -> 0x0096 }
            r9 = r0
            java.util.Enumeration r0 = r9.entries()     // Catch:{ IOException -> 0x0096 }
        L_0x002f:
            boolean r11 = r0.hasMoreElements()     // Catch:{ IOException -> 0x0096 }
            if (r11 == 0) goto L_0x007f
            java.lang.Object r11 = r0.nextElement()     // Catch:{ IOException -> 0x0096 }
            java.util.jar.JarEntry r11 = (java.util.jar.JarEntry) r11     // Catch:{ IOException -> 0x0096 }
            java.lang.String r12 = r11.getName()     // Catch:{ IOException -> 0x0096 }
            r13 = 47
            int r13 = r12.lastIndexOf(r13)     // Catch:{ IOException -> 0x0096 }
            if (r13 < 0) goto L_0x0069
            java.io.File r14 = new java.io.File     // Catch:{ IOException -> 0x0096 }
            java.lang.String r15 = r12.substring(r7, r13)     // Catch:{ IOException -> 0x0096 }
            r14.<init>(r4, r15)     // Catch:{ IOException -> 0x0096 }
            boolean r15 = r14.mkdirs()     // Catch:{ IOException -> 0x0096 }
            if (r15 == 0) goto L_0x0057
            goto L_0x0069
        L_0x0057:
            java.lang.IllegalStateException r15 = new java.lang.IllegalStateException     // Catch:{ IOException -> 0x0096 }
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ IOException -> 0x0096 }
            java.lang.String r16 = r14.getAbsolutePath()     // Catch:{ IOException -> 0x0096 }
            r5[r7] = r16     // Catch:{ IOException -> 0x0096 }
            java.lang.String r5 = java.lang.String.format(r6, r5)     // Catch:{ IOException -> 0x0096 }
            r15.<init>(r5)     // Catch:{ IOException -> 0x0096 }
            throw r15     // Catch:{ IOException -> 0x0096 }
        L_0x0069:
            java.lang.String r14 = "/"
            boolean r14 = r12.endsWith(r14)     // Catch:{ IOException -> 0x0096 }
            if (r14 == 0) goto L_0x0072
            goto L_0x002f
        L_0x0072:
            java.io.InputStream r14 = r9.getInputStream(r11)     // Catch:{ IOException -> 0x0096 }
            r10 = r14
            expand((java.io.InputStream) r10, (java.io.File) r4, (java.lang.String) r12)     // Catch:{ IOException -> 0x0096 }
            r10.close()     // Catch:{ IOException -> 0x0096 }
            r10 = 0
            goto L_0x002f
        L_0x007f:
            if (r10 == 0) goto L_0x0087
            r10.close()     // Catch:{ all -> 0x0085 }
            goto L_0x0086
        L_0x0085:
            r0 = move-exception
        L_0x0086:
            r10 = 0
        L_0x0087:
            r9.close()     // Catch:{ all -> 0x008c }
            goto L_0x008d
        L_0x008c:
            r0 = move-exception
        L_0x008d:
            r0 = 0
            java.lang.String r5 = r4.getAbsolutePath()
            return r5
        L_0x0093:
            r0 = move-exception
            r5 = r0
            goto L_0x009c
        L_0x0096:
            r0 = move-exception
            deleteDir(r4)     // Catch:{ all -> 0x0093 }
            throw r0     // Catch:{ all -> 0x0093 }
        L_0x009c:
            if (r10 == 0) goto L_0x00a4
            r10.close()     // Catch:{ all -> 0x00a2 }
            goto L_0x00a3
        L_0x00a2:
            r0 = move-exception
        L_0x00a3:
            r10 = 0
        L_0x00a4:
            if (r9 == 0) goto L_0x00ac
            r9.close()     // Catch:{ all -> 0x00aa }
            goto L_0x00ab
        L_0x00aa:
            r0 = move-exception
        L_0x00ab:
            r9 = 0
        L_0x00ac:
            throw r5
        L_0x00ad:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String r8 = r4.getAbsolutePath()
            r5[r7] = r8
            java.lang.String r5 = java.lang.String.format(r6, r5)
            r0.<init>(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.util.ExpandJar.expand(java.net.URL, java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0071 A[SYNTHETIC, Splitter:B:30:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0078 A[SYNTHETIC, Splitter:B:34:0x0078] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copy(java.io.File r14, java.io.File r15) {
        /*
            r0 = 1
            boolean r1 = r14.isDirectory()
            r2 = 0
            if (r1 == 0) goto L_0x0011
            java.lang.String[] r1 = r14.list()
            boolean r0 = r15.mkdir()
            goto L_0x0018
        L_0x0011:
            r1 = 1
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.String r3 = ""
            r1[r2] = r3
        L_0x0018:
            if (r1 != 0) goto L_0x001c
            java.lang.String[] r1 = new java.lang.String[r2]
        L_0x001c:
            r2 = 0
        L_0x001d:
            int r3 = r1.length
            if (r2 >= r3) goto L_0x0092
            if (r0 == 0) goto L_0x0092
            java.io.File r3 = new java.io.File
            r4 = r1[r2]
            r3.<init>(r14, r4)
            java.io.File r4 = new java.io.File
            r5 = r1[r2]
            r4.<init>(r15, r5)
            boolean r5 = r3.isDirectory()
            if (r5 == 0) goto L_0x003b
            boolean r0 = copy(r3, r4)
            goto L_0x008f
        L_0x003b:
            r5 = 0
            r6 = 0
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ IOException -> 0x007e, all -> 0x006c }
            r7.<init>(r3)     // Catch:{ IOException -> 0x007e, all -> 0x006c }
            java.nio.channels.FileChannel r8 = r7.getChannel()     // Catch:{ IOException -> 0x007e, all -> 0x006c }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x006a, all -> 0x0068 }
            r5.<init>(r4)     // Catch:{ IOException -> 0x006a, all -> 0x0068 }
            java.nio.channels.FileChannel r5 = r5.getChannel()     // Catch:{ IOException -> 0x006a, all -> 0x0068 }
            r6 = r5
            r9 = 0
            long r11 = r8.size()     // Catch:{ IOException -> 0x006a, all -> 0x0068 }
            r13 = r6
            r8.transferTo(r9, r11, r13)     // Catch:{ IOException -> 0x006a, all -> 0x0068 }
            r8.close()     // Catch:{ IOException -> 0x005f }
            goto L_0x0060
        L_0x005f:
            r5 = move-exception
        L_0x0060:
            if (r6 == 0) goto L_0x008f
            r6.close()     // Catch:{ IOException -> 0x0066 }
        L_0x0065:
            goto L_0x008f
        L_0x0066:
            r5 = move-exception
            goto L_0x0065
        L_0x0068:
            r5 = move-exception
            goto L_0x006f
        L_0x006a:
            r5 = move-exception
            goto L_0x0081
        L_0x006c:
            r7 = move-exception
            r8 = r5
            r5 = r7
        L_0x006f:
            if (r8 == 0) goto L_0x0076
            r8.close()     // Catch:{ IOException -> 0x0075 }
            goto L_0x0076
        L_0x0075:
            r7 = move-exception
        L_0x0076:
            if (r6 == 0) goto L_0x007d
            r6.close()     // Catch:{ IOException -> 0x007c }
            goto L_0x007d
        L_0x007c:
            r7 = move-exception
        L_0x007d:
            throw r5
        L_0x007e:
            r7 = move-exception
            r8 = r5
            r5 = r7
        L_0x0081:
            r0 = 0
            if (r8 == 0) goto L_0x0089
            r8.close()     // Catch:{ IOException -> 0x0088 }
            goto L_0x0089
        L_0x0088:
            r5 = move-exception
        L_0x0089:
            if (r6 == 0) goto L_0x008f
            r6.close()     // Catch:{ IOException -> 0x0066 }
            goto L_0x0065
        L_0x008f:
            int r2 = r2 + 1
            goto L_0x001d
        L_0x0092:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.util.ExpandJar.copy(java.io.File, java.io.File):boolean");
    }

    public static boolean delete(File dir) {
        if (dir.isDirectory()) {
            return deleteDir(dir);
        }
        return dir.delete();
    }

    public static boolean deleteDir(File dir) {
        String[] files = dir.list();
        if (files == null) {
            files = new String[0];
        }
        for (String file : files) {
            File file2 = new File(dir, file);
            if (file2.isDirectory()) {
                deleteDir(file2);
            } else if (!file2.delete()) {
                throw new IllegalStateException(String.format("Unable to delete file: %s", new Object[]{file2.getAbsolutePath()}));
            }
        }
        return dir.delete();
    }

    protected static void expand(InputStream input, File docBase, String name) {
        BufferedOutputStream output = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(new File(docBase, name)));
            byte[] buffer = new byte[2048];
            while (true) {
                int n = input.read(buffer);
                if (n <= 0) {
                    try {
                        output.close();
                        return;
                    } catch (IOException e) {
                        return;
                    }
                } else {
                    output.write(buffer, 0, n);
                }
            }
        } catch (Throwable th) {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e2) {
                }
            }
            throw th;
        }
    }
}
