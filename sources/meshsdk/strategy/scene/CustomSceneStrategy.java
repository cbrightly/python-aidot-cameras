package meshsdk.strategy.scene;

import android.content.Context;
import com.google.gson.Gson;
import com.telink.ble.mesh.foundation.MeshService;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import meshsdk.BaseResp;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshSceneCallback;
import meshsdk.ctrl.CmdCtrl;
import meshsdk.model.CustomScene;
import meshsdk.model.MeshInfo;
import meshsdk.model.NodeInfo;
import meshsdk.model.Scene;
import meshsdk.model.SceneRulesWrap;
import meshsdk.util.LDSMeshUtil;
import org.json.JSONObject;

public class CustomSceneStrategy extends SceneStrategy {
    /* access modifiers changed from: private */
    public CmdCtrl cmdCtrl;

    public CustomSceneStrategy(Context context, CmdCtrl cmdCtrl2) {
        super(context);
        this.cmdCtrl = cmdCtrl2;
    }

    public int addScene(int sceneId) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        if (meshInfo.getCustomScenes().containsKey(String.valueOf(sceneId))) {
            return 0;
        }
        CustomScene customScene = new CustomScene(String.valueOf(sceneId));
        meshInfo.getCustomScenes().put(customScene.getSceneId(), customScene);
        return sceneId;
    }

    public JSONObject removeScene(int sceneId) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        if (!meshInfo.getCustomScenes().containsKey(String.valueOf(sceneId))) {
            return BaseResp.generatorFailResp(BaseResp.ERR_SCENE_NOT_EXIST, "Scene is not exist");
        }
        meshInfo.getCustomScenes().remove(String.valueOf(sceneId));
        meshInfo.saveOrUpdate(this.mContext, "CustomSceneStrategy.java removeScene");
        return BaseResp.generatorSuccessResp();
    }

    public void addSceneAction(int sceneId, String macAddress, List<CustomScene.SceneRule> ruleList, MeshSceneCallback sceneCallback) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        NodeInfo node = LDSMeshUtil.findMeshNode(meshInfo.nodes, macAddress);
        if (node != null || sceneCallback == null) {
            CustomScene customScene = meshInfo.getCustomScenes().get(String.valueOf(sceneId));
            if (customScene == null) {
                customScene = new CustomScene(String.valueOf(sceneId));
            }
            CustomScene.SceneDevice sceneDevice = new CustomScene.SceneDevice(macAddress);
            if (ruleList != null && ruleList.size() != 0) {
                for (CustomScene.SceneRule item : ruleList) {
                    sceneDevice.putRule(item);
                }
                customScene.putSceneDevice(sceneDevice);
                MeshLog.d(new Gson().toJson((Object) customScene));
                meshInfo.getCustomScenes().put(customScene.getSceneId(), customScene);
                meshInfo.saveOrUpdate(this.mContext, "CustomSceneStrategy.java addSceneAction");
                if (sceneCallback != null) {
                    sceneCallback.onSuccess(sceneId, (Scene) null, node.meshAddress);
                }
            } else if (sceneCallback != null) {
                sceneCallback.onFail(-1, "empty scene rule", (Scene) null, -1);
            }
        } else {
            MeshLog.w("LDSSceneApi nodes:" + LDSMeshUtil.printNodes(meshInfo.nodes) + "meshInfo:" + meshInfo.toString() + ",sigmesh#meshInfo:" + SIGMesh.getInstance().getMeshInfo().toString());
            sceneCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "Device node is not exist", (Scene) null, -1);
        }
    }

    public void removeSceneAction(int sceneId, String macAddress, MeshSceneCallback sceneCallback) {
        HashMap<String, CustomScene.SceneDevice> devices;
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        CustomScene customScene = meshInfo.getCustomScenes().get(String.valueOf(sceneId));
        if (!(customScene == null || (devices = customScene.getDevices()) == null || !devices.containsKey(macAddress))) {
            devices.remove(macAddress);
            meshInfo.saveOrUpdate(this.mContext, "CustomSceneStrategy.java removeSceneAction");
        }
        if (sceneCallback != null) {
            sceneCallback.onSuccess(sceneId, (Scene) null, 0);
        }
    }

    public JSONObject runScene(int sceneId, int fading) {
        return unqueueRunScene(sceneId);
    }

    public boolean isExist(int sceneId) {
        return SIGMesh.getInstance().getMeshInfo().getCustomScenes().get(String.valueOf(sceneId)) != null;
    }

    public boolean hasRules(int sceneId, String mac) {
        return true;
    }

    public JSONObject unqueueRunScene(final int sceneId) {
        final MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        final CustomScene customScene = meshInfo.getCustomScenes().get(String.valueOf(sceneId));
        if (customScene == null) {
            return BaseResp.generatorFailResp(200, "Local Scene is not exist");
        }
        SIGMesh.getInstance().executorTask(new Runnable() {
            public void run() {
                MeshService.k().u(false);
                HashMap<String, CustomScene.SceneDevice> devices = customScene.getDevices();
                if (devices != null && devices.size() > 0) {
                    for (CustomScene.SceneDevice next : devices.values()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String mac = next.mac;
                        SceneRulesWrap rulesWrap = new SceneRulesWrap(next.getRules());
                        NodeInfo node = LDSMeshUtil.findMeshNode(meshInfo.nodes, mac);
                        if (node != null) {
                            MeshLog.i("执行本地Scene mac:" + node.macAddress);
                            CustomSceneStrategy.this.cmdCtrl.customSceneCtrl(node, rulesWrap, sceneId);
                        }
                    }
                }
                MeshService.k().u(true);
            }
        });
        return BaseResp.generatorSuccessResp();
    }

    public void removeMemberByMac(String mac) {
        ConcurrentHashMap<String, CustomScene> customScenes = SIGMesh.getInstance().getMeshInfo().getCustomScenes();
        if (customScenes.size() > 0) {
            for (CustomScene scene : customScenes.values()) {
                if (scene.getDevices() != null && scene.getDevices().size() > 0) {
                    scene.getDevices().remove(mac);
                }
            }
        }
    }
}
