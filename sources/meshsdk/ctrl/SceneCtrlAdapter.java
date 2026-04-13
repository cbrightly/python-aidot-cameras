package meshsdk.ctrl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshSceneCallback;
import meshsdk.model.NodeInfo;
import meshsdk.model.Scene;

public class SceneCtrlAdapter extends MeshSceneCallback {
    private HashMap<String, MeshSceneCallback> callbackHashMap;
    private AtomicBoolean isWaiting;
    private final Object mLock = new Object();
    private Queue<SceneActionWrap> queue;
    private SceneCtrl sceneCtrl;

    public SceneCtrlAdapter(SIGMesh sigMesh) {
        super(sigMesh);
        this.sceneCtrl = new SceneCtrl(sigMesh);
        this.queue = new LinkedList();
        this.isWaiting = new AtomicBoolean(false);
        this.callbackHashMap = new HashMap<>();
    }

    public void onCreate() {
    }

    public void controlScene(int id, byte fading) {
        this.sceneCtrl.runScene(id, fading);
    }

    public void addSceneAction(NodeInfo node, Scene scene, MeshSceneCallback meshGroupCallback) {
        this.queue.add(new SceneActionWrap(node, scene, true));
        this.callbackHashMap.put(String.valueOf(node.meshAddress), meshGroupCallback);
        processSceneQueue();
    }

    public void removeSceneAction(NodeInfo node, Scene scene, MeshSceneCallback meshGroupCallback) {
        this.queue.add(new SceneActionWrap(node, scene, false));
        this.callbackHashMap.put(String.valueOf(node.meshAddress), meshGroupCallback);
        processSceneQueue();
    }

    private void processSceneQueue() {
        SceneActionWrap wrap;
        if (SIGMesh.getInstance().hasConnected()) {
            synchronized (this.mLock) {
                if (!this.isWaiting.get() && (wrap = this.queue.poll()) != null) {
                    this.isWaiting.compareAndSet(false, true);
                    if (wrap.isAdd) {
                        this.sceneCtrl.addSceneAction(wrap.nodeInfo, wrap.scene, this);
                    } else {
                        this.sceneCtrl.removeSceneAction(wrap.nodeInfo, wrap.scene, this);
                    }
                }
            }
        }
    }

    public void onSuccess(int sceneId, Scene scene, int meshAddr) {
        HashMap<String, MeshSceneCallback> hashMap = this.callbackHashMap;
        MeshSceneCallback callback = hashMap.get(meshAddr + "");
        if (callback != null) {
            callback.onSuccess(sceneId, scene, meshAddr);
            this.callbackHashMap.remove(callback);
        }
        this.isWaiting.set(false);
        processSceneQueue();
    }

    public void onFail(int code, String msg, Scene scene, int meshAddress) {
        HashMap<String, MeshSceneCallback> hashMap = this.callbackHashMap;
        MeshSceneCallback callback = hashMap.get(meshAddress + "");
        if (callback != null) {
            callback.onFail(code, msg, scene, meshAddress);
        }
        this.isWaiting.set(false);
        processSceneQueue();
    }

    public static class SceneActionWrap {
        public boolean isAdd;
        public NodeInfo nodeInfo;
        public Scene scene;

        public SceneActionWrap(NodeInfo nodeInfo2, Scene scene2, boolean isAdd2) {
            this.nodeInfo = nodeInfo2;
            this.isAdd = isAdd2;
            this.scene = scene2;
        }
    }
}
