package com.didichuxing.doraemonkit.kit.toolpanel;

import android.view.View;
import android.widget.RadioButton;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.l;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "it", "Lkotlin/x;", "onClick", "(Landroid/view/View;)V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: ToolPanelAdapter.kt */
public final class ToolPanelAdapter$convert$4 implements View.OnClickListener {
    final /* synthetic */ RadioButton $rbNormal;

    ToolPanelAdapter$convert$4(RadioButton radioButton) {
        this.$rbNormal = radioButton;
    }

    @SensorsDataInstrumented
    public final void onClick(View view) {
        View view2 = view;
        this.$rbNormal.postDelayed(AnonymousClass1.INSTANCE, 500);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
