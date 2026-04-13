package zendesk.ui.android.conversation.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;

/* compiled from: FieldResponseView.kt */
public final class FieldResponseView extends LinearLayout implements zendesk.ui.android.a<k> {
    @NotNull
    private final TextView c;
    @NotNull
    private final TextView d;
    @NotNull
    private k f;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FieldResponseView(@NotNull Context context) {
        this(context, (AttributeSet) null, 2, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ FieldResponseView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FieldResponseView(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0, 0);
        k.e(context, "context");
        this.f = new k();
        LinearLayout.inflate(context, R$layout.zuia_view_field_response, this);
        setOrientation(1);
        View findViewById = findViewById(R$id.zuia_form_response_title);
        k.d(findViewById, "findViewById(R.id.zuia_form_response_title)");
        this.c = (TextView) findViewById;
        View findViewById2 = findViewById(R$id.zuia_form_response_subtitle);
        k.d(findViewById2, "findViewById(R.id.zuia_form_response_subtitle)");
        this.d = (TextView) findViewById2;
        a(a.INSTANCE);
    }

    /* compiled from: FieldResponseView.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<k, k> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final k invoke(@NotNull k it) {
            k.e(it, "it");
            return it;
        }
    }

    public void a(@NotNull kotlin.jvm.functions.l<? super k, k> renderingUpdate) {
        k.e(renderingUpdate, "renderingUpdate");
        k invoke = renderingUpdate.invoke(this.f);
        k $this$render_u24lambda_u2d0 = invoke;
        this.c.setText($this$render_u24lambda_u2d0.a().c());
        this.d.setText($this$render_u24lambda_u2d0.a().b());
        x xVar = x.a;
        this.f = invoke;
    }
}
