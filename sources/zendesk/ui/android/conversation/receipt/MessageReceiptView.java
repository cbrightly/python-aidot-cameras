package zendesk.ui.android.conversation.receipt;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$attr;
import zendesk.ui.android.R$drawable;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$style;

/* compiled from: MessageReceiptView.kt */
public final class MessageReceiptView extends LinearLayout implements zendesk.ui.android.a<b> {
    @NotNull
    private final LinearLayout c;
    /* access modifiers changed from: private */
    @NotNull
    public final TextView d;
    /* access modifiers changed from: private */
    @NotNull
    public final ImageView f;
    /* access modifiers changed from: private */
    @NotNull
    public b q;

    /* compiled from: MessageReceiptView.kt */
    public final /* synthetic */ class b {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[a.values().length];
            iArr[a.INBOUND.ordinal()] = 1;
            iArr[a.NONE.ordinal()] = 2;
            iArr[a.OUTBOUND_SENDING.ordinal()] = 3;
            iArr[a.OUTBOUND_SENT.ordinal()] = 4;
            iArr[a.INBOUND_FAILED.ordinal()] = 5;
            iArr[a.OUTBOUND_FAILED.ordinal()] = 6;
            a = iArr;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MessageReceiptView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MessageReceiptView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MessageReceiptView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ MessageReceiptView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MessageReceiptView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.q = new b();
        context.getTheme().applyStyle(R$style.ThemeOverlay_ZendeskComponents_MessageReceipt, false);
        LinearLayout.inflate(context, R$layout.zuia_view_message_receipt, this);
        View findViewById = findViewById(R$id.zuia_message_receipt_container);
        k.d(findViewById, "findViewById(R.id.zuia_message_receipt_container)");
        this.c = (LinearLayout) findViewById;
        View findViewById2 = findViewById(R$id.zuia_icon_image);
        k.d(findViewById2, "findViewById(R.id.zuia_icon_image)");
        this.f = (ImageView) findViewById2;
        View findViewById3 = findViewById(R$id.zuia_label_text);
        k.d(findViewById3, "findViewById(R.id.zuia_label_text)");
        this.d = (TextView) findViewById3;
        a(a.INSTANCE);
    }

    /* compiled from: MessageReceiptView.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<b, b> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final b invoke(@NotNull b it) {
            k.e(it, "it");
            return it;
        }
    }

    public void a(@NotNull kotlin.jvm.functions.l<? super b, b> renderingUpdate) {
        k.e(renderingUpdate, "renderingUpdate");
        b invoke = renderingUpdate.invoke(this.q);
        this.q = invoke;
        this.d.setText(invoke.a().d());
        TextView textView = this.d;
        Integer e2 = this.q.a().e();
        textView.setTextColor(e2 == null ? h(this.q.a().f()) : e2.intValue());
        e();
    }

    @ColorInt
    private final int h(a position) {
        switch (b.a[position.ordinal()]) {
            case 1:
            case 2:
                Context context = getContext();
                k.d(context, "context");
                return zendesk.ui.android.internal.d.b(context, R$attr.messageReceiptInboundLabelColor);
            case 3:
            case 4:
                Context context2 = getContext();
                k.d(context2, "context");
                return zendesk.ui.android.internal.d.b(context2, R$attr.messageReceiptOutboundLabelColor);
            case 5:
            case 6:
                Context context3 = getContext();
                k.d(context3, "context");
                return zendesk.ui.android.internal.d.b(context3, R$attr.messageReceiptOutboundFailedLabelColor);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final void e() {
        this.c.removeAllViews();
        switch (b.a[this.q.a().f().ordinal()]) {
            case 1:
                f(R$drawable.zuia_message_status_inbound, new c(this));
                return;
            case 3:
                g(this, R$drawable.zuia_message_status_outbound_sending, (kotlin.jvm.functions.l) null, 2, (Object) null);
                return;
            case 4:
                g(this, R$drawable.zuia_message_status_outbound_sent, (kotlin.jvm.functions.l) null, 2, (Object) null);
                return;
            case 5:
                f(R$drawable.zuia_message_status_outbound_failed, new d(this));
                return;
            case 6:
                g(this, R$drawable.zuia_message_status_outbound_failed, (kotlin.jvm.functions.l) null, 2, (Object) null);
                return;
            default:
                return;
        }
    }

    /* compiled from: MessageReceiptView.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<LinearLayout, x> {
        final /* synthetic */ MessageReceiptView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(MessageReceiptView messageReceiptView) {
            super(1);
            this.this$0 = messageReceiptView;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((LinearLayout) p1);
            return x.a;
        }

        public final void invoke(@NotNull LinearLayout $this$formatIconView) {
            k.e($this$formatIconView, "$this$formatIconView");
            if (this.this$0.q.a().g()) {
                $this$formatIconView.addView(this.this$0.f);
            }
            $this$formatIconView.addView(this.this$0.d);
        }
    }

    /* compiled from: MessageReceiptView.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<LinearLayout, x> {
        final /* synthetic */ MessageReceiptView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(MessageReceiptView messageReceiptView) {
            super(1);
            this.this$0 = messageReceiptView;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((LinearLayout) p1);
            return x.a;
        }

        public final void invoke(@NotNull LinearLayout $this$formatIconView) {
            k.e($this$formatIconView, "$this$formatIconView");
            if (this.this$0.q.a().g()) {
                $this$formatIconView.addView(this.this$0.f);
            }
            $this$formatIconView.addView(this.this$0.d);
        }
    }

    static /* synthetic */ void g(MessageReceiptView messageReceiptView, int i, kotlin.jvm.functions.l lVar, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            lVar = new e(messageReceiptView);
        }
        messageReceiptView.f(i, lVar);
    }

    /* compiled from: MessageReceiptView.kt */
    public static final class e extends l implements kotlin.jvm.functions.l<LinearLayout, x> {
        final /* synthetic */ MessageReceiptView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(MessageReceiptView messageReceiptView) {
            super(1);
            this.this$0 = messageReceiptView;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((LinearLayout) p1);
            return x.a;
        }

        public final void invoke(@NotNull LinearLayout $this$null) {
            k.e($this$null, "$this$null");
            $this$null.addView(this.this$0.d);
            if (this.this$0.q.a().g()) {
                $this$null.addView(this.this$0.f);
            }
        }
    }

    private final void f(@DrawableRes int imageResource, kotlin.jvm.functions.l<? super LinearLayout, x> containerBlock) {
        this.f.setImageResource(imageResource);
        Integer c2 = this.q.a().c();
        if (c2 != null) {
            this.f.setColorFilter(c2.intValue(), PorterDuff.Mode.SRC_IN);
        }
        containerBlock.invoke(this.c);
    }
}
