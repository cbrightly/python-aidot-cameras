package zendesk.android.settings.internal.model;

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

/* compiled from: ChannelIntegrationJsonAdapter.kt */
public final class ChannelIntegrationJsonAdapter extends f<ChannelIntegration> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public ChannelIntegrationJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("_id", IjkMediaMeta.IJKM_KEY_TYPE);
        k.d(a2, "of(\"_id\", \"type\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("ChannelIntegration");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public ChannelIntegration b(@NotNull i reader) {
        k.e(reader, "reader");
        String id = null;
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
                        id = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("id", "_id", reader);
                        k.d(u, "unexpectedNull(\"id\", \"_id\", reader)");
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
            }
        }
        reader.i();
        if (id == null) {
            JsonDataException l = b.l("id", "_id", reader);
            k.d(l, "missingProperty(\"id\", \"_id\", reader)");
            throw l;
        } else if (type != null) {
            return new ChannelIntegration(id, type);
        } else {
            JsonDataException l2 = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, reader);
            k.d(l2, "missingProperty(\"type\", \"type\", reader)");
            throw l2;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable ChannelIntegration value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("_id");
            this.b.i(writer, value_.a());
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.b.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
