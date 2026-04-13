package zendesk.conversationkit.android.internal.rest.model;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.bean.Constants;
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
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: MessageActionDtoJsonAdapter.kt */
public final class MessageActionDtoJsonAdapter extends f<MessageActionDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<Boolean> d;
    @NotNull
    private final f<Map<String, Object>> e;
    @NotNull
    private final f<Long> f;

    public MessageActionDtoJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("_id", IjkMediaMeta.IJKM_KEY_TYPE, "text", "uri", "default", "iconUrl", "fallback", "payload", "metadata", "amount", FirebaseAnalytics.Param.CURRENCY, Constants.ACTION_STATE);
        k.d(a2, "of(\"_id\", \"type\", \"text\"…nt\", \"currency\", \"state\")");
        this.a = a2;
        f<String> f2 = moshi.f(cls, o0.d(), "id");
        k.d(f2, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f2;
        f<String> f3 = moshi.f(cls, o0.d(), "text");
        k.d(f3, "moshi.adapter(String::cl…      emptySet(), \"text\")");
        this.c = f3;
        f<Boolean> f4 = moshi.f(Boolean.class, o0.d(), "default");
        k.d(f4, "moshi.adapter(Boolean::c…e, emptySet(), \"default\")");
        this.d = f4;
        f<Map<String, Object>> f5 = moshi.f(t.j(Map.class, cls, Object.class), o0.d(), "metadata");
        k.d(f5, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.e = f5;
        f<Long> f6 = moshi.f(Long.class, o0.d(), "amount");
        k.d(f6, "moshi.adapter(Long::clas…    emptySet(), \"amount\")");
        this.f = f6;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(38);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageActionDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageActionDto b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String id = null;
        String type = null;
        String text = null;
        String uri = null;
        Boolean bool = null;
        String iconUrl = null;
        String fallback = null;
        String payload = null;
        Map metadata = null;
        Long amount = null;
        String currency = null;
        String state = null;
        reader.c();
        while (true) {
            String state2 = state;
            String currency2 = currency;
            if (reader.l()) {
                switch (iVar.J(this.a)) {
                    case -1:
                        reader.o0();
                        reader.u0();
                        break;
                    case 0:
                        String b2 = this.b.b(iVar);
                        if (b2 != null) {
                            id = b2;
                            state = state2;
                            currency = currency2;
                            continue;
                        } else {
                            JsonDataException u = b.u("id", "_id", iVar);
                            k.d(u, "unexpectedNull(\"id\", \"_id\", reader)");
                            throw u;
                        }
                    case 1:
                        String b3 = this.b.b(iVar);
                        if (b3 != null) {
                            type = b3;
                            state = state2;
                            currency = currency2;
                            continue;
                        } else {
                            JsonDataException u2 = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                            k.d(u2, "unexpectedNull(\"type\", \"type\",\n            reader)");
                            throw u2;
                        }
                    case 2:
                        text = this.c.b(iVar);
                        state = state2;
                        currency = currency2;
                        continue;
                    case 3:
                        uri = this.c.b(iVar);
                        state = state2;
                        currency = currency2;
                        continue;
                    case 4:
                        bool = this.d.b(iVar);
                        state = state2;
                        currency = currency2;
                        continue;
                    case 5:
                        iconUrl = this.c.b(iVar);
                        state = state2;
                        currency = currency2;
                        continue;
                    case 6:
                        fallback = this.c.b(iVar);
                        state = state2;
                        currency = currency2;
                        continue;
                    case 7:
                        payload = this.c.b(iVar);
                        state = state2;
                        currency = currency2;
                        continue;
                    case 8:
                        metadata = this.e.b(iVar);
                        state = state2;
                        currency = currency2;
                        continue;
                    case 9:
                        amount = this.f.b(iVar);
                        state = state2;
                        currency = currency2;
                        continue;
                    case 10:
                        currency = this.c.b(iVar);
                        state = state2;
                        continue;
                    case 11:
                        state = this.c.b(iVar);
                        currency = currency2;
                        continue;
                }
                state = state2;
                currency = currency2;
            } else {
                reader.i();
                if (id == null) {
                    JsonDataException l = b.l("id", "_id", iVar);
                    k.d(l, "missingProperty(\"id\", \"_id\", reader)");
                    throw l;
                } else if (type != null) {
                    return new MessageActionDto(id, type, text, uri, bool, iconUrl, fallback, payload, metadata, amount, currency2, state2);
                } else {
                    JsonDataException l2 = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                    k.d(l2, "missingProperty(\"type\", \"type\", reader)");
                    throw l2;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageActionDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("_id");
            this.b.i(writer, value_.f());
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.b.i(writer, value_.k());
            writer.r("text");
            this.c.i(writer, value_.j());
            writer.r("uri");
            this.c.i(writer, value_.l());
            writer.r("default");
            this.d.i(writer, value_.c());
            writer.r("iconUrl");
            this.c.i(writer, value_.e());
            writer.r("fallback");
            this.c.i(writer, value_.d());
            writer.r("payload");
            this.c.i(writer, value_.h());
            writer.r("metadata");
            this.e.i(writer, value_.g());
            writer.r("amount");
            this.f.i(writer, value_.a());
            writer.r(FirebaseAnalytics.Param.CURRENCY);
            this.c.i(writer, value_.b());
            writer.r(Constants.ACTION_STATE);
            this.c.i(writer, value_.i());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
