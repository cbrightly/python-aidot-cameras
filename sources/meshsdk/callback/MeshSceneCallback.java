package meshsdk.callback;

import meshsdk.SIGMesh;
import meshsdk.ctrl.CtrlLifecycle;
import meshsdk.model.Scene;

public abstract class MeshSceneCallback extends CtrlLifecycle {
    private String callbackkey;

    public abstract void onFail(int i, String str, Scene scene, int i2);

    public abstract void onSuccess(int i, Scene scene, int i2);

    public MeshSceneCallback(SIGMesh sigMesh) {
        super(sigMesh);
    }

    public MeshSceneCallback(SIGMesh sigMesh, String callbackkey2) {
        super(sigMesh);
        this.callbackkey = callbackkey2;
    }

    public String getCallbackkey() {
        return this.callbackkey;
    }

    public void onCreate() {
    }
}
