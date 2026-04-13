package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import kotlin.collections.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
import zendesk.conversationkit.android.model.k;

/* compiled from: CreateConversationRequestDtoJsonAdapter.kt */
public final class CreateConversationRequestDtoJsonAdapter extends f<CreateConversationRequestDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<k> b;
    @NotNull
    private final f<b> c;
    @NotNull
    private final f<ClientDto> d;

    public CreateConversationRequestDtoJsonAdapter(@NotNull r moshi) {
        kotlin.jvm.internal.k.e(moshi, "moshi");
        i.a a2 = i.a.a(IjkMediaMeta.IJKM_KEY_TYPE, "intent", "client");
        kotlin.jvm.internal.k.d(a2, "of(\"type\", \"intent\", \"client\")");
        this.a = a2;
        f<k> f = moshi.f(k.class, o0.d(), IjkMediaMeta.IJKM_KEY_TYPE);
        kotlin.jvm.internal.k.d(f, "moshi.adapter(Conversati…java, emptySet(), \"type\")");
        this.b = f;
        f<b> f2 = moshi.f(b.class, o0.d(), "intent");
        kotlin.jvm.internal.k.d(f2, "moshi.adapter(Intent::cl…ptySet(),\n      \"intent\")");
        this.c = f2;
        f<ClientDto> f3 = moshi.f(ClientDto.class, o0.d(), "client");
        kotlin.jvm.internal.k.d(f3, "moshi.adapter(ClientDto:…    emptySet(), \"client\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(50);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("CreateConversationRequestDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        kotlin.jvm.internal.k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public CreateConversationRequestDto b(@NotNull i reader) {
        kotlin.jvm.internal.k.e(reader, "reader");
        k type = null;
        b intent = null;
        ClientDto client = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    k b2 = this.b.b(reader);
                    if (b2 != null) {
                        type = b2;
                        break;
                    } else {
                        JsonDataException u = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, reader);
                        kotlin.jvm.internal.k.d(u, "unexpectedNull(\"type\",\n            \"type\", reader)");
                        throw u;
                    }
                case 1:
                    b b3 = this.c.b(reader);
                    if (b3 != null) {
                        intent = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("intent", "intent", reader);
                        kotlin.jvm.internal.k.d(u2, "unexpectedNull(\"intent\",…        \"intent\", reader)");
                        throw u2;
                    }
                case 2:
                    ClientDto b4 = this.d.b(reader);
                    if (b4 != null) {
                        client = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("client", "client", reader);
                        kotlin.jvm.internal.k.d(u3, "unexpectedNull(\"client\",…        \"client\", reader)");
                        throw u3;
                    }
            }
        }
        reader.i();
        if (type == null) {
            JsonDataException l = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, reader);
            kotlin.jvm.internal.k.d(l, "missingProperty(\"type\", \"type\", reader)");
            throw l;
        } else if (intent == null) {
            JsonDataException l2 = b.l("intent", "intent", reader);
            kotlin.jvm.internal.k.d(l2, "missingProperty(\"intent\", \"intent\", reader)");
            throw l2;
        } else if (client != null) {
            return new CreateConversationRequestDto(type, intent, client);
        } else {
            JsonDataException l3 = b.l("client", "client", reader);
            kotlin.jvm.internal.k.d(l3, "missingProperty(\"client\", \"client\", reader)");
            throw l3;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable CreateConversationRequestDto value_) {
        kotlin.jvm.internal.k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.b.i(writer, value_.c());
            writer.r("intent");
            this.c.i(writer, value_.b());
            writer.r("client");
            this.d.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
