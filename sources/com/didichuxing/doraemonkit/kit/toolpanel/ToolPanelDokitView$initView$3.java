package com.didichuxing.doraemonkit.kit.toolpanel;

import android.text.TextUtils;
import android.view.View;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.datapick.DataPickManager;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter;
import com.didichuxing.doraemonkit.widget.bravh.listener.OnItemClickListener;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\t\u001a\u00020\u00062\u000e\u0010\u0001\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {"Lcom/didichuxing/doraemonkit/widget/bravh/BaseQuickAdapter;", "<anonymous parameter 0>", "Landroid/view/View;", "<anonymous parameter 1>", "", "position", "Lkotlin/x;", "onItemClick", "(Lcom/didichuxing/doraemonkit/widget/bravh/BaseQuickAdapter;Landroid/view/View;I)V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: ToolPanelDokitView.kt */
public final class ToolPanelDokitView$initView$3 implements OnItemClickListener {
    final /* synthetic */ ToolPanelDokitView this$0;

    ToolPanelDokitView$initView$3(ToolPanelDokitView toolPanelDokitView) {
        this.this$0 = toolPanelDokitView;
    }

    public final void onItemClick(@NotNull BaseQuickAdapter<?, ?> $noName_0, @NotNull View $noName_1, int position) {
        k.f($noName_0, "<anonymous parameter 0>");
        k.f($noName_1, "<anonymous parameter 1>");
        KitWrapItem multiKitItem = (KitWrapItem) this.this$0.mKits.get(position);
        if (multiKitItem.getItemType() == 201) {
            DokitViewManager.getInstance().detachToolPanel();
            AbstractKit kit = multiKitItem.getKit();
            if (kit != null) {
                kit.onClick(a.b());
            }
            try {
                AbstractKit kit2 = multiKitItem.getKit();
                Boolean valueOf = kit2 != null ? Boolean.valueOf(kit2.isInnerKit()) : null;
                if (valueOf == null) {
                    k.n();
                }
                if (!valueOf.booleanValue() || TextUtils.isEmpty(multiKitItem.getKit().innerKitId())) {
                    DataPickManager.getInstance().addData("dokit_sdk_business_ck");
                } else {
                    DataPickManager.getInstance().addData(multiKitItem.getKit().innerKitId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
