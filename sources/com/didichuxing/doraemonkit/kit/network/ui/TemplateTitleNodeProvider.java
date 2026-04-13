package com.didichuxing.doraemonkit.kit.network.ui;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.network.bean.MockTemplateTitleBean;
import com.didichuxing.doraemonkit.kit.network.room_db.DokitDbManager;
import com.didichuxing.doraemonkit.kit.network.room_db.MockTemplateApiBean;
import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseNode;
import com.didichuxing.doraemonkit.widget.bravh.provider.BaseNodeProvider;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class TemplateTitleNodeProvider extends BaseNodeProvider {
    public int getItemViewType() {
        return 100;
    }

    public int getLayoutId() {
        return R.layout.dk_mock_title_item;
    }

    public void convert(@NonNull BaseViewHolder holder, BaseNode item) {
        if (item instanceof MockTemplateTitleBean) {
            final MockTemplateTitleBean mockTitleBean = (MockTemplateTitleBean) item;
            MockTemplateApiBean mockApi = (MockTemplateApiBean) mockTitleBean.getChildNode().get(0);
            holder.setText(R.id.tv_title, (CharSequence) mockTitleBean.getName());
            if (mockTitleBean.isExpanded()) {
                holder.setImageResource(R.id.iv_more, R.mipmap.dk_arrow_open);
            } else {
                holder.setImageResource(R.id.iv_more, R.mipmap.dk_arrow_normal);
            }
            CheckBox checkBox = (CheckBox) holder.getView(R.id.menu_switch);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @SensorsDataInstrumented
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    CompoundButton compoundButton2 = compoundButton;
                    MockTemplateApiBean mockApi = (MockTemplateApiBean) mockTitleBean.getChildNode().get(0);
                    mockApi.setOpen(isChecked);
                    DokitDbManager.getInstance().updateTemplateApi(mockApi);
                    SensorsDataAutoTrackHelper.trackViewOnClick(compoundButton);
                }
            });
            if (mockApi.isOpen()) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
        }
    }

    public void onClick(BaseViewHolder holder, View view, BaseNode data, int position) {
        super.onClick(holder, view, data, position);
        if ((data instanceof MockTemplateTitleBean) && getAdapter() != null) {
            getAdapter().expandOrCollapse(position);
            if (((MockTemplateTitleBean) data).isExpanded()) {
                holder.setImageResource(R.id.iv_more, R.mipmap.dk_arrow_normal);
            } else {
                holder.setImageResource(R.id.iv_more, R.mipmap.dk_arrow_open);
            }
        }
    }
}
