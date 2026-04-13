package com.leedarson.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.base.logger.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class LDSSlidingConflictRecyclerView extends RecyclerView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c;
    private int d;
    private int f = 40;
    private int q = 3;
    /* access modifiers changed from: private */
    public boolean x = false;

    public LDSSlidingConflictRecyclerView(Context context) {
        super(context);
        b();
    }

    public LDSSlidingConflictRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        b();
    }

    public LDSSlidingConflictRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        b();
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ev}, this, changeQuickRedirect, false, 12008, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        switch (ev.getAction()) {
            case 0:
                this.c = (int) ev.getX();
                this.d = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case 1:
            case 3:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case 2:
                int endX = (int) ev.getX();
                int disX = Math.abs(endX - this.c);
                if (disX > this.q * Math.abs(((int) ev.getY()) - this.d) && !this.x) {
                    if (!canScrollVertically(this.c - endX) || disX <= this.f) {
                        if (canScrollVertically(-1)) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                            a.c("RecyclerView", "RecyclerView  自身处理");
                            break;
                        } else {
                            a.c("RecyclerView", "RecyclerView  都无法往上滚了，交给父类");
                            getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                        }
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        a.c("RecyclerView", "RecyclerView  转交父组来处理");
                        break;
                    }
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12009, new Class[0], Void.TYPE).isSupported) {
            addOnScrollListener(new RecyclerView.OnScrollListener() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (!PatchProxy.proxy(new Object[]{recyclerView, new Integer(newState)}, this, changeQuickRedirect, false, 12010, new Class[]{RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == 0) {
                            boolean unused = LDSSlidingConflictRecyclerView.this.x = false;
                        } else {
                            boolean unused2 = LDSSlidingConflictRecyclerView.this.x = true;
                        }
                    }
                }
            });
        }
    }

    public void setOnAnimationState(boolean flagAnimationed) {
        this.x = flagAnimationed;
    }
}
