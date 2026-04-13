package zendesk.conversationkit.android;

import kotlin.coroutines.d;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.User;
import zendesk.conversationkit.android.model.a;

/* compiled from: ConversationKit.kt */
public interface b {
    void a(@NotNull e eVar);

    @Nullable
    Object b(@NotNull Message message, @NotNull String str, @NotNull d<? super g<Message>> dVar);

    @Nullable
    Object c(@NotNull String str, @NotNull d<? super g<User>> dVar);

    @Nullable
    Object d(@NotNull d<? super g<Conversation>> dVar);

    @Nullable
    Object e(@NotNull a aVar, @NotNull String str, @NotNull d<? super x> dVar);

    @Nullable
    Object f(@NotNull d<? super g<x>> dVar);

    @Nullable
    User g();

    @Nullable
    Object h(@NotNull String str, @NotNull d<? super g<Conversation>> dVar);

    void i(@NotNull e eVar);

    @Nullable
    Object j(@NotNull d<? super x> dVar);

    @Nullable
    Object k(@NotNull d<? super g<User>> dVar);

    @Nullable
    Object l(@NotNull d<? super x> dVar);

    @Nullable
    Object m(@NotNull String str, @NotNull d<? super x> dVar);
}
