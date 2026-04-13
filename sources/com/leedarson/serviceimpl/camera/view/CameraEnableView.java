package com.leedarson.serviceimpl.camera.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.leedarson.serviceimpl.camera.R$id;
import com.leedarson.serviceimpl.camera.R$layout;
import com.leedarson.serviceinterface.SystemService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class CameraEnableView extends LinearLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private View c;
    private View d;
    private TextView f;
    private AddManualView q;
    /* access modifiers changed from: private */
    public d x;

    public interface d {
        void a();

        void b();

        void c(View view, int i);
    }

    public CameraEnableView(Context context) {
        this(context, (AttributeSet) null);
    }

    public CameraEnableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraEnableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        b();
    }

    private void b() {
        int statusBarHeight = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7481, new Class[0], Void.TYPE).isSupported) {
            int b2 = com.leedarson.base.utils.d.b(getContext(), 24.0f);
            setGravity(1);
            setOrientation(1);
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
            LayoutInflater.from(getContext()).inflate(R$layout.view_enable_camera, this, true);
            this.f = (TextView) findViewById(R$id.tv_enable_manually);
            View findViewById = findViewById(R$id.fl_enable_camera);
            this.c = findViewById;
            findViewById.setOnClickListener(new a());
            View findViewById2 = findViewById(R$id.fl_add_manually);
            this.d = findViewById2;
            findViewById2.setOnClickListener(new b());
            LinearLayout title_layout = (LinearLayout) findViewById(R$id.ll_title);
            SystemService systemService = (SystemService) com.alibaba.android.arouter.launcher.a.c().g(SystemService.class);
            if (systemService != null) {
                statusBarHeight = systemService.getStatusBarHeight(getContext());
            }
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) title_layout.getLayoutParams();
            lp.topMargin = statusBarHeight;
            title_layout.setLayoutParams(lp);
            title_layout.findViewById(R$id.btn_back).setOnClickListener(new c());
            this.q = (AddManualView) findViewById(R$id.view_add_manual);
        }
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 7484, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View v = view;
            if (CameraEnableView.this.x != null) {
                CameraEnableView.this.x.c(v, v.getId());
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 7485, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (CameraEnableView.this.x != null) {
                CameraEnableView.this.x.b();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class c implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 7486, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (CameraEnableView.this.x != null) {
                CameraEnableView.this.x.a();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void c(String text) {
        if (!PatchProxy.proxy(new Object[]{text}, this, changeQuickRedirect, false, 7482, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.d.setVisibility(0);
            if (!TextUtils.isEmpty(text)) {
                this.f.setText(text);
            }
            this.q.setText(text);
        }
    }

    public void setOnItemClickListener(d listener) {
        this.x = listener;
    }

    public void setPosition(String position) {
        if (!PatchProxy.proxy(new Object[]{position}, this, changeQuickRedirect, false, 7483, new Class[]{String.class}, Void.TYPE).isSupported) {
            if ("bottom".equalsIgnoreCase(position)) {
                this.q.setVisibility(0);
                this.d.setVisibility(8);
                return;
            }
            this.q.setVisibility(8);
            this.d.setVisibility(0);
        }
    }
}
