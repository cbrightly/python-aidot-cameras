package zendesk.conversationkit.android.model;

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
import zendesk.conversationkit.android.model.Field;

/* compiled from: Field_EmailJsonAdapter.kt */
public final class Field_EmailJsonAdapter extends f<Field.Email> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;

    public Field_EmailJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", "name", Constants.ScionAnalytics.PARAM_LABEL, "placeholder", "email");
        k.d(a2, "of(\"id\", \"name\", \"label\"…  \"placeholder\", \"email\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(33);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("Field.Email");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public Field.Email b(@NotNull i reader) {
        k.e(reader, "reader");
        String id = null;
        String name = null;
        String label = null;
        String placeholder = null;
        String email = null;
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
                        JsonDataException u = b.u("id", "id", reader);
                        k.d(u, "unexpectedNull(\"id\", \"id\", reader)");
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
                        placeholder = b5;
                        break;
                    } else {
                        JsonDataException u4 = b.u("placeholder", "placeholder", reader);
                        k.d(u4, "unexpectedNull(\"placehol…\", \"placeholder\", reader)");
                        throw u4;
                    }
                case 4:
                    String b6 = this.b.b(reader);
                    if (b6 != null) {
                        email = b6;
                        break;
                    } else {
                        JsonDataException u5 = b.u("email", "email", reader);
                        k.d(u5, "unexpectedNull(\"email\", …ail\",\n            reader)");
                        throw u5;
                    }
            }
        }
        reader.i();
        if (id == null) {
            JsonDataException l = b.l("id", "id", reader);
            k.d(l, "missingProperty(\"id\", \"id\", reader)");
            throw l;
        } else if (name == null) {
            JsonDataException l2 = b.l("name", "name", reader);
            k.d(l2, "missingProperty(\"name\", \"name\", reader)");
            throw l2;
        } else if (label == null) {
            JsonDataException l3 = b.l(Constants.ScionAnalytics.PARAM_LABEL, Constants.ScionAnalytics.PARAM_LABEL, reader);
            k.d(l3, "missingProperty(\"label\", \"label\", reader)");
            throw l3;
        } else if (placeholder == null) {
            JsonDataException l4 = b.l("placeholder", "placeholder", reader);
            k.d(l4, "missingProperty(\"placeho…der\",\n            reader)");
            throw l4;
        } else if (email != null) {
            return new Field.Email(id, name, label, placeholder, email);
        } else {
            JsonDataException l5 = b.l("email", "email", reader);
            k.d(l5, "missingProperty(\"email\", \"email\", reader)");
            throw l5;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable Field.Email value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("id");
            this.b.i(writer, value_.a());
            writer.r("name");
            this.b.i(writer, value_.c());
            writer.r(Constants.ScionAnalytics.PARAM_LABEL);
            this.b.i(writer, value_.b());
            writer.r("placeholder");
            this.b.i(writer, value_.d());
            writer.r("email");
            this.b.i(writer, value_.g());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
