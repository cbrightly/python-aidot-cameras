package zendesk.messaging.android.internal;

import android.os.Build;
import org.jetbrains.annotations.NotNull;

/* compiled from: MessagingBuildConfig.kt */
public final class h {
    @NotNull
    public static final h a = new h();

    private h() {
    }

    public final boolean a() {
        return Build.VERSION.SDK_INT >= 26;
    }
}
