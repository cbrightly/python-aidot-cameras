package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: UpdatePushTokenDtoJsonAdapter.kt */
public final class UpdatePushTokenDtoJsonAdapter extends f<UpdatePushTokenDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public UpdatePushTokenDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("pushNotificationToken", "integrationId");
        k.d(a2, "of(\"pushNotificationToken\",\n      \"integrationId\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "pushNotificationToken");
        k.d(f, "moshi.adapter(String::cl… \"pushNotificationToken\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("UpdatePushTokenDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public UpdatePushTokenDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String pushNotificationToken = null;
        String integrationId = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    pushNotificationToken = this.b.b(reader);
                    break;
                case 1:
                    integrationId = this.b.b(reader);
                    break;
            }
        }
        reader.i();
        return new UpdatePushTokenDto(pushNotificationToken, integrationId);
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable UpdatePushTokenDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("pushNotificationToken");
            this.b.i(writer, value_.b());
            writer.r("integrationId");
            this.b.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
