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

/* compiled from: MessageContent_ImageJsonAdapter.kt */
public final class MessageContent_ImageJsonAdapter extends f<MessageContent.Image> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<Long> d;

    public MessageContent_ImageJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("text", "mediaUrl", "localUri", "mediaType", "mediaSize");
        k.d(a2, "of(\"text\", \"mediaUrl\", \"…\"mediaType\", \"mediaSize\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "text");
        k.d(f, "moshi.adapter(String::cl…emptySet(),\n      \"text\")");
        this.b = f;
        f<String> f2 = moshi.f(cls, o0.d(), "localUri");
        k.d(f2, "moshi.adapter(String::cl…  emptySet(), \"localUri\")");
        this.c = f2;
        f<Long> f3 = moshi.f(Long.TYPE, o0.d(), "mediaSize");
        k.d(f3, "moshi.adapter(Long::clas…Set(),\n      \"mediaSize\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(42);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageContent.Image");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageContent.Image b(@NotNull i reader) {
        k.e(reader, "reader");
        String text = null;
        String mediaUrl = null;
        String localUri = null;
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
                        mediaUrl = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("mediaUrl", "mediaUrl", reader);
                        k.d(u2, "unexpectedNull(\"mediaUrl…      \"mediaUrl\", reader)");
                        throw u2;
                    }
                case 2:
                    localUri = this.c.b(reader);
                    break;
                case 3:
                    String b4 = this.b.b(reader);
                    if (b4 != null) {
                        mediaType = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("mediaType", "mediaType", reader);
                        k.d(u3, "unexpectedNull(\"mediaTyp…     \"mediaType\", reader)");
                        throw u3;
                    }
                case 4:
                    Long b5 = this.d.b(reader);
                    if (b5 != null) {
                        mediaSize = b5;
                        break;
                    } else {
                        JsonDataException u4 = b.u("mediaSize", "mediaSize", reader);
                        k.d(u4, "unexpectedNull(\"mediaSiz…     \"mediaSize\", reader)");
                        throw u4;
                    }
            }
        }
        reader.i();
        if (text == null) {
            JsonDataException l = b.l("text", "text", reader);
            k.d(l, "missingProperty(\"text\", \"text\", reader)");
            throw l;
        } else if (mediaUrl == null) {
            JsonDataException l2 = b.l("mediaUrl", "mediaUrl", reader);
            k.d(l2, "missingProperty(\"mediaUrl\", \"mediaUrl\", reader)");
            throw l2;
        } else if (mediaType == null) {
            JsonDataException l3 = b.l("mediaType", "mediaType", reader);
            k.d(l3, "missingProperty(\"mediaType\", \"mediaType\", reader)");
            throw l3;
        } else if (mediaSize != null) {
            return new MessageContent.Image(text, mediaUrl, localUri, mediaType, mediaSize.longValue());
        } else {
            JsonDataException l4 = b.l("mediaSize", "mediaSize", reader);
            k.d(l4, "missingProperty(\"mediaSize\", \"mediaSize\", reader)");
            throw l4;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageContent.Image value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("text");
            this.b.i(writer, value_.h());
            writer.r("mediaUrl");
            this.b.i(writer, value_.g());
            writer.r("localUri");
            this.c.i(writer, value_.d());
            writer.r("mediaType");
            this.b.i(writer, value_.f());
            writer.r("mediaSize");
            this.d.i(writer, Long.valueOf(value_.e()));
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
