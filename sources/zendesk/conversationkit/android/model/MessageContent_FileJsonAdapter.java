package zendesk.conversationkit.android.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.MessageContent;

/* compiled from: MessageContent_FileJsonAdapter.kt */
public final class MessageContent_FileJsonAdapter extends f<MessageContent.File> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Long> c;

    public MessageContent_FileJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("text", "altText", "mediaUrl", "mediaType", "mediaSize");
        k.d(a2, "of(\"text\", \"altText\", \"m…\"mediaType\", \"mediaSize\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "text");
        k.d(f, "moshi.adapter(String::cl…emptySet(),\n      \"text\")");
        this.b = f;
        f<Long> f2 = moshi.f(Long.TYPE, o0.d(), "mediaSize");
        k.d(f2, "moshi.adapter(Long::clas…Set(),\n      \"mediaSize\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(41);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageContent.File");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageContent.File b(@NotNull i reader) {
        k.e(reader, "reader");
        String text = null;
        String altText = null;
        String mediaUrl = null;
        String mediaType = null;
        Long mediaSize = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    String b2 = this.b.b(reader);
                    if (b2 != null) {
                        text = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("text", "text", reader);
                        k.d(u, "unexpectedNull(\"text\", \"text\",\n            reader)");
                        throw u;
                    }
                case 1:
                    String b3 = this.b.b(reader);
                    if (b3 != null) {
                        altText = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("altText", "altText", reader);
                        k.d(u2, "unexpectedNull(\"altText\"…       \"altText\", reader)");
                        throw u2;
                    }
                case 2:
                    String b4 = this.b.b(reader);
                    if (b4 != null) {
                        mediaUrl = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("mediaUrl", "mediaUrl", reader);
                        k.d(u3, "unexpectedNull(\"mediaUrl…      \"mediaUrl\", reader)");
                        throw u3;
                    }
                case 3:
                    String b5 = this.b.b(reader);
                    if (b5 != null) {
                        mediaType = b5;
                        break;
                    } else {
                        JsonDataException u4 = b.u("mediaType", "mediaType", reader);
                        k.d(u4, "unexpectedNull(\"mediaTyp…     \"mediaType\", reader)");
                        throw u4;
                    }
                case 4:
                    Long b6 = this.c.b(reader);
                    if (b6 != null) {
                        mediaSize = b6;
                        break;
                    } else {
                        JsonDataException u5 = b.u("mediaSize", "mediaSize", reader);
                        k.d(u5, "unexpectedNull(\"mediaSiz…     \"mediaSize\", reader)");
                        throw u5;
                    }
            }
        }
        reader.i();
        if (text == null) {
            JsonDataException l = b.l("text", "text", reader);
            k.d(l, "missingProperty(\"text\", \"text\", reader)");
            throw l;
        } else if (altText == null) {
            JsonDataException l2 = b.l("altText", "altText", reader);
            k.d(l2, "missingProperty(\"altText\", \"altText\", reader)");
            throw l2;
        } else if (mediaUrl == null) {
            JsonDataException l3 = b.l("mediaUrl", "mediaUrl", reader);
            k.d(l3, "missingProperty(\"mediaUrl\", \"mediaUrl\", reader)");
            throw l3;
        } else if (mediaType == null) {
            JsonDataException l4 = b.l("mediaType", "mediaType", reader);
            k.d(l4, "missingProperty(\"mediaType\", \"mediaType\", reader)");
            throw l4;
        } else if (mediaSize != null) {
            return new MessageContent.File(text, altText, mediaUrl, mediaType, mediaSize.longValue());
        } else {
            JsonDataException l5 = b.l("mediaSize", "mediaSize", reader);
            k.d(l5, "missingProperty(\"mediaSize\", \"mediaSize\", reader)");
            throw l5;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageContent.File value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("text");
            this.b.i(writer, value_.f());
            writer.r("altText");
            this.b.i(writer, value_.b());
            writer.r("mediaUrl");
            this.b.i(writer, value_.e());
            writer.r("mediaType");
            this.b.i(writer, value_.d());
            writer.r("mediaSize");
            this.c.i(writer, Long.valueOf(value_.c()));
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
