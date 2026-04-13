package com.bumptech.glide.load.model;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.f;
import com.bumptech.glide.util.i;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Map;

/* compiled from: GlideUrl */
public class g implements f {
    private final h b;
    @Nullable
    private final URL c;
    @Nullable
    private final String d;
    @Nullable
    private String e;
    @Nullable
    private URL f;
    @Nullable
    private volatile byte[] g;
    private int h;

    public g(URL url) {
        this(url, h.b);
    }

    public g(String url) {
        this(url, h.b);
    }

    public g(URL url, h headers) {
        this.c = (URL) i.d(url);
        this.d = null;
        this.b = (h) i.d(headers);
    }

    public g(String url, h headers) {
        this.c = null;
        this.d = i.b(url);
        this.b = (h) i.d(headers);
    }

    public URL f() {
        return e();
    }

    private URL e() {
        if (this.f == null) {
            this.f = new URL(d());
        }
        return this.f;
    }

    private String d() {
        if (TextUtils.isEmpty(this.e)) {
            String unsafeStringUrl = this.d;
            if (TextUtils.isEmpty(unsafeStringUrl)) {
                unsafeStringUrl = ((URL) i.d(this.c)).toString();
            }
            this.e = Uri.encode(unsafeStringUrl, "@#&=*+-_.,:!?()/~'%;$");
        }
        return this.e;
    }

    public Map<String, String> c() {
        return this.b.getHeaders();
    }

    public String a() {
        String str = this.d;
        return str != null ? str : ((URL) i.d(this.c)).toString();
    }

    public String toString() {
        return a();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(b());
    }

    private byte[] b() {
        if (this.g == null) {
            this.g = a().getBytes(f.a);
        }
        return this.g;
    }

    public boolean equals(Object o) {
        if (!(o instanceof g)) {
            return false;
        }
        g other = (g) o;
        if (!a().equals(other.a()) || !this.b.equals(other.b)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.h == 0) {
            int hashCode = a().hashCode();
            this.h = hashCode;
            this.h = (hashCode * 31) + this.b.hashCode();
        }
        return this.h;
    }
}
