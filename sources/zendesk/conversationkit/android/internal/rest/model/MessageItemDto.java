package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: MessageDto.kt */
public final class MessageItemDto {
    @NotNull
    private final String a;
    @Nullable
    private final String b;
    @NotNull
    private final List<MessageActionDto> c;
    @Nullable
    private final String d;
    @Nullable
    private final Map<String, Object> e;
    @Nullable
    private final String f;
    @Nullable
    private final String g;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MessageItemDto)) {
            return false;
        }
        MessageItemDto messageItemDto = (MessageItemDto) obj;
        return k.a(this.a, messageItemDto.a) && k.a(this.b, messageItemDto.b) && k.a(this.c, messageItemDto.c) && k.a(this.d, messageItemDto.d) && k.a(this.e, messageItemDto.e) && k.a(this.f, messageItemDto.f) && k.a(this.g, messageItemDto.g);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        String str = this.b;
        int i = 0;
        int hashCode2 = (((hashCode + (str == null ? 0 : str.hashCode())) * 31) + this.c.hashCode()) * 31;
        String str2 = this.d;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Map<String, Object> map = this.e;
        int hashCode4 = (hashCode3 + (map == null ? 0 : map.hashCode())) * 31;
        String str3 = this.f;
        int hashCode5 = (hashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.g;
        if (str4 != null) {
            i = str4.hashCode();
        }
        return hashCode5 + i;
    }

    @NotNull
    public String toString() {
        return "MessageItemDto(title=" + this.a + ", description=" + this.b + ", actions=" + this.c + ", size=" + this.d + ", metadata=" + this.e + ", mediaUrl=" + this.f + ", mediaType=" + this.g + ')';
    }

    public MessageItemDto(@NotNull String title, @Nullable String description, @NotNull List<MessageActionDto> actions, @Nullable String size, @Nullable Map<String, ? extends Object> metadata, @Nullable String mediaUrl, @Nullable String mediaType) {
        k.e(title, "title");
        k.e(actions, "actions");
        this.a = title;
        this.b = description;
        this.c = actions;
        this.d = size;
        this.e = metadata;
        this.f = mediaUrl;
        this.g = mediaType;
    }

    @NotNull
    public final String g() {
        return this.a;
    }

    @Nullable
    public final String b() {
        return this.b;
    }

    @NotNull
    public final List<MessageActionDto> a() {
        return this.c;
    }

    @Nullable
    public final String f() {
        return this.d;
    }

    @Nullable
    public final Map<String, Object> e() {
        return this.e;
    }

    @Nullable
    public final String d() {
        return this.f;
    }

    @Nullable
    public final String c() {
        return this.g;
    }
}
