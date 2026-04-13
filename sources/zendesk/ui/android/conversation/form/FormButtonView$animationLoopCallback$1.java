package zendesk.ui.android.conversation.form;

import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FormButtonView.kt */
public final class FormButtonView$animationLoopCallback$1 extends Animatable2Compat.AnimationCallback {
    final /* synthetic */ FormButtonView a;

    FormButtonView$animationLoopCallback$1(FormButtonView $receiver) {
        this.a = $receiver;
    }

    /* access modifiers changed from: private */
    public static final void b(FormButtonView this$0) {
        k.e(this$0, "this$0");
        AnimatedVectorDrawableCompat b = this$0.c;
        if (b != null) {
            b.start();
        }
    }

    public void onAnimationEnd(@NotNull Drawable drawable) {
        k.e(drawable, "drawable");
        if (this.a.f.b().e()) {
            new f(this.a).run();
        }
    }
}
