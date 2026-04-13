package com.scwang.smart.refresh.layout.util;

import android.content.res.Resources;
import android.graphics.PointF;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ScrollingView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.scwang.smart.refresh.layout.api.a;
import com.scwang.smart.refresh.layout.kernel.R$id;

/* compiled from: SmartUtil */
public class b implements Interpolator {
    public static int a = 0;
    public static int b = 1;
    private static float c = Resources.getSystem().getDisplayMetrics().density;
    private static final float d;
    private static final float e;
    private int f;

    static {
        float k = 1.0f / k(1.0f);
        d = k;
        e = 1.0f - (k * k(1.0f));
    }

    public b(int type) {
        this.f = type;
    }

    public static int h(View view) {
        int childHeightSpec;
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int i = p.height;
        if (i > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        }
        view.measure(childWidthSpec, childHeightSpec);
        return view.getMeasuredHeight();
    }

    public static void j(@NonNull AbsListView listView, int y) {
        View firstView;
        if (Build.VERSION.SDK_INT >= 19) {
            listView.scrollListBy(y);
        } else if (listView instanceof ListView) {
            int firstPosition = listView.getFirstVisiblePosition();
            if (firstPosition != -1 && (firstView = listView.getChildAt(0)) != null) {
                ((ListView) listView).setSelectionFromTop(firstPosition, firstView.getTop() - y);
            }
        } else {
            listView.smoothScrollBy(y, 0);
        }
    }

    public static boolean f(View view) {
        if (view instanceof a) {
            return false;
        }
        if ((view instanceof AbsListView) || (view instanceof ScrollView) || (view instanceof ScrollingView) || (view instanceof WebView) || (view instanceof NestedScrollingChild)) {
            return true;
        }
        return false;
    }

    public static boolean e(View view) {
        if (view instanceof a) {
            return false;
        }
        if (f(view) || (view instanceof ViewPager) || (view instanceof NestedScrollingParent)) {
            return true;
        }
        return false;
    }

    public static void d(View scrollableView, int velocity) {
        if (scrollableView instanceof ScrollView) {
            ((ScrollView) scrollableView).fling(velocity);
        } else if (scrollableView instanceof AbsListView) {
            if (Build.VERSION.SDK_INT >= 21) {
                ((AbsListView) scrollableView).fling(velocity);
            }
        } else if (scrollableView instanceof WebView) {
            ((WebView) scrollableView).flingScroll(0, velocity);
        } else if (scrollableView instanceof NestedScrollView) {
            ((NestedScrollView) scrollableView).fling(velocity);
        } else if (scrollableView instanceof RecyclerView) {
            ((RecyclerView) scrollableView).fling(0, velocity);
        }
    }

    public static boolean b(@NonNull View targetView, PointF touch) {
        if (targetView.canScrollVertically(-1) && targetView.getVisibility() == 0) {
            return false;
        }
        if (!(targetView instanceof ViewGroup) || touch == null) {
            return true;
        }
        ViewGroup viewGroup = (ViewGroup) targetView;
        int childCount = viewGroup.getChildCount();
        PointF point = new PointF();
        for (int i = childCount; i > 0; i--) {
            View child = viewGroup.getChildAt(i - 1);
            if (g(viewGroup, child, touch.x, touch.y, point)) {
                Object tag = child.getTag(R$id.srl_tag);
                if ("fixed".equals(tag) || "fixed-bottom".equals(tag)) {
                    return false;
                }
                touch.offset(point.x, point.y);
                boolean can = b(child, touch);
                touch.offset(-point.x, -point.y);
                return can;
            }
        }
        return true;
    }

    public static boolean a(@NonNull View targetView, PointF touch, boolean contentFull) {
        if (targetView.canScrollVertically(1) && targetView.getVisibility() == 0) {
            return false;
        }
        if ((targetView instanceof ViewGroup) && touch != null && !f(targetView)) {
            ViewGroup viewGroup = (ViewGroup) targetView;
            int childCount = viewGroup.getChildCount();
            PointF point = new PointF();
            for (int i = childCount; i > 0; i--) {
                View child = viewGroup.getChildAt(i - 1);
                if (g(viewGroup, child, touch.x, touch.y, point)) {
                    Object tag = child.getTag(R$id.srl_tag);
                    if ("fixed".equals(tag) || "fixed-top".equals(tag)) {
                        return false;
                    }
                    touch.offset(point.x, point.y);
                    boolean can = a(child, touch, contentFull);
                    touch.offset(-point.x, -point.y);
                    return can;
                }
            }
        }
        if (contentFull || targetView.canScrollVertically(-1)) {
            return true;
        }
        return false;
    }

    public static boolean g(@NonNull View group, @NonNull View child, float x, float y, PointF outLocalPoint) {
        if (child.getVisibility() != 0) {
            return false;
        }
        float[] point = {x, y};
        point[0] = point[0] + ((float) (group.getScrollX() - child.getLeft()));
        point[1] = point[1] + ((float) (group.getScrollY() - child.getTop()));
        boolean isInView = point[0] >= 0.0f && point[1] >= 0.0f && point[0] < ((float) child.getWidth()) && point[1] < ((float) child.getHeight());
        if (isInView && outLocalPoint != null) {
            outLocalPoint.set(point[0] - x, point[1] - y);
        }
        return isInView;
    }

    public static int c(float dpValue) {
        return (int) ((c * dpValue) + 0.5f);
    }

    public static float i(int pxValue) {
        return ((float) pxValue) / c;
    }

    private static float k(float x) {
        float x2 = x * 8.0f;
        if (x2 < 1.0f) {
            return x2 - (1.0f - ((float) Math.exp((double) (-x2))));
        }
        return 0.36787945f + ((1.0f - 0.36787945f) * (1.0f - ((float) Math.exp((double) (1.0f - x2)))));
    }

    public float getInterpolation(float input) {
        if (this.f == b) {
            return 1.0f - ((1.0f - input) * (1.0f - input));
        }
        float interpolated = d * k(input);
        if (interpolated > 0.0f) {
            return e + interpolated;
        }
        return interpolated;
    }
}
