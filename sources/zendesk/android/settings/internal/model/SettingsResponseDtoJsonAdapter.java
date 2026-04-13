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

/* compiled from: SettingsResponseDtoJsonAdapter.kt */
public final class SettingsResponseDtoJsonAdapter extends f<SettingsResponseDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<SettingsDto> b;

    public SettingsResponseDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("settings");
        k.d(a2, "of(\"settings\")");
        this.a = a2;
        f<SettingsDto> f = moshi.f(SettingsDto.class, o0.d(), "settings");
        k.d(f, "moshi.adapter(SettingsDt…  emptySet(), \"settings\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(41);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("SettingsResponseDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public SettingsResponseDto b(@NotNull i reader) {
        k.e(reader, "reader");
        SettingsDto settings = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    SettingsDto b2 = this.b.b(reader);
                    if (b2 != null) {
                        settings = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("settings", "settings", reader);
                        k.d(u, "unexpectedNull(\"settings…      \"settings\", reader)");
                        throw u;
                    }
            }
        }
        reader.i();
        if (settings != null) {
            return new SettingsResponseDto(settings);
        }
        JsonDataException l = b.l("settings", "settings", reader);
        k.d(l, "missingProperty(\"settings\", \"settings\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable SettingsResponseDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("settings");
            this.b.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
