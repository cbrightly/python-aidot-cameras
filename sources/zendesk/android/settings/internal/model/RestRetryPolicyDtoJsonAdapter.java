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

/* compiled from: RestRetryPolicyDtoJsonAdapter.kt */
public final class RestRetryPolicyDtoJsonAdapter extends f<RestRetryPolicyDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<RetryIntervalDto> b;
    @NotNull
    private final f<Integer> c;

    public RestRetryPolicyDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("intervals", "backoffMultiplier", "maxRetries");
        k.d(a2, "of(\"intervals\", \"backoff…ier\",\n      \"maxRetries\")");
        this.a = a2;
        f<RetryIntervalDto> f = moshi.f(RetryIntervalDto.class, o0.d(), "intervals");
        k.d(f, "moshi.adapter(RetryInter… emptySet(), \"intervals\")");
        this.b = f;
        f<Integer> f2 = moshi.f(Integer.TYPE, o0.d(), "backoffMultiplier");
        k.d(f2, "moshi.adapter(Int::class…     \"backoffMultiplier\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("RestRetryPolicyDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public RestRetryPolicyDto b(@NotNull i reader) {
        k.e(reader, "reader");
        RetryIntervalDto intervals = null;
        Integer backoffMultiplier = null;
        Integer maxRetries = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    RetryIntervalDto b2 = this.b.b(reader);
                    if (b2 != null) {
                        intervals = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("intervals", "intervals", reader);
                        k.d(u, "unexpectedNull(\"intervals\", \"intervals\", reader)");
                        throw u;
                    }
                case 1:
                    Integer b3 = this.c.b(reader);
                    if (b3 != null) {
                        backoffMultiplier = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("backoffMultiplier", "backoffMultiplier", reader);
                        k.d(u2, "unexpectedNull(\"backoffM…ckoffMultiplier\", reader)");
                        throw u2;
                    }
                case 2:
                    Integer b4 = this.c.b(reader);
                    if (b4 != null) {
                        maxRetries = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("maxRetries", "maxRetries", reader);
                        k.d(u3, "unexpectedNull(\"maxRetri…    \"maxRetries\", reader)");
                        throw u3;
                    }
            }
        }
        reader.i();
        if (intervals == null) {
            JsonDataException l = b.l("intervals", "intervals", reader);
            k.d(l, "missingProperty(\"intervals\", \"intervals\", reader)");
            throw l;
        } else if (backoffMultiplier != null) {
            int intValue = backoffMultiplier.intValue();
            if (maxRetries != null) {
                return new RestRetryPolicyDto(intervals, intValue, maxRetries.intValue());
            }
            JsonDataException l2 = b.l("maxRetries", "maxRetries", reader);
            k.d(l2, "missingProperty(\"maxRetr…s\", \"maxRetries\", reader)");
            throw l2;
        } else {
            JsonDataException l3 = b.l("backoffMultiplier", "backoffMultiplier", reader);
            k.d(l3, "missingProperty(\"backoff…ckoffMultiplier\", reader)");
            throw l3;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable RestRetryPolicyDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("intervals");
            this.b.i(writer, value_.b());
            writer.r("backoffMultiplier");
            this.c.i(writer, Integer.valueOf(value_.a()));
            writer.r("maxRetries");
            this.c.i(writer, Integer.valueOf(value_.c()));
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
