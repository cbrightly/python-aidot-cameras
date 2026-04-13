package com.leedarson.newui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.utils.b;
import com.leedarson.utils.i;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class CenPlayBackController extends LinearLayout implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ImageView c;
    private ImageView d;
    private ImageView f;
    private i q;
    private boolean x;

    public CenPlayBackController(Context context) {
        this(context, (AttributeSet) null);
    }

    public CenPlayBackController(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.x = false;
        a(context);
    }

    private void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 4944, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.layout_center_playback_ctrl, this, true);
            setOrientation(0);
            setGravity(16);
            this.c = (ImageView) findViewById(R$id.img_last);
            this.d = (ImageView) findViewById(R$id.img_play);
            this.f = (ImageView) findViewById(R$id.img_next);
            this.c.setOnClickListener(this);
            this.d.setOnClickListener(this);
            this.f.setOnClickListener(this);
        }
    }

    public void setEventCallback(i eventCallback) {
        this.q = eventCallback;
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        i iVar;
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4945, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        i iVar2 = this.q;
        if (iVar2 != null) {
            iVar2.f();
        }
        if (b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.img_last) {
            i iVar3 = this.q;
            if (iVar3 != null) {
                iVar3.i();
            }
        } else if (viewId == R$id.img_play) {
            boolean z = true ^ this.x;
            this.x = z;
            setPlayStatus(z);
            i iVar4 = this.q;
            if (iVar4 != null) {
                iVar4.h(this.x);
            }
        } else if (viewId == R$id.img_next && (iVar = this.q) != null) {
            iVar.g();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void setPlayStatus(boolean isPlay) {
        boolean z = true;
        if (!PatchProxy.proxy(new Object[]{new Byte(isPlay ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4946, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.x = isPlay;
            ImageView imageView = this.d;
            if (isPlay) {
                z = false;
            }
            imageView.setSelected(z);
        }
    }

    public boolean b() {
        return this.x;
    }
}
