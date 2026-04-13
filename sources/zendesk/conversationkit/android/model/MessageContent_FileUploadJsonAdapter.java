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

/* compiled from: MessageContent_FileUploadJsonAdapter.kt */
public final class MessageContent_FileUploadJsonAdapter extends f<MessageContent.FileUpload> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Long> c;

    public MessageContent_FileUploadJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("uri", "name", "size", "mimeType");
        k.d(a2, "of(\"uri\", \"name\", \"size\", \"mimeType\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "uri");
        k.d(f, "moshi.adapter(String::cl… emptySet(),\n      \"uri\")");
        this.b = f;
        f<Long> f2 = moshi.f(Long.TYPE, o0.d(), "size");
        k.d(f2, "moshi.adapter(Long::clas…java, emptySet(), \"size\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(47);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageContent.FileUpload");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageContent.FileUpload b(@NotNull i reader) {
        k.e(reader, "reader");
        String uri = null;
        String name = null;
        Long size = null;
        String mimeType = null;
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
                        uri = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("uri", "uri", reader);
                        k.d(u, "unexpectedNull(\"uri\", \"uri\", reader)");
                        throw u;
                    }
                case 1:
                    String b3 = this.b.b(reader);
                    if (b3 != null) {
                        name = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("name", "name", reader);
                        k.d(u2, "unexpectedNull(\"name\", \"name\",\n            reader)");
                        throw u2;
                    }
                case 2:
                    Long b4 = this.c.b(reader);
                    if (b4 != null) {
                        size = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("size", "size", reader);
                        k.d(u3, "unexpectedNull(\"size\", \"size\",\n            reader)");
                        throw u3;
                    }
                case 3:
                    String b5 = this.b.b(reader);
                    if (b5 != null) {
                        mimeType = b5;
                        break;
                    } else {
                        JsonDataException u4 = b.u("mimeType", "mimeType", reader);
                        k.d(u4, "unexpectedNull(\"mimeType…      \"mimeType\", reader)");
                        throw u4;
                    }
            }
        }
        reader.i();
        if (uri == null) {
            JsonDataException l = b.l("uri", "uri", reader);
            k.d(l, "missingProperty(\"uri\", \"uri\", reader)");
            throw l;
        } else if (name == null) {
            JsonDataException l2 = b.l("name", "name", reader);
            k.d(l2, "missingProperty(\"name\", \"name\", reader)");
            throw l2;
        } else if (size != null) {
            long longValue = size.longValue();
            if (mimeType != null) {
                return new MessageContent.FileUpload(uri, name, longValue, mimeType);
            }
            JsonDataException l3 = b.l("mimeType", "mimeType", reader);
            k.d(l3, "missingProperty(\"mimeType\", \"mimeType\", reader)");
            throw l3;
        } else {
            JsonDataException l4 = b.l("size", "size", reader);
            k.d(l4, "missingProperty(\"size\", \"size\", reader)");
            throw l4;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageContent.FileUpload value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("uri");
            this.b.i(writer, value_.e());
            writer.r("name");
            this.b.i(writer, value_.c());
            writer.r("size");
            this.c.i(writer, Long.valueOf(value_.d()));
            writer.r("mimeType");
            this.b.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
