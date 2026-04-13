package zendesk.ui.android.conversation.quickreply;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.view.GravityCompat;
import com.google.android.material.chip.ChipGroup;
import com.leedarson.bean.Constants;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;

/* compiled from: QuickReplyView.kt */
public final class QuickReplyView extends FrameLayout implements zendesk.ui.android.a<d> {
    /* access modifiers changed from: private */
    @NotNull
    public final ChipGroup c;
    /* access modifiers changed from: private */
    @NotNull
    public d d;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public QuickReplyView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public QuickReplyView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public QuickReplyView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ QuickReplyView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public QuickReplyView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.d = new d();
        FrameLayout.inflate(context, R$layout.zuia_view_quick_reply, this);
        View findViewById = findViewById(R$id.zuia_quick_reply_chip_group);
        k.d(findViewById, "findViewById(R.id.zuia_quick_reply_chip_group)");
        ChipGroup chipGroup = (ChipGroup) findViewById;
        this.c = chipGroup;
        ViewGroup.LayoutParams layoutParams = chipGroup.getLayoutParams();
        if (layoutParams != null) {
            ((FrameLayout.LayoutParams) layoutParams).gravity = GravityCompat.END;
            a(a.INSTANCE);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
    }

    /* compiled from: QuickReplyView.kt */
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
        k.e(renderingUpdate, "renderingUpdate");
        this.d = renderingUpdate.invoke(this.d);
        this.c.removeAllViews();
        for (a quickReplyOption : this.d.b().c()) {
            this.c.addView(d(quickReplyOption.a(), quickReplyOption.b()));
        }
    }

    private final View d(String id, String text) {
        Context context = getContext();
        k.d(context, "context");
        QuickReplyOptionView $this$addQuickReplyOption_u24lambda_u2d1 = new QuickReplyOptionView(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        $this$addQuickReplyOption_u24lambda_u2d1.a(new b(id, text, this));
        return $this$addQuickReplyOption_u24lambda_u2d1;
    }

    /* compiled from: QuickReplyView.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<b, b> {
        final /* synthetic */ String $id;
        final /* synthetic */ String $text;
        final /* synthetic */ QuickReplyView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(String str, String str2, QuickReplyView quickReplyView) {
            super(1);
            this.$id = str;
            this.$text = str2;
            this.this$0 = quickReplyView;
        }

        @NotNull
        public final b invoke(@NotNull b quickReplyOptionRendering) {
            k.e(quickReplyOptionRendering, "quickReplyOptionRendering");
            return quickReplyOptionRendering.c().g(new a(this.$id, this.$text, this.this$0)).d(new C0573b(this.this$0)).a();
        }

        /* compiled from: QuickReplyView.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<c, c> {
            final /* synthetic */ String $id;
            final /* synthetic */ String $text;
            final /* synthetic */ QuickReplyView this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(String str, String str2, QuickReplyView quickReplyView) {
                super(1);
                this.$id = str;
                this.$text = str2;
                this.this$0 = quickReplyView;
            }

            @NotNull
            public final c invoke(@NotNull c state) {
                k.e(state, Constants.ACTION_STATE);
                return state.a(this.$id, this.$text, this.this$0.d.b().b());
            }
        }

        /* renamed from: zendesk.ui.android.conversation.quickreply.QuickReplyView$b$b  reason: collision with other inner class name */
        /* compiled from: QuickReplyView.kt */
        public static final class C0573b extends l implements p<String, String, x> {
            final /* synthetic */ QuickReplyView this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0573b(QuickReplyView quickReplyView) {
                super(2);
                this.this$0 = quickReplyView;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
                invoke((String) p1, (String) p2);
                return x.a;
            }

            public final void invoke(@NotNull String id, @NotNull String text) {
                k.e(id, "id");
                k.e(text, "text");
                kotlin.jvm.functions.l it = this.this$0.d.a();
                if (it != null) {
                    it.invoke(new a(id, text));
                }
                int childCount = this.this$0.c.getChildCount();
                if (childCount > 0) {
                    int i = 0;
                    do {
                        int index = i;
                        boolean z = true;
                        i++;
                        View childAt = this.this$0.c.getChildAt(index);
                        QuickReplyOptionView quickReplyOptionView = childAt instanceof QuickReplyOptionView ? (QuickReplyOptionView) childAt : null;
                        if (quickReplyOptionView == null || quickReplyOptionView.isSelected()) {
                            z = false;
                        }
                        if (z && quickReplyOptionView.getChildCount() > 0) {
                            quickReplyOptionView.getChildAt(0).setEnabled(false);
                            continue;
                        }
                    } while (i < childCount);
                }
            }
        }
    }
}
