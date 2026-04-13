package com.leedarson.base.views.photoview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;

public class AlbumPhotoView extends AppCompatImageView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private a c;
    private ImageView.ScaleType d;

    public AlbumPhotoView(Context context) {
        this(context, (AttributeSet) null);
    }

    public AlbumPhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public AlbumPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init();
    }

    private void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 854, new Class[0], Void.TYPE).isSupported) {
            this.c = new a(this);
            super.setScaleType(ImageView.ScaleType.MATRIX);
            ImageView.ScaleType scaleType = this.d;
            if (scaleType != null) {
                setScaleType(scaleType);
                this.d = null;
            }
        }
    }

    public a getAttacher() {
        return this.c;
    }

    public ImageView.ScaleType getScaleType() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 855, new Class[0], ImageView.ScaleType.class);
        return proxy.isSupported ? (ImageView.ScaleType) proxy.result : this.c.N();
    }

    public Matrix getImageMatrix() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 856, new Class[0], Matrix.class);
        return proxy.isSupported ? (Matrix) proxy.result : this.c.G();
    }

    public void setOnLongClickListener(View.OnLongClickListener l) {
        if (!PatchProxy.proxy(new Object[]{l}, this, changeQuickRedirect, false, 857, new Class[]{View.OnLongClickListener.class}, Void.TYPE).isSupported) {
            this.c.setOnLongClickListener(l);
        }
    }

    public void setOnClickListener(View.OnClickListener l) {
        if (!PatchProxy.proxy(new Object[]{l}, this, changeQuickRedirect, false, 858, new Class[]{View.OnClickListener.class}, Void.TYPE).isSupported) {
            this.c.setOnClickListener(l);
        }
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        if (!PatchProxy.proxy(new Object[]{scaleType}, this, changeQuickRedirect, false, 859, new Class[]{ImageView.ScaleType.class}, Void.TYPE).isSupported) {
            a aVar = this.c;
            if (aVar == null) {
                this.d = scaleType;
            } else {
                aVar.a0(scaleType);
            }
        }
    }

    public void setImageDrawable(Drawable drawable) {
        if (!PatchProxy.proxy(new Object[]{drawable}, this, changeQuickRedirect, false, 860, new Class[]{Drawable.class}, Void.TYPE).isSupported) {
            super.setImageDrawable(drawable);
            a aVar = this.c;
            if (aVar != null) {
                aVar.d0();
            }
        }
    }

    public void setImageResource(int resId) {
        Object[] objArr = {new Integer(resId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 861, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            super.setImageResource(resId);
            a aVar = this.c;
            if (aVar != null) {
                aVar.d0();
            }
        }
    }

    public void setImageURI(Uri uri) {
        if (!PatchProxy.proxy(new Object[]{uri}, this, changeQuickRedirect, false, 862, new Class[]{Uri.class}, Void.TYPE).isSupported) {
            super.setImageURI(uri);
            a aVar = this.c;
            if (aVar != null) {
                aVar.d0();
            }
        }
    }

    public boolean setFrame(int l, int t, int r, int b) {
        Object[] objArr = {new Integer(l), new Integer(t), new Integer(r), new Integer(b)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 863, new Class[]{cls, cls, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        boolean changed = super.setFrame(l, t, r, b);
        if (changed) {
            this.c.d0();
        }
        return changed;
    }

    public void setRotationTo(float rotationDegree) {
        Object[] objArr = {new Float(rotationDegree)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_ENVIRONMENT_REQ, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.c.W(rotationDegree);
        }
    }

    public void setRotationBy(float rotationDegree) {
        Object[] objArr = {new Float(rotationDegree)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_ENVIRONMENT_RESP, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.c.V(rotationDegree);
        }
    }

    public void setZoomable(boolean zoomable) {
        Object[] objArr = {new Byte(zoomable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_ENVIRONMENT_RESP, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.c.c0(zoomable);
        }
    }

    public RectF getDisplayRect() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 868, new Class[0], RectF.class);
        return proxy.isSupported ? (RectF) proxy.result : this.c.D();
    }

    public float getMinimumScale() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 873, new Class[0], Float.TYPE);
        return proxy.isSupported ? ((Float) proxy.result).floatValue() : this.c.L();
    }

    public float getMediumScale() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 874, new Class[0], Float.TYPE);
        return proxy.isSupported ? ((Float) proxy.result).floatValue() : this.c.K();
    }

    public float getMaximumScale() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 875, new Class[0], Float.TYPE);
        return proxy.isSupported ? ((Float) proxy.result).floatValue() : this.c.J();
    }

    public float getScale() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 876, new Class[0], Float.TYPE);
        return proxy.isSupported ? ((Float) proxy.result).floatValue() : this.c.M();
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        Object[] objArr = {new Byte(allow ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 877, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.c.Q(allow);
        }
    }

    public void setMinimumScale(float minimumScale) {
        Object[] objArr = {new Float(minimumScale)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 878, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.c.U(minimumScale);
        }
    }

    public void setMediumScale(float mediumScale) {
        Object[] objArr = {new Float(mediumScale)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 879, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.c.T(mediumScale);
        }
    }

    public void setMaximumScale(float maximumScale) {
        Object[] objArr = {new Float(maximumScale)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_VIDEOMODE_REQ, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.c.S(maximumScale);
        }
    }

    public void setOnMatrixChangeListener(e listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_VIDEOMODE_REQ, new Class[]{e.class}, Void.TYPE).isSupported) {
            this.c.setOnMatrixChangeListener(listener);
        }
    }

    public void setOnPhotoTapListener(g listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_VIDEOMODE_RESP, new Class[]{g.class}, Void.TYPE).isSupported) {
            this.c.setOnPhotoTapListener(listener);
        }
    }

    public void setOnOutsidePhotoTapListener(f listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 884, new Class[]{f.class}, Void.TYPE).isSupported) {
            this.c.setOnOutsidePhotoTapListener(listener);
        }
    }

    public void setOnViewTapListener(k listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 885, new Class[]{k.class}, Void.TYPE).isSupported) {
            this.c.setOnViewTapListener(listener);
        }
    }

    public void setOnViewDragListener(j listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 886, new Class[]{j.class}, Void.TYPE).isSupported) {
            this.c.setOnViewDragListener(listener);
        }
    }

    public void setScale(float scale) {
        Object[] objArr = {new Float(scale)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 887, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.c.X(scale);
        }
    }

    public void setZoomTransitionDuration(int milliseconds) {
        Object[] objArr = {new Integer(milliseconds)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 890, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.c.b0(milliseconds);
        }
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        if (!PatchProxy.proxy(new Object[]{onDoubleTapListener}, this, changeQuickRedirect, false, 891, new Class[]{GestureDetector.OnDoubleTapListener.class}, Void.TYPE).isSupported) {
            this.c.setOnDoubleTapListener(onDoubleTapListener);
        }
    }

    public void setOnScaleChangeListener(h onScaleChangedListener) {
        if (!PatchProxy.proxy(new Object[]{onScaleChangedListener}, this, changeQuickRedirect, false, 892, new Class[]{h.class}, Void.TYPE).isSupported) {
            this.c.setOnScaleChangeListener(onScaleChangedListener);
        }
    }

    public void setOnSingleFlingListener(i onSingleFlingListener) {
        if (!PatchProxy.proxy(new Object[]{onSingleFlingListener}, this, changeQuickRedirect, false, 893, new Class[]{i.class}, Void.TYPE).isSupported) {
            this.c.setOnSingleFlingListener(onSingleFlingListener);
        }
    }
}
