package zendesk.ui.android.conversation.imagerviewer;

import android.net.Uri;
import androidx.annotation.ColorInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageViewerState.kt */
public final class b {
    @Nullable
    private final String a;
    @Nullable
    private final String b;
    @Nullable
    private final String c;
    @Nullable
    private final Uri d;
    @Nullable
    private final Integer e;
    @Nullable
    private final Integer f;

    public b() {
        this((String) null, (String) null, (String) null, (Uri) null, (Integer) null, (Integer) null, 63, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ b b(b bVar, String str, String str2, String str3, Uri uri, Integer num, Integer num2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = bVar.a;
        }
        if ((i & 2) != 0) {
            str2 = bVar.b;
        }
        String str4 = str2;
        if ((i & 4) != 0) {
            str3 = bVar.c;
        }
        String str5 = str3;
        if ((i & 8) != 0) {
            uri = bVar.d;
        }
        Uri uri2 = uri;
        if ((i & 16) != 0) {
            num = bVar.e;
        }
        Integer num3 = num;
        if ((i & 32) != 0) {
            num2 = bVar.f;
        }
        return bVar.a(str, str4, str5, uri2, num3, num2);
    }

    @NotNull
    public final b a(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable Uri uri, @Nullable @ColorInt Integer num, @Nullable @ColorInt Integer num2) {
        return new b(str, str2, str3, uri, num, num2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return k.a(this.a, bVar.a) && k.a(this.b, bVar.b) && k.a(this.c, bVar.c) && k.a(this.d, bVar.d) && k.a(this.e, bVar.e) && k.a(this.f, bVar.f);
    }

    public int hashCode() {
        String str = this.a;
        int i = 0;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.b;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.c;
        int hashCode3 = (hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Uri uri = this.d;
        int hashCode4 = (hashCode3 + (uri == null ? 0 : uri.hashCode())) * 31;
        Integer num = this.e;
        int hashCode5 = (hashCode4 + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.f;
        if (num2 != null) {
            i = num2.hashCode();
        }
        return hashCode5 + i;
    }

    @NotNull
    public String toString() {
        return "ImageViewerState(uri=" + this.a + ", title=" + this.b + ", description=" + this.c + ", logo=" + this.d + ", toolbarColor=" + this.e + ", statusBarColor=" + this.f + ')';
    }

    public b(@Nullable String uri, @Nullable String title, @Nullable String description, @Nullable Uri logo, @Nullable @ColorInt Integer toolbarColor, @Nullable @ColorInt Integer statusBarColor) {
        this.a = uri;
        this.b = title;
        this.c = description;
        this.d = logo;
        this.e = toolbarColor;
        this.f = statusBarColor;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ b(java.lang.String r6, java.lang.String r7, java.lang.String r8, android.net.Uri r9, java.lang.Integer r10, java.lang.Integer r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r5 = this;
            r13 = r12 & 1
            r0 = 0
            if (r13 == 0) goto L_0x0007
            r13 = r0
            goto L_0x0008
        L_0x0007:
            r13 = r6
        L_0x0008:
            r6 = r12 & 2
            if (r6 == 0) goto L_0x0010
            java.lang.String r7 = ""
            r1 = r7
            goto L_0x0011
        L_0x0010:
            r1 = r7
        L_0x0011:
            r6 = r12 & 4
            if (r6 == 0) goto L_0x0017
            r2 = r0
            goto L_0x0018
        L_0x0017:
            r2 = r8
        L_0x0018:
            r6 = r12 & 8
            if (r6 == 0) goto L_0x001e
            r3 = r0
            goto L_0x001f
        L_0x001e:
            r3 = r9
        L_0x001f:
            r6 = r12 & 16
            if (r6 == 0) goto L_0x0025
            r4 = r0
            goto L_0x0026
        L_0x0025:
            r4 = r10
        L_0x0026:
            r6 = r12 & 32
            if (r6 == 0) goto L_0x002c
            r12 = r0
            goto L_0x002d
        L_0x002c:
            r12 = r11
        L_0x002d:
            r6 = r5
            r7 = r13
            r8 = r1
            r9 = r2
            r10 = r3
            r11 = r4
            r6.<init>(r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.imagerviewer.b.<init>(java.lang.String, java.lang.String, java.lang.String, android.net.Uri, java.lang.Integer, java.lang.Integer, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Nullable
    public final String e() {
        return this.a;
    }

    @Nullable
    public final Integer d() {
        return this.e;
    }

    @Nullable
    public final Integer c() {
        return this.f;
    }
}
