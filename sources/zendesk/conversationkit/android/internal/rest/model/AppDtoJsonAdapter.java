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

/* compiled from: AppDtoJsonAdapter.kt */
public final class AppDtoJsonAdapter extends f<AppDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public AppDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("_id", "status", "name");
        k.d(a2, "of(\"_id\", \"status\", \"name\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(28);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("AppDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public AppDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String id = null;
        String status = null;
        String name = null;
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
                        status = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("status", "status", reader);
                        k.d(u2, "unexpectedNull(\"status\",…        \"status\", reader)");
                        throw u2;
                    }
                case 2:
                    String b4 = this.b.b(reader);
                    if (b4 != null) {
                        name = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("name", "name", reader);
                        k.d(u3, "unexpectedNull(\"name\", \"name\",\n            reader)");
                        throw u3;
                    }
            }
        }
        reader.i();
        if (id == null) {
            JsonDataException l = b.l("id", "_id", reader);
            k.d(l, "missingProperty(\"id\", \"_id\", reader)");
            throw l;
        } else if (status == null) {
            JsonDataException l2 = b.l("status", "status", reader);
            k.d(l2, "missingProperty(\"status\", \"status\", reader)");
            throw l2;
        } else if (name != null) {
            return new AppDto(id, status, name);
        } else {
            JsonDataException l3 = b.l("name", "name", reader);
            k.d(l3, "missingProperty(\"name\", \"name\", reader)");
            throw l3;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable AppDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("_id");
            this.b.i(writer, value_.a());
            writer.r("status");
            this.b.i(writer, value_.c());
            writer.r("name");
            this.b.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
