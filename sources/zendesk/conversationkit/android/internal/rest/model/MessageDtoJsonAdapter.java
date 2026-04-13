package zendesk.conversationkit.android.internal.rest.model;

import com.google.firebase.analytics.FirebaseAnalytics;
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

/* compiled from: MessageDtoJsonAdapter.kt */
public final class MessageDtoJsonAdapter extends f<MessageDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<Double> d;
    @NotNull
    private final f<Map<String, Object>> e;
    @NotNull
    private final f<Long> f;
    @NotNull
    private final f<CoordinatesDto> g;
    @NotNull
    private final f<LocationDto> h;
    @NotNull
    private final f<List<MessageActionDto>> i;
    @NotNull
    private final f<List<MessageItemDto>> j;
    @NotNull
    private final f<DisplaySettingsDto> k;
    @NotNull
    private final f<Boolean> l;
    @NotNull
    private final f<List<MessageFieldDto>> m;
    @NotNull
    private final f<MessageSourceDto> n;

    public MessageDtoJsonAdapter(@NotNull r moshi) {
        r rVar = moshi;
        Class<String> cls = String.class;
        k.e(rVar, "moshi");
        i.a a2 = i.a.a("_id", "authorId", "role", "name", "avatarUrl", "received", IjkMediaMeta.IJKM_KEY_TYPE, "text", "textFallback", "altText", "payload", "metadata", "mediaUrl", "mediaType", "mediaSize", "coordinates", FirebaseAnalytics.Param.LOCATION, "actions", FirebaseAnalytics.Param.ITEMS, "displaySettings", "blockChatInput", "fields", "quotedMessageId", "source");
        k.d(a2, "of(\"_id\", \"authorId\", \"r…otedMessageId\", \"source\")");
        this.a = a2;
        f<String> f2 = rVar.f(cls, o0.d(), "id");
        k.d(f2, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f2;
        f<String> f3 = rVar.f(cls, o0.d(), "name");
        k.d(f3, "moshi.adapter(String::cl…      emptySet(), \"name\")");
        this.c = f3;
        f<Double> f4 = rVar.f(Double.TYPE, o0.d(), "received");
        k.d(f4, "moshi.adapter(Double::cl…ySet(),\n      \"received\")");
        this.d = f4;
        f<Map<String, Object>> f5 = rVar.f(t.j(Map.class, cls, Object.class), o0.d(), "metadata");
        k.d(f5, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.e = f5;
        f<Long> f6 = rVar.f(Long.class, o0.d(), "mediaSize");
        k.d(f6, "moshi.adapter(Long::clas… emptySet(), \"mediaSize\")");
        this.f = f6;
        f<CoordinatesDto> f7 = rVar.f(CoordinatesDto.class, o0.d(), "coordinates");
        k.d(f7, "moshi.adapter(Coordinate…mptySet(), \"coordinates\")");
        this.g = f7;
        f<LocationDto> f8 = rVar.f(LocationDto.class, o0.d(), FirebaseAnalytics.Param.LOCATION);
        k.d(f8, "moshi.adapter(LocationDt…, emptySet(), \"location\")");
        this.h = f8;
        f<List<MessageActionDto>> f9 = rVar.f(t.j(List.class, MessageActionDto.class), o0.d(), "actions");
        k.d(f9, "moshi.adapter(Types.newP…   emptySet(), \"actions\")");
        this.i = f9;
        f<List<MessageItemDto>> f10 = rVar.f(t.j(List.class, MessageItemDto.class), o0.d(), FirebaseAnalytics.Param.ITEMS);
        k.d(f10, "moshi.adapter(Types.newP…     emptySet(), \"items\")");
        this.j = f10;
        f<DisplaySettingsDto> f11 = rVar.f(DisplaySettingsDto.class, o0.d(), "displaySettings");
        k.d(f11, "moshi.adapter(DisplaySet…Set(), \"displaySettings\")");
        this.k = f11;
        f<Boolean> f12 = rVar.f(Boolean.class, o0.d(), "blockChatInput");
        k.d(f12, "moshi.adapter(Boolean::c…ySet(), \"blockChatInput\")");
        this.l = f12;
        f<List<MessageFieldDto>> f13 = rVar.f(t.j(List.class, MessageFieldDto.class), o0.d(), "fields");
        k.d(f13, "moshi.adapter(Types.newP…    emptySet(), \"fields\")");
        this.m = f13;
        f<MessageSourceDto> f14 = rVar.f(MessageSourceDto.class, o0.d(), "source");
        k.d(f14, "moshi.adapter(MessageSou…va, emptySet(), \"source\")");
        this.n = f14;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessageDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessageDto b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String id = null;
        String authorId = null;
        String role = null;
        String name = null;
        String avatarUrl = null;
        Double received = null;
        String type = null;
        String text = null;
        String textFallback = null;
        String altText = null;
        String payload = null;
        Map metadata = null;
        String mediaUrl = null;
        String mediaType = null;
        Long mediaSize = null;
        CoordinatesDto coordinates = null;
        LocationDto location = null;
        List actions = null;
        List items = null;
        DisplaySettingsDto displaySettings = null;
        Boolean blockChatInput = null;
        List fields = null;
        String quotedMessageId = null;
        MessageSourceDto source = null;
        reader.c();
        while (true) {
            String mediaType2 = mediaType;
            String mediaUrl2 = mediaUrl;
            Map metadata2 = metadata;
            String payload2 = payload;
            String altText2 = altText;
            String textFallback2 = textFallback;
            if (reader.l()) {
                String text2 = text;
                switch (iVar.J(this.a)) {
                    case -1:
                        reader.o0();
                        reader.u0();
                        break;
                    case 0:
                        String b2 = this.b.b(iVar);
                        if (b2 != null) {
                            id = b2;
                            mediaType = mediaType2;
                            mediaUrl = mediaUrl2;
                            metadata = metadata2;
                            payload = payload2;
                            altText = altText2;
                            textFallback = textFallback2;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u = b.u("id", "_id", iVar);
                            k.d(u, "unexpectedNull(\"id\", \"_id\", reader)");
                            throw u;
                        }
                    case 1:
                        String b3 = this.b.b(iVar);
                        if (b3 != null) {
                            authorId = b3;
                            mediaType = mediaType2;
                            mediaUrl = mediaUrl2;
                            metadata = metadata2;
                            payload = payload2;
                            altText = altText2;
                            textFallback = textFallback2;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u2 = b.u("authorId", "authorId", iVar);
                            k.d(u2, "unexpectedNull(\"authorId…      \"authorId\", reader)");
                            throw u2;
                        }
                    case 2:
                        String b4 = this.b.b(iVar);
                        if (b4 != null) {
                            role = b4;
                            mediaType = mediaType2;
                            mediaUrl = mediaUrl2;
                            metadata = metadata2;
                            payload = payload2;
                            altText = altText2;
                            textFallback = textFallback2;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u3 = b.u("role", "role", iVar);
                            k.d(u3, "unexpectedNull(\"role\", \"role\",\n            reader)");
                            throw u3;
                        }
                    case 3:
                        name = this.c.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 4:
                        avatarUrl = this.c.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 5:
                        Double b5 = this.d.b(iVar);
                        if (b5 != null) {
                            received = b5;
                            mediaType = mediaType2;
                            mediaUrl = mediaUrl2;
                            metadata = metadata2;
                            payload = payload2;
                            altText = altText2;
                            textFallback = textFallback2;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u4 = b.u("received", "received", iVar);
                            k.d(u4, "unexpectedNull(\"received…      \"received\", reader)");
                            throw u4;
                        }
                    case 6:
                        String b6 = this.b.b(iVar);
                        if (b6 != null) {
                            type = b6;
                            mediaType = mediaType2;
                            mediaUrl = mediaUrl2;
                            metadata = metadata2;
                            payload = payload2;
                            altText = altText2;
                            textFallback = textFallback2;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u5 = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                            k.d(u5, "unexpectedNull(\"type\", \"type\",\n            reader)");
                            throw u5;
                        }
                    case 7:
                        text = this.c.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        continue;
                    case 8:
                        textFallback = this.c.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        text = text2;
                        continue;
                    case 9:
                        altText = this.c.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 10:
                        payload = this.c.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 11:
                        metadata = this.e.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 12:
                        mediaUrl = this.c.b(iVar);
                        mediaType = mediaType2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 13:
                        mediaType = this.c.b(iVar);
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 14:
                        mediaSize = this.f.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 15:
                        coordinates = this.g.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 16:
                        location = this.h.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 17:
                        actions = this.i.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 18:
                        items = this.j.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 19:
                        displaySettings = this.k.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 20:
                        blockChatInput = this.l.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 21:
                        fields = this.m.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 22:
                        quotedMessageId = this.c.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                    case 23:
                        source = this.n.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        metadata = metadata2;
                        payload = payload2;
                        altText = altText2;
                        textFallback = textFallback2;
                        text = text2;
                        continue;
                }
                mediaType = mediaType2;
                mediaUrl = mediaUrl2;
                metadata = metadata2;
                payload = payload2;
                altText = altText2;
                textFallback = textFallback2;
                text = text2;
            } else {
                String text3 = text;
                reader.i();
                if (id == null) {
                    JsonDataException l2 = b.l("id", "_id", iVar);
                    k.d(l2, "missingProperty(\"id\", \"_id\", reader)");
                    throw l2;
                } else if (authorId == null) {
                    JsonDataException l3 = b.l("authorId", "authorId", iVar);
                    k.d(l3, "missingProperty(\"authorId\", \"authorId\", reader)");
                    throw l3;
                } else if (role == null) {
                    JsonDataException l4 = b.l("role", "role", iVar);
                    k.d(l4, "missingProperty(\"role\", \"role\", reader)");
                    throw l4;
                } else if (received != null) {
                    double doubleValue = received.doubleValue();
                    if (type != null) {
                        return new MessageDto(id, authorId, role, name, avatarUrl, doubleValue, type, text3, textFallback2, altText2, payload2, metadata2, mediaUrl2, mediaType2, mediaSize, coordinates, location, actions, items, displaySettings, blockChatInput, fields, quotedMessageId, source);
                    }
                    JsonDataException l5 = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                    k.d(l5, "missingProperty(\"type\", \"type\", reader)");
                    throw l5;
                } else {
                    JsonDataException l6 = b.l("received", "received", iVar);
                    k.d(l6, "missingProperty(\"received\", \"received\", reader)");
                    throw l6;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessageDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("_id");
            this.b.i(writer, value_.i());
            writer.r("authorId");
            this.b.i(writer, value_.c());
            writer.r("role");
            this.b.i(writer, value_.t());
            writer.r("name");
            this.c.i(writer, value_.p());
            writer.r("avatarUrl");
            this.c.i(writer, value_.d());
            writer.r("received");
            this.d.i(writer, Double.valueOf(value_.s()));
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.b.i(writer, value_.x());
            writer.r("text");
            this.c.i(writer, value_.v());
            writer.r("textFallback");
            this.c.i(writer, value_.w());
            writer.r("altText");
            this.c.i(writer, value_.b());
            writer.r("payload");
            this.c.i(writer, value_.q());
            writer.r("metadata");
            this.e.i(writer, value_.o());
            writer.r("mediaUrl");
            this.c.i(writer, value_.n());
            writer.r("mediaType");
            this.c.i(writer, value_.m());
            writer.r("mediaSize");
            this.f.i(writer, value_.l());
            writer.r("coordinates");
            this.g.i(writer, value_.f());
            writer.r(FirebaseAnalytics.Param.LOCATION);
            this.h.i(writer, value_.k());
            writer.r("actions");
            this.i.i(writer, value_.a());
            writer.r(FirebaseAnalytics.Param.ITEMS);
            this.j.i(writer, value_.j());
            writer.r("displaySettings");
            this.k.i(writer, value_.g());
            writer.r("blockChatInput");
            this.l.i(writer, value_.e());
            writer.r("fields");
            this.m.i(writer, value_.h());
            writer.r("quotedMessageId");
            this.c.i(writer, value_.r());
            writer.r("source");
            this.n.i(writer, value_.u());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
