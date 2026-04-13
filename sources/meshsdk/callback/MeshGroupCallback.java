package meshsdk.callback;

public interface MeshGroupCallback {
    void onFail(int i, String str, int i2, int i3, int i4);

    void onSuccess(int i, int i2, int i3);
}
