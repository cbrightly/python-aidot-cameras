package com.lzf.easyfloat.utils;

import com.lzf.easyfloat.interfaces.FloatCallbacks;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0003\u001a\u00020\u0002*\u00060\u0000R\u00020\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Lcom/lzf/easyfloat/interfaces/FloatCallbacks$Builder;", "Lcom/lzf/easyfloat/interfaces/FloatCallbacks;", "Lkotlin/x;", "<anonymous>", "(Lcom/lzf/easyfloat/interfaces/FloatCallbacks$Builder;)V"}, k = 3, mv = {1, 5, 1})
/* compiled from: DragUtils.kt */
public final class DragUtils$showAdd$1 extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<FloatCallbacks.Builder, x> {
    public static final DragUtils$showAdd$1 INSTANCE = new DragUtils$showAdd$1();

    DragUtils$showAdd$1() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((FloatCallbacks.Builder) p1);
        return x.a;
    }

    public final void invoke(@NotNull FloatCallbacks.Builder $this$registerCallback) {
        k.e($this$registerCallback, "$this$registerCallback");
        $this$registerCallback.createResult(AnonymousClass1.INSTANCE);
        $this$registerCallback.dismiss(AnonymousClass2.INSTANCE);
    }
}
