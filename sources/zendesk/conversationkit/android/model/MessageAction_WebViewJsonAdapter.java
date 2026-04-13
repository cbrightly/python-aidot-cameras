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

/* compiled from: MessageAction_WebViewJsonAdapter.kt */
public final class MessageAction_WebViewJsonAdapter extends f<MessageAction.WebView> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Map<String, Object>> c;
    @NotNull
    private final f<Boolean> d;

    public MessageAction_WebViewJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", "metadata", "text", "uri", "fallback", "default");
        k.d(a2, "of(\"id\", \"metadata\", \"te…   \"fallback\", \"default\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
        f<Map<String, Object>> f2 = moshi.f(t.j(Map.class, cls, Object.class), o0.d(), "metadata");
        k.d(f2, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.c = f2;
        f<Boolean> f3 = moshi.f(Boolean.TYPE, o0.d(), "default");
        k.d(f3, "moshi.adapter(Boolean::c…tySet(),\n      \"default\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(43);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageAction.WebView");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageAction.WebView b(@NotNull i reader) {
        k.e(reader, "reader");
        String id = null;
        Map metadata = null;
        String text = null;
        String uri = null;
        String fallback = null;
        Boolean bool = null;
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
                case 2:
                    String b3 = this.b.b(reader);
                    if (b3 != null) {
                        text = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("text", "text", reader);
                        k.d(u2, "unexpectedNull(\"text\", \"text\",\n            reader)");
                        throw u2;
                    }
                case 3:
                    String b4 = this.b.b(reader);
                    if (b4 != null) {
                        uri = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("uri", "uri", reader);
                        k.d(u3, "unexpectedNull(\"uri\", \"uri\", reader)");
                        throw u3;
                    }
                case 4:
                    String b5 = this.b.b(reader);
                    if (b5 != null) {
                        fallback = b5;
                        break;
                    } else {
                        JsonDataException u4 = b.u("fallback", "fallback", reader);
                        k.d(u4, "unexpectedNull(\"fallback…      \"fallback\", reader)");
                        throw u4;
                    }
                case 5:
                    Boolean b6 = this.d.b(reader);
                    if (b6 != null) {
                        bool = b6;
                        break;
                    } else {
                        JsonDataException u5 = b.u("default", "default", reader);
                        k.d(u5, "unexpectedNull(\"default\"…       \"default\", reader)");
                        throw u5;
                    }
            }
        }
        reader.i();
        if (id == null) {
            JsonDataException l = b.l("id", "id", reader);
            k.d(l, "missingProperty(\"id\", \"id\", reader)");
            throw l;
        } else if (text == null) {
            JsonDataException l2 = b.l("text", "text", reader);
            k.d(l2, "missingProperty(\"text\", \"text\", reader)");
            throw l2;
        } else if (uri == null) {
            JsonDataException l3 = b.l("uri", "uri", reader);
            k.d(l3, "missingProperty(\"uri\", \"uri\", reader)");
            throw l3;
        } else if (fallback == null) {
            JsonDataException l4 = b.l("fallback", "fallback", reader);
            k.d(l4, "missingProperty(\"fallback\", \"fallback\", reader)");
            throw l4;
        } else if (bool != null) {
            return new MessageAction.WebView(id, metadata, text, uri, fallback, bool.booleanValue());
        } else {
            JsonDataException l5 = b.l("default", "default", reader);
            k.d(l5, "missingProperty(\"default\", \"default\", reader)");
            throw l5;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageAction.WebView value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("id");
            this.b.i(writer, value_.c());
            writer.r("metadata");
            this.c.i(writer, value_.d());
            writer.r("text");
            this.b.i(writer, value_.e());
            writer.r("uri");
            this.b.i(writer, value_.f());
            writer.r("fallback");
            this.b.i(writer, value_.b());
            writer.r("default");
            this.d.i(writer, Boolean.valueOf(value_.a()));
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
