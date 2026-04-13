package com.leedarson.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class TabLayoutMediator {
    private static Method a;
    private static Method b;
    public static ChangeQuickRedirect changeQuickRedirect;
    @NonNull
    private final TabLayout c;
    @NonNull
    private final ViewPager2 d;
    private final boolean e;
    private final a f;
    private RecyclerView.Adapter g;
    private boolean h;
    private TabLayoutOnPageChangeCallback i;
    private TabLayout.OnTabSelectedListener j;
    private RecyclerView.AdapterDataObserver k;

    public interface a {
        void onConfigureTab(@NonNull TabLayout.Tab tab, int i);
    }

    public TabLayoutMediator(@NonNull TabLayout tabLayout, @NonNull ViewPager2 viewPager, @NonNull a onConfigureTabCallback) {
        this(tabLayout, viewPager, true, onConfigureTabCallback);
    }

    public TabLayoutMediator(@NonNull TabLayout tabLayout, @NonNull ViewPager2 viewPager, boolean autoRefresh, @NonNull a onConfigureTabCallback) {
        this.c = tabLayout;
        this.d = viewPager;
        this.e = autoRefresh;
        this.f = onConfigureTabCallback;
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11778, new Class[0], Void.TYPE).isSupported) {
            if (!this.h) {
                RecyclerView.Adapter adapter = this.d.getAdapter();
                this.g = adapter;
                if (adapter != null) {
                    this.h = true;
                    TabLayoutOnPageChangeCallback tabLayoutOnPageChangeCallback = new TabLayoutOnPageChangeCallback(this.c);
                    this.i = tabLayoutOnPageChangeCallback;
                    this.d.registerOnPageChangeCallback(tabLayoutOnPageChangeCallback);
                    b bVar = new b(this.d);
                    this.j = bVar;
                    this.c.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) bVar);
                    if (this.e) {
                        PagerAdapterObserver pagerAdapterObserver = new PagerAdapterObserver();
                        this.k = pagerAdapterObserver;
                        this.g.registerAdapterDataObserver(pagerAdapterObserver);
                    }
                    b();
                    this.c.setScrollPosition(this.d.getCurrentItem(), 0.0f, true);
                    return;
                }
                throw new IllegalStateException("TabLayoutMediator attached before ViewPager2 has an adapter");
            }
            throw new IllegalStateException("TabLayoutMediator is already attached");
        }
    }

    /* access modifiers changed from: package-private */
    public void b() {
        int currItem;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11780, new Class[0], Void.TYPE).isSupported) {
            this.c.removeAllTabs();
            RecyclerView.Adapter adapter = this.g;
            if (adapter != null) {
                int adapterCount = adapter.getItemCount();
                for (int i2 = 0; i2 < adapterCount; i2++) {
                    TabLayout.Tab tab = this.c.newTab();
                    this.f.onConfigureTab(tab, i2);
                    this.c.addTab(tab, false);
                }
                if (adapterCount > 0 && (currItem = this.d.getCurrentItem()) != this.c.getSelectedTabPosition()) {
                    this.c.getTabAt(currItem).select();
                }
            }
        }
    }

    public static class TabLayoutOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        private final WeakReference<TabLayout> a;
        private int b;
        private int c;

        TabLayoutOnPageChangeCallback(TabLayout tabLayout) {
            this.a = new WeakReference<>(tabLayout);
            reset();
        }

        public void onPageScrollStateChanged(int state) {
            this.b = this.c;
            this.c = state;
        }

        public void onPageScrolled(int position, float positionOffset, int i) {
            boolean updateIndicator = false;
            Object[] objArr = {new Integer(position), new Float(positionOffset), new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11791, new Class[]{cls, Float.TYPE, cls}, Void.TYPE).isSupported) {
                TabLayout tabLayout = (TabLayout) this.a.get();
                if (tabLayout != null) {
                    int i2 = this.c;
                    boolean updateText = i2 != 2 || this.b == 1;
                    if (!(i2 == 2 && this.b == 0)) {
                        updateIndicator = true;
                    }
                    TabLayoutMediator.d(tabLayout, position, positionOffset, updateText, updateIndicator);
                }
            }
        }

        public void onPageSelected(int position) {
            boolean updateIndicator = true;
            if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 11792, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                TabLayout tabLayout = (TabLayout) this.a.get();
                if (tabLayout != null && tabLayout.getSelectedTabPosition() != position && position < tabLayout.getTabCount()) {
                    int i = this.c;
                    if (!(i == 0 || (i == 2 && this.b == 0))) {
                        updateIndicator = false;
                    }
                    TabLayoutMediator.c(tabLayout, tabLayout.getTabAt(position), updateIndicator);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            this.c = 0;
            this.b = 0;
        }
    }

    static {
        Class<TabLayout> cls = TabLayout.class;
        try {
            Class cls2 = Boolean.TYPE;
            Method declaredMethod = cls.getDeclaredMethod("setScrollPosition", new Class[]{Integer.TYPE, Float.TYPE, cls2, cls2});
            a = declaredMethod;
            declaredMethod.setAccessible(true);
            Method declaredMethod2 = TabLayout.class.getDeclaredMethod("selectTab", new Class[]{TabLayout.Tab.class, cls2});
            b = declaredMethod2;
            declaredMethod2.setAccessible(true);
        } catch (NoSuchMethodException e2) {
            throw new IllegalStateException("Can't reflect into method TabLayout.setScrollPosition(int, float, boolean, boolean)");
        }
    }

    static void d(TabLayout tabLayout, int i2, float f2, boolean z, boolean z2) {
        Object[] objArr = {tabLayout, new Integer(i2), new Float(f2), new Byte(z ? (byte) 1 : 0), new Byte(z2 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11781, new Class[]{TabLayout.class, Integer.TYPE, Float.TYPE, cls, cls}, Void.TYPE).isSupported) {
            TabLayout tabLayout2 = tabLayout;
            float positionOffset = f2;
            boolean updateIndicatorPosition = z2;
            int position = i2;
            boolean updateSelectedText = z;
            try {
                Method method = a;
                if (method != null) {
                    method.invoke(tabLayout2, new Object[]{Integer.valueOf(position), Float.valueOf(positionOffset), Boolean.valueOf(updateSelectedText), Boolean.valueOf(updateIndicatorPosition)});
                    return;
                }
                f("TabLayout.setScrollPosition(int, float, boolean, boolean)");
            } catch (Exception e2) {
                e("TabLayout.setScrollPosition(int, float, boolean, boolean)");
            }
        }
    }

    static void c(TabLayout tabLayout, TabLayout.Tab tab, boolean updateIndicator) {
        if (!PatchProxy.proxy(new Object[]{tabLayout, tab, new Byte(updateIndicator ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 11782, new Class[]{TabLayout.class, TabLayout.Tab.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            try {
                Method method = b;
                if (method != null) {
                    method.invoke(tabLayout, new Object[]{tab, Boolean.valueOf(updateIndicator)});
                    return;
                }
                f("TabLayout.selectTab(TabLayout.Tab, boolean)");
            } catch (Exception e2) {
                e("TabLayout.selectTab(TabLayout.Tab, boolean)");
            }
        }
    }

    private static void f(String method) {
        if (!PatchProxy.proxy(new Object[]{method}, (Object) null, changeQuickRedirect, true, 11783, new Class[]{String.class}, Void.TYPE).isSupported) {
            throw new IllegalStateException("Method " + method + " not found");
        }
    }

    private static void e(String method) {
        if (!PatchProxy.proxy(new Object[]{method}, (Object) null, changeQuickRedirect, true, 11784, new Class[]{String.class}, Void.TYPE).isSupported) {
            throw new IllegalStateException("Couldn't invoke method " + method);
        }
    }

    public static class b implements TabLayout.OnTabSelectedListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        private final ViewPager2 a;

        b(ViewPager2 viewPager) {
            this.a = viewPager;
        }

        @SensorsDataInstrumented
        public void onTabSelected(TabLayout.Tab tab) {
            if (PatchProxy.proxy(new Object[]{tab}, this, changeQuickRedirect, false, 11793, new Class[]{TabLayout.Tab.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackTabLayoutSelected(this, tab);
                return;
            }
            this.a.setCurrentItem(tab.getPosition(), true);
            SensorsDataAutoTrackHelper.trackTabLayoutSelected(this, tab);
        }

        public void onTabUnselected(TabLayout.Tab tab) {
        }

        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    public class PagerAdapterObserver extends RecyclerView.AdapterDataObserver {
        public static ChangeQuickRedirect changeQuickRedirect;

        PagerAdapterObserver() {
        }

        public void onChanged() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11785, new Class[0], Void.TYPE).isSupported) {
                TabLayoutMediator.this.b();
            }
        }

        public void onItemRangeChanged(int i, int i2) {
            Object[] objArr = {new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11786, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                TabLayoutMediator.this.b();
            }
        }

        public void onItemRangeChanged(int i, int i2, @Nullable Object obj) {
            Object[] objArr = {new Integer(i), new Integer(i2), obj};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11787, new Class[]{cls, cls, Object.class}, Void.TYPE).isSupported) {
                TabLayoutMediator.this.b();
            }
        }

        public void onItemRangeInserted(int i, int i2) {
            Object[] objArr = {new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11788, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                TabLayoutMediator.this.b();
            }
        }

        public void onItemRangeRemoved(int i, int i2) {
            Object[] objArr = {new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11789, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                TabLayoutMediator.this.b();
            }
        }

        public void onItemRangeMoved(int i, int i2, int i3) {
            Object[] objArr = {new Integer(i), new Integer(i2), new Integer(i3)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11790, clsArr, Void.TYPE).isSupported) {
                TabLayoutMediator.this.b();
            }
        }
    }
}
