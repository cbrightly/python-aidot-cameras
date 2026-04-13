package zendesk.conversationkit.android.model;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Date;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Message.kt */
public final class l {
    @NotNull
    private final String a;
    @NotNull
    private final Author b;
    @NotNull
    private final u c;
    @NotNull
    private final Date d;
    @NotNull
    private final MessageContent e;
    @Nullable
    private final Map<String, Object> f;
    @Nullable
    private final String g;
    @NotNull
    private final String h;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof l)) {
            return false;
        }
        l lVar = (l) obj;
        return k.a(this.a, lVar.a) && k.a(this.b, lVar.b) && this.c == lVar.c && k.a(this.d, lVar.d) && k.a(this.e, lVar.e) && k.a(this.f, lVar.f) && k.a(this.g, lVar.g) && k.a(this.h, lVar.h);
    }

    public int hashCode() {
        int hashCode = ((((((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31;
        Map<String, Object> map = this.f;
        int i = 0;
        int hashCode2 = (hashCode + (map == null ? 0 : map.hashCode())) * 31;
        String str = this.g;
        if (str != null) {
            i = str.hashCode();
        }
        return ((hashCode2 + i) * 31) + this.h.hashCode();
    }

    @NotNull
    public String toString() {
        return "EssentialMessageData(id=" + this.a + ", author=" + this.b + ", status=" + this.c + ", received=" + this.d + ", content=" + this.e + ", metadata=" + this.f + ", sourceId=" + this.g + ", localId=" + this.h + ')';
    }

    public l(@NotNull String id, @NotNull Author author, @NotNull u status, @NotNull Date received, @NotNull MessageContent content, @Nullable Map<String, ? extends Object> metadata, @Nullable String sourceId, @NotNull String localId) {
        k.e(id, "id");
        k.e(author, "author");
        k.e(status, "status");
        k.e(received, "received");
        k.e(content, FirebaseAnalytics.Param.CONTENT);
        k.e(localId, "localId");
        this.a = id;
        this.b = author;
        this.c = status;
        this.d = received;
        this.e = content;
        this.f = metadata;
        this.g = sourceId;
        this.h = localId;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public l(@NotNull Message message) {
        this(message.g(), message.c(), message.m(), message.k(), message.d(), message.i(), message.l(), message.h());
        k.e(message, "message");
    }
}
