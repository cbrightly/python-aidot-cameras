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

/* compiled from: ActivityDataDtoJsonAdapter.kt */
public final class ActivityDataDtoJsonAdapter extends f<ActivityDataDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public ActivityDataDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a(IjkMediaMeta.IJKM_KEY_TYPE);
        k.d(a2, "of(\"type\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), IjkMediaMeta.IJKM_KEY_TYPE);
        k.d(f, "moshi.adapter(String::cl…emptySet(),\n      \"type\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(37);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("ActivityDataDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public ActivityDataDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String type = null;
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
                        type = b2;
                        break;
                    } else {
                        JsonDataException u = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, reader);
                        k.d(u, "unexpectedNull(\"type\", \"type\",\n            reader)");
                        throw u;
                    }
            }
        }
        reader.i();
        if (type != null) {
            return new ActivityDataDto(type);
        }
        JsonDataException l = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, reader);
        k.d(l, "missingProperty(\"type\", \"type\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable ActivityDataDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.b.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
