package com.downloader.utils;

import com.downloader.database.d;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/* compiled from: Utils */
public final class a {
    public static String c(String dirPath, String fileName) {
        return dirPath + File.separator + fileName;
    }

    public static String e(String dirPath, String fileName) {
        return c(dirPath, fileName) + ".temp";
    }

    public static void h(String oldPath, String newPath) {
        File newFile = new File(oldPath);
        try {
            newFile = new File(newPath);
            if (r2) {
                if (!newFile.delete()) {
                    throw new IOException("Deletion Failed");
                }
            }
            if (!newFile.renameTo(newFile)) {
                throw new IOException("Rename Failed");
            } else if (newFile.exists()) {
                newFile.delete();
            }
        } finally {
            if (newFile.exists()) {
                newFile.delete();
            }
        }
    }

    /* renamed from: com.downloader.utils.a$a  reason: collision with other inner class name */
    /* compiled from: Utils */
    public class C0066a implements Runnable {
        final /* synthetic */ int c;
        final /* synthetic */ String d;

        C0066a(int i, String str) {
            this.c = i;
            this.d = str;
        }

        public void run() {
            com.downloader.internal.a.d().b().remove(this.c);
            File file = new File(this.d);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void a(String path, int downloadId) {
        com.downloader.core.a.b().a().c().execute(new C0066a(downloadId, path));
    }

    /* compiled from: Utils */
    public class b implements Runnable {
        final /* synthetic */ int c;

        b(int i) {
            this.c = i;
        }

        public void run() {
            List<d> c2 = com.downloader.internal.a.d().b().c(this.c);
            if (c2 != null) {
                for (d model : c2) {
                    String tempPath = a.e(model.a(), model.d());
                    com.downloader.internal.a.d().b().remove(model.e());
                    File file = new File(tempPath);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }
    }

    public static void b(int days) {
        com.downloader.core.a.b().a().c().execute(new b(days));
    }

    public static int f(String url, String dirPath, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        String str = File.separator;
        sb.append(str);
        sb.append(dirPath);
        sb.append(str);
        sb.append(fileName);
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(sb.toString().getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b2 : hash) {
                if ((b2 & 255) < 16) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b2 & 255));
            }
            return hex.toString().hashCode();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("UnsupportedEncodingException", e2);
        }
    }

    public static com.downloader.httpclient.b d(com.downloader.httpclient.b httpClient, com.downloader.request.a request) {
        int redirectTimes = 0;
        int code = httpClient.F0();
        String location = httpClient.E("Location");
        while (g(code)) {
            if (location != null) {
                httpClient.close();
                request.K(location);
                httpClient = com.downloader.internal.a.d().c();
                httpClient.P(request);
                code = httpClient.F0();
                location = httpClient.E("Location");
                redirectTimes++;
                if (redirectTimes >= 10) {
                    throw new IllegalAccessException("Max redirection done");
                }
            } else {
                throw new IllegalAccessException("Location is null");
            }
        }
        return httpClient;
    }

    private static boolean g(int code) {
        return code == 301 || code == 302 || code == 303 || code == 300 || code == 307 || code == 308;
    }
}
