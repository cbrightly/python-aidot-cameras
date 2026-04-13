package com.didichuxing.doraemonkit.widget.bravh.entity.node;

/* compiled from: BaseExpandNode.kt */
public abstract class BaseExpandNode extends BaseNode {
    private boolean isExpanded = true;

    public final boolean isExpanded() {
        return this.isExpanded;
    }

    public final void setExpanded(boolean z) {
        this.isExpanded = z;
    }
}
