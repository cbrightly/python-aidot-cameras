package meshsdk.callback;

public abstract class MeshCustomcmdCallback {
    private String callbackKey = "";

    public abstract void onFail(int i, String str, Object obj);

    public abstract void onSuccess(Object obj);

    public MeshCustomcmdCallback(String callbackKey2) {
        this.callbackKey = callbackKey2;
    }

    public MeshCustomcmdCallback() {
    }

    public void setCallbackKey(String callbackKey2) {
        this.callbackKey = callbackKey2;
    }

    public String getCallbackKey() {
        return this.callbackKey;
    }
}
