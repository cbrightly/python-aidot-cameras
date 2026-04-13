package zendesk.conversationkit.android.internal.rest.model;

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

/* compiled from: MetadataDtoJsonAdapter.kt */
public final class MetadataDtoJsonAdapter extends f<MetadataDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<Map<String, Object>> b;

    public MetadataDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("metadata");
        k.d(a2, "of(\"metadata\")");
        this.a = a2;
        f<Map<String, Object>> f = moshi.f(t.j(Map.class, String.class, Object.class), o0.d(), "metadata");
        k.d(f, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(33);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MetadataDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MetadataDto b(@NotNull i reader) {
        k.e(reader, "reader");
        Map metadata = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    Map b2 = this.b.b(reader);
                    if (b2 != null) {
                        metadata = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("metadata", "metadata", reader);
                        k.d(u, "unexpectedNull(\"metadata\", \"metadata\", reader)");
                        throw u;
                    }
            }
        }
        reader.i();
        if (metadata != null) {
            return new MetadataDto(metadata);
        }
        JsonDataException l = b.l("metadata", "metadata", reader);
        k.d(l, "missingProperty(\"metadata\", \"metadata\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MetadataDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("metadata");
            this.b.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
