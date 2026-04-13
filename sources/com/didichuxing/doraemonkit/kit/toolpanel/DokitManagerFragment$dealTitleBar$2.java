package com.didichuxing.doraemonkit.kit.toolpanel;

import android.app.Application;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.widget.dialog.DialogListener;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.l;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "it", "Lkotlin/x;", "onClick", "(Landroid/view/View;)V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: DokitManagerFragment.kt */
public final class DokitManagerFragment$dealTitleBar$2 implements View.OnClickListener {
    final /* synthetic */ DokitManagerFragment this$0;

    DokitManagerFragment$dealTitleBar$2(DokitManagerFragment dokitManagerFragment) {
        this.this$0 = dokitManagerFragment;
    }

    @SensorsDataInstrumented
    public final void onClick(View view) {
        View it = view;
        if (it != null) {
            TextView textView = (TextView) it;
            int i = R.string.dk_edit;
            if (k.a(DokitUtil.getString(i), textView.getText().toString())) {
                TextView textView2 = (TextView) this.this$0._$_findCachedViewById(R.id.tv_reset);
                k.b(textView2, "tv_reset");
                textView2.setVisibility(0);
                DokitManagerFragment.Companion.setIS_EDIT(true);
                textView.setText(DokitUtil.getString(R.string.dk_complete));
                Application application = DoraemonKit.APPLICATION;
                if (application == null) {
                    k.n();
                }
                textView.setTextColor(ContextCompat.getColor(application, R.color.dk_color_337CC4));
                DokitManagerFragment.access$getMAdapter$p(this.this$0).getDraggableModule().setDragEnabled(true);
                this.this$0.reSetKits(true);
            } else if (k.a(DokitUtil.getString(R.string.dk_complete), textView.getText().toString())) {
                TextView textView3 = (TextView) this.this$0._$_findCachedViewById(R.id.tv_reset);
                k.b(textView3, "tv_reset");
                textView3.setVisibility(8);
                DokitManagerFragment.Companion.setIS_EDIT(false);
                textView.setText(DokitUtil.getString(i));
                Application application2 = DoraemonKit.APPLICATION;
                if (application2 == null) {
                    k.n();
                }
                textView.setTextColor(ContextCompat.getColor(application2, R.color.dk_color_333333));
                DokitManagerFragment.access$getMAdapter$p(this.this$0).getDraggableModule().setDragEnabled(false);
                this.this$0.reSetKits(false);
                this.this$0.saveSystemKits();
                this.this$0.showDialog((DialogProvider) new TipDialogProvider(DokitUtil.getString(R.string.dk_toolpanel_save_complete), (DialogListener) null));
            }
            DokitManagerFragment.access$getMAdapter$p(this.this$0).notifyDataSetChanged();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        TypeCastException typeCastException = new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
        throw typeCastException;
    }
}
