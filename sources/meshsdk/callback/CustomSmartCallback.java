package meshsdk.callback;

import meshsdk.SIGMesh;
import meshsdk.ctrl.CtrlLifecycle;

public abstract class CustomSmartCallback extends CtrlLifecycle {
    private int meshSmartId;

    public abstract void onSmartFail(int i, String str, Object obj);

    public abstract void onSmartSuccess(int i);

    public CustomSmartCallback(SIGMesh sigMesh) {
        super(sigMesh);
    }

    public int getMeshSmartId() {
        return this.meshSmartId;
    }

    public void setMeshSmartId(int meshSmartId2) {
        this.meshSmartId = meshSmartId2;
    }

    public void onSuccess(Object data) {
        onSmartSuccess(this.meshSmartId);
    }

    public void onFail(int code, String msg, Object data) {
        onSmartFail(code, msg, data);
    }
}
