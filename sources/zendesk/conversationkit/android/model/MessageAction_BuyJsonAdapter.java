package zendesk.conversationkit.android.model;

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
import zendesk.conversationkit.android.model.MessageAction;

/* compiled from: MessageAction_BuyJsonAdapter.kt */
public final class MessageAction_BuyJsonAdapter extends f<MessageAction.Buy> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Map<String, Object>> c;
    @NotNull
    private final f<Long> d;
    @NotNull
    private final f<o> e;

    public MessageAction_BuyJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", "metadata", "text", "uri", "amount", FirebaseAnalytics.Param.CURRENCY, Constants.ACTION_STATE);
        k.d(a2, "of(\"id\", \"metadata\", \"te…nt\", \"currency\", \"state\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
        f<Map<String, Object>> f2 = moshi.f(t.j(Map.class, cls, Object.class), o0.d(), "metadata");
        k.d(f2, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.c = f2;
        f<Long> f3 = moshi.f(Long.TYPE, o0.d(), "amount");
        k.d(f3, "moshi.adapter(Long::clas…va, emptySet(), \"amount\")");
        this.d = f3;
        f<o> f4 = moshi.f(o.class, o0.d(), Constants.ACTION_STATE);
        k.d(f4, "moshi.adapter(MessageAct…ava, emptySet(), \"state\")");
        this.e = f4;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(39);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageAction.Buy");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageAction.Buy b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String id = null;
        Map metadata = null;
        String text = null;
        String uri = null;
        Long amount = null;
        String currency = null;
        o state = null;
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
                        id = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("id", "id", iVar);
                        k.d(u, "unexpectedNull(\"id\", \"id\", reader)");
                        throw u;
                    }
                case 1:
                    metadata = this.c.b(iVar);
                    break;
                case 2:
                    String b3 = this.b.b(iVar);
                    if (b3 != null) {
                        text = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("text", "text", iVar);
                        k.d(u2, "unexpectedNull(\"text\", \"text\",\n            reader)");
                        throw u2;
                    }
                case 3:
                    String b4 = this.b.b(iVar);
                    if (b4 != null) {
                        uri = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("uri", "uri", iVar);
                        k.d(u3, "unexpectedNull(\"uri\", \"uri\", reader)");
                        throw u3;
                    }
                case 4:
                    Long b5 = this.d.b(iVar);
                    if (b5 != null) {
                        amount = b5;
                        break;
                    } else {
                        JsonDataException u4 = b.u("amount", "amount", iVar);
                        k.d(u4, "unexpectedNull(\"amount\",…unt\",\n            reader)");
                        throw u4;
                    }
                case 5:
                    String b6 = this.b.b(iVar);
                    if (b6 != null) {
                        currency = b6;
                        break;
                    } else {
                        JsonDataException u5 = b.u(FirebaseAnalytics.Param.CURRENCY, FirebaseAnalytics.Param.CURRENCY, iVar);
                        k.d(u5, "unexpectedNull(\"currency…      \"currency\", reader)");
                        throw u5;
                    }
                case 6:
                    o b7 = this.e.b(iVar);
                    if (b7 != null) {
                        state = b7;
                        break;
                    } else {
                        JsonDataException u6 = b.u(Constants.ACTION_STATE, Constants.ACTION_STATE, iVar);
                        k.d(u6, "unexpectedNull(\"state\", \"state\", reader)");
                        throw u6;
                    }
            }
        }
        reader.i();
        if (id == null) {
            JsonDataException l = b.l("id", "id", iVar);
            k.d(l, "missingProperty(\"id\", \"id\", reader)");
            throw l;
        } else if (text == null) {
            JsonDataException l2 = b.l("text", "text", iVar);
            k.d(l2, "missingProperty(\"text\", \"text\", reader)");
            throw l2;
        } else if (uri == null) {
            JsonDataException l3 = b.l("uri", "uri", iVar);
            k.d(l3, "missingProperty(\"uri\", \"uri\", reader)");
            throw l3;
        } else if (amount != null) {
            long longValue = amount.longValue();
            if (currency == null) {
                JsonDataException l4 = b.l(FirebaseAnalytics.Param.CURRENCY, FirebaseAnalytics.Param.CURRENCY, iVar);
                k.d(l4, "missingProperty(\"currency\", \"currency\", reader)");
                throw l4;
            } else if (state != null) {
                return new MessageAction.Buy(id, metadata, text, uri, longValue, currency, state);
            } else {
                JsonDataException l5 = b.l(Constants.ACTION_STATE, Constants.ACTION_STATE, iVar);
                k.d(l5, "missingProperty(\"state\", \"state\", reader)");
                throw l5;
            }
        } else {
            JsonDataException l6 = b.l("amount", "amount", iVar);
            k.d(l6, "missingProperty(\"amount\", \"amount\", reader)");
            throw l6;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageAction.Buy value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("id");
            this.b.i(writer, value_.c());
            writer.r("metadata");
            this.c.i(writer, value_.d());
            writer.r("text");
            this.b.i(writer, value_.f());
            writer.r("uri");
            this.b.i(writer, value_.g());
            writer.r("amount");
            this.d.i(writer, Long.valueOf(value_.a()));
            writer.r(FirebaseAnalytics.Param.CURRENCY);
            this.b.i(writer, value_.b());
            writer.r(Constants.ACTION_STATE);
            this.e.i(writer, value_.e());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
