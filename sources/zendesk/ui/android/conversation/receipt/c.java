package zendesk.ui.android.conversation.receipt;

import androidx.annotation.ColorInt;
import com.google.firebase.messaging.Constants;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessageReceiptState.kt */
public final class c {
    @NotNull
    private final String a;
    @NotNull
    private final a b;
    private final boolean c;
    @Nullable
    private final Integer d;
    @Nullable
    private final Integer e;

    public c() {
        this((String) null, (a) null, false, (Integer) null, (Integer) null, 31, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ c b(c cVar, String str, a aVar, boolean z, Integer num, Integer num2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = cVar.a;
        }
        if ((i & 2) != 0) {
            aVar = cVar.b;
        }
        a aVar2 = aVar;
        if ((i & 4) != 0) {
            z = cVar.c;
        }
        boolean z2 = z;
        if ((i & 8) != 0) {
            num = cVar.d;
        }
        Integer num3 = num;
        if ((i & 16) != 0) {
            num2 = cVar.e;
        }
        return cVar.a(str, aVar2, z2, num3, num2);
    }

    @NotNull
    public final c a(@NotNull String str, @NotNull a aVar, boolean z, @Nullable @ColorInt Integer num, @Nullable @ColorInt Integer num2) {
        k.e(str, Constants.ScionAnalytics.PARAM_LABEL);
        k.e(aVar, "messageReceiptPosition");
        return new c(str, aVar, z, num, num2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return k.a(this.a, cVar.a) && this.b == cVar.b && this.c == cVar.c && k.a(this.d, cVar.d) && k.a(this.e, cVar.e);
    }

    public int hashCode() {
        int hashCode = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
        boolean z = this.c;
        if (z) {
            z = true;
        }
        int i = (hashCode + (z ? 1 : 0)) * 31;
        Integer num = this.d;
        int i2 = 0;
        int hashCode2 = (i + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.e;
        if (num2 != null) {
            i2 = num2.hashCode();
        }
        return hashCode2 + i2;
    }

    @NotNull
    public String toString() {
        return "MessageReceiptState(label=" + this.a + ", messageReceiptPosition=" + this.b + ", showIcon=" + this.c + ", labelColor=" + this.d + ", iconColor=" + this.e + ')';
    }

    public c(@NotNull String label, @NotNull a messageReceiptPosition, boolean showIcon, @Nullable @ColorInt Integer labelColor, @Nullable @ColorInt Integer iconColor) {
        k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
        k.e(messageReceiptPosition, "messageReceiptPosition");
        this.a = label;
        this.b = messageReceiptPosition;
        this.c = showIcon;
        this.d = labelColor;
        this.e = iconColor;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ c(java.lang.String r4, zendesk.ui.android.conversation.receipt.a r5, boolean r6, java.lang.Integer r7, java.lang.Integer r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r3 = this;
            r10 = r9 & 1
            if (r10 == 0) goto L_0x0006
            java.lang.String r4 = ""
        L_0x0006:
            r10 = r9 & 2
            if (r10 == 0) goto L_0x000e
            zendesk.ui.android.conversation.receipt.a r5 = zendesk.ui.android.conversation.receipt.a.NONE
            r10 = r5
            goto L_0x000f
        L_0x000e:
            r10 = r5
        L_0x000f:
            r5 = r9 & 4
            if (r5 == 0) goto L_0x0016
            r6 = 1
            r0 = r6
            goto L_0x0017
        L_0x0016:
            r0 = r6
        L_0x0017:
            r5 = r9 & 8
            r6 = 0
            if (r5 == 0) goto L_0x001e
            r1 = r6
            goto L_0x001f
        L_0x001e:
            r1 = r7
        L_0x001f:
            r5 = r9 & 16
            if (r5 == 0) goto L_0x0025
            r2 = r6
            goto L_0x0026
        L_0x0025:
            r2 = r8
        L_0x0026:
            r5 = r3
            r6 = r4
            r7 = r10
            r8 = r0
            r9 = r1
            r10 = r2
            r5.<init>(r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.receipt.c.<init>(java.lang.String, zendesk.ui.android.conversation.receipt.a, boolean, java.lang.Integer, java.lang.Integer, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final String d() {
        return this.a;
    }

    @NotNull
    public final a f() {
        return this.b;
    }

    public final boolean g() {
        return this.c;
    }

    @Nullable
    public final Integer e() {
        return this.d;
    }

    @Nullable
    public final Integer c() {
        return this.e;
    }

    @NotNull
    public final a h() {
        return new a(this);
    }

    /* compiled from: MessageReceiptState.kt */
    public static final class a {
        @NotNull
        private c a;

        public a() {
            this.a = new c((String) null, (a) null, false, (Integer) null, (Integer) null, 31, (DefaultConstructorMarker) null);
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(@NotNull c state) {
            this();
            k.e(state, com.leedarson.bean.Constants.ACTION_STATE);
            this.a = state;
        }

        @NotNull
        public final a c(@NotNull String label) {
            k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
            this.a = c.b(this.a, label, (a) null, false, (Integer) null, (Integer) null, 30, (Object) null);
            return this;
        }

        @NotNull
        public final a e(@NotNull a messageReceiptPosition) {
            k.e(messageReceiptPosition, "messageReceiptPosition");
            this.a = c.b(this.a, (String) null, messageReceiptPosition, false, (Integer) null, (Integer) null, 29, (Object) null);
            return this;
        }

        @NotNull
        public final a f(boolean value) {
            this.a = c.b(this.a, (String) null, (a) null, value, (Integer) null, (Integer) null, 27, (Object) null);
            return this;
        }

        @NotNull
        public final a d(@ColorInt int color) {
            this.a = c.b(this.a, (String) null, (a) null, false, Integer.valueOf(color), (Integer) null, 23, (Object) null);
            return this;
        }

        @NotNull
        public final a b(@ColorInt int color) {
            this.a = c.b(this.a, (String) null, (a) null, false, (Integer) null, Integer.valueOf(color), 15, (Object) null);
            return this;
        }

        @NotNull
        public final c a() {
            return this.a;
        }
    }
}
