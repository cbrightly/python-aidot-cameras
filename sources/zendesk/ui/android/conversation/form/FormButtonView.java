package zendesk.ui.android.conversation.form;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import com.google.android.material.button.MaterialButton;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$attr;
import zendesk.ui.android.R$drawable;
import zendesk.ui.android.R$string;
import zendesk.ui.android.internal.d;

/* compiled from: FormButtonView.kt */
public final class FormButtonView extends MaterialButton implements zendesk.ui.android.a<n> {
    /* access modifiers changed from: private */
    @Nullable
    public final AnimatedVectorDrawableCompat c;
    @NotNull
    private final Animatable2Compat.AnimationCallback d;
    /* access modifiers changed from: private */
    @NotNull
    public n f;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FormButtonView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 6, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FormButtonView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ FormButtonView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? R$attr.formButtonStyle : i);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FormButtonView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs) {
        super(context, attrs, defStyleAttrs);
        k.e(context, "context");
        this.c = AnimatedVectorDrawableCompat.create(context, R$drawable.zuia_animation_loading_juggle);
        this.d = new FormButtonView$animationLoopCallback$1(this);
        this.f = new n();
        setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        a(a.INSTANCE);
    }

    /* compiled from: FormButtonView.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<n, n> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final n invoke(@NotNull n it) {
            k.e(it, "it");
            return it;
        }
    }

    public void a(@NotNull kotlin.jvm.functions.l<? super n, n> renderingUpdate) {
        String str;
        int i;
        k.e(renderingUpdate, "renderingUpdate");
        n invoke = renderingUpdate.invoke(this.f);
        this.f = invoke;
        if (invoke.b().e()) {
            str = "";
        } else {
            str = this.f.b().d();
        }
        setText(str);
        setOnClickListener(zendesk.ui.android.internal.k.b(0, new b(this), 1, (Object) null));
        Integer c2 = this.f.b().c();
        if (c2 == null) {
            Context context = getContext();
            k.d(context, "context");
            i = d.b(context, R$attr.colorAccent);
        } else {
            i = c2.intValue();
        }
        setBackgroundColor(i);
        setClickable(!this.f.b().e());
        if (this.c != null) {
            if (this.f.b().e() && this.c.isRunning()) {
                return;
            }
            if (this.f.b().e()) {
                setMinimumWidth(getWidth());
                setTextScaleX(0.0f);
                setContentDescription(getResources().getString(R$string.zuia_accessibility_loading_label));
                setIcon(this.c);
                this.c.registerAnimationCallback(this.d);
                this.c.start();
                return;
            }
            setMinimumWidth(0);
            setTextScaleX(1.0f);
            setContentDescription((CharSequence) null);
            setIcon((Drawable) null);
            this.c.setCallback((Drawable.Callback) null);
            this.c.stop();
        }
    }

    /* compiled from: FormButtonView.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ FormButtonView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(FormButtonView formButtonView) {
            super(0);
            this.this$0 = formButtonView;
        }

        public final void invoke() {
            this.this$0.f.a().invoke();
        }
    }
}
