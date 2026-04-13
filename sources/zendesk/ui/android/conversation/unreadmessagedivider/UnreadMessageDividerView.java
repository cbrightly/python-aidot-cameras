package zendesk.ui.android.conversation.unreadmessagedivider;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$color;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;

/* compiled from: UnreadMessageDividerView.kt */
public final class UnreadMessageDividerView extends FrameLayout implements zendesk.ui.android.a<a> {
    @NotNull
    private final TextView c;
    @NotNull
    private final View d;
    @NotNull
    private final FrameLayout f;
    @NotNull
    private a q;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public UnreadMessageDividerView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public UnreadMessageDividerView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public UnreadMessageDividerView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ UnreadMessageDividerView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UnreadMessageDividerView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.q = new a();
        FrameLayout.inflate(context, R$layout.zuia_view_unread_message_divider, this);
        View findViewById = findViewById(R$id.zui_message_divider_text);
        k.d(findViewById, "findViewById(R.id.zui_message_divider_text)");
        this.c = (TextView) findViewById;
        View findViewById2 = findViewById(R$id.zui_divider_view);
        k.d(findViewById2, "findViewById(R.id.zui_divider_view)");
        this.d = findViewById2;
        View findViewById3 = findViewById(R$id.zui_message_divider);
        k.d(findViewById3, "findViewById(R.id.zui_message_divider)");
        this.f = (FrameLayout) findViewById3;
        a(a.INSTANCE);
    }

    /* compiled from: UnreadMessageDividerView.kt */
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
        k.e(renderingUpdate, "renderingUpdate");
        a invoke = renderingUpdate.invoke(this.q);
        this.q = invoke;
        this.c.setText(invoke.a().c());
        Integer b = this.q.a().b();
        int dividerColor = b == null ? ContextCompat.getColor(getContext(), R$color.colorError) : b.intValue();
        this.c.setTextColor(dividerColor);
        this.d.setBackgroundColor(dividerColor);
    }
}
