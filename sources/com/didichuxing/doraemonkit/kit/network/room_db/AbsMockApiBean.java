package com.didichuxing.doraemonkit.kit.network.room_db;

import com.didichuxing.doraemonkit.widget.bravh.entity.node.BaseNode;
import java.util.List;

public abstract class AbsMockApiBean extends BaseNode {
    /* access modifiers changed from: package-private */
    public boolean isOpen() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void setOpen(boolean open) {
    }

    /* access modifiers changed from: package-private */
    public String getId() {
        return "";
    }

    /* access modifiers changed from: package-private */
    public String getSelectedSceneId() {
        return "";
    }

    /* access modifiers changed from: package-private */
    public String getQuery() {
        return "";
    }

    /* access modifiers changed from: package-private */
    public String getBody() {
        return "";
    }

    /* access modifiers changed from: package-private */
    public String getPath() {
        return "";
    }

    public List<BaseNode> getChildNode() {
        return null;
    }
}
