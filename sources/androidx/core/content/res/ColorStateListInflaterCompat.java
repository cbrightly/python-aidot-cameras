package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.XmlRes;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class ColorStateListInflaterCompat {
    private static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal<>();

    private ColorStateListInflaterCompat() {
    }

    @Nullable
    public static ColorStateList inflate(@NonNull Resources resources, @XmlRes int resId, @Nullable Resources.Theme theme) {
        try {
            return createFromXml(resources, resources.getXml(resId), theme);
        } catch (Exception e) {
            Log.e("CSLCompat", "Failed to inflate ColorStateList.", e);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0012  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0017  */
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.res.ColorStateList createFromXml(@androidx.annotation.NonNull android.content.res.Resources r4, @androidx.annotation.NonNull org.xmlpull.v1.XmlPullParser r5, @androidx.annotation.Nullable android.content.res.Resources.Theme r6) {
        /*
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r5)
        L_0x0004:
            int r1 = r5.next()
            r2 = r1
            r3 = 2
            if (r1 == r3) goto L_0x0010
            r1 = 1
            if (r2 == r1) goto L_0x0010
            goto L_0x0004
        L_0x0010:
            if (r2 != r3) goto L_0x0017
            android.content.res.ColorStateList r1 = createFromXmlInner(r4, r5, r0, r6)
            return r1
        L_0x0017:
            org.xmlpull.v1.XmlPullParserException r1 = new org.xmlpull.v1.XmlPullParserException
            java.lang.String r3 = "No start tag found"
            r1.<init>(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ColorStateListInflaterCompat.createFromXml(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.content.res.Resources$Theme):android.content.res.ColorStateList");
    }

    @NonNull
    public static ColorStateList createFromXmlInner(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Resources.Theme theme) {
        String name = parser.getName();
        if (name.equals("selector")) {
            return inflate(r, parser, attrs, theme);
        }
        throw new XmlPullParserException(parser.getPositionDescription() + ": invalid color state list tag " + name);
    }

    /* JADX WARNING: type inference failed for: r4v6, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.res.ColorStateList inflate(@androidx.annotation.NonNull android.content.res.Resources r20, @androidx.annotation.NonNull org.xmlpull.v1.XmlPullParser r21, @androidx.annotation.NonNull android.util.AttributeSet r22, @androidx.annotation.Nullable android.content.res.Resources.Theme r23) {
        /*
            r1 = r20
            r2 = r22
            r3 = r23
            int r0 = r21.getDepth()
            r4 = 1
            int r5 = r0 + 1
            r0 = 20
            int[][] r0 = new int[r0][]
            int r6 = r0.length
            int[] r6 = new int[r6]
            r7 = 0
            r8 = r7
            r7 = r6
            r6 = r0
        L_0x0018:
            int r0 = r21.next()
            r9 = r0
            if (r0 == r4) goto L_0x00eb
            int r0 = r21.getDepth()
            r11 = r0
            if (r0 >= r5) goto L_0x0029
            r0 = 3
            if (r9 == r0) goto L_0x00eb
        L_0x0029:
            r0 = 2
            if (r9 != r0) goto L_0x00e4
            if (r11 > r5) goto L_0x00e4
            java.lang.String r0 = r21.getName()
            java.lang.String r12 = "item"
            boolean r0 = r0.equals(r12)
            if (r0 != 0) goto L_0x003c
            goto L_0x00e4
        L_0x003c:
            int[] r0 = androidx.core.R.styleable.ColorStateListItem
            android.content.res.TypedArray r12 = obtainAttributes(r1, r3, r2, r0)
            int r0 = androidx.core.R.styleable.ColorStateListItem_android_color
            r13 = -1
            int r14 = r12.getResourceId(r0, r13)
            r15 = -65281(0xffffffffffff00ff, float:NaN)
            if (r14 == r13) goto L_0x0069
            boolean r13 = isColorInt(r1, r14)
            if (r13 != 0) goto L_0x0069
            android.content.res.XmlResourceParser r0 = r1.getXml(r14)     // Catch:{ Exception -> 0x0061 }
            android.content.res.ColorStateList r0 = createFromXml(r1, r0, r3)     // Catch:{ Exception -> 0x0061 }
            int r0 = r0.getDefaultColor()     // Catch:{ Exception -> 0x0061 }
            goto L_0x0068
        L_0x0061:
            r0 = move-exception
            int r13 = androidx.core.R.styleable.ColorStateListItem_android_color
            int r0 = r12.getColor(r13, r15)
        L_0x0068:
            goto L_0x006d
        L_0x0069:
            int r0 = r12.getColor(r0, r15)
        L_0x006d:
            r13 = 1065353216(0x3f800000, float:1.0)
            int r15 = androidx.core.R.styleable.ColorStateListItem_android_alpha
            boolean r16 = r12.hasValue(r15)
            if (r16 == 0) goto L_0x007c
            float r13 = r12.getFloat(r15, r13)
            goto L_0x0088
        L_0x007c:
            int r15 = androidx.core.R.styleable.ColorStateListItem_alpha
            boolean r16 = r12.hasValue(r15)
            if (r16 == 0) goto L_0x0088
            float r13 = r12.getFloat(r15, r13)
        L_0x0088:
            r12.recycle()
            r15 = 0
            int r4 = r22.getAttributeCount()
            int[] r10 = new int[r4]
            r17 = 0
            r1 = r17
        L_0x0096:
            if (r1 >= r4) goto L_0x00c5
            int r3 = r2.getAttributeNameResource(r1)
            r17 = r4
            r4 = 16843173(0x10101a5, float:2.3694738E-38)
            if (r3 == r4) goto L_0x00be
            r4 = 16843551(0x101031f, float:2.3695797E-38)
            if (r3 == r4) goto L_0x00be
            int r4 = androidx.core.R.attr.alpha
            if (r3 == r4) goto L_0x00be
            int r4 = r15 + 1
            r18 = r4
            r4 = 0
            boolean r19 = r2.getAttributeBooleanValue(r1, r4)
            if (r19 == 0) goto L_0x00b9
            r4 = r3
            goto L_0x00ba
        L_0x00b9:
            int r4 = -r3
        L_0x00ba:
            r10[r15] = r4
            r15 = r18
        L_0x00be:
            int r1 = r1 + 1
            r3 = r23
            r4 = r17
            goto L_0x0096
        L_0x00c5:
            r17 = r4
            int[] r1 = android.util.StateSet.trimStateSet(r10, r15)
            int r3 = modulateColorAlpha(r0, r13)
            int[] r7 = androidx.core.content.res.GrowingArrayUtils.append((int[]) r7, (int) r8, (int) r3)
            java.lang.Object[] r4 = androidx.core.content.res.GrowingArrayUtils.append((T[]) r6, (int) r8, r1)
            r6 = r4
            int[][] r6 = (int[][]) r6
            int r8 = r8 + 1
            r4 = 1
            r1 = r20
            r3 = r23
            goto L_0x0018
        L_0x00e4:
            r4 = 1
            r1 = r20
            r3 = r23
            goto L_0x0018
        L_0x00eb:
            int[] r0 = new int[r8]
            int[][] r1 = new int[r8][]
            r3 = 0
            java.lang.System.arraycopy(r7, r3, r0, r3, r8)
            java.lang.System.arraycopy(r6, r3, r1, r3, r8)
            android.content.res.ColorStateList r3 = new android.content.res.ColorStateList
            r3.<init>(r1, r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ColorStateListInflaterCompat.inflate(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):android.content.res.ColorStateList");
    }

    private static boolean isColorInt(@NonNull Resources r, @ColorRes int resId) {
        TypedValue value = getTypedValue();
        r.getValue(resId, value, true);
        int i = value.type;
        if (i < 28 || i > 31) {
            return false;
        }
        return true;
    }

    @NonNull
    private static TypedValue getTypedValue() {
        ThreadLocal<TypedValue> threadLocal = sTempTypedValue;
        TypedValue tv2 = threadLocal.get();
        if (tv2 != null) {
            return tv2;
        }
        TypedValue tv3 = new TypedValue();
        threadLocal.set(tv3);
        return tv3;
    }

    private static TypedArray obtainAttributes(Resources res, Resources.Theme theme, AttributeSet set, int[] attrs) {
        if (theme == null) {
            return res.obtainAttributes(set, attrs);
        }
        return theme.obtainStyledAttributes(set, attrs, 0, 0);
    }

    @ColorInt
    private static int modulateColorAlpha(@ColorInt int color, @FloatRange(from = 0.0d, to = 1.0d) float alphaMod) {
        return (16777215 & color) | (Math.round(((float) Color.alpha(color)) * alphaMod) << 24);
    }
}
