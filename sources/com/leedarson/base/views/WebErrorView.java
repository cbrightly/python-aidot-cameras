package com.leedarson.base.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.leedarson.module_base.R$id;
import com.leedarson.module_base.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class WebErrorView extends FrameLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private View c;
    /* access modifiers changed from: private */
    public View.OnClickListener d;

    public WebErrorView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public WebErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebErrorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        b(context);
    }

    private void b(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 827, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.layout_web_error, this, true);
            View findViewById = findViewById(R$id.btn_try_again);
            this.c = findViewById;
            findViewById.setOnClickListener(new a());
            setBackgroundColor(Color.parseColor("#f0f4f8"));
        }
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 828, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View v = view;
            if (WebErrorView.this.d != null) {
                WebErrorView.this.d.onClick(v);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void setRetryClickListener(View.OnClickListener clickListener) {
        this.d = clickListener;
    }
}
