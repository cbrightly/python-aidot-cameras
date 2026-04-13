package zendesk.conversationkit.android.internal.rest.model;

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

/* compiled from: ParticipantDtoJsonAdapter.kt */
public final class ParticipantDtoJsonAdapter extends f<ParticipantDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Integer> c;
    @NotNull
    private final f<Double> d;

    public ParticipantDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("_id", "appUserId", "unreadCount", "lastRead");
        k.d(a2, "of(\"_id\", \"appUserId\", \"…Count\",\n      \"lastRead\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
        f<Integer> f2 = moshi.f(Integer.class, o0.d(), "unreadCount");
        k.d(f2, "moshi.adapter(Int::class…mptySet(), \"unreadCount\")");
        this.c = f2;
        f<Double> f3 = moshi.f(Double.class, o0.d(), "lastRead");
        k.d(f3, "moshi.adapter(Double::cl…, emptySet(), \"lastRead\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(36);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("ParticipantDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public ParticipantDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String id = null;
        String appUserId = null;
        Integer unreadCount = null;
        Double lastRead = null;
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
                        appUserId = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("appUserId", "appUserId", reader);
                        k.d(u2, "unexpectedNull(\"appUserI…     \"appUserId\", reader)");
                        throw u2;
                    }
                case 2:
                    unreadCount = this.c.b(reader);
                    break;
                case 3:
                    lastRead = this.d.b(reader);
                    break;
            }
        }
        reader.i();
        if (id == null) {
            JsonDataException l = b.l("id", "_id", reader);
            k.d(l, "missingProperty(\"id\", \"_id\", reader)");
            throw l;
        } else if (appUserId != null) {
            return new ParticipantDto(id, appUserId, unreadCount, lastRead);
        } else {
            JsonDataException l2 = b.l("appUserId", "appUserId", reader);
            k.d(l2, "missingProperty(\"appUserId\", \"appUserId\", reader)");
            throw l2;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable ParticipantDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("_id");
            this.b.i(writer, value_.b());
            writer.r("appUserId");
            this.b.i(writer, value_.a());
            writer.r("unreadCount");
            this.c.i(writer, value_.d());
            writer.r("lastRead");
            this.d.i(writer, value_.c());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
