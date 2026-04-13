package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: ActivityDataRequestDto.kt */
public final class ActivityDataRequestDto {
    @NotNull
    private final AuthorDto a;
    @NotNull
    private final ActivityDataDto b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActivityDataRequestDto)) {
            return false;
        }
        ActivityDataRequestDto activityDataRequestDto = (ActivityDataRequestDto) obj;
        return k.a(this.a, activityDataRequestDto.a) && k.a(this.b, activityDataRequestDto.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "ActivityDataRequestDto(author=" + this.a + ", activity=" + this.b + ')';
    }

    public ActivityDataRequestDto(@NotNull AuthorDto author, @NotNull ActivityDataDto activity) {
        k.e(author, "author");
        k.e(activity, "activity");
        this.a = author;
        this.b = activity;
    }

    @NotNull
    public final AuthorDto b() {
        return this.a;
    }

    @NotNull
    public final ActivityDataDto a() {
        return this.b;
    }
}
