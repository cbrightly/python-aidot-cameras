package zendesk.ui.android.conversation.connectionbanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$attr;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$integer;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$string;
import zendesk.ui.android.R$style;
import zendesk.ui.android.conversation.connectionbanner.e;
import zendesk.ui.android.internal.d;
import zendesk.ui.android.internal.e;

/* compiled from: ConnectionBannerView.kt */
public final class ConnectionBannerView extends FrameLayout implements zendesk.ui.android.a<d> {
    @NotNull
    private static final b c = new b((DefaultConstructorMarker) null);
    @NotNull
    private d d;
    @NotNull
    private final ConstraintLayout f;
    private final long p0;
    @NotNull
    private final TextView q;
    @NotNull
    private final ImageView x;
    @NotNull
    private final GradientDrawable y;
    private boolean z;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ConnectionBannerView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ConnectionBannerView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ConnectionBannerView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ConnectionBannerView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ConnectionBannerView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.d = new d();
        this.y = new GradientDrawable();
        context.getTheme().applyStyle(R$style.ThemeOverlay_ZendeskComponents_ConnectionBannerStyle, false);
        FrameLayout.inflate(context, R$layout.zuia_view_connection_banner, this);
        View findViewById = findViewById(R$id.zuia_connection_banner);
        k.d(findViewById, "findViewById(R.id.zuia_connection_banner)");
        this.f = (ConstraintLayout) findViewById;
        View findViewById2 = findViewById(R$id.zuia_banner_label);
        k.d(findViewById2, "findViewById(R.id.zuia_banner_label)");
        this.q = (TextView) findViewById2;
        View findViewById3 = findViewById(R$id.zuia_retry_button);
        k.d(findViewById3, "findViewById(R.id.zuia_retry_button)");
        this.x = (ImageView) findViewById3;
        this.p0 = (long) getResources().getInteger(R$integer.zuia_connection_banner_animation_duration);
        GradientDrawable $this$_init__u24lambda_u2d0 = this.y;
        $this$_init__u24lambda_u2d0.setShape(0);
        $this$_init__u24lambda_u2d0.setCornerRadius((float) e.a(context, new int[]{R$attr.connectionBannerRadius}));
        setVisibility(8);
        a(a.INSTANCE);
    }

    /* compiled from: ConnectionBannerView.kt */
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

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@Nullable MotionEvent event) {
        return true;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState $this$onSaveInstanceState_u24lambda_u2d1 = savedState;
        $this$onSaveInstanceState_u24lambda_u2d1.d(getVisibility());
        $this$onSaveInstanceState_u24lambda_u2d1.c(this.d.b().b());
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(@Nullable Parcelable state) {
        if (state instanceof SavedState) {
            super.onRestoreInstanceState(((SavedState) state).getSuperState());
            setVisibility(((SavedState) state).b());
            a(new c(state));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /* compiled from: ConnectionBannerView.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<d, d> {
        final /* synthetic */ Parcelable $state;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(Parcelable parcelable) {
            super(1);
            this.$state = parcelable;
        }

        /* compiled from: ConnectionBannerView.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<e, e> {
            final /* synthetic */ Parcelable $state;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(Parcelable parcelable) {
                super(1);
                this.$state = parcelable;
            }

            @NotNull
            public final e invoke(@NotNull e it) {
                k.e(it, "it");
                return it.a(((SavedState) this.$state).a());
            }
        }

        @NotNull
        public final d invoke(@NotNull d it) {
            k.e(it, "it");
            return it.c().g(new a(this.$state)).a();
        }
    }

    public void a(@NotNull kotlin.jvm.functions.l<? super d, d> renderingUpdate) {
        k.e(renderingUpdate, "renderingUpdate");
        this.d = renderingUpdate.invoke(this.d);
        this.x.setOnClickListener(new a(this));
        if (getVisibility() == 0 || k.a(this.d.b().b(), e.a.b.b)) {
            int backgroundColor = R$attr.connectionBannerBackgroundColor;
            int labelColor = R$attr.connectionBannerLabelColor;
            int retryButtonVisibility = 8;
            e.a b2 = this.d.b().b();
            boolean z2 = true;
            if (k.a(b2, e.a.b.b) ? true : k.a(b2, e.a.C0559a.b)) {
                this.q.setText(R$string.zuia_connection_banner_label_disconnected);
                retryButtonVisibility = 0;
                this.z = true;
            } else if (k.a(b2, e.a.d.b)) {
                this.q.setText(R$string.zuia_connection_banner_label_reconnecting);
                this.z = false;
            } else if (k.a(b2, e.a.c.b)) {
                this.q.setText(R$string.zuia_connection_banner_label_reconnected);
                backgroundColor = R$attr.connectionBannerSuccessBackgroundColor;
                labelColor = R$attr.connectionBannerSuccessLabelColor;
                if (getVisibility() != 0) {
                    z2 = false;
                }
                this.z = z2;
                onSaveInstanceState();
            }
            GradientDrawable gradientDrawable = this.y;
            Context context = getContext();
            k.d(context, "context");
            gradientDrawable.setColor(d.b(context, backgroundColor));
            this.x.setVisibility(retryButtonVisibility);
            TextView textView = this.q;
            Context context2 = getContext();
            k.d(context2, "context");
            textView.setTextColor(d.b(context2, labelColor));
            Drawable drawable = this.x.getDrawable();
            Context context3 = getContext();
            k.d(context3, "context");
            drawable.setTint(d.b(context3, labelColor));
            this.f.setBackground(this.y);
            if (this.z) {
                f();
                return;
            }
            return;
        }
        animate().cancel();
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void e(ConnectionBannerView this$0, View view) {
        View view2 = view;
        k.e(this$0, "this$0");
        this$0.d.a().invoke();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private final void f() {
        animate().setDuration(300).setStartDelay(this.p0);
        if (k.a(this.d.b().b(), e.a.b.b)) {
            animate().alpha(1.0f).withStartAction(new c(this)).start();
        }
        if (k.a(this.d.b().b(), e.a.c.b)) {
            animate().alpha(0.0f).withEndAction(new b(this)).start();
        }
    }

    /* access modifiers changed from: private */
    public static final void g(ConnectionBannerView this$0) {
        k.e(this$0, "this$0");
        this$0.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public static final void h(ConnectionBannerView this$0) {
        k.e(this$0, "this$0");
        this$0.setVisibility(8);
    }

    /* compiled from: ConnectionBannerView.kt */
    public static final class SavedState extends View.BaseSavedState {
        @NotNull
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        @NotNull
        public static final b c = new b((DefaultConstructorMarker) null);
        private int d = 8;
        @NotNull
        private e.a f = e.a.C0559a.b;

        public final int b() {
            return this.d;
        }

        public final void d(int i) {
            this.d = i;
        }

        @NotNull
        public final e.a a() {
            return this.f;
        }

        public final void c(@NotNull e.a aVar) {
            k.e(aVar, "<set-?>");
            this.f = aVar;
        }

        public SavedState(@Nullable Parcelable superState) {
            super(superState);
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public SavedState(@NotNull Parcel source) {
            super(source);
            k.e(source, "source");
            this.d = source.readInt();
            this.f = c.a(String.valueOf(source.readString()));
        }

        public void writeToParcel(@NotNull Parcel out, int flags) {
            k.e(out, "out");
            super.writeToParcel(out, flags);
            out.writeInt(this.d);
            out.writeString(this.f.a());
        }

        /* compiled from: ConnectionBannerView.kt */
        public static final class b {
            public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private b() {
            }

            @NotNull
            public final e.a a(@NotNull String stateValue) {
                k.e(stateValue, "stateValue");
                switch (stateValue.hashCode()) {
                    case -1217068453:
                        if (stateValue.equals("Disconnected")) {
                            return e.a.b.b;
                        }
                        break;
                    case -273361386:
                        if (stateValue.equals("Reconnected")) {
                            return e.a.c.b;
                        }
                        break;
                    case 115735883:
                        if (stateValue.equals("Reconnecting")) {
                            return e.a.d.b;
                        }
                        break;
                }
                return e.a.C0559a.b;
            }
        }

        /* compiled from: ConnectionBannerView.kt */
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

    /* compiled from: ConnectionBannerView.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }
    }
}
