package smarthome.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import com.bumptech.glide.h;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.target.e;
import com.leedarson.base.R$drawable;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.listener.OnImageCompleteCallback;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

/* compiled from: GlideEngine */
public class i implements ImageEngine {
    private static i a;

    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        com.bumptech.glide.b.t(context).q(url).H0(imageView);
    }

    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView, OnImageCompleteCallback callback) {
        com.bumptech.glide.b.t(context).i().M0(url).D0(new a(imageView, callback, longImageView, imageView));
    }

    /* compiled from: GlideEngine */
    public class a extends e<Bitmap> {
        final /* synthetic */ SubsamplingScaleImageView a2;
        final /* synthetic */ OnImageCompleteCallback p1;
        final /* synthetic */ ImageView p2;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(ImageView view, OnImageCompleteCallback onImageCompleteCallback, SubsamplingScaleImageView subsamplingScaleImageView, ImageView imageView) {
            super(view);
            this.p1 = onImageCompleteCallback;
            this.a2 = subsamplingScaleImageView;
            this.p2 = imageView;
        }

        public void b(@Nullable Drawable placeholder) {
            super.b(placeholder);
            OnImageCompleteCallback onImageCompleteCallback = this.p1;
            if (onImageCompleteCallback != null) {
                onImageCompleteCallback.onShowLoading();
            }
        }

        public void f(@Nullable Drawable errorDrawable) {
            super.f(errorDrawable);
            OnImageCompleteCallback onImageCompleteCallback = this.p1;
            if (onImageCompleteCallback != null) {
                onImageCompleteCallback.onHideLoading();
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: p */
        public void n(@Nullable Bitmap resource) {
            OnImageCompleteCallback onImageCompleteCallback = this.p1;
            if (onImageCompleteCallback != null) {
                onImageCompleteCallback.onHideLoading();
            }
            if (resource != null) {
                boolean eqLongImage = MediaUtils.isLongImg(resource.getWidth(), resource.getHeight());
                int i = 8;
                this.a2.setVisibility(eqLongImage ? 0 : 8);
                ImageView imageView = this.p2;
                if (!eqLongImage) {
                    i = 0;
                }
                imageView.setVisibility(i);
                if (eqLongImage) {
                    this.a2.setQuickScaleEnabled(true);
                    this.a2.setZoomEnabled(true);
                    this.a2.setDoubleTapZoomDuration(100);
                    this.a2.setMinimumScaleType(2);
                    this.a2.setDoubleTapZoomDpi(2);
                    this.a2.setImage(ImageSource.bitmap(resource), new ImageViewState(0.0f, new PointF(0.0f, 0.0f), 0));
                    return;
                }
                this.p2.setImageBitmap(resource);
            }
        }
    }

    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView) {
        com.bumptech.glide.b.t(context).i().M0(url).D0(new b(imageView, longImageView, imageView));
    }

    /* compiled from: GlideEngine */
    public class b extends e<Bitmap> {
        final /* synthetic */ ImageView a2;
        final /* synthetic */ SubsamplingScaleImageView p1;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(ImageView view, SubsamplingScaleImageView subsamplingScaleImageView, ImageView imageView) {
            super(view);
            this.p1 = subsamplingScaleImageView;
            this.a2 = imageView;
        }

        /* access modifiers changed from: protected */
        /* renamed from: p */
        public void n(@Nullable Bitmap resource) {
            if (resource != null) {
                boolean eqLongImage = MediaUtils.isLongImg(resource.getWidth(), resource.getHeight());
                int i = 8;
                this.p1.setVisibility(eqLongImage ? 0 : 8);
                ImageView imageView = this.a2;
                if (!eqLongImage) {
                    i = 0;
                }
                imageView.setVisibility(i);
                if (eqLongImage) {
                    this.p1.setQuickScaleEnabled(true);
                    this.p1.setZoomEnabled(true);
                    this.p1.setDoubleTapZoomDuration(100);
                    this.p1.setMinimumScaleType(2);
                    this.p1.setDoubleTapZoomDpi(2);
                    this.p1.setImage(ImageSource.bitmap(resource), new ImageViewState(0.0f, new PointF(0.0f, 0.0f), 0));
                    return;
                }
                this.a2.setImageBitmap(resource);
            }
        }
    }

    public void loadFolderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        ((h) ((h) ((h) com.bumptech.glide.b.t(context).i().M0(url).c0(180, 180)).c()).l0(0.5f)).a(new f().d0(R$drawable.picture_image_placeholder)).D0(new c(imageView, context, imageView));
    }

    /* compiled from: GlideEngine */
    public class c extends com.bumptech.glide.request.target.b {
        final /* synthetic */ ImageView a2;
        final /* synthetic */ Context p1;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(ImageView view, Context context, ImageView imageView) {
            super(view);
            this.p1 = context;
            this.a2 = imageView;
        }

        /* access modifiers changed from: protected */
        /* renamed from: p */
        public void n(Bitmap resource) {
            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(this.p1.getResources(), resource);
            circularBitmapDrawable.setCornerRadius(8.0f);
            this.a2.setImageDrawable(circularBitmapDrawable);
        }
    }

    public void loadAsGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        com.bumptech.glide.b.t(context).k().M0(url).H0(imageView);
    }

    public void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        ((h) ((h) com.bumptech.glide.b.t(context).q(url).c0(200, 200)).c()).a(new f().d0(R$drawable.picture_image_placeholder)).H0(imageView);
    }

    private i() {
    }

    public static i a() {
        if (a == null) {
            synchronized (i.class) {
                if (a == null) {
                    a = new i();
                }
            }
        }
        return a;
    }
}
