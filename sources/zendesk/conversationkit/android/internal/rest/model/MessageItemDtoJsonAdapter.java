package zendesk.conversationkit.android.internal.rest.model;

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

/* compiled from: MessageItemDtoJsonAdapter.kt */
public final class MessageItemDtoJsonAdapter extends f<MessageItemDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<List<MessageActionDto>> d;
    @NotNull
    private final f<Map<String, Object>> e;

    public MessageItemDtoJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("title", "description", "actions", "size", "metadata", "mediaUrl", "mediaType");
        k.d(a2, "of(\"title\", \"description… \"mediaUrl\", \"mediaType\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "title");
        k.d(f, "moshi.adapter(String::cl…mptySet(),\n      \"title\")");
        this.b = f;
        f<String> f2 = moshi.f(cls, o0.d(), "description");
        k.d(f2, "moshi.adapter(String::cl…mptySet(), \"description\")");
        this.c = f2;
        f<List<MessageActionDto>> f3 = moshi.f(t.j(List.class, MessageActionDto.class), o0.d(), "actions");
        k.d(f3, "moshi.adapter(Types.newP…   emptySet(), \"actions\")");
        this.d = f3;
        f<Map<String, Object>> f4 = moshi.f(t.j(Map.class, cls, Object.class), o0.d(), "metadata");
        k.d(f4, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.e = f4;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(36);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageItemDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageItemDto b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String title = null;
        String description = null;
        List actions = null;
        String size = null;
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
                    size = this.c.b(iVar);
                    break;
                case 4:
                    metadata = this.e.b(iVar);
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
        } else if (actions != null) {
            return new MessageItemDto(title, description, actions, size, metadata, mediaUrl, mediaType);
        } else {
            JsonDataException l2 = b.l("actions", "actions", iVar);
            k.d(l2, "missingProperty(\"actions\", \"actions\", reader)");
            throw l2;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageItemDto value_) {
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
            this.c.i(writer, value_.f());
            writer.r("metadata");
            this.e.i(writer, value_.e());
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
