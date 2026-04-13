package com.didichuxing.doraemonkit.kit.weaknetwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class NetWokDokitView extends AbsDokitView {
    ImageView mIvClose;
    LinearLayout mLlSpeedWrap;
    LinearLayout mLlTimeWrap;
    TextView mTvNetWork;
    TextView mTvRequestSpeed;
    TextView mTvResponseSpeed;
    TextView mTvTimeOutTime;

    public void onCreate(Context context) {
    }

    public View onCreateView(Context context, FrameLayout rootView) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_network, rootView, false);
    }

    public void onViewCreated(FrameLayout rootView) {
        this.mTvNetWork = (TextView) rootView.findViewById(R.id.tv_net_type);
        this.mTvTimeOutTime = (TextView) rootView.findViewById(R.id.tv_time);
        this.mTvRequestSpeed = (TextView) rootView.findViewById(R.id.tv_request_speed);
        this.mTvResponseSpeed = (TextView) rootView.findViewById(R.id.tv_response_speed);
        this.mLlTimeWrap = (LinearLayout) rootView.findViewById(R.id.ll_timeout_wrap);
        this.mLlSpeedWrap = (LinearLayout) rootView.findViewById(R.id.ll_speed_wrap);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.iv_close);
        this.mIvClose = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                WeakNetworkManager.get().setActive(false);
                DokitViewManager.getInstance().detach((Class<? extends AbsDokitView>) NetWokDokitView.class);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        int i = DokitViewLayoutParams.WRAP_CONTENT;
        params.width = i;
        params.height = i;
        params.gravity = 51;
        params.x = 100;
        params.y = 100;
    }

    public void onResume() {
        super.onResume();
        try {
            if (this.mTvNetWork != null) {
                switch (WeakNetworkManager.get().getType()) {
                    case 1:
                        this.mTvNetWork.setText(DokitUtil.getString(R.string.dk_weaknet_type_timeout));
                        TextView textView = this.mTvTimeOutTime;
                        textView.setText("" + WeakNetworkManager.get().getTimeOutMillis() + " ms");
                        this.mLlTimeWrap.setVisibility(0);
                        this.mLlSpeedWrap.setVisibility(8);
                        break;
                    case 2:
                        this.mTvNetWork.setText(DokitUtil.getString(R.string.dk_weaknet_type_speed));
                        TextView textView2 = this.mTvRequestSpeed;
                        textView2.setText("" + WeakNetworkManager.get().getRequestSpeed() + " KB/S");
                        TextView textView3 = this.mTvResponseSpeed;
                        textView3.setText("" + WeakNetworkManager.get().getResponseSpeed() + " KB/S");
                        this.mLlTimeWrap.setVisibility(8);
                        this.mLlSpeedWrap.setVisibility(0);
                        break;
                    default:
                        this.mTvNetWork.setText(DokitUtil.getString(R.string.dk_weaknet_type_off));
                        this.mLlTimeWrap.setVisibility(8);
                        this.mLlSpeedWrap.setVisibility(8);
                        break;
                }
                invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void invalidate() {
        if (getRootView() != null) {
            if (isNormalMode()) {
                FrameLayout.LayoutParams layoutParams = getNormalLayoutParams();
                if (layoutParams != null) {
                    layoutParams.width = -2;
                    layoutParams.height = -2;
                    getRootView().setLayoutParams(layoutParams);
                    return;
                }
                return;
            }
            WindowManager.LayoutParams layoutParams2 = getSystemLayoutParams();
            if (layoutParams2 != null) {
                layoutParams2.width = -2;
                layoutParams2.height = -2;
                this.mWindowManager.updateViewLayout(getRootView(), layoutParams2);
            }
        }
    }
}
