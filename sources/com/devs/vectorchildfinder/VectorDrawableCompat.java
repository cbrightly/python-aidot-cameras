package com.devs.vectorchildfinder;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.collection.ArrayMap;
import androidx.constraintlayout.motion.widget.Key;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.devs.vectorchildfinder.b;
import java.util.ArrayList;
import java.util.Stack;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class VectorDrawableCompat extends VectorDrawableCommon {
    static final PorterDuff.Mode d = PorterDuff.Mode.SRC_IN;
    private final Matrix a1;
    private f f;
    private final float[] p0;
    private final Rect p1;
    private PorterDuffColorFilter q;
    private ColorFilter x;
    private boolean y;
    private boolean z;

    public /* bridge */ /* synthetic */ void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
    }

    public /* bridge */ /* synthetic */ void clearColorFilter() {
        super.clearColorFilter();
    }

    public /* bridge */ /* synthetic */ ColorFilter getColorFilter() {
        return super.getColorFilter();
    }

    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    public /* bridge */ /* synthetic */ boolean getPadding(Rect rect) {
        return super.getPadding(rect);
    }

    public /* bridge */ /* synthetic */ int[] getState() {
        return super.getState();
    }

    public /* bridge */ /* synthetic */ Region getTransparentRegion() {
        return super.getTransparentRegion();
    }

    public /* bridge */ /* synthetic */ void jumpToCurrentState() {
        super.jumpToCurrentState();
    }

    public /* bridge */ /* synthetic */ void setChangingConfigurations(int i) {
        super.setChangingConfigurations(i);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(int i, PorterDuff.Mode mode) {
        super.setColorFilter(i, mode);
    }

    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z2) {
        super.setFilterBitmap(z2);
    }

    public /* bridge */ /* synthetic */ void setHotspot(float f2, float f3) {
        super.setHotspot(f2, f3);
    }

    public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
        super.setHotspotBounds(i, i2, i3, i4);
    }

    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        return super.setState(iArr);
    }

    VectorDrawableCompat() {
        this.z = true;
        this.p0 = new float[9];
        this.a1 = new Matrix();
        this.p1 = new Rect();
        this.f = new f();
    }

    VectorDrawableCompat(@NonNull f state) {
        this.z = true;
        this.p0 = new float[9];
        this.a1 = new Matrix();
        this.p1 = new Rect();
        this.f = state;
        this.q = k(this.q, state.c, state.d);
    }

    public Drawable mutate() {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.mutate();
            return this;
        }
        if (!this.y && super.mutate() == this) {
            this.f = new f(this.f);
            this.y = true;
        }
        return this;
    }

    public Object e(String name) {
        return this.f.b.p.get(name);
    }

    public Drawable.ConstantState getConstantState() {
        if (this.c != null) {
            return new g(this.c.getConstantState());
        }
        this.f.a = getChangingConfigurations();
        return this.f;
    }

    public void draw(Canvas canvas) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        copyBounds(this.p1);
        if (this.p1.width() > 0 && this.p1.height() > 0) {
            ColorFilter colorFilter = this.x;
            if (colorFilter == null) {
                colorFilter = this.q;
            }
            canvas.getMatrix(this.a1);
            this.a1.getValues(this.p0);
            float canvasScaleX = Math.abs(this.p0[0]);
            float canvasScaleY = Math.abs(this.p0[4]);
            float canvasSkewX = Math.abs(this.p0[1]);
            float canvasSkewY = Math.abs(this.p0[3]);
            if (!(canvasSkewX == 0.0f && canvasSkewY == 0.0f)) {
                canvasScaleX = 1.0f;
                canvasScaleY = 1.0f;
            }
            int scaledWidth = Math.min(2048, (int) (((float) this.p1.width()) * canvasScaleX));
            int scaledHeight = Math.min(2048, (int) (((float) this.p1.height()) * canvasScaleY));
            if (scaledWidth > 0 && scaledHeight > 0) {
                int saveCount = canvas.save();
                Rect rect = this.p1;
                canvas.translate((float) rect.left, (float) rect.top);
                if (g()) {
                    canvas.translate((float) this.p1.width(), 0.0f);
                    canvas.scale(-1.0f, 1.0f);
                }
                this.p1.offsetTo(0, 0);
                this.f.c(scaledWidth, scaledHeight);
                if (!this.z) {
                    this.f.h(scaledWidth, scaledHeight);
                } else if (!this.f.b()) {
                    this.f.h(scaledWidth, scaledHeight);
                    this.f.g();
                }
                this.f.d(canvas, colorFilter, this.p1);
                canvas.restoreToCount(saveCount);
            }
        }
    }

    public int getAlpha() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return DrawableCompat.getAlpha(drawable);
        }
        return this.f.b.k();
    }

    public void setAlpha(int alpha) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setAlpha(alpha);
        } else if (this.f.b.k() != alpha) {
            this.f.b.m(alpha);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
            return;
        }
        this.x = colorFilter;
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    public PorterDuffColorFilter k(PorterDuffColorFilter tintFilter, ColorStateList tint, PorterDuff.Mode tintMode) {
        if (tint == null || tintMode == null) {
            return null;
        }
        return new PorterDuffColorFilter(tint.getColorForState(getState(), 0), tintMode);
    }

    @SuppressLint({"NewApi"})
    public void setTint(int tint) {
        Drawable drawable = this.c;
        if (drawable != null) {
            DrawableCompat.setTint(drawable, tint);
        } else {
            setTintList(ColorStateList.valueOf(tint));
        }
    }

    public void setTintList(ColorStateList tint) {
        Drawable drawable = this.c;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, tint);
            return;
        }
        f state = this.f;
        if (state.c != tint) {
            state.c = tint;
            this.q = k(this.q, tint, state.d);
            invalidateSelf();
        }
    }

    public void setTintMode(PorterDuff.Mode tintMode) {
        Drawable drawable = this.c;
        if (drawable != null) {
            DrawableCompat.setTintMode(drawable, tintMode);
            return;
        }
        f state = this.f;
        if (state.d != tintMode) {
            state.d = tintMode;
            this.q = k(this.q, state.c, tintMode);
            invalidateSelf();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0013, code lost:
        r0 = (r0 = r1.f).c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isStateful() {
        /*
            r1 = this;
            android.graphics.drawable.Drawable r0 = r1.c
            if (r0 == 0) goto L_0x0009
            boolean r0 = r0.isStateful()
            return r0
        L_0x0009:
            boolean r0 = super.isStateful()
            if (r0 != 0) goto L_0x0020
            com.devs.vectorchildfinder.VectorDrawableCompat$f r0 = r1.f
            if (r0 == 0) goto L_0x001e
            android.content.res.ColorStateList r0 = r0.c
            if (r0 == 0) goto L_0x001e
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x001e
            goto L_0x0020
        L_0x001e:
            r0 = 0
            goto L_0x0021
        L_0x0020:
            r0 = 1
        L_0x0021:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.devs.vectorchildfinder.VectorDrawableCompat.isStateful():boolean");
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] stateSet) {
        PorterDuff.Mode mode;
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.setState(stateSet);
        }
        f state = this.f;
        ColorStateList colorStateList = state.c;
        if (colorStateList == null || (mode = state.d) == null) {
            return false;
        }
        this.q = k(this.q, colorStateList, mode);
        invalidateSelf();
        return true;
    }

    public int getOpacity() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -3;
    }

    public int getIntrinsicWidth() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return (int) this.f.b.j;
    }

    public int getIntrinsicHeight() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return (int) this.f.b.k;
    }

    public boolean canApplyTheme() {
        Drawable drawable = this.c;
        if (drawable == null) {
            return false;
        }
        DrawableCompat.canApplyTheme(drawable);
        return false;
    }

    public boolean isAutoMirrored() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return DrawableCompat.isAutoMirrored(drawable);
        }
        return this.f.e;
    }

    public void setAutoMirrored(boolean mirrored) {
        Drawable drawable = this.c;
        if (drawable != null) {
            DrawableCompat.setAutoMirrored(drawable, mirrored);
        } else {
            this.f.e = mirrored;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x001f A[Catch:{ XmlPullParserException -> 0x002c, IOException -> 0x0027 }] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001a A[Catch:{ XmlPullParserException -> 0x002c, IOException -> 0x0027 }] */
    @android.annotation.SuppressLint({"NewApi"})
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.devs.vectorchildfinder.VectorDrawableCompat c(@androidx.annotation.NonNull android.content.res.Resources r7, @androidx.annotation.DrawableRes int r8, @androidx.annotation.Nullable android.content.res.Resources.Theme r9) {
        /*
            java.lang.String r0 = "parser error"
            java.lang.String r1 = "VectorDrawableCompat"
            android.content.res.XmlResourceParser r2 = r7.getXml(r8)     // Catch:{ XmlPullParserException -> 0x002c, IOException -> 0x0027 }
            android.util.AttributeSet r3 = android.util.Xml.asAttributeSet(r2)     // Catch:{ XmlPullParserException -> 0x002c, IOException -> 0x0027 }
        L_0x000c:
            int r4 = r2.next()     // Catch:{ XmlPullParserException -> 0x002c, IOException -> 0x0027 }
            r5 = r4
            r6 = 2
            if (r4 == r6) goto L_0x0018
            r4 = 1
            if (r5 == r4) goto L_0x0018
            goto L_0x000c
        L_0x0018:
            if (r5 != r6) goto L_0x001f
            com.devs.vectorchildfinder.VectorDrawableCompat r0 = d(r7, r2, r3, r9)     // Catch:{ XmlPullParserException -> 0x002c, IOException -> 0x0027 }
            return r0
        L_0x001f:
            org.xmlpull.v1.XmlPullParserException r4 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x002c, IOException -> 0x0027 }
            java.lang.String r6 = "No start tag found"
            r4.<init>(r6)     // Catch:{ XmlPullParserException -> 0x002c, IOException -> 0x0027 }
            throw r4     // Catch:{ XmlPullParserException -> 0x002c, IOException -> 0x0027 }
        L_0x0027:
            r2 = move-exception
            android.util.Log.e(r1, r0, r2)
            goto L_0x0031
        L_0x002c:
            r2 = move-exception
            android.util.Log.e(r1, r0, r2)
        L_0x0031:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.devs.vectorchildfinder.VectorDrawableCompat.c(android.content.res.Resources, int, android.content.res.Resources$Theme):com.devs.vectorchildfinder.VectorDrawableCompat");
    }

    @SuppressLint({"NewApi"})
    public static VectorDrawableCompat d(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) {
        VectorDrawableCompat drawable = new VectorDrawableCompat();
        drawable.inflate(r, parser, attrs, theme);
        return drawable;
    }

    static int b(int color, float alpha) {
        return (color & ViewCompat.MEASURED_SIZE_MASK) | (((int) (((float) Color.alpha(color)) * alpha)) << 24);
    }

    @SuppressLint({"NewApi"})
    public void inflate(Resources res, XmlPullParser parser, AttributeSet attrs) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.inflate(res, parser, attrs);
        } else {
            inflate(res, parser, attrs, (Resources.Theme) null);
        }
    }

    public void inflate(Resources res, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) {
        Drawable drawable = this.c;
        if (drawable != null) {
            DrawableCompat.inflate(drawable, res, parser, attrs, theme);
            return;
        }
        f state = this.f;
        state.b = new e();
        TypedArray a2 = VectorDrawableCommon.a(res, theme, attrs, a.a);
        j(a2, parser);
        a2.recycle();
        state.a = getChangingConfigurations();
        state.k = true;
        f(res, parser, attrs, theme);
        this.q = k(this.q, state.c, state.d);
    }

    private static PorterDuff.Mode h(int value, PorterDuff.Mode defaultMode) {
        switch (value) {
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                if (Build.VERSION.SDK_INT >= 11) {
                    return PorterDuff.Mode.ADD;
                }
                return defaultMode;
            default:
                return defaultMode;
        }
    }

    private void j(TypedArray a2, XmlPullParser parser) {
        f state = this.f;
        e pathRenderer = state.b;
        state.d = h(c.d(a2, parser, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN);
        ColorStateList tint = a2.getColorStateList(1);
        if (tint != null) {
            state.c = tint;
        }
        state.e = c.a(a2, parser, "autoMirrored", 5, state.e);
        pathRenderer.l = c.c(a2, parser, "viewportWidth", 7, pathRenderer.l);
        float c2 = c.c(a2, parser, "viewportHeight", 8, pathRenderer.m);
        pathRenderer.m = c2;
        if (pathRenderer.l <= 0.0f) {
            throw new XmlPullParserException(a2.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        } else if (c2 > 0.0f) {
            pathRenderer.j = a2.getDimension(3, pathRenderer.j);
            float dimension = a2.getDimension(2, pathRenderer.k);
            pathRenderer.k = dimension;
            if (pathRenderer.j <= 0.0f) {
                throw new XmlPullParserException(a2.getPositionDescription() + "<vector> tag requires width > 0");
            } else if (dimension > 0.0f) {
                pathRenderer.l(c.c(a2, parser, "alpha", 4, pathRenderer.i()));
                String name = a2.getString(0);
                if (name != null) {
                    pathRenderer.o = name;
                    pathRenderer.p.put(name, pathRenderer);
                }
            } else {
                throw new XmlPullParserException(a2.getPositionDescription() + "<vector> tag requires height > 0");
            }
        } else {
            throw new XmlPullParserException(a2.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
    }

    private void f(Resources res, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) {
        Resources resources = res;
        XmlPullParser xmlPullParser = parser;
        AttributeSet attributeSet = attrs;
        Resources.Theme theme2 = theme;
        f state = this.f;
        e pathRenderer = state.b;
        boolean noPathTag = true;
        Stack<VGroup> groupStack = new Stack<>();
        groupStack.push(pathRenderer.i);
        int eventType = parser.getEventType();
        int innerDepth = parser.getDepth() + 1;
        for (int i = 1; eventType != i && (parser.getDepth() >= innerDepth || eventType != 3); i = 1) {
            if (eventType == 2) {
                String tagName = parser.getName();
                c currentGroup = (c) groupStack.peek();
                if ("path".equals(tagName)) {
                    b path = new b();
                    path.f(resources, attributeSet, theme2, xmlPullParser);
                    currentGroup.b.add(path);
                    if (path.a() != null) {
                        pathRenderer.p.put(path.a(), path);
                    }
                    noPathTag = false;
                    state.a |= path.c;
                } else if ("clip-path".equals(tagName)) {
                    a path2 = new a();
                    path2.d(resources, attributeSet, theme2, xmlPullParser);
                    currentGroup.b.add(path2);
                    if (path2.a() != null) {
                        pathRenderer.p.put(path2.a(), path2);
                    }
                    state.a |= path2.c;
                } else if ("group".equals(tagName)) {
                    c newChildGroup = new c();
                    newChildGroup.d(resources, attributeSet, theme2, xmlPullParser);
                    currentGroup.b.add(newChildGroup);
                    groupStack.push(newChildGroup);
                    if (newChildGroup.c() != null) {
                        pathRenderer.p.put(newChildGroup.c(), newChildGroup);
                    }
                    state.a |= newChildGroup.k;
                }
            } else if (eventType == 3 && "group".equals(parser.getName())) {
                groupStack.pop();
            }
            eventType = parser.next();
        }
        if (noPathTag) {
            StringBuffer tag = new StringBuffer();
            if (tag.length() > 0) {
                tag.append(" or ");
            }
            tag.append("path");
            throw new XmlPullParserException("no " + tag + " defined");
        }
    }

    public void i(boolean allowCaching) {
        this.z = allowCaching;
    }

    @SuppressLint({"NewApi", "WrongConstant"})
    private boolean g() {
        if (Build.VERSION.SDK_INT >= 17 && isAutoMirrored() && getLayoutDirection() == 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setBounds(bounds);
        }
    }

    public int getChangingConfigurations() {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.getChangingConfigurations();
        }
        return super.getChangingConfigurations() | this.f.getChangingConfigurations();
    }

    public void invalidateSelf() {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    public void scheduleSelf(Runnable what, long when) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.scheduleSelf(what, when);
        } else {
            super.scheduleSelf(what, when);
        }
    }

    public boolean setVisible(boolean visible, boolean restart) {
        Drawable drawable = this.c;
        if (drawable != null) {
            return drawable.setVisible(visible, restart);
        }
        return super.setVisible(visible, restart);
    }

    public void unscheduleSelf(Runnable what) {
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.unscheduleSelf(what);
        } else {
            super.unscheduleSelf(what);
        }
    }

    public static class g extends Drawable.ConstantState {
        private final Drawable.ConstantState a;

        public g(Drawable.ConstantState state) {
            this.a = state;
        }

        @RequiresApi(api = 21)
        public Drawable newDrawable() {
            VectorDrawableCompat drawableCompat = new VectorDrawableCompat();
            drawableCompat.c = (VectorDrawable) this.a.newDrawable();
            return drawableCompat;
        }

        @RequiresApi(api = 21)
        public Drawable newDrawable(Resources res) {
            VectorDrawableCompat drawableCompat = new VectorDrawableCompat();
            drawableCompat.c = (VectorDrawable) this.a.newDrawable(res);
            return drawableCompat;
        }

        @RequiresApi(api = 21)
        public Drawable newDrawable(Resources res, Resources.Theme theme) {
            VectorDrawableCompat drawableCompat = new VectorDrawableCompat();
            drawableCompat.c = (VectorDrawable) this.a.newDrawable(res, theme);
            return drawableCompat;
        }

        @RequiresApi(api = 21)
        public boolean canApplyTheme() {
            return this.a.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.a.getChangingConfigurations();
        }
    }

    public static class f extends Drawable.ConstantState {
        int a;
        e b;
        ColorStateList c;
        PorterDuff.Mode d;
        boolean e;
        Bitmap f;
        ColorStateList g;
        PorterDuff.Mode h;
        int i;
        boolean j;
        boolean k;
        Paint l;

        public f(f copy) {
            this.c = null;
            this.d = VectorDrawableCompat.d;
            if (copy != null) {
                this.a = copy.a;
                this.b = new e(copy.b);
                if (copy.b.f != null) {
                    Paint unused = this.b.f = new Paint(copy.b.f);
                }
                if (copy.b.e != null) {
                    Paint unused2 = this.b.e = new Paint(copy.b.e);
                }
                this.c = copy.c;
                this.d = copy.d;
                this.e = copy.e;
            }
        }

        public void d(Canvas canvas, ColorFilter filter, Rect originalBounds) {
            canvas.drawBitmap(this.f, (Rect) null, originalBounds, e(filter));
        }

        public boolean f() {
            return this.b.k() < 255;
        }

        public Paint e(ColorFilter filter) {
            if (!f() && filter == null) {
                return null;
            }
            if (this.l == null) {
                Paint paint = new Paint();
                this.l = paint;
                paint.setFilterBitmap(true);
            }
            this.l.setAlpha(this.b.k());
            this.l.setColorFilter(filter);
            return this.l;
        }

        public void h(int width, int height) {
            this.f.eraseColor(0);
            this.b.f(new Canvas(this.f), width, height, (ColorFilter) null);
        }

        public void c(int width, int height) {
            if (this.f == null || !a(width, height)) {
                this.f = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                this.k = true;
            }
        }

        public boolean a(int width, int height) {
            if (width == this.f.getWidth() && height == this.f.getHeight()) {
                return true;
            }
            return false;
        }

        public boolean b() {
            if (!this.k && this.g == this.c && this.h == this.d && this.j == this.e && this.i == this.b.k()) {
                return true;
            }
            return false;
        }

        public void g() {
            this.g = this.c;
            this.h = this.d;
            this.i = this.b.k();
            this.j = this.e;
            this.k = false;
        }

        public f() {
            this.c = null;
            this.d = VectorDrawableCompat.d;
            this.b = new e();
        }

        public Drawable newDrawable() {
            return new VectorDrawableCompat(this);
        }

        public Drawable newDrawable(Resources res) {
            return new VectorDrawableCompat(this);
        }

        public int getChangingConfigurations() {
            return this.a;
        }
    }

    public static class e {
        private static final Matrix a = new Matrix();
        private final Path b;
        private final Path c;
        private final Matrix d;
        /* access modifiers changed from: private */
        public Paint e;
        /* access modifiers changed from: private */
        public Paint f;
        private PathMeasure g;
        private int h;
        final c i;
        float j;
        float k;
        float l;
        float m;
        int n;
        String o;
        final ArrayMap<String, Object> p;

        public e() {
            this.d = new Matrix();
            this.j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            this.n = 255;
            this.o = null;
            this.p = new ArrayMap<>();
            this.i = new c();
            this.b = new Path();
            this.c = new Path();
        }

        public void m(int alpha) {
            this.n = alpha;
        }

        public int k() {
            return this.n;
        }

        public void l(float alpha) {
            m((int) (255.0f * alpha));
        }

        public float i() {
            return ((float) k()) / 255.0f;
        }

        public e(e copy) {
            this.d = new Matrix();
            this.j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            this.n = 255;
            this.o = null;
            ArrayMap<String, Object> arrayMap = new ArrayMap<>();
            this.p = arrayMap;
            this.i = new c(copy.i, arrayMap);
            this.b = new Path(copy.b);
            this.c = new Path(copy.c);
            this.j = copy.j;
            this.k = copy.k;
            this.l = copy.l;
            this.m = copy.m;
            this.h = copy.h;
            this.n = copy.n;
            this.o = copy.o;
            String str = copy.o;
            if (str != null) {
                arrayMap.put(str, this);
            }
        }

        private void g(c currentGroup, Matrix currentMatrix, Canvas canvas, int w, int h2, ColorFilter filter) {
            c cVar = currentGroup;
            currentGroup.a.set(currentMatrix);
            currentGroup.a.preConcat(currentGroup.j);
            canvas.save();
            for (int i2 = 0; i2 < cVar.b.size(); i2++) {
                Object child = cVar.b.get(i2);
                if (child instanceof c) {
                    g((c) child, currentGroup.a, canvas, w, h2, filter);
                } else if (child instanceof d) {
                    h(currentGroup, (d) child, canvas, w, h2, filter);
                }
            }
            canvas.restore();
        }

        public void f(Canvas canvas, int w, int h2, ColorFilter filter) {
            g(this.i, a, canvas, w, h2, filter);
        }

        private void h(c vGroup, d vPath, Canvas canvas, int w, int h2, ColorFilter filter) {
            float len;
            d dVar = vPath;
            Canvas canvas2 = canvas;
            ColorFilter colorFilter = filter;
            float scaleX = ((float) w) / this.l;
            float scaleY = ((float) h2) / this.m;
            float minScale = Math.min(scaleX, scaleY);
            Matrix groupStackedMatrix = vGroup.a;
            this.d.set(groupStackedMatrix);
            this.d.postScale(scaleX, scaleY);
            float matrixScale = j(groupStackedMatrix);
            if (matrixScale != 0.0f) {
                dVar.c(this.b);
                Path path = this.b;
                this.c.reset();
                if (vPath.b()) {
                    this.c.addPath(path, this.d);
                    canvas2.clipPath(this.c);
                    return;
                }
                b fullPath = (b) dVar;
                float f2 = fullPath.k;
                if (!(f2 == 0.0f && fullPath.l == 1.0f)) {
                    float f3 = fullPath.m;
                    float start = (f2 + f3) % 1.0f;
                    float end = (fullPath.l + f3) % 1.0f;
                    if (this.g == null) {
                        this.g = new PathMeasure();
                    }
                    this.g.setPath(this.b, false);
                    float len2 = this.g.getLength();
                    float start2 = start * len2;
                    float end2 = end * len2;
                    path.reset();
                    if (start2 > end2) {
                        this.g.getSegment(start2, len2, path, true);
                        float f4 = len2;
                        len = 0.0f;
                        this.g.getSegment(0.0f, end2, path, true);
                    } else {
                        len = 0.0f;
                        this.g.getSegment(start2, end2, path, true);
                    }
                    path.rLineTo(len, len);
                }
                this.c.addPath(path, this.d);
                if (fullPath.g != 0) {
                    if (this.f == null) {
                        Paint paint = new Paint();
                        this.f = paint;
                        paint.setStyle(Paint.Style.FILL);
                        this.f.setAntiAlias(true);
                    }
                    Paint fillPaint = this.f;
                    fillPaint.setColor(VectorDrawableCompat.b(fullPath.g, fullPath.j));
                    fillPaint.setColorFilter(colorFilter);
                    canvas2.drawPath(this.c, fillPaint);
                }
                if (fullPath.e != 0) {
                    if (this.e == null) {
                        Paint paint2 = new Paint();
                        this.e = paint2;
                        paint2.setStyle(Paint.Style.STROKE);
                        this.e.setAntiAlias(true);
                    }
                    Paint strokePaint = this.e;
                    Paint.Join join = fullPath.o;
                    if (join != null) {
                        strokePaint.setStrokeJoin(join);
                    }
                    Paint.Cap cap = fullPath.n;
                    if (cap != null) {
                        strokePaint.setStrokeCap(cap);
                    }
                    strokePaint.setStrokeMiter(fullPath.p);
                    strokePaint.setColor(VectorDrawableCompat.b(fullPath.e, fullPath.h));
                    strokePaint.setColorFilter(colorFilter);
                    strokePaint.setStrokeWidth(fullPath.f * minScale * matrixScale);
                    canvas2.drawPath(this.c, strokePaint);
                }
            }
        }

        private static float e(float v1x, float v1y, float v2x, float v2y) {
            return (v1x * v2y) - (v1y * v2x);
        }

        private float j(Matrix groupStackedMatrix) {
            float[] unitVectors = {0.0f, 1.0f, 1.0f, 0.0f};
            groupStackedMatrix.mapVectors(unitVectors);
            float crossProduct = e(unitVectors[0], unitVectors[1], unitVectors[2], unitVectors[3]);
            float maxScale = Math.max((float) Math.hypot((double) unitVectors[0], (double) unitVectors[1]), (float) Math.hypot((double) unitVectors[2], (double) unitVectors[3]));
            if (maxScale > 0.0f) {
                return Math.abs(crossProduct) / maxScale;
            }
            return 0.0f;
        }
    }

    public static class c {
        /* access modifiers changed from: private */
        public final Matrix a;
        final ArrayList<Object> b;
        float c;
        private float d;
        private float e;
        private float f;
        private float g;
        private float h;
        private float i;
        /* access modifiers changed from: private */
        public final Matrix j;
        int k;
        private int[] l;
        private String m;

        public c(c copy, ArrayMap<String, Object> targetsMap) {
            d newPath;
            this.a = new Matrix();
            this.b = new ArrayList<>();
            this.c = 0.0f;
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 1.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = 0.0f;
            Matrix matrix = new Matrix();
            this.j = matrix;
            this.m = null;
            this.c = copy.c;
            this.d = copy.d;
            this.e = copy.e;
            this.f = copy.f;
            this.g = copy.g;
            this.h = copy.h;
            this.i = copy.i;
            this.l = copy.l;
            String str = copy.m;
            this.m = str;
            this.k = copy.k;
            if (str != null) {
                targetsMap.put(str, this);
            }
            matrix.set(copy.j);
            ArrayList<Object> children = copy.b;
            for (int i2 = 0; i2 < children.size(); i2++) {
                Object copyChild = children.get(i2);
                if (copyChild instanceof c) {
                    this.b.add(new c((c) copyChild, targetsMap));
                } else {
                    if (copyChild instanceof b) {
                        newPath = new b((b) copyChild);
                    } else if (copyChild instanceof a) {
                        newPath = new a((a) copyChild);
                    } else {
                        throw new IllegalStateException("Unknown object in the tree!");
                    }
                    this.b.add(newPath);
                    String str2 = newPath.b;
                    if (str2 != null) {
                        targetsMap.put(str2, newPath);
                    }
                }
            }
        }

        public c() {
            this.a = new Matrix();
            this.b = new ArrayList<>();
            this.c = 0.0f;
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 1.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = 0.0f;
            this.j = new Matrix();
            this.m = null;
        }

        public String c() {
            return this.m;
        }

        public void d(Resources res, AttributeSet attrs, Resources.Theme theme, XmlPullParser parser) {
            TypedArray a2 = VectorDrawableCommon.a(res, theme, attrs, a.b);
            f(a2, parser);
            a2.recycle();
        }

        private void f(TypedArray a2, XmlPullParser parser) {
            this.l = null;
            this.c = c.c(a2, parser, Key.ROTATION, 5, this.c);
            this.d = a2.getFloat(1, this.d);
            this.e = a2.getFloat(2, this.e);
            this.f = c.c(a2, parser, "scaleX", 3, this.f);
            this.g = c.c(a2, parser, "scaleY", 4, this.g);
            this.h = c.c(a2, parser, "translateX", 6, this.h);
            this.i = c.c(a2, parser, "translateY", 7, this.i);
            String groupName = a2.getString(0);
            if (groupName != null) {
                this.m = groupName;
            }
            e();
        }

        private void e() {
            this.j.reset();
            this.j.postTranslate(-this.d, -this.e);
            this.j.postScale(this.f, this.g);
            this.j.postRotate(this.c, 0.0f, 0.0f);
            this.j.postTranslate(this.h + this.d, this.i + this.e);
        }
    }

    public static class d {
        protected b.C0064b[] a = null;
        String b;
        int c;

        public d() {
        }

        public d(d copy) {
            this.b = copy.b;
            this.c = copy.c;
            this.a = b.d(copy.a);
        }

        public void c(Path path) {
            path.reset();
            b.C0064b[] bVarArr = this.a;
            if (bVarArr != null) {
                b.C0064b.d(bVarArr, path);
            }
        }

        public String a() {
            return this.b;
        }

        public boolean b() {
            return false;
        }
    }

    public static class a extends d {
        public a() {
        }

        public a(a copy) {
            super(copy);
        }

        public void d(Resources r, AttributeSet attrs, Resources.Theme theme, XmlPullParser parser) {
            if (c.e(parser, "pathData")) {
                TypedArray a = VectorDrawableCommon.a(r, theme, attrs, a.d);
                e(a);
                a.recycle();
            }
        }

        private void e(TypedArray a) {
            String pathName = a.getString(0);
            if (pathName != null) {
                this.b = pathName;
            }
            String pathData = a.getString(1);
            if (pathData != null) {
                this.a = b.c(pathData);
            }
        }

        public boolean b() {
            return true;
        }
    }

    public static class b extends d {
        private int[] d;
        int e = 0;
        float f = 0.0f;
        int g = 0;
        float h = 1.0f;
        int i;
        float j = 1.0f;
        float k = 0.0f;
        float l = 1.0f;
        float m = 0.0f;
        Paint.Cap n = Paint.Cap.BUTT;
        Paint.Join o = Paint.Join.MITER;
        float p = 4.0f;

        public /* bridge */ /* synthetic */ String a() {
            return super.a();
        }

        public /* bridge */ /* synthetic */ boolean b() {
            return super.b();
        }

        public /* bridge */ /* synthetic */ void c(Path path) {
            super.c(path);
        }

        public b() {
        }

        public b(b copy) {
            super(copy);
            this.d = copy.d;
            this.e = copy.e;
            this.f = copy.f;
            this.h = copy.h;
            this.g = copy.g;
            this.i = copy.i;
            this.j = copy.j;
            this.k = copy.k;
            this.l = copy.l;
            this.m = copy.m;
            this.n = copy.n;
            this.o = copy.o;
            this.p = copy.p;
        }

        private Paint.Cap d(int id, Paint.Cap defValue) {
            switch (id) {
                case 0:
                    return Paint.Cap.BUTT;
                case 1:
                    return Paint.Cap.ROUND;
                case 2:
                    return Paint.Cap.SQUARE;
                default:
                    return defValue;
            }
        }

        private Paint.Join e(int id, Paint.Join defValue) {
            switch (id) {
                case 0:
                    return Paint.Join.MITER;
                case 1:
                    return Paint.Join.ROUND;
                case 2:
                    return Paint.Join.BEVEL;
                default:
                    return defValue;
            }
        }

        public void f(Resources r, AttributeSet attrs, Resources.Theme theme, XmlPullParser parser) {
            TypedArray a = VectorDrawableCommon.a(r, theme, attrs, a.c);
            h(a, parser);
            a.recycle();
        }

        private void h(TypedArray a, XmlPullParser parser) {
            this.d = null;
            if (c.e(parser, "pathData")) {
                String pathName = a.getString(0);
                if (pathName != null) {
                    this.b = pathName;
                }
                String pathData = a.getString(2);
                if (pathData != null) {
                    this.a = b.c(pathData);
                }
                this.g = c.b(a, parser, "fillColor", 1, this.g);
                this.j = c.c(a, parser, "fillAlpha", 12, this.j);
                this.n = d(c.d(a, parser, "strokeLineCap", 8, -1), this.n);
                this.o = e(c.d(a, parser, "strokeLineJoin", 9, -1), this.o);
                this.p = c.c(a, parser, "strokeMiterLimit", 10, this.p);
                this.e = c.b(a, parser, "strokeColor", 3, this.e);
                this.h = c.c(a, parser, "strokeAlpha", 11, this.h);
                this.f = c.c(a, parser, "strokeWidth", 4, this.f);
                this.l = c.c(a, parser, "trimPathEnd", 6, this.l);
                this.m = c.c(a, parser, "trimPathOffset", 7, this.m);
                this.k = c.c(a, parser, "trimPathStart", 5, this.k);
            }
        }

        public void g(int fillColor) {
            this.g = fillColor;
        }
    }
}
