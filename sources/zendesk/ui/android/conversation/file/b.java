package zendesk.ui.android.conversation.file;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.chip.chiptool.setuppayloadscanner.a;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FileState.kt */
public final class b {
    @NotNull
    private final String a;
    private final long b;
    @Nullable
    private final Integer c;
    @Nullable
    private final Integer d;
    @Nullable
    private final Integer e;
    @Nullable
    private final Integer f;

    public b() {
        this((String) null, 0, (Integer) null, (Integer) null, (Integer) null, (Integer) null, 63, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final b a(@NotNull String str, long j, @Nullable @ColorInt Integer num, @Nullable @ColorInt Integer num2, @Nullable @ColorInt Integer num3, @Nullable @DrawableRes Integer num4) {
        String str2 = str;
        k.e(str, Progress.FILE_NAME);
        return new b(str, j, num, num2, num3, num4);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return k.a(this.a, bVar.a) && this.b == bVar.b && k.a(this.c, bVar.c) && k.a(this.d, bVar.d) && k.a(this.e, bVar.e) && k.a(this.f, bVar.f);
    }

    public int hashCode() {
        int hashCode = ((this.a.hashCode() * 31) + a.a(this.b)) * 31;
        Integer num = this.c;
        int i = 0;
        int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.d;
        int hashCode3 = (hashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.e;
        int hashCode4 = (hashCode3 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Integer num4 = this.f;
        if (num4 != null) {
            i = num4.hashCode();
        }
        return hashCode4 + i;
    }

    @NotNull
    public String toString() {
        return "FileState(fileName=" + this.a + ", fileSize=" + this.b + ", textColor=" + this.c + ", iconColor=" + this.d + ", backgroundColor=" + this.e + ", backgroundDrawable=" + this.f + ')';
    }

    public b(@NotNull String fileName, long fileSize, @Nullable @ColorInt Integer textColor, @Nullable @ColorInt Integer iconColor, @Nullable @ColorInt Integer backgroundColor, @Nullable @DrawableRes Integer backgroundDrawable) {
        k.e(fileName, Progress.FILE_NAME);
        this.a = fileName;
        this.b = fileSize;
        this.c = textColor;
        this.d = iconColor;
        this.e = backgroundColor;
        this.f = backgroundDrawable;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ b(java.lang.String r6, long r7, java.lang.Integer r9, java.lang.Integer r10, java.lang.Integer r11, java.lang.Integer r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r5 = this;
            r14 = r13 & 1
            if (r14 == 0) goto L_0x0006
            java.lang.String r6 = ""
        L_0x0006:
            r14 = r13 & 2
            if (r14 == 0) goto L_0x000e
            r7 = 0
            r0 = r7
            goto L_0x000f
        L_0x000e:
            r0 = r7
        L_0x000f:
            r7 = r13 & 4
            r8 = 0
            if (r7 == 0) goto L_0x0016
            r14 = r8
            goto L_0x0017
        L_0x0016:
            r14 = r9
        L_0x0017:
            r7 = r13 & 8
            if (r7 == 0) goto L_0x001d
            r2 = r8
            goto L_0x001e
        L_0x001d:
            r2 = r10
        L_0x001e:
            r7 = r13 & 16
            if (r7 == 0) goto L_0x0024
            r3 = r8
            goto L_0x0025
        L_0x0024:
            r3 = r11
        L_0x0025:
            r7 = r13 & 32
            if (r7 == 0) goto L_0x002b
            r4 = r8
            goto L_0x002c
        L_0x002b:
            r4 = r12
        L_0x002c:
            r7 = r5
            r8 = r6
            r9 = r0
            r11 = r14
            r12 = r2
            r13 = r3
            r14 = r4
            r7.<init>(r8, r9, r11, r12, r13, r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.file.b.<init>(java.lang.String, long, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final String d() {
        return this.a;
    }

    public final long e() {
        return this.b;
    }

    @Nullable
    public final Integer g() {
        return this.c;
    }

    @Nullable
    public final Integer f() {
        return this.d;
    }

    @Nullable
    public final Integer b() {
        return this.e;
    }

    @Nullable
    public final Integer c() {
        return this.f;
    }
}
