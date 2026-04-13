package com.didichuxing.doraemonkit.kit.toolpanel;

import android.view.View;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;
import com.didichuxing.doraemonkit.widget.dialog.SimpleDialogListener;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.l;

@l(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0006\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", "it", "Lkotlin/x;", "onClick", "(Landroid/view/View;)V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: DokitManagerFragment.kt */
public final class DokitManagerFragment$dealTitleBar$3 implements View.OnClickListener {
    final /* synthetic */ DokitManagerFragment this$0;

    DokitManagerFragment$dealTitleBar$3(DokitManagerFragment dokitManagerFragment) {
        this.this$0 = dokitManagerFragment;
    }

    @SensorsDataInstrumented
    public final void onClick(View view) {
        View view2 = view;
        this.this$0.showDialog((DialogProvider) new ConfirmDialogProvider(DokitUtil.getString(R.string.dk_toolpanel_dialog_reset_tip), new SimpleDialogListener(this) {
            final /* synthetic */ DokitManagerFragment$dealTitleBar$3 this$0;

            {
                this.this$0 = $outer;
            }

            /* JADX WARNING: Code restructure failed: missing block: B:2:0x0005, code lost:
                r0 = r0.getAssets();
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean onPositive() {
                /*
                    r7 = this;
                    android.app.Application r0 = com.didichuxing.doraemonkit.DoraemonKit.APPLICATION
                    r1 = 0
                    if (r0 == 0) goto L_0x0012
                    android.content.res.AssetManager r0 = r0.getAssets()
                    if (r0 == 0) goto L_0x0012
                    java.lang.String r2 = "dokit_system_kits.json"
                    java.io.InputStream r0 = r0.open(r2)
                    goto L_0x0013
                L_0x0012:
                    r0 = r1
                L_0x0013:
                    java.lang.String r2 = "UTF-8"
                    java.lang.String r2 = com.blankj.utilcode.util.f.h(r0, r2)
                    com.didichuxing.doraemonkit.kit.toolpanel.ToolPanelUtil$Companion r3 = com.didichuxing.doraemonkit.kit.toolpanel.ToolPanelUtil.Companion
                    java.lang.String r4 = "json"
                    kotlin.jvm.internal.k.b(r2, r4)
                    r3.jsonConfig2InnerKits(r2)
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment$dealTitleBar$3 r3 = r7.this$0
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment r3 = r3.this$0
                    r3.generateData()
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment$dealTitleBar$3 r3 = r7.this$0
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment r3 = r3.this$0
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerAdapter r3 = com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment.access$getMAdapter$p(r3)
                    r3.notifyDataSetChanged()
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment$dealTitleBar$3 r3 = r7.this$0
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment r3 = r3.this$0
                    r3.saveSystemKits()
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment$dealTitleBar$3 r3 = r7.this$0
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment r3 = r3.this$0
                    int r4 = com.didichuxing.doraemonkit.R.id.tv_reset
                    android.view.View r3 = r3._$_findCachedViewById(r4)
                    android.widget.TextView r3 = (android.widget.TextView) r3
                    java.lang.String r4 = "tv_reset"
                    kotlin.jvm.internal.k.b(r3, r4)
                    r4 = 8
                    r3.setVisibility(r4)
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment$Companion r3 = com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment.Companion
                    r4 = 0
                    r3.setIS_EDIT(r4)
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment$dealTitleBar$3 r3 = r7.this$0
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment r3 = r3.this$0
                    int r5 = com.didichuxing.doraemonkit.R.id.tv_edit
                    android.view.View r3 = r3._$_findCachedViewById(r5)
                    android.widget.TextView r3 = (android.widget.TextView) r3
                    java.lang.String r6 = "tv_edit"
                    kotlin.jvm.internal.k.b(r3, r6)
                    int r6 = com.didichuxing.doraemonkit.R.string.dk_edit
                    java.lang.String r6 = com.didichuxing.doraemonkit.util.DokitUtil.getString(r6)
                    r3.setText(r6)
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment$dealTitleBar$3 r3 = r7.this$0
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment r3 = r3.this$0
                    android.view.View r3 = r3._$_findCachedViewById(r5)
                    android.widget.TextView r3 = (android.widget.TextView) r3
                    android.app.Application r5 = com.didichuxing.doraemonkit.DoraemonKit.APPLICATION
                    if (r5 != 0) goto L_0x0085
                    kotlin.jvm.internal.k.n()
                L_0x0085:
                    int r6 = com.didichuxing.doraemonkit.R.color.dk_color_333333
                    int r5 = androidx.core.content.ContextCompat.getColor(r5, r6)
                    r3.setTextColor(r5)
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment$dealTitleBar$3 r3 = r7.this$0
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment r3 = r3.this$0
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerAdapter r3 = com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment.access$getMAdapter$p(r3)
                    com.didichuxing.doraemonkit.widget.bravh.module.BaseDraggableModule r3 = r3.getDraggableModule()
                    r3.setDragEnabled(r4)
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment$dealTitleBar$3 r3 = r7.this$0
                    com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment r3 = r3.this$0
                    com.didichuxing.doraemonkit.kit.toolpanel.TipDialogProvider r4 = new com.didichuxing.doraemonkit.kit.toolpanel.TipDialogProvider
                    int r5 = com.didichuxing.doraemonkit.R.string.dk_toolpanel_reset_complete
                    java.lang.String r5 = com.didichuxing.doraemonkit.util.DokitUtil.getString(r5)
                    r4.<init>(r5, r1)
                    r3.showDialog((com.didichuxing.doraemonkit.widget.dialog.DialogProvider) r4)
                    r1 = 1
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.toolpanel.DokitManagerFragment$dealTitleBar$3.AnonymousClass1.onPositive():boolean");
            }

            public boolean onNegative() {
                return true;
            }
        }));
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
