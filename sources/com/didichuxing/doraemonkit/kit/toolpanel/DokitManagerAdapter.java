package com.didichuxing.doraemonkit.kit.toolpanel;

import android.widget.ImageView;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.widget.bravh.BaseMultiItemQuickAdapter;
import com.didichuxing.doraemonkit.widget.bravh.module.DraggableModule;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DokitManagerAdapter.kt */
public final class DokitManagerAdapter extends BaseMultiItemQuickAdapter<KitWrapItem, BaseViewHolder> implements DraggableModule {
    public DokitManagerAdapter(@Nullable List<KitWrapItem> kitViews) {
        super(kitViews);
        addItemType(999, R.layout.dk_item_group_title);
        addItemType(201, R.layout.dk_item_group_kit_manager);
    }

    /* access modifiers changed from: protected */
    public void convert(@NotNull BaseViewHolder holder, @NotNull KitWrapItem item) {
        k.f(holder, "holder");
        k.f(item, "item");
        switch (item.getItemType()) {
            case 201:
                AbstractKit it = item.getKit();
                if (it != null) {
                    ((TextView) holder.getView(R.id.name)).setText(it.getName());
                    ((ImageView) holder.getView(R.id.icon)).setImageResource(it.getIcon());
                    if (DokitManagerFragment.Companion.getIS_EDIT()) {
                        int i = R.id.iv_tag;
                        ((ImageView) holder.getView(i)).setVisibility(0);
                        ImageView $this$apply = (ImageView) holder.getView(i);
                        if (item.getChecked()) {
                            $this$apply.setImageResource(R.mipmap.dk_kit_item_checked);
                        } else {
                            $this$apply.setImageResource(R.mipmap.dk_kit_item_normal);
                        }
                        if (item.getChecked()) {
                            holder.getView(R.id.view_mask).setVisibility(8);
                            return;
                        } else {
                            holder.getView(R.id.view_mask).setVisibility(0);
                            return;
                        }
                    } else {
                        ((ImageView) holder.getView(R.id.iv_tag)).setVisibility(8);
                        holder.getView(R.id.view_mask).setVisibility(8);
                        return;
                    }
                } else {
                    return;
                }
            case 999:
                ((TextView) holder.getView(R.id.tv_title_name)).setText(item.getName());
                return;
            default:
                return;
        }
    }
}
