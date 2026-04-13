package com.didichuxing.doraemonkit.kit.dataclean;

import android.view.View;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.SettingItem;
import com.didichuxing.doraemonkit.util.DataCleanUtil;
import com.didichuxing.doraemonkit.widget.dialog.DialogInfo;
import com.didichuxing.doraemonkit.widget.dialog.SimpleDialogListener;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.l;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "view", "Lkotlin/x;", "onClick", "(Landroid/view/View;)V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: DataCleanFragment.kt */
public final class DataCleanFragment$initView$3 implements View.OnClickListener {
    final /* synthetic */ DataCleanFragment this$0;

    DataCleanFragment$initView$3(DataCleanFragment dataCleanFragment) {
        this.this$0 = dataCleanFragment;
    }

    @SensorsDataInstrumented
    public final void onClick(View view) {
        View view2 = view;
        DialogInfo dialogInfo = new DialogInfo();
        dialogInfo.title = this.this$0.getString(R.string.dk_hint);
        dialogInfo.desc = this.this$0.getString(R.string.dk_app_data_clean);
        dialogInfo.listener = new SimpleDialogListener(this) {
            final /* synthetic */ DataCleanFragment$initView$3 this$0;

            {
                this.this$0 = $outer;
            }

            public boolean onPositive() {
                this.this$0.this$0.cleanCache();
                ((SettingItem) DataCleanFragment.access$getMSettingItemAdapter$p(this.this$0.this$0).getData().get(0)).rightDesc = DataCleanUtil.getApplicationDataSizeStr(this.this$0.this$0.getContext());
                DataCleanFragment.access$getMSettingItemAdapter$p(this.this$0.this$0).notifyDataSetChanged();
                return true;
            }

            public boolean onNegative() {
                return true;
            }
        };
        this.this$0.showDialog(dialogInfo);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
