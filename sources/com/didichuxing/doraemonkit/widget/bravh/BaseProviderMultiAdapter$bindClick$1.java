package com.didichuxing.doraemonkit.widget.bravh;

import android.view.View;
import com.didichuxing.doraemonkit.widget.bravh.provider.BaseItemProvider;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.jvm.internal.k;
import kotlin.l;

@l(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0007\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u00002\u000e\u0010\u0003\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"T", "Landroid/view/View;", "kotlin.jvm.PlatformType", "it", "Lkotlin/x;", "onClick", "(Landroid/view/View;)V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: BaseProviderMultiAdapter.kt */
public final class BaseProviderMultiAdapter$bindClick$1 implements View.OnClickListener {
    final /* synthetic */ BaseViewHolder $viewHolder;
    final /* synthetic */ BaseProviderMultiAdapter this$0;

    BaseProviderMultiAdapter$bindClick$1(BaseProviderMultiAdapter baseProviderMultiAdapter, BaseViewHolder baseViewHolder) {
        this.this$0 = baseProviderMultiAdapter;
        this.$viewHolder = baseViewHolder;
    }

    @SensorsDataInstrumented
    public final void onClick(View view) {
        View it = view;
        int position = this.$viewHolder.getAdapterPosition();
        if (position == -1) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int position2 = position - this.this$0.getHeaderLayoutCount();
        int itemViewType = this.$viewHolder.getItemViewType();
        BaseViewHolder baseViewHolder = this.$viewHolder;
        k.b(it, "it");
        ((BaseItemProvider) this.this$0.getMItemProviders().get(itemViewType)).onClick(baseViewHolder, it, this.this$0.getData().get(position2), position2);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
