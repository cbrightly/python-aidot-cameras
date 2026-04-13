package zendesk.messaging.android.push.internal;

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
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: MessagePayloadJsonAdapter.kt */
public final class MessagePayloadJsonAdapter extends f<MessagePayload> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<String> c;
    @NotNull
    private final f<Double> d;
    @NotNull
    private final f<Long> e;

    public MessagePayloadJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("_id", "authorId", "role", "name", "avatarUrl", "received", IjkMediaMeta.IJKM_KEY_TYPE, "text", "mediaUrl", "mediaType", "mediaSize");
        k.d(a2, "of(\"_id\", \"authorId\", \"r…\"mediaType\", \"mediaSize\")");
        this.a = a2;
        f<String> f = moshi.f(cls, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
        f<String> f2 = moshi.f(cls, o0.d(), "name");
        k.d(f2, "moshi.adapter(String::cl…      emptySet(), \"name\")");
        this.c = f2;
        f<Double> f3 = moshi.f(Double.TYPE, o0.d(), "received");
        k.d(f3, "moshi.adapter(Double::cl…ySet(),\n      \"received\")");
        this.d = f3;
        f<Long> f4 = moshi.f(Long.class, o0.d(), "mediaSize");
        k.d(f4, "moshi.adapter(Long::clas… emptySet(), \"mediaSize\")");
        this.e = f4;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(36);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("MessagePayload");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public MessagePayload b(@NotNull i reader) {
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
        String mediaUrl = null;
        String mediaType = null;
        Long mediaSize = null;
        reader.c();
        while (true) {
            Long mediaSize2 = mediaSize;
            String mediaType2 = mediaType;
            String mediaUrl2 = mediaUrl;
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
                            mediaSize = mediaSize2;
                            mediaType = mediaType2;
                            mediaUrl = mediaUrl2;
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
                            mediaSize = mediaSize2;
                            mediaType = mediaType2;
                            mediaUrl = mediaUrl2;
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
                            mediaSize = mediaSize2;
                            mediaType = mediaType2;
                            mediaUrl = mediaUrl2;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u3 = b.u("role", "role", iVar);
                            k.d(u3, "unexpectedNull(\"role\", \"role\",\n            reader)");
                            throw u3;
                        }
                    case 3:
                        name = this.c.b(iVar);
                        mediaSize = mediaSize2;
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        text = text2;
                        continue;
                    case 4:
                        avatarUrl = this.c.b(iVar);
                        mediaSize = mediaSize2;
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        text = text2;
                        continue;
                    case 5:
                        Double b5 = this.d.b(iVar);
                        if (b5 != null) {
                            received = b5;
                            mediaSize = mediaSize2;
                            mediaType = mediaType2;
                            mediaUrl = mediaUrl2;
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
                            mediaSize = mediaSize2;
                            mediaType = mediaType2;
                            mediaUrl = mediaUrl2;
                            text = text2;
                            continue;
                        } else {
                            JsonDataException u5 = b.u(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                            k.d(u5, "unexpectedNull(\"type\", \"type\",\n            reader)");
                            throw u5;
                        }
                    case 7:
                        text = this.c.b(iVar);
                        mediaSize = mediaSize2;
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        continue;
                    case 8:
                        mediaUrl = this.c.b(iVar);
                        mediaSize = mediaSize2;
                        mediaType = mediaType2;
                        text = text2;
                        continue;
                    case 9:
                        mediaType = this.c.b(iVar);
                        mediaSize = mediaSize2;
                        mediaUrl = mediaUrl2;
                        text = text2;
                        continue;
                    case 10:
                        mediaSize = this.e.b(iVar);
                        mediaType = mediaType2;
                        mediaUrl = mediaUrl2;
                        text = text2;
                        continue;
                }
                mediaSize = mediaSize2;
                mediaType = mediaType2;
                mediaUrl = mediaUrl2;
                text = text2;
            } else {
                reader.i();
                if (id == null) {
                    JsonDataException l = b.l("id", "_id", iVar);
                    k.d(l, "missingProperty(\"id\", \"_id\", reader)");
                    throw l;
                } else if (authorId == null) {
                    JsonDataException l2 = b.l("authorId", "authorId", iVar);
                    k.d(l2, "missingProperty(\"authorId\", \"authorId\", reader)");
                    throw l2;
                } else if (role == null) {
                    JsonDataException l3 = b.l("role", "role", iVar);
                    k.d(l3, "missingProperty(\"role\", \"role\", reader)");
                    throw l3;
                } else if (received != null) {
                    double doubleValue = received.doubleValue();
                    if (type != null) {
                        return new MessagePayload(id, authorId, role, name, avatarUrl, doubleValue, type, text2, mediaUrl2, mediaType2, mediaSize2);
                    }
                    JsonDataException l4 = b.l(IjkMediaMeta.IJKM_KEY_TYPE, IjkMediaMeta.IJKM_KEY_TYPE, iVar);
                    k.d(l4, "missingProperty(\"type\", \"type\", reader)");
                    throw l4;
                } else {
                    JsonDataException l5 = b.l("received", "received", iVar);
                    k.d(l5, "missingProperty(\"received\", \"received\", reader)");
                    throw l5;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable MessagePayload value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("_id");
            this.b.i(writer, value_.c());
            writer.r("authorId");
            this.b.i(writer, value_.a());
            writer.r("role");
            this.b.i(writer, value_.i());
            writer.r("name");
            this.c.i(writer, value_.g());
            writer.r("avatarUrl");
            this.c.i(writer, value_.b());
            writer.r("received");
            this.d.i(writer, Double.valueOf(value_.h()));
            writer.r(IjkMediaMeta.IJKM_KEY_TYPE);
            this.b.i(writer, value_.k());
            writer.r("text");
            this.c.i(writer, value_.j());
            writer.r("mediaUrl");
            this.c.i(writer, value_.f());
            writer.r("mediaType");
            this.c.i(writer, value_.e());
            writer.r("mediaSize");
            this.e.i(writer, value_.d());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
