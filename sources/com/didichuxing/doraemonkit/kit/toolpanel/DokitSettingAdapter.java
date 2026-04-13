package com.didichuxing.doraemonkit.kit.toolpanel;

import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DokitSettingAdapter.kt */
public final class DokitSettingAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DokitSettingAdapter(@NotNull List<String> datas) {
        super(R.layout.dk_item_main_setting, datas);
        k.f(datas, "datas");
    }

    /* access modifiers changed from: protected */
    public void convert(@NotNull BaseViewHolder holder, @NotNull String name) {
        k.f(holder, "holder");
        k.f(name, "name");
        ((TextView) holder.getView(R.id.tv_name)).setText(name);
    }
}
