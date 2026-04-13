package meshsdk.ctrl;

import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.core.message.NotificationMessage;
import com.telink.ble.mesh.core.message.config.ConfigStatus;
import com.telink.ble.mesh.core.message.scene.SceneDeleteMessage;
import com.telink.ble.mesh.core.message.scene.SceneRecallMessage;
import com.telink.ble.mesh.core.message.scene.SceneRegisterStatusMessage;
import com.telink.ble.mesh.core.message.scene.SceneStoreMessage;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.event.StatusNotificationEvent;
import com.telink.ble.mesh.util.MeshLogger;
import meshsdk.BaseResp;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshSceneCallback;
import meshsdk.model.MeshInfo;
import meshsdk.model.NodeInfo;
import meshsdk.model.Scene;

public class SceneCtrl extends CtrlLifecycle implements EventListener<String> {
    private int currentModelAddr;
    private NodeInfo deviceInfo;
    private MeshSceneCallback meshSceneCallback;
    private int opType;
    private Scene scene;

    public SceneCtrl(SIGMesh sigMesh) {
        super(sigMesh);
        onCreate();
    }

    public void onCreate() {
        MeshEventHandler.getInstance().addEventListener(SceneRegisterStatusMessage.class.getName(), this);
    }

    public void onDestroy() {
        MeshEventHandler.getInstance().removeEventListener(this);
    }

    public synchronized void addSceneAction(NodeInfo nodeInfo, Scene scene2, MeshSceneCallback meshSceneCallback2) {
        this.opType = 0;
        this.scene = scene2;
        this.deviceInfo = nodeInfo;
        this.meshSceneCallback = meshSceneCallback2;
        setNextAddress();
    }

    public synchronized void removeSceneAction(NodeInfo nodeInfo, Scene scene2, MeshSceneCallback meshSceneCallback2) {
        this.opType = 1;
        this.scene = scene2;
        this.deviceInfo = nodeInfo;
        this.meshSceneCallback = meshSceneCallback2;
        setNextAddress();
    }

    public synchronized void runScene(int id, byte fading) {
        SceneRecallMessage recallMessage = SceneRecallMessage.I(65535, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), id, false, 0);
        recallMessage.K(fading);
        recallMessage.J(true);
        MeshLog.i("执行meshScene");
        MeshMessagePool.getInstance().addAndSend(recallMessage);
    }

    public void performed(Event<String> event) {
        if (event.getType().equals(SceneRegisterStatusMessage.class.getName())) {
            NotificationMessage notificationMessage = ((StatusNotificationEvent) event).a();
            if (((SceneRegisterStatusMessage) notificationMessage.d()).c() == ConfigStatus.SUCCESS.code) {
                MeshInfo mesh = SIGMesh.getInstance().getMeshInfo();
                NodeInfo deviceInfo2 = mesh.getDeviceByMeshAddress(notificationMessage.c());
                if (deviceInfo2 == null) {
                    MeshSceneCallback meshSceneCallback2 = this.meshSceneCallback;
                    meshSceneCallback2.onFail(BaseResp.ERR_NODE_NOT_EXIST, "scene rule set fail:node info is null,src=" + notificationMessage.c(), this.scene, notificationMessage.c());
                    return;
                }
                if (this.opType == 0) {
                    this.scene.saveFromDeviceInfo(deviceInfo2);
                } else {
                    this.scene.removeByAddress(deviceInfo2.meshAddress);
                }
                mesh.saveScene(this.scene);
                mesh.saveOrUpdate(SIGMesh.getInstance().getContext(), "SceneCtrl.Performed    SceneRegisterStatusMessage");
                MeshSceneCallback meshSceneCallback3 = this.meshSceneCallback;
                Scene scene2 = this.scene;
                meshSceneCallback3.onSuccess(scene2.sceneId, scene2, deviceInfo2.meshAddress);
                return;
            }
            this.meshSceneCallback.onFail(421, "scene rule set fail, node reach to the max allocate", this.scene, this.deviceInfo.meshAddress);
        }
    }

    private void setNextAddress() {
        MeshMessage meshMessage;
        int targetEleAdr = this.deviceInfo.getTargetEleAdr(MeshSigModel.SIG_MD_SCENE_S.modelId);
        this.currentModelAddr = targetEleAdr;
        if (targetEleAdr == -1) {
            MeshLogger.d("scene save: device check fail");
            this.meshSceneCallback.onFail(401, "device check fail", this.scene, this.deviceInfo.meshAddress);
            return;
        }
        int appKeyIndex = SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex();
        if (this.opType == 0) {
            meshMessage = SceneStoreMessage.I(this.currentModelAddr, appKeyIndex, this.scene.id, true, 1);
        } else {
            meshMessage = SceneDeleteMessage.I(this.currentModelAddr, appKeyIndex, this.scene.id, true, 1);
        }
        MeshMessagePool.getInstance().addAndSend(meshMessage);
    }
}
