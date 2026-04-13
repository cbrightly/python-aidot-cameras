package zendesk.conversationkit.android.internal.rest.model;

import com.google.firebase.messaging.Constants;
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
import zendesk.conversationkit.android.internal.rest.model.SendFieldResponseDto;

/* compiled from: SendFieldResponseDto_TextJsonAdapter.kt */
public final class SendFieldResponseDto_TextJsonAdapter extends f<SendFieldResponseDto.Text> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public SendFieldResponseDto_TextJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("_id", "name", Constants.ScionAnalytics.PARAM_LABEL, "text");
        k.d(a2, "of(\"_id\", \"name\", \"label\", \"text\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(47);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("SendFieldResponseDto.Text");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public SendFieldResponseDto.Text b(@NotNull i reader) {
        k.e(reader, "reader");
        String id = null;
        String name = null;
        String label = null;
        String text = null;
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
                        id = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("id", "_id", reader);
                        k.d(u, "unexpectedNull(\"id\", \"_id\", reader)");
                        throw u;
                    }
                case 1:
                    String b3 = this.b.b(reader);
                    if (b3 != null) {
                        name = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("name", "name", reader);
                        k.d(u2, "unexpectedNull(\"name\", \"name\",\n            reader)");
                        throw u2;
                    }
                case 2:
                    String b4 = this.b.b(reader);
                    if (b4 != null) {
                        label = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u(Constants.ScionAnalytics.PARAM_LABEL, Constants.ScionAnalytics.PARAM_LABEL, reader);
                        k.d(u3, "unexpectedNull(\"label\", …bel\",\n            reader)");
                        throw u3;
                    }
                case 3:
                    String b5 = this.b.b(reader);
                    if (b5 != null) {
                        text = b5;
                        break;
                    } else {
                        JsonDataException u4 = b.u("text", "text", reader);
                        k.d(u4, "unexpectedNull(\"text\", \"text\",\n            reader)");
                        throw u4;
                    }
            }
        }
        reader.i();
        if (id == null) {
            JsonDataException l = b.l("id", "_id", reader);
            k.d(l, "missingProperty(\"id\", \"_id\", reader)");
            throw l;
        } else if (name == null) {
            JsonDataException l2 = b.l("name", "name", reader);
            k.d(l2, "missingProperty(\"name\", \"name\", reader)");
            throw l2;
        } else if (label == null) {
            JsonDataException l3 = b.l(Constants.ScionAnalytics.PARAM_LABEL, Constants.ScionAnalytics.PARAM_LABEL, reader);
            k.d(l3, "missingProperty(\"label\", \"label\", reader)");
            throw l3;
        } else if (text != null) {
            return new SendFieldResponseDto.Text(id, name, label, text);
        } else {
            JsonDataException l4 = b.l("text", "text", reader);
            k.d(l4, "missingProperty(\"text\", \"text\", reader)");
            throw l4;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable SendFieldResponseDto.Text value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("_id");
            this.b.i(writer, value_.a());
            writer.r("name");
            this.b.i(writer, value_.c());
            writer.r(Constants.ScionAnalytics.PARAM_LABEL);
            this.b.i(writer, value_.b());
            writer.r("text");
            this.b.i(writer, value_.d());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
