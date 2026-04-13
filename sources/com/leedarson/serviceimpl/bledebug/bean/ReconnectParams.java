package com.leedarson.serviceimpl.bledebug.bean;

import java.io.Serializable;

public class ReconnectParams implements Serializable {
    public int maxCount = 3;
    public int reconnectDelay = 1000;

    public ReconnectParams(int maxCount2, int reconnectDelay2) {
        this.maxCount = maxCount2;
        this.reconnectDelay = reconnectDelay2;
    }

    public ReconnectParams() {
    }
}
