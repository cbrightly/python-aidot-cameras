package zendesk.ui.android.conversation.composer;

import androidx.annotation.ColorInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessageComposerState.kt */
public final class h {
    private final boolean a;
    private final boolean b;
    private final boolean c;
    private final boolean d;
    private final int e;
    private final int f;
    @Nullable
    private final Integer g;
    @NotNull
    private final String h;

    public h() {
        this(false, false, false, false, 0, 0, (Integer) null, (String) null, 255, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final h a(boolean z, boolean z2, boolean z3, boolean z4, int i, int i2, @Nullable @ColorInt Integer num, @NotNull String str) {
        k.e(str, "composerText");
        return new h(z, z2, z3, z4, i, i2, num, str);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof h)) {
            return false;
        }
        h hVar = (h) obj;
        return this.a == hVar.a && this.b == hVar.b && this.c == hVar.c && this.d == hVar.d && this.e == hVar.e && this.f == hVar.f && k.a(this.g, hVar.g) && k.a(this.h, hVar.h);
    }

    public int hashCode() {
        boolean z = this.a;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i = (z ? 1 : 0) * true;
        boolean z3 = this.b;
        if (z3) {
            z3 = true;
        }
        int i2 = (i + (z3 ? 1 : 0)) * 31;
        boolean z4 = this.c;
        if (z4) {
            z4 = true;
        }
        int i3 = (i2 + (z4 ? 1 : 0)) * 31;
        boolean z5 = this.d;
        if (!z5) {
            z2 = z5;
        }
        int i4 = (((((i3 + (z2 ? 1 : 0)) * 31) + this.e) * 31) + this.f) * 31;
        Integer num = this.g;
        return ((i4 + (num == null ? 0 : num.hashCode())) * 31) + this.h.hashCode();
    }

    @NotNull
    public String toString() {
        return "MessageComposerState(enabled=" + this.a + ", cameraSupported=" + this.b + ", gallerySupported=" + this.c + ", showAttachment=" + this.d + ", visibility=" + this.e + ", inputMaxLength=" + this.f + ", buttonColor=" + this.g + ", composerText=" + this.h + ')';
    }

    public h(boolean enabled, boolean cameraSupported, boolean gallerySupported, boolean showAttachment, int visibility, int inputMaxLength, @Nullable @ColorInt Integer buttonColor, @NotNull String composerText) {
        k.e(composerText, "composerText");
        this.a = enabled;
        this.b = cameraSupported;
        this.c = gallerySupported;
        this.d = showAttachment;
        this.e = visibility;
        this.f = inputMaxLength;
        this.g = buttonColor;
        this.h = composerText;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ h(boolean r9, boolean r10, boolean r11, boolean r12, int r13, int r14, java.lang.Integer r15, java.lang.String r16, int r17, kotlin.jvm.internal.DefaultConstructorMarker r18) {
        /*
            r8 = this;
            r0 = r17
            r1 = r0 & 1
            r2 = 1
            if (r1 == 0) goto L_0x0009
            r1 = r2
            goto L_0x000a
        L_0x0009:
            r1 = r9
        L_0x000a:
            r3 = r0 & 2
            if (r3 == 0) goto L_0x0010
            r3 = r2
            goto L_0x0011
        L_0x0010:
            r3 = r10
        L_0x0011:
            r4 = r0 & 4
            if (r4 == 0) goto L_0x0016
            goto L_0x0017
        L_0x0016:
            r2 = r11
        L_0x0017:
            r4 = r0 & 8
            r5 = 0
            if (r4 == 0) goto L_0x001e
            r4 = r5
            goto L_0x001f
        L_0x001e:
            r4 = r12
        L_0x001f:
            r6 = r0 & 16
            if (r6 == 0) goto L_0x0024
            goto L_0x0025
        L_0x0024:
            r5 = r13
        L_0x0025:
            r6 = r0 & 32
            if (r6 == 0) goto L_0x002d
            r6 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x002e
        L_0x002d:
            r6 = r14
        L_0x002e:
            r7 = r0 & 64
            if (r7 == 0) goto L_0x0034
            r7 = 0
            goto L_0x0035
        L_0x0034:
            r7 = r15
        L_0x0035:
            r0 = r0 & 128(0x80, float:1.794E-43)
            if (r0 == 0) goto L_0x003c
            java.lang.String r0 = ""
            goto L_0x003e
        L_0x003c:
            r0 = r16
        L_0x003e:
            r9 = r8
            r10 = r1
            r11 = r3
            r12 = r2
            r13 = r4
            r14 = r5
            r15 = r6
            r16 = r7
            r17 = r0
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.composer.h.<init>(boolean, boolean, boolean, boolean, int, int, java.lang.Integer, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final boolean e() {
        return this.a;
    }

    public final boolean c() {
        return this.b;
    }

    public final boolean f() {
        return this.c;
    }

    public final boolean h() {
        return this.d;
    }

    public final int i() {
        return this.e;
    }

    public final int g() {
        return this.f;
    }

    @Nullable
    public final Integer b() {
        return this.g;
    }

    @NotNull
    public final String d() {
        return this.h;
    }
}
