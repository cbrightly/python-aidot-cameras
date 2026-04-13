package zendesk.android.internal;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.internal.i;
import zendesk.conversationkit.android.b;
import zendesk.conversationkit.android.g;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.User;
import zendesk.logger.a;

/* compiled from: NotInitializedConversationKit.kt */
public final class e implements b {
    @NotNull
    public static final e a = new e();

    private e() {
    }

    public void i(@NotNull zendesk.conversationkit.android.e listener) {
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        a.h("Zendesk", i.e.INSTANCE.getMessage(), new Object[0]);
    }

    public void a(@NotNull zendesk.conversationkit.android.e listener) {
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        a.h("Zendesk", i.e.INSTANCE.getMessage(), new Object[0]);
    }

    @Nullable
    public Object j(@NotNull d<? super x> $completion) {
        a.h("Zendesk", i.e.INSTANCE.getMessage(), new Object[0]);
        return x.a;
    }

    @Nullable
    public Object l(@NotNull d<? super x> $completion) {
        a.h("Zendesk", i.e.INSTANCE.getMessage(), new Object[0]);
        return x.a;
    }

    @Nullable
    public Object k(@NotNull d<? super g<User>> $completion) {
        i.e eVar = i.e.INSTANCE;
        a.h("Zendesk", eVar.getMessage(), new Object[0]);
        return new g.a(eVar);
    }

    @Nullable
    public Object c(@NotNull String jwt, @NotNull d<? super g<User>> $completion) {
        i.e eVar = i.e.INSTANCE;
        a.h("Zendesk", eVar.getMessage(), new Object[0]);
        return new g.a(eVar);
    }

    @Nullable
    public User g() {
        return null;
    }

    @Nullable
    public Object f(@NotNull d<? super g<x>> $completion) {
        i.e eVar = i.e.INSTANCE;
        a.h("Zendesk", eVar.getMessage(), new Object[0]);
        return new g.a(eVar);
    }

    @Nullable
    public Object d(@NotNull d<? super g<Conversation>> $completion) {
        i.e eVar = i.e.INSTANCE;
        a.h("Zendesk", eVar.getMessage(), new Object[0]);
        return new g.a(eVar);
    }

    @Nullable
    public Object h(@NotNull String conversationId, @NotNull d<? super g<Conversation>> $completion) {
        i.e eVar = i.e.INSTANCE;
        a.h("Zendesk", eVar.getMessage(), new Object[0]);
        return new g.a(eVar);
    }

    @Nullable
    public Object b(@NotNull Message message, @NotNull String conversationId, @NotNull d<? super g<Message>> $completion) {
        i.e eVar = i.e.INSTANCE;
        a.h("Zendesk", eVar.getMessage(), new Object[0]);
        return new g.a(eVar);
    }

    @Nullable
    public Object m(@NotNull String pushNotificationToken, @NotNull d<? super x> $completion) {
        a.h("Zendesk", i.e.INSTANCE.getMessage(), new Object[0]);
        return x.a;
    }

    @Nullable
    public Object e(@NotNull zendesk.conversationkit.android.model.a activityData, @NotNull String conversationId, @NotNull d<? super x> $completion) {
        a.h("Zendesk", i.e.INSTANCE.getMessage(), new Object[0]);
        return x.a;
    }
}
