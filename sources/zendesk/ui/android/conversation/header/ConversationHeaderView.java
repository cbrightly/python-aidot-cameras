package zendesk.ui.android.conversation.header;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import coil.d;
import coil.request.f;
import coil.request.i;
import com.google.android.material.appbar.MaterialToolbar;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$dimen;
import zendesk.ui.android.R$drawable;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$string;

/* compiled from: ConversationHeaderView.kt */
public final class ConversationHeaderView extends FrameLayout implements zendesk.ui.android.a<b> {
    @NotNull
    private final MaterialToolbar c;
    @Nullable
    private f d;
    @NotNull
    private b f;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ConversationHeaderView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ConversationHeaderView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ConversationHeaderView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ConversationHeaderView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ConversationHeaderView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.f = new b();
        FrameLayout.inflate(context, R$layout.zuia_view_conversation_header, this);
        View findViewById = findViewById(R$id.zuia_conversation_header_toolbar);
        k.d(findViewById, "findViewById(R.id.zuia_c…versation_header_toolbar)");
        this.c = (MaterialToolbar) findViewById;
        a(a.INSTANCE);
    }

    /* compiled from: ConversationHeaderView.kt */
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
        x xVar;
        x xVar2;
        k.e(renderingUpdate, "renderingUpdate");
        b invoke = renderingUpdate.invoke(this.f);
        this.f = invoke;
        MaterialToolbar $this$render_u24lambda_u2d8 = this.c;
        kotlin.jvm.functions.a onBackButtonClicked = invoke.a();
        if (onBackButtonClicked == null) {
            xVar = null;
        } else {
            $this$render_u24lambda_u2d8.setTitleMarginStart($this$render_u24lambda_u2d8.getResources().getDimensionPixelSize(R$dimen.zuia_header_logo_content_insert));
            $this$render_u24lambda_u2d8.setNavigationIcon(R$drawable.zuia_ic_arrow_back);
            $this$render_u24lambda_u2d8.setNavigationOnClickListener(new a(onBackButtonClicked));
            xVar = x.a;
        }
        if (xVar == null) {
            MaterialToolbar $this$render_u24lambda_u2d8_u24lambda_u2d2 = $this$render_u24lambda_u2d8;
            $this$render_u24lambda_u2d8_u24lambda_u2d2.setTitleMarginStart($this$render_u24lambda_u2d8_u24lambda_u2d2.getResources().getDimensionPixelSize(R$dimen.zuia_header_logo_margin));
            $this$render_u24lambda_u2d8_u24lambda_u2d2.setNavigationOnClickListener((View.OnClickListener) null);
        }
        Integer c2 = this.f.b().c();
        if (c2 != null) {
            $this$render_u24lambda_u2d8.setBackground(new ColorDrawable(c2.intValue()));
        }
        Integer f2 = this.f.b().f();
        if (f2 != null) {
            int it = f2.intValue();
            Activity b2 = b($this$render_u24lambda_u2d8);
            Window window = b2 == null ? null : b2.getWindow();
            if (window != null) {
                window.setStatusBarColor(it);
            }
        }
        $this$render_u24lambda_u2d8.setTitle((CharSequence) this.f.b().g());
        $this$render_u24lambda_u2d8.setSubtitle((CharSequence) this.f.b().d());
        Uri brandAvatar = this.f.b().e();
        if (brandAvatar == null) {
            xVar2 = null;
        } else {
            int logoSize = $this$render_u24lambda_u2d8.getResources().getDimensionPixelSize(R$dimen.zuia_avatar_image_size);
            zendesk.ui.android.internal.f fVar = zendesk.ui.android.internal.f.a;
            Context context = $this$render_u24lambda_u2d8.getContext();
            k.d(context, "context");
            d imageLoader = fVar.c(context);
            Context context2 = $this$render_u24lambda_u2d8.getContext();
            k.d(context2, "context");
            this.d = imageLoader.a(new i.a(context2).e(brandAvatar).s(logoSize).z(new coil.transform.b()).x(new b($this$render_u24lambda_u2d8)).b());
            xVar2 = x.a;
        }
        if (xVar2 == null) {
            $this$render_u24lambda_u2d8.setLogo((Drawable) null);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void d(kotlin.jvm.functions.a $onBackButtonClicked, View view) {
        View view2 = view;
        k.e($onBackButtonClicked, "$onBackButtonClicked");
        $onBackButtonClicked.invoke();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        f fVar = this.d;
        if (fVar != null) {
            fVar.dispose();
        }
    }

    private final Activity b(View $this$getActivity) {
        Context context = $this$getActivity.getContext();
        k.d(context, "context");
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            k.d(baseContext, "context.baseContext");
            context = baseContext;
        }
        return null;
    }

    /* compiled from: ImageRequest.kt */
    public static final class b implements coil.target.b {
        final /* synthetic */ MaterialToolbar c;

        public b(MaterialToolbar materialToolbar) {
            this.c = materialToolbar;
        }

        public void b(@Nullable Drawable placeholder) {
            Drawable drawable = placeholder;
        }

        public void c(@Nullable Drawable error) {
            Drawable drawable = error;
        }

        public void a(@NotNull Drawable result) {
            k.e(result, "result");
            this.c.setLogo(result);
            MaterialToolbar materialToolbar = this.c;
            materialToolbar.setLogoDescription((CharSequence) materialToolbar.getContext().getString(R$string.zuia_conversation_header_logo));
        }
    }
}
