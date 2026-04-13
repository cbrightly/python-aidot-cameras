package com.didichuxing.doraemonkit.kit.viewcheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.viewcheck.ViewCheckDokitView;
import com.didichuxing.doraemonkit.model.ViewInfo;
import java.util.List;

public class ViewCheckDrawDokitView extends AbsDokitView implements ViewCheckDokitView.OnViewSelectListener {
    private LayoutBorderView mLayoutBorderView;

    public void onCreate(Context context) {
    }

    public void onDestroy() {
        super.onDestroy();
        ViewCheckDokitView page = (ViewCheckDokitView) DokitViewManager.getInstance().getDokitView(a.b(), ViewCheckDokitView.class.getSimpleName());
        if (page != null) {
            page.removeViewSelectListener(this);
        }
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_view_check_draw, (ViewGroup) null);
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.flags = DokitViewLayoutParams.FLAG_NOT_FOCUSABLE_AND_NOT_TOUCHABLE;
        int i = DokitViewLayoutParams.MATCH_PARENT;
        params.width = i;
        params.height = i;
    }

    public void onViewCreated(FrameLayout view) {
        this.mLayoutBorderView = (LayoutBorderView) findViewById(R.id.rect_view);
        setDokitViewNotResponseTouchEvent(getRootView());
        postDelayed(new Runnable() {
            public void run() {
                ViewCheckDokitView dokitView = (ViewCheckDokitView) DokitViewManager.getInstance().getDokitView(a.b(), ViewCheckDokitView.class.getSimpleName());
                if (dokitView != null) {
                    dokitView.setViewSelectListener(ViewCheckDrawDokitView.this);
                }
            }
        }, 200);
    }

    public void onViewSelected(@Nullable View current, @NonNull List<View> list) {
        if (current == null) {
            this.mLayoutBorderView.showViewLayoutBorder((ViewInfo) null);
        } else {
            this.mLayoutBorderView.showViewLayoutBorder(new ViewInfo(current));
        }
    }

    public void onResume() {
        super.onResume();
        if (getNormalLayoutParams() != null) {
            FrameLayout.LayoutParams params = getNormalLayoutParams();
            params.setMargins(0, 0, 0, 0);
            params.width = -1;
            params.height = -1;
            invalidate();
        }
    }

    public boolean canDrag() {
        return false;
    }
}
