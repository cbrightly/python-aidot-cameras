package zendesk.ui.android.conversation.imagecell;

import android.net.Uri;
import androidx.annotation.ColorInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageCellState.kt */
public final class c {
    @Nullable
    private final Uri a;
    @Nullable
    private final Uri b;
    @Nullable
    private final String c;
    @Nullable
    private final String d;
    private final boolean e;
    private final boolean f;
    @Nullable
    private final Integer g;
    @Nullable
    private final Integer h;
    @Nullable
    private final String i;
    @NotNull
    private final a j;

    public c() {
        this((Uri) null, (Uri) null, (String) null, (String) null, false, false, (Integer) null, (Integer) null, (String) null, (a) null, 1023, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ c b(c cVar, Uri uri, Uri uri2, String str, String str2, boolean z, boolean z2, Integer num, Integer num2, String str3, a aVar, int i2, Object obj) {
        c cVar2 = cVar;
        int i3 = i2;
        return cVar.a((i3 & 1) != 0 ? cVar2.a : uri, (i3 & 2) != 0 ? cVar2.b : uri2, (i3 & 4) != 0 ? cVar2.c : str, (i3 & 8) != 0 ? cVar2.d : str2, (i3 & 16) != 0 ? cVar2.e : z, (i3 & 32) != 0 ? cVar2.f : z2, (i3 & 64) != 0 ? cVar2.g : num, (i3 & 128) != 0 ? cVar2.h : num2, (i3 & 256) != 0 ? cVar2.i : str3, (i3 & 512) != 0 ? cVar2.j : aVar);
    }

    @NotNull
    public final c a(@Nullable Uri uri, @Nullable Uri uri2, @Nullable String str, @Nullable String str2, boolean z, boolean z2, @Nullable @ColorInt Integer num, @Nullable @ColorInt Integer num2, @Nullable String str3, @NotNull a aVar) {
        k.e(aVar, "imageCellDirection");
        return new c(uri, uri2, str, str2, z, z2, num, num2, str3, aVar);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return k.a(this.a, cVar.a) && k.a(this.b, cVar.b) && k.a(this.c, cVar.c) && k.a(this.d, cVar.d) && this.e == cVar.e && this.f == cVar.f && k.a(this.g, cVar.g) && k.a(this.h, cVar.h) && k.a(this.i, cVar.i) && this.j == cVar.j;
    }

    public int hashCode() {
        Uri uri = this.a;
        int i2 = 0;
        int hashCode = (uri == null ? 0 : uri.hashCode()) * 31;
        Uri uri2 = this.b;
        int hashCode2 = (hashCode + (uri2 == null ? 0 : uri2.hashCode())) * 31;
        String str = this.c;
        int hashCode3 = (hashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.d;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        boolean z = this.e;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i3 = (hashCode4 + (z ? 1 : 0)) * 31;
        boolean z3 = this.f;
        if (!z3) {
            z2 = z3;
        }
        int i4 = (i3 + (z2 ? 1 : 0)) * 31;
        Integer num = this.g;
        int hashCode5 = (i4 + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.h;
        int hashCode6 = (hashCode5 + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str3 = this.i;
        if (str3 != null) {
            i2 = str3.hashCode();
        }
        return ((hashCode6 + i2) * 31) + this.j.hashCode();
    }

    @NotNull
    public String toString() {
        return "ImageCellState(uri=" + this.a + ", localUri=" + this.b + ", imageType=" + this.c + ", messageText=" + this.d + ", isError=" + this.e + ", isPending=" + this.f + ", textColor=" + this.g + ", backgroundColor=" + this.h + ", errorText=" + this.i + ", imageCellDirection=" + this.j + ')';
    }

    public c(@Nullable Uri uri, @Nullable Uri localUri, @Nullable String imageType, @Nullable String messageText, boolean isError, boolean isPending, @Nullable @ColorInt Integer textColor, @Nullable @ColorInt Integer backgroundColor, @Nullable String errorText, @NotNull a imageCellDirection) {
        k.e(imageCellDirection, "imageCellDirection");
        this.a = uri;
        this.b = localUri;
        this.c = imageType;
        this.d = messageText;
        this.e = isError;
        this.f = isPending;
        this.g = textColor;
        this.h = backgroundColor;
        this.i = errorText;
        this.j = imageCellDirection;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ c(android.net.Uri r12, android.net.Uri r13, java.lang.String r14, java.lang.String r15, boolean r16, boolean r17, java.lang.Integer r18, java.lang.Integer r19, java.lang.String r20, zendesk.ui.android.conversation.imagecell.a r21, int r22, kotlin.jvm.internal.DefaultConstructorMarker r23) {
        /*
            r11 = this;
            r0 = r22
            r1 = r0 & 1
            r2 = 0
            if (r1 == 0) goto L_0x0009
            r1 = r2
            goto L_0x000a
        L_0x0009:
            r1 = r12
        L_0x000a:
            r3 = r0 & 2
            if (r3 == 0) goto L_0x0010
            r3 = r2
            goto L_0x0011
        L_0x0010:
            r3 = r13
        L_0x0011:
            r4 = r0 & 4
            if (r4 == 0) goto L_0x0017
            r4 = r2
            goto L_0x0018
        L_0x0017:
            r4 = r14
        L_0x0018:
            r5 = r0 & 8
            java.lang.String r6 = ""
            if (r5 == 0) goto L_0x0020
            r5 = r6
            goto L_0x0021
        L_0x0020:
            r5 = r15
        L_0x0021:
            r7 = r0 & 16
            r8 = 0
            if (r7 == 0) goto L_0x0028
            r7 = r8
            goto L_0x002a
        L_0x0028:
            r7 = r16
        L_0x002a:
            r9 = r0 & 32
            if (r9 == 0) goto L_0x002f
            goto L_0x0031
        L_0x002f:
            r8 = r17
        L_0x0031:
            r9 = r0 & 64
            if (r9 == 0) goto L_0x0037
            r9 = r2
            goto L_0x0039
        L_0x0037:
            r9 = r18
        L_0x0039:
            r10 = r0 & 128(0x80, float:1.794E-43)
            if (r10 == 0) goto L_0x003e
            goto L_0x0040
        L_0x003e:
            r2 = r19
        L_0x0040:
            r10 = r0 & 256(0x100, float:3.59E-43)
            if (r10 == 0) goto L_0x0045
            goto L_0x0047
        L_0x0045:
            r6 = r20
        L_0x0047:
            r0 = r0 & 512(0x200, float:7.175E-43)
            if (r0 == 0) goto L_0x004e
            zendesk.ui.android.conversation.imagecell.a r0 = zendesk.ui.android.conversation.imagecell.a.INBOUND_SINGLE
            goto L_0x0050
        L_0x004e:
            r0 = r21
        L_0x0050:
            r12 = r11
            r13 = r1
            r14 = r3
            r15 = r4
            r16 = r5
            r17 = r7
            r18 = r8
            r19 = r9
            r20 = r2
            r21 = r6
            r22 = r0
            r12.<init>(r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.imagecell.c.<init>(android.net.Uri, android.net.Uri, java.lang.String, java.lang.String, boolean, boolean, java.lang.Integer, java.lang.Integer, java.lang.String, zendesk.ui.android.conversation.imagecell.a, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Nullable
    public final Uri j() {
        return this.a;
    }

    @Nullable
    public final Uri g() {
        return this.b;
    }

    @Nullable
    public final String f() {
        return this.c;
    }

    @Nullable
    public final String h() {
        return this.d;
    }

    public final boolean k() {
        return this.e;
    }

    public final boolean l() {
        return this.f;
    }

    @Nullable
    public final Integer i() {
        return this.g;
    }

    @Nullable
    public final Integer c() {
        return this.h;
    }

    @Nullable
    public final String d() {
        return this.i;
    }

    @NotNull
    public final a e() {
        return this.j;
    }
}
