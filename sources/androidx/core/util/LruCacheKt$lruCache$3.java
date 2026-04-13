package androidx.core.util;

import kotlin.jvm.functions.r;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u0016\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0010\t\u001a\u00020\b\"\b\b\u0000\u0010\u0001*\u00020\u0000\"\b\b\u0001\u0010\u0002*\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00028\u00012\b\u0010\u0007\u001a\u0004\u0018\u00018\u0001H\n"}, d2 = {"", "K", "V", "", "<anonymous parameter 0>", "<anonymous parameter 1>", "<anonymous parameter 2>", "<anonymous parameter 3>", "Lkotlin/x;", "<anonymous>"}, k = 3, mv = {1, 5, 1})
/* compiled from: LruCache.kt */
public final class LruCacheKt$lruCache$3 extends kotlin.jvm.internal.l implements r<Boolean, K, V, V, x> {
    public static final LruCacheKt$lruCache$3 INSTANCE = new LruCacheKt$lruCache$3();

    public LruCacheKt$lruCache$3() {
        super(4);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2, Object p3, Object p4) {
        invoke(((Boolean) p1).booleanValue(), p2, p3, p4);
        return x.a;
    }

    public final void invoke(boolean $noName_0, @NotNull K $noName_1, @NotNull V $noName_2, @Nullable V $noName_3) {
        k.e($noName_1, "$noName_1");
        k.e($noName_2, "$noName_2");
    }
}
