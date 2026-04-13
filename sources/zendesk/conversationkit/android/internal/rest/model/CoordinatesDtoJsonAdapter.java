package zendesk.conversationkit.android.internal.rest.model;

import com.meituan.robust.Constants;
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

/* compiled from: CoordinatesDtoJsonAdapter.kt */
public final class CoordinatesDtoJsonAdapter extends f<CoordinatesDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<Double> b;

    public CoordinatesDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("lat", Constants.LONG);
        k.d(a2, "of(\"lat\", \"long\")");
        this.a = a2;
        f<Double> f = moshi.f(Double.TYPE, o0.d(), "lat");
        k.d(f, "moshi.adapter(Double::cl… emptySet(),\n      \"lat\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(36);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("CoordinatesDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public CoordinatesDto b(@NotNull i reader) {
        k.e(reader, "reader");
        Double lat = null;
        Double d = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    Double b2 = this.b.b(reader);
                    if (b2 != null) {
                        lat = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("lat", "lat", reader);
                        k.d(u, "unexpectedNull(\"lat\", \"lat\", reader)");
                        throw u;
                    }
                case 1:
                    Double b3 = this.b.b(reader);
                    if (b3 != null) {
                        d = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u(Constants.LONG, Constants.LONG, reader);
                        k.d(u2, "unexpectedNull(\"long\", \"long\",\n            reader)");
                        throw u2;
                    }
            }
        }
        reader.i();
        if (lat != null) {
            double doubleValue = lat.doubleValue();
            if (d != null) {
                return new CoordinatesDto(doubleValue, d.doubleValue());
            }
            JsonDataException l = b.l(Constants.LONG, Constants.LONG, reader);
            k.d(l, "missingProperty(\"long\", \"long\", reader)");
            throw l;
        }
        JsonDataException l2 = b.l("lat", "lat", reader);
        k.d(l2, "missingProperty(\"lat\", \"lat\", reader)");
        throw l2;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable CoordinatesDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("lat");
            this.b.i(writer, Double.valueOf(value_.a()));
            writer.r(Constants.LONG);
            this.b.i(writer, Double.valueOf(value_.b()));
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
