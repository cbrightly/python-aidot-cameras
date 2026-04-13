package zendesk.messaging.android.internal.conversationscreen.cache;

import com.squareup.moshi.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: MessagingUIPersistence.kt */
public final class MessagingUIPersistence {
    @NotNull
    private final String a;
    @NotNull
    private final String b;

    public static /* synthetic */ MessagingUIPersistence b(MessagingUIPersistence messagingUIPersistence, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = messagingUIPersistence.a;
        }
        if ((i & 2) != 0) {
            str2 = messagingUIPersistence.b;
        }
        return messagingUIPersistence.a(str, str2);
    }

    @NotNull
    public final MessagingUIPersistence a(@NotNull String str, @NotNull String str2) {
        k.e(str, "conversationId");
        k.e(str2, "composerText");
        return new MessagingUIPersistence(str, str2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MessagingUIPersistence)) {
            return false;
        }
        MessagingUIPersistence messagingUIPersistence = (MessagingUIPersistence) obj;
        return k.a(this.a, messagingUIPersistence.a) && k.a(this.b, messagingUIPersistence.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "MessagingUIPersistence(conversationId=" + this.a + ", composerText=" + this.b + ')';
    }

    public MessagingUIPersistence(@NotNull String conversationId, @NotNull String composerText) {
        k.e(conversationId, "conversationId");
        k.e(composerText, "composerText");
        this.a = conversationId;
        this.b = composerText;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ MessagingUIPersistence(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? "" : str2);
    }

    @NotNull
    public final String d() {
        return this.a;
    }

    @NotNull
    public final String c() {
        return this.b;
    }
}
