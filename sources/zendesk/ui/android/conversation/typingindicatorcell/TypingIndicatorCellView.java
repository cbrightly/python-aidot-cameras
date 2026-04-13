package zendesk.ui.android.conversation.typingindicatorcell;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$drawable;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$style;
import zendesk.ui.android.internal.ImageViewExtensionKt;

/* compiled from: TypingIndicatorCellView.kt */
public final class TypingIndicatorCellView extends FrameLayout implements zendesk.ui.android.a<a> {
    @NotNull
    private a c;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public TypingIndicatorCellView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public TypingIndicatorCellView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public TypingIndicatorCellView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ TypingIndicatorCellView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TypingIndicatorCellView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.c = new a();
        context.getTheme().applyStyle(R$style.ThemeOverlay_ZendeskComponents_TypingIndicatorCellStyle, false);
        FrameLayout.inflate(context, R$layout.zuia_view_typing_indicator_cell, this);
        b();
        a(a.INSTANCE);
    }

    /* compiled from: TypingIndicatorCellView.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<a, a> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final a invoke(@NotNull a it) {
            k.e(it, "it");
            return it;
        }
    }

    public void a(@NotNull kotlin.jvm.functions.l<? super a, a> renderingUpdate) {
        Integer b;
        k.e(renderingUpdate, "renderingUpdate");
        this.c = renderingUpdate.invoke(this.c);
        setBackgroundResource(R$drawable.zuia_message_cell_inbound_shape_single);
        if (getBackground() != null && (b = this.c.a().b()) != null) {
            int it = b.intValue();
            Drawable background = getBackground();
            GradientDrawable gradientDrawable = background instanceof GradientDrawable ? (GradientDrawable) background : null;
            if (gradientDrawable != null) {
                gradientDrawable.setColor(it);
            }
        }
    }

    private final void b() {
        View findViewById = findViewById(R$id.zuia_typing_indicator);
        k.d(findViewById, "findViewById(R.id.zuia_typing_indicator)");
        ImageViewExtensionKt.a((ImageView) findViewById, R$drawable.zuia_animation_typing_indicator);
    }
}
