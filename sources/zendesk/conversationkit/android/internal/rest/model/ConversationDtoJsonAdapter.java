package zendesk.conversationkit.android.internal.rest.model;

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
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ConversationDtoJsonAdapter.kt */
public final class ConversationDtoJsonAdapter extends f<ConversationDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<Boolean> d;
    @NotNull
    private final f<List<String>> e;
    @NotNull
    private final f<Double> f;
    @NotNull
    private final f<List<ParticipantDto>> g;
    @NotNull
    private final f<List<MessageDto>> h;

    public ConversationDtoJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("_id", "displayName", "description", "iconUrl", IjkMediaMeta.IJKM_KEY_TYPE, "isDefault", "appMakers", "appMakerLastRead", "lastUpdatedAt", "participants", "messages");
        k.d(a2, "of(\"_id\", \"displayName\",…articipants\", \"messages\")");
        this.a = a2;
        f<String> f2 = moshi.f(cls, o0.d(), "id");
        k.d(f2, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f2;
        f<String> f3 = moshi.f(cls, o0.d(), "displayName");
        k.d(f3, "moshi.adapter(String::cl…mptySet(), \"displayName\")");
        this.c = f3;
        f<Boolean> f4 = moshi.f(Boolean.TYPE, o0.d(), "isDefault");
        k.d(f4, "moshi.adapter(Boolean::c…Set(),\n      \"isDefault\")");
        this.d = f4;
        f<List<String>> f5 = moshi.f(t.j(List.class, cls), o0.d(), "appMakers");
        k.d(f5, "moshi.adapter(Types.newP…Set(),\n      \"appMakers\")");
        this.e = f5;
        f<Double> f6 = moshi.f(Double.class, o0.d(), "appMakerLastRead");
        k.d(f6, "moshi.adapter(Double::cl…et(), \"appMakerLastRead\")");
        this.f = f6;
        f<List<ParticipantDto>> f7 = moshi.f(t.j(List.class, ParticipantDto.class), o0.d(), "participants");
        k.d(f7, "moshi.adapter(Types.newP…ptySet(), \"participants\")");
        this.g = f7;
        f<List<MessageDto>> f8 = moshi.f(t.j(List.class, MessageDto.class), o0.d(), "messages");
        k.d(f8, "moshi.adapter(Types.newP…  emptySet(), \"messages\")");
        this.h = f8;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(37);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("ConversationDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public ConversationDto b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String id = null;
        String displayName = null;
        String description = null;
        String iconUrl = null;
        String type = null;
        Boolean isDefault = null;
        List appMakers = null;
        Double appMakerLastRead = null;
        Double lastUpdatedAt = null;
        List participants = null;
        List messages = null;
        reader.c();
        while (true) {
            List messages2 = messages;
            List participants2 = participants;
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
                            messages = messages2;
                            participants = participants2;
                            continue;
                        } else {
                            JsonDataException u = b.u("id", "_id", iVar);
                            k.d(u, "unexpectedNull(\"id\", \"_id\", reader)");
                            throw u;
                        }
                    case 1:
                        displayName = this.c.b(iVar);
                        messages = messages2;
                        participants = participants2;
                        continue;
                    case 2:
                        description = this.c.b(iVar);
                        messages = messages2;
                        participants = participants2;
                        continue;
                    case 3:
                        iconUrl = this.c.b(iVar);
                        messages = messages2;
                        participants = participants2;
                        continue;
                    case 4:
                        String b3 = this.b.b(iVar);
                        if (b3 != null) {
                            type = b3;
                            messages = messages2;
                            participants = participants2;
                            continue;
                        } else {
                            JsonDataException u2 = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                            k.d(u2, "unexpectedNull(\"type\", \"type\",\n            reader)");
                            throw u2;
                        }
                    case 5:
                        Boolean b4 = this.d.b(iVar);
                        if (b4 != null) {
                            isDefault = b4;
                            messages = messages2;
                            participants = participants2;
                            continue;
                        } else {
                            JsonDataException u3 = b.u("isDefault", "isDefault", iVar);
                            k.d(u3, "unexpectedNull(\"isDefaul…     \"isDefault\", reader)");
                            throw u3;
                        }
                    case 6:
                        appMakers = this.e.b(iVar);
                        messages = messages2;
                        participants = participants2;
                        continue;
                    case 7:
                        appMakerLastRead = this.f.b(iVar);
                        messages = messages2;
                        participants = participants2;
                        continue;
                    case 8:
                        lastUpdatedAt = this.f.b(iVar);
                        messages = messages2;
                        participants = participants2;
                        continue;
                    case 9:
                        participants = this.g.b(iVar);
                        messages = messages2;
                        continue;
                    case 10:
                        messages = this.h.b(iVar);
                        participants = participants2;
                        continue;
                }
                messages = messages2;
                participants = participants2;
            } else {
                reader.i();
                if (id == null) {
                    JsonDataException l = b.l("id", "_id", iVar);
                    k.d(l, "missingProperty(\"id\", \"_id\", reader)");
                    throw l;
                } else if (type == null) {
                    JsonDataException l2 = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                    k.d(l2, "missingProperty(\"type\", \"type\", reader)");
                    throw l2;
                } else if (isDefault != null) {
                    return new ConversationDto(id, displayName, description, iconUrl, type, isDefault.booleanValue(), appMakers, appMakerLastRead, lastUpdatedAt, participants2, messages2);
                } else {
                    JsonDataException l3 = b.l("isDefault", "isDefault", iVar);
                    k.d(l3, "missingProperty(\"isDefault\", \"isDefault\", reader)");
                    throw l3;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable ConversationDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("_id");
            this.b.i(writer, value_.f());
            writer.r("displayName");
            this.c.i(writer, value_.d());
            writer.r("description");
            this.c.i(writer, value_.c());
            writer.r("iconUrl");
            this.c.i(writer, value_.e());
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.b.i(writer, value_.j());
            writer.r("isDefault");
            this.d.i(writer, Boolean.valueOf(value_.k()));
            writer.r("appMakers");
            this.e.i(writer, value_.b());
            writer.r("appMakerLastRead");
            this.f.i(writer, value_.a());
            writer.r("lastUpdatedAt");
            this.f.i(writer, value_.g());
            writer.r("participants");
            this.g.i(writer, value_.i());
            writer.r("messages");
            this.h.i(writer, value_.h());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
