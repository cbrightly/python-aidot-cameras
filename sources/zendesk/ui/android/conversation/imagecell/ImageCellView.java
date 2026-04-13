package zendesk.ui.android.conversation.imagecell;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import coil.memory.MemoryCache;
import coil.request.i;
import coil.request.j;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.leedarson.bean.Constants;
import kotlin.NoWhenBranchMatchedException;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$attr;
import zendesk.ui.android.R$color;
import zendesk.ui.android.R$dimen;
import zendesk.ui.android.R$drawable;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;
import zendesk.ui.android.R$style;
import zendesk.ui.android.conversation.imagecell.d;
import zendesk.ui.android.conversation.textcell.TextCellView;

/* compiled from: ImageCellView.kt */
public final class ImageCellView extends ConstraintLayout implements zendesk.ui.android.a<b> {
    @NotNull
    private static final b c = new b((DefaultConstructorMarker) null);
    private final float a1;
    private final boolean a2;
    @NotNull
    private final TextCellView d;
    /* access modifiers changed from: private */
    @NotNull
    public final ShapeableImageView f;
    private final float p0;
    /* access modifiers changed from: private */
    public boolean p1;
    @NotNull
    private final kotlin.g p2;
    @NotNull
    private final kotlin.g p3;
    @Nullable
    private AnimatedVectorDrawableCompat p4;
    @NotNull
    private final ShapeableImageView q;
    /* access modifiers changed from: private */
    @NotNull
    public final TextView x;
    /* access modifiers changed from: private */
    @NotNull
    public b y;
    @Nullable
    private coil.request.f z;

    /* compiled from: ImageCellView.kt */
    public final /* synthetic */ class c {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[a.values().length];
            iArr[a.INBOUND_SINGLE.ordinal()] = 1;
            iArr[a.INBOUND_BOTTOM.ordinal()] = 2;
            iArr[a.INBOUND_TOP.ordinal()] = 3;
            iArr[a.INBOUND_MIDDLE.ordinal()] = 4;
            iArr[a.OUTBOUND_SINGLE.ordinal()] = 5;
            iArr[a.OUTBOUND_BOTTOM.ordinal()] = 6;
            iArr[a.OUTBOUND_TOP.ordinal()] = 7;
            iArr[a.OUTBOUND_MIDDLE.ordinal()] = 8;
            a = iArr;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ImageCellView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 6, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ImageCellView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ImageCellView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ImageCellView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        k.e(context, "context");
        this.y = new b();
        this.a2 = getResources().getConfiguration().getLayoutDirection() == 0;
        this.p2 = i.b(new g(context));
        this.p3 = i.b(new h(context));
        context.getTheme().applyStyle(R$style.ThemeOverlay_ZendeskComponents_TextCellStyle, false);
        ViewGroup.inflate(context, R$layout.zuia_view_image_cell, this);
        View findViewById = findViewById(R$id.zuia_text_cell_view);
        k.d(findViewById, "findViewById(R.id.zuia_text_cell_view)");
        this.d = (TextCellView) findViewById;
        View findViewById2 = findViewById(R$id.zuia_image_view);
        k.d(findViewById2, "findViewById(R.id.zuia_image_view)");
        this.f = (ShapeableImageView) findViewById2;
        View findViewById3 = findViewById(R$id.zuia_image_view_overlay);
        k.d(findViewById3, "findViewById(R.id.zuia_image_view_overlay)");
        this.q = (ShapeableImageView) findViewById3;
        View findViewById4 = findViewById(R$id.zuia_error_text);
        k.d(findViewById4, "findViewById(R.id.zuia_error_text)");
        this.x = (TextView) findViewById4;
        this.p0 = (float) zendesk.ui.android.internal.e.a(context, new int[]{R$attr.messageCellRadiusSize});
        this.a1 = (float) zendesk.ui.android.internal.e.a(context, new int[]{R$attr.messageCellSmallRadiusSize});
        a(a.INSTANCE);
    }

    /* compiled from: ImageCellView.kt */
    public static final class g extends l implements kotlin.jvm.functions.a<AnimatedVectorDrawableCompat> {
        final /* synthetic */ Context $context;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(Context context) {
            super(0);
            this.$context = context;
        }

        @Nullable
        public final AnimatedVectorDrawableCompat invoke() {
            return AnimatedVectorDrawableCompat.create(this.$context, R$drawable.zuia_skeleton_loader_inbound);
        }
    }

    private final AnimatedVectorDrawableCompat getSkeletonLoaderInboundAnimation() {
        return (AnimatedVectorDrawableCompat) this.p2.getValue();
    }

    /* compiled from: ImageCellView.kt */
    public static final class h extends l implements kotlin.jvm.functions.a<AnimatedVectorDrawableCompat> {
        final /* synthetic */ Context $context;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(Context context) {
            super(0);
            this.$context = context;
        }

        @Nullable
        public final AnimatedVectorDrawableCompat invoke() {
            return AnimatedVectorDrawableCompat.create(this.$context, R$drawable.zuia_skeleton_loader_outbound);
        }
    }

    private final AnimatedVectorDrawableCompat getSkeletonLoaderOutboundAnimation() {
        return (AnimatedVectorDrawableCompat) this.p3.getValue();
    }

    /* compiled from: ImageCellView.kt */
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
        int i;
        int i2;
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat;
        kotlin.jvm.functions.l<? super b, b> lVar = renderingUpdate;
        k.e(lVar, "renderingUpdate");
        b invoke = lVar.invoke(this.y);
        this.y = invoke;
        c $this$render_u24lambda_u2d5 = invoke.b();
        TextCellView textCellView = this.d;
        String h2 = $this$render_u24lambda_u2d5.h();
        if (h2 == null || h2.length() == 0) {
            i = 8;
        } else {
            this.d.a(new d($this$render_u24lambda_u2d5, this));
            this.d.setMessageTextGravity$zendesk_ui_ui_android(GravityCompat.START);
            i = 0;
        }
        textCellView.setVisibility(i);
        this.x.setText($this$render_u24lambda_u2d5.d());
        Context context = getContext();
        int i3 = R$color.zuia_color_red;
        ColorDrawable errorDrawable = new ColorDrawable(ContextCompat.getColor(context, i3));
        ShapeAppearanceModel shapeAppearanceModel = g(this.d.getVisibility() == 0);
        this.f.setShapeAppearanceModel(shapeAppearanceModel);
        this.q.setShapeAppearanceModel(shapeAppearanceModel);
        Integer c2 = $this$render_u24lambda_u2d5.c();
        if (c2 != null) {
            i2 = c2.intValue();
        } else if (a.Companion.a($this$render_u24lambda_u2d5.e())) {
            Context context2 = getContext();
            k.d(context2, "context");
            i2 = zendesk.ui.android.internal.d.b(context2, R$attr.messageCellInboundBackgroundColor);
        } else {
            Context context3 = getContext();
            k.d(context3, "context");
            i2 = zendesk.ui.android.internal.d.b(context3, R$attr.colorPrimary);
        }
        int actualBackgroundColor = i2;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
        MaterialShapeDrawable $this$render_u24lambda_u2d5_u24lambda_u2d0 = materialShapeDrawable;
        $this$render_u24lambda_u2d5_u24lambda_u2d0.setFillColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R$color.zuia_color_transparent)));
        $this$render_u24lambda_u2d5_u24lambda_u2d0.setStrokeWidth(getResources().getDimension(R$dimen.zuia_inner_stroke_width));
        $this$render_u24lambda_u2d5_u24lambda_u2d0.setStrokeColor(ColorStateList.valueOf(actualBackgroundColor));
        MaterialShapeDrawable backgroundDrawable = materialShapeDrawable;
        ColorDrawable defaultDrawable = new ColorDrawable(actualBackgroundColor);
        ColorDrawable errorColorOverlay = new ColorDrawable(zendesk.ui.android.internal.d.a(ContextCompat.getColor(getContext(), i3), 0.2f));
        if (a.Companion.a($this$render_u24lambda_u2d5.e())) {
            animatedVectorDrawableCompat = getSkeletonLoaderInboundAnimation();
        } else {
            animatedVectorDrawableCompat = getSkeletonLoaderOutboundAnimation();
        }
        this.p4 = animatedVectorDrawableCompat;
        this.f.setImageDrawable(animatedVectorDrawableCompat);
        this.f.setBackground(h(true, $this$render_u24lambda_u2d5, shapeAppearanceModel));
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat2 = this.p4;
        if (animatedVectorDrawableCompat2 != null) {
            animatedVectorDrawableCompat2.start();
        }
        this.f.setOnClickListener(zendesk.ui.android.internal.k.b(0, new e(this, $this$render_u24lambda_u2d5), 1, (Object) null));
        if ($this$render_u24lambda_u2d5.k()) {
            this.q.setVisibility(0);
            this.q.setImageDrawable(errorColorOverlay);
        } else {
            this.q.setVisibility(8);
            this.q.setImageDrawable((Drawable) null);
        }
        if ($this$render_u24lambda_u2d5.l()) {
            this.f.setAlpha(0.5f);
        } else {
            this.f.setAlpha(1.0f);
        }
        zendesk.ui.android.internal.f fVar = zendesk.ui.android.internal.f.a;
        Context context4 = getContext();
        k.d(context4, "context");
        coil.d imageLoader = fVar.c(context4);
        Uri g2 = $this$render_u24lambda_u2d5.g();
        if (g2 == null) {
            g2 = $this$render_u24lambda_u2d5.j();
        }
        Uri uriToLoad = g2;
        MemoryCache memoryCache = imageLoader.c();
        MemoryCache.Key.a aVar = MemoryCache.Key.c;
        Bitmap cachedBitmap = memoryCache.b(aVar.a(String.valueOf(uriToLoad)));
        if (cachedBitmap != null) {
            this.x.setVisibility(8);
            this.f.setImageBitmap(cachedBitmap);
            Bitmap bitmap = cachedBitmap;
            MemoryCache memoryCache2 = memoryCache;
            ColorDrawable colorDrawable = errorColorOverlay;
            c cVar = $this$render_u24lambda_u2d5;
            ShapeAppearanceModel shapeAppearanceModel2 = shapeAppearanceModel;
            Uri uri = uriToLoad;
            return;
        }
        Bitmap bitmap2 = cachedBitmap;
        if (e.Companion.a($this$render_u24lambda_u2d5.f())) {
            Context context5 = getContext();
            k.d(context5, "context");
            c cVar2 = $this$render_u24lambda_u2d5;
            MemoryCache memoryCache3 = memoryCache;
            ShapeAppearanceModel shapeAppearanceModel3 = shapeAppearanceModel;
            Uri uriToLoad2 = uriToLoad;
            ColorDrawable colorDrawable2 = errorColorOverlay;
            this.z = imageLoader.a(new i.a(context5).h(this.p4).k(this.p4).i(new f(this, backgroundDrawable, this, this, this)).k(defaultDrawable).d(true).e(uriToLoad2).j(aVar.a(String.valueOf(uriToLoad2))).w(this.f).b());
            return;
        }
        ColorDrawable colorDrawable3 = errorColorOverlay;
        c cVar3 = $this$render_u24lambda_u2d5;
        ShapeAppearanceModel shapeAppearanceModel4 = shapeAppearanceModel;
        Uri uri2 = uriToLoad;
        this.p1 = false;
        this.f.setBackground(backgroundDrawable);
        this.f.setImageDrawable(errorDrawable);
        this.x.setVisibility(0);
    }

    /* compiled from: ImageCellView.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.textcell.a, zendesk.ui.android.conversation.textcell.a> {
        final /* synthetic */ c $this_with;
        final /* synthetic */ ImageCellView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(c cVar, ImageCellView imageCellView) {
            super(1);
            this.$this_with = cVar;
            this.this$0 = imageCellView;
        }

        @NotNull
        public final zendesk.ui.android.conversation.textcell.a invoke(@NotNull zendesk.ui.android.conversation.textcell.a textCellRendering) {
            k.e(textCellRendering, "textCellRendering");
            return textCellRendering.d().j(new a(this.$this_with, this.this$0)).a();
        }

        /* compiled from: ImageCellView.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<zendesk.ui.android.conversation.textcell.b, zendesk.ui.android.conversation.textcell.b> {
            final /* synthetic */ c $this_with;
            final /* synthetic */ ImageCellView this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(c cVar, ImageCellView imageCellView) {
                super(1);
                this.$this_with = cVar;
                this.this$0 = imageCellView;
            }

            @NotNull
            public final zendesk.ui.android.conversation.textcell.b invoke(@NotNull zendesk.ui.android.conversation.textcell.b state) {
                k.e(state, Constants.ACTION_STATE);
                return state.a(this.$this_with.h(), this.$this_with.i(), this.$this_with.c(), Integer.valueOf(this.this$0.getTextCellViewBackgroundResource()));
            }
        }
    }

    /* compiled from: ImageCellView.kt */
    public static final class e extends l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ c $this_with;
        final /* synthetic */ ImageCellView this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(ImageCellView imageCellView, c cVar) {
            super(0);
            this.this$0 = imageCellView;
            this.$this_with = cVar;
        }

        public final void invoke() {
            kotlin.jvm.functions.l<String, x> a = this.this$0.y.a();
            if (a != null) {
                a.invoke(String.valueOf(this.$this_with.j()));
            }
        }
    }

    private final ShapeAppearanceModel g(boolean isMessageTextViewVisible) {
        ShapeAppearanceModel.Builder builder;
        d imageRoundedCorner = new d.a(this.p0, this.a1, this.y.b().e(), this.a2).a();
        ShapeAppearanceModel.Builder shapeAppearanceModel = new ShapeAppearanceModel().toBuilder().setTopLeftCorner(0, imageRoundedCorner.c()).setTopRightCorner(0, imageRoundedCorner.d());
        k.d(shapeAppearanceModel, "ShapeAppearanceModel().t…geRoundedCorner.topRight)");
        if (isMessageTextViewVisible) {
            builder = shapeAppearanceModel.setBottomRightCorner(0, 0.0f).setBottomLeftCorner(0, 0.0f);
        } else {
            builder = shapeAppearanceModel.setBottomRightCorner(0, imageRoundedCorner.b()).setBottomLeftCorner(0, imageRoundedCorner.a());
        }
        ShapeAppearanceModel build = builder.build();
        k.d(build, "if (isMessageTextViewVis…omLeft)\n        }.build()");
        return build;
    }

    /* access modifiers changed from: private */
    public final int getTextCellViewBackgroundResource() {
        switch (c.a[this.y.b().e().ordinal()]) {
            case 1:
            case 2:
                return R$drawable.zuia_image_cell_message_inbound_shape_single;
            case 3:
            case 4:
                return R$drawable.zuia_image_cell_message_inbound_shape_middle;
            case 5:
            case 6:
                return R$drawable.zuia_image_cell_message_outbound_shape_single;
            case 7:
            case 8:
                return R$drawable.zuia_image_cell_message_outbound_shape_middle;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final MaterialShapeDrawable h(boolean onStart, c state, ShapeAppearanceModel shapeAppearance) {
        int backgroundColor;
        int fillColor;
        Integer c2 = state.c();
        if (c2 != null) {
            backgroundColor = c2.intValue();
        } else if (a.Companion.a(state.e())) {
            Context context = getContext();
            k.d(context, "context");
            backgroundColor = zendesk.ui.android.internal.d.b(context, R$attr.messageCellInboundBackgroundColor);
        } else {
            Context context2 = getContext();
            k.d(context2, "context");
            backgroundColor = zendesk.ui.android.internal.d.b(context2, R$attr.colorPrimary);
        }
        if (onStart) {
            fillColor = backgroundColor;
        } else {
            fillColor = ContextCompat.getColor(getContext(), R$color.zuia_color_transparent);
        }
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapeAppearance);
        MaterialShapeDrawable $this$getImageBackground_u24lambda_u2d6 = materialShapeDrawable;
        $this$getImageBackground_u24lambda_u2d6.setFillColor(ColorStateList.valueOf(fillColor));
        if (!onStart) {
            $this$getImageBackground_u24lambda_u2d6.setStrokeWidth(getResources().getDimension(R$dimen.zuia_inner_stroke_width));
            $this$getImageBackground_u24lambda_u2d6.setStrokeColor(ColorStateList.valueOf(backgroundColor));
        }
        return materialShapeDrawable;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        coil.request.f fVar = this.z;
        if (fVar != null) {
            fVar.dispose();
        }
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat = this.p4;
        if (animatedVectorDrawableCompat != null) {
            animatedVectorDrawableCompat.stop();
        }
    }

    /* compiled from: ImageCellView.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }
    }

    /* compiled from: ImageRequest.kt */
    public static final class f implements i.b {
        final /* synthetic */ ImageCellView c;
        final /* synthetic */ MaterialShapeDrawable d;

        public f(ImageCellView imageCellView, MaterialShapeDrawable materialShapeDrawable, ImageCellView imageCellView2, ImageCellView imageCellView3, ImageCellView imageCellView4) {
            this.c = imageCellView;
            this.d = materialShapeDrawable;
        }

        public void b(@NotNull coil.request.i request) {
            k.e(request, Progress.REQUEST);
            coil.request.i iVar = request;
            this.c.p1 = false;
            this.c.f.setBackground(this.d);
            this.c.x.setVisibility(8);
        }

        public void a(@NotNull coil.request.i request) {
            k.e(request, Progress.REQUEST);
            coil.request.i iVar = request;
            this.c.p1 = false;
            this.c.x.setVisibility(0);
        }

        public void c(@NotNull coil.request.i request, @NotNull Throwable throwable) {
            k.e(request, Progress.REQUEST);
            k.e(throwable, "throwable");
            coil.request.i iVar = request;
            Throwable th = throwable;
            this.c.p1 = false;
            this.c.x.setVisibility(0);
        }

        public void d(@NotNull coil.request.i request, @NotNull j.a metadata) {
            k.e(request, Progress.REQUEST);
            k.e(metadata, "metadata");
            coil.request.i iVar = request;
            j.a aVar = metadata;
            this.c.p1 = true;
            this.c.f.setBackground((Drawable) null);
            this.c.x.setVisibility(8);
        }
    }
}
