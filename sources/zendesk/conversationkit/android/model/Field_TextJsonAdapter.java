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

/* compiled from: Field_TextJsonAdapter.kt */
public final class Field_TextJsonAdapter extends f<Field.Text> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Integer> c;

    public Field_TextJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", "name", Constants.ScionAnalytics.PARAM_LABEL, "placeholder", "minSize", "maxSize", "text");
        k.d(a2, "of(\"id\", \"name\", \"label\"…Size\", \"maxSize\", \"text\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
        f<Integer> f2 = moshi.f(Integer.TYPE, o0.d(), "minSize");
        k.d(f2, "moshi.adapter(Int::class…a, emptySet(), \"minSize\")");
        this.c = f2;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("Field.Text");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public Field.Text b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String id = null;
        String name = null;
        String label = null;
        String placeholder = null;
        Integer minSize = null;
        Integer maxSize = null;
        String text = null;
        reader.c();
        while (true) {
            String text2 = text;
            if (reader.l()) {
                switch (iVar.J(this.a)) {
                    case -1:
                        reader.o0();
                        reader.u0();
                        break;
                    case 0:
                        String b2 = this.b.b(iVar);
                        if (b2 != null) {
                            id = b2;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u = b.u("id", "id", iVar);
                            k.d(u, "unexpectedNull(\"id\", \"id\", reader)");
                            throw u;
                        }
                    case 1:
                        String b3 = this.b.b(iVar);
                        if (b3 != null) {
                            name = b3;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u2 = b.u("name", "name", iVar);
                            k.d(u2, "unexpectedNull(\"name\", \"name\",\n            reader)");
                            throw u2;
                        }
                    case 2:
                        String b4 = this.b.b(iVar);
                        if (b4 != null) {
                            label = b4;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u3 = b.u(Constants.ScionAnalytics.PARAM_LABEL, Constants.ScionAnalytics.PARAM_LABEL, iVar);
                            k.d(u3, "unexpectedNull(\"label\", …bel\",\n            reader)");
                            throw u3;
                        }
                    case 3:
                        String b5 = this.b.b(iVar);
                        if (b5 != null) {
                            placeholder = b5;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u4 = b.u("placeholder", "placeholder", iVar);
                            k.d(u4, "unexpectedNull(\"placehol…\", \"placeholder\", reader)");
                            throw u4;
                        }
                    case 4:
                        Integer b6 = this.c.b(iVar);
                        if (b6 != null) {
                            minSize = b6;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u5 = b.u("minSize", "minSize", iVar);
                            k.d(u5, "unexpectedNull(\"minSize\"…       \"minSize\", reader)");
                            throw u5;
                        }
                    case 5:
                        Integer b7 = this.c.b(iVar);
                        if (b7 != null) {
                            maxSize = b7;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u6 = b.u("maxSize", "maxSize", iVar);
                            k.d(u6, "unexpectedNull(\"maxSize\"…       \"maxSize\", reader)");
                            throw u6;
                        }
                    case 6:
                        text = this.b.b(iVar);
                        if (text != null) {
                            continue;
                        } else {
                            JsonDataException u7 = b.u("text", "text", iVar);
                            k.d(u7, "unexpectedNull(\"text\", \"text\",\n            reader)");
                            throw u7;
                        }
                }
                text = text2;
            } else {
                reader.i();
                if (id == null) {
                    JsonDataException l = b.l("id", "id", iVar);
                    k.d(l, "missingProperty(\"id\", \"id\", reader)");
                    throw l;
                } else if (name == null) {
                    JsonDataException l2 = b.l("name", "name", iVar);
                    k.d(l2, "missingProperty(\"name\", \"name\", reader)");
                    throw l2;
                } else if (label == null) {
                    JsonDataException l3 = b.l(Constants.ScionAnalytics.PARAM_LABEL, Constants.ScionAnalytics.PARAM_LABEL, iVar);
                    k.d(l3, "missingProperty(\"label\", \"label\", reader)");
                    throw l3;
                } else if (placeholder == null) {
                    JsonDataException l4 = b.l("placeholder", "placeholder", iVar);
                    k.d(l4, "missingProperty(\"placeho…der\",\n            reader)");
                    throw l4;
                } else if (minSize != null) {
                    int intValue = minSize.intValue();
                    if (maxSize != null) {
                        int intValue2 = maxSize.intValue();
                        if (text2 != null) {
                            return new Field.Text(id, name, label, placeholder, intValue, intValue2, text2);
                        }
                        JsonDataException l5 = b.l("text", "text", iVar);
                        k.d(l5, "missingProperty(\"text\", \"text\", reader)");
                        throw l5;
                    }
                    JsonDataException l6 = b.l("maxSize", "maxSize", iVar);
                    k.d(l6, "missingProperty(\"maxSize\", \"maxSize\", reader)");
                    throw l6;
                } else {
                    JsonDataException l7 = b.l("minSize", "minSize", iVar);
                    k.d(l7, "missingProperty(\"minSize\", \"minSize\", reader)");
                    throw l7;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable Field.Text value_) {
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
            writer.r("minSize");
            this.c.i(writer, Integer.valueOf(value_.h()));
            writer.r("maxSize");
            this.c.i(writer, Integer.valueOf(value_.g()));
            writer.r("text");
            this.b.i(writer, value_.i());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
