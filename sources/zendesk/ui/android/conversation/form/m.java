package zendesk.ui.android.conversation.form;

import androidx.annotation.ColorInt;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FieldState.kt */
public abstract class m {
    @Nullable
    private final String a;
    @Nullable
    private final String b;
    @Nullable
    private final Integer c;

    public /* synthetic */ m(String str, String str2, Integer num, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, num);
    }

    @Nullable
    public abstract Integer a();

    @Nullable
    public abstract String b();

    @Nullable
    public abstract String c();

    private m(String placeholder, String label, @ColorInt Integer borderColor) {
        this.a = placeholder;
        this.b = label;
        this.c = borderColor;
    }

    /* compiled from: FieldState.kt */
    public static final class c extends m {
        @Nullable
        private final String d;
        private final int e;
        private final int f;
        @Nullable
        private final String g;
        @Nullable
        private final String h;
        @Nullable
        private final Integer i;

        public c() {
            this((String) null, 0, 0, (String) null, (String) null, (Integer) null, 63, (DefaultConstructorMarker) null);
        }

        public static /* synthetic */ c e(c cVar, String str, int i2, int i3, String str2, String str3, Integer num, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                str = cVar.d;
            }
            if ((i4 & 2) != 0) {
                i2 = cVar.e;
            }
            int i5 = i2;
            if ((i4 & 4) != 0) {
                i3 = cVar.f;
            }
            int i6 = i3;
            if ((i4 & 8) != 0) {
                str2 = cVar.c();
            }
            String str4 = str2;
            if ((i4 & 16) != 0) {
                str3 = cVar.b();
            }
            String str5 = str3;
            if ((i4 & 32) != 0) {
                num = cVar.a();
            }
            return cVar.d(str, i5, i6, str4, str5, num);
        }

        @NotNull
        public final c d(@Nullable String str, int i2, int i3, @Nullable String str2, @Nullable String str3, @Nullable @ColorInt Integer num) {
            return new c(str, i2, i3, str2, str3, num);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return k.a(this.d, cVar.d) && this.e == cVar.e && this.f == cVar.f && k.a(c(), cVar.c()) && k.a(b(), cVar.b()) && k.a(a(), cVar.a());
        }

        public int hashCode() {
            String str = this.d;
            int i2 = 0;
            int hashCode = (((((((((str == null ? 0 : str.hashCode()) * 31) + this.e) * 31) + this.f) * 31) + (c() == null ? 0 : c().hashCode())) * 31) + (b() == null ? 0 : b().hashCode())) * 31;
            if (a() != null) {
                i2 = a().hashCode();
            }
            return hashCode + i2;
        }

        @NotNull
        public String toString() {
            return "Text(text=" + this.d + ", minLength=" + this.e + ", maxLength=" + this.f + ", placeholder=" + c() + ", label=" + b() + ", borderColor=" + a() + ')';
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public /* synthetic */ c(java.lang.String r6, int r7, int r8, java.lang.String r9, java.lang.String r10, java.lang.Integer r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
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
                if (r6 == 0) goto L_0x000f
                r7 = 0
                r1 = r7
                goto L_0x0010
            L_0x000f:
                r1 = r7
            L_0x0010:
                r6 = r12 & 4
                if (r6 == 0) goto L_0x0019
                r8 = 2147483647(0x7fffffff, float:NaN)
                r2 = r8
                goto L_0x001a
            L_0x0019:
                r2 = r8
            L_0x001a:
                r6 = r12 & 8
                if (r6 == 0) goto L_0x0020
                r3 = r0
                goto L_0x0021
            L_0x0020:
                r3 = r9
            L_0x0021:
                r6 = r12 & 16
                if (r6 == 0) goto L_0x0027
                r4 = r0
                goto L_0x0028
            L_0x0027:
                r4 = r10
            L_0x0028:
                r6 = r12 & 32
                if (r6 == 0) goto L_0x002e
                r12 = r0
                goto L_0x002f
            L_0x002e:
                r12 = r11
            L_0x002f:
                r6 = r5
                r7 = r13
                r8 = r1
                r9 = r2
                r10 = r3
                r11 = r4
                r6.<init>(r7, r8, r9, r10, r11, r12)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.form.m.c.<init>(java.lang.String, int, int, java.lang.String, java.lang.String, java.lang.Integer, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        @Nullable
        public final String h() {
            return this.d;
        }

        public final int g() {
            return this.e;
        }

        public final int f() {
            return this.f;
        }

        @Nullable
        public String c() {
            return this.g;
        }

        @Nullable
        public String b() {
            return this.h;
        }

        @Nullable
        public Integer a() {
            return this.i;
        }

        public c(@Nullable String text, int minLength, int maxLength, @Nullable String placeholder, @Nullable String label, @Nullable @ColorInt Integer borderColor) {
            super(placeholder, label, borderColor, (DefaultConstructorMarker) null);
            this.d = text;
            this.e = minLength;
            this.f = maxLength;
            this.g = placeholder;
            this.h = label;
            this.i = borderColor;
        }

        /* compiled from: FieldState.kt */
        public static final class a {
            @NotNull
            private c a = new c((String) null, 0, 0, (String) null, (String) null, (Integer) null, 63, (DefaultConstructorMarker) null);

            @NotNull
            public final a f(@Nullable String text) {
                this.a = c.e(this.a, text, 0, 0, (String) null, (String) null, (Integer) null, 62, (Object) null);
                return this;
            }

            @NotNull
            public final a d(int minLength) {
                this.a = c.e(this.a, (String) null, n.b(minLength, 0), 0, (String) null, (String) null, (Integer) null, 61, (Object) null);
                return this;
            }

            @NotNull
            public final a c(int maxLength) {
                this.a = c.e(this.a, (String) null, 0, maxLength, (String) null, (String) null, (Integer) null, 59, (Object) null);
                return this;
            }

            @NotNull
            public final a e(@Nullable String placeholder) {
                this.a = c.e(this.a, (String) null, 0, 0, placeholder, (String) null, (Integer) null, 55, (Object) null);
                return this;
            }

            @NotNull
            public final a b(@Nullable String label) {
                this.a = c.e(this.a, (String) null, 0, 0, (String) null, label, (Integer) null, 47, (Object) null);
                return this;
            }

            @NotNull
            public final c a() {
                return this.a;
            }
        }
    }

    /* compiled from: FieldState.kt */
    public static final class a extends m {
        @Nullable
        private final String d;
        @Nullable
        private final String e;
        @Nullable
        private final String f;
        @Nullable
        private final Integer g;

        public a() {
            this((String) null, (String) null, (String) null, (Integer) null, 15, (DefaultConstructorMarker) null);
        }

        public static /* synthetic */ a e(a aVar, String str, String str2, String str3, Integer num, int i, Object obj) {
            if ((i & 1) != 0) {
                str = aVar.d;
            }
            if ((i & 2) != 0) {
                str2 = aVar.c();
            }
            if ((i & 4) != 0) {
                str3 = aVar.b();
            }
            if ((i & 8) != 0) {
                num = aVar.a();
            }
            return aVar.d(str, str2, str3, num);
        }

        @NotNull
        public final a d(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable @ColorInt Integer num) {
            return new a(str, str2, str3, num);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return k.a(this.d, aVar.d) && k.a(c(), aVar.c()) && k.a(b(), aVar.b()) && k.a(a(), aVar.a());
        }

        public int hashCode() {
            String str = this.d;
            int i = 0;
            int hashCode = (((((str == null ? 0 : str.hashCode()) * 31) + (c() == null ? 0 : c().hashCode())) * 31) + (b() == null ? 0 : b().hashCode())) * 31;
            if (a() != null) {
                i = a().hashCode();
            }
            return hashCode + i;
        }

        @NotNull
        public String toString() {
            return "Email(email=" + this.d + ", placeholder=" + c() + ", label=" + b() + ", borderColor=" + a() + ')';
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ a(String str, String str2, String str3, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : num);
        }

        @Nullable
        public final String f() {
            return this.d;
        }

        @Nullable
        public String c() {
            return this.e;
        }

        @Nullable
        public String b() {
            return this.f;
        }

        @Nullable
        public Integer a() {
            return this.g;
        }

        public a(@Nullable String email, @Nullable String placeholder, @Nullable String label, @Nullable @ColorInt Integer borderColor) {
            super(placeholder, label, borderColor, (DefaultConstructorMarker) null);
            this.d = email;
            this.e = placeholder;
            this.f = label;
            this.g = borderColor;
        }

        /* renamed from: zendesk.ui.android.conversation.form.m$a$a  reason: collision with other inner class name */
        /* compiled from: FieldState.kt */
        public static final class C0566a {
            @NotNull
            private a a = new a((String) null, (String) null, (String) null, (Integer) null, 15, (DefaultConstructorMarker) null);

            @NotNull
            public final C0566a b(@Nullable String email) {
                this.a = a.e(this.a, email, (String) null, (String) null, (Integer) null, 14, (Object) null);
                return this;
            }

            @NotNull
            public final C0566a d(@Nullable String placeholder) {
                this.a = a.e(this.a, (String) null, placeholder, (String) null, (Integer) null, 13, (Object) null);
                return this;
            }

            @NotNull
            public final C0566a c(@Nullable String label) {
                this.a = a.e(this.a, (String) null, (String) null, label, (Integer) null, 11, (Object) null);
                return this;
            }

            @NotNull
            public final a a() {
                return this.a;
            }
        }
    }

    /* compiled from: FieldState.kt */
    public static final class b extends m {
        @NotNull
        private final List<u> d;
        @NotNull
        private final List<u> e;
        @Nullable
        private final String f;
        @Nullable
        private final String g;
        @Nullable
        private final Integer h;

        public b() {
            this((List) null, (List) null, (String) null, (String) null, (Integer) null, 31, (DefaultConstructorMarker) null);
        }

        public static /* synthetic */ b e(b bVar, List<u> list, List<u> list2, String str, String str2, Integer num, int i, Object obj) {
            if ((i & 1) != 0) {
                list = bVar.d;
            }
            if ((i & 2) != 0) {
                list2 = bVar.e;
            }
            List<u> list3 = list2;
            if ((i & 4) != 0) {
                str = bVar.c();
            }
            String str3 = str;
            if ((i & 8) != 0) {
                str2 = bVar.b();
            }
            String str4 = str2;
            if ((i & 16) != 0) {
                num = bVar.a();
            }
            return bVar.d(list, list3, str3, str4, num);
        }

        @NotNull
        public final b d(@NotNull List<u> list, @NotNull List<u> list2, @Nullable String str, @Nullable String str2, @Nullable @ColorInt Integer num) {
            k.e(list, "options");
            k.e(list2, "select");
            return new b(list, list2, str, str2, num);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return k.a(this.d, bVar.d) && k.a(this.e, bVar.e) && k.a(c(), bVar.c()) && k.a(b(), bVar.b()) && k.a(a(), bVar.a());
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((((((this.d.hashCode() * 31) + this.e.hashCode()) * 31) + (c() == null ? 0 : c().hashCode())) * 31) + (b() == null ? 0 : b().hashCode())) * 31;
            if (a() != null) {
                i = a().hashCode();
            }
            return hashCode + i;
        }

        @NotNull
        public String toString() {
            return "Select(options=" + this.d + ", select=" + this.e + ", placeholder=" + c() + ", label=" + b() + ", borderColor=" + a() + ')';
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public /* synthetic */ b(java.util.List r4, java.util.List r5, java.lang.String r6, java.lang.String r7, java.lang.Integer r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
            /*
                r3 = this;
                r10 = r9 & 1
                if (r10 == 0) goto L_0x0008
                java.util.List r4 = kotlin.collections.q.g()
            L_0x0008:
                r10 = r9 & 2
                if (r10 == 0) goto L_0x0012
                java.util.List r5 = kotlin.collections.q.g()
                r10 = r5
                goto L_0x0013
            L_0x0012:
                r10 = r5
            L_0x0013:
                r5 = r9 & 4
                r0 = 0
                if (r5 == 0) goto L_0x001a
                r1 = r0
                goto L_0x001b
            L_0x001a:
                r1 = r6
            L_0x001b:
                r5 = r9 & 8
                if (r5 == 0) goto L_0x0021
                r2 = r0
                goto L_0x0022
            L_0x0021:
                r2 = r7
            L_0x0022:
                r5 = r9 & 16
                if (r5 == 0) goto L_0x0027
                goto L_0x0028
            L_0x0027:
                r0 = r8
            L_0x0028:
                r5 = r3
                r6 = r4
                r7 = r10
                r8 = r1
                r9 = r2
                r10 = r0
                r5.<init>(r6, r7, r8, r9, r10)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.form.m.b.<init>(java.util.List, java.util.List, java.lang.String, java.lang.String, java.lang.Integer, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        @NotNull
        public final List<u> f() {
            return this.d;
        }

        @NotNull
        public final List<u> g() {
            return this.e;
        }

        @Nullable
        public String c() {
            return this.f;
        }

        @Nullable
        public String b() {
            return this.g;
        }

        @Nullable
        public Integer a() {
            return this.h;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull List<u> options, @NotNull List<u> select, @Nullable String placeholder, @Nullable String label, @Nullable @ColorInt Integer borderColor) {
            super(placeholder, label, borderColor, (DefaultConstructorMarker) null);
            k.e(options, "options");
            k.e(select, "select");
            this.d = options;
            this.e = select;
            this.f = placeholder;
            this.g = label;
            this.h = borderColor;
        }

        /* compiled from: FieldState.kt */
        public static final class a {
            @NotNull
            private b a = new b((List) null, (List) null, (String) null, (String) null, (Integer) null, 31, (DefaultConstructorMarker) null);

            @NotNull
            public final a c(@NotNull List<u> options) {
                k.e(options, "options");
                this.a = b.e(this.a, options, (List) null, (String) null, (String) null, (Integer) null, 30, (Object) null);
                return this;
            }

            @NotNull
            public final a e(@NotNull List<u> select) {
                k.e(select, "select");
                this.a = b.e(this.a, (List) null, select, (String) null, (String) null, (Integer) null, 29, (Object) null);
                return this;
            }

            @NotNull
            public final a d(@Nullable String placeholder) {
                this.a = b.e(this.a, (List) null, (List) null, placeholder, (String) null, (Integer) null, 27, (Object) null);
                return this;
            }

            @NotNull
            public final a b(@Nullable String label) {
                this.a = b.e(this.a, (List) null, (List) null, (String) null, label, (Integer) null, 23, (Object) null);
                return this;
            }

            @NotNull
            public final b a() {
                return this.a;
            }
        }
    }
}
