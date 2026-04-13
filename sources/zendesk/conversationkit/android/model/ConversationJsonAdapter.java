package zendesk.conversationkit.android.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.util.Date;
import java.util.List;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ConversationJsonAdapter.kt */
public final class ConversationJsonAdapter extends f<Conversation> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<k> d;
    @NotNull
    private final f<Boolean> e;
    @NotNull
    private final f<List<String>> f;
    @NotNull
    private final f<Date> g;
    @NotNull
    private final f<Double> h;
    @NotNull
    private final f<Participant> i;
    @NotNull
    private final f<List<Participant>> j;
    @NotNull
    private final f<List<Message>> k;

    public ConversationJsonAdapter(@NotNull r moshi) {
        r rVar = moshi;
        Class<Participant> cls = Participant.class;
        Class<String> cls2 = String.class;
        k.e(rVar, "moshi");
        i.a a2 = i.a.a("id", "displayName", "description", "iconUrl", IjkMediaMeta.IJKM_KEY_TYPE, "isDefault", "business", "businessLastRead", "lastUpdatedAt", "myself", "participants", "messages", "hasPrevious");
        k.d(a2, "of(\"id\", \"displayName\",\n…messages\", \"hasPrevious\")");
        this.a = a2;
        f<String> f2 = rVar.f(cls2, o0.d(), "id");
        k.d(f2, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f2;
        f<String> f3 = rVar.f(cls2, o0.d(), "displayName");
        k.d(f3, "moshi.adapter(String::cl…mptySet(), \"displayName\")");
        this.c = f3;
        f<k> f4 = rVar.f(k.class, o0.d(), IjkMediaMeta.IJKM_KEY_TYPE);
        k.d(f4, "moshi.adapter(Conversati…java, emptySet(), \"type\")");
        this.d = f4;
        f<Boolean> f5 = rVar.f(Boolean.TYPE, o0.d(), "isDefault");
        k.d(f5, "moshi.adapter(Boolean::c…Set(),\n      \"isDefault\")");
        this.e = f5;
        f<List<String>> f6 = rVar.f(t.j(List.class, cls2), o0.d(), "business");
        k.d(f6, "moshi.adapter(Types.newP…ySet(),\n      \"business\")");
        this.f = f6;
        f<Date> f7 = rVar.f(Date.class, o0.d(), "businessLastRead");
        k.d(f7, "moshi.adapter(Date::clas…      \"businessLastRead\")");
        this.g = f7;
        f<Double> f8 = rVar.f(Double.class, o0.d(), "lastUpdatedAt");
        k.d(f8, "moshi.adapter(Double::cl…tySet(), \"lastUpdatedAt\")");
        this.h = f8;
        f<Participant> f9 = rVar.f(cls, o0.d(), "myself");
        k.d(f9, "moshi.adapter(Participan…va, emptySet(), \"myself\")");
        this.i = f9;
        f<List<Participant>> f10 = rVar.f(t.j(List.class, cls), o0.d(), "participants");
        k.d(f10, "moshi.adapter(Types.newP…ptySet(), \"participants\")");
        this.j = f10;
        f<List<Message>> f11 = rVar.f(t.j(List.class, Message.class), o0.d(), "messages");
        k.d(f11, "moshi.adapter(Types.newP…ySet(),\n      \"messages\")");
        this.k = f11;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(34);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("Conversation");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public Conversation b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String id = null;
        String displayName = null;
        String description = null;
        String iconUrl = null;
        k type = null;
        Boolean isDefault = null;
        List business = null;
        Date businessLastRead = null;
        Double lastUpdatedAt = null;
        Participant myself = null;
        List participants = null;
        List messages = null;
        Boolean hasPrevious = null;
        reader.c();
        while (true) {
            Participant myself2 = myself;
            Double lastUpdatedAt2 = lastUpdatedAt;
            Date businessLastRead2 = businessLastRead;
            String iconUrl2 = iconUrl;
            String description2 = description;
            String displayName2 = displayName;
            Boolean hasPrevious2 = hasPrevious;
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
                            myself = myself2;
                            lastUpdatedAt = lastUpdatedAt2;
                            businessLastRead = businessLastRead2;
                            iconUrl = iconUrl2;
                            description = description2;
                            displayName = displayName2;
                            hasPrevious = hasPrevious2;
                            continue;
                        } else {
                            JsonDataException u = b.u("id", "id", iVar);
                            k.d(u, "unexpectedNull(\"id\", \"id\", reader)");
                            throw u;
                        }
                    case 1:
                        displayName = this.c.b(iVar);
                        myself = myself2;
                        lastUpdatedAt = lastUpdatedAt2;
                        businessLastRead = businessLastRead2;
                        iconUrl = iconUrl2;
                        description = description2;
                        hasPrevious = hasPrevious2;
                        continue;
                    case 2:
                        description = this.c.b(iVar);
                        myself = myself2;
                        lastUpdatedAt = lastUpdatedAt2;
                        businessLastRead = businessLastRead2;
                        iconUrl = iconUrl2;
                        displayName = displayName2;
                        hasPrevious = hasPrevious2;
                        continue;
                    case 3:
                        iconUrl = this.c.b(iVar);
                        myself = myself2;
                        lastUpdatedAt = lastUpdatedAt2;
                        businessLastRead = businessLastRead2;
                        description = description2;
                        displayName = displayName2;
                        hasPrevious = hasPrevious2;
                        continue;
                    case 4:
                        k b3 = this.d.b(iVar);
                        if (b3 != null) {
                            type = b3;
                            myself = myself2;
                            lastUpdatedAt = lastUpdatedAt2;
                            businessLastRead = businessLastRead2;
                            iconUrl = iconUrl2;
                            description = description2;
                            displayName = displayName2;
                            hasPrevious = hasPrevious2;
                            continue;
                        } else {
                            JsonDataException u2 = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                            k.d(u2, "unexpectedNull(\"type\",\n            \"type\", reader)");
                            throw u2;
                        }
                    case 5:
                        Boolean b4 = this.e.b(iVar);
                        if (b4 != null) {
                            isDefault = b4;
                            myself = myself2;
                            lastUpdatedAt = lastUpdatedAt2;
                            businessLastRead = businessLastRead2;
                            iconUrl = iconUrl2;
                            description = description2;
                            displayName = displayName2;
                            hasPrevious = hasPrevious2;
                            continue;
                        } else {
                            JsonDataException u3 = b.u("isDefault", "isDefault", iVar);
                            k.d(u3, "unexpectedNull(\"isDefaul…     \"isDefault\", reader)");
                            throw u3;
                        }
                    case 6:
                        List b5 = this.f.b(iVar);
                        if (b5 != null) {
                            business = b5;
                            myself = myself2;
                            lastUpdatedAt = lastUpdatedAt2;
                            businessLastRead = businessLastRead2;
                            iconUrl = iconUrl2;
                            description = description2;
                            displayName = displayName2;
                            hasPrevious = hasPrevious2;
                            continue;
                        } else {
                            JsonDataException u4 = b.u("business", "business", iVar);
                            k.d(u4, "unexpectedNull(\"business\", \"business\", reader)");
                            throw u4;
                        }
                    case 7:
                        businessLastRead = this.g.b(iVar);
                        myself = myself2;
                        lastUpdatedAt = lastUpdatedAt2;
                        iconUrl = iconUrl2;
                        description = description2;
                        displayName = displayName2;
                        hasPrevious = hasPrevious2;
                        continue;
                    case 8:
                        lastUpdatedAt = this.h.b(iVar);
                        myself = myself2;
                        businessLastRead = businessLastRead2;
                        iconUrl = iconUrl2;
                        description = description2;
                        displayName = displayName2;
                        hasPrevious = hasPrevious2;
                        continue;
                    case 9:
                        myself = this.i.b(iVar);
                        lastUpdatedAt = lastUpdatedAt2;
                        businessLastRead = businessLastRead2;
                        iconUrl = iconUrl2;
                        description = description2;
                        displayName = displayName2;
                        hasPrevious = hasPrevious2;
                        continue;
                    case 10:
                        List b6 = this.j.b(iVar);
                        if (b6 != null) {
                            participants = b6;
                            myself = myself2;
                            lastUpdatedAt = lastUpdatedAt2;
                            businessLastRead = businessLastRead2;
                            iconUrl = iconUrl2;
                            description = description2;
                            displayName = displayName2;
                            hasPrevious = hasPrevious2;
                            continue;
                        } else {
                            JsonDataException u5 = b.u("participants", "participants", iVar);
                            k.d(u5, "unexpectedNull(\"particip…, \"participants\", reader)");
                            throw u5;
                        }
                    case 11:
                        List b7 = this.k.b(iVar);
                        if (b7 != null) {
                            messages = b7;
                            myself = myself2;
                            lastUpdatedAt = lastUpdatedAt2;
                            businessLastRead = businessLastRead2;
                            iconUrl = iconUrl2;
                            description = description2;
                            displayName = displayName2;
                            hasPrevious = hasPrevious2;
                            continue;
                        } else {
                            JsonDataException u6 = b.u("messages", "messages", iVar);
                            k.d(u6, "unexpectedNull(\"messages\", \"messages\", reader)");
                            throw u6;
                        }
                    case 12:
                        Boolean b8 = this.e.b(iVar);
                        if (b8 != null) {
                            hasPrevious = b8;
                            myself = myself2;
                            lastUpdatedAt = lastUpdatedAt2;
                            businessLastRead = businessLastRead2;
                            iconUrl = iconUrl2;
                            description = description2;
                            displayName = displayName2;
                            continue;
                        } else {
                            JsonDataException u7 = b.u("hasPrevious", "hasPrevious", iVar);
                            k.d(u7, "unexpectedNull(\"hasPrevi…\", \"hasPrevious\", reader)");
                            throw u7;
                        }
                }
                myself = myself2;
                lastUpdatedAt = lastUpdatedAt2;
                businessLastRead = businessLastRead2;
                iconUrl = iconUrl2;
                description = description2;
                displayName = displayName2;
                hasPrevious = hasPrevious2;
            } else {
                reader.i();
                if (id == null) {
                    JsonDataException l = b.l("id", "id", iVar);
                    k.d(l, "missingProperty(\"id\", \"id\", reader)");
                    throw l;
                } else if (type == null) {
                    JsonDataException l2 = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                    k.d(l2, "missingProperty(\"type\", \"type\", reader)");
                    throw l2;
                } else if (isDefault != null) {
                    boolean booleanValue = isDefault.booleanValue();
                    if (business == null) {
                        JsonDataException l3 = b.l("business", "business", iVar);
                        k.d(l3, "missingProperty(\"business\", \"business\", reader)");
                        throw l3;
                    } else if (participants == null) {
                        JsonDataException l4 = b.l("participants", "participants", iVar);
                        k.d(l4, "missingProperty(\"partici…nts\",\n            reader)");
                        throw l4;
                    } else if (messages == null) {
                        JsonDataException l5 = b.l("messages", "messages", iVar);
                        k.d(l5, "missingProperty(\"messages\", \"messages\", reader)");
                        throw l5;
                    } else if (hasPrevious2 != null) {
                        return new Conversation(id, displayName2, description2, iconUrl2, type, booleanValue, business, businessLastRead2, lastUpdatedAt2, myself2, participants, messages, hasPrevious2.booleanValue());
                    } else {
                        JsonDataException l6 = b.l("hasPrevious", "hasPrevious", iVar);
                        k.d(l6, "missingProperty(\"hasPrev…ous\",\n            reader)");
                        throw l6;
                    }
                } else {
                    JsonDataException l7 = b.l("isDefault", "isDefault", iVar);
                    k.d(l7, "missingProperty(\"isDefault\", \"isDefault\", reader)");
                    throw l7;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable Conversation value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("id");
            this.b.i(writer, value_.i());
            writer.r("displayName");
            this.c.i(writer, value_.f());
            writer.r("description");
            this.c.i(writer, value_.e());
            writer.r("iconUrl");
            this.c.i(writer, value_.h());
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.d.i(writer, value_.n());
            writer.r("isDefault");
            this.e.i(writer, Boolean.valueOf(value_.o()));
            writer.r("business");
            this.f.i(writer, value_.c());
            writer.r("businessLastRead");
            this.g.i(writer, value_.d());
            writer.r("lastUpdatedAt");
            this.h.i(writer, value_.j());
            writer.r("myself");
            this.i.i(writer, value_.l());
            writer.r("participants");
            this.j.i(writer, value_.m());
            writer.r("messages");
            this.k.i(writer, value_.k());
            writer.r("hasPrevious");
            this.e.i(writer, Boolean.valueOf(value_.g()));
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
