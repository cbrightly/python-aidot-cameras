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

/* compiled from: BaseUrlDtoJsonAdapter.kt */
public final class BaseUrlDtoJsonAdapter extends f<BaseUrlDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public BaseUrlDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("android");
        k.d(a2, "of(\"android\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "android");
        k.d(f, "moshi.adapter(String::cl…tySet(),\n      \"android\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("BaseUrlDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public BaseUrlDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String android2 = null;
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
                        android2 = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("android", "android", reader);
                        k.d(u, "unexpectedNull(\"android\"…       \"android\", reader)");
                        throw u;
                    }
            }
        }
        reader.i();
        if (android2 != null) {
            return new BaseUrlDto(android2);
        }
        JsonDataException l = b.l("android", "android", reader);
        k.d(l, "missingProperty(\"android\", \"android\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable BaseUrlDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("android");
            this.b.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
