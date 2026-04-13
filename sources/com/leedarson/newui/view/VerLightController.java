package com.leedarson.newui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.view.LDSImageView;
import com.leedarson.view.rangeseekbar.RangeSeekBar;
import com.leedarson.view.rangeseekbar.VerticalRangeSeekBar;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class VerLightController extends LinearLayout implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean c;
    private VerticalRangeSeekBar d;
    private LDSImageView f;
    private int q;
    /* access modifiers changed from: private */
    public b x;

    public interface b {
        void a(int i);

        void b(boolean z);
    }

    public VerLightController(Context context) {
        this(context, (AttributeSet) null);
    }

    public VerLightController(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.c = false;
        this.q = 10;
        b(context);
    }

    private void b(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5288, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.layout_ver_light_ctrl, this, true);
            setOrientation(1);
            this.d = (VerticalRangeSeekBar) findViewById(R$id.ver_seekbar_light);
            this.f = (LDSImageView) findViewById(R$id.img_onoff);
            this.d.setIndicatorTextDecimalFormat("#");
            this.d.setIndicatorTextStringFormat("%s%%");
            this.f.setOnClickListener(this);
            setOnClickListener(this);
            this.d.setOnRangeChangedListener(new a());
        }
    }

    public class a implements com.leedarson.view.rangeseekbar.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
        }

        public void c(RangeSeekBar view, boolean isLeft) {
        }

        public void b(RangeSeekBar view, boolean isLeft) {
            if (!PatchProxy.proxy(new Object[]{view, new Byte(isLeft ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5294, new Class[]{RangeSeekBar.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                if (isLeft && VerLightController.this.x != null) {
                    VerLightController.this.x.a(Math.round(view.getLeftSeekBar().s()));
                }
            }
        }
    }

    public void setLightOn(boolean lightOn) {
        Object[] objArr = {new Byte(lightOn ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5289, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.c = lightOn;
            this.f.setSelected(lightOn);
            this.d.setEnabled(lightOn);
            if (lightOn) {
                this.d.setAlpha(1.0f);
            } else {
                this.d.setAlpha(0.4f);
            }
        }
    }

    public int getCurrentProgress() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5290, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : Math.round(this.d.getLeftSeekBar().s());
    }

    public void setDimming(int dimming) {
        Object[] objArr = {new Integer(dimming)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5291, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.q = dimming;
            this.d.setProgress((float) dimming);
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5292, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        if (v.getId() == R$id.img_onoff) {
            boolean z = true ^ this.c;
            this.c = z;
            b bVar = this.x;
            if (bVar != null) {
                bVar.b(z);
            }
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void setLightListener(b lightListener) {
        this.x = lightListener;
    }
}
