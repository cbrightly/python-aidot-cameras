package zendesk.ui.android.conversation.item;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$attr;
import zendesk.ui.android.R$dimen;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$style;
import zendesk.ui.android.internal.d;

/* compiled from: ItemView.kt */
public final class ItemView extends LinearLayout implements zendesk.ui.android.a<d> {
    @NotNull
    private final TextView c;
    @NotNull
    private final TextView d;
    /* access modifiers changed from: private */
    @NotNull
    public d f;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ItemView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ItemView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ItemView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ItemView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ItemView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.f = new d();
        context.getTheme().applyStyle(R$style.ThemeOverlay_ZendeskComponents_Item, false);
        LinearLayout.inflate(context, R$layout.zuia_view_item, this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        LinearLayout.LayoutParams layoutParams2 = layoutParams;
        int padding = getResources().getDimensionPixelSize(R$dimen.zuia_vertical_message_padding);
        setPadding(0, padding, 0, padding);
        x xVar = x.a;
        setLayoutParams(layoutParams);
        setOrientation(1);
        setClickable(true);
        setFocusable(true);
        View findViewById = findViewById(R$id.zuia_item_title);
        k.d(findViewById, "findViewById(R.id.zuia_item_title)");
        this.c = (TextView) findViewById;
        View findViewById2 = findViewById(R$id.zuia_item_subtitle);
        k.d(findViewById2, "findViewById(R.id.zuia_item_subtitle)");
        this.d = (TextView) findViewById2;
        a(a.INSTANCE);
    }

    /* compiled from: ItemView.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<d, d> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final d invoke(@NotNull d it) {
            k.e(it, "it");
            return it;
        }
    }

    public void a(@NotNull kotlin.jvm.functions.l<? super d, d> renderingUpdate) {
        int backgroundColor;
        x xVar;
        k.e(renderingUpdate, "renderingUpdate");
        this.f = renderingUpdate.invoke(this.f);
        StateListDrawable states = new StateListDrawable();
        Integer c2 = this.f.b().c();
        if (c2 == null) {
            Context context = getContext();
            k.d(context, "context");
            backgroundColor = d.b(context, R$attr.colorAccent);
        } else {
            backgroundColor = c2.intValue();
        }
        GradientDrawable tappedBackgroundDrawable = new GradientDrawable();
        tappedBackgroundDrawable.setColor(d.a(backgroundColor, 0.2f));
        states.addState(new int[]{16842919}, tappedBackgroundDrawable);
        GradientDrawable untappedBackgroundDrawable = new GradientDrawable();
        untappedBackgroundDrawable.setColor(0);
        states.addState(StateSet.WILD_CARD, untappedBackgroundDrawable);
        setBackground(states);
        a<?> b2 = this.f.b().b();
        Integer c3 = b2.c();
        if (c3 == null) {
            c3 = this.f.b().d();
        }
        if (c3 != null) {
            this.c.setTextColor(c3.intValue());
        }
        this.c.setText(b2.b());
        String it = b2.a();
        if (it == null) {
            xVar = null;
        } else {
            this.d.setVisibility(0);
            this.d.setText(it);
            xVar = x.a;
        }
        if (xVar == null) {
            a<?> aVar = b2;
            this.d.setVisibility(8);
        }
        setOnClickListener(zendesk.ui.android.internal.k.b(0, new b(this), 1, (Object) null));
    }

    /* compiled from: ItemView.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ ItemView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(ItemView itemView) {
            super(0);
            this.this$0 = itemView;
        }

        public final void invoke() {
            kotlin.jvm.functions.l<a<?>, x> a = this.this$0.f.a();
            if (a != null) {
                a.invoke(this.this$0.f.b().b());
            }
        }
    }
}
