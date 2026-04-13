package zendesk.conversationkit.android.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import java.util.Date;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ParticipantJsonAdapter.kt */
public final class ParticipantJsonAdapter extends f<Participant> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<Integer> c;
    @NotNull
    private final f<Date> d;

    public ParticipantJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("id", "userId", "unreadCount", "lastRead");
        k.d(a2, "of(\"id\", \"userId\", \"unre…Count\",\n      \"lastRead\")");
        this.a = a2;
        f<String> f = moshi.f(String.class, o0.d(), "id");
        k.d(f, "moshi.adapter(String::cl…, emptySet(),\n      \"id\")");
        this.b = f;
        f<Integer> f2 = moshi.f(Integer.TYPE, o0.d(), "unreadCount");
        k.d(f2, "moshi.adapter(Int::class…t(),\n      \"unreadCount\")");
        this.c = f2;
        f<Date> f3 = moshi.f(Date.class, o0.d(), "lastRead");
        k.d(f3, "moshi.adapter(Date::clas…ySet(),\n      \"lastRead\")");
        this.d = f3;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(33);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("Participant");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public Participant b(@NotNull i reader) {
        k.e(reader, "reader");
        String id = null;
        String userId = null;
        Integer unreadCount = null;
        Date lastRead = null;
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
                        userId = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("userId", "userId", reader);
                        k.d(u2, "unexpectedNull(\"userId\",…        \"userId\", reader)");
                        throw u2;
                    }
                case 2:
                    Integer b4 = this.c.b(reader);
                    if (b4 != null) {
                        unreadCount = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("unreadCount", "unreadCount", reader);
                        k.d(u3, "unexpectedNull(\"unreadCo…   \"unreadCount\", reader)");
                        throw u3;
                    }
                case 3:
                    lastRead = this.d.b(reader);
                    break;
            }
        }
        reader.i();
        if (id == null) {
            JsonDataException l = b.l("id", "id", reader);
            k.d(l, "missingProperty(\"id\", \"id\", reader)");
            throw l;
        } else if (userId == null) {
            JsonDataException l2 = b.l("userId", "userId", reader);
            k.d(l2, "missingProperty(\"userId\", \"userId\", reader)");
            throw l2;
        } else if (unreadCount != null) {
            return new Participant(id, userId, unreadCount.intValue(), lastRead);
        } else {
            JsonDataException l3 = b.l("unreadCount", "unreadCount", reader);
            k.d(l3, "missingProperty(\"unreadC…unt\",\n            reader)");
            throw l3;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable Participant value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("id");
            this.b.i(writer, value_.c());
            writer.r("userId");
            this.b.i(writer, value_.f());
            writer.r("unreadCount");
            this.c.i(writer, Integer.valueOf(value_.e()));
            writer.r("lastRead");
            this.d.i(writer, value_.d());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
