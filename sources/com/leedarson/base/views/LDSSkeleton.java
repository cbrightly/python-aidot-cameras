package com.leedarson.base.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.leedarson.module_base.R$id;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.rmiri.skeleton.SkeletonViewGroup;

public class LDSSkeleton extends FrameLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private SkeletonViewGroup c;

    public LDSSkeleton(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public LDSSkeleton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLayout(int layoutId) {
        Object[] objArr = {new Integer(layoutId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 766, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            View inflate = LayoutInflater.from(getContext()).inflate(layoutId, this, true);
            SkeletonViewGroup skeletonViewGroup = (SkeletonViewGroup) findViewById(R$id.skeletonGroup);
            this.c = skeletonViewGroup;
            skeletonViewGroup.setSkeletonListener(new a());
            this.c.v();
        }
    }

    public class a implements SkeletonViewGroup.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void b() {
        }

        public void a() {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 767(0x2ff, float:1.075E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.rmiri.skeleton.SkeletonViewGroup r1 = r0.c
            if (r1 == 0) goto L_0x0025
            r1.p()
            io.rmiri.skeleton.SkeletonViewGroup r1 = r0.c
            r2 = 8
            r1.setVisibility(r2)
        L_0x0025:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.views.LDSSkeleton.a():void");
    }
}
