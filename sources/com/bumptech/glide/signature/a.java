package com.bumptech.glide.signature;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.f;
import com.bumptech.glide.util.j;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: AndroidResourceSignature */
public final class a implements f {
    private final int b;
    private final f c;

    @NonNull
    public static f a(@NonNull Context context) {
        return new a(context.getResources().getConfiguration().uiMode & 48, b.c(context));
    }

    private a(int nightMode, f applicationVersion) {
        this.b = nightMode;
        this.c = applicationVersion;
    }

    public boolean equals(Object o) {
        if (!(o instanceof a)) {
            return false;
        }
        a that = (a) o;
        if (this.b != that.b || !this.c.equals(that.c)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return j.o(this.c, this.b);
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.c.updateDiskCacheKey(messageDigest);
        messageDigest.update(ByteBuffer.allocate(4).putInt(this.b).array());
    }
}
