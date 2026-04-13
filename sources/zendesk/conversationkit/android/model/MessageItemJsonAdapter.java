package zendesk.conversationkit.android.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.util.List;
import java.util.Map;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessageItemJsonAdapter.kt */
public final class MessageItemJsonAdapter extends f<MessageItem> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<List<MessageAction>> d;
    @NotNull
    private final f<s> e;
    @NotNull
    private final f<Map<String, Object>> f;

    public MessageItemJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("title", "description", "actions", "size", "metadata", "mediaUrl", "mediaType");
        k.d(a2, "of(\"title\", \"description… \"mediaUrl\", \"mediaType\")");
        this.a = a2;
        f<String> f2 = moshi.f(cls, o0.d(), "title");
        k.d(f2, "moshi.adapter(String::cl…mptySet(),\n      \"title\")");
        this.b = f2;
        f<String> f3 = moshi.f(cls, o0.d(), "description");
        k.d(f3, "moshi.adapter(String::cl…mptySet(), \"description\")");
        this.c = f3;
        f<List<MessageAction>> f4 = moshi.f(t.j(List.class, MessageAction.class), o0.d(), "actions");
        k.d(f4, "moshi.adapter(Types.newP…   emptySet(), \"actions\")");
        this.d = f4;
        f<s> f5 = moshi.f(s.class, o0.d(), "size");
        k.d(f5, "moshi.adapter(MessageIte…java, emptySet(), \"size\")");
        this.e = f5;
        f<Map<String, Object>> f6 = moshi.f(t.j(Map.class, cls, Object.class), o0.d(), "metadata");
        k.d(f6, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.f = f6;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(33);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageItem");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageItem b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String title = null;
        String description = null;
        List actions = null;
        s size = null;
        Map metadata = null;
        String mediaUrl = null;
        String mediaType = null;
        reader.c();
        while (reader.l()) {
            switch (iVar.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    String b2 = this.b.b(iVar);
                    if (b2 != null) {
                        title = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("title", "title", iVar);
                        k.d(u, "unexpectedNull(\"title\", …tle\",\n            reader)");
                        throw u;
                    }
                case 1:
                    description = this.c.b(iVar);
                    break;
                case 2:
                    List b3 = this.d.b(iVar);
                    if (b3 != null) {
                        actions = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("actions", "actions", iVar);
                        k.d(u2, "unexpectedNull(\"actions\", \"actions\", reader)");
                        throw u2;
                    }
                case 3:
                    s b4 = this.e.b(iVar);
                    if (b4 != null) {
                        size = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("size", "size", iVar);
                        k.d(u3, "unexpectedNull(\"size\",\n            \"size\", reader)");
                        throw u3;
                    }
                case 4:
                    metadata = this.f.b(iVar);
                    break;
                case 5:
                    mediaUrl = this.c.b(iVar);
                    break;
                case 6:
                    mediaType = this.c.b(iVar);
                    break;
            }
        }
        reader.i();
        if (title == null) {
            JsonDataException l = b.l("title", "title", iVar);
            k.d(l, "missingProperty(\"title\", \"title\", reader)");
            throw l;
        } else if (actions == null) {
            JsonDataException l2 = b.l("actions", "actions", iVar);
            k.d(l2, "missingProperty(\"actions\", \"actions\", reader)");
            throw l2;
        } else if (size != null) {
            return new MessageItem(title, description, actions, size, metadata, mediaUrl, mediaType);
        } else {
            JsonDataException l3 = b.l("size", "size", iVar);
            k.d(l3, "missingProperty(\"size\", \"size\", reader)");
            throw l3;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageItem value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("title");
            this.b.i(writer, value_.g());
            writer.r("description");
            this.c.i(writer, value_.b());
            writer.r("actions");
            this.d.i(writer, value_.a());
            writer.r("size");
            this.e.i(writer, value_.f());
            writer.r("metadata");
            this.f.i(writer, value_.e());
            writer.r("mediaUrl");
            this.c.i(writer, value_.d());
            writer.r("mediaType");
            this.c.i(writer, value_.c());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
