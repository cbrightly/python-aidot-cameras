package com.didichuxing.doraemonkit.kit.toolpanel;

import android.widget.RadioGroup;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.SharedPrefsKey;
import com.didichuxing.doraemonkit.util.SharedPrefsUtil;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.l;
import meshsdk.model.GroupInfo;

@l(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\b\u001a\u00020\u00052\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u00002\u0006\u0010\u0004\u001a\u00020\u0003H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"Landroid/widget/RadioGroup;", "kotlin.jvm.PlatformType", "<anonymous parameter 0>", "", "checkedId", "Lkotlin/x;", "onCheckedChanged", "(Landroid/widget/RadioGroup;I)V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: ToolPanelAdapter.kt */
public final class ToolPanelAdapter$convert$3 implements RadioGroup.OnCheckedChangeListener {
    public static final ToolPanelAdapter$convert$3 INSTANCE = new ToolPanelAdapter$convert$3();

    ToolPanelAdapter$convert$3() {
    }

    @SensorsDataInstrumented
    public final void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        RadioGroup radioGroup2 = radioGroup;
        if (checkedId == R.id.rb_normal) {
            SharedPrefsUtil.putString(SharedPrefsKey.FLOAT_START_MODE, GroupInfo.TYPE_NORMAL);
        } else {
            SharedPrefsUtil.putString(SharedPrefsKey.FLOAT_START_MODE, "system");
        }
        SensorsDataAutoTrackHelper.trackRadioGroup(radioGroup, checkedId);
    }
}
