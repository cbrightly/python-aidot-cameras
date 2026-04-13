package meshsdk.callback;

public interface MeshUnbindCallback {
    void onUnBindFail(int i, String str);

    void onUnBindSuccess(String str, int i, boolean z);
}
