package com.didichuxing.doraemonkit.kit.toolpanel;

import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.blankj.utilcode.util.e;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.SharedPrefsKey;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.util.SharedPrefsUtil;
import com.didichuxing.doraemonkit.widget.bravh.BaseMultiItemQuickAdapter;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.util.Arrays;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import meshsdk.model.GroupInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ToolPanelAdapter.kt */
public final class ToolPanelAdapter extends BaseMultiItemQuickAdapter<KitWrapItem, BaseViewHolder> {
    public ToolPanelAdapter(@Nullable List<KitWrapItem> kitViews) {
        super(kitViews);
        addItemType(999, R.layout.dk_item_group_title);
        addItemType(201, R.layout.dk_item_kit);
        addItemType(KitWrapItem.TYPE_MODE, R.layout.dk_item_group_mode);
        addItemType(KitWrapItem.TYPE_EXIT, R.layout.dk_item_group_exit);
        addItemType(KitWrapItem.TYPE_VERSION, R.layout.dk_item_group_version);
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
                    return;
                }
                return;
            case KitWrapItem.TYPE_MODE:
                RadioButton rbNormal = (RadioButton) holder.getView(R.id.rb_normal);
                RadioButton rbSystem = (RadioButton) holder.getView(R.id.rb_system);
                ((RadioGroup) holder.getView(R.id.rb_group)).setOnCheckedChangeListener(ToolPanelAdapter$convert$3.INSTANCE);
                rbNormal.setOnClickListener(new ToolPanelAdapter$convert$4(rbNormal));
                rbSystem.setOnClickListener(new ToolPanelAdapter$convert$5(rbSystem));
                if (k.a(SharedPrefsUtil.getString(SharedPrefsKey.FLOAT_START_MODE, GroupInfo.TYPE_NORMAL), GroupInfo.TYPE_NORMAL)) {
                    rbNormal.setChecked(true);
                    return;
                } else {
                    rbSystem.setChecked(true);
                    return;
                }
            case KitWrapItem.TYPE_EXIT:
                ((TextView) holder.getView(R.id.close)).setOnClickListener(ToolPanelAdapter$convert$6.INSTANCE);
                return;
            case KitWrapItem.TYPE_VERSION:
                TextView name = (TextView) holder.getView(R.id.version);
                if (name.getParent() != null) {
                    ViewParent parent = name.getParent();
                    if (parent != null) {
                        ((ViewGroup) parent).setPadding(0, 0, 0, e.a());
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type android.view.ViewGroup");
                    }
                }
                String version = DokitUtil.getString(R.string.dk_kit_version);
                k.b(version, "DokitUtil.getString(R.string.dk_kit_version)");
                d0 d0Var = d0.a;
                String format = String.format(version, Arrays.copyOf(new Object[]{"3.2.0"}, 1));
                k.b(format, "java.lang.String.format(format, *args)");
                name.setText(format);
                return;
            case 999:
                String it2 = item.getName();
                if (it2.equals(DokitUtil.getString(R.string.dk_category_platform))) {
                    int i = R.id.tv_sub_title_name;
                    ((TextView) holder.getView(i)).setVisibility(0);
                    ((TextView) holder.getView(i)).setText("(www.dokit.cn)");
                } else {
                    ((TextView) holder.getView(R.id.tv_sub_title_name)).setVisibility(8);
                }
                ((TextView) holder.getView(R.id.tv_title_name)).setText(it2);
                return;
            default:
                return;
        }
    }
}
