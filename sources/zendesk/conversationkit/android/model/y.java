package zendesk.conversationkit.android.model;

import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.internal.rest.model.RestRetryPolicyDto;

/* compiled from: RestRetryPolicy.kt */
public final class y {
    @NotNull
    public static final x a(@NotNull RestRetryPolicyDto $this$toRestRetryPolicy) {
        k.e($this$toRestRetryPolicy, "<this>");
        return new x($this$toRestRetryPolicy.b().b(), $this$toRestRetryPolicy.b().a(), (TimeUnit) null, $this$toRestRetryPolicy.a(), $this$toRestRetryPolicy.c(), 4, (DefaultConstructorMarker) null);
    }
}
