package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClientInfoDtoJsonAdapter.kt */
public final class ClientInfoDtoJsonAdapter extends f<ClientInfoDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public ClientInfoDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("appId", "appName", "vendor", "sdkVersion", "devicePlatform", "os", "osVersion", "installer", "carrier", "locale");
        k.d(a2, "of(\"appId\", \"appName\", \"…er\", \"carrier\", \"locale\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "appId");
        k.d(f, "moshi.adapter(String::cl…     emptySet(), \"appId\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(35);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("ClientInfoDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public ClientInfoDto b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String appId = null;
        String appName = null;
        String vendor = null;
        String sdkVersion = null;
        String devicePlatform = null;
        String os = null;
        String osVersion = null;
        String installer = null;
        String carrier = null;
        String locale = null;
        reader.c();
        while (reader.l()) {
            switch (iVar.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    appId = this.b.b(iVar);
                    break;
                case 1:
                    appName = this.b.b(iVar);
                    break;
                case 2:
                    vendor = this.b.b(iVar);
                    break;
                case 3:
                    sdkVersion = this.b.b(iVar);
                    break;
                case 4:
                    devicePlatform = this.b.b(iVar);
                    break;
                case 5:
                    os = this.b.b(iVar);
                    break;
                case 6:
                    osVersion = this.b.b(iVar);
                    break;
                case 7:
                    installer = this.b.b(iVar);
                    break;
                case 8:
                    carrier = this.b.b(iVar);
                    break;
                case 9:
                    locale = this.b.b(iVar);
                    break;
            }
        }
        reader.i();
        return new ClientInfoDto(appId, appName, vendor, sdkVersion, devicePlatform, os, osVersion, installer, carrier, locale);
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable ClientInfoDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("appId");
            this.b.i(writer, value_.a());
            writer.r("appName");
            this.b.i(writer, value_.b());
            writer.r("vendor");
            this.b.i(writer, value_.j());
            writer.r("sdkVersion");
            this.b.i(writer, value_.i());
            writer.r("devicePlatform");
            this.b.i(writer, value_.d());
            writer.r("os");
            this.b.i(writer, value_.g());
            writer.r("osVersion");
            this.b.i(writer, value_.h());
            writer.r("installer");
            this.b.i(writer, value_.e());
            writer.r("carrier");
            this.b.i(writer, value_.c());
            writer.r("locale");
            this.b.i(writer, value_.f());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
