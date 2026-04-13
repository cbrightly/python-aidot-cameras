package com.didichuxing.doraemonkit.kit.network.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.network.bean.WhiteHostBean;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.widget.bravh.BaseQuickAdapter;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;
import org.slf4j.e;

public class WhiteHostAdapter extends BaseQuickAdapter<WhiteHostBean, BaseViewHolder> {
    WhiteHostAdapter(int layoutResId, @Nullable List<WhiteHostBean> data) {
        super(layoutResId, data);
    }

    /* access modifiers changed from: protected */
    public void convert(@NonNull final BaseViewHolder helper, final WhiteHostBean item) {
        if (item.isCanAdd()) {
            ((TextView) helper.getView(R.id.tv_add)).setText(e.ANY_NON_NULL_MARKER);
        } else {
            ((TextView) helper.getView(R.id.tv_add)).setText("-");
        }
        int i = R.id.ed_host;
        ((EditText) helper.getView(i)).setText(item.getHost());
        ((EditText) helper.getView(i)).addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (s != null) {
                    item.setHost(s.toString());
                }
            }
        });
        helper.getView(R.id.fl_add_wrap).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                List<WhiteHostBean> hostBeans = WhiteHostAdapter.this.getData();
                if (!((TextView) helper.getView(R.id.tv_add)).getText().toString().equals(e.ANY_NON_NULL_MARKER)) {
                    hostBeans.remove(item);
                } else if (TextUtils.isEmpty(((EditText) helper.getView(R.id.ed_host)).getText().toString())) {
                    e0.n(DokitUtil.getString(R.string.dk_kit_net_monitor_white_host_edit_toast));
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                } else {
                    for (WhiteHostBean hostBean : hostBeans) {
                        hostBean.setCanAdd(false);
                    }
                    hostBeans.add(new WhiteHostBean("", true));
                }
                WhiteHostAdapter.this.notifyDataSetChanged();
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }
}
