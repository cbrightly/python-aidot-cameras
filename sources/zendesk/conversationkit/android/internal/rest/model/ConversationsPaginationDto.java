package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: ConversationsPaginationDto.kt */
public final class ConversationsPaginationDto {
    @Nullable
    private final Boolean a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ConversationsPaginationDto) && k.a(this.a, ((ConversationsPaginationDto) obj).a);
    }

    public int hashCode() {
        Boolean bool = this.a;
        if (bool == null) {
            return 0;
        }
        return bool.hashCode();
    }

    @NotNull
    public String toString() {
        return "ConversationsPaginationDto(hasMore=" + this.a + ')';
    }

    public ConversationsPaginationDto(@Nullable Boolean hasMore) {
        this.a = hasMore;
    }

    @Nullable
    public final Boolean a() {
        return this.a;
    }
}
