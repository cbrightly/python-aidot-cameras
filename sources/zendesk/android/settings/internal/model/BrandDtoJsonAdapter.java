package zendesk.android.settings.internal.model;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BrandDtoJsonAdapter.kt */
public final class BrandDtoJsonAdapter extends f<BrandDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<Long> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<Boolean> d;

    public BrandDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", "account_id", "name", AppMeasurementSdk.ConditionalUserProperty.ACTIVE, "deleted_at", DbParams.KEY_CREATED_AT, "updated_at", "route_id", "signature_template");
        k.d(a2, "of(\"id\", \"account_id\", \"…d\", \"signature_template\")");
        this.a = a2;
        f<Long> f = moshi.f(Long.class, o0.d(), "id");
        k.d(f, "moshi.adapter(Long::clas…,\n      emptySet(), \"id\")");
        this.b = f;
        f<String> f2 = moshi.f(String.class, o0.d(), "name");
        k.d(f2, "moshi.adapter(String::cl…      emptySet(), \"name\")");
        this.c = f2;
        f<Boolean> f3 = moshi.f(Boolean.class, o0.d(), AppMeasurementSdk.ConditionalUserProperty.ACTIVE);
        k.d(f3, "moshi.adapter(Boolean::c…pe, emptySet(), \"active\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(30);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("BrandDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public BrandDto b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        Long id = null;
        Long accountId = null;
        String name = null;
        Boolean active = null;
        String deletedAt = null;
        String createdAt = null;
        String updatedAt = null;
        Long routeId = null;
        String signatureTemplate = null;
        reader.c();
        while (reader.l()) {
            switch (iVar.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    id = this.b.b(iVar);
                    break;
                case 1:
                    accountId = this.b.b(iVar);
                    break;
                case 2:
                    name = this.c.b(iVar);
                    break;
                case 3:
                    active = this.d.b(iVar);
                    break;
                case 4:
                    deletedAt = this.c.b(iVar);
                    break;
                case 5:
                    createdAt = this.c.b(iVar);
                    break;
                case 6:
                    updatedAt = this.c.b(iVar);
                    break;
                case 7:
                    routeId = this.b.b(iVar);
                    break;
                case 8:
                    signatureTemplate = this.c.b(iVar);
                    break;
            }
        }
        reader.i();
        return new BrandDto(id, accountId, name, active, deletedAt, createdAt, updatedAt, routeId, signatureTemplate);
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable BrandDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("id");
            this.b.i(writer, value_.e());
            writer.r("account_id");
            this.b.i(writer, value_.a());
            writer.r("name");
            this.c.i(writer, value_.f());
            writer.r(AppMeasurementSdk.ConditionalUserProperty.ACTIVE);
            this.d.i(writer, value_.b());
            writer.r("deleted_at");
            this.c.i(writer, value_.d());
            writer.r(DbParams.KEY_CREATED_AT);
            this.c.i(writer, value_.c());
            writer.r("updated_at");
            this.c.i(writer, value_.i());
            writer.r("route_id");
            this.b.i(writer, value_.g());
            writer.r("signature_template");
            this.c.i(writer, value_.h());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
