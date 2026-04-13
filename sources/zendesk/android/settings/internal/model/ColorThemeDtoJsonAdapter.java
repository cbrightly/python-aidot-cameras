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

/* compiled from: ColorThemeDtoJsonAdapter.kt */
public final class ColorThemeDtoJsonAdapter extends f<ColorThemeDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;

    public ColorThemeDtoJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("primary_color", "message_color", "action_color", "notify_color");
        k.d(a2, "of(\"primary_color\", \"mes…n_color\", \"notify_color\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "primaryColor");
        k.d(f, "moshi.adapter(String::cl…(),\n      \"primaryColor\")");
        this.b = f;
        f<String> f2 = moshi.f(cls, o0.d(), "notifyColor");
        k.d(f2, "moshi.adapter(String::cl…mptySet(), \"notifyColor\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(35);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("ColorThemeDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public ColorThemeDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String primaryColor = null;
        String messageColor = null;
        String actionColor = null;
        String notifyColor = null;
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
                        primaryColor = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("primaryColor", "primary_color", reader);
                        k.d(u, "unexpectedNull(\"primaryC… \"primary_color\", reader)");
                        throw u;
                    }
                case 1:
                    String b3 = this.b.b(reader);
                    if (b3 != null) {
                        messageColor = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("messageColor", "message_color", reader);
                        k.d(u2, "unexpectedNull(\"messageC… \"message_color\", reader)");
                        throw u2;
                    }
                case 2:
                    String b4 = this.b.b(reader);
                    if (b4 != null) {
                        actionColor = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("actionColor", "action_color", reader);
                        k.d(u3, "unexpectedNull(\"actionCo…, \"action_color\", reader)");
                        throw u3;
                    }
                case 3:
                    notifyColor = this.c.b(reader);
                    break;
            }
        }
        reader.i();
        if (primaryColor == null) {
            JsonDataException l = b.l("primaryColor", "primary_color", reader);
            k.d(l, "missingProperty(\"primary…lor\",\n            reader)");
            throw l;
        } else if (messageColor == null) {
            JsonDataException l2 = b.l("messageColor", "message_color", reader);
            k.d(l2, "missingProperty(\"message…lor\",\n            reader)");
            throw l2;
        } else if (actionColor != null) {
            return new ColorThemeDto(primaryColor, messageColor, actionColor, notifyColor);
        } else {
            JsonDataException l3 = b.l("actionColor", "action_color", reader);
            k.d(l3, "missingProperty(\"actionC…lor\",\n            reader)");
            throw l3;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable ColorThemeDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("primary_color");
            this.b.i(writer, value_.d());
            writer.r("message_color");
            this.b.i(writer, value_.b());
            writer.r("action_color");
            this.b.i(writer, value_.a());
            writer.r("notify_color");
            this.c.i(writer, value_.c());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
