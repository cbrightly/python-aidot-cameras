package zendesk.conversationkit.android.internal.rest.user.model;

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
import zendesk.conversationkit.android.internal.rest.model.ClientDto;

/* compiled from: LogoutRequestBodyJsonAdapter.kt */
public final class LogoutRequestBodyJsonAdapter extends f<LogoutRequestBody> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<ClientDto> b;

    public LogoutRequestBodyJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("client");
        k.d(a2, "of(\"client\")");
        this.a = a2;
        f<ClientDto> f = moshi.f(ClientDto.class, o0.d(), "client");
        k.d(f, "moshi.adapter(ClientDto:…    emptySet(), \"client\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(39);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("LogoutRequestBody");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public LogoutRequestBody b(@NotNull i reader) {
        k.e(reader, "reader");
        ClientDto client = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    ClientDto b2 = this.b.b(reader);
                    if (b2 != null) {
                        client = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("client", "client", reader);
                        k.d(u, "unexpectedNull(\"client\",…        \"client\", reader)");
                        throw u;
                    }
            }
        }
        reader.i();
        if (client != null) {
            return new LogoutRequestBody(client);
        }
        JsonDataException l = b.l("client", "client", reader);
        k.d(l, "missingProperty(\"client\", \"client\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable LogoutRequestBody value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("client");
            this.b.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
