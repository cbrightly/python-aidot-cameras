package com.didichuxing.doraemonkit.kit.network.ui;

import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.kit.network.bean.MockInterceptTitleBean;
import com.didichuxing.doraemonkit.kit.network.room_db.MockInterceptApiBean;
import com.didichuxing.doraemonkit.widget.bravh.BaseNodeAdapter;
import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseNode;
import com.didichuxing.doraemonkit.widget.bravh.module.LoadMoreModule;
import java.util.List;

public class InterceptMockAdapter extends BaseNodeAdapter implements LoadMoreModule {
    public static final String TAG = "InterceptMockAdapter";
    public static final int TYPE_CONTENT = 200;
    public static final int TYPE_TITLE = 100;

    public InterceptMockAdapter(List<BaseNode> nodeList) {
        super(nodeList);
        addFullSpanNodeProvider(new InterceptTitleNodeProvider());
        addNodeProvider(new InterceptDetailNodeProvider());
    }

    /* access modifiers changed from: protected */
    public int getItemType(@NonNull List<? extends BaseNode> data, int position) {
        BaseNode node = (BaseNode) data.get(position);
        if (node instanceof MockInterceptTitleBean) {
            return 100;
        }
        if (node instanceof MockInterceptApiBean) {
            return 200;
        }
        return -1;
    }
}
