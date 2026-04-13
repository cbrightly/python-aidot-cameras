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

/* compiled from: TypingSettingsDtoJsonAdapter.kt */
public final class TypingSettingsDtoJsonAdapter extends f<TypingSettingsDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<Boolean> b;

    public TypingSettingsDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("enabled");
        k.d(a2, "of(\"enabled\")");
        this.a = a2;
        f<Boolean> f = moshi.f(Boolean.TYPE, o0.d(), "enabled");
        k.d(f, "moshi.adapter(Boolean::c…tySet(),\n      \"enabled\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(39);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("TypingSettingsDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public TypingSettingsDto b(@NotNull i reader) {
        k.e(reader, "reader");
        Boolean enabled = null;
        reader.c();
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
            }
        }
        reader.i();
        if (enabled != null) {
            return new TypingSettingsDto(enabled.booleanValue());
        }
        JsonDataException l = b.l("enabled", "enabled", reader);
        k.d(l, "missingProperty(\"enabled\", \"enabled\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable TypingSettingsDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("enabled");
            this.b.i(writer, Boolean.valueOf(value_.a()));
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
