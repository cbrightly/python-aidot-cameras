package okhttp3.internal.ws;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.k;
import okio.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WebSocketProtocol.kt */
public final class f {
    public static final f a = new f();

    private f() {
    }

    public final void b(@NotNull c.a cursor, @NotNull byte[] key) {
        k.f(cursor, "cursor");
        k.f(key, CacheEntity.KEY);
        int keyIndex = 0;
        int keyLength = key.length;
        do {
            byte[] buffer = cursor.x;
            int i = cursor.y;
            int end = cursor.z;
            if (buffer != null) {
                while (i < end) {
                    int keyIndex2 = keyIndex % keyLength;
                    buffer[i] = (byte) (buffer[i] ^ key[keyIndex2]);
                    i++;
                    keyIndex = keyIndex2 + 1;
                }
            }
        } while (cursor.c() != -1);
    }

    @Nullable
    public final String a(int code) {
        if (code < 1000 || code >= 5000) {
            return "Code must be in range [1000,5000): " + code;
        } else if ((1004 > code || 1006 < code) && (1015 > code || 2999 < code)) {
            return null;
        } else {
            return "Code " + code + " is reserved and may not be used.";
        }
    }

    public final void c(int code) {
        String message = a(code);
        if (!(message == null)) {
            if (message == null) {
                k.n();
            }
            throw new IllegalArgumentException(message.toString());
        }
    }
}
