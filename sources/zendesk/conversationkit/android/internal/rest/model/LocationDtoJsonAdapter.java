package zendesk.conversationkit.android.internal.rest.model;

import com.google.android.libraries.places.api.model.PlaceTypes;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LocationDtoJsonAdapter.kt */
public final class LocationDtoJsonAdapter extends f<LocationDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public LocationDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("name", PlaceTypes.ADDRESS);
        k.d(a2, "of(\"name\", \"address\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "name");
        k.d(f, "moshi.adapter(String::cl…      emptySet(), \"name\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(33);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("LocationDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public LocationDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String name = null;
        String address = null;
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
                    address = this.b.b(reader);
                    break;
            }
        }
        reader.i();
        return new LocationDto(name, address);
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable LocationDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("name");
            this.b.i(writer, value_.b());
            writer.r(PlaceTypes.ADDRESS);
            this.b.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
