package zendesk.ui.android.conversation.item;

import androidx.annotation.ColorInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Item.kt */
public final class a<T> {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @Nullable
    private final Integer c;
    @Nullable
    private final String d;
    @Nullable
    private final T e;

    public a() {
        this((String) null, (String) null, (Integer) null, (String) null, (Object) null, 31, (DefaultConstructorMarker) null);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return k.a(this.a, aVar.a) && k.a(this.b, aVar.b) && k.a(this.c, aVar.c) && k.a(this.d, aVar.d) && k.a(this.e, aVar.e);
    }

    public int hashCode() {
        int hashCode = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
        Integer num = this.c;
        int i = 0;
        int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        String str = this.d;
        int hashCode3 = (hashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        T t = this.e;
        if (t != null) {
            i = t.hashCode();
        }
        return hashCode3 + i;
    }

    @NotNull
    public String toString() {
        return "Item(id=" + this.a + ", title=" + this.b + ", titleColor=" + this.c + ", subtitle=" + this.d + ", value=" + this.e + ')';
    }

    public a(@NotNull String id, @NotNull String title, @Nullable @ColorInt Integer titleColor, @Nullable String subtitle, @Nullable T value) {
        k.e(id, "id");
        k.e(title, "title");
        this.a = id;
        this.b = title;
        this.c = titleColor;
        this.d = subtitle;
        this.e = value;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ a(java.lang.String r7, java.lang.String r8, java.lang.Integer r9, java.lang.String r10, java.lang.Object r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r13 = r12 & 1
            if (r13 == 0) goto L_0x0013
            java.util.UUID r7 = java.util.UUID.randomUUID()
            java.lang.String r7 = r7.toString()
            java.lang.String r13 = "randomUUID().toString()"
            kotlin.jvm.internal.k.d(r7, r13)
            r1 = r7
            goto L_0x0014
        L_0x0013:
            r1 = r7
        L_0x0014:
            r7 = r12 & 2
            if (r7 == 0) goto L_0x001c
            java.lang.String r8 = ""
            r2 = r8
            goto L_0x001d
        L_0x001c:
            r2 = r8
        L_0x001d:
            r7 = r12 & 4
            r8 = 0
            if (r7 == 0) goto L_0x0024
            r3 = r8
            goto L_0x0025
        L_0x0024:
            r3 = r9
        L_0x0025:
            r7 = r12 & 8
            if (r7 == 0) goto L_0x002b
            r4 = r8
            goto L_0x002c
        L_0x002b:
            r4 = r10
        L_0x002c:
            r7 = r12 & 16
            if (r7 == 0) goto L_0x0032
            r5 = r8
            goto L_0x0033
        L_0x0032:
            r5 = r11
        L_0x0033:
            r0 = r6
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.item.a.<init>(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Object, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final String b() {
        return this.b;
    }

    @Nullable
    public final Integer c() {
        return this.c;
    }

    @Nullable
    public final String a() {
        return this.d;
    }

    @Nullable
    public final T d() {
        return this.e;
    }
}
