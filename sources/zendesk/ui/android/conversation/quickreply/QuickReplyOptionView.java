package zendesk.ui.android.conversation.quickreply;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.leedarson.bean.Constants;
import com.tencent.bugly.Bugly;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$style;

/* compiled from: QuickReplyOptionView.kt */
public final class QuickReplyOptionView extends FrameLayout implements zendesk.ui.android.a<b> {
    @NotNull
    private final TextView c;
    /* access modifiers changed from: private */
    @NotNull
    public b d;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public QuickReplyOptionView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public QuickReplyOptionView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public QuickReplyOptionView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ QuickReplyOptionView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public QuickReplyOptionView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.d = new b();
        context.getTheme().applyStyle(R$style.ThemeOverlay_ZendeskComponents_QuickReplyOption, false);
        FrameLayout.inflate(context, R$layout.zuia_view_quick_reply_option, this);
        View findViewById = findViewById(R$id.zuia_quick_reply_options_view);
        k.d(findViewById, "findViewById(R.id.zuia_quick_reply_options_view)");
        this.c = (TextView) findViewById;
        a(a.INSTANCE);
    }

    /* compiled from: QuickReplyOptionView.kt */
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: android.graphics.drawable.GradientDrawable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: android.graphics.drawable.StateListDrawable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v12, resolved type: android.graphics.drawable.StateListDrawable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v14, resolved type: android.graphics.drawable.StateListDrawable} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(@org.jetbrains.annotations.NotNull kotlin.jvm.functions.l<? super zendesk.ui.android.conversation.quickreply.b, zendesk.ui.android.conversation.quickreply.b> r12) {
        /*
            r11 = this;
            java.lang.String r0 = "renderingUpdate"
            kotlin.jvm.internal.k.e(r12, r0)
            zendesk.ui.android.conversation.quickreply.b r0 = r11.d
            java.lang.Object r0 = r12.invoke(r0)
            zendesk.ui.android.conversation.quickreply.b r0 = (zendesk.ui.android.conversation.quickreply.b) r0
            r11.d = r0
            android.graphics.drawable.StateListDrawable r0 = new android.graphics.drawable.StateListDrawable
            r0.<init>()
            zendesk.ui.android.conversation.quickreply.b r1 = r11.d
            zendesk.ui.android.conversation.quickreply.c r1 = r1.b()
            java.lang.Integer r1 = r1.b()
            java.lang.String r2 = "context"
            if (r1 != 0) goto L_0x0030
            android.content.Context r1 = r11.getContext()
            kotlin.jvm.internal.k.d(r1, r2)
            int r3 = zendesk.ui.android.R$attr.colorPrimary
            int r1 = zendesk.ui.android.internal.d.b(r1, r3)
            goto L_0x0034
        L_0x0030:
            int r1 = r1.intValue()
        L_0x0034:
            android.content.Context r3 = r11.getContext()
            int r4 = zendesk.ui.android.R$drawable.zuia_quick_reply_option_background
            android.graphics.drawable.Drawable r3 = androidx.core.content.ContextCompat.getDrawable(r3, r4)
            java.lang.String r5 = "null cannot be cast to non-null type android.graphics.drawable.GradientDrawable"
            if (r3 == 0) goto L_0x00fd
            android.graphics.drawable.GradientDrawable r3 = (android.graphics.drawable.GradientDrawable) r3
            r6 = 1045220557(0x3e4ccccd, float:0.2)
            int r6 = zendesk.ui.android.internal.d.a(r1, r6)
            r3.setColor(r6)
            android.content.res.Resources r6 = r11.getResources()
            int r7 = zendesk.ui.android.R$dimen.zuia_quick_reply_options_width
            int r6 = r6.getDimensionPixelSize(r7)
            r3.setStroke(r6, r1)
            r6 = 1
            int[] r8 = new int[r6]
            r9 = 16842912(0x10100a0, float:2.3694006E-38)
            r10 = 0
            r8[r10] = r9
            r0.addState(r8, r3)
            int[] r8 = new int[r6]
            r9 = 16842919(0x10100a7, float:2.3694026E-38)
            r8[r10] = r9
            r0.addState(r8, r3)
            android.content.Context r8 = r11.getContext()
            android.graphics.drawable.Drawable r4 = androidx.core.content.ContextCompat.getDrawable(r8, r4)
            if (r4 == 0) goto L_0x00f7
            android.graphics.drawable.GradientDrawable r4 = (android.graphics.drawable.GradientDrawable) r4
            r4.setColor(r10)
            android.content.res.Resources r5 = r11.getResources()
            int r5 = r5.getDimensionPixelSize(r7)
            r4.setStroke(r5, r1)
            int[] r5 = android.util.StateSet.WILD_CARD
            r0.addState(r5, r4)
            android.widget.TextView r5 = r11.c
            boolean r7 = r11.isSelected()
            if (r7 == 0) goto L_0x00aa
            android.widget.TextView r7 = r11.c
            r7.setEnabled(r10)
            r7 = r3
            goto L_0x00b0
        L_0x00aa:
            android.widget.TextView r7 = r11.c
            r7.setEnabled(r6)
            r7 = r0
        L_0x00b0:
            r5.setBackground(r7)
            android.widget.TextView r5 = r11.c
            zendesk.ui.android.conversation.quickreply.b r7 = r11.d
            zendesk.ui.android.conversation.quickreply.c r7 = r7.b()
            java.lang.String r7 = r7.d()
            r5.setText(r7)
            android.widget.TextView r5 = r11.c
            zendesk.ui.android.conversation.quickreply.b r7 = r11.d
            zendesk.ui.android.conversation.quickreply.c r7 = r7.b()
            java.lang.Integer r7 = r7.b()
            if (r7 != 0) goto L_0x00de
            android.content.Context r7 = r11.getContext()
            kotlin.jvm.internal.k.d(r7, r2)
            int r2 = zendesk.ui.android.R$attr.colorPrimary
            int r2 = zendesk.ui.android.internal.d.b(r7, r2)
            goto L_0x00e2
        L_0x00de:
            int r2 = r7.intValue()
        L_0x00e2:
            r5.setTextColor(r2)
            android.widget.TextView r2 = r11.c
            r7 = 0
            zendesk.ui.android.conversation.quickreply.QuickReplyOptionView$c r5 = new zendesk.ui.android.conversation.quickreply.QuickReplyOptionView$c
            r5.<init>(r11)
            r9 = 0
            zendesk.ui.android.internal.j r5 = zendesk.ui.android.internal.k.b(r7, r5, r6, r9)
            r2.setOnClickListener(r5)
            return
        L_0x00f7:
            java.lang.NullPointerException r2 = new java.lang.NullPointerException
            r2.<init>(r5)
            throw r2
        L_0x00fd:
            java.lang.NullPointerException r2 = new java.lang.NullPointerException
            r2.<init>(r5)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.ui.android.conversation.quickreply.QuickReplyOptionView.a(kotlin.jvm.functions.l):void");
    }

    /* compiled from: QuickReplyOptionView.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ QuickReplyOptionView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(QuickReplyOptionView quickReplyOptionView) {
            super(0);
            this.this$0 = quickReplyOptionView;
        }

        public final void invoke() {
            p onOptionClicked = this.this$0.d.a();
            if (onOptionClicked != null) {
                QuickReplyOptionView quickReplyOptionView = this.this$0;
                onOptionClicked.invoke(quickReplyOptionView.d.b().c(), quickReplyOptionView.d.b().d());
                quickReplyOptionView.setSelected(true);
                quickReplyOptionView.a(a.INSTANCE);
            }
        }

        /* compiled from: QuickReplyOptionView.kt */
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
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Parcelable onSaveInstanceState() {
        SavedState $this$onSaveInstanceState_u24lambda_u2d0 = new SavedState(super.onSaveInstanceState());
        $this$onSaveInstanceState_u24lambda_u2d0.b(String.valueOf(isSelected()));
        return $this$onSaveInstanceState_u24lambda_u2d0;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(@NotNull Parcelable state) {
        k.e(state, Constants.ACTION_STATE);
        if (state instanceof SavedState) {
            super.onRestoreInstanceState(((SavedState) state).getSuperState());
            setSelected(Boolean.parseBoolean(((SavedState) state).a()));
            a(b.INSTANCE);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /* compiled from: QuickReplyOptionView.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<b, b> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @NotNull
        public final b invoke(@NotNull b it) {
            k.e(it, "it");
            return it;
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchSaveInstanceState(@NotNull SparseArray<Parcelable> container) {
        k.e(container, "container");
        dispatchFreezeSelfOnly(container);
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(@NotNull SparseArray<Parcelable> container) {
        k.e(container, "container");
        dispatchThawSelfOnly(container);
    }

    /* compiled from: QuickReplyOptionView.kt */
    public static final class SavedState extends View.BaseSavedState {
        @NotNull
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        @NotNull
        public static final b c = new b((DefaultConstructorMarker) null);
        @Nullable
        private String d = Bugly.SDK_IS_DEV;

        @Nullable
        public final String a() {
            return this.d;
        }

        public final void b(@Nullable String str) {
            this.d = str;
        }

        public SavedState(@Nullable Parcelable superState) {
            super(superState);
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public SavedState(@NotNull Parcel source) {
            super(source);
            k.e(source, "source");
            this.d = source.readString();
        }

        public void writeToParcel(@NotNull Parcel out, int flags) {
            k.e(out, "out");
            super.writeToParcel(out, flags);
            out.writeString(this.d);
        }

        /* compiled from: QuickReplyOptionView.kt */
        public static final class b {
            public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private b() {
            }
        }

        /* compiled from: QuickReplyOptionView.kt */
        public static final class a implements Parcelable.Creator<SavedState> {
            a() {
            }

            @NotNull
            /* renamed from: a */
            public SavedState createFromParcel(@NotNull Parcel source) {
                k.e(source, "source");
                return new SavedState(source);
            }

            @NotNull
            /* renamed from: b */
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }
    }
}
