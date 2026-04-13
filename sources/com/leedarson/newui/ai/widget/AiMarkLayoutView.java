package com.leedarson.newui.ai.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.leedarson.R$layout;
import com.leedarson.newui.ai.beans.AiMarkPositionBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.ArrayList;

public class AiMarkLayoutView extends RelativeLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ArrayList<AiMarkPositionBean> c = new ArrayList<>();

    public AiMarkLayoutView(Context context) {
        super(context);
    }

    public AiMarkLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AiMarkLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void a(ArrayList<AiMarkPositionBean> datas) {
        if (!PatchProxy.proxy(new Object[]{datas}, this, changeQuickRedirect, false, 3499, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            this.c = datas;
            c();
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3500, new Class[0], Void.TYPE).isSupported) {
            removeAllViews();
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 3501, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            c();
        }
    }

    private void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3502, new Class[0], Void.TYPE).isSupported) {
            post(new a(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public /* synthetic */ void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3503, new Class[0], Void.TYPE).isSupported) {
            int width = getWidth();
            int height = getHeight();
            removeAllViews();
            ArrayList<AiMarkPositionBean> arrayList = this.c;
            if (arrayList == null || arrayList.size() == 0) {
                setVisibility(8);
                return;
            }
            for (int i = 0; i < this.c.size(); i++) {
                AiMarkPositionBean itemBean = this.c.get(i);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
                params.leftMargin = (int) (itemBean.topLeft * ((float) width));
                params.topMargin = (int) (itemBean.topTop * ((float) height));
                params.bottomMargin = (int) ((1.0f - itemBean.bottomTop) * ((float) height));
                params.rightMargin = (int) ((1.0f - itemBean.bottomLeft) * ((float) width));
                addView(LayoutInflater.from(getContext()).inflate(R$layout.ai_mark_item_layout, (ViewGroup) null), params);
            }
        }
    }
}
