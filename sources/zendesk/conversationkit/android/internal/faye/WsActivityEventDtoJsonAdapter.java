package zendesk.conversationkit.android.internal.faye;

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

/* compiled from: WsActivityEventDtoJsonAdapter.kt */
public final class WsActivityEventDtoJsonAdapter extends f<WsActivityEventDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<WsActivityEventDataDto> d;

    public WsActivityEventDtoJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("role", IjkMediaMeta.IJKM_KEY_TYPE, "appUserId", "data");
        k.d(a2, "of(\"role\", \"type\", \"appUserId\",\n      \"data\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "role");
        k.d(f, "moshi.adapter(String::cl…emptySet(),\n      \"role\")");
        this.b = f;
        f<String> f2 = moshi.f(cls, o0.d(), "appUserId");
        k.d(f2, "moshi.adapter(String::cl… emptySet(), \"appUserId\")");
        this.c = f2;
        f<WsActivityEventDataDto> f3 = moshi.f(WsActivityEventDataDto.class, o0.d(), "data");
        k.d(f3, "moshi.adapter(WsActivity…java, emptySet(), \"data\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("WsActivityEventDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public WsActivityEventDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String role = null;
        String type = null;
        String appUserId = null;
        WsActivityEventDataDto data_ = null;
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
                        role = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("role", "role", reader);
                        k.d(u, "unexpectedNull(\"role\", \"role\",\n            reader)");
                        throw u;
                    }
                case 1:
                    String b3 = this.b.b(reader);
                    if (b3 != null) {
                        type = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, reader);
                        k.d(u2, "unexpectedNull(\"type\", \"type\",\n            reader)");
                        throw u2;
                    }
                case 2:
                    appUserId = this.c.b(reader);
                    break;
                case 3:
                    WsActivityEventDataDto b4 = this.d.b(reader);
                    if (b4 != null) {
                        data_ = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("data_", "data", reader);
                        k.d(u3, "unexpectedNull(\"data_\", \"data\", reader)");
                        throw u3;
                    }
            }
        }
        reader.i();
        if (role == null) {
            JsonDataException l = b.l("role", "role", reader);
            k.d(l, "missingProperty(\"role\", \"role\", reader)");
            throw l;
        } else if (type == null) {
            JsonDataException l2 = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, reader);
            k.d(l2, "missingProperty(\"type\", \"type\", reader)");
            throw l2;
        } else if (data_ != null) {
            return new WsActivityEventDto(role, type, appUserId, data_);
        } else {
            JsonDataException l3 = b.l("data_", "data", reader);
            k.d(l3, "missingProperty(\"data_\", \"data\", reader)");
            throw l3;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable WsActivityEventDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("role");
            this.b.i(writer, value_.c());
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.b.i(writer, value_.d());
            writer.r("appUserId");
            this.c.i(writer, value_.a());
            writer.r("data");
            this.d.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
