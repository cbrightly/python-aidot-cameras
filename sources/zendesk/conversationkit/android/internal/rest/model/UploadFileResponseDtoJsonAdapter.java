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

/* compiled from: UploadFileResponseDtoJsonAdapter.kt */
public final class UploadFileResponseDtoJsonAdapter extends f<UploadFileResponseDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public UploadFileResponseDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("messageId");
        k.d(a2, "of(\"messageId\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "messageId");
        k.d(f, "moshi.adapter(String::cl…Set(),\n      \"messageId\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(43);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("UploadFileResponseDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public UploadFileResponseDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String messageId = null;
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
                        messageId = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("messageId", "messageId", reader);
                        k.d(u, "unexpectedNull(\"messageI…     \"messageId\", reader)");
                        throw u;
                    }
            }
        }
        reader.i();
        if (messageId != null) {
            return new UploadFileResponseDto(messageId);
        }
        JsonDataException l = b.l("messageId", "messageId", reader);
        k.d(l, "missingProperty(\"messageId\", \"messageId\", reader)");
        throw l;
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable UploadFileResponseDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("messageId");
            this.b.i(writer, value_.a());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
