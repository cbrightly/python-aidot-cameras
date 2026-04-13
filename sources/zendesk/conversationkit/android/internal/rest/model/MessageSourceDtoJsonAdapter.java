package zendesk.conversationkit.android.internal.rest.model;

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
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: MessageSourceDtoJsonAdapter.kt */
public final class MessageSourceDtoJsonAdapter extends f<MessageSourceDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;

    public MessageSourceDtoJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", IjkMediaMeta.IJKM_KEY_TYPE, "sessionId");
        k.d(a2, "of(\"id\", \"type\", \"sessionId\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…,\n      emptySet(), \"id\")");
        this.b = f;
        f<String> f2 = moshi.f(cls, o0.d(), IjkMediaMeta.IJKM_KEY_TYPE);
        k.d(f2, "moshi.adapter(String::cl…emptySet(),\n      \"type\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(38);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageSourceDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageSourceDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String id = null;
        String type = null;
        String sessionId = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    id = this.b.b(reader);
                    break;
                case 1:
                    String b2 = this.c.b(reader);
                    if (b2 != null) {
                        type = b2;
                        break;
                    } else {
                        JsonDataException u = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, reader);
                        k.d(u, "unexpectedNull(\"type\", \"type\",\n            reader)");
                        throw u;
                    }
                case 2:
                    sessionId = this.b.b(reader);
                    break;
            }
        }
        reader.i();
        if (type != null) {
            return new MessageSourceDto(id, type, sessionId);
        }
        JsonDataException l = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, reader);
        k.d(l, "missingProperty(\"type\", \"type\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageSourceDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("id");
            this.b.i(writer, value_.a());
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.c.i(writer, value_.c());
            writer.r("sessionId");
            this.b.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
