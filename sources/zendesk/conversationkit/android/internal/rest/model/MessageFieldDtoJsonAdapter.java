package zendesk.conversationkit.android.internal.rest.model;

import com.google.firebase.messaging.Constants;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.util.List;
import java.util.Map;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: MessageFieldDtoJsonAdapter.kt */
public final class MessageFieldDtoJsonAdapter extends f<MessageFieldDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Map<String, Object>> c;
    @NotNull
    private final f<String> d;
    @NotNull
    private final f<Integer> e;
    @NotNull
    private final f<List<MessageFieldOptionDto>> f;

    public MessageFieldDtoJsonAdapter(@NotNull r moshi) {
        r rVar = moshi;
        Class<String> cls = String.class;
        k.e(rVar, "moshi");
        i.a a2 = i.a.a("_id", "name", Constants.ScionAnalytics.PARAM_LABEL, IjkMediaMeta.IJKM_KEY_TYPE, "metadata", "placeholder", "text", "minSize", "maxSize", "email", "options", "select", "selectSize");
        k.d(a2, "of(\"_id\", \"name\", \"label…ect\",\n      \"selectSize\")");
        this.a = a2;
        f<String> f2 = rVar.f(cls, o0.d(), "id");
        k.d(f2, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f2;
        f<Map<String, Object>> f3 = rVar.f(t.j(Map.class, cls, Object.class), o0.d(), "metadata");
        k.d(f3, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.c = f3;
        f<String> f4 = rVar.f(cls, o0.d(), "placeholder");
        k.d(f4, "moshi.adapter(String::cl…mptySet(), \"placeholder\")");
        this.d = f4;
        f<Integer> f5 = rVar.f(Integer.class, o0.d(), "minSize");
        k.d(f5, "moshi.adapter(Int::class…   emptySet(), \"minSize\")");
        this.e = f5;
        f<List<MessageFieldOptionDto>> f6 = rVar.f(t.j(List.class, MessageFieldOptionDto.class), o0.d(), "options");
        k.d(f6, "moshi.adapter(Types.newP…), emptySet(), \"options\")");
        this.f = f6;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(37);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageFieldDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageFieldDto b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String id = null;
        String name = null;
        String label = null;
        String type = null;
        Map metadata = null;
        String placeholder = null;
        String text = null;
        Integer minSize = null;
        Integer maxSize = null;
        String email = null;
        List options_ = null;
        List select = null;
        Integer selectSize = null;
        reader.c();
        while (true) {
            Integer selectSize2 = selectSize;
            List select2 = select;
            List options_2 = options_;
            String email2 = email;
            Integer maxSize2 = maxSize;
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
                            selectSize = selectSize2;
                            select = select2;
                            options_ = options_2;
                            email = email2;
                            maxSize = maxSize2;
                            continue;
                        } else {
                            JsonDataException u = b.u("id", "_id", iVar);
                            k.d(u, "unexpectedNull(\"id\", \"_id\", reader)");
                            throw u;
                        }
                    case 1:
                        String b3 = this.b.b(iVar);
                        if (b3 != null) {
                            name = b3;
                            selectSize = selectSize2;
                            select = select2;
                            options_ = options_2;
                            email = email2;
                            maxSize = maxSize2;
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
                            selectSize = selectSize2;
                            select = select2;
                            options_ = options_2;
                            email = email2;
                            maxSize = maxSize2;
                            continue;
                        } else {
                            JsonDataException u3 = b.u(Constants.ScionAnalytics.PARAM_LABEL, Constants.ScionAnalytics.PARAM_LABEL, iVar);
                            k.d(u3, "unexpectedNull(\"label\", …bel\",\n            reader)");
                            throw u3;
                        }
                    case 3:
                        String b5 = this.b.b(iVar);
                        if (b5 != null) {
                            type = b5;
                            selectSize = selectSize2;
                            select = select2;
                            options_ = options_2;
                            email = email2;
                            maxSize = maxSize2;
                            continue;
                        } else {
                            JsonDataException u4 = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                            k.d(u4, "unexpectedNull(\"type\", \"type\",\n            reader)");
                            throw u4;
                        }
                    case 4:
                        metadata = this.c.b(iVar);
                        selectSize = selectSize2;
                        select = select2;
                        options_ = options_2;
                        email = email2;
                        maxSize = maxSize2;
                        continue;
                    case 5:
                        placeholder = this.d.b(iVar);
                        selectSize = selectSize2;
                        select = select2;
                        options_ = options_2;
                        email = email2;
                        maxSize = maxSize2;
                        continue;
                    case 6:
                        text = this.d.b(iVar);
                        selectSize = selectSize2;
                        select = select2;
                        options_ = options_2;
                        email = email2;
                        maxSize = maxSize2;
                        continue;
                    case 7:
                        minSize = this.e.b(iVar);
                        selectSize = selectSize2;
                        select = select2;
                        options_ = options_2;
                        email = email2;
                        maxSize = maxSize2;
                        continue;
                    case 8:
                        maxSize = this.e.b(iVar);
                        selectSize = selectSize2;
                        select = select2;
                        options_ = options_2;
                        email = email2;
                        continue;
                    case 9:
                        email = this.d.b(iVar);
                        selectSize = selectSize2;
                        select = select2;
                        options_ = options_2;
                        maxSize = maxSize2;
                        continue;
                    case 10:
                        options_ = this.f.b(iVar);
                        selectSize = selectSize2;
                        select = select2;
                        email = email2;
                        maxSize = maxSize2;
                        continue;
                    case 11:
                        select = this.f.b(iVar);
                        selectSize = selectSize2;
                        options_ = options_2;
                        email = email2;
                        maxSize = maxSize2;
                        continue;
                    case 12:
                        selectSize = this.e.b(iVar);
                        select = select2;
                        options_ = options_2;
                        email = email2;
                        maxSize = maxSize2;
                        continue;
                }
                selectSize = selectSize2;
                select = select2;
                options_ = options_2;
                email = email2;
                maxSize = maxSize2;
            } else {
                reader.i();
                if (id == null) {
                    JsonDataException l = b.l("id", "_id", iVar);
                    k.d(l, "missingProperty(\"id\", \"_id\", reader)");
                    throw l;
                } else if (name == null) {
                    JsonDataException l2 = b.l("name", "name", iVar);
                    k.d(l2, "missingProperty(\"name\", \"name\", reader)");
                    throw l2;
                } else if (label == null) {
                    JsonDataException l3 = b.l(Constants.ScionAnalytics.PARAM_LABEL, Constants.ScionAnalytics.PARAM_LABEL, iVar);
                    k.d(l3, "missingProperty(\"label\", \"label\", reader)");
                    throw l3;
                } else if (type != null) {
                    return new MessageFieldDto(id, name, label, type, metadata, placeholder, text, minSize, maxSize2, email2, options_2, select2, selectSize2);
                } else {
                    JsonDataException l4 = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                    k.d(l4, "missingProperty(\"type\", \"type\", reader)");
                    throw l4;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageFieldDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("_id");
            this.b.i(writer, value_.b());
            writer.r("name");
            this.b.i(writer, value_.g());
            writer.r(Constants.ScionAnalytics.PARAM_LABEL);
            this.b.i(writer, value_.c());
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.b.i(writer, value_.m());
            writer.r("metadata");
            this.c.i(writer, value_.e());
            writer.r("placeholder");
            this.d.i(writer, value_.i());
            writer.r("text");
            this.d.i(writer, value_.l());
            writer.r("minSize");
            this.e.i(writer, value_.f());
            writer.r("maxSize");
            this.e.i(writer, value_.d());
            writer.r("email");
            this.d.i(writer, value_.a());
            writer.r("options");
            this.f.i(writer, value_.h());
            writer.r("select");
            this.f.i(writer, value_.j());
            writer.r("selectSize");
            this.e.i(writer, value_.k());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
