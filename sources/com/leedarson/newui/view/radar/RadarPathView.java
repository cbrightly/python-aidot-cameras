package com.leedarson.newui.view.radar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.leedarson.smartcamera.kvswebrtc.beans.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.HashMap;
import java.util.List;

public class RadarPathView extends View implements b {
    public static int c = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d = 0;
    private Paint f;
    private Paint q;
    private HashMap<Integer, List<a>> x;
    private int y;
    private int z;

    public RadarPathView(Context context) {
        super(context);
        d(context);
    }

    public RadarPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        d(context);
    }

    public RadarPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        d(context);
    }

    private void d(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5436, new Class[]{Context.class}, Void.TYPE).isSupported) {
            Paint paint = new Paint();
            this.f = paint;
            paint.setStyle(Paint.Style.STROKE);
            this.f.setAntiAlias(true);
            Paint paint2 = new Paint();
            this.q = paint2;
            paint2.setStyle(Paint.Style.STROKE);
            this.q.setAntiAlias(true);
            this.q.setColor(Color.parseColor("#2EAD9F"));
            this.y = com.leedarson.view.a.a(context, 1.0f);
            this.z = com.leedarson.view.a.a(context, 5.0f);
        }
    }

    public void setDatasMap(HashMap<Integer, List<a>> datasMap) {
        this.x = datasMap;
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 5437, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            canvas.translate((float) (c / 2), (float) d);
            b(canvas);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: com.leedarson.smartcamera.kvswebrtc.beans.a} */
    /*  JADX ERROR: JadxRuntimeException in pass: SimplifyVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Don't wrap MOVE or CONST insns: 0x001d: MOVE  (r1v3 'this' com.leedarson.newui.view.radar.RadarPathView A[THIS]) = 
          (r14v0 'this' com.leedarson.newui.view.radar.RadarPathView A[THIS])
        
        	at jadx.core.dex.instructions.args.InsnArg.wrapArg(InsnArg.java:164)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.assignInline(CodeShrinkVisitor.java:133)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.checkInline(CodeShrinkVisitor.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:65)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.SimplifyVisitor.visit(SimplifyVisitor.java:71)
        */
    /* JADX WARNING: Multi-variable type inference failed */
    public void b(android.graphics.Canvas r15) {
        /*
            r14 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r15
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.graphics.Canvas> r2 = android.graphics.Canvas.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 5438(0x153e, float:7.62E-42)
            r2 = r14
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r14
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r2 = r1.x
            if (r2 == 0) goto L_0x00cc
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x00cc
            android.graphics.Paint r2 = r1.f
            int r3 = r1.y
            float r3 = (float) r3
            r2.setStrokeWidth(r3)
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r2 = r1.x
            java.util.Set r2 = r2.keySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x003a:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x00cc
            java.lang.Object r3 = r2.next()
            java.lang.Integer r3 = (java.lang.Integer) r3
            java.util.HashMap<java.lang.Integer, java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a>> r4 = r1.x
            java.lang.Object r4 = r4.get(r3)
            java.util.List r4 = (java.util.List) r4
            if (r4 == 0) goto L_0x00ca
            int r5 = r4.size()
            if (r5 <= 0) goto L_0x00ca
            android.graphics.Paint r5 = r1.f
            java.lang.Object r6 = r4.get(r8)
            com.leedarson.smartcamera.kvswebrtc.beans.a r6 = (com.leedarson.smartcamera.kvswebrtc.beans.a) r6
            int r6 = r6.q
            r5.setColor(r6)
            android.graphics.Path r5 = new android.graphics.Path
            r5.<init>()
            r6 = 0
        L_0x0069:
            int r7 = r4.size()
            if (r6 >= r7) goto L_0x00c5
            java.lang.Object r7 = r4.get(r6)
            com.leedarson.smartcamera.kvswebrtc.beans.a r7 = (com.leedarson.smartcamera.kvswebrtc.beans.a) r7
            if (r6 != 0) goto L_0x007f
            float r9 = r7.l
            float r10 = r7.m
            r5.moveTo(r9, r10)
            goto L_0x00c2
        L_0x007f:
            float r9 = r7.l
            float r10 = r7.m
            r5.lineTo(r9, r10)
            int r9 = r4.size()
            int r9 = r9 - r0
            if (r6 != r9) goto L_0x00c2
            int r9 = r4.size()
            if (r9 <= r0) goto L_0x00c2
            int r9 = r6 + -1
            java.lang.Object r10 = r4.get(r9)
            com.leedarson.smartcamera.kvswebrtc.beans.a r10 = (com.leedarson.smartcamera.kvswebrtc.beans.a) r10
            java.lang.Object r11 = r4.get(r6)
            com.leedarson.smartcamera.kvswebrtc.beans.a r11 = (com.leedarson.smartcamera.kvswebrtc.beans.a) r11
        L_0x00a1:
            float r12 = r10.f
            float r13 = r11.f
            int r12 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r12 != 0) goto L_0x00bf
            float r12 = r10.g
            float r13 = r11.g
            int r12 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r12 != 0) goto L_0x00bf
            if (r9 < 0) goto L_0x00bf
            int r9 = r9 + -1
            if (r9 < 0) goto L_0x00a1
            java.lang.Object r12 = r4.get(r9)
            r10 = r12
            com.leedarson.smartcamera.kvswebrtc.beans.a r10 = (com.leedarson.smartcamera.kvswebrtc.beans.a) r10
            goto L_0x00a1
        L_0x00bf:
            r1.c(r10, r11, r15)
        L_0x00c2:
            int r6 = r6 + 1
            goto L_0x0069
        L_0x00c5:
            android.graphics.Paint r6 = r1.f
            r15.drawPath(r5, r6)
        L_0x00ca:
            goto L_0x003a
        L_0x00cc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.radar.RadarPathView.b(android.graphics.Canvas):void");
    }

    private void c(a aVar, a aVar2, Canvas canvas) {
        Class<a> cls = a.class;
        Class[] clsArr = {cls, cls, Canvas.class};
        if (!PatchProxy.proxy(new Object[]{aVar, aVar2, canvas}, this, changeQuickRedirect, false, 5439, clsArr, Void.TYPE).isSupported) {
            a lastRadarPoint = aVar2;
            a preRadarPoint = aVar;
            Paint paint = new Paint();
            paint.setColor(lastRadarPoint.q);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5.0f);
            Paint paint2 = paint;
            a(canvas, paint2, preRadarPoint.l, preRadarPoint.m, lastRadarPoint.l, lastRadarPoint.m, this.z, this.z);
        }
    }

    private void a(Canvas canvas, Paint paint, float f2, float f3, float f4, float f5, int i, int i2) {
        Object[] objArr = {canvas, paint, new Float(f2), new Float(f3), new Float(f4), new Float(f5), new Integer(i), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        Class cls2 = Integer.TYPE;
        Class[] clsArr = {Canvas.class, Paint.class, cls, cls, cls, cls, cls2, cls2};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5440, clsArr, Void.TYPE).isSupported) {
            Paint paintLine = paint;
            float toY = f5;
            float fromY = f3;
            int i3 = i2;
            Canvas canvas2 = canvas;
            float fromX = f2;
            int height = i;
            float toX = f4;
            try {
                PointF point1 = new PointF(fromX, fromY);
                PointF point2 = new PointF(toX, toY);
                double angle = Math.atan2((double) (point2.y - point1.y), (double) (point2.x - point1.x));
                Path path = new Path();
                PointF pointF = point1;
                path.moveTo(point2.x, point2.y);
                float f6 = toY;
                float f7 = fromY;
                try {
                    float f8 = fromX;
                    int i4 = height;
                    try {
                        path.lineTo((float) (((double) point2.x) + (((double) 15.0f) * Math.cos(angle + 1.5707963267948966d))), (float) (((double) point2.y) + (((double) 15.0f) * Math.sin(angle + 1.5707963267948966d))));
                        path.lineTo((float) (((double) point2.x) + (((double) 15.0f) * Math.cos(angle))), (float) (((double) point2.y) + (((double) 15.0f) * Math.sin(angle))));
                        path.lineTo((float) (((double) point2.x) + (((double) 15.0f) * Math.cos(angle - 1.5707963267948966d))), (float) (((double) point2.y) + (((double) 15.0f) * Math.sin(angle - 1.5707963267948966d))));
                        path.close();
                        canvas2.drawPath(path, paintLine);
                    } catch (Exception e) {
                        ex = e;
                    }
                } catch (Exception e2) {
                    ex = e2;
                    float f9 = fromX;
                    int i5 = height;
                    ex.printStackTrace();
                    g.c("_drawTrangle exception:" + ex.getMessage());
                }
            } catch (Exception e3) {
                ex = e3;
                float f10 = toY;
                float f11 = fromY;
                float f12 = fromX;
                int i6 = height;
                ex.printStackTrace();
                g.c("_drawTrangle exception:" + ex.getMessage());
            }
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5441, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setData(a data) {
    }

    public a getData() {
        return null;
    }

    public static void e(int width, int height) {
        c = width;
        d = height;
    }
}
