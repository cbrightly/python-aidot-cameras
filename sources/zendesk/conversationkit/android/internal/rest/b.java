package zendesk.conversationkit.android.internal.rest;

import android.content.Context;
import android.util.Base64;
import java.io.File;
import java.nio.charset.Charset;
import kotlin.io.j;
import kotlin.jvm.internal.k;
import kotlin.text.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: RestClientFiles.kt */
public final class b implements e {
    @NotNull
    private final Context a;

    public b(@NotNull Context context) {
        k.e(context, "context");
        this.a = context;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0043 A[Catch:{ Exception -> 0x0064 }] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0057 A[Catch:{ Exception -> 0x0064 }] */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.io.File b(@org.jetbrains.annotations.NotNull java.lang.String r6, @org.jetbrains.annotations.NotNull java.lang.String r7) {
        /*
            r5 = this;
            java.lang.String r0 = "uri"
            kotlin.jvm.internal.k.e(r6, r0)
            java.lang.String r0 = "name"
            kotlin.jvm.internal.k.e(r7, r0)
            java.io.File r0 = r5.d(r7)     // Catch:{ Exception -> 0x0064 }
            boolean r1 = r0.exists()     // Catch:{ Exception -> 0x0064 }
            if (r1 != 0) goto L_0x0063
            java.io.File r1 = r0.getParentFile()     // Catch:{ Exception -> 0x0064 }
            if (r1 != 0) goto L_0x001c
        L_0x001b:
            goto L_0x0020
        L_0x001c:
            r1.mkdirs()     // Catch:{ Exception -> 0x0064 }
            goto L_0x001b
        L_0x0020:
            r0.createNewFile()     // Catch:{ Exception -> 0x0064 }
            android.content.Context r1 = r5.a     // Catch:{ Exception -> 0x0064 }
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch:{ Exception -> 0x0064 }
            android.net.Uri r2 = android.net.Uri.parse(r6)     // Catch:{ Exception -> 0x0064 }
            java.io.InputStream r1 = r1.openInputStream(r2)     // Catch:{ Exception -> 0x0064 }
            r2 = 0
            if (r1 != 0) goto L_0x0036
        L_0x0034:
            r1 = r2
            goto L_0x0041
        L_0x0036:
            okio.e0 r1 = okio.p.l(r1)     // Catch:{ Exception -> 0x0064 }
            if (r1 != 0) goto L_0x003d
            goto L_0x0034
        L_0x003d:
            okio.e r1 = okio.p.d(r1)     // Catch:{ Exception -> 0x0064 }
        L_0x0041:
            if (r1 == 0) goto L_0x0057
            r3 = 0
            r4 = 1
            okio.b0 r2 = okio.q.g(r0, r3, r4, r2)     // Catch:{ Exception -> 0x0064 }
            okio.d r2 = okio.p.c(r2)     // Catch:{ Exception -> 0x0064 }
            r2.writeAll(r1)     // Catch:{ Exception -> 0x0064 }
            r1.close()     // Catch:{ Exception -> 0x0064 }
            r2.close()     // Catch:{ Exception -> 0x0064 }
            goto L_0x0063
        L_0x0057:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ Exception -> 0x0064 }
            java.lang.String r2 = "Content resolver failed to find source for "
            java.lang.String r2 = kotlin.jvm.internal.k.l(r2, r6)     // Catch:{ Exception -> 0x0064 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0064 }
            throw r1     // Catch:{ Exception -> 0x0064 }
        L_0x0063:
            return r0
        L_0x0064:
            r0 = move-exception
            r5.a(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.rest.b.b(java.lang.String, java.lang.String):java.io.File");
    }

    public void a(@NotNull String name) {
        k.e(name, "name");
        d(name).delete();
    }

    public void clearCache() {
        j.c(c());
    }

    private final File d(String name) {
        Charset charset = c.a;
        if (name != null) {
            byte[] bytes = name.getBytes(charset);
            k.d(bytes, "(this as java.lang.String).getBytes(charset)");
            return new File(c(), Base64.encodeToString(bytes, 8));
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
    }

    private final File c() {
        return new File(this.a.getCacheDir().getPath() + File.pathSeparator + "upload_cache");
    }
}
