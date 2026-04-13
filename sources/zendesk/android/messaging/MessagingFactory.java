package zendesk.android.messaging;

import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import zendesk.android.d;
import zendesk.android.events.a;
import zendesk.android.messaging.model.c;
import zendesk.conversationkit.android.b;

/* compiled from: MessagingFactory.kt */
public interface MessagingFactory {
    @NotNull
    b a(@NotNull CreateParams createParams);

    /* compiled from: MessagingFactory.kt */
    public static final class CreateParams {
        @NotNull
        private final b conversationKit;
        @NotNull
        private final o0 coroutineScope;
        @NotNull
        private final d credentials;
        @NotNull
        private final p<a, kotlin.coroutines.d<? super x>, Object> dispatchEvent;
        @NotNull
        private final c messagingSettings;

        public CreateParams(@NotNull d credentials2, @NotNull b conversationKit2, @NotNull c messagingSettings2, @NotNull o0 coroutineScope2, @NotNull p<? super a, ? super kotlin.coroutines.d<? super x>, ? extends Object> dispatchEvent2) {
            k.e(credentials2, "credentials");
            k.e(conversationKit2, "conversationKit");
            k.e(messagingSettings2, "messagingSettings");
            k.e(coroutineScope2, "coroutineScope");
            k.e(dispatchEvent2, "dispatchEvent");
            this.credentials = credentials2;
            this.conversationKit = conversationKit2;
            this.messagingSettings = messagingSettings2;
            this.coroutineScope = coroutineScope2;
            this.dispatchEvent = dispatchEvent2;
        }

        @NotNull
        public final d getCredentials() {
            return this.credentials;
        }

        @NotNull
        public final b getConversationKit() {
            return this.conversationKit;
        }

        @NotNull
        public final c getMessagingSettings() {
            return this.messagingSettings;
        }

        @NotNull
        public final o0 getCoroutineScope() {
            return this.coroutineScope;
        }

        @NotNull
        public final p<a, kotlin.coroutines.d<? super x>, Object> getDispatchEvent() {
            return this.dispatchEvent;
        }
    }
}
