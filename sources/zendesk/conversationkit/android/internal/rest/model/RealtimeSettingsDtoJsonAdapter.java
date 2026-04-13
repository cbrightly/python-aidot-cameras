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

/* compiled from: RealtimeSettingsDtoJsonAdapter.kt */
public final class RealtimeSettingsDtoJsonAdapter extends f<RealtimeSettingsDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<Boolean> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<Integer> d;

    public RealtimeSettingsDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("enabled", "baseUrl", "retryInterval", "maxConnectionAttempts", "connectionDelay");
        k.d(a2, "of(\"enabled\", \"baseUrl\",…mpts\", \"connectionDelay\")");
        this.a = a2;
        f<Boolean> f = moshi.f(Boolean.TYPE, o0.d(), "enabled");
        k.d(f, "moshi.adapter(Boolean::c…tySet(),\n      \"enabled\")");
        this.b = f;
        f<String> f2 = moshi.f(String.class, o0.d(), "baseUrl");
        k.d(f2, "moshi.adapter(String::cl…tySet(),\n      \"baseUrl\")");
        this.c = f2;
        f<Integer> f3 = moshi.f(Integer.TYPE, o0.d(), "retryInterval");
        k.d(f3, "moshi.adapter(Int::class…),\n      \"retryInterval\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(41);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("RealtimeSettingsDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public RealtimeSettingsDto b(@NotNull i reader) {
        k.e(reader, "reader");
        Boolean enabled = null;
        reader.c();
        String baseUrl = null;
        Integer retryInterval = null;
        Integer maxConnectionAttempts = null;
        Integer connectionDelay = null;
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    Boolean b2 = this.b.b(reader);
                    if (b2 != null) {
                        enabled = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("enabled", "enabled", reader);
                        k.d(u, "unexpectedNull(\"enabled\"…       \"enabled\", reader)");
                        throw u;
                    }
                case 1:
                    String b3 = this.c.b(reader);
                    if (b3 != null) {
                        baseUrl = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("baseUrl", "baseUrl", reader);
                        k.d(u2, "unexpectedNull(\"baseUrl\"…       \"baseUrl\", reader)");
                        throw u2;
                    }
                case 2:
                    Integer b4 = this.d.b(reader);
                    if (b4 != null) {
                        retryInterval = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("retryInterval", "retryInterval", reader);
                        k.d(u3, "unexpectedNull(\"retryInt… \"retryInterval\", reader)");
                        throw u3;
                    }
                case 3:
                    Integer b5 = this.d.b(reader);
                    if (b5 != null) {
                        maxConnectionAttempts = b5;
                        break;
                    } else {
                        JsonDataException u4 = b.u("maxConnectionAttempts", "maxConnectionAttempts", reader);
                        k.d(u4, "unexpectedNull(\"maxConne…nectionAttempts\", reader)");
                        throw u4;
                    }
                case 4:
                    Integer b6 = this.d.b(reader);
                    if (b6 != null) {
                        connectionDelay = b6;
                        break;
                    } else {
                        JsonDataException u5 = b.u("connectionDelay", "connectionDelay", reader);
                        k.d(u5, "unexpectedNull(\"connecti…connectionDelay\", reader)");
                        throw u5;
                    }
            }
        }
        reader.i();
        if (enabled != null) {
            boolean booleanValue = enabled.booleanValue();
            if (baseUrl == null) {
                JsonDataException l = b.l("baseUrl", "baseUrl", reader);
                k.d(l, "missingProperty(\"baseUrl\", \"baseUrl\", reader)");
                throw l;
            } else if (retryInterval != null) {
                int intValue = retryInterval.intValue();
                if (maxConnectionAttempts != null) {
                    int intValue2 = maxConnectionAttempts.intValue();
                    if (connectionDelay != null) {
                        return new RealtimeSettingsDto(booleanValue, baseUrl, intValue, intValue2, connectionDelay.intValue());
                    }
                    JsonDataException l2 = b.l("connectionDelay", "connectionDelay", reader);
                    k.d(l2, "missingProperty(\"connect…connectionDelay\", reader)");
                    throw l2;
                }
                JsonDataException l3 = b.l("maxConnectionAttempts", "maxConnectionAttempts", reader);
                k.d(l3, "missingProperty(\"maxConn…nectionAttempts\", reader)");
                throw l3;
            } else {
                JsonDataException l4 = b.l("retryInterval", "retryInterval", reader);
                k.d(l4, "missingProperty(\"retryIn… \"retryInterval\", reader)");
                throw l4;
            }
        } else {
            JsonDataException l5 = b.l("enabled", "enabled", reader);
            k.d(l5, "missingProperty(\"enabled\", \"enabled\", reader)");
            throw l5;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable RealtimeSettingsDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("enabled");
            this.b.i(writer, Boolean.valueOf(value_.c()));
            writer.r("baseUrl");
            this.c.i(writer, value_.a());
            writer.r("retryInterval");
            this.d.i(writer, Integer.valueOf(value_.e()));
            writer.r("maxConnectionAttempts");
            this.d.i(writer, Integer.valueOf(value_.d()));
            writer.r("connectionDelay");
            this.d.i(writer, Integer.valueOf(value_.b()));
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
