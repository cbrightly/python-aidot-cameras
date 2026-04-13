package smarthome.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class RoundImageView extends AppCompatImageView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c = 0;
    private Context d;
    private int f = -1;
    private int q = 0;
    private int x = 0;
    private int y = 0;
    private int z = 0;

    public RoundImageView(Context context) {
        super(context);
        this.d = context;
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.d = context;
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.d = context;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r1 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDraw(android.graphics.Canvas r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.graphics.Canvas> r2 = android.graphics.Canvas.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 14229(0x3795, float:1.9939E-41)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r9
            android.graphics.drawable.Drawable r2 = r1.getDrawable()
            if (r2 != 0) goto L_0x0025
            return
        L_0x0025:
            int r3 = r1.getWidth()
            if (r3 == 0) goto L_0x00e0
            int r3 = r1.getHeight()
            if (r3 != 0) goto L_0x0033
            goto L_0x00e0
        L_0x0033:
            r1.measure(r8, r8)
            java.lang.Class r3 = r2.getClass()
            java.lang.Class<android.graphics.drawable.NinePatchDrawable> r4 = android.graphics.drawable.NinePatchDrawable.class
            if (r3 != r4) goto L_0x003f
            return
        L_0x003f:
            r3 = r2
            android.graphics.drawable.BitmapDrawable r3 = (android.graphics.drawable.BitmapDrawable) r3
            android.graphics.Bitmap r3 = r3.getBitmap()
            android.graphics.Bitmap$Config r4 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r0 = r3.copy(r4, r0)
            int r4 = r1.y
            if (r4 != 0) goto L_0x0056
            int r4 = r1.getWidth()
            r1.y = r4
        L_0x0056:
            int r4 = r1.z
            if (r4 != 0) goto L_0x0060
            int r4 = r1.getHeight()
            r1.z = r4
        L_0x0060:
            r4 = 0
            int r5 = r1.x
            int r6 = r1.f
            if (r5 == r6) goto L_0x008d
            int r7 = r1.q
            if (r7 == r6) goto L_0x008d
            int r6 = r1.y
            int r7 = r1.z
            if (r6 >= r7) goto L_0x0072
            goto L_0x0073
        L_0x0072:
            r6 = r7
        L_0x0073:
            int r6 = r6 / 2
            int r7 = r1.c
            int r8 = r7 * 2
            int r6 = r6 - r8
            int r7 = r7 / 2
            int r7 = r7 + r6
            r1.a(r10, r7, r5)
            int r4 = r1.c
            int r5 = r6 + r4
            int r4 = r4 / 2
            int r5 = r5 + r4
            int r4 = r1.q
            r1.a(r10, r5, r4)
            goto L_0x00cb
        L_0x008d:
            if (r5 == r6) goto L_0x00a7
            int r7 = r1.q
            if (r7 != r6) goto L_0x00a7
            int r6 = r1.y
            int r7 = r1.z
            if (r6 >= r7) goto L_0x009a
            goto L_0x009b
        L_0x009a:
            r6 = r7
        L_0x009b:
            int r6 = r6 / 2
            int r7 = r1.c
            int r6 = r6 - r7
            int r7 = r7 / 2
            int r7 = r7 + r6
            r1.a(r10, r7, r5)
            goto L_0x00cb
        L_0x00a7:
            if (r5 != r6) goto L_0x00c1
            int r5 = r1.q
            if (r5 == r6) goto L_0x00c1
            int r6 = r1.y
            int r7 = r1.z
            if (r6 >= r7) goto L_0x00b4
            goto L_0x00b5
        L_0x00b4:
            r6 = r7
        L_0x00b5:
            int r6 = r6 / 2
            int r7 = r1.c
            int r6 = r6 - r7
            int r7 = r7 / 2
            int r7 = r7 + r6
            r1.a(r10, r7, r5)
            goto L_0x00cb
        L_0x00c1:
            int r5 = r1.y
            int r6 = r1.z
            if (r5 >= r6) goto L_0x00c8
            goto L_0x00c9
        L_0x00c8:
            r5 = r6
        L_0x00c9:
            int r6 = r5 / 2
        L_0x00cb:
            android.graphics.Bitmap r4 = r1.b(r0, r6)
            int r5 = r1.y
            int r5 = r5 / 2
            int r5 = r5 - r6
            float r5 = (float) r5
            int r7 = r1.z
            int r7 = r7 / 2
            int r7 = r7 - r6
            float r7 = (float) r7
            r8 = 0
            r10.drawBitmap(r4, r5, r7, r8)
            return
        L_0x00e0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.RoundImageView.onDraw(android.graphics.Canvas):void");
    }

    public Bitmap b(Bitmap bitmap, int i) {
        Bitmap squareBitmap;
        Bitmap scaledSrcBmp;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bitmap, new Integer(i)}, this, changeQuickRedirect, false, 14230, new Class[]{Bitmap.class, Integer.TYPE}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        int radius = i;
        Bitmap bmp = bitmap;
        int diameter = radius * 2;
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        if (bmpHeight > bmpWidth) {
            int y2 = (bmpHeight - bmpWidth) / 2;
            squareBitmap = Bitmap.createBitmap(bmp, 0, y2, bmpWidth, bmpWidth);
        } else if (bmpHeight < bmpWidth) {
            int x2 = (bmpWidth - bmpHeight) / 2;
            squareBitmap = Bitmap.createBitmap(bmp, x2, 0, bmpHeight, bmpHeight);
        } else {
            squareBitmap = bmp;
        }
        if (squareBitmap.getWidth() == diameter && squareBitmap.getHeight() == diameter) {
            scaledSrcBmp = squareBitmap;
        } else {
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);
        }
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        int i2 = radius;
        Bitmap bitmap2 = bmp;
        Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle((float) (scaledSrcBmp.getWidth() / 2), (float) (scaledSrcBmp.getHeight() / 2), (float) (scaledSrcBmp.getWidth() / 2), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
        return output;
    }

    private void a(Canvas canvas, int radius, int color) {
        Object[] objArr = {canvas, new Integer(radius), new Integer(color)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14231, new Class[]{Canvas.class, cls, cls}, Void.TYPE).isSupported) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            paint.setColor(color);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth((float) this.c);
            canvas.drawCircle((float) (this.y / 2), (float) (this.z / 2), (float) radius, paint);
        }
    }
}
