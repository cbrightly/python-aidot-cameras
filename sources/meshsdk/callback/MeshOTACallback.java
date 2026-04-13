package meshsdk.callback;

public interface MeshOTACallback {
    void onFail(int i, String str, String str2);

    void onProgress(int i, String str, int i2);

    void onStage(int i);

    void onSuccess(String str);
}
