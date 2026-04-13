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

/* compiled from: SendMessageRequestDtoJsonAdapter.kt */
public final class SendMessageRequestDtoJsonAdapter extends f<SendMessageRequestDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<AuthorDto> b;
    @NotNull
    private final f<SendMessageDto> c;

    public SendMessageRequestDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("author", "message");
        k.d(a2, "of(\"author\", \"message\")");
        this.a = a2;
        f<AuthorDto> f = moshi.f(AuthorDto.class, o0.d(), "author");
        k.d(f, "moshi.adapter(AuthorDto:…    emptySet(), \"author\")");
        this.b = f;
        f<SendMessageDto> f2 = moshi.f(SendMessageDto.class, o0.d(), "message");
        k.d(f2, "moshi.adapter(SendMessag…a, emptySet(), \"message\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(43);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("SendMessageRequestDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public SendMessageRequestDto b(@NotNull i reader) {
        k.e(reader, "reader");
        AuthorDto author = null;
        SendMessageDto message = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    AuthorDto b2 = this.b.b(reader);
                    if (b2 != null) {
                        author = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("author", "author", reader);
                        k.d(u, "unexpectedNull(\"author\",…        \"author\", reader)");
                        throw u;
                    }
                case 1:
                    SendMessageDto b3 = this.c.b(reader);
                    if (b3 != null) {
                        message = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("message", "message", reader);
                        k.d(u2, "unexpectedNull(\"message\", \"message\", reader)");
                        throw u2;
                    }
            }
        }
        reader.i();
        if (author == null) {
            JsonDataException l = b.l("author", "author", reader);
            k.d(l, "missingProperty(\"author\", \"author\", reader)");
            throw l;
        } else if (message != null) {
            return new SendMessageRequestDto(author, message);
        } else {
            JsonDataException l2 = b.l("message", "message", reader);
            k.d(l2, "missingProperty(\"message\", \"message\", reader)");
            throw l2;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable SendMessageRequestDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("author");
            this.b.i(writer, value_.a());
            writer.r("message");
            this.c.i(writer, value_.b());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
