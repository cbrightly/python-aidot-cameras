package zendesk.conversationkit.android.model;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.util.Date;
import java.util.Map;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessageJsonAdapter.kt */
public final class MessageJsonAdapter extends f<Message> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Author> c;
    @NotNull
    private final f<u> d;
    @NotNull
    private final f<Date> e;
    @NotNull
    private final f<Date> f;
    @NotNull
    private final f<MessageContent> g;
    @NotNull
    private final f<Map<String, Object>> h;
    @NotNull
    private final f<String> i;

    public MessageJsonAdapter(@NotNull r moshi) {
        Class<String> cls = String.class;
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", "author", "status", "created", "received", FirebaseAnalytics.Param.CONTENT, "metadata", "sourceId", "localId", "payload");
        k.d(a2, "of(\"id\", \"author\", \"stat…d\", \"localId\", \"payload\")");
        this.a = a2;
        f<String> f2 = moshi.f(cls, o0.d(), "id");
        k.d(f2, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f2;
        f<Author> f3 = moshi.f(Author.class, o0.d(), "author");
        k.d(f3, "moshi.adapter(Author::cl…ptySet(),\n      \"author\")");
        this.c = f3;
        f<u> f4 = moshi.f(u.class, o0.d(), "status");
        k.d(f4, "moshi.adapter(MessageSta…va, emptySet(), \"status\")");
        this.d = f4;
        f<Date> f5 = moshi.f(Date.class, o0.d(), "created");
        k.d(f5, "moshi.adapter(Date::clas…tySet(),\n      \"created\")");
        this.e = f5;
        f<Date> f6 = moshi.f(Date.class, o0.d(), "received");
        k.d(f6, "moshi.adapter(Date::clas…ySet(),\n      \"received\")");
        this.f = f6;
        f<MessageContent> f7 = moshi.f(MessageContent.class, o0.d(), FirebaseAnalytics.Param.CONTENT);
        k.d(f7, "moshi.adapter(MessageCon…a, emptySet(), \"content\")");
        this.g = f7;
        f<Map<String, Object>> f8 = moshi.f(t.j(Map.class, cls, Object.class), o0.d(), "metadata");
        k.d(f8, "moshi.adapter(Types.newP…, emptySet(), \"metadata\")");
        this.h = f8;
        f<String> f9 = moshi.f(cls, o0.d(), "sourceId");
        k.d(f9, "moshi.adapter(String::cl…  emptySet(), \"sourceId\")");
        this.i = f9;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(29);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("Message");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public Message b(@NotNull i reader) {
        i iVar = reader;
        k.e(iVar, "reader");
        String id = null;
        Author author = null;
        u status = null;
        Date created = null;
        Date received = null;
        MessageContent content = null;
        Map metadata = null;
        String sourceId = null;
        String localId = null;
        String payload = null;
        reader.c();
        while (true) {
            String payload2 = payload;
            String sourceId2 = sourceId;
            Map metadata2 = metadata;
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
                            payload = payload2;
                            sourceId = sourceId2;
                            metadata = metadata2;
                            continue;
                        } else {
                            JsonDataException u = b.u("id", "id", iVar);
                            k.d(u, "unexpectedNull(\"id\", \"id\", reader)");
                            throw u;
                        }
                    case 1:
                        Author b3 = this.c.b(iVar);
                        if (b3 != null) {
                            author = b3;
                            payload = payload2;
                            sourceId = sourceId2;
                            metadata = metadata2;
                            continue;
                        } else {
                            JsonDataException u2 = b.u("author", "author", iVar);
                            k.d(u2, "unexpectedNull(\"author\",…        \"author\", reader)");
                            throw u2;
                        }
                    case 2:
                        u b4 = this.d.b(iVar);
                        if (b4 != null) {
                            status = b4;
                            payload = payload2;
                            sourceId = sourceId2;
                            metadata = metadata2;
                            continue;
                        } else {
                            JsonDataException u3 = b.u("status", "status", iVar);
                            k.d(u3, "unexpectedNull(\"status\",…        \"status\", reader)");
                            throw u3;
                        }
                    case 3:
                        created = this.e.b(iVar);
                        payload = payload2;
                        sourceId = sourceId2;
                        metadata = metadata2;
                        continue;
                    case 4:
                        Date b5 = this.f.b(iVar);
                        if (b5 != null) {
                            received = b5;
                            payload = payload2;
                            sourceId = sourceId2;
                            metadata = metadata2;
                            continue;
                        } else {
                            JsonDataException u4 = b.u("received", "received", iVar);
                            k.d(u4, "unexpectedNull(\"received…      \"received\", reader)");
                            throw u4;
                        }
                    case 5:
                        MessageContent b6 = this.g.b(iVar);
                        if (b6 != null) {
                            content = b6;
                            payload = payload2;
                            sourceId = sourceId2;
                            metadata = metadata2;
                            continue;
                        } else {
                            JsonDataException u5 = b.u(FirebaseAnalytics.Param.CONTENT, FirebaseAnalytics.Param.CONTENT, iVar);
                            k.d(u5, "unexpectedNull(\"content\", \"content\", reader)");
                            throw u5;
                        }
                    case 6:
                        metadata = this.h.b(iVar);
                        payload = payload2;
                        sourceId = sourceId2;
                        continue;
                    case 7:
                        sourceId = this.i.b(iVar);
                        payload = payload2;
                        metadata = metadata2;
                        continue;
                    case 8:
                        String b7 = this.b.b(iVar);
                        if (b7 != null) {
                            localId = b7;
                            payload = payload2;
                            sourceId = sourceId2;
                            metadata = metadata2;
                            continue;
                        } else {
                            JsonDataException u6 = b.u("localId", "localId", iVar);
                            k.d(u6, "unexpectedNull(\"localId\"…       \"localId\", reader)");
                            throw u6;
                        }
                    case 9:
                        payload = this.i.b(iVar);
                        sourceId = sourceId2;
                        metadata = metadata2;
                        continue;
                }
                payload = payload2;
                sourceId = sourceId2;
                metadata = metadata2;
            } else {
                reader.i();
                if (id == null) {
                    JsonDataException l = b.l("id", "id", iVar);
                    k.d(l, "missingProperty(\"id\", \"id\", reader)");
                    throw l;
                } else if (author == null) {
                    JsonDataException l2 = b.l("author", "author", iVar);
                    k.d(l2, "missingProperty(\"author\", \"author\", reader)");
                    throw l2;
                } else if (status == null) {
                    JsonDataException l3 = b.l("status", "status", iVar);
                    k.d(l3, "missingProperty(\"status\", \"status\", reader)");
                    throw l3;
                } else if (received == null) {
                    JsonDataException l4 = b.l("received", "received", iVar);
                    k.d(l4, "missingProperty(\"received\", \"received\", reader)");
                    throw l4;
                } else if (content == null) {
                    JsonDataException l5 = b.l(FirebaseAnalytics.Param.CONTENT, FirebaseAnalytics.Param.CONTENT, iVar);
                    k.d(l5, "missingProperty(\"content\", \"content\", reader)");
                    throw l5;
                } else if (localId != null) {
                    return new Message(id, author, status, created, received, content, metadata2, sourceId2, localId, payload2);
                } else {
                    JsonDataException l6 = b.l("localId", "localId", iVar);
                    k.d(l6, "missingProperty(\"localId\", \"localId\", reader)");
                    throw l6;
                }
            }
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable Message value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("id");
            this.b.i(writer, value_.g());
            writer.r("author");
            this.c.i(writer, value_.c());
            writer.r("status");
            this.d.i(writer, value_.m());
            writer.r("created");
            this.e.i(writer, value_.e());
            writer.r("received");
            this.f.i(writer, value_.k());
            writer.r(FirebaseAnalytics.Param.CONTENT);
            this.g.i(writer, value_.d());
            writer.r("metadata");
            this.h.i(writer, value_.i());
            writer.r("sourceId");
            this.i.i(writer, value_.l());
            writer.r("localId");
            this.b.i(writer, value_.h());
            writer.r("payload");
            this.i.i(writer, value_.j());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
