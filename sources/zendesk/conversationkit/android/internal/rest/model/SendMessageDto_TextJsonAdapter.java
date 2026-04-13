package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.lang.reflect.Constructor;
import java.util.Map;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.rest.model.SendMessageDto;

/* compiled from: SendMessageDto_TextJsonAdapter.kt */
public final class SendMessageDto_TextJsonAdapter extends f<SendMessageDto.Text> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Map<String, Object>> c;
    @NotNull
    private final f<String> d;
    @Nullable
    private volatile Constructor<SendMessageDto.Text> e;

    public SendMessageDto_TextJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("role", "metadata", "payload", "text");
        k.d(a2, "of(\"role\", \"metadata\", \"payload\",\n      \"text\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "role");
        k.d(f, "moshi.adapter(String::cl…emptySet(),\n      \"role\")");
        this.b = f;
        f<Map<String, Object>> f2 = moshi.f(t.j(Map.class, cls, Object.class), o0.d(), "metadata");
        k.d(f2, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.c = f2;
        f<String> f3 = moshi.f(cls, o0.d(), "payload");
        k.d(f3, "moshi.adapter(String::cl…   emptySet(), \"payload\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(41);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("SendMessageDto.Text");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public SendMessageDto.Text b(@NotNull i reader) {
        i iVar = reader;
        Class<String> cls = String.class;
        k.e(iVar, "reader");
        String role = null;
        Map metadata = null;
        String payload = null;
        String text = null;
        int mask0 = -1;
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
                        role = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("role", "role", iVar);
                        k.d(u, "unexpectedNull(\"role\", \"role\",\n            reader)");
                        throw u;
                    }
                case 1:
                    metadata = this.c.b(iVar);
                    mask0 &= -3;
                    break;
                case 2:
                    payload = this.d.b(iVar);
                    mask0 &= -5;
                    break;
                case 3:
                    String b3 = this.b.b(iVar);
                    if (b3 != null) {
                        text = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("text", "text", iVar);
                        k.d(u2, "unexpectedNull(\"text\", \"text\",\n            reader)");
                        throw u2;
                    }
            }
        }
        reader.i();
        if (mask0 != -7) {
            Constructor it = this.e;
            if (it == null) {
                it = SendMessageDto.Text.class.getDeclaredConstructor(new Class[]{cls, Map.class, cls, cls, Integer.TYPE, b.c});
                this.e = it;
                k.d(it, "SendMessageDto.Text::cla…his.constructorRef = it }");
            }
            Constructor localConstructor = it;
            Object[] objArr = new Object[6];
            if (role != null) {
                objArr[0] = role;
                objArr[1] = metadata;
                objArr[2] = payload;
                if (text != null) {
                    objArr[3] = text;
                    objArr[4] = Integer.valueOf(mask0);
                    objArr[5] = null;
                    SendMessageDto.Text newInstance = localConstructor.newInstance(objArr);
                    k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
                    return newInstance;
                }
                JsonDataException l = b.l("text", "text", iVar);
                k.d(l, "missingProperty(\"text\", \"text\", reader)");
                throw l;
            }
            JsonDataException l2 = b.l("role", "role", iVar);
            k.d(l2, "missingProperty(\"role\", \"role\", reader)");
            throw l2;
        } else if (role == null) {
            JsonDataException l3 = b.l("role", "role", iVar);
            k.d(l3, "missingProperty(\"role\", \"role\", reader)");
            throw l3;
        } else if (text != null) {
            return new SendMessageDto.Text(role, metadata, payload, text);
        } else {
            JsonDataException l4 = b.l("text", "text", iVar);
            k.d(l4, "missingProperty(\"text\", \"text\", reader)");
            throw l4;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable SendMessageDto.Text value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("role");
            this.b.i(writer, value_.c());
            writer.r("metadata");
            this.c.i(writer, value_.a());
            writer.r("payload");
            this.d.i(writer, value_.b());
            writer.r("text");
            this.b.i(writer, value_.d());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
