package androidx.activity;

import kotlin.jvm.functions.l;
import kotlin.x;

/* compiled from: OnBackPressedDispatcher.kt */
public final class OnBackPressedDispatcherKt$addCallback$callback$1 extends OnBackPressedCallback {
    final /* synthetic */ boolean $enabled;
    final /* synthetic */ l<OnBackPressedCallback, x> $onBackPressed;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    OnBackPressedDispatcherKt$addCallback$callback$1(l<? super OnBackPressedCallback, x> $onBackPressed2, boolean $enabled2) {
        super($enabled2);
        this.$onBackPressed = $onBackPressed2;
        this.$enabled = $enabled2;
    }

    public void handleOnBackPressed() {
        this.$onBackPressed.invoke(this);
    }
}
