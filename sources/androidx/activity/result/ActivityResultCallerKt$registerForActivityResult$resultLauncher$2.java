package androidx.activity.result;

import kotlin.l;
import kotlin.x;

@l(d1 = {"\u0000\n\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u00012\u000e\u0010\u0003\u001a\n \u0002*\u0004\u0018\u00018\u00018\u0001H\n"}, d2 = {"I", "O", "kotlin.jvm.PlatformType", "it", "Lkotlin/x;", "<anonymous>"}, k = 3, mv = {1, 5, 1})
/* compiled from: ActivityResultCaller.kt */
public final class ActivityResultCallerKt$registerForActivityResult$resultLauncher$2<O> implements ActivityResultCallback {
    final /* synthetic */ kotlin.jvm.functions.l<O, x> $callback;

    ActivityResultCallerKt$registerForActivityResult$resultLauncher$2(kotlin.jvm.functions.l<? super O, x> lVar) {
        this.$callback = lVar;
    }

    public final void onActivityResult(O it) {
        this.$callback.invoke(it);
    }
}
