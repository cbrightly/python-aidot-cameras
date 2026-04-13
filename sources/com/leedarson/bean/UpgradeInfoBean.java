package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;

public class UpgradeInfoBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String context;
    private boolean open;
    private String partSplit;
    private String title;

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open2) {
        this.open = open2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context2) {
        this.context = context2;
    }

    public String getPartSplit() {
        return this.partSplit;
    }

    public void setPartSplit(String partSplit2) {
        this.partSplit = partSplit2;
    }
}
