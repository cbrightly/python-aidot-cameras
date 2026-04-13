package zendesk.messaging.android.push;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DefaultMessagingService.kt */
public final class DefaultMessagingService extends FirebaseMessagingService {
    @NotNull
    private static final a c = new a((DefaultConstructorMarker) null);

    /* compiled from: DefaultMessagingService.kt */
    public final /* synthetic */ class b {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            iArr[b.MESSAGING_SHOULD_DISPLAY.ordinal()] = 1;
            iArr[b.MESSAGING_SHOULD_NOT_DISPLAY.ordinal()] = 2;
            iArr[b.NOT_FROM_MESSAGING.ordinal()] = 3;
            a = iArr;
        }
    }

    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        k.e(remoteMessage, "remoteMessage");
        Map<String, String> data = remoteMessage.getData();
        k.d(data, "remoteMessage.data");
        switch (b.a[a.g(data).ordinal()]) {
            case 1:
                zendesk.logger.a.b("DefaultMessagingService", "Notification received from Messaging and should be displayed", new Object[0]);
                Map<String, String> data2 = remoteMessage.getData();
                k.d(data2, "remoteMessage.data");
                a.c(this, data2);
                return;
            case 2:
                zendesk.logger.a.b("DefaultMessagingService", "Notification received from Messaging but shouldn't be displayed", new Object[0]);
                return;
            case 3:
                zendesk.logger.a.b("DefaultMessagingService", "Notification received but doesn't belong to Messaging", new Object[0]);
                return;
            default:
                return;
        }
    }

    public void onNewToken(@NotNull String newToken) {
        k.e(newToken, "newToken");
        zendesk.logger.a.b("DefaultMessagingService", k.l("New Firebase Cloud Messaging token: ", newToken), new Object[0]);
        a.h(newToken);
    }

    /* compiled from: DefaultMessagingService.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
