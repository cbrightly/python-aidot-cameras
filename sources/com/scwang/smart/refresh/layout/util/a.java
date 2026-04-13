package com.scwang.smart.refresh.layout.util;

import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.scwang.smart.refresh.layout.api.e;

/* compiled from: DesignUtil */
public class a {
    public static void a(View content, e kernel, com.scwang.smart.refresh.layout.listener.a listener) {
        try {
            if (content instanceof CoordinatorLayout) {
                kernel.d().d(false);
                ViewGroup layout = (ViewGroup) content;
                for (int i = layout.getChildCount() - 1; i >= 0; i--) {
                    View view = layout.getChildAt(i);
                    if (view instanceof AppBarLayout) {
                        ((AppBarLayout) view).addOnOffsetChangedListener((AppBarLayout.OnOffsetChangedListener) new C0203a(listener));
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /* renamed from: com.scwang.smart.refresh.layout.util.a$a  reason: collision with other inner class name */
    /* compiled from: DesignUtil */
    public static final class C0203a implements AppBarLayout.OnOffsetChangedListener {
        final /* synthetic */ com.scwang.smart.refresh.layout.listener.a a;

        C0203a(com.scwang.smart.refresh.layout.listener.a aVar) {
            this.a = aVar;
        }

        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            com.scwang.smart.refresh.layout.listener.a aVar = this.a;
            boolean z = true;
            boolean z2 = verticalOffset >= 0;
            if (appBarLayout.getTotalScrollRange() + verticalOffset > 0) {
                z = false;
            }
            aVar.j(z2, z);
        }
    }
}
