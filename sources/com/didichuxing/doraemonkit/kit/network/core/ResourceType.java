package com.didichuxing.doraemonkit.kit.network.core;

public enum ResourceType {
    DOCUMENT("Document"),
    STYLESHEET("Stylesheet"),
    IMAGE("Image"),
    FONT("Font"),
    SCRIPT("Script"),
    XHR("XHR"),
    WEBSOCKET("WebSocket"),
    OTHER("Other");
    
    private final String mProtocolValue;

    private ResourceType(String protocolValue) {
        this.mProtocolValue = protocolValue;
    }

    public String getProtocolValue() {
        return this.mProtocolValue;
    }
}
