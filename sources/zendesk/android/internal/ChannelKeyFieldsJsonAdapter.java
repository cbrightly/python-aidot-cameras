package zendesk.android.internal;

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

/* compiled from: ChannelKeyFieldsJsonAdapter.kt */
public final class ChannelKeyFieldsJsonAdapter extends f<ChannelKeyFields> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public ChannelKeyFieldsJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("settings_url");
        k.d(a2, "of(\"settings_url\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "settingsUrl");
        k.d(f, "moshi.adapter(String::cl…t(),\n      \"settingsUrl\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(38);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("ChannelKeyFields");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public ChannelKeyFields b(@NotNull i reader) {
        k.e(reader, "reader");
        String settingsUrl = null;
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
                        settingsUrl = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("settingsUrl", "settings_url", reader);
                        k.d(u, "unexpectedNull(\"settings…, \"settings_url\", reader)");
                        throw u;
                    }
            }
        }
        reader.i();
        if (settingsUrl != null) {
            return new ChannelKeyFields(settingsUrl);
        }
        JsonDataException l = b.l("settingsUrl", "settings_url", reader);
        k.d(l, "missingProperty(\"setting…url\",\n            reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable ChannelKeyFields value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("settings_url");
            this.b.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
