package com.sensorsdata.analytics.android.sdk.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.sensorsdata.analytics.android.sdk.R;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.util.SADisplayUtil;

public class DebugModeSelectDialog extends Dialog implements View.OnClickListener {
    private SensorsDataAPI.DebugMode currentDebugMode;
    private OnDebugModeViewClickListener onDebugModeDialogClickListener;

    public interface OnDebugModeViewClickListener {
        void onCancel(Dialog dialog);

        void setDebugMode(Dialog dialog, SensorsDataAPI.DebugMode debugMode);
    }

    DebugModeSelectDialog(Context context, SensorsDataAPI.DebugMode debugMode) {
        super(context);
        this.currentDebugMode = debugMode;
    }

    /* access modifiers changed from: package-private */
    public void setOnDebugModeDialogClickListener(OnDebugModeViewClickListener onDebugModeDialogClickListener2) {
        this.onDebugModeDialogClickListener = onDebugModeDialogClickListener2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.sensors_analytics_debug_mode_dialog_content);
        initView();
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams p = window.getAttributes();
            p.width = SADisplayUtil.dip2px(getContext(), 270.0f);
            p.height = SADisplayUtil.dip2px(getContext(), 240.0f);
            window.setAttributes(p);
            GradientDrawable bg = new GradientDrawable();
            bg.setShape(0);
            bg.setColor(-1);
            bg.setCornerRadius((float) SADisplayUtil.dip2px(getContext(), 7.0f));
            window.setBackgroundDrawable(bg);
        }
    }

    private void initView() {
        ((TextView) findViewById(R.id.sensors_analytics_debug_mode_title)).setText("SDK 调试模式选择");
        TextView debugModeCancel = (TextView) findViewById(R.id.sensors_analytics_debug_mode_cancel);
        debugModeCancel.setText("取消");
        debugModeCancel.setOnClickListener(this);
        debugModeCancel.setFocusable(true);
        TextView debugModeOnly = (TextView) findViewById(R.id.sensors_analytics_debug_mode_only);
        debugModeOnly.setText("开启调试模式（不导入数据）");
        debugModeOnly.setOnClickListener(this);
        debugModeOnly.setFocusable(true);
        TextView debugModeTrack = (TextView) findViewById(R.id.sensors_analytics_debug_mode_track);
        debugModeTrack.setText("开启调试模式（导入数据）");
        debugModeTrack.setOnClickListener(this);
        debugModeTrack.setFocusable(true);
        String msg = "调试模式已关闭";
        SensorsDataAPI.DebugMode debugMode = this.currentDebugMode;
        if (debugMode == SensorsDataAPI.DebugMode.DEBUG_ONLY) {
            msg = "当前为 调试模式（不导入数据）";
        } else if (debugMode == SensorsDataAPI.DebugMode.DEBUG_AND_TRACK) {
            msg = "当前为 测试模式（导入数据）";
        }
        ((TextView) findViewById(R.id.sensors_analytics_debug_mode_message)).setText(msg);
        if (Build.VERSION.SDK_INT >= 16) {
            debugModeCancel.setBackground(getDrawable());
            debugModeOnly.setBackground(getDrawable());
            debugModeTrack.setBackground(getDrawable());
            return;
        }
        debugModeCancel.setBackgroundDrawable(getDrawable());
        debugModeOnly.setBackgroundDrawable(getDrawable());
        debugModeTrack.setBackgroundDrawable(getDrawable());
    }

    private StateListDrawable getDrawable() {
        GradientDrawable pressDrawable = new GradientDrawable();
        pressDrawable.setShape(0);
        pressDrawable.setColor(Color.parseColor("#dddddd"));
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setShape(0);
        normalDrawable.setColor(-1);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, pressDrawable);
        stateListDrawable.addState(new int[]{16842908}, pressDrawable);
        stateListDrawable.addState(new int[0], normalDrawable);
        return stateListDrawable;
    }

    public void onClick(View v) {
        if (this.onDebugModeDialogClickListener != null) {
            int id = v.getId();
            if (id == R.id.sensors_analytics_debug_mode_track) {
                this.onDebugModeDialogClickListener.setDebugMode(this, SensorsDataAPI.DebugMode.DEBUG_AND_TRACK);
            } else if (id == R.id.sensors_analytics_debug_mode_only) {
                this.onDebugModeDialogClickListener.setDebugMode(this, SensorsDataAPI.DebugMode.DEBUG_ONLY);
            } else if (id == R.id.sensors_analytics_debug_mode_cancel) {
                this.onDebugModeDialogClickListener.onCancel(this);
            }
        }
    }
}
