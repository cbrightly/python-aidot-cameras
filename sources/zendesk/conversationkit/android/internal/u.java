package zendesk.conversationkit.android.internal;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import retrofit2.HttpException;

/* compiled from: ErrorKtx.kt */
public final class u {
    public static final boolean a(@NotNull Throwable $this$isUnauthorizedException) {
        k.e($this$isUnauthorizedException, "<this>");
        return ($this$isUnauthorizedException instanceof HttpException) && ((HttpException) $this$isUnauthorizedException).code() == 401;
    }
}
