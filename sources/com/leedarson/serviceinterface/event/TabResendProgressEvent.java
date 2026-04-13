package com.leedarson.serviceinterface.event;

public class TabResendProgressEvent {
    public static final String STEP_DIAGNOSIS_WEBVIEW = "diagnosisWebview";
    public static final String STEP_RESEND_ON_CHANGE = "resendOnChange";
    public static final String STEP_RESTART_HTTP_SERVER = "restartHttpServer";
    public int code;
    public long duration;
    public String stepName;

    public TabResendProgressEvent(String stepName2, long duration2, int code2) {
        this.stepName = stepName2;
        this.duration = duration2;
        this.code = code2;
    }
}
