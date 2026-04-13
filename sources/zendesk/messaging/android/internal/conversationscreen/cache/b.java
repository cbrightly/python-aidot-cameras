package zendesk.messaging.android.internal.conversationscreen.cache;

import com.squareup.moshi.r;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: MessagingStorage.kt */
public final class b implements zendesk.storage.android.b {
    @NotNull
    private final r a;

    public b(@NotNull r moshi) {
        k.e(moshi, "moshi");
        this.a = moshi;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ b(com.squareup.moshi.r r1, int r2, kotlin.jvm.internal.DefaultConstructorMarker r3) {
        /*
            r0 = this;
            r2 = r2 & 1
            if (r2 == 0) goto L_0x0013
            com.squareup.moshi.r$b r1 = new com.squareup.moshi.r$b
            r1.<init>()
            com.squareup.moshi.r r1 = r1.c()
            java.lang.String r2 = "Builder()\n        .build()"
            kotlin.jvm.internal.k.d(r1, r2)
        L_0x0013:
            r0.<init>(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.cache.b.<init>(com.squareup.moshi.r, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Nullable
    public <T> T a(@NotNull String source, @NotNull Class<T> type) {
        k.e(source, "source");
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        try {
            return this.a.c(type).c(source);
        } catch (Exception e) {
            return null;
        }
    }

    @NotNull
    public <T> String b(T data, @NotNull Class<T> type) {
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        String h = this.a.c(type).h(data);
        k.d(h, "moshi.adapter(type).toJson(data)");
        return h;
    }
}
