package zendesk.conversationkit.android.model;

import com.google.firebase.messaging.Constants;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.util.List;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Field;

/* compiled from: Field_SelectJsonAdapter.kt */
public final class Field_SelectJsonAdapter extends f<Field.Select> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<List<FieldOption>> c;
    @NotNull
    private final f<Integer> d;

    public Field_SelectJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", "name", Constants.ScionAnalytics.PARAM_LABEL, "placeholder", "options", "selectSize", "select");
        k.d(a2, "of(\"id\", \"name\", \"label\"…, \"selectSize\", \"select\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
        f<List<FieldOption>> f2 = moshi.f(t.j(List.class, FieldOption.class), o0.d(), "options");
        k.d(f2, "moshi.adapter(Types.newP…   emptySet(), \"options\")");
        this.c = f2;
        f<Integer> f3 = moshi.f(Integer.TYPE, o0.d(), "selectSize");
        k.d(f3, "moshi.adapter(Int::class…et(),\n      \"selectSize\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(34);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("Field.Select");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public Field.Select b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String id = null;
        String name = null;
        String label = null;
        String placeholder = null;
        List options_ = null;
        Integer selectSize = null;
        List select = null;
        reader.c();
        while (true) {
            List select2 = select;
            Integer selectSize2 = selectSize;
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
                            select = select2;
                            selectSize = selectSize2;
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
                            select = select2;
                            selectSize = selectSize2;
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
                            select = select2;
                            selectSize = selectSize2;
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
                            select = select2;
                            selectSize = selectSize2;
                            continue;
                        } else {
                            JsonDataException u4 = b.u("placeholder", "placeholder", iVar);
                            k.d(u4, "unexpectedNull(\"placehol…\", \"placeholder\", reader)");
                            throw u4;
                        }
                    case 4:
                        List b6 = this.c.b(iVar);
                        if (b6 != null) {
                            options_ = b6;
                            select = select2;
                            selectSize = selectSize2;
                            continue;
                        } else {
                            JsonDataException u5 = b.u("options_", "options", iVar);
                            k.d(u5, "unexpectedNull(\"options_\", \"options\", reader)");
                            throw u5;
                        }
                    case 5:
                        selectSize = this.d.b(iVar);
                        if (selectSize != null) {
                            select = select2;
                            continue;
                        } else {
                            JsonDataException u6 = b.u("selectSize", "selectSize", iVar);
                            k.d(u6, "unexpectedNull(\"selectSi…    \"selectSize\", reader)");
                            throw u6;
                        }
                    case 6:
                        List b7 = this.c.b(iVar);
                        if (b7 != null) {
                            select = b7;
                            selectSize = selectSize2;
                            continue;
                        } else {
                            JsonDataException u7 = b.u("select", "select", iVar);
                            k.d(u7, "unexpectedNull(\"select\", \"select\", reader)");
                            throw u7;
                        }
                }
                select = select2;
                selectSize = selectSize2;
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
                } else if (options_ == null) {
                    JsonDataException l5 = b.l("options_", "options", iVar);
                    k.d(l5, "missingProperty(\"options_\", \"options\", reader)");
                    throw l5;
                } else if (selectSize2 != null) {
                    int intValue = selectSize2.intValue();
                    if (select2 != null) {
                        return new Field.Select(id, name, label, placeholder, options_, intValue, select2);
                    }
                    JsonDataException l6 = b.l("select", "select", iVar);
                    k.d(l6, "missingProperty(\"select\", \"select\", reader)");
                    throw l6;
                } else {
                    JsonDataException l7 = b.l("selectSize", "selectSize", iVar);
                    k.d(l7, "missingProperty(\"selectS…e\", \"selectSize\", reader)");
                    throw l7;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable Field.Select value_) {
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
            writer.r("options");
            this.c.i(writer, value_.g());
            writer.r("selectSize");
            this.d.i(writer, Integer.valueOf(value_.i()));
            writer.r("select");
            this.c.i(writer, value_.h());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
