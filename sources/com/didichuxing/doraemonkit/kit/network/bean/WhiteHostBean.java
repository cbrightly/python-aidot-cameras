package com.didichuxing.doraemonkit.kit.network.bean;

public class WhiteHostBean {
    private boolean canAdd;
    private String host;

    public WhiteHostBean(String host2, boolean canAdd2) {
        this.host = host2;
        this.canAdd = canAdd2;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host2) {
        this.host = host2;
    }

    public boolean isCanAdd() {
        return this.canAdd;
    }

    public void setCanAdd(boolean canAdd2) {
        this.canAdd = canAdd2;
    }
}
