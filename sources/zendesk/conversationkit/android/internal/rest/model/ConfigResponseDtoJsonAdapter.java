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

/* compiled from: ConfigResponseDtoJsonAdapter.kt */
public final class ConfigResponseDtoJsonAdapter extends f<ConfigResponseDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<ConfigDto> b;

    public ConfigResponseDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("config");
        k.d(a2, "of(\"config\")");
        this.a = a2;
        f<ConfigDto> f = moshi.f(ConfigDto.class, o0.d(), "config");
        k.d(f, "moshi.adapter(ConfigDto:…    emptySet(), \"config\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(39);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("ConfigResponseDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public ConfigResponseDto b(@NotNull i reader) {
        k.e(reader, "reader");
        ConfigDto config = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    ConfigDto b2 = this.b.b(reader);
                    if (b2 != null) {
                        config = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("config", "config", reader);
                        k.d(u, "unexpectedNull(\"config\",…        \"config\", reader)");
                        throw u;
                    }
            }
        }
        reader.i();
        if (config != null) {
            return new ConfigResponseDto(config);
        }
        JsonDataException l = b.l("config", "config", reader);
        k.d(l, "missingProperty(\"config\", \"config\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable ConfigResponseDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("config");
            this.b.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
