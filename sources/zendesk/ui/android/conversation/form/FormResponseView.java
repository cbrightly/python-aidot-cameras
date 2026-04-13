package zendesk.ui.android.conversation.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Px;
import com.leedarson.bean.Constants;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$dimen;
import zendesk.ui.android.internal.l;

/* compiled from: FormResponseView.kt */
public final class FormResponseView extends LinearLayout implements zendesk.ui.android.a<q> {
    @NotNull
    private q c;
    @Px
    private final int d;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FormResponseView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FormResponseView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FormResponseView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ FormResponseView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FormResponseView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.c = new q();
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.d = context.getResources().getDimensionPixelOffset(R$dimen.zuia_spacing_small);
        int size$iv = this.d;
        setPadding(size$iv, size$iv, size$iv, size$iv);
        setClipChildren(true);
        setOrientation(1);
        l.g(this, 0, 0.0f, 3, (Object) null);
        a(a.INSTANCE);
    }

    /* compiled from: FormResponseView.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<q, q> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final q invoke(@NotNull q it) {
            k.e(it, "it");
            return it;
        }
    }

    public void a(@NotNull kotlin.jvm.functions.l<? super q, q> renderingUpdate) {
        k.e(renderingUpdate, "renderingUpdate");
        this.c = renderingUpdate.invoke(this.c);
        removeAllViews();
        for (j fieldResponse : this.c.a().b()) {
            Context context = getContext();
            k.d(context, "context");
            FieldResponseView $this$render_u24lambda_u2d0 = new FieldResponseView(context, (AttributeSet) null, 2, (DefaultConstructorMarker) null);
            $this$render_u24lambda_u2d0.a(new b(fieldResponse));
            x xVar = x.a;
            ViewGroup.MarginLayoutParams $this$render_u24lambda_u2d1 = new LinearLayout.LayoutParams(-1, -2);
            int size$iv = this.d;
            $this$render_u24lambda_u2d1.setMargins(size$iv, size$iv, size$iv, size$iv);
            addView($this$render_u24lambda_u2d0, $this$render_u24lambda_u2d1);
        }
    }

    /* compiled from: FormResponseView.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<k, k> {
        final /* synthetic */ j $fieldResponse;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(j jVar) {
            super(1);
            this.$fieldResponse = jVar;
        }

        @NotNull
        public final k invoke(@NotNull k fieldResponseRendering) {
            k.e(fieldResponseRendering, "fieldResponseRendering");
            return fieldResponseRendering.b().d(new a(this.$fieldResponse)).a();
        }

        /* compiled from: FormResponseView.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<l, l> {
            final /* synthetic */ j $fieldResponse;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(j jVar) {
                super(1);
                this.$fieldResponse = jVar;
            }

            @NotNull
            public final l invoke(@NotNull l state) {
                k.e(state, Constants.ACTION_STATE);
                return state.a(this.$fieldResponse.a(), this.$fieldResponse.b());
            }
        }
    }
}
