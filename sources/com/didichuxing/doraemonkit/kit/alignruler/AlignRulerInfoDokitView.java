package com.didichuxing.doraemonkit.kit.alignruler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.AlignRulerConfig;
import com.didichuxing.doraemonkit.kit.alignruler.AlignRulerMarkerDokitView;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.util.UIUtils;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class AlignRulerInfoDokitView extends AbsDokitView implements AlignRulerMarkerDokitView.OnAlignRulerMarkerPositionChangeListener {
    private TextView mAlignHex;
    private ImageView mClose;
    /* access modifiers changed from: private */
    public AlignRulerMarkerDokitView mMarker;
    private int mWindowHeight;
    private int mWindowWidth;

    public void onCreate(Context context) {
        this.mWindowWidth = UIUtils.getWidthPixels();
        this.mWindowHeight = UIUtils.getHeightPixels();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mMarker.removePositionChangeListener(this);
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_align_ruler_info, (ViewGroup) null);
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.width = getScreenShortSideLength();
        params.height = -2;
        params.x = 0;
        params.y = UIUtils.getHeightPixels() - UIUtils.dp2px(95.0f);
    }

    public void onViewCreated(FrameLayout view) {
        postDelayed(new Runnable() {
            public void run() {
                AlignRulerMarkerDokitView unused = AlignRulerInfoDokitView.this.mMarker = (AlignRulerMarkerDokitView) DokitViewManager.getInstance().getDokitView(a.b(), AlignRulerMarkerDokitView.class.getSimpleName());
                if (AlignRulerInfoDokitView.this.mMarker != null) {
                    AlignRulerInfoDokitView.this.mMarker.addPositionChangeListener(AlignRulerInfoDokitView.this);
                }
            }
        }, 100);
        initView();
    }

    private void initView() {
        getRootView().setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return AlignRulerInfoDokitView.this.mTouchProxy.onTouchEvent(v, event);
            }
        });
        this.mAlignHex = (TextView) findViewById(R.id.align_hex);
        ImageView imageView = (ImageView) findViewById(R.id.close);
        this.mClose = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                AlignRulerConfig.setAlignRulerOpen(false);
                DokitViewManager.getInstance().detach(AlignRulerMarkerDokitView.class.getSimpleName());
                DokitViewManager.getInstance().detach(AlignRulerLineDokitView.class.getSimpleName());
                DokitViewManager.getInstance().detach(AlignRulerInfoDokitView.class.getSimpleName());
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    public void onPositionChanged(int x, int y) {
        int left = x;
        int top = y;
        this.mAlignHex.setText(getResources().getString(R.string.dk_align_info_text, new Object[]{Integer.valueOf(left), Integer.valueOf(this.mWindowWidth - left), Integer.valueOf(top), Integer.valueOf(this.mWindowHeight - top)}));
    }
}
