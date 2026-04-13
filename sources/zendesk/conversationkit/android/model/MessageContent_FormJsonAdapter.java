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

/* compiled from: MessageContent_FormJsonAdapter.kt */
public final class MessageContent_FormJsonAdapter extends f<MessageContent.Form> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<List<Field>> b;
    @NotNull
    private final f<Boolean> c;

    public MessageContent_FormJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("fields", "blockChatInput");
        k.d(a2, "of(\"fields\", \"blockChatInput\")");
        this.a = a2;
        f<List<Field>> f = moshi.f(t.j(List.class, Field.class), o0.d(), "fields");
        k.d(f, "moshi.adapter(Types.newP…ptySet(),\n      \"fields\")");
        this.b = f;
        f<Boolean> f2 = moshi.f(Boolean.TYPE, o0.d(), "blockChatInput");
        k.d(f2, "moshi.adapter(Boolean::c…,\n      \"blockChatInput\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(41);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageContent.Form");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageContent.Form b(@NotNull i reader) {
        k.e(reader, "reader");
        List fields = null;
        Boolean blockChatInput = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    List b2 = this.b.b(reader);
                    if (b2 != null) {
                        fields = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("fields", "fields", reader);
                        k.d(u, "unexpectedNull(\"fields\",…        \"fields\", reader)");
                        throw u;
                    }
                case 1:
                    Boolean b3 = this.c.b(reader);
                    if (b3 != null) {
                        blockChatInput = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("blockChatInput", "blockChatInput", reader);
                        k.d(u2, "unexpectedNull(\"blockCha…\"blockChatInput\", reader)");
                        throw u2;
                    }
            }
        }
        reader.i();
        if (fields == null) {
            JsonDataException l = b.l("fields", "fields", reader);
            k.d(l, "missingProperty(\"fields\", \"fields\", reader)");
            throw l;
        } else if (blockChatInput != null) {
            return new MessageContent.Form(fields, blockChatInput.booleanValue());
        } else {
            JsonDataException l2 = b.l("blockChatInput", "blockChatInput", reader);
            k.d(l2, "missingProperty(\"blockCh…\"blockChatInput\", reader)");
            throw l2;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageContent.Form value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("fields");
            this.b.i(writer, value_.c());
            writer.r("blockChatInput");
            this.c.i(writer, Boolean.valueOf(value_.b()));
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
