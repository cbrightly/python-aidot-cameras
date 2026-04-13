package com.leedarson.serviceimpl.camera.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceimpl.camera.R$id;
import com.leedarson.serviceimpl.camera.R$layout;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class AddManualView extends LinearLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LDSTextView c;

    public AddManualView(Context context) {
        super(context, (AttributeSet) null);
    }

    public AddManualView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    private void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 7476, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.layout_add_manual, this, true);
            this.c = (LDSTextView) findViewById(R$id.tv_add_manually);
            b();
        }
    }

    public void setText(String txt) {
        if (!PatchProxy.proxy(new Object[]{txt}, this, changeQuickRedirect, false, 7477, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.c.setText(txt);
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7478, new Class[0], Void.TYPE).isSupported) {
            String hexColor = "#FC8E35";
            if (!SharePreferenceUtils.getPrefString(getContext(), "repositoryName", "").equals("M071-AiDot")) {
                hexColor = SharePreferenceUtils.getPrefString(getContext(), "themeColor", hexColor);
            }
            int themeColor = Color.parseColor(hexColor);
            this.c.setTextColor(themeColor);
            ((ImageView) findViewById(R$id.iv_arrow)).setImageTintList(ColorStateList.valueOf(Color.argb(255, Color.red(themeColor), Color.green(themeColor), Color.blue(themeColor))));
        }
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ View.OnClickListener c;

        a(View.OnClickListener onClickListener) {
            this.c = onClickListener;
        }

        @SensorsDataInstrumented
        public void onClick(View v) {
            if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 7480, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
                return;
            }
            this.c.onClick(v);
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
        }
    }

    public void setClickEvent(View.OnClickListener clickListener) {
        if (!PatchProxy.proxy(new Object[]{clickListener}, this, changeQuickRedirect, false, 7479, new Class[]{View.OnClickListener.class}, Void.TYPE).isSupported) {
            this.c.setOnClickListener(new a(clickListener));
        }
    }
}
