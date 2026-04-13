package com.leedarson.serviceinterface.event;

public class CloseQRScanEvent {
    public int delay;
    public String navigationType;

    public CloseQRScanEvent(String navigationType2, int delay2) {
        this.navigationType = navigationType2;
        this.delay = delay2;
    }
}
