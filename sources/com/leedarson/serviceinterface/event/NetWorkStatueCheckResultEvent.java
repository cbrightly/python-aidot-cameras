package com.leedarson.serviceinterface.event;

public class NetWorkStatueCheckResultEvent {
    public String callParams = "";
    private String from = "";
    public String localFileContent = "";

    public NetWorkStatueCheckResultEvent(String localFileContent2, String callParams2, String from2) {
        this.localFileContent = localFileContent2;
        this.callParams = callParams2;
        this.from = from2;
    }
}
