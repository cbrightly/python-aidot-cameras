package zendesk.ui.android.conversation.item;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.leedarson.bean.Constants;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$attr;
import zendesk.ui.android.R$dimen;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.conversation.item.d;
import zendesk.ui.android.internal.l;

/* compiled from: ItemGroupView.kt */
public final class ItemGroupView extends FrameLayout implements zendesk.ui.android.a<b> {
    @NotNull
    private final LinearLayout c;
    /* access modifiers changed from: private */
    @NotNull
    public b d;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ItemGroupView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ItemGroupView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ItemGroupView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ItemGroupView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ItemGroupView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.d = new b();
        FrameLayout.inflate(context, R$layout.zuia_view_item_group, this);
        View findViewById = findViewById(R$id.zuia_item_container);
        k.d(findViewById, "findViewById(R.id.zuia_item_container)");
        LinearLayout linearLayout = (LinearLayout) findViewById;
        this.c = linearLayout;
        linearLayout.setClipToOutline(true);
        context.getTheme().resolveAttribute(R$attr.colorOnSurface, new TypedValue(), true);
        l.g(linearLayout, 0, 0.0f, 3, (Object) null);
        a(a.INSTANCE);
    }

    /* compiled from: ItemGroupView.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<b, b> {
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
        this.d = renderingUpdate.invoke(this.d);
        int dividerSize = getResources().getDimensionPixelSize(R$dimen.zuia_divider_size);
        a lastItem = (a) y.f0(this.d.b().c());
        this.c.removeAllViews();
        for (a item : this.d.b().c()) {
            this.c.addView(c(item));
            if (!k.a(item, lastItem)) {
                View divider = LayoutInflater.from(getContext()).inflate(R$layout.zuia_view_divider, this, false);
                View $this$render_u24lambda_u2d1 = divider;
                ViewGroup.LayoutParams layoutParams = $this$render_u24lambda_u2d1.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams = null;
                ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
                if (marginLayoutParams2 != null) {
                    ViewGroup.MarginLayoutParams $this$render_u24lambda_u2d1_u24lambda_u2d0 = marginLayoutParams2;
                    $this$render_u24lambda_u2d1_u24lambda_u2d0.setMarginStart(dividerSize);
                    $this$render_u24lambda_u2d1_u24lambda_u2d0.setMarginEnd(dividerSize);
                    x xVar = x.a;
                    marginLayoutParams = marginLayoutParams2;
                }
                $this$render_u24lambda_u2d1.setLayoutParams(marginLayoutParams);
                this.c.addView(divider);
            } else {
                return;
            }
        }
    }

    private final View c(a<?> item) {
        Context context = getContext();
        k.d(context, "context");
        ItemView $this$createItemView_u24lambda_u2d2 = new ItemView(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        $this$createItemView_u24lambda_u2d2.a(new b(item, this));
        return $this$createItemView_u24lambda_u2d2;
    }

    /* compiled from: ItemGroupView.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<d, d> {
        final /* synthetic */ a<?> $item;
        final /* synthetic */ ItemGroupView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(a<?> aVar, ItemGroupView itemGroupView) {
            super(1);
            this.$item = aVar;
            this.this$0 = itemGroupView;
        }

        @NotNull
        public final d invoke(@NotNull d itemRendering) {
            k.e(itemRendering, "itemRendering");
            d.a g = itemRendering.c().g(new a(this.$item, this.this$0));
            d.a $this$invoke_u24lambda_u2d1 = g;
            kotlin.jvm.functions.l it = this.this$0.d.a();
            if (it != null) {
                $this$invoke_u24lambda_u2d1.d(it);
            }
            return g.a();
        }

        /* compiled from: ItemGroupView.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<e, e> {
            final /* synthetic */ a<?> $item;
            final /* synthetic */ ItemGroupView this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(a<?> aVar, ItemGroupView itemGroupView) {
                super(1);
                this.$item = aVar;
                this.this$0 = itemGroupView;
            }

            @NotNull
            public final e invoke(@NotNull e state) {
                k.e(state, Constants.ACTION_STATE);
                return state.a(this.$item, this.this$0.d.b().e(), this.this$0.d.b().d());
            }
        }
    }
}
