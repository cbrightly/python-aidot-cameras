package zendesk.ui.android.conversation.avatar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import coil.d;
import coil.request.f;
import coil.request.h;
import coil.request.i;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.ui.android.R$drawable;
import zendesk.ui.android.R$id;
import zendesk.ui.android.R$layout;

/* compiled from: AvatarImageView.kt */
public final class AvatarImageView extends FrameLayout implements zendesk.ui.android.a<a> {
    @NotNull
    private final FrameLayout c;
    @NotNull
    private final ShapeableImageView d;
    @Nullable
    private f f;
    @NotNull
    private a q;
    @NotNull
    private final g x;

    /* compiled from: AvatarImageView.kt */
    public final /* synthetic */ class b {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[c.values().length];
            iArr[c.NONE.ordinal()] = 1;
            iArr[c.CIRCLE.ordinal()] = 2;
            a = iArr;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public AvatarImageView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public AvatarImageView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public AvatarImageView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ AvatarImageView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AvatarImageView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttrs, int defStyleRes) {
        super(context, attrs, defStyleAttrs, defStyleRes);
        k.e(context, "context");
        this.q = new a();
        this.x = i.b(new c(context));
        FrameLayout.inflate(context, R$layout.zuia_view_avatar_image, this);
        View findViewById = findViewById(R$id.zuia_avatar_container);
        k.d(findViewById, "findViewById(R.id.zuia_avatar_container)");
        this.c = (FrameLayout) findViewById;
        View findViewById2 = findViewById(R$id.zuia_avatar_image_view);
        k.d(findViewById2, "findViewById(R.id.zuia_avatar_image_view)");
        this.d = (ShapeableImageView) findViewById2;
        a(a.INSTANCE);
    }

    /* compiled from: AvatarImageView.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<AnimatedVectorDrawableCompat> {
        final /* synthetic */ Context $context;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(Context context) {
            super(0);
            this.$context = context;
        }

        @Nullable
        public final AnimatedVectorDrawableCompat invoke() {
            return AnimatedVectorDrawableCompat.create(this.$context, R$drawable.zuia_skeleton_loader_inbound);
        }
    }

    private final AnimatedVectorDrawableCompat getSkeletonLoaderDrawable() {
        return (AnimatedVectorDrawableCompat) this.x.getValue();
    }

    /* compiled from: AvatarImageView.kt */
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
        ShapeAppearanceModel shapeAppearanceModel;
        ColorStateList colorStateList;
        k.e(renderingUpdate, "renderingUpdate");
        this.q = renderingUpdate.invoke(this.q);
        int avatarSize = getResources().getDimensionPixelSize(this.q.a().c());
        ShapeableImageView $this$render_u24lambda_u2d2 = this.d;
        switch (b.a[this.q.a().e().ordinal()]) {
            case 1:
                shapeAppearanceModel = new ShapeAppearanceModel().toBuilder().setAllCorners(0, 0.0f).build();
                break;
            case 2:
                shapeAppearanceModel = new ShapeAppearanceModel().toBuilder().setAllCorners(0, ((float) avatarSize) / ((float) 2)).build();
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        k.d(shapeAppearanceModel, "when (rendering.state.ma…   .build()\n            }");
        $this$render_u24lambda_u2d2.setShapeAppearanceModel(shapeAppearanceModel);
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
        MaterialShapeDrawable $this$render_u24lambda_u2d2_u24lambda_u2d1 = materialShapeDrawable;
        Integer d2 = this.q.a().d();
        if (d2 == null) {
            colorStateList = null;
        } else {
            colorStateList = ColorStateList.valueOf(d2.intValue());
        }
        $this$render_u24lambda_u2d2_u24lambda_u2d1.setFillColor(colorStateList);
        x xVar = x.a;
        $this$render_u24lambda_u2d2.setBackground(materialShapeDrawable);
        $this$render_u24lambda_u2d2.setImageDrawable(getSkeletonLoaderDrawable());
        AnimatedVectorDrawableCompat skeletonLoaderDrawable = getSkeletonLoaderDrawable();
        if (skeletonLoaderDrawable != null) {
            skeletonLoaderDrawable.start();
        }
        FrameLayout $this$render_u24lambda_u2d4 = this.c;
        ViewGroup.LayoutParams $this$render_u24lambda_u2d4_u24lambda_u2d3 = $this$render_u24lambda_u2d4.getLayoutParams();
        $this$render_u24lambda_u2d4_u24lambda_u2d3.height = avatarSize;
        $this$render_u24lambda_u2d4_u24lambda_u2d3.width = avatarSize;
        $this$render_u24lambda_u2d4.setClipChildren(true);
        $this$render_u24lambda_u2d4.setClipToOutline(true);
        ShapeableImageView $this$render_u24lambda_u2d7 = this.d;
        ViewGroup.LayoutParams $this$render_u24lambda_u2d7_u24lambda_u2d5 = $this$render_u24lambda_u2d7.getLayoutParams();
        $this$render_u24lambda_u2d7_u24lambda_u2d5.height = avatarSize;
        $this$render_u24lambda_u2d7_u24lambda_u2d5.width = avatarSize;
        f fVar = this.f;
        if (fVar != null) {
            fVar.dispose();
        }
        Uri avatarUri = this.q.a().g();
        if (avatarUri != null) {
            zendesk.ui.android.internal.f fVar2 = zendesk.ui.android.internal.f.a;
            Context context = $this$render_u24lambda_u2d7.getContext();
            k.d(context, "context");
            d imageLoader = fVar2.c(context);
            Context context2 = $this$render_u24lambda_u2d7.getContext();
            k.d(context2, "context");
            i.a k = new i.a(context2).e(avatarUri).g(ResourcesCompat.getDrawable($this$render_u24lambda_u2d7.getContext().getResources(), R$drawable.zuia_avatar_default, $this$render_u24lambda_u2d7.getContext().getTheme())).h(getSkeletonLoaderDrawable()).k(getSkeletonLoaderDrawable());
            i.a $this$render_u24lambda_u2d7_u24lambda_u2d6 = k;
            if (!this.q.a().f()) {
                h.d($this$render_u24lambda_u2d7_u24lambda_u2d6, 0);
            }
            this.f = imageLoader.a(k.w($this$render_u24lambda_u2d7).b());
            return;
        }
        $this$render_u24lambda_u2d7.setBackground((Drawable) null);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        f fVar = this.f;
        if (fVar != null) {
            fVar.dispose();
        }
        AnimatedVectorDrawableCompat skeletonLoaderDrawable = getSkeletonLoaderDrawable();
        if (skeletonLoaderDrawable != null) {
            skeletonLoaderDrawable.stop();
        }
    }
}
