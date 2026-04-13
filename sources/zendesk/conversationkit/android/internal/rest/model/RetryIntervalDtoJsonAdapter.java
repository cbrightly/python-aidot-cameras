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

/* compiled from: RetryIntervalDtoJsonAdapter.kt */
public final class RetryIntervalDtoJsonAdapter extends f<RetryIntervalDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<Integer> b;

    public RetryIntervalDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("regular", "aggressive");
        k.d(a2, "of(\"regular\", \"aggressive\")");
        this.a = a2;
        f<Integer> f = moshi.f(Integer.TYPE, o0.d(), "regular");
        k.d(f, "moshi.adapter(Int::class…a, emptySet(), \"regular\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(38);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("RetryIntervalDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public RetryIntervalDto b(@NotNull i reader) {
        k.e(reader, "reader");
        Integer regular = null;
        Integer aggressive = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    Integer b2 = this.b.b(reader);
                    if (b2 != null) {
                        regular = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("regular", "regular", reader);
                        k.d(u, "unexpectedNull(\"regular\"…       \"regular\", reader)");
                        throw u;
                    }
                case 1:
                    Integer b3 = this.b.b(reader);
                    if (b3 != null) {
                        aggressive = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("aggressive", "aggressive", reader);
                        k.d(u2, "unexpectedNull(\"aggressi…    \"aggressive\", reader)");
                        throw u2;
                    }
            }
        }
        reader.i();
        if (regular != null) {
            int intValue = regular.intValue();
            if (aggressive != null) {
                return new RetryIntervalDto(intValue, aggressive.intValue());
            }
            JsonDataException l = b.l("aggressive", "aggressive", reader);
            k.d(l, "missingProperty(\"aggress…e\", \"aggressive\", reader)");
            throw l;
        }
        JsonDataException l2 = b.l("regular", "regular", reader);
        k.d(l2, "missingProperty(\"regular\", \"regular\", reader)");
        throw l2;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable RetryIntervalDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("regular");
            this.b.i(writer, Integer.valueOf(value_.b()));
            writer.r("aggressive");
            this.b.i(writer, Integer.valueOf(value_.a()));
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
