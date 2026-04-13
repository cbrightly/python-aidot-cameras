package demo;

import android.view.View;
import android.widget.Button;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.serviceimpl.matter.R$id;
import com.leedarson.serviceimpl.matter.R$layout;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

/* compiled from: MtCodeTestAct.kt */
public final class MtCodeTestAct extends BaseActivity {
    /* access modifiers changed from: protected */
    public int O() {
        return R$layout.layout_mtcode_test;
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void X(View view) {
        View view2 = view;
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: protected */
    public void init() {
        ((Button) findViewById(R$id.btnFormat)).setOnClickListener(f.c);
    }

    /* access modifiers changed from: protected */
    public void R() {
    }
}
