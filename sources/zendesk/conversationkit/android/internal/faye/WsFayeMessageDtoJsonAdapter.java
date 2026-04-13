package zendesk.conversationkit.android.internal.faye;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import java.lang.reflect.Constructor;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
import zendesk.conversationkit.android.internal.rest.model.MessageDto;

/* compiled from: WsFayeMessageDtoJsonAdapter.kt */
public final class WsFayeMessageDtoJsonAdapter extends f<WsFayeMessageDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<WsConversationDto> c;
    @NotNull
    private final f<MessageDto> d;
    @NotNull
    private final f<WsActivityEventDto> e;
    @Nullable
    private volatile Constructor<WsFayeMessageDto> f;

    public WsFayeMessageDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a(IjkMediaMeta.IJKM_KEY_TYPE, "conversation", "message", "activity");
        k.d(a2, "of(\"type\", \"conversation…ssage\",\n      \"activity\")");
        this.a = a2;
        f<String> f2 = moshi.f(String.class, o0.d(), IjkMediaMeta.IJKM_KEY_TYPE);
        k.d(f2, "moshi.adapter(String::cl…emptySet(),\n      \"type\")");
        this.b = f2;
        f<WsConversationDto> f3 = moshi.f(WsConversationDto.class, o0.d(), "conversation");
        k.d(f3, "moshi.adapter(WsConversa…ptySet(), \"conversation\")");
        this.c = f3;
        f<MessageDto> f4 = moshi.f(MessageDto.class, o0.d(), "message");
        k.d(f4, "moshi.adapter(MessageDto…a, emptySet(), \"message\")");
        this.d = f4;
        f<WsActivityEventDto> f5 = moshi.f(WsActivityEventDto.class, o0.d(), "activity");
        k.d(f5, "moshi.adapter(WsActivity…, emptySet(), \"activity\")");
        this.e = f5;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(38);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("WsFayeMessageDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public WsFayeMessageDto b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String type = null;
        WsConversationDto conversation = null;
        MessageDto message = null;
        WsActivityEventDto activity = null;
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
                        type = b2;
                        break;
                    } else {
                        JsonDataException u = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                        k.d(u, "unexpectedNull(\"type\", \"type\",\n            reader)");
                        throw u;
                    }
                case 1:
                    WsConversationDto b3 = this.c.b(iVar);
                    if (b3 != null) {
                        conversation = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("conversation", "conversation", iVar);
                        k.d(u2, "unexpectedNull(\"conversa…, \"conversation\", reader)");
                        throw u2;
                    }
                case 2:
                    message = this.d.b(iVar);
                    mask0 &= -5;
                    break;
                case 3:
                    activity = this.e.b(iVar);
                    mask0 &= -9;
                    break;
            }
        }
        reader.i();
        if (mask0 != -13) {
            Constructor localConstructor = this.f;
            if (localConstructor == null) {
                localConstructor = WsFayeMessageDto.class.getDeclaredConstructor(new Class[]{String.class, WsConversationDto.class, MessageDto.class, WsActivityEventDto.class, Integer.TYPE, b.c});
                this.f = localConstructor;
                k.d(localConstructor, "WsFayeMessageDto::class.…his.constructorRef = it }");
            }
            Object[] objArr = new Object[6];
            if (type != null) {
                objArr[0] = type;
                if (conversation != null) {
                    objArr[1] = conversation;
                    objArr[2] = message;
                    objArr[3] = activity;
                    objArr[4] = Integer.valueOf(mask0);
                    objArr[5] = null;
                    WsFayeMessageDto newInstance = localConstructor.newInstance(objArr);
                    k.d(newInstance, "localConstructor.newInst…torMarker */ null\n      )");
                    return newInstance;
                }
                JsonDataException l = b.l("conversation", "conversation", iVar);
                k.d(l, "missingProperty(\"convers…, \"conversation\", reader)");
                throw l;
            }
            JsonDataException l2 = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
            k.d(l2, "missingProperty(\"type\", \"type\", reader)");
            throw l2;
        } else if (type == null) {
            JsonDataException l3 = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
            k.d(l3, "missingProperty(\"type\", \"type\", reader)");
            throw l3;
        } else if (conversation != null) {
            return new WsFayeMessageDto(type, conversation, message, activity);
        } else {
            JsonDataException l4 = b.l("conversation", "conversation", iVar);
            k.d(l4, "missingProperty(\"convers…n\",\n              reader)");
            throw l4;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable WsFayeMessageDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.b.i(writer, value_.d());
            writer.r("conversation");
            this.c.i(writer, value_.b());
            writer.r("message");
            this.d.i(writer, value_.c());
            writer.r("activity");
            this.e.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
