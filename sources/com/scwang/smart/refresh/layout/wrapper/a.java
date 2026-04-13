package com.scwang.smart.refresh.layout.wrapper;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.Space;
import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingParent;
import androidx.viewpager.widget.ViewPager;
import com.scwang.smart.refresh.layout.api.b;
import com.scwang.smart.refresh.layout.api.e;
import com.scwang.smart.refresh.layout.kernel.R$id;
import com.scwang.smart.refresh.layout.listener.i;
import java.util.LinkedList;
import java.util.List;

/* compiled from: RefreshContentWrapper */
public class a implements b, com.scwang.smart.refresh.layout.listener.a, ValueAnimator.AnimatorUpdateListener {
    protected com.scwang.smart.refresh.layout.simple.a a1 = new com.scwang.smart.refresh.layout.simple.a();
    protected View c;
    protected View d;
    protected View f;
    protected boolean p0 = true;
    protected View q;
    protected View x;
    protected int y = 0;
    protected boolean z = true;

    public a(@NonNull View view) {
        this.f = view;
        this.d = view;
        this.c = view;
    }

    /* access modifiers changed from: protected */
    public void k(View content, e kernel) {
        View scrollableView = null;
        boolean isInEditMode = this.c.isInEditMode();
        while (true) {
            if (scrollableView != null && (!(scrollableView instanceof NestedScrollingParent) || (scrollableView instanceof NestedScrollingChild))) {
                break;
            }
            content = m(content, scrollableView == null);
            if (content == scrollableView) {
                break;
            }
            if (!isInEditMode) {
                com.scwang.smart.refresh.layout.util.a.a(content, kernel, this);
            }
            scrollableView = content;
        }
        if (scrollableView != null) {
            this.f = scrollableView;
        }
    }

    public void j(boolean enableRefresh, boolean enableLoadMore) {
        this.z = enableRefresh;
        this.p0 = enableLoadMore;
    }

    /* access modifiers changed from: protected */
    public View m(View content, boolean selfAble) {
        View scrollableView = null;
        LinkedList linkedList = new LinkedList();
        List<View> list = linkedList;
        list.add(content);
        while (list.size() > 0 && scrollableView == null) {
            View view = (View) linkedList.poll();
            if (view != null) {
                if ((selfAble || view != content) && com.scwang.smart.refresh.layout.util.b.e(view)) {
                    scrollableView = view;
                } else if (view instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) view;
                    for (int j = 0; j < group.getChildCount(); j++) {
                        list.add(group.getChildAt(j));
                    }
                }
            }
        }
        return scrollableView == null ? content : scrollableView;
    }

    /* access modifiers changed from: protected */
    public View l(View content, PointF event, View orgScrollableView) {
        if ((content instanceof ViewGroup) && event != null) {
            ViewGroup viewGroup = (ViewGroup) content;
            int childCount = viewGroup.getChildCount();
            PointF point = new PointF();
            int i = childCount;
            while (i > 0) {
                View child = viewGroup.getChildAt(i - 1);
                if (!com.scwang.smart.refresh.layout.util.b.g(viewGroup, child, event.x, event.y, point)) {
                    i--;
                } else if (!(child instanceof ViewPager) && com.scwang.smart.refresh.layout.util.b.e(child)) {
                    return child;
                } else {
                    event.offset(point.x, point.y);
                    View child2 = l(child, event, orgScrollableView);
                    event.offset(-point.x, -point.y);
                    return child2;
                }
            }
        }
        return orgScrollableView;
    }

    @NonNull
    public View getView() {
        return this.c;
    }

    @NonNull
    public View f() {
        return this.f;
    }

    public void h(int spinner, int headerTranslationViewId, int footerTranslationViewId) {
        View footerTranslationView;
        View headerTranslationView;
        boolean translated = false;
        if (!(headerTranslationViewId == -1 || (headerTranslationView = this.d.findViewById(headerTranslationViewId)) == null)) {
            if (spinner > 0) {
                translated = true;
                headerTranslationView.setTranslationY((float) spinner);
            } else if (headerTranslationView.getTranslationY() > 0.0f) {
                headerTranslationView.setTranslationY(0.0f);
            }
        }
        if (!(footerTranslationViewId == -1 || (footerTranslationView = this.d.findViewById(footerTranslationViewId)) == null)) {
            if (spinner < 0) {
                translated = true;
                footerTranslationView.setTranslationY((float) spinner);
            } else if (footerTranslationView.getTranslationY() < 0.0f) {
                footerTranslationView.setTranslationY(0.0f);
            }
        }
        if (!translated) {
            this.d.setTranslationY((float) spinner);
        } else {
            this.d.setTranslationY(0.0f);
        }
        View view = this.q;
        if (view != null) {
            view.setTranslationY((float) Math.max(0, spinner));
        }
        View view2 = this.x;
        if (view2 != null) {
            view2.setTranslationY((float) Math.min(0, spinner));
        }
    }

    public boolean g() {
        return this.z && this.a1.a(this.c);
    }

    public boolean i() {
        return this.p0 && this.a1.b(this.c);
    }

    public void b(MotionEvent e) {
        PointF point = new PointF(e.getX(), e.getY());
        point.offset((float) (-this.c.getLeft()), (float) (-this.c.getTop()));
        View view = this.f;
        View view2 = this.c;
        if (view != view2) {
            this.f = l(view2, point, view);
        }
        if (this.f == this.c) {
            this.a1.a = null;
        } else {
            this.a1.a = point;
        }
    }

    public void c(e kernel, View fixedHeader, View fixedFooter) {
        k(this.c, kernel);
        if (fixedHeader != null || fixedFooter != null) {
            this.q = fixedHeader;
            this.x = fixedFooter;
            ViewGroup frameLayout = new FrameLayout(this.c.getContext());
            int index = kernel.d().getLayout().indexOfChild(this.c);
            kernel.d().getLayout().removeView(this.c);
            frameLayout.addView(this.c, 0, new ViewGroup.LayoutParams(-1, -1));
            kernel.d().getLayout().addView(frameLayout, index, this.c.getLayoutParams());
            this.c = frameLayout;
            if (fixedHeader != null) {
                fixedHeader.setTag(R$id.srl_tag, "fixed-top");
                ViewGroup.LayoutParams lp = fixedHeader.getLayoutParams();
                ViewGroup parent = (ViewGroup) fixedHeader.getParent();
                int index2 = parent.indexOfChild(fixedHeader);
                parent.removeView(fixedHeader);
                lp.height = com.scwang.smart.refresh.layout.util.b.h(fixedHeader);
                parent.addView(new Space(this.c.getContext()), index2, lp);
                frameLayout.addView(fixedHeader, 1, lp);
            }
            if (fixedFooter != null) {
                fixedFooter.setTag(R$id.srl_tag, "fixed-bottom");
                ViewGroup.LayoutParams lp2 = fixedFooter.getLayoutParams();
                ViewGroup parent2 = (ViewGroup) fixedFooter.getParent();
                int index3 = parent2.indexOfChild(fixedFooter);
                parent2.removeView(fixedFooter);
                FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(lp2);
                lp2.height = com.scwang.smart.refresh.layout.util.b.h(fixedFooter);
                parent2.addView(new Space(this.c.getContext()), index3, lp2);
                flp.gravity = 80;
                frameLayout.addView(fixedFooter, 1, flp);
            }
        }
    }

    public void a(i boundary) {
        if (boundary instanceof com.scwang.smart.refresh.layout.simple.a) {
            this.a1 = (com.scwang.smart.refresh.layout.simple.a) boundary;
        } else {
            this.a1.b = boundary;
        }
    }

    public void d(boolean enable) {
        this.a1.c = enable;
    }

    public ValueAnimator.AnimatorUpdateListener e(int spinner) {
        View view = this.f;
        if (view == null || spinner == 0) {
            return null;
        }
        if ((spinner >= 0 || !view.canScrollVertically(1)) && (spinner <= 0 || !this.f.canScrollVertically(-1))) {
            return null;
        }
        this.y = spinner;
        return this;
    }

    public void onAnimationUpdate(ValueAnimator animation) {
        int value = ((Integer) animation.getAnimatedValue()).intValue();
        try {
            float dy = ((float) (value - this.y)) * this.f.getScaleY();
            View view = this.f;
            if (view instanceof AbsListView) {
                com.scwang.smart.refresh.layout.util.b.j((AbsListView) view, (int) dy);
            } else {
                view.scrollBy(0, (int) dy);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        this.y = value;
    }
}
