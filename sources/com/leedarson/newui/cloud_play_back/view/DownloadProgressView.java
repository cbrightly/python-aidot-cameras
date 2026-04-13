package com.leedarson.newui.cloud_play_back.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.Locale;

public class DownloadProgressView extends RelativeLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LDSTextView c;
    private ImageView d;
    private a f;

    public interface a {
        void onClose();
    }

    public DownloadProgressView(Context context) {
        super(context);
    }

    public DownloadProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    private void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 3837, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.include_download_progress, this, true);
            this.c = (LDSTextView) findViewById(R$id.tv_progress);
            ImageView imageView = (ImageView) findViewById(R$id.iv_close);
            this.d = imageView;
            imageView.setVisibility(0);
            this.d.setOnClickListener(new a(this));
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: b */
    public /* synthetic */ void c(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3839, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        a aVar = this.f;
        if (aVar != null) {
            aVar.onClose();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void setProgress(int progress) {
        if (!PatchProxy.proxy(new Object[]{new Integer(progress)}, this, changeQuickRedirect, false, 3838, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.c.setText(String.format(Locale.US, PubUtils.getString(getContext(), R$string.lds_downloading), new Object[]{Integer.valueOf(progress)}));
        }
    }

    public void setDownloadProgressListener(a downloadProgressListener) {
        this.f = downloadProgressListener;
    }
}
