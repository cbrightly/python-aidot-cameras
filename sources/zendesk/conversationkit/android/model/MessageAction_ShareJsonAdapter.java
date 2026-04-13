package zendesk.conversationkit.android.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.util.Map;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.MessageAction;

/* compiled from: MessageAction_ShareJsonAdapter.kt */
public final class MessageAction_ShareJsonAdapter extends f<MessageAction.Share> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Map<String, Object>> c;

    public MessageAction_ShareJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", "metadata");
        k.d(a2, "of(\"id\", \"metadata\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
        f<Map<String, Object>> f2 = moshi.f(t.j(Map.class, cls, Object.class), o0.d(), "metadata");
        k.d(f2, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(41);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageAction.Share");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageAction.Share b(@NotNull i reader) {
        k.e(reader, "reader");
        String id = null;
        Map metadata = null;
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
                        id = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("id", "id", reader);
                        k.d(u, "unexpectedNull(\"id\", \"id\", reader)");
                        throw u;
                    }
                case 1:
                    metadata = this.c.b(reader);
                    break;
            }
        }
        reader.i();
        if (id != null) {
            return new MessageAction.Share(id, metadata);
        }
        JsonDataException l = b.l("id", "id", reader);
        k.d(l, "missingProperty(\"id\", \"id\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageAction.Share value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("id");
            this.b.i(writer, value_.a());
            writer.r("metadata");
            this.c.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
