package com.yanzhenjie.andserver.framework.body;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.http.i;
import com.yanzhenjie.andserver.util.e;
import com.yanzhenjie.andserver.util.h;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.apache.commons.io.a;

/* compiled from: StringBody */
public class b implements i {
    private byte[] a;
    private h b;

    public b(String body) {
        this(body, h.TEXT_PLAIN);
    }

    public b(String body, h mediaType) {
        if (body != null) {
            this.b = mediaType;
            if (mediaType == null) {
                this.b = new h(h.TEXT_PLAIN, a.a("utf-8"));
            }
            Charset charset = this.b.getCharset();
            this.a = body.getBytes(charset == null ? a.a("utf-8") : charset);
            return;
        }
        throw new IllegalArgumentException("The content cannot be null.");
    }

    public long a() {
        return (long) this.a.length;
    }

    @Nullable
    public h d() {
        if (this.b.getCharset() != null) {
            return this.b;
        }
        return new h(this.b.getType(), this.b.getSubtype(), a.a("utf-8"));
    }

    public void writeTo(@NonNull OutputStream output) {
        e.j(output, this.a);
    }
}
