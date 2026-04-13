package com.didichuxing.doraemonkit.widget.bravh.provider;

import com.didichuxing.doraemonkit.widget.bravh.BaseNodeAdapter;
import com.didichuxing.doraemonkit.widget.bravh.BaseProviderMultiAdapter;
import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseNode;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseNodeProvider.kt */
public abstract class BaseNodeProvider extends BaseItemProvider<BaseNode> {
    @Nullable
    public BaseNodeAdapter getAdapter() {
        BaseProviderMultiAdapter adapter = super.getAdapter();
        if (!(adapter instanceof BaseNodeAdapter)) {
            adapter = null;
        }
        return (BaseNodeAdapter) adapter;
    }
}
