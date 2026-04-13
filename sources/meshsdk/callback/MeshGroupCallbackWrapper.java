package meshsdk.callback;

public interface MeshGroupCallbackWrapper extends MeshGroupCallback {
    void onDegradeToLocalGroup(String str);
}
