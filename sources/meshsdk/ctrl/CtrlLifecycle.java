package meshsdk.ctrl;

import java.util.HashMap;
import meshsdk.SIGMesh;
import meshsdk.callback.AbCommonCallback;
import meshsdk.callback.MeshCustomcmdCallback;

public abstract class CtrlLifecycle extends MeshCustomcmdCallback {
    private HashMap<String, AbCommonCallback> callbackHashMap = new HashMap<>();
    @Deprecated
    protected SIGMesh sigMesh;

    public abstract void onCreate();

    public CtrlLifecycle(SIGMesh sigMesh2) {
        this.sigMesh = sigMesh2;
    }

    public void onDestroy() {
        HashMap<String, AbCommonCallback> hashMap = this.callbackHashMap;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public void onSuccess(Object data) {
    }

    public void onFail(int code, String msg, Object data) {
    }
}
