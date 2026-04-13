package com.leedarson.newui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class BatteryMeterView extends RelativeLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private BatteryView c;
    private ImageView d;

    public BatteryMeterView(Context context) {
        super(context);
    }

    public BatteryMeterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    private void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 4937, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.layout_battery_meter, this, true);
            this.c = (BatteryView) findViewById(R$id.battery);
            this.d = (ImageView) findViewById(R$id.img_charging);
        }
    }

    public void setProgress(int percent) {
        BatteryView batteryView;
        Object[] objArr = {new Integer(percent)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4938, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (batteryView = this.c) != null) {
            batteryView.setProgress(percent);
        }
    }

    public void setCharging(boolean isCharging) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isCharging ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4939, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            BatteryView batteryView = this.c;
            if (batteryView != null) {
                batteryView.setCharging(isCharging);
            }
            ImageView imageView = this.d;
            if (imageView == null) {
                return;
            }
            if (isCharging) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(4);
            }
        }
    }
}
