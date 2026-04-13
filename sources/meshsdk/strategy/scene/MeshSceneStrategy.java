package meshsdk.strategy.scene;

import android.content.Context;
import java.util.List;
import meshsdk.BaseResp;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshSceneCallback;
import meshsdk.ctrl.SceneCtrlAdapter;
import meshsdk.model.CustomScene;
import meshsdk.model.MeshInfo;
import meshsdk.model.NodeInfo;
import meshsdk.model.Scene;
import meshsdk.model.json.RoutineRule;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.UnitConvert;
import org.json.JSONObject;

public class MeshSceneStrategy extends SceneStrategy {
    private SceneCtrlAdapter sceneCtrl;

    public MeshSceneStrategy(Context context, SceneCtrlAdapter sceneCtrl2) {
        super(context);
        this.sceneCtrl = sceneCtrl2;
    }

    public int addScene(int sceneId) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        if (LDSMeshUtil.findScene(meshInfo.scenes, sceneId) != null) {
            return sceneId;
        }
        int localId = meshInfo.allocSceneId();
        if (localId == -1) {
            return -1;
        }
        Scene info = new Scene();
        info.id = localId;
        info.sceneId = sceneId;
        info.name = RoutineRule.THEN_TYPE_SCENE + sceneId;
        meshInfo.scenes.add(info);
        meshInfo.saveOrUpdate(this.mContext, "MeshSceneStrategy.java addScene");
        return localId;
    }

    public JSONObject removeScene(int sceneId) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        Scene scene = LDSMeshUtil.findScene(meshInfo.scenes, sceneId);
        if (scene == null) {
            return BaseResp.generatorFailResp(BaseResp.ERR_SCENE_NOT_EXIST, "Scene is not exist");
        }
        List<Scene.SceneState> list = scene.states;
        if (list != null && list.size() > 0) {
            return BaseResp.generatorFailResp(420, "Scene member is not empty");
        }
        meshInfo.scenes.remove(scene);
        Context context = this.mContext;
        meshInfo.saveOrUpdate(context, "MeshSceneStrategy.java removeScene scendId=" + sceneId);
        return BaseResp.generatorSuccessResp();
    }

    public void addSceneAction(int sceneId, String macAddress, List<CustomScene.SceneRule> list, MeshSceneCallback sceneCallback) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        NodeInfo node = LDSMeshUtil.findMeshNode(meshInfo.nodes, macAddress);
        Scene scene = meshInfo.getSceneByCloudSceneId(sceneId);
        if (scene == null) {
            sceneCallback.onFail(BaseResp.ERR_SCENE_NOT_EXIST, "Scene is not exist", scene, node.meshAddress);
        } else if (node == null) {
            sceneCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "Device node is not exist", scene, -1);
        } else {
            this.sceneCtrl.addSceneAction(node, scene, sceneCallback);
        }
    }

    public void removeSceneAction(int sceneId, String macAddress, MeshSceneCallback sceneCallback) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        NodeInfo node = LDSMeshUtil.findMeshNode(meshInfo.nodes, macAddress);
        Scene scene = meshInfo.getSceneByCloudSceneId(sceneId);
        if (scene == null) {
            sceneCallback.onFail(BaseResp.ERR_SCENE_NOT_EXIST, "Scene is not exist", scene, node.meshAddress);
        } else if (node == null) {
            sceneCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "Device node is not exist", scene, -1);
        } else {
            this.sceneCtrl.removeSceneAction(node, scene, sceneCallback);
        }
    }

    public JSONObject runScene(int sceneId, int fading) {
        Scene scene = SIGMesh.getInstance().getMeshInfo().getSceneByCloudSceneId(sceneId);
        if (scene == null) {
            return BaseResp.generatorFailResp(BaseResp.ERR_SCENE_NOT_EXIST, "Scene is not exist");
        }
        if (fading > 37800000 || fading < 0) {
            return BaseResp.generatorFailResp(BaseResp.ERR_SCENE_NOT_EXIST, "fading param error");
        }
        this.sceneCtrl.controlScene(scene.id, UnitConvert.fading2Transition(fading));
        return BaseResp.generatorSuccessResp();
    }

    public boolean isExist(int sceneId) {
        return SIGMesh.getInstance().getMeshInfo().getSceneByCloudSceneId(sceneId) != null;
    }

    public boolean hasRules(int sceneId, String mac) {
        List<Scene.SceneState> list;
        List<Scene.SceneState> list2;
        Scene scene = SIGMesh.getInstance().getMeshInfo().getSceneByCloudSceneId(sceneId);
        if (mac != null) {
            if (!(scene == null || (list2 = scene.states) == null)) {
                for (Scene.SceneState state : list2) {
                    if (mac.equals(state.macAddress)) {
                        return true;
                    }
                }
            }
            return false;
        } else if (scene == null || (list = scene.states) == null || list.size() <= 0) {
            return false;
        } else {
            return true;
        }
    }
}
