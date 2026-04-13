package zendesk.conversationkit.android.internal.faye;

import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WsActivityEventDataDtoJsonAdapter.kt */
public final class WsActivityEventDataDtoJsonAdapter extends f<WsActivityEventDataDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Double> c;

    public WsActivityEventDataDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("name", "avatarUrl", "lastRead");
        k.d(a2, "of(\"name\", \"avatarUrl\", \"lastRead\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "name");
        k.d(f, "moshi.adapter(String::cl…      emptySet(), \"name\")");
        this.b = f;
        f<Double> f2 = moshi.f(Double.class, o0.d(), "lastRead");
        k.d(f2, "moshi.adapter(Double::cl…, emptySet(), \"lastRead\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(44);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("WsActivityEventDataDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public WsActivityEventDataDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String name = null;
        String avatarUrl = null;
        Double lastRead = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    name = this.b.b(reader);
                    break;
                case 1:
                    avatarUrl = this.b.b(reader);
                    break;
                case 2:
                    lastRead = this.c.b(reader);
                    break;
            }
        }
        reader.i();
        return new WsActivityEventDataDto(name, avatarUrl, lastRead);
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable WsActivityEventDataDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("name");
            this.b.i(writer, value_.c());
            writer.r("avatarUrl");
            this.b.i(writer, value_.a());
            writer.r("lastRead");
            this.c.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
