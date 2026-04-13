package zendesk.conversationkit.android.internal.rest.model;

import com.google.firebase.messaging.Constants;
import com.squareup.moshi.e;
import com.squareup.moshi.g;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SendMessageRequestDto.kt */
public abstract class SendFieldResponseDto {
    @NotNull
    private final String a;

    public /* synthetic */ SendFieldResponseDto(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private SendFieldResponseDto(String type) {
        this.a = type;
    }

    @g(generateAdapter = true)
    /* compiled from: SendMessageRequestDto.kt */
    public static final class Text extends SendFieldResponseDto {
        @NotNull
        private final String b;
        @NotNull
        private final String c;
        @NotNull
        private final String d;
        @NotNull
        private final String e;

        @NotNull
        public final Text copy(@e(name = "_id") @NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull String str4) {
            k.e(str, "id");
            k.e(str2, "name");
            k.e(str3, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(str4, "text");
            return new Text(str, str2, str3, str4);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Text)) {
                return false;
            }
            Text text = (Text) obj;
            return k.a(a(), text.a()) && k.a(c(), text.c()) && k.a(b(), text.b()) && k.a(this.e, text.e);
        }

        public int hashCode() {
            return (((((a().hashCode() * 31) + c().hashCode()) * 31) + b().hashCode()) * 31) + this.e.hashCode();
        }

        @NotNull
        public String toString() {
            return "Text(id=" + a() + ", name=" + c() + ", label=" + b() + ", text=" + this.e + ')';
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
        public final String d() {
            return this.e;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Text(@e(name = "_id") @NotNull String id, @NotNull String name, @NotNull String label, @NotNull String text) {
            super("text", (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(name, "name");
            k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(text, "text");
            this.b = id;
            this.c = name;
            this.d = label;
            this.e = text;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: SendMessageRequestDto.kt */
    public static final class Email extends SendFieldResponseDto {
        @NotNull
        private final String b;
        @NotNull
        private final String c;
        @NotNull
        private final String d;
        @NotNull
        private final String e;

        @NotNull
        public final Email copy(@e(name = "_id") @NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull String str4) {
            k.e(str, "id");
            k.e(str2, "name");
            k.e(str3, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(str4, "email");
            return new Email(str, str2, str3, str4);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Email)) {
                return false;
            }
            Email email = (Email) obj;
            return k.a(b(), email.b()) && k.a(d(), email.d()) && k.a(c(), email.c()) && k.a(this.e, email.e);
        }

        public int hashCode() {
            return (((((b().hashCode() * 31) + d().hashCode()) * 31) + c().hashCode()) * 31) + this.e.hashCode();
        }

        @NotNull
        public String toString() {
            return "Email(id=" + b() + ", name=" + d() + ", label=" + c() + ", email=" + this.e + ')';
        }

        @NotNull
        public String b() {
            return this.b;
        }

        @NotNull
        public String d() {
            return this.c;
        }

        @NotNull
        public String c() {
            return this.d;
        }

        @NotNull
        public final String a() {
            return this.e;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Email(@e(name = "_id") @NotNull String id, @NotNull String name, @NotNull String label, @NotNull String email) {
            super("email", (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(name, "name");
            k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(email, "email");
            this.b = id;
            this.c = name;
            this.d = label;
            this.e = email;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: SendMessageRequestDto.kt */
    public static final class Select extends SendFieldResponseDto {
        @NotNull
        private final String b;
        @NotNull
        private final String c;
        @NotNull
        private final String d;
        @NotNull
        private final List<SendFieldSelectDto> e;

        @NotNull
        public final Select copy(@e(name = "_id") @NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull List<SendFieldSelectDto> list) {
            k.e(str, "id");
            k.e(str2, "name");
            k.e(str3, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(list, "select");
            return new Select(str, str2, str3, list);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Select)) {
                return false;
            }
            Select select = (Select) obj;
            return k.a(a(), select.a()) && k.a(c(), select.c()) && k.a(b(), select.b()) && k.a(this.e, select.e);
        }

        public int hashCode() {
            return (((((a().hashCode() * 31) + c().hashCode()) * 31) + b().hashCode()) * 31) + this.e.hashCode();
        }

        @NotNull
        public String toString() {
            return "Select(id=" + a() + ", name=" + c() + ", label=" + b() + ", select=" + this.e + ')';
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
        public final List<SendFieldSelectDto> d() {
            return this.e;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Select(@e(name = "_id") @NotNull String id, @NotNull String name, @NotNull String label, @NotNull List<SendFieldSelectDto> select) {
            super("select", (DefaultConstructorMarker) null);
            k.e(id, "id");
            k.e(name, "name");
            k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
            k.e(select, "select");
            this.b = id;
            this.c = name;
            this.d = label;
            this.e = select;
        }
    }
}
