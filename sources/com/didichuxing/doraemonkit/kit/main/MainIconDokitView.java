package com.didichuxing.doraemonkit.kit.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.FloatIconConfig;
import com.didichuxing.doraemonkit.datapick.DataPickManager;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class MainIconDokitView extends AbsDokitView {
    public static int FLOAT_SIZE = 174;

    public void onCreate(Context context) {
    }

    public void onViewCreated(FrameLayout view) {
        getRootView().setId(R.id.float_icon_id);
        getRootView().setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                DataPickManager.getInstance().addData("dokit_sdk_home_ck_entry");
                DoraemonKit.showToolPanel();
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_main_launch_icon, view, false);
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.x = FloatIconConfig.getLastPosX();
        params.y = FloatIconConfig.getLastPosY();
        int i = FLOAT_SIZE;
        params.width = i;
        params.height = i;
    }

    public void onResume() {
        super.onResume();
        if (isNormalMode()) {
            FrameLayout.LayoutParams params = getNormalLayoutParams();
            int i = FLOAT_SIZE;
            params.width = i;
            params.height = i;
            invalidate();
        }
    }
}
