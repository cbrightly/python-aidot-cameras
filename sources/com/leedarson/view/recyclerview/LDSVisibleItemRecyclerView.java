package com.leedarson.view.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LDSVisibleItemRecyclerView.kt */
public final class LDSVisibleItemRecyclerView extends RecyclerView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private a c;

    /* compiled from: LDSVisibleItemRecyclerView.kt */
    public interface a {
        void a(@NotNull List<Integer> list);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public LDSVisibleItemRecyclerView(@NotNull Context context) {
        this(context, (AttributeSet) null, 0, 6, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public LDSVisibleItemRecyclerView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, (DefaultConstructorMarker) null);
        k.e(context, "context");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ LDSVisibleItemRecyclerView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LDSVisibleItemRecyclerView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        k.e(context, "context");
    }

    public void onScrollStateChanged(int state) {
        Object[] objArr = {new Integer(state)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12011, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            super.onScrollStateChanged(state);
            if (state == 0) {
                a();
            }
        }
    }

    public void onScrolled(int dx, int dy) {
        Object[] objArr = {new Integer(dx), new Integer(dy)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12012, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            super.onScrolled(dx, dy);
            if (dx == 0 && dy == 0) {
                a();
            }
        }
    }

    private final void a() {
        int i;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12013, new Class[0], Void.TYPE).isSupported) {
            RecyclerView.LayoutManager manager = getLayoutManager();
            if (manager instanceof LinearLayoutManager) {
                int firstPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                int lastPosition = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
                List visibleRange = new ArrayList();
                if (firstPosition <= lastPosition) {
                    int i2 = firstPosition;
                    do {
                        i = i2;
                        i2++;
                        View view = ((LinearLayoutManager) manager).findViewByPosition(i);
                        if (view != null && b(view)) {
                            visibleRange.add(Integer.valueOf(i));
                            continue;
                        }
                    } while (i != lastPosition);
                }
                a aVar = this.c;
                if (aVar != null) {
                    aVar.a(visibleRange);
                } else {
                    k.t("mVisibleItemsListener");
                    throw null;
                }
            }
        }
    }

    public final boolean b(@NotNull View view) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 12014, new Class[]{View.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        k.e(view, "view");
        return view.getGlobalVisibleRect(new Rect());
    }

    public final void setVisibleItemsListener(@NotNull a listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 12016, new Class[]{a.class}, Void.TYPE).isSupported) {
            k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
            this.c = listener;
        }
    }
}
