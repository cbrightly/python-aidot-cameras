package zendesk.android.settings.internal.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.util.List;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SunCoConfigDtoJsonAdapter.kt */
public final class SunCoConfigDtoJsonAdapter extends f<SunCoConfigDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<AppDto> b;
    @NotNull
    private final f<BaseUrlDto> c;
    @NotNull
    private final f<RestRetryPolicyDto> d;
    @NotNull
    private final f<List<ChannelIntegration>> e;

    public SunCoConfigDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("app", "baseUrl", "restRetryPolicy", "integrations");
        k.d(a2, "of(\"app\", \"baseUrl\",\n   …yPolicy\", \"integrations\")");
        this.a = a2;
        f<AppDto> f = moshi.f(AppDto.class, o0.d(), "app");
        k.d(f, "moshi.adapter(AppDto::cl… emptySet(),\n      \"app\")");
        this.b = f;
        f<BaseUrlDto> f2 = moshi.f(BaseUrlDto.class, o0.d(), "baseUrl");
        k.d(f2, "moshi.adapter(BaseUrlDto…   emptySet(), \"baseUrl\")");
        this.c = f2;
        f<RestRetryPolicyDto> f3 = moshi.f(RestRetryPolicyDto.class, o0.d(), "restRetryPolicy");
        k.d(f3, "moshi.adapter(RestRetryP…Set(), \"restRetryPolicy\")");
        this.d = f3;
        f<List<ChannelIntegration>> f4 = moshi.f(t.j(List.class, ChannelIntegration.class), o0.d(), "integrations");
        k.d(f4, "moshi.adapter(Types.newP…ptySet(), \"integrations\")");
        this.e = f4;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(36);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("SunCoConfigDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public SunCoConfigDto b(@NotNull i reader) {
        k.e(reader, "reader");
        AppDto app = null;
        BaseUrlDto baseUrl = null;
        RestRetryPolicyDto restRetryPolicy = null;
        List integrations = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    AppDto b2 = this.b.b(reader);
                    if (b2 != null) {
                        app = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("app", "app", reader);
                        k.d(u, "unexpectedNull(\"app\", \"app\", reader)");
                        throw u;
                    }
                case 1:
                    BaseUrlDto b3 = this.c.b(reader);
                    if (b3 != null) {
                        baseUrl = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("baseUrl", "baseUrl", reader);
                        k.d(u2, "unexpectedNull(\"baseUrl\"…       \"baseUrl\", reader)");
                        throw u2;
                    }
                case 2:
                    RestRetryPolicyDto b4 = this.d.b(reader);
                    if (b4 != null) {
                        restRetryPolicy = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("restRetryPolicy", "restRetryPolicy", reader);
                        k.d(u3, "unexpectedNull(\"restRetr…restRetryPolicy\", reader)");
                        throw u3;
                    }
                case 3:
                    List b5 = this.e.b(reader);
                    if (b5 != null) {
                        integrations = b5;
                        break;
                    } else {
                        JsonDataException u4 = b.u("integrations", "integrations", reader);
                        k.d(u4, "unexpectedNull(\"integrat…, \"integrations\", reader)");
                        throw u4;
                    }
            }
        }
        reader.i();
        if (app == null) {
            JsonDataException l = b.l("app", "app", reader);
            k.d(l, "missingProperty(\"app\", \"app\", reader)");
            throw l;
        } else if (baseUrl == null) {
            JsonDataException l2 = b.l("baseUrl", "baseUrl", reader);
            k.d(l2, "missingProperty(\"baseUrl\", \"baseUrl\", reader)");
            throw l2;
        } else if (restRetryPolicy == null) {
            JsonDataException l3 = b.l("restRetryPolicy", "restRetryPolicy", reader);
            k.d(l3, "missingProperty(\"restRet…restRetryPolicy\", reader)");
            throw l3;
        } else if (integrations != null) {
            return new SunCoConfigDto(app, baseUrl, restRetryPolicy, integrations);
        } else {
            JsonDataException l4 = b.l("integrations", "integrations", reader);
            k.d(l4, "missingProperty(\"integra…ons\",\n            reader)");
            throw l4;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable SunCoConfigDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("app");
            this.b.i(writer, value_.a());
            writer.r("baseUrl");
            this.c.i(writer, value_.b());
            writer.r("restRetryPolicy");
            this.d.i(writer, value_.d());
            writer.r("integrations");
            this.e.i(writer, value_.c());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
