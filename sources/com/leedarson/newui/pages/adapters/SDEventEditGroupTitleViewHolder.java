package com.leedarson.newui.pages.adapters;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$id;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.SDRecord;
import com.leedarson.newui.pages.adapters.SDCardEventEditAdapter;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SDEventEditGroupTitleViewHolder.kt */
public final class SDEventEditGroupTitleViewHolder extends RecyclerView.ViewHolder {
    public static ChangeQuickRedirect changeQuickRedirect;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SDEventEditGroupTitleViewHolder(@NotNull View itemView) {
        super(itemView);
        k.e(itemView, "itemView");
    }

    public final void a(@NotNull SDRecord itemData, int position, @Nullable SDCardEventEditAdapter.a callback) {
        if (!PatchProxy.proxy(new Object[]{itemData, new Integer(position), callback}, this, changeQuickRedirect, false, 4313, new Class[]{SDRecord.class, Integer.TYPE, SDCardEventEditAdapter.a.class}, Void.TYPE).isSupported) {
            k.e(itemData, "itemData");
            LDSTextView $this$bindView_u24lambda_u2d0 = (LDSTextView) this.itemView.findViewById(R$id.tv_sd_event_group_title);
            if ($this$bindView_u24lambda_u2d0 != null) {
                $this$bindView_u24lambda_u2d0.setText(itemData.groupTitle);
            }
            LDSTextView $this$bindView_u24lambda_u2d2 = (LDSTextView) this.itemView.findViewById(R$id.tv_sd_event_group_action);
            if ($this$bindView_u24lambda_u2d2 != null) {
                $this$bindView_u24lambda_u2d2.setText(PubUtils.getString(BaseApplication.b(), !itemData.isCheck() ? R$string.lds_sd_card_group_select_branche : R$string.lds_sd_card_group_deselect_branche));
                $this$bindView_u24lambda_u2d2.setOnClickListener(new a(callback, position, itemData));
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    public static final void b(SDCardEventEditAdapter.a $callback, int $position, SDRecord $itemData, View view) {
        Object[] objArr = {$callback, new Integer($position), $itemData, view};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 4314, new Class[]{SDCardEventEditAdapter.a.class, Integer.TYPE, SDRecord.class, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        k.e($itemData, "$itemData");
        if ($callback != null) {
            $callback.a($position, $itemData);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
