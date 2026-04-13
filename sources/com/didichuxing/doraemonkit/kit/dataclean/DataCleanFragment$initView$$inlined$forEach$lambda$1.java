package com.didichuxing.doraemonkit.kit.dataclean;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.view.ViewGroupKt;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.jvm.internal.k;
import kotlin.l;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0010\u0007\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "innerItem", "Lkotlin/x;", "onClick", "(Landroid/view/View;)V", "com/didichuxing/doraemonkit/kit/dataclean/DataCleanFragment$initView$2$1", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: DataCleanFragment.kt */
public final class DataCleanFragment$initView$$inlined$forEach$lambda$1 implements View.OnClickListener {
    final /* synthetic */ DataCleanFragment this$0;

    DataCleanFragment$initView$$inlined$forEach$lambda$1(DataCleanFragment dataCleanFragment) {
        this.this$0 = dataCleanFragment;
    }

    @SensorsDataInstrumented
    public final void onClick(View view) {
        View innerItem = view;
        Switch switchR = (Switch) innerItem.findViewById(R.id.switch_btn);
        TextView name = (TextView) innerItem.findViewById(R.id.tv_name);
        k.b(name, "name");
        if (k.a(name.getText(), DokitUtil.getString(R.string.dk_kit_cache_check_all))) {
            k.b(switchR, "switch");
            if (switchR.isChecked()) {
                for (View it : ViewGroupKt.getChildren(DataCleanFragment.access$getMItemWrap$p(this.this$0))) {
                    View findViewById = it.findViewById(R.id.switch_btn);
                    k.b(findViewById, "it.findViewById<Switch>(R.id.switch_btn)");
                    ((Switch) findViewById).setChecked(false);
                }
            } else {
                for (View it2 : ViewGroupKt.getChildren(DataCleanFragment.access$getMItemWrap$p(this.this$0))) {
                    View findViewById2 = it2.findViewById(R.id.switch_btn);
                    k.b(findViewById2, "it.findViewById<Switch>(R.id.switch_btn)");
                    ((Switch) findViewById2).setChecked(true);
                }
            }
        } else {
            k.b(switchR, "switch");
            switchR.setChecked(!switchR.isChecked());
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
