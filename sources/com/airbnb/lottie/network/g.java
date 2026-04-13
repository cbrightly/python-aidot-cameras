package com.airbnb.lottie.network;

import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import com.airbnb.lottie.utils.d;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: NetworkCache */
public class g {
    @NonNull
    private final e a;

    public g(@NonNull e cacheProvider) {
        this.a = cacheProvider;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @Nullable
    public Pair<c, InputStream> a(String url) {
        c extension;
        try {
            File cachedFile = c(url);
            if (cachedFile == null) {
                return null;
            }
            try {
                FileInputStream inputStream = new FileInputStream(cachedFile);
                if (cachedFile.getAbsolutePath().endsWith(".zip")) {
                    extension = c.ZIP;
                } else {
                    extension = c.JSON;
                }
                d.a("Cache hit for " + url + " at " + cachedFile.getAbsolutePath());
                return new Pair<>(extension, inputStream);
            } catch (FileNotFoundException e) {
                return null;
            }
        } catch (FileNotFoundException e2) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public File g(String url, InputStream stream, c extension) {
        OutputStream output;
        File file = new File(e(), b(url, extension, true));
        try {
            output = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            while (true) {
                int read = stream.read(buffer);
                int read2 = read;
                if (read != -1) {
                    output.write(buffer, 0, read2);
                } else {
                    output.flush();
                    output.close();
                    stream.close();
                    return file;
                }
            }
        } catch (Throwable th) {
            stream.close();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public void f(String url, c extension) {
        File file = new File(e(), b(url, extension, true));
        File newFile = new File(file.getAbsolutePath().replace(".temp", ""));
        boolean renamed = file.renameTo(newFile);
        d.a("Copying temp file to real file (" + newFile + ")");
        if (!renamed) {
            d.c("Unable to rename cache file " + file.getAbsolutePath() + " to " + newFile.getAbsolutePath() + ".");
        }
    }

    @Nullable
    private File c(String url) {
        File jsonFile = new File(e(), b(url, c.JSON, false));
        if (jsonFile.exists()) {
            return jsonFile;
        }
        File zipFile = new File(e(), b(url, c.ZIP, false));
        if (zipFile.exists()) {
            return zipFile;
        }
        return null;
    }

    private File e() {
        File file = this.a.a();
        if (file.isFile()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static String b(String url, c extension, boolean isTemp) {
        String suffix = isTemp ? extension.tempExtension() : extension.extension;
        String sanitizedUrl = url.replaceAll("\\W+", "");
        int maxUrlLength = (255 - "lottie_cache_".length()) - suffix.length();
        if (sanitizedUrl.length() > maxUrlLength) {
            sanitizedUrl = d(sanitizedUrl, maxUrlLength);
        }
        return "lottie_cache_" + sanitizedUrl + suffix;
    }

    private static String d(String input, int maxLength) {
        try {
            byte[] messageDigest = MessageDigest.getInstance("MD5").digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                sb.append(String.format("%02x", new Object[]{Byte.valueOf(messageDigest[i])}));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return input.substring(0, maxLength);
        }
    }
}
