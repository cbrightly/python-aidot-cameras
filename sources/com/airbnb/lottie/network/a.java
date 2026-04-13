package com.airbnb.lottie.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.airbnb.lottie.utils.d;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: DefaultLottieFetchResult */
public class a implements d {
    @NonNull
    private final HttpURLConnection c;

    public a(@NonNull HttpURLConnection connection) {
        this.c = connection;
    }

    public boolean h0() {
        try {
            return this.c.getResponseCode() / 100 == 2;
        } catch (IOException e) {
            return false;
        }
    }

    @NonNull
    public InputStream Y() {
        return this.c.getInputStream();
    }

    @Nullable
    public String d() {
        return this.c.getContentType();
    }

    @Nullable
    public String p() {
        try {
            if (h0()) {
                return null;
            }
            return "Unable to fetch " + this.c.getURL() + ". Failed with " + this.c.getResponseCode() + "\n" + a(this.c);
        } catch (IOException e) {
            d.d("get error failed ", e);
            return e.getMessage();
        }
    }

    public void close() {
        this.c.disconnect();
    }

    private String a(HttpURLConnection connection) {
        BufferedReader r = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        StringBuilder error = new StringBuilder();
        while (true) {
            try {
                String readLine = r.readLine();
                String line = readLine;
                if (readLine != null) {
                    error.append(line);
                    error.append(10);
                } else {
                    try {
                        break;
                    } catch (Exception e) {
                    }
                }
            } finally {
                try {
                    r.close();
                } catch (Exception e2) {
                }
            }
        }
        return error.toString();
    }
}
