package com.leedarson.serviceinterface.event;

import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.serviceinterface.listener.OnBridgeRespListener;

public class BaseEvent {
    public OnBridgeRespListener onBridgeRespListener;
    public WVJBWebView target;
}
