package zendesk.conversationkit.android.model;

import com.squareup.moshi.g;
import java.util.Date;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: Participant.kt */
public final class Participant {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    private final int c;
    @Nullable
    private final Date d;

    public static /* synthetic */ Participant b(Participant participant, String str, String str2, int i, Date date, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = participant.a;
        }
        if ((i2 & 2) != 0) {
            str2 = participant.b;
        }
        if ((i2 & 4) != 0) {
            i = participant.c;
        }
        if ((i2 & 8) != 0) {
            date = participant.d;
        }
        return participant.a(str, str2, i, date);
    }

    @NotNull
    public final Participant a(@NotNull String str, @NotNull String str2, int i, @Nullable Date date) {
        k.e(str, "id");
        k.e(str2, "userId");
        return new Participant(str, str2, i, date);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Participant)) {
            return false;
        }
        Participant participant = (Participant) obj;
        return k.a(this.a, participant.a) && k.a(this.b, participant.b) && this.c == participant.c && k.a(this.d, participant.d);
    }

    public int hashCode() {
        int hashCode = ((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c) * 31;
        Date date = this.d;
        return hashCode + (date == null ? 0 : date.hashCode());
    }

    @NotNull
    public String toString() {
        return "Participant(id=" + this.a + ", userId=" + this.b + ", unreadCount=" + this.c + ", lastRead=" + this.d + ')';
    }

    public Participant(@NotNull String id, @NotNull String userId, int unreadCount, @Nullable Date lastRead) {
        k.e(id, "id");
        k.e(userId, "userId");
        this.a = id;
        this.b = userId;
        this.c = unreadCount;
        this.d = lastRead;
    }

    @NotNull
    public final String c() {
        return this.a;
    }

    @NotNull
    public final String f() {
        return this.b;
    }

    public final int e() {
        return this.c;
    }

    @Nullable
    public final Date d() {
        return this.d;
    }
}
