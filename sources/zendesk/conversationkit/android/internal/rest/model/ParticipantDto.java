package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: ParticipantDto.kt */
public final class ParticipantDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @Nullable
    private final Integer c;
    @Nullable
    private final Double d;

    @NotNull
    public final ParticipantDto copy(@e(name = "_id") @NotNull String str, @NotNull String str2, @Nullable Integer num, @Nullable Double d2) {
        k.e(str, "id");
        k.e(str2, "appUserId");
        return new ParticipantDto(str, str2, num, d2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ParticipantDto)) {
            return false;
        }
        ParticipantDto participantDto = (ParticipantDto) obj;
        return k.a(this.a, participantDto.a) && k.a(this.b, participantDto.b) && k.a(this.c, participantDto.c) && k.a(this.d, participantDto.d);
    }

    public int hashCode() {
        int hashCode = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
        Integer num = this.c;
        int i = 0;
        int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        Double d2 = this.d;
        if (d2 != null) {
            i = d2.hashCode();
        }
        return hashCode2 + i;
    }

    @NotNull
    public String toString() {
        return "ParticipantDto(id=" + this.a + ", appUserId=" + this.b + ", unreadCount=" + this.c + ", lastRead=" + this.d + ')';
    }

    public ParticipantDto(@e(name = "_id") @NotNull String id, @NotNull String appUserId, @Nullable Integer unreadCount, @Nullable Double lastRead) {
        k.e(id, "id");
        k.e(appUserId, "appUserId");
        this.a = id;
        this.b = appUserId;
        this.c = unreadCount;
        this.d = lastRead;
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    @NotNull
    public final String a() {
        return this.b;
    }

    @Nullable
    public final Integer d() {
        return this.c;
    }

    @Nullable
    public final Double c() {
        return this.d;
    }
}
