package com.leedarson.serviceimpl.business.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public final class FileIOUtils {
    public static ChangeQuickRedirect changeQuickRedirect;
    private static int sBufferSize = 524288;

    public interface OnProgressUpdateListener {
        void onProgressUpdate(double d);
    }

    private FileIOUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean writeFileFromIS(String filePath, InputStream is) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath, is}, (Object) null, changeQuickRedirect, true, 7257, new Class[]{String.class, InputStream.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromIS(getFileByPath(filePath), is, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(String filePath, InputStream is, boolean append) {
        Object[] objArr = {filePath, is, new Byte(append ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7258, new Class[]{String.class, InputStream.class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromIS(getFileByPath(filePath), is, append, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(File file, InputStream is) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file, is}, (Object) null, changeQuickRedirect, true, 7259, new Class[]{File.class, InputStream.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromIS(file, is, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(File file, InputStream is, boolean append) {
        Object[] objArr = {file, is, new Byte(append ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7260, new Class[]{File.class, InputStream.class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromIS(file, is, append, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(String filePath, InputStream is, OnProgressUpdateListener listener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath, is, listener}, (Object) null, changeQuickRedirect, true, 7261, new Class[]{String.class, InputStream.class, OnProgressUpdateListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromIS(getFileByPath(filePath), is, false, listener);
    }

    public static boolean writeFileFromIS(String filePath, InputStream is, boolean append, OnProgressUpdateListener listener) {
        Object[] objArr = {filePath, is, new Byte(append ? (byte) 1 : 0), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7262, new Class[]{String.class, InputStream.class, cls, OnProgressUpdateListener.class}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromIS(getFileByPath(filePath), is, append, listener);
    }

    public static boolean writeFileFromIS(File file, InputStream is, OnProgressUpdateListener listener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file, is, listener}, (Object) null, changeQuickRedirect, true, 7263, new Class[]{File.class, InputStream.class, OnProgressUpdateListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromIS(file, is, false, listener);
    }

    public static boolean writeFileFromIS(File file, InputStream is, boolean append, OnProgressUpdateListener listener) {
        Object[] objArr = {file, is, new Byte(append ? (byte) 1 : 0), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7264, new Class[]{File.class, InputStream.class, cls, OnProgressUpdateListener.class}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (is == null || !createOrExistsFile(file)) {
            return false;
        }
        OutputStream os = null;
        try {
            OutputStream os2 = new BufferedOutputStream(new FileOutputStream(file, append), sBufferSize);
            if (listener == null) {
                byte[] data = new byte[sBufferSize];
                while (true) {
                    int read = is.read(data);
                    int len = read;
                    if (read == -1) {
                        break;
                    }
                    os2.write(data, 0, len);
                }
            } else {
                double totalSize = (double) is.available();
                int curSize = 0;
                listener.onProgressUpdate(0.0d);
                byte[] data2 = new byte[sBufferSize];
                while (true) {
                    int read2 = is.read(data2);
                    int len2 = read2;
                    if (read2 != -1) {
                        os2.write(data2, 0, len2);
                        curSize += len2;
                        listener.onProgressUpdate(((double) curSize) / totalSize);
                    }
                }
                is.close();
                os2.close();
                return true;
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os2.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return true;
        } catch (IOException e3) {
            e3.printStackTrace();
            try {
                is.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            try {
                is.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean writeFileFromBytesByStream(String filePath, byte[] bytes) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath, bytes}, (Object) null, changeQuickRedirect, true, 7265, new Class[]{String.class, byte[].class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByStream(getFileByPath(filePath), bytes, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromBytesByStream(String filePath, byte[] bytes, boolean append) {
        Object[] objArr = {filePath, bytes, new Byte(append ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7266, new Class[]{String.class, byte[].class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByStream(getFileByPath(filePath), bytes, append, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bytes) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file, bytes}, (Object) null, changeQuickRedirect, true, 7267, new Class[]{File.class, byte[].class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByStream(file, bytes, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bytes, boolean append) {
        Object[] objArr = {file, bytes, new Byte(append ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7268, new Class[]{File.class, byte[].class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByStream(file, bytes, append, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromBytesByStream(String filePath, byte[] bytes, OnProgressUpdateListener listener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath, bytes, listener}, (Object) null, changeQuickRedirect, true, 7269, new Class[]{String.class, byte[].class, OnProgressUpdateListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByStream(getFileByPath(filePath), bytes, false, listener);
    }

    public static boolean writeFileFromBytesByStream(String filePath, byte[] bytes, boolean append, OnProgressUpdateListener listener) {
        Object[] objArr = {filePath, bytes, new Byte(append ? (byte) 1 : 0), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7270, new Class[]{String.class, byte[].class, cls, OnProgressUpdateListener.class}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByStream(getFileByPath(filePath), bytes, append, listener);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bytes, OnProgressUpdateListener listener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file, bytes, listener}, (Object) null, changeQuickRedirect, true, 7271, new Class[]{File.class, byte[].class, OnProgressUpdateListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByStream(file, bytes, false, listener);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bytes, boolean append, OnProgressUpdateListener listener) {
        Object[] objArr = {file, bytes, new Byte(append ? (byte) 1 : 0), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7272, new Class[]{File.class, byte[].class, cls, OnProgressUpdateListener.class}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (bytes == null) {
            return false;
        }
        return writeFileFromIS(file, (InputStream) new ByteArrayInputStream(bytes), append, listener);
    }

    public static boolean writeFileFromBytesByChannel(String filePath, byte[] bytes, boolean isForce) {
        Object[] objArr = {filePath, bytes, new Byte(isForce ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7273, new Class[]{String.class, byte[].class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByChannel(String filePath, byte[] bytes, boolean append, boolean isForce) {
        Object[] objArr = {filePath, bytes, new Byte(append ? (byte) 1 : 0), new Byte(isForce ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7274, new Class[]{String.class, byte[].class, cls, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce);
    }

    public static boolean writeFileFromBytesByChannel(File file, byte[] bytes, boolean isForce) {
        Object[] objArr = {file, bytes, new Byte(isForce ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7275, new Class[]{File.class, byte[].class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByChannel(File file, byte[] bytes, boolean append, boolean isForce) {
        Object[] objArr = {file, bytes, new Byte(append ? (byte) 1 : 0), new Byte(isForce ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7276, new Class[]{File.class, byte[].class, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (bytes == null || !createOrExistsFile(file)) {
            return false;
        }
        FileChannel fc = null;
        try {
            FileChannel fc2 = new FileOutputStream(file, append).getChannel();
            fc2.position(fc2.size());
            fc2.write(ByteBuffer.wrap(bytes));
            if (isForce) {
                fc2.force(true);
            }
            try {
                fc2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } catch (IOException e2) {
            e2.printStackTrace();
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean writeFileFromBytesByMap(String filePath, byte[] bytes, boolean isForce) {
        Object[] objArr = {filePath, bytes, new Byte(isForce ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7277, new Class[]{String.class, byte[].class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByMap(filePath, bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByMap(String filePath, byte[] bytes, boolean append, boolean isForce) {
        Object[] objArr = {filePath, bytes, new Byte(append ? (byte) 1 : 0), new Byte(isForce ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7278, new Class[]{String.class, byte[].class, cls, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByMap(getFileByPath(filePath), bytes, append, isForce);
    }

    public static boolean writeFileFromBytesByMap(File file, byte[] bytes, boolean isForce) {
        Object[] objArr = {file, bytes, new Byte(isForce ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7279, new Class[]{File.class, byte[].class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromBytesByMap(file, bytes, false, isForce);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0083 A[SYNTHETIC, Splitter:B:27:0x0083] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean writeFileFromBytesByMap(java.io.File r10, byte[] r11, boolean r12, boolean r13) {
        /*
            r0 = 4
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            r9 = 1
            r1[r9] = r11
            java.lang.Byte r2 = new java.lang.Byte
            r2.<init>(r12)
            r3 = 2
            r1[r3] = r2
            java.lang.Byte r2 = new java.lang.Byte
            r2.<init>(r13)
            r4 = 3
            r1[r4] = r2
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.io.File> r0 = java.io.File.class
            r6[r8] = r0
            java.lang.Class<byte[]> r0 = byte[].class
            r6[r9] = r0
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r6[r3] = r7
            r6[r4] = r7
            r2 = 0
            r4 = 1
            r0 = 7280(0x1c70, float:1.0201E-41)
            r3 = r5
            r5 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0042
            java.lang.Object r10 = r0.result
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            return r10
        L_0x0042:
            if (r11 == 0) goto L_0x009c
            boolean r0 = createOrExistsFile((java.io.File) r10)
            if (r0 != 0) goto L_0x004b
            goto L_0x009c
        L_0x004b:
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x007a, all -> 0x0076 }
            r1.<init>(r10, r12)     // Catch:{ IOException -> 0x007a, all -> 0x0076 }
            java.nio.channels.FileChannel r2 = r1.getChannel()     // Catch:{ IOException -> 0x007a, all -> 0x0076 }
            java.nio.channels.FileChannel$MapMode r3 = java.nio.channels.FileChannel.MapMode.READ_WRITE     // Catch:{ IOException -> 0x0074 }
            long r4 = r2.size()     // Catch:{ IOException -> 0x0074 }
            int r0 = r11.length     // Catch:{ IOException -> 0x0074 }
            long r6 = (long) r0     // Catch:{ IOException -> 0x0074 }
            java.nio.MappedByteBuffer r0 = r2.map(r3, r4, r6)     // Catch:{ IOException -> 0x0074 }
            r0.put(r11)     // Catch:{ IOException -> 0x0074 }
            if (r13 == 0) goto L_0x0069
            r0.force()     // Catch:{ IOException -> 0x0074 }
        L_0x0069:
            r2.close()     // Catch:{ IOException -> 0x006f }
            goto L_0x0073
        L_0x006f:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0073:
            return r9
        L_0x0074:
            r0 = move-exception
            goto L_0x007d
        L_0x0076:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L_0x008f
        L_0x007a:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x007d:
            r0.printStackTrace()     // Catch:{ all -> 0x008e }
            if (r2 == 0) goto L_0x008c
            r2.close()     // Catch:{ IOException -> 0x0087 }
            goto L_0x008c
        L_0x0087:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x008d
        L_0x008c:
        L_0x008d:
            return r8
        L_0x008e:
            r0 = move-exception
        L_0x008f:
            if (r2 == 0) goto L_0x009a
            r2.close()     // Catch:{ IOException -> 0x0095 }
            goto L_0x009a
        L_0x0095:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x009b
        L_0x009a:
        L_0x009b:
            throw r0
        L_0x009c:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.utils.FileIOUtils.writeFileFromBytesByMap(java.io.File, byte[], boolean, boolean):boolean");
    }

    public static boolean writeFileFromString(String filePath, String content) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath, content}, (Object) null, changeQuickRedirect, true, 7281, new Class[]{cls, cls}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromString(getFileByPath(filePath), content, false);
    }

    public static boolean writeFileFromString(String filePath, String content, boolean append) {
        Class<String> cls = String.class;
        Object[] objArr = {filePath, content, new Byte(append ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7282, new Class[]{cls, cls, cls2}, cls2);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromString(getFileByPath(filePath), content, append);
    }

    public static boolean writeFileFromString(File file, String content) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file, content}, (Object) null, changeQuickRedirect, true, 7283, new Class[]{File.class, String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : writeFileFromString(file, content, false);
    }

    public static boolean writeFileFromString(File file, String content, boolean append) {
        Object[] objArr = {file, content, new Byte(append ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7284, new Class[]{File.class, String.class, cls}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file == null || content == null || !createOrExistsFile(file)) {
            return false;
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } catch (IOException e2) {
            e2.printStackTrace();
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static List<String> readFile2List(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 7285, new Class[]{String.class}, List.class);
        return proxy.isSupported ? (List) proxy.result : readFile2List(getFileByPath(filePath), (String) null);
    }

    public static List<String> readFile2List(String filePath, String charsetName) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath, charsetName}, (Object) null, changeQuickRedirect, true, 7286, new Class[]{cls, cls}, List.class);
        return proxy.isSupported ? (List) proxy.result : readFile2List(getFileByPath(filePath), charsetName);
    }

    public static List<String> readFile2List(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 7287, new Class[]{File.class}, List.class);
        return proxy.isSupported ? (List) proxy.result : readFile2List(file, 0, Integer.MAX_VALUE, (String) null);
    }

    public static List<String> readFile2List(File file, String charsetName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file, charsetName}, (Object) null, changeQuickRedirect, true, 7288, new Class[]{File.class, String.class}, List.class);
        return proxy.isSupported ? (List) proxy.result : readFile2List(file, 0, Integer.MAX_VALUE, charsetName);
    }

    public static List<String> readFile2List(String filePath, int st, int end) {
        Object[] objArr = {filePath, new Integer(st), new Integer(end)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7289, new Class[]{String.class, cls, cls}, List.class);
        return proxy.isSupported ? (List) proxy.result : readFile2List(getFileByPath(filePath), st, end, (String) null);
    }

    public static List<String> readFile2List(String filePath, int st, int end, String charsetName) {
        Class<String> cls = String.class;
        Object[] objArr = {filePath, new Integer(st), new Integer(end), charsetName};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7290, new Class[]{cls, cls2, cls2, cls}, List.class);
        return proxy.isSupported ? (List) proxy.result : readFile2List(getFileByPath(filePath), st, end, charsetName);
    }

    public static List<String> readFile2List(File file, int st, int end) {
        Object[] objArr = {file, new Integer(st), new Integer(end)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7291, new Class[]{File.class, cls, cls}, List.class);
        return proxy.isSupported ? (List) proxy.result : readFile2List(file, st, end, (String) null);
    }

    public static List<String> readFile2List(File file, int st, int end, String charsetName) {
        BufferedReader reader;
        Object[] objArr = {file, new Integer(st), new Integer(end), charsetName};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7292, new Class[]{File.class, cls, cls, String.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        if (!isFileExists(file) || st > end) {
            return null;
        }
        BufferedReader reader2 = null;
        int curLine = 1;
        try {
            List<String> list = new ArrayList<>();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            while (true) {
                String readLine = reader.readLine();
                String line = readLine;
                if (readLine != null) {
                    if (curLine > end) {
                        break;
                    }
                    if (st <= curLine && curLine <= end) {
                        list.add(line);
                    }
                    curLine++;
                }
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        } catch (IOException e2) {
            e2.printStackTrace();
            if (reader2 != null) {
                try {
                    reader2.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            if (reader2 != null) {
                try {
                    reader2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static String readFile2String(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 7293, new Class[]{String.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : readFile2String(getFileByPath(filePath), (String) null);
    }

    public static String readFile2String(String filePath, String charsetName) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath, charsetName}, (Object) null, changeQuickRedirect, true, 7294, new Class[]{cls, cls}, String.class);
        return proxy.isSupported ? (String) proxy.result : readFile2String(getFileByPath(filePath), charsetName);
    }

    public static String readFile2String(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 7295, new Class[]{File.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : readFile2String(file, (String) null);
    }

    public static String readFile2String(File file, String charsetName) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file, charsetName}, (Object) null, changeQuickRedirect2, true, 7296, new Class[]{File.class, String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        byte[] bytes = readFile2BytesByStream(file);
        if (bytes == null) {
            return null;
        }
        if (isSpace(charsetName)) {
            return new String(bytes);
        }
        try {
            return new String(bytes, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] readFile2BytesByStream(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 7297, new Class[]{String.class}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : readFile2BytesByStream(getFileByPath(filePath), (OnProgressUpdateListener) null);
    }

    public static byte[] readFile2BytesByStream(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 7298, new Class[]{File.class}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : readFile2BytesByStream(file, (OnProgressUpdateListener) null);
    }

    public static byte[] readFile2BytesByStream(String filePath, OnProgressUpdateListener onProgressUpdateListener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath, onProgressUpdateListener}, (Object) null, changeQuickRedirect, true, 7299, new Class[]{String.class, OnProgressUpdateListener.class}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : readFile2BytesByStream(getFileByPath(filePath));
    }

    public static byte[] readFile2BytesByStream(File file, OnProgressUpdateListener listener) {
        InputStream is;
        int len;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file, listener}, (Object) null, changeQuickRedirect, true, 7300, new Class[]{File.class, OnProgressUpdateListener.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (!isFileExists(file)) {
            return null;
        }
        ByteArrayOutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file), sBufferSize);
            try {
                ByteArrayOutputStream os2 = new ByteArrayOutputStream();
                byte[] b = new byte[sBufferSize];
                if (listener == null) {
                    while (true) {
                        int read = is.read(b, 0, sBufferSize);
                        int len2 = read;
                        if (read == -1) {
                            break;
                        }
                        os2.write(b, 0, len2);
                    }
                } else {
                    double totalSize = (double) is.available();
                    int curSize = 0;
                    listener.onProgressUpdate(0.0d);
                    while (true) {
                        int read2 = is.read(b, 0, sBufferSize);
                        len = read2;
                        if (read2 == -1) {
                            break;
                        }
                        os2.write(b, 0, len);
                        curSize += len;
                        listener.onProgressUpdate(((double) curSize) / totalSize);
                    }
                    int i = len;
                }
                byte[] byteArray = os2.toByteArray();
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    os2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                return byteArray;
            } catch (IOException e3) {
                e3.printStackTrace();
                try {
                    is.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                return null;
            }
        } catch (FileNotFoundException e6) {
            e6.printStackTrace();
            return null;
        } catch (Throwable th) {
            try {
                is.close();
            } catch (IOException e7) {
                e7.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static byte[] readFile2BytesByChannel(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 7301, new Class[]{String.class}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : readFile2BytesByChannel(getFileByPath(filePath));
    }

    public static byte[] readFile2BytesByChannel(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 7302, new Class[]{File.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (!isFileExists(file)) {
            return null;
        }
        FileChannel fc = null;
        try {
            FileChannel fc2 = new RandomAccessFile(file, "r").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc2.size());
            do {
            } while (fc2.read(byteBuffer) > 0);
            byte[] array = byteBuffer.array();
            try {
                fc2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return array;
        } catch (IOException e2) {
            e2.printStackTrace();
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static byte[] readFile2BytesByMap(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 7303, new Class[]{String.class}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : readFile2BytesByMap(getFileByPath(filePath));
    }

    public static byte[] readFile2BytesByMap(File file) {
        FileChannel fc;
        IOException e;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 7304, new Class[]{File.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (!isFileExists(file)) {
            return null;
        }
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            try {
                int size = (int) fc.size();
                byte[] result = new byte[size];
                fc.map(FileChannel.MapMode.READ_ONLY, 0, (long) size).load().get(result, 0, size);
                try {
                    fc.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                return result;
            } catch (IOException e3) {
                e = e3;
            }
        } catch (IOException e4) {
            fc = null;
            e = e4;
            try {
                e.printStackTrace();
                if (fc != null) {
                    try {
                        fc.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                return null;
            } catch (Throwable th) {
                e = th;
            }
        } catch (Throwable th2) {
            fc = null;
            e = th2;
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            throw e;
        }
    }

    public static void setBufferSize(int bufferSize) {
        sBufferSize = bufferSize;
    }

    public static File getFileByPath(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 7305, new Class[]{String.class}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        if (isSpace(filePath)) {
            return null;
        }
        return new File(filePath);
    }

    private static boolean createOrExistsFile(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 7306, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : createOrExistsFile(getFileByPath(filePath));
    }

    private static boolean createOrExistsFile(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 7307, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 7308, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file != null) {
            if (file.exists()) {
                if (file.isDirectory()) {
                    return true;
                }
            } else if (file.mkdirs()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFileExists(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 7309, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return file != null && file.exists();
    }

    private static boolean isSpace(String s) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, (Object) null, changeQuickRedirect, true, 7310, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (s == null) {
            return true;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static byte[] is2Bytes(InputStream is) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{is}, (Object) null, changeQuickRedirect, true, 7311, new Class[]{InputStream.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (is == null) {
            return null;
        }
        ByteArrayOutputStream os = null;
        try {
            ByteArrayOutputStream os2 = new ByteArrayOutputStream();
            byte[] b = new byte[sBufferSize];
            while (true) {
                int read = is.read(b, 0, sBufferSize);
                int len = read;
                if (read == -1) {
                    break;
                }
                os2.write(b, 0, len);
            }
            byte[] byteArray = os2.toByteArray();
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os2.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return byteArray;
        } catch (IOException e3) {
            e3.printStackTrace();
            try {
                is.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            try {
                is.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
    }
}
