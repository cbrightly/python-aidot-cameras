package androidx.core.os;

import kotlin.jvm.functions.a;
import kotlin.l;
import kotlin.x;

@l(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0001\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Lkotlin/x;", "<anonymous>", "()V"}, k = 3, mv = {1, 5, 1})
/* compiled from: Handler.kt */
public final class HandlerKt$postDelayed$runnable$1 implements Runnable {
    final /* synthetic */ a<x> $action;

    public HandlerKt$postDelayed$runnable$1(a<x> aVar) {
        this.$action = aVar;
    }

    public final void run() {
        this.$action.invoke();
    }
}
