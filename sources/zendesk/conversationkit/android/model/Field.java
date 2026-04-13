package zendesk.conversationkit.android.model;

import com.google.firebase.messaging.Constants;
import com.squareup.moshi.g;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Field.kt */
public abstract class Field {
    @NotNull
    private final n a;

    public /* synthetic */ Field(n nVar, DefaultConstructorMarker defaultConstructorMarker) {
        this(nVar);
    }

    @NotNull
    public abstract String a();

    @NotNull
    public abstract String b();

    @NotNull
    public abstract String c();

    @NotNull
    public abstract String d();

    private Field(n type) {
        this.a = type;
    }

    @g(generateAdapter = true)
    /* compiled from: Field.kt */
    public static final class Text extends Field {
        @NotNull
        public static final a b = new a((DefaultConstructorMarker) null);
        @NotNull
        private final String c;
        @NotNull
        private final String d;
        @NotNull
        private final String e;
        @NotNull
        private final String f;
        private final int g;
        private final int h;
        @NotNull
        private final String i;

        public static /* synthetic */ Text f(Text text, String str, String str2, String str3, String str4, int i2, int i3, String str5, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                str = text.a();
            }
            if ((i4 & 2) != 0) {
                str2 = text.c();
            }
            String str6 = str2;
            if ((i4 & 4) != 0) {
                str3 = text.b();
            }
            String str7 = str3;
            if ((i4 & 8) != 0) {
                str4 = text.d();
            }
            String str8 = str4;
            if ((i4 & 16) != 0) {
                i2 = text.g;
            }
            int i5 = i2;
            if ((i4 & 32) != 0) {
                i3 = text.h;
            }
            int i6 = i3;
            if ((i4 & 64) != 0) {
                str5 = text.i;
            }
            return text.e(str, str6, str7, str8, i5, i6, str5);
        }

        @NotNull
        public final Text e(@NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull String str4, int i2, int i3, @NotNull String str5) {
            String str6 = str;
            k.e(str, "id");
            k.e(str2, "name");
            k.e(str3, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(str4, "placeholder");
            k.e(str5, "text");
            return new Text(str, str2, str3, str4, i2, i3, str5);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Text)) {
                return false;
            }
            Text text = (Text) obj;
            return k.a(a(), text.a()) && k.a(c(), text.c()) && k.a(b(), text.b()) && k.a(d(), text.d()) && this.g == text.g && this.h == text.h && k.a(this.i, text.i);
        }

        public int hashCode() {
            return (((((((((((a().hashCode() * 31) + c().hashCode()) * 31) + b().hashCode()) * 31) + d().hashCode()) * 31) + this.g) * 31) + this.h) * 31) + this.i.hashCode();
        }

        @NotNull
        public String toString() {
            return "Text(id=" + a() + ", name=" + c() + ", label=" + b() + ", placeholder=" + d() + ", minSize=" + this.g + ", maxSize=" + this.h + ", text=" + this.i + ')';
        }

        @NotNull
        public String a() {
            return this.c;
        }

        @NotNull
        public String c() {
            return this.d;
        }

        @NotNull
        public String b() {
            return this.e;
        }

        @NotNull
        public String d() {
            return this.f;
        }

        public final int h() {
            return this.g;
        }

        public final int g() {
            return this.h;
        }

        @NotNull
        public final String i() {
            return this.i;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Text(@NotNull String id, @NotNull String name, @NotNull String label, @NotNull String placeholder, int minSize, int maxSize, @NotNull String text) {
            super(n.TEXT, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(name, "name");
            k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(placeholder, "placeholder");
            k.e(text, "text");
            this.c = id;
            this.d = name;
            this.e = label;
            this.f = placeholder;
            this.g = minSize;
            this.h = maxSize;
            this.i = text;
        }

        /* compiled from: Field.kt */
        public static final class a {
            public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private a() {
            }
        }
    }

    @g(generateAdapter = true)
    /* compiled from: Field.kt */
    public static final class Email extends Field {
        @NotNull
        private final String b;
        @NotNull
        private final String c;
        @NotNull
        private final String d;
        @NotNull
        private final String e;
        @NotNull
        private final String f;

        public static /* synthetic */ Email f(Email email, String str, String str2, String str3, String str4, String str5, int i, Object obj) {
            if ((i & 1) != 0) {
                str = email.a();
            }
            if ((i & 2) != 0) {
                str2 = email.c();
            }
            String str6 = str2;
            if ((i & 4) != 0) {
                str3 = email.b();
            }
            String str7 = str3;
            if ((i & 8) != 0) {
                str4 = email.d();
            }
            String str8 = str4;
            if ((i & 16) != 0) {
                str5 = email.f;
            }
            return email.e(str, str6, str7, str8, str5);
        }

        @NotNull
        public final Email e(@NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull String str4, @NotNull String str5) {
            k.e(str, "id");
            k.e(str2, "name");
            k.e(str3, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(str4, "placeholder");
            k.e(str5, "email");
            return new Email(str, str2, str3, str4, str5);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Email)) {
                return false;
            }
            Email email = (Email) obj;
            return k.a(a(), email.a()) && k.a(c(), email.c()) && k.a(b(), email.b()) && k.a(d(), email.d()) && k.a(this.f, email.f);
        }

        public int hashCode() {
            return (((((((a().hashCode() * 31) + c().hashCode()) * 31) + b().hashCode()) * 31) + d().hashCode()) * 31) + this.f.hashCode();
        }

        @NotNull
        public String toString() {
            return "Email(id=" + a() + ", name=" + c() + ", label=" + b() + ", placeholder=" + d() + ", email=" + this.f + ')';
        }

        @NotNull
        public String a() {
            return this.b;
        }

        @NotNull
        public String c() {
            return this.c;
        }

        @NotNull
        public String b() {
            return this.d;
        }

        @NotNull
        public String d() {
            return this.e;
        }

        @NotNull
        public final String g() {
            return this.f;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Email(@NotNull String id, @NotNull String name, @NotNull String label, @NotNull String placeholder, @NotNull String email) {
            super(n.EMAIL, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(name, "name");
            k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(placeholder, "placeholder");
            k.e(email, "email");
            this.b = id;
            this.c = name;
            this.d = label;
            this.e = placeholder;
            this.f = email;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: Field.kt */
    public static final class Select extends Field {
        @NotNull
        public static final a b = new a((DefaultConstructorMarker) null);
        @NotNull
        private final String c;
        @NotNull
        private final String d;
        @NotNull
        private final String e;
        @NotNull
        private final String f;
        @NotNull
        private final List<FieldOption> g;
        private final int h;
        @NotNull
        private final List<FieldOption> i;

        public static /* synthetic */ Select f(Select select, String str, String str2, String str3, String str4, List<FieldOption> list, int i2, List<FieldOption> list2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                str = select.a();
            }
            if ((i3 & 2) != 0) {
                str2 = select.c();
            }
            String str5 = str2;
            if ((i3 & 4) != 0) {
                str3 = select.b();
            }
            String str6 = str3;
            if ((i3 & 8) != 0) {
                str4 = select.d();
            }
            String str7 = str4;
            if ((i3 & 16) != 0) {
                list = select.g;
            }
            List<FieldOption> list3 = list;
            if ((i3 & 32) != 0) {
                i2 = select.h;
            }
            int i4 = i2;
            if ((i3 & 64) != 0) {
                list2 = select.i;
            }
            return select.e(str, str5, str6, str7, list3, i4, list2);
        }

        @NotNull
        public final Select e(@NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull String str4, @NotNull List<FieldOption> list, int i2, @NotNull List<FieldOption> list2) {
            k.e(str, "id");
            k.e(str2, "name");
            k.e(str3, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(str4, "placeholder");
            k.e(list, "options");
            k.e(list2, "select");
            return new Select(str, str2, str3, str4, list, i2, list2);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Select)) {
                return false;
            }
            Select select = (Select) obj;
            return k.a(a(), select.a()) && k.a(c(), select.c()) && k.a(b(), select.b()) && k.a(d(), select.d()) && k.a(this.g, select.g) && this.h == select.h && k.a(this.i, select.i);
        }

        public int hashCode() {
            return (((((((((((a().hashCode() * 31) + c().hashCode()) * 31) + b().hashCode()) * 31) + d().hashCode()) * 31) + this.g.hashCode()) * 31) + this.h) * 31) + this.i.hashCode();
        }

        @NotNull
        public String toString() {
            return "Select(id=" + a() + ", name=" + c() + ", label=" + b() + ", placeholder=" + d() + ", options=" + this.g + ", selectSize=" + this.h + ", select=" + this.i + ')';
        }

        @NotNull
        public String a() {
            return this.c;
        }

        @NotNull
        public String c() {
            return this.d;
        }

        @NotNull
        public String b() {
            return this.e;
        }

        @NotNull
        public String d() {
            return this.f;
        }

        @NotNull
        public final List<FieldOption> g() {
            return this.g;
        }

        public final int i() {
            return this.h;
        }

        @NotNull
        public final List<FieldOption> h() {
            return this.i;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Select(@NotNull String id, @NotNull String name, @NotNull String label, @NotNull String placeholder, @NotNull List<FieldOption> options, int selectSize, @NotNull List<FieldOption> select) {
            super(n.SELECT, (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(name, "name");
            k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(placeholder, "placeholder");
            k.e(options, "options");
            k.e(select, "select");
            this.c = id;
            this.d = name;
            this.e = label;
            this.f = placeholder;
            this.g = options;
            this.h = selectSize;
            this.i = select;
        }

        /* compiled from: Field.kt */
        public static final class a {
            public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private a() {
            }
        }
    }
}
