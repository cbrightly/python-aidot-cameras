package kotlinx.coroutines.channels;

import kotlin.coroutines.d;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"kotlinx/coroutines/channels/ChannelsKt__ChannelsKt", "kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt", "kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt"}, k = 4, mv = {1, 6, 0}, xi = 48)
public final class m {
    public static final void a(@NotNull w<?> $this$cancelConsumed, @Nullable Throwable cause) {
        n.a($this$cancelConsumed, cause);
    }

    @Nullable
    public static final <E> Object b(@NotNull w<? extends E> $this$receiveOrNull, @NotNull d<? super E> $completion) {
        return n.b($this$receiveOrNull, $completion);
    }
}
