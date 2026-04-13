package zendesk.conversationkit.android.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.util.List;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.MessageContent;

/* compiled from: MessageContent_FormResponseJsonAdapter.kt */
public final class MessageContent_FormResponseJsonAdapter extends f<MessageContent.FormResponse> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<List<Field>> c;

    public MessageContent_FormResponseJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("quotedMessageId", "fields");
        k.d(a2, "of(\"quotedMessageId\", \"fields\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "quotedMessageId");
        k.d(f, "moshi.adapter(String::cl…\n      \"quotedMessageId\")");
        this.b = f;
        f<List<Field>> f2 = moshi.f(t.j(List.class, Field.class), o0.d(), "fields");
        k.d(f2, "moshi.adapter(Types.newP…ptySet(),\n      \"fields\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(49);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageContent.FormResponse");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageContent.FormResponse b(@NotNull i reader) {
        k.e(reader, "reader");
        String quotedMessageId = null;
        List fields = null;
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
                        quotedMessageId = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("quotedMessageId", "quotedMessageId", reader);
                        k.d(u, "unexpectedNull(\"quotedMe…quotedMessageId\", reader)");
                        throw u;
                    }
                case 1:
                    List b3 = this.c.b(reader);
                    if (b3 != null) {
                        fields = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("fields", "fields", reader);
                        k.d(u2, "unexpectedNull(\"fields\",…        \"fields\", reader)");
                        throw u2;
                    }
            }
        }
        reader.i();
        if (quotedMessageId == null) {
            JsonDataException l = b.l("quotedMessageId", "quotedMessageId", reader);
            k.d(l, "missingProperty(\"quotedM…quotedMessageId\", reader)");
            throw l;
        } else if (fields != null) {
            return new MessageContent.FormResponse(quotedMessageId, fields);
        } else {
            JsonDataException l2 = b.l("fields", "fields", reader);
            k.d(l2, "missingProperty(\"fields\", \"fields\", reader)");
            throw l2;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageContent.FormResponse value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("quotedMessageId");
            this.b.i(writer, value_.e());
            writer.r("fields");
            this.c.i(writer, value_.d());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
