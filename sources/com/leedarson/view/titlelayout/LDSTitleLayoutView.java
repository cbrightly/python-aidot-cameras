package com.leedarson.view.titlelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.views.common.LDSTextView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class LDSTitleLayoutView extends RelativeLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context c;
    private ImageView d;
    private ImageView f;
    /* access modifiers changed from: private */
    public d p0;
    private ImageView q;
    private ImageView x;
    private LDSTextView y;
    private LinearLayout z;

    public interface d {
        void a();

        void b();

        void c();
    }

    public LDSTitleLayoutView(Context context) {
        this(context, (AttributeSet) null);
    }

    public LDSTitleLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LDSTitleLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LDSTitleLayoutView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.c = context;
        b();
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12060, new Class[0], Void.TYPE).isSupported) {
            LayoutInflater.from(this.c).inflate(R$layout.common_title_layout, this, true);
            this.d = (ImageView) findViewById(R$id.iv_back);
            this.f = (ImageView) findViewById(R$id.iv_setting);
            this.q = (ImageView) findViewById(R$id.iv_edit);
            this.x = (ImageView) findViewById(R$id.iv_filter);
            this.y = (LDSTextView) findViewById(R$id.tv_title);
            this.z = (LinearLayout) findViewById(R$id.eventActionLayout);
            this.d.setOnClickListener(new a());
            this.x.setOnClickListener(new b());
            this.q.setOnClickListener(new c());
            this.f.setVisibility(8);
        }
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 12062, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (LDSTitleLayoutView.this.p0 != null) {
                LDSTitleLayoutView.this.p0.b();
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
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 12063, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (LDSTitleLayoutView.this.p0 != null) {
                LDSTitleLayoutView.this.p0.a();
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
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 12064, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (LDSTitleLayoutView.this.p0 != null) {
                LDSTitleLayoutView.this.p0.c();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void setTitle(String title) {
        if (!PatchProxy.proxy(new Object[]{title}, this, changeQuickRedirect, false, 12061, new Class[]{String.class}, Void.TYPE).isSupported) {
            LDSTextView lDSTextView = this.y;
            lDSTextView.setText(title + "");
        }
    }

    public void set_EventActionHandler(d _EventActionHandler) {
        this.p0 = _EventActionHandler;
    }

    public LDSTextView getTitleTxt() {
        return this.y;
    }

    public ImageView getGoBackImg() {
        return this.d;
    }
}
