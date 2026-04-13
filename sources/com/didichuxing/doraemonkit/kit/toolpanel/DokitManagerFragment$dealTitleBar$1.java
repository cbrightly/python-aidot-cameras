package com.didichuxing.doraemonkit.kit.toolpanel;

import android.view.View;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.l;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "it", "Lkotlin/x;", "onClick", "(Landroid/view/View;)V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: DokitManagerFragment.kt */
public final class DokitManagerFragment$dealTitleBar$1 implements View.OnClickListener {
    final /* synthetic */ DokitManagerFragment this$0;

    DokitManagerFragment$dealTitleBar$1(DokitManagerFragment dokitManagerFragment) {
        this.this$0 = dokitManagerFragment;
    }

    @SensorsDataInstrumented
    public final void onClick(View view) {
        View view2 = view;
        this.this$0.dealBack();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
