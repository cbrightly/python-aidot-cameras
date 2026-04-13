package zendesk.ui.android.conversation.header;

import android.net.Uri;
import androidx.annotation.ColorInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConversationHeaderState.kt */
public final class c {
    @NotNull
    private final String a;
    @Nullable
    private final String b;
    @Nullable
    private final Uri c;
    @Nullable
    private final Integer d;
    @Nullable
    private final Integer e;

    public c() {
        this((String) null, (String) null, (Uri) null, (Integer) null, (Integer) null, 31, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ c b(c cVar, String str, String str2, Uri uri, Integer num, Integer num2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = cVar.a;
        }
        if ((i & 2) != 0) {
            str2 = cVar.b;
        }
        String str3 = str2;
        if ((i & 4) != 0) {
            uri = cVar.c;
        }
        Uri uri2 = uri;
        if ((i & 8) != 0) {
            num = cVar.d;
        }
        Integer num3 = num;
        if ((i & 16) != 0) {
            num2 = cVar.e;
        }
        return cVar.a(str, str3, uri2, num3, num2);
    }

    @NotNull
    public final c a(@NotNull String str, @Nullable String str2, @Nullable Uri uri, @Nullable @ColorInt Integer num, @Nullable @ColorInt Integer num2) {
        k.e(str, "title");
        return new c(str, str2, uri, num, num2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return k.a(this.a, cVar.a) && k.a(this.b, cVar.b) && k.a(this.c, cVar.c) && k.a(this.d, cVar.d) && k.a(this.e, cVar.e);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        String str = this.b;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        Uri uri = this.c;
        int hashCode3 = (hashCode2 + (uri == null ? 0 : uri.hashCode())) * 31;
        Integer num = this.d;
        int hashCode4 = (hashCode3 + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.e;
        if (num2 != null) {
            i = num2.hashCode();
        }
        return hashCode4 + i;
    }

    @NotNull
    public String toString() {
        return "ConversationHeaderState(title=" + this.a + ", description=" + this.b + ", logo=" + this.c + ", backgroundColor=" + this.d + ", statusBarColor=" + this.e + ')';
    }

    public c(@NotNull String title, @Nullable String description, @Nullable Uri logo, @Nullable @ColorInt Integer backgroundColor, @Nullable @ColorInt Integer statusBarColor) {
        k.e(title, "title");
        this.a = title;
        this.b = description;
        this.c = logo;
        this.d = backgroundColor;
        this.e = statusBarColor;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ c(java.lang.String r4, java.lang.String r5, android.net.Uri r6, java.lang.Integer r7, java.lang.Integer r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r3 = this;
            r10 = r9 & 1
            if (r10 == 0) goto L_0x0006
            java.lang.String r4 = ""
        L_0x0006:
            r10 = r9 & 2
            r0 = 0
            if (r10 == 0) goto L_0x000d
            r10 = r0
            goto L_0x000e
        L_0x000d:
            r10 = r5
        L_0x000e:
            r5 = r9 & 4
            if (r5 == 0) goto L_0x0014
            r1 = r0
            goto L_0x0015
        L_0x0014:
            r1 = r6
        L_0x0015:
            r5 = r9 & 8
            if (r5 == 0) goto L_0x001b
            r2 = r0
            goto L_0x001c
        L_0x001b:
            r2 = r7
        L_0x001c:
            r5 = r9 & 16
            if (r5 == 0) goto L_0x0021
            goto L_0x0022
        L_0x0021:
            r0 = r8
        L_0x0022:
            r5 = r3
            r6 = r4
            r7 = r10
            r8 = r1
            r9 = r2
            r10 = r0
            r5.<init>(r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.header.c.<init>(java.lang.String, java.lang.String, android.net.Uri, java.lang.Integer, java.lang.Integer, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final String g() {
        return this.a;
    }

    @Nullable
    public final String d() {
        return this.b;
    }

    @Nullable
    public final Uri e() {
        return this.c;
    }

    @Nullable
    public final Integer c() {
        return this.d;
    }

    @Nullable
    public final Integer f() {
        return this.e;
    }
}
