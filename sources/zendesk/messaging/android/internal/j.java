package zendesk.messaging.android.internal;

import kotlin.jvm.internal.k;
import kotlinx.coroutines.flow.q;
import kotlinx.coroutines.flow.x;
import kotlinx.coroutines.flow.z;
import org.jetbrains.annotations.NotNull;
import zendesk.android.messaging.d;

/* compiled from: ScreenStateStore.kt */
public final class j {
    @NotNull
    public static final j a = new j();
    @NotNull
    private static final q<d> b = z.a(null);

    private j() {
    }

    @NotNull
    public final x<d> b() {
        return b;
    }

    public final void c(@NotNull d $this$setAsVisibleMessagingScreen) {
        k.e($this$setAsVisibleMessagingScreen, "<this>");
        b.setValue($this$setAsVisibleMessagingScreen);
    }

    public final void a(@NotNull d $this$clearAsVisibleMessagingScreen) {
        k.e($this$clearAsVisibleMessagingScreen, "<this>");
        q<d> qVar = b;
        if (k.a(qVar.getValue(), $this$clearAsVisibleMessagingScreen)) {
            qVar.setValue(null);
        }
    }
}
