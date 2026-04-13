package zendesk.conversationkit.android.model;

import com.squareup.moshi.g;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: MessageItem.kt */
public final class MessageItem {
    @NotNull
    private final String a;
    @Nullable
    private final String b;
    @NotNull
    private final List<MessageAction> c;
    @NotNull
    private final s d;
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
        if (!(obj instanceof MessageItem)) {
            return false;
        }
        MessageItem messageItem = (MessageItem) obj;
        return k.a(this.a, messageItem.a) && k.a(this.b, messageItem.b) && k.a(this.c, messageItem.c) && this.d == messageItem.d && k.a(this.e, messageItem.e) && k.a(this.f, messageItem.f) && k.a(this.g, messageItem.g);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        String str = this.b;
        int i = 0;
        int hashCode2 = (((((hashCode + (str == null ? 0 : str.hashCode())) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31;
        Map<String, Object> map = this.e;
        int hashCode3 = (hashCode2 + (map == null ? 0 : map.hashCode())) * 31;
        String str2 = this.f;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.g;
        if (str3 != null) {
            i = str3.hashCode();
        }
        return hashCode4 + i;
    }

    @NotNull
    public String toString() {
        return "MessageItem(title=" + this.a + ", description=" + this.b + ", actions=" + this.c + ", size=" + this.d + ", metadata=" + this.e + ", mediaUrl=" + this.f + ", mediaType=" + this.g + ')';
    }

    public MessageItem(@NotNull String title, @Nullable String description, @NotNull List<? extends MessageAction> actions, @NotNull s size, @Nullable Map<String, ? extends Object> metadata, @Nullable String mediaUrl, @Nullable String mediaType) {
        k.e(title, "title");
        k.e(actions, "actions");
        k.e(size, "size");
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
    public final List<MessageAction> a() {
        return this.c;
    }

    @NotNull
    public final s f() {
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
