package com.didichuxing.doraemonkit.kit.network.ui;

import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.kit.network.bean.MockTemplateTitleBean;
import com.didichuxing.doraemonkit.kit.network.room_db.MockTemplateApiBean;
import com.didichuxing.doraemonkit.widget.bravh.BaseNodeAdapter;
import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseNode;
import com.didichuxing.doraemonkit.widget.bravh.module.LoadMoreModule;
import java.util.List;

public class TemplateMockAdapter extends BaseNodeAdapter implements LoadMoreModule {
    public static final String TAG = "InterceptMockAdapter";
    public static String TEMPLATER_UPLOAD_URL = "https://mock.dokit.cn/api/app/interface";
    public static final int TYPE_CONTENT = 200;
    public static final int TYPE_TITLE = 100;

    public TemplateMockAdapter(List<BaseNode> nodeList) {
        super(nodeList);
        addFullSpanNodeProvider(new TemplateTitleNodeProvider());
        addNodeProvider(new TemplateDetailNodeProvider());
    }

    /* access modifiers changed from: protected */
    public int getItemType(@NonNull List<? extends BaseNode> data, int position) {
        BaseNode node = (BaseNode) data.get(position);
        if (node instanceof MockTemplateTitleBean) {
            return 100;
        }
        if (node instanceof MockTemplateApiBean) {
            return 200;
        }
        return -1;
    }
}
