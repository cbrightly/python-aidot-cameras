package zendesk.ui.android.conversation.textcell;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$style;
import zendesk.ui.android.internal.d;

/* compiled from: TextCellView.kt */
public final class TextCellView extends FrameLayout implements zendesk.ui.android.a<a> {
    /* access modifiers changed from: private */
    @NotNull
    public final TextView c;
    /* access modifiers changed from: private */
    @NotNull
    public a d;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public TextCellView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public TextCellView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public TextCellView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ TextCellView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TextCellView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.d = new a();
        context.getTheme().applyStyle(R$style.ThemeOverlay_ZendeskComponents_TextCellStyle, false);
        FrameLayout.inflate(context, R$layout.zuia_view_text_cell, this);
        View findViewById = findViewById(R$id.zuia_message_text);
        k.d(findViewById, "findViewById(R.id.zuia_message_text)");
        this.c = (TextView) findViewById;
        a(a.INSTANCE);
    }

    /* compiled from: TextCellView.kt */
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
        int textColor;
        Integer b2;
        k.e(renderingUpdate, "renderingUpdate");
        a invoke = renderingUpdate.invoke(this.d);
        this.d = invoke;
        this.c.setText(invoke.c().d());
        Integer c2 = this.d.c().c();
        if (c2 != null) {
            setBackgroundResource(c2.intValue());
        }
        if (!(getBackground() == null || (b2 = this.d.c().b()) == null)) {
            int it = b2.intValue();
            Drawable background = getBackground();
            GradientDrawable gradientDrawable = background instanceof GradientDrawable ? (GradientDrawable) background : null;
            if (gradientDrawable != null) {
                gradientDrawable.setColor(it);
            }
        }
        Integer e = this.d.c().e();
        if (e == null) {
            Context context = getContext();
            k.d(context, "context");
            textColor = d.b(context, 16842904);
        } else {
            textColor = e.intValue();
        }
        this.c.setTextColor(textColor);
        this.c.setLinkTextColor(textColor);
        this.c.setOnClickListener(zendesk.ui.android.internal.k.b(0, new b(this), 1, (Object) null));
        d();
    }

    /* compiled from: TextCellView.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ TextCellView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(TextCellView textCellView) {
            super(0);
            this.this$0 = textCellView;
        }

        public final void invoke() {
            if (this.this$0.c.getSelectionStart() == -1 && this.this$0.c.getSelectionEnd() == -1) {
                this.this$0.d.a().invoke(this.this$0.c.getText().toString());
            }
        }
    }

    public final void setMessageTextGravity$zendesk_ui_ui_android(int gravity) {
        ViewGroup.LayoutParams layoutParams = this.c.getLayoutParams();
        if (layoutParams != null) {
            ((FrameLayout.LayoutParams) layoutParams).gravity = gravity;
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
    }

    private final void d() {
        CharSequence text = this.c.getText();
        SpannableString $this$prepareClickableElements_u24lambda_u2d1 = text instanceof SpannableString ? (SpannableString) text : null;
        if ($this$prepareClickableElements_u24lambda_u2d1 != null) {
            URLSpan[] spans = (URLSpan[]) $this$prepareClickableElements_u24lambda_u2d1.getSpans(0, $this$prepareClickableElements_u24lambda_u2d1.length(), URLSpan.class);
            k.d(spans, "spans");
            int length = spans.length;
            int i = 0;
            while (i < length) {
                URLSpan span = spans[i];
                i++;
                int start = $this$prepareClickableElements_u24lambda_u2d1.getSpanStart(span);
                int end = $this$prepareClickableElements_u24lambda_u2d1.getSpanEnd(span);
                $this$prepareClickableElements_u24lambda_u2d1.removeSpan(span);
                $this$prepareClickableElements_u24lambda_u2d1.setSpan(new TextCellView$prepareClickableElements$1$1(this, span.getURL()), start, end, 0);
            }
        }
    }
}
