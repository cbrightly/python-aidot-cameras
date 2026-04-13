package zendesk.conversationkit.android.model;

import com.google.chip.chiptool.setuppayloadscanner.a;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.moshi.g;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.w;

/* compiled from: Message.kt */
public abstract class MessageContent {
    @NotNull
    private final v a;

    public /* synthetic */ MessageContent(v vVar, DefaultConstructorMarker defaultConstructorMarker) {
        this(vVar);
    }

    private MessageContent(v type) {
        this.a = type;
    }

    @NotNull
    public final v a() {
        return this.a;
    }

    @g(generateAdapter = true)
    /* compiled from: Message.kt */
    public static final class Unsupported extends MessageContent {
        @NotNull
        private final String b;

        public Unsupported() {
            this((String) null, 1, (DefaultConstructorMarker) null);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Unsupported) && k.a(this.b, ((Unsupported) obj).b);
        }

        public int hashCode() {
            return this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "Unsupported(id=" + this.b + ')';
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public /* synthetic */ Unsupported(java.lang.String r1, int r2, kotlin.jvm.internal.DefaultConstructorMarker r3) {
            /*
                r0 = this;
                r2 = r2 & 1
                if (r2 == 0) goto L_0x0011
                java.util.UUID r1 = java.util.UUID.randomUUID()
                java.lang.String r1 = r1.toString()
                java.lang.String r2 = "randomUUID().toString()"
                kotlin.jvm.internal.k.d(r1, r2)
            L_0x0011:
                r0.<init>(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.model.MessageContent.Unsupported.<init>(java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        @NotNull
        public final String b() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Unsupported(@NotNull String id) {
            super(v.UNSUPPORTED, (DefaultConstructorMarker) null);
            k.e(id, "id");
            this.b = id;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: Message.kt */
    public static final class Text extends MessageContent {
        @NotNull
        private final String b;
        @Nullable
        private final List<MessageAction> c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Text)) {
                return false;
            }
            Text text = (Text) obj;
            return k.a(this.b, text.b) && k.a(this.c, text.c);
        }

        public int hashCode() {
            int hashCode = this.b.hashCode() * 31;
            List<MessageAction> list = this.c;
            return hashCode + (list == null ? 0 : list.hashCode());
        }

        @NotNull
        public String toString() {
            return "Text(text=" + this.b + ", actions=" + this.c + ')';
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ Text(String str, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : list);
        }

        @NotNull
        public final String c() {
            return this.b;
        }

        @Nullable
        public final List<MessageAction> b() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Text(@NotNull String text, @Nullable List<? extends MessageAction> actions) {
            super(v.TEXT, (DefaultConstructorMarker) null);
            k.e(text, "text");
            this.b = text;
            this.c = actions;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: Message.kt */
    public static final class FileUpload extends MessageContent {
        @NotNull
        private final String b;
        @NotNull
        private final String c;
        private final long d;
        @NotNull
        private final String e;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FileUpload)) {
                return false;
            }
            FileUpload fileUpload = (FileUpload) obj;
            return k.a(this.b, fileUpload.b) && k.a(this.c, fileUpload.c) && this.d == fileUpload.d && k.a(this.e, fileUpload.e);
        }

        public int hashCode() {
            return (((((this.b.hashCode() * 31) + this.c.hashCode()) * 31) + a.a(this.d)) * 31) + this.e.hashCode();
        }

        @NotNull
        public String toString() {
            return "FileUpload(uri=" + this.b + ", name=" + this.c + ", size=" + this.d + ", mimeType=" + this.e + ')';
        }

        @NotNull
        public final String e() {
            return this.b;
        }

        @NotNull
        public final String c() {
            return this.c;
        }

        public final long d() {
            return this.d;
        }

        @NotNull
        public final String b() {
            return this.e;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public FileUpload(@NotNull String uri, @NotNull String name, long size, @NotNull String mimeType) {
            super(v.FILE_UPLOAD, (DefaultConstructorMarker) null);
            k.e(uri, "uri");
            k.e(name, "name");
            k.e(mimeType, "mimeType");
            this.b = uri;
            this.c = name;
            this.d = size;
            this.e = mimeType;
        }

        public final boolean f() {
            return w.a(this.e);
        }
    }

    @g(generateAdapter = true)
    /* compiled from: Message.kt */
    public static final class File extends MessageContent {
        @NotNull
        private final String b;
        @NotNull
        private final String c;
        @NotNull
        private final String d;
        @NotNull
        private final String e;
        private final long f;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof File)) {
                return false;
            }
            File file = (File) obj;
            return k.a(this.b, file.b) && k.a(this.c, file.c) && k.a(this.d, file.d) && k.a(this.e, file.e) && this.f == file.f;
        }

        public int hashCode() {
            return (((((((this.b.hashCode() * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + a.a(this.f);
        }

        @NotNull
        public String toString() {
            return "File(text=" + this.b + ", altText=" + this.c + ", mediaUrl=" + this.d + ", mediaType=" + this.e + ", mediaSize=" + this.f + ')';
        }

        @NotNull
        public final String f() {
            return this.b;
        }

        @NotNull
        public final String b() {
            return this.c;
        }

        @NotNull
        public final String e() {
            return this.d;
        }

        @NotNull
        public final String d() {
            return this.e;
        }

        public final long c() {
            return this.f;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public File(@NotNull String text, @NotNull String altText, @NotNull String mediaUrl, @NotNull String mediaType, long mediaSize) {
            super(v.FILE, (DefaultConstructorMarker) null);
            k.e(text, "text");
            k.e(altText, "altText");
            k.e(mediaUrl, "mediaUrl");
            k.e(mediaType, "mediaType");
            this.b = text;
            this.c = altText;
            this.d = mediaUrl;
            this.e = mediaType;
            this.f = mediaSize;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: Message.kt */
    public static final class Form extends MessageContent {
        @NotNull
        private final List<Field> b;
        private final boolean c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Form)) {
                return false;
            }
            Form form = (Form) obj;
            return k.a(this.b, form.b) && this.c == form.c;
        }

        public int hashCode() {
            int hashCode = this.b.hashCode() * 31;
            boolean z = this.c;
            if (z) {
                z = true;
            }
            return hashCode + (z ? 1 : 0);
        }

        @NotNull
        public String toString() {
            return "Form(fields=" + this.b + ", blockChatInput=" + this.c + ')';
        }

        @NotNull
        public final List<Field> c() {
            return this.b;
        }

        public final boolean b() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Form(@NotNull List<? extends Field> fields, boolean blockChatInput) {
            super(v.FORM, (DefaultConstructorMarker) null);
            k.e(fields, "fields");
            this.b = fields;
            this.c = blockChatInput;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: Message.kt */
    public static final class FormResponse extends MessageContent {
        @NotNull
        private final String b;
        @NotNull
        private final List<Field> c;

        public static /* synthetic */ FormResponse c(FormResponse formResponse, String str, List<Field> list, int i, Object obj) {
            if ((i & 1) != 0) {
                str = formResponse.b;
            }
            if ((i & 2) != 0) {
                list = formResponse.c;
            }
            return formResponse.b(str, list);
        }

        @NotNull
        public final FormResponse b(@NotNull String str, @NotNull List<? extends Field> list) {
            k.e(str, "quotedMessageId");
            k.e(list, "fields");
            return new FormResponse(str, list);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FormResponse)) {
                return false;
            }
            FormResponse formResponse = (FormResponse) obj;
            return k.a(this.b, formResponse.b) && k.a(this.c, formResponse.c);
        }

        public int hashCode() {
            return (this.b.hashCode() * 31) + this.c.hashCode();
        }

        @NotNull
        public String toString() {
            return "FormResponse(quotedMessageId=" + this.b + ", fields=" + this.c + ')';
        }

        @NotNull
        public final String e() {
            return this.b;
        }

        @NotNull
        public final List<Field> d() {
            return this.c;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public FormResponse(@NotNull String quotedMessageId, @NotNull List<? extends Field> fields) {
            super(v.FORM_RESPONSE, (DefaultConstructorMarker) null);
            k.e(quotedMessageId, "quotedMessageId");
            k.e(fields, "fields");
            this.b = quotedMessageId;
            this.c = fields;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: Message.kt */
    public static final class Carousel extends MessageContent {
        @NotNull
        private final List<MessageItem> b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Carousel) && k.a(this.b, ((Carousel) obj).b);
        }

        public int hashCode() {
            return this.b.hashCode();
        }

        @NotNull
        public String toString() {
            return "Carousel(items=" + this.b + ')';
        }

        @NotNull
        public final List<MessageItem> b() {
            return this.b;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Carousel(@NotNull List<MessageItem> items) {
            super(v.CAROUSEL, (DefaultConstructorMarker) null);
            k.e(items, FirebaseAnalytics.Param.ITEMS);
            this.b = items;
        }
    }

    @g(generateAdapter = true)
    /* compiled from: Message.kt */
    public static final class Image extends MessageContent {
        @NotNull
        private final String b;
        @NotNull
        private final String c;
        @Nullable
        private final String d;
        @NotNull
        private final String e;
        private final long f;

        public static /* synthetic */ Image c(Image image, String str, String str2, String str3, String str4, long j, int i, Object obj) {
            if ((i & 1) != 0) {
                str = image.b;
            }
            if ((i & 2) != 0) {
                str2 = image.c;
            }
            String str5 = str2;
            if ((i & 4) != 0) {
                str3 = image.d;
            }
            String str6 = str3;
            if ((i & 8) != 0) {
                str4 = image.e;
            }
            String str7 = str4;
            if ((i & 16) != 0) {
                j = image.f;
            }
            return image.b(str, str5, str6, str7, j);
        }

        @NotNull
        public final Image b(@NotNull String str, @NotNull String str2, @Nullable String str3, @NotNull String str4, long j) {
            k.e(str, "text");
            k.e(str2, "mediaUrl");
            k.e(str4, "mediaType");
            return new Image(str, str2, str3, str4, j);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Image)) {
                return false;
            }
            Image image = (Image) obj;
            return k.a(this.b, image.b) && k.a(this.c, image.c) && k.a(this.d, image.d) && k.a(this.e, image.e) && this.f == image.f;
        }

        public int hashCode() {
            int hashCode = ((this.b.hashCode() * 31) + this.c.hashCode()) * 31;
            String str = this.d;
            return ((((hashCode + (str == null ? 0 : str.hashCode())) * 31) + this.e.hashCode()) * 31) + a.a(this.f);
        }

        @NotNull
        public String toString() {
            return "Image(text=" + this.b + ", mediaUrl=" + this.c + ", localUri=" + this.d + ", mediaType=" + this.e + ", mediaSize=" + this.f + ')';
        }

        @NotNull
        public final String h() {
            return this.b;
        }

        @NotNull
        public final String g() {
            return this.c;
        }

        @Nullable
        public final String d() {
            return this.d;
        }

        @NotNull
        public final String f() {
            return this.e;
        }

        public final long e() {
            return this.f;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Image(@NotNull String text, @NotNull String mediaUrl, @Nullable String localUri, @NotNull String mediaType, long mediaSize) {
            super(v.IMAGE, (DefaultConstructorMarker) null);
            k.e(text, "text");
            k.e(mediaUrl, "mediaUrl");
            k.e(mediaType, "mediaType");
            this.b = text;
            this.c = mediaUrl;
            this.d = localUri;
            this.e = mediaType;
            this.f = mediaSize;
        }
    }
}
