package com.leedarson.newui.view.radar;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.view.dialogs.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

/* compiled from: RadarIntroduceDialog */
public class f extends c {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ImageView f;
    private View q;
    /* access modifiers changed from: private */
    public b x;

    /* compiled from: RadarIntroduceDialog */
    public interface b {
        void a();
    }

    public f(@NonNull Context context, b listener) {
        super(context);
        h();
        this.x = listener;
    }

    private void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5429, new Class[0], Void.TYPE).isSupported) {
            e(R$layout.radar_introduce_dialog_view);
            this.q = findViewById(R$id.tv_confirm);
            this.f = (ImageView) findViewById(R$id.ivGif);
            com.bumptech.glide.b.t(getContext()).k().K0(Integer.valueOf(R$drawable.path_introduce_gif)).H0(this.f);
            this.q.setOnClickListener(new a());
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            ((RelativeLayout) findViewById(R$id.rl_dialog)).setOnClickListener((View.OnClickListener) null);
        }
    }

    /* compiled from: RadarIntroduceDialog */
    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5430, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            SharePreferenceUtils.setPrefBoolean(f.this.getContext(), "show_radar_introduce", false);
            f.this.dismiss();
            if (f.this.x != null) {
                f.this.x.a();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }
}
