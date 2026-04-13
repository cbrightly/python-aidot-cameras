package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.rest.model.SendMessageDto;

/* compiled from: SendMessageDto_FormResponseJsonAdapter.kt */
public final class SendMessageDto_FormResponseJsonAdapter extends f<SendMessageDto.FormResponse> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Map<String, Object>> c;
    @NotNull
    private final f<String> d;
    @NotNull
    private final f<List<SendFieldResponseDto>> e;
    @Nullable
    private volatile Constructor<SendMessageDto.FormResponse> f;

    public SendMessageDto_FormResponseJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("role", "metadata", "payload", "fields", "quotedMessageId");
        k.d(a2, "of(\"role\", \"metadata\", \"…elds\", \"quotedMessageId\")");
        this.a = a2;
        f<String> f2 = moshi.f(cls, o0.d(), "role");
        k.d(f2, "moshi.adapter(String::cl…emptySet(),\n      \"role\")");
        this.b = f2;
        f<Map<String, Object>> f3 = moshi.f(t.j(Map.class, cls, Object.class), o0.d(), "metadata");
        k.d(f3, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.c = f3;
        f<String> f4 = moshi.f(cls, o0.d(), "payload");
        k.d(f4, "moshi.adapter(String::cl…   emptySet(), \"payload\")");
        this.d = f4;
        f<List<SendFieldResponseDto>> f5 = moshi.f(t.j(List.class, SendFieldResponseDto.class), o0.d(), "fields");
        k.d(f5, "moshi.adapter(Types.newP…    emptySet(), \"fields\")");
        this.e = f5;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(49);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("SendMessageDto.FormResponse");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public SendMessageDto.FormResponse b(@NotNull i reader) {
        String str;
        i iVar = reader;
        Class<String> cls = String.class;
        k.e(iVar, "reader");
        String role = null;
        Map metadata = null;
        String payload = null;
        List fields = null;
        String quotedMessageId = null;
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
                    List b3 = this.e.b(iVar);
                    if (b3 != null) {
                        fields = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("fields", "fields", iVar);
                        k.d(u2, "unexpectedNull(\"fields\", \"fields\", reader)");
                        throw u2;
                    }
                case 4:
                    String b4 = this.b.b(iVar);
                    if (b4 != null) {
                        quotedMessageId = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("quotedMessageId", "quotedMessageId", iVar);
                        k.d(u3, "unexpectedNull(\"quotedMe…quotedMessageId\", reader)");
                        throw u3;
                    }
            }
        }
        reader.i();
        if (mask0 != -7) {
            Constructor it = this.f;
            if (it == null) {
                str = "missingProperty(\"role\", \"role\", reader)";
                it = SendMessageDto.FormResponse.class.getDeclaredConstructor(new Class[]{cls, Map.class, cls, List.class, cls, Integer.TYPE, b.c});
                this.f = it;
                k.d(it, "SendMessageDto.FormRespo…his.constructorRef = it }");
            } else {
                str = "missingProperty(\"role\", \"role\", reader)";
            }
            Constructor localConstructor = it;
            Object[] objArr = new Object[7];
            if (role != null) {
                objArr[0] = role;
                objArr[1] = metadata;
                objArr[2] = payload;
                if (fields != null) {
                    objArr[3] = fields;
                    if (quotedMessageId != null) {
                        objArr[4] = quotedMessageId;
                        objArr[5] = Integer.valueOf(mask0);
                        objArr[6] = null;
                        SendMessageDto.FormResponse newInstance = localConstructor.newInstance(objArr);
                        k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
                        return newInstance;
                    }
                    JsonDataException l = b.l("quotedMessageId", "quotedMessageId", iVar);
                    k.d(l, "missingProperty(\"quotedM…d\",\n              reader)");
                    throw l;
                }
                JsonDataException l2 = b.l("fields", "fields", iVar);
                k.d(l2, "missingProperty(\"fields\", \"fields\", reader)");
                throw l2;
            }
            JsonDataException l3 = b.l("role", "role", iVar);
            k.d(l3, str);
            throw l3;
        } else if (role == null) {
            JsonDataException l4 = b.l("role", "role", iVar);
            k.d(l4, "missingProperty(\"role\", \"role\", reader)");
            throw l4;
        } else if (fields == null) {
            JsonDataException l5 = b.l("fields", "fields", iVar);
            k.d(l5, "missingProperty(\"fields\", \"fields\", reader)");
            throw l5;
        } else if (quotedMessageId != null) {
            return new SendMessageDto.FormResponse(role, metadata, payload, fields, quotedMessageId);
        } else {
            JsonDataException l6 = b.l("quotedMessageId", "quotedMessageId", iVar);
            k.d(l6, "missingProperty(\"quotedM…quotedMessageId\", reader)");
            throw l6;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable SendMessageDto.FormResponse value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("role");
            this.b.i(writer, value_.e());
            writer.r("metadata");
            this.c.i(writer, value_.b());
            writer.r("payload");
            this.d.i(writer, value_.c());
            writer.r("fields");
            this.e.i(writer, value_.a());
            writer.r("quotedMessageId");
            this.b.i(writer, value_.d());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
