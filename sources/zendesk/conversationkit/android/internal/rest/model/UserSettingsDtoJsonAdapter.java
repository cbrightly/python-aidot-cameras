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

/* compiled from: UserSettingsDtoJsonAdapter.kt */
public final class UserSettingsDtoJsonAdapter extends f<UserSettingsDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<RealtimeSettingsDto> b;
    @NotNull
    private final f<TypingSettingsDto> c;

    public UserSettingsDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("realtime", "typing");
        k.d(a2, "of(\"realtime\", \"typing\")");
        this.a = a2;
        f<RealtimeSettingsDto> f = moshi.f(RealtimeSettingsDto.class, o0.d(), "realtime");
        k.d(f, "moshi.adapter(RealtimeSe…, emptySet(), \"realtime\")");
        this.b = f;
        f<TypingSettingsDto> f2 = moshi.f(TypingSettingsDto.class, o0.d(), "typing");
        k.d(f2, "moshi.adapter(TypingSett…va, emptySet(), \"typing\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(37);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("UserSettingsDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public UserSettingsDto b(@NotNull i reader) {
        k.e(reader, "reader");
        RealtimeSettingsDto realtime = null;
        TypingSettingsDto typing = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    RealtimeSettingsDto b2 = this.b.b(reader);
                    if (b2 != null) {
                        realtime = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("realtime", "realtime", reader);
                        k.d(u, "unexpectedNull(\"realtime\", \"realtime\", reader)");
                        throw u;
                    }
                case 1:
                    TypingSettingsDto b3 = this.c.b(reader);
                    if (b3 != null) {
                        typing = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("typing", "typing", reader);
                        k.d(u2, "unexpectedNull(\"typing\", \"typing\", reader)");
                        throw u2;
                    }
            }
        }
        reader.i();
        if (realtime == null) {
            JsonDataException l = b.l("realtime", "realtime", reader);
            k.d(l, "missingProperty(\"realtime\", \"realtime\", reader)");
            throw l;
        } else if (typing != null) {
            return new UserSettingsDto(realtime, typing);
        } else {
            JsonDataException l2 = b.l("typing", "typing", reader);
            k.d(l2, "missingProperty(\"typing\", \"typing\", reader)");
            throw l2;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable UserSettingsDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("realtime");
            this.b.i(writer, value_.a());
            writer.r("typing");
            this.c.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
