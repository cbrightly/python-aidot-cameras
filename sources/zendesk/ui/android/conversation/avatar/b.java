package zendesk.ui.android.conversation.avatar;

import android.net.Uri;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AvatarImageState.kt */
public final class b {
    @Nullable
    private final Uri a;
    private final boolean b;
    private final int c;
    @Nullable
    private final Integer d;
    @NotNull
    private final c e;

    public b() {
        this((Uri) null, false, 0, (Integer) null, (c) null, 31, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ b b(b bVar, Uri uri, boolean z, int i, Integer num, c cVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            uri = bVar.a;
        }
        if ((i2 & 2) != 0) {
            z = bVar.b;
        }
        boolean z2 = z;
        if ((i2 & 4) != 0) {
            i = bVar.c;
        }
        int i3 = i;
        if ((i2 & 8) != 0) {
            num = bVar.d;
        }
        Integer num2 = num;
        if ((i2 & 16) != 0) {
            cVar = bVar.e;
        }
        return bVar.a(uri, z2, i3, num2, cVar);
    }

    @NotNull
    public final b a(@Nullable Uri uri, boolean z, @DimenRes int i, @Nullable @ColorInt Integer num, @NotNull c cVar) {
        k.e(cVar, "mask");
        return new b(uri, z, i, num, cVar);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return k.a(this.a, bVar.a) && this.b == bVar.b && this.c == bVar.c && k.a(this.d, bVar.d) && this.e == bVar.e;
    }

    public int hashCode() {
        Uri uri = this.a;
        int i = 0;
        int hashCode = (uri == null ? 0 : uri.hashCode()) * 31;
        boolean z = this.b;
        if (z) {
            z = true;
        }
        int i2 = (((hashCode + (z ? 1 : 0)) * 31) + this.c) * 31;
        Integer num = this.d;
        if (num != null) {
            i = num.hashCode();
        }
        return ((i2 + i) * 31) + this.e.hashCode();
    }

    @NotNull
    public String toString() {
        return "AvatarImageState(uri=" + this.a + ", shouldAnimate=" + this.b + ", avatarSize=" + this.c + ", backgroundColor=" + this.d + ", mask=" + this.e + ')';
    }

    public b(@Nullable Uri uri, boolean shouldAnimate, @DimenRes int avatarSize, @Nullable @ColorInt Integer backgroundColor, @NotNull c mask) {
        k.e(mask, "mask");
        this.a = uri;
        this.b = shouldAnimate;
        this.c = avatarSize;
        this.d = backgroundColor;
        this.e = mask;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ b(android.net.Uri r4, boolean r5, int r6, java.lang.Integer r7, zendesk.ui.android.conversation.avatar.c r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r3 = this;
            r10 = r9 & 1
            r0 = 0
            if (r10 == 0) goto L_0x0007
            r10 = r0
            goto L_0x0008
        L_0x0007:
            r10 = r4
        L_0x0008:
            r4 = r9 & 2
            if (r4 == 0) goto L_0x000f
            r5 = 1
            r1 = r5
            goto L_0x0010
        L_0x000f:
            r1 = r5
        L_0x0010:
            r4 = r9 & 4
            if (r4 == 0) goto L_0x0018
            int r6 = zendesk.ui.android.R$dimen.zuia_avatar_image_size
            r2 = r6
            goto L_0x0019
        L_0x0018:
            r2 = r6
        L_0x0019:
            r4 = r9 & 8
            if (r4 == 0) goto L_0x001e
            goto L_0x001f
        L_0x001e:
            r0 = r7
        L_0x001f:
            r4 = r9 & 16
            if (r4 == 0) goto L_0x0027
            zendesk.ui.android.conversation.avatar.c r8 = zendesk.ui.android.conversation.avatar.c.NONE
            r9 = r8
            goto L_0x0028
        L_0x0027:
            r9 = r8
        L_0x0028:
            r4 = r3
            r5 = r10
            r6 = r1
            r7 = r2
            r8 = r0
            r4.<init>(r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.avatar.b.<init>(android.net.Uri, boolean, int, java.lang.Integer, zendesk.ui.android.conversation.avatar.c, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Nullable
    public final Uri g() {
        return this.a;
    }

    public final boolean f() {
        return this.b;
    }

    public final int c() {
        return this.c;
    }

    @Nullable
    public final Integer d() {
        return this.d;
    }

    @NotNull
    public final c e() {
        return this.e;
    }
}
