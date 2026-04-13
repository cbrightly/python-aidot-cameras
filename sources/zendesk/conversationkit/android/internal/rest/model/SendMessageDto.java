package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SendMessageRequestDto.kt */
public abstract class SendMessageDto {
    @NotNull
    private final String a;

    public /* synthetic */ SendMessageDto(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private SendMessageDto(String type) {
        this.a = type;
    }

    @g(generateAdapter = true)
    /* compiled from: SendMessageRequestDto.kt */
    public static final class Text extends SendMessageDto {
        @NotNull
        private final String b;
        @Nullable
        private final Map<String, Object> c;
        @Nullable
        private final String d;
        @NotNull
        private final String e;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Text)) {
                return false;
            }
            Text text = (Text) obj;
            return k.a(c(), text.c()) && k.a(a(), text.a()) && k.a(b(), text.b()) && k.a(this.e, text.e);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((c().hashCode() * 31) + (a() == null ? 0 : a().hashCode())) * 31;
            if (b() != null) {
                i = b().hashCode();
            }
            return ((hashCode + i) * 31) + this.e.hashCode();
        }

        @NotNull
        public String toString() {
            return "Text(role=" + c() + ", metadata=" + a() + ", payload=" + b() + ", text=" + this.e + ')';
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ Text(String str, Map map, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : map, (i & 4) != 0 ? null : str2, str3);
        }

        @NotNull
        public String c() {
            return this.b;
        }

        @Nullable
        public Map<String, Object> a() {
            return this.c;
        }

        @Nullable
        public String b() {
            return this.d;
        }

        @NotNull
        public final String d() {
            return this.e;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Text(@NotNull String role, @Nullable Map<String, ? extends Object> metadata, @Nullable String payload, @NotNull String text) {
            super("text", (DefaultConstructorMarker) null);
            k.e(role, "role");
            k.e(text, "text");
            this.b = role;
            this.c = metadata;
            this.d = payload;
            this.e = text;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: SendMessageRequestDto.kt */
    public static final class FormResponse extends SendMessageDto {
        @NotNull
        private final String b;
        @Nullable
        private final Map<String, Object> c;
        @Nullable
        private final String d;
        @NotNull
        private final List<SendFieldResponseDto> e;
        @NotNull
        private final String f;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FormResponse)) {
                return false;
            }
            FormResponse formResponse = (FormResponse) obj;
            return k.a(e(), formResponse.e()) && k.a(b(), formResponse.b()) && k.a(c(), formResponse.c()) && k.a(this.e, formResponse.e) && k.a(this.f, formResponse.f);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((e().hashCode() * 31) + (b() == null ? 0 : b().hashCode())) * 31;
            if (c() != null) {
                i = c().hashCode();
            }
            return ((((hashCode + i) * 31) + this.e.hashCode()) * 31) + this.f.hashCode();
        }

        @NotNull
        public String toString() {
            return "FormResponse(role=" + e() + ", metadata=" + b() + ", payload=" + c() + ", fields=" + this.e + ", quotedMessageId=" + this.f + ')';
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public /* synthetic */ FormResponse(java.lang.String r8, java.util.Map r9, java.lang.String r10, java.util.List r11, java.lang.String r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
            /*
                r7 = this;
                r14 = r13 & 2
                r0 = 0
                if (r14 == 0) goto L_0x0007
                r3 = r0
                goto L_0x0008
            L_0x0007:
                r3 = r9
            L_0x0008:
                r9 = r13 & 4
                if (r9 == 0) goto L_0x000e
                r4 = r0
                goto L_0x000f
            L_0x000e:
                r4 = r10
            L_0x000f:
                r1 = r7
                r2 = r8
                r5 = r11
                r6 = r12
                r1.<init>(r2, r3, r4, r5, r6)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.rest.model.SendMessageDto.FormResponse.<init>(java.lang.String, java.util.Map, java.lang.String, java.util.List, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        @NotNull
        public String e() {
            return this.b;
        }

        @Nullable
        public Map<String, Object> b() {
            return this.c;
        }

        @Nullable
        public String c() {
            return this.d;
        }

        @NotNull
        public final List<SendFieldResponseDto> a() {
            return this.e;
        }

        @NotNull
        public final String d() {
            return this.f;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public FormResponse(@NotNull String role, @Nullable Map<String, ? extends Object> metadata, @Nullable String payload, @NotNull List<? extends SendFieldResponseDto> fields, @NotNull String quotedMessageId) {
            super("formResponse", (DefaultConstructorMarker) null);
            k.e(role, "role");
            k.e(fields, "fields");
            k.e(quotedMessageId, "quotedMessageId");
            this.b = role;
            this.c = metadata;
            this.d = payload;
            this.e = fields;
            this.f = quotedMessageId;
        }
    }
}
