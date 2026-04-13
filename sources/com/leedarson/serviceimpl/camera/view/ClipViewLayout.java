package com.leedarson.serviceimpl.camera.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.leedarson.serviceimpl.camera.R$styleable;
import com.leedarson.serviceimpl.camera.h;
import com.leedarson.serviceimpl.camera.view.ClipView;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.io.IOException;
import timber.log.a;

public class ClipViewLayout extends RelativeLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int A4;
    private int B4;
    private Bitmap C4;
    private Bitmap D4;
    private int a1;
    private PointF a2;
    private String c;
    /* access modifiers changed from: private */
    public ImageView d;
    private ClipView f;
    private Matrix p0;
    private PointF p1;
    private float p2;
    private final float[] p3;
    private float p4;
    private float q;
    private float x;
    private Matrix y;
    private Matrix z;
    private float z4;

    public ClipViewLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClipViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.c = "ClipViewLayout";
        this.y = new Matrix();
        this.z = new Matrix();
        this.p0 = new Matrix();
        this.a1 = 0;
        this.p1 = new PointF();
        this.a2 = new PointF();
        this.p2 = 1.0f;
        this.p3 = new float[9];
        this.z4 = 4.0f;
        this.A4 = 0;
        this.B4 = 90;
        h(context, attrs);
    }

    public void h(Context context, AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{context, attrs}, this, changeQuickRedirect, false, 7496, new Class[]{Context.class, AttributeSet.class}, Void.TYPE).isSupported) {
            TypedArray array = context.obtainStyledAttributes(attrs, R$styleable.ClipViewLayout);
            this.q = (float) array.getDimensionPixelSize(R$styleable.ClipViewLayout_mHorizontalPadding, (int) TypedValue.applyDimension(1, 50.0f, getResources().getDisplayMetrics()));
            int clipBorderWidth = array.getDimensionPixelSize(R$styleable.ClipViewLayout_clipBorderWidth, (int) TypedValue.applyDimension(1, 1.0f, getResources().getDisplayMetrics()));
            int clipType = array.getInt(R$styleable.ClipViewLayout_clipType, 1);
            array.recycle();
            ClipView clipView = new ClipView(context);
            this.f = clipView;
            clipView.setClipType(clipType == 1 ? ClipView.a.CIRCLE : ClipView.a.RECTANGLE);
            this.f.setClipBorderWidth(clipBorderWidth);
            this.f.setmHorizontalPadding(this.q);
            this.d = new ImageView(context);
            ViewGroup.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
            addView(this.d, lp);
            addView(this.f, lp);
        }
    }

    public void setClipType(int type) {
        Object[] objArr = {new Integer(type)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7497, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (type == 1) {
                this.f.setClipType(ClipView.a.CIRCLE);
            } else {
                this.f.setClipType(ClipView.a.RECTANGLE);
            }
            this.f.postInvalidate();
        }
    }

    public void setCutRatio(float ratio) {
        Object[] objArr = {new Float(ratio)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7498, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.f.setRatio(ratio);
            this.f.postInvalidate();
        }
    }

    public class a implements ViewTreeObserver.OnGlobalLayoutListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Uri c;

        a(Uri uri) {
            this.c = uri;
        }

        public void onGlobalLayout() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7517, new Class[0], Void.TYPE).isSupported) {
                ClipViewLayout.this.i(this.c);
                ClipViewLayout.this.d.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        }
    }

    public void setImageSrc(Uri uri) {
        if (!PatchProxy.proxy(new Object[]{uri}, this, changeQuickRedirect, false, 7501, new Class[]{Uri.class}, Void.TYPE).isSupported) {
            this.d.getViewTreeObserver().addOnGlobalLayoutListener(new a(uri));
        }
    }

    public class b implements ViewTreeObserver.OnGlobalLayoutListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        b(String str) {
            this.c = str;
        }

        public void onGlobalLayout() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7518, new Class[0], Void.TYPE).isSupported) {
                ClipViewLayout.this.j(this.c);
                ClipViewLayout.this.d.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        }
    }

    public void setImageSrc(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 7502, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.d.getViewTreeObserver().addOnGlobalLayoutListener(new b(path));
        }
    }

    public void i(Uri uri) {
        float scale;
        if (!PatchProxy.proxy(new Object[]{uri}, this, changeQuickRedirect, false, 7503, new Class[]{Uri.class}, Void.TYPE).isSupported) {
            if (uri != null) {
                a.b g = timber.log.a.g(this.c);
                g.h("**********clip_view uri*******  " + uri, new Object[0]);
                String path = h.a(getContext(), uri);
                a.b g2 = timber.log.a.g(this.c);
                g2.h("**********clip_view path*******  " + path, new Object[0]);
                if (!TextUtils.isEmpty(path)) {
                    Bitmap e = e(path, 720, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_REQ);
                    this.C4 = e;
                    if (e != null) {
                        int rotation = f(path);
                        Matrix m = new Matrix();
                        m.setRotate((float) rotation);
                        Bitmap bitmap = this.C4;
                        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.C4.getHeight(), m, true);
                        this.C4 = createBitmap;
                        this.D4 = createBitmap;
                        if (createBitmap.getWidth() >= this.C4.getHeight()) {
                            scale = ((float) this.d.getWidth()) / ((float) this.C4.getWidth());
                            float height = ((float) this.f.getClipRect().height()) / ((float) this.C4.getHeight());
                            this.p4 = height;
                            if (scale < height) {
                                scale = this.p4;
                            }
                        } else {
                            scale = ((float) this.d.getHeight()) / ((float) this.C4.getHeight());
                            float width = ((float) this.f.getClipRect().width()) / ((float) this.C4.getWidth());
                            this.p4 = width;
                            if (scale < width) {
                                scale = this.p4;
                            }
                        }
                        this.y.postScale(scale, scale);
                        this.y.postTranslate((float) ((this.d.getWidth() / 2) - ((int) ((((float) this.C4.getWidth()) * scale) / 2.0f))), (float) ((this.d.getHeight() / 2) - ((int) ((((float) this.C4.getHeight()) * scale) / 2.0f))));
                        this.d.setScaleType(ImageView.ScaleType.MATRIX);
                        this.d.setImageMatrix(this.y);
                        this.d.setImageBitmap(this.C4);
                    }
                }
            }
        }
    }

    public void j(String path) {
        float scale;
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 7504, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(path)) {
                Bitmap e = e(path, 720, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_REQ);
                this.C4 = e;
                if (e != null) {
                    int rotation = f(path);
                    Matrix m = new Matrix();
                    m.setRotate((float) rotation);
                    Bitmap bitmap = this.C4;
                    Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.C4.getHeight(), m, true);
                    this.C4 = createBitmap;
                    this.D4 = createBitmap;
                    if (createBitmap.getWidth() >= this.C4.getHeight()) {
                        scale = ((float) this.d.getWidth()) / ((float) this.C4.getWidth());
                        float height = ((float) this.f.getClipRect().height()) / ((float) this.C4.getHeight());
                        this.p4 = height;
                        if (scale < height) {
                            scale = this.p4;
                        }
                    } else {
                        scale = ((float) this.d.getHeight()) / ((float) this.C4.getHeight());
                        float width = ((float) this.f.getClipRect().width()) / ((float) this.C4.getWidth());
                        this.p4 = width;
                        if (scale < width) {
                            scale = this.p4;
                        }
                    }
                    this.y.postScale(scale, scale);
                    this.y.postTranslate((float) ((this.d.getWidth() / 2) - ((int) ((((float) this.C4.getWidth()) * scale) / 2.0f))), (float) ((this.d.getHeight() / 2) - ((int) ((((float) this.C4.getHeight()) * scale) / 2.0f))));
                    this.d.setScaleType(ImageView.ScaleType.MATRIX);
                    this.d.setImageMatrix(this.y);
                    this.d.setImageBitmap(this.C4);
                }
            }
        }
    }

    public int f(String filepath) {
        int orientation;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filepath}, this, changeQuickRedirect, false, 7506, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (!(exif == null || (orientation = exif.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, -1)) == -1)) {
            switch (orientation) {
                case 3:
                    degree = 180;
                    break;
                case 6:
                    degree = 90;
                    break;
                case 8:
                    degree = SubsamplingScaleImageView.ORIENTATION_270;
                    break;
            }
        }
        this.A4 = degree;
        return degree;
    }

    public boolean onTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 7507, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        switch (event.getAction() & 255) {
            case 0:
                this.p0.set(this.y);
                this.p1.set(event.getX(), event.getY());
                this.a1 = 1;
                break;
            case 2:
                int i = this.a1;
                if (i == 1) {
                    this.y.set(this.p0);
                    float dx = event.getX() - this.p1.x;
                    float dy = event.getY() - this.p1.y;
                    this.x = (float) this.f.getClipRect().top;
                    this.y.postTranslate(dx, dy);
                    c();
                } else if (i == 2) {
                    float newDist = l(event);
                    if (newDist > 10.0f) {
                        float scale = newDist / this.p2;
                        if (scale < 1.0f) {
                            if (getScale() > this.p4) {
                                this.y.set(this.p0);
                                this.x = (float) this.f.getClipRect().top;
                                Matrix matrix = this.y;
                                PointF pointF = this.a2;
                                matrix.postScale(scale, scale, pointF.x, pointF.y);
                                while (getScale() < this.p4) {
                                    Matrix matrix2 = this.y;
                                    PointF pointF2 = this.a2;
                                    matrix2.postScale(1.01f, 1.01f, pointF2.x, pointF2.y);
                                }
                            }
                            c();
                        } else if (getScale() <= this.z4) {
                            this.y.set(this.p0);
                            this.x = (float) this.f.getClipRect().top;
                            Matrix matrix3 = this.y;
                            PointF pointF3 = this.a2;
                            matrix3.postScale(scale, scale, pointF3.x, pointF3.y);
                        }
                    }
                }
                this.d.setImageMatrix(this.y);
                break;
            case 5:
                float l = l(event);
                this.p2 = l;
                if (l > 10.0f) {
                    this.p0.set(this.y);
                    k(this.a2, event);
                    this.a1 = 2;
                    break;
                }
                break;
            case 6:
                this.a1 = 0;
                break;
        }
        return true;
    }

    private RectF g(Matrix matrix) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{matrix}, this, changeQuickRedirect, false, 7508, new Class[]{Matrix.class}, RectF.class);
        if (proxy.isSupported) {
            return (RectF) proxy.result;
        }
        RectF rect = new RectF();
        Drawable d2 = this.d.getDrawable();
        if (d2 != null) {
            rect.set(0.0f, 0.0f, (float) d2.getIntrinsicWidth(), (float) d2.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    private void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7509, new Class[0], Void.TYPE).isSupported) {
            RectF rect = g(this.y);
            float deltaX = 0.0f;
            float deltaY = 0.0f;
            int width = this.d.getWidth();
            int height = this.d.getHeight();
            float width2 = rect.width();
            float f2 = this.q;
            if (width2 >= ((float) width) - (f2 * 2.0f)) {
                float f3 = rect.left;
                if (f3 > f2) {
                    deltaX = (-f3) + f2;
                }
                float f4 = rect.right;
                if (f4 < ((float) width) - f2) {
                    deltaX = (((float) width) - f2) - f4;
                }
            }
            float height2 = rect.height();
            float f5 = this.x;
            if (height2 >= ((float) height) - (2.0f * f5)) {
                float f6 = rect.top;
                if (f6 > f5) {
                    deltaY = (-f6) + f5;
                }
                float f7 = rect.bottom;
                if (f7 < ((float) height) - f5) {
                    deltaY = (((float) height) - f5) - f7;
                }
            }
            this.y.postTranslate(deltaX, deltaY);
        }
    }

    public final float getScale() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7510, new Class[0], Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        this.y.getValues(this.p3);
        return this.p3[0];
    }

    private float l(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 7511, new Class[]{MotionEvent.class}, Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        float x2 = event.getX(0) - event.getX(1);
        float y2 = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((double) ((x2 * x2) + (y2 * y2)));
    }

    private void k(PointF point, MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{point, event}, this, changeQuickRedirect, false, 7512, new Class[]{PointF.class, MotionEvent.class}, Void.TYPE).isSupported) {
            point.set((event.getX(0) + event.getX(1)) / 2.0f, (event.getY(0) + event.getY(1)) / 2.0f);
        }
    }

    public Bitmap d(double scale) {
        Object[] objArr = {new Double(scale)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 7513, new Class[]{Double.TYPE}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        this.d.setDrawingCacheEnabled(true);
        this.d.buildDrawingCache();
        Rect rect = this.f.getClipRect();
        Bitmap cropBitmap = null;
        Bitmap zoomedCropBitmap = null;
        try {
            cropBitmap = Bitmap.createBitmap(this.d.getDrawingCache(), rect.left, rect.top, rect.width(), rect.height());
            zoomedCropBitmap = m(cropBitmap, (int) (((double) cropBitmap.getWidth()) * scale), (int) (((double) cropBitmap.getHeight()) * scale));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cropBitmap != null) {
            cropBitmap.recycle();
        }
        this.d.destroyDrawingCache();
        return zoomedCropBitmap;
    }

    public static Bitmap e(String filePath, int reqWidth, int reqHeight) {
        Object[] objArr = {filePath, new Integer(reqWidth), new Integer(reqHeight)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 7514, new Class[]{String.class, cls, cls}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap decodeFile = BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = b(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int b(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        Object[] objArr = {options, new Integer(reqWidth), new Integer(reqHeight)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7515, new Class[]{BitmapFactory.Options.class, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        int heightRatio = Math.round(((float) height) / ((float) reqHeight));
        int widthRatio = Math.round(((float) width) / ((float) reqWidth));
        int ratio = heightRatio < widthRatio ? heightRatio : widthRatio;
        if (ratio < 3) {
            return ratio;
        }
        if (((double) ratio) < 6.5d) {
            return 4;
        }
        if (ratio < 8) {
            return 8;
        }
        return ratio;
    }

    public static Bitmap m(Bitmap bitmap, int w, int i) {
        Object[] objArr = {bitmap, new Integer(w), new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7516, new Class[]{Bitmap.class, cls, cls}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        Bitmap bitmap2 = bitmap;
        int h = i;
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) w) / ((float) width), ((float) h) / ((float) height));
        return Bitmap.createBitmap(bitmap2, 0, 0, width, height, matrix, false);
    }
}
