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

/* compiled from: NativeMessagingDtoJsonAdapter.kt */
public final class NativeMessagingDtoJsonAdapter extends f<NativeMessagingDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Boolean> c;
    @NotNull
    private final f<BrandDto> d;

    public NativeMessagingDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("integration_id", "platform", "enabled", "brand", "title", "description", "logo_url");
        k.d(a2, "of(\"integration_id\", \"pl…description\", \"logo_url\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "integrationId");
        k.d(f, "moshi.adapter(String::cl…tySet(), \"integrationId\")");
        this.b = f;
        f<Boolean> f2 = moshi.f(Boolean.TYPE, o0.d(), "enabled");
        k.d(f2, "moshi.adapter(Boolean::c…tySet(),\n      \"enabled\")");
        this.c = f2;
        f<BrandDto> f3 = moshi.f(BrandDto.class, o0.d(), "brand");
        k.d(f3, "moshi.adapter(BrandDto::…     emptySet(), \"brand\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("NativeMessagingDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public NativeMessagingDto b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String integrationId = null;
        String platform = null;
        Boolean enabled = null;
        BrandDto brand = null;
        String title = null;
        String description = null;
        String logoUrl = null;
        reader.c();
        while (reader.l()) {
            switch (iVar.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    integrationId = this.b.b(iVar);
                    break;
                case 1:
                    platform = this.b.b(iVar);
                    break;
                case 2:
                    Boolean b2 = this.c.b(iVar);
                    if (b2 != null) {
                        enabled = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("enabled", "enabled", iVar);
                        k.d(u, "unexpectedNull(\"enabled\"…       \"enabled\", reader)");
                        throw u;
                    }
                case 3:
                    brand = this.d.b(iVar);
                    break;
                case 4:
                    title = this.b.b(iVar);
                    break;
                case 5:
                    description = this.b.b(iVar);
                    break;
                case 6:
                    logoUrl = this.b.b(iVar);
                    break;
            }
        }
        reader.i();
        if (enabled != null) {
            return new NativeMessagingDto(integrationId, platform, enabled.booleanValue(), brand, title, description, logoUrl);
        }
        JsonDataException l = b.l("enabled", "enabled", iVar);
        k.d(l, "missingProperty(\"enabled\", \"enabled\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable NativeMessagingDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("integration_id");
            this.b.i(writer, value_.d());
            writer.r("platform");
            this.b.i(writer, value_.f());
            writer.r("enabled");
            this.c.i(writer, Boolean.valueOf(value_.c()));
            writer.r("brand");
            this.d.i(writer, value_.a());
            writer.r("title");
            this.b.i(writer, value_.g());
            writer.r("description");
            this.b.i(writer, value_.b());
            writer.r("logo_url");
            this.b.i(writer, value_.e());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
