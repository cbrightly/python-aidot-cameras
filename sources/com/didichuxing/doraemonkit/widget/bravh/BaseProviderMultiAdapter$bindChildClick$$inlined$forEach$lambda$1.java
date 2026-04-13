package com.didichuxing.doraemonkit.widget.bravh;

import android.view.View;
import com.didichuxing.doraemonkit.widget.bravh.provider.BaseItemProvider;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.jvm.internal.k;
import kotlin.l;

@l(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0010\b\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u00002\u000e\u0010\u0003\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"T", "LLandroid/view/View;;", "kotlin.jvm.PlatformType", "v", "Lkotlin/x;", "onClick", "(LLandroid/view/View;;)V", "com/didichuxing/doraemonkit/widget/bravh/BaseProviderMultiAdapter$$special$$inlined$let$lambda$1", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: BaseProviderMultiAdapter.kt */
public final class BaseProviderMultiAdapter$bindChildClick$$inlined$forEach$lambda$1 implements View.OnClickListener {
    final /* synthetic */ BaseItemProvider $provider$inlined;
    final /* synthetic */ BaseViewHolder $viewHolder$inlined;
    final /* synthetic */ BaseProviderMultiAdapter this$0;

    BaseProviderMultiAdapter$bindChildClick$$inlined$forEach$lambda$1(BaseProviderMultiAdapter baseProviderMultiAdapter, BaseViewHolder baseViewHolder, BaseItemProvider baseItemProvider) {
        this.this$0 = baseProviderMultiAdapter;
        this.$viewHolder$inlined = baseViewHolder;
        this.$provider$inlined = baseItemProvider;
    }

    @SensorsDataInstrumented
    public final void onClick(View view) {
        View v = view;
        int position = this.$viewHolder$inlined.getAdapterPosition();
        if (position == -1) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int position2 = position - this.this$0.getHeaderLayoutCount();
        BaseItemProvider baseItemProvider = this.$provider$inlined;
        BaseViewHolder baseViewHolder = this.$viewHolder$inlined;
        k.b(v, "v");
        baseItemProvider.onChildClick(baseViewHolder, v, this.this$0.getData().get(position2), position2);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
