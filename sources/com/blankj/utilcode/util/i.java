package com.blankj.utilcode.util;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/* compiled from: FileIOUtils */
public final class i {
    private static int a = 524288;

    /* compiled from: FileIOUtils */
    public interface a {
        void onProgressUpdate(double d);
    }

    public static boolean f(String filePath, String content) {
        return e(h0.o(filePath), content, false);
    }

    public static boolean g(String filePath, String content, boolean append) {
        return e(h0.o(filePath), content, append);
    }

    public static boolean e(File file, String content, boolean append) {
        if (file == null || content == null) {
            return false;
        }
        if (!h0.e(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
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

    public static String d(String filePath) {
        return c(h0.o(filePath), (String) null);
    }

    public static String c(File file, String charsetName) {
        byte[] bytes = a(file);
        if (bytes == null) {
            return null;
        }
        if (h0.E(charsetName)) {
            return new String(bytes);
        }
        try {
            return new String(bytes, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] a(File file) {
        return b(file, (a) null);
    }

    public static byte[] b(File file, a listener) {
        InputStream is;
        int len;
        if (!h0.A(file)) {
            return null;
        }
        ByteArrayOutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file), a);
            try {
                ByteArrayOutputStream os2 = new ByteArrayOutputStream();
                byte[] b = new byte[a];
                if (listener == null) {
                    while (true) {
                        int read = is.read(b, 0, a);
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
                        int read2 = is.read(b, 0, a);
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
}
