package com.didichuxing.doraemonkit.kit.network.bean;

import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseExpandNode;
import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseNode;
import java.util.List;

public class MockInterceptTitleBean<T extends BaseNode> extends BaseExpandNode {
    private List<T> mChildNode;
    private String mName;

    public MockInterceptTitleBean(String name, List<T> childNode) {
        this.mName = name;
        this.mChildNode = childNode;
        setExpanded(false);
    }

    public String getName() {
        return this.mName;
    }

    public List<BaseNode> getChildNode() {
        return this.mChildNode;
    }
}
