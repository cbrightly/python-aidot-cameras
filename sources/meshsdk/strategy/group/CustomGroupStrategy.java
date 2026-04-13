package meshsdk.strategy.group;

import android.content.Context;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.serviceinterface.BleMeshService;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import meshsdk.BaseResp;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.callback.MeshGroupCallback;
import meshsdk.ctrl.EffectLinkageCtrlAdapter;
import meshsdk.ctrl.EffectModeCtrlAdapter;
import meshsdk.model.CustomGroup;
import meshsdk.model.MeshInfo;
import meshsdk.model.NodeInfo;
import meshsdk.model.json.CustomEffectMode;
import meshsdk.util.LDSMeshUtil;
import org.json.JSONObject;

public class CustomGroupStrategy extends GroupStrategy {
    private static final String TAG = "CustomGroupStrategy";

    public CustomGroupStrategy(Context mContext) {
        super(mContext);
    }

    public boolean isExist(int groupId) {
        return SIGMesh.getInstance().getMeshInfo().getCustomGroups().containsKey(String.valueOf(groupId));
    }

    public synchronized int addGroup(int groupId, String groupType) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        if (!isExist(groupId)) {
            CustomGroup customGroup = new CustomGroup(String.valueOf(groupId));
            meshInfo.getCustomGroups().put(String.valueOf(customGroup.getGroupId()), customGroup);
        }
        return groupId;
    }

    public synchronized JSONObject removeGroup(int groupId) {
        if (isExist(groupId)) {
            MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
            MeshLog.i("remove custom group :" + groupId);
            meshInfo.getCustomGroups().remove(String.valueOf(groupId));
            meshInfo.saveOrUpdate(this.mContext, "CustomerGroupStrategy.java removGroup");
            return BaseResp.generatorSuccessResp();
        }
        MeshLog.w("group not exist, remove group fail");
        return BaseResp.generatorFailResp(416, "Group is not exist");
    }

    public void addGroupMember(int groupId, String macAddress, MeshGroupCallback groupCallback) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        NodeInfo node = LDSMeshUtil.findMeshNode(meshInfo.nodes, macAddress);
        if (node != null) {
            CustomGroup customGroup = meshInfo.getCustomGroups().get(String.valueOf(groupId));
            if (customGroup == null) {
                customGroup = new CustomGroup(String.valueOf(groupId));
            }
            customGroup.addDevice(macAddress);
            meshInfo.getCustomGroups().put(customGroup.getGroupId(), customGroup);
            Context context = this.mContext;
            meshInfo.saveOrUpdate(context, "CustomGroupStrategy.AddCustomGroup groupId:" + groupId + ",mac:" + macAddress);
            if (groupCallback != null) {
                groupCallback.onSuccess(node.meshAddress, 0, 0);
            }
        } else if (groupCallback != null) {
            groupCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "Device node is not exist", -1, 0, 0);
        }
    }

    public void removeGroupMember(int groupId, String macAddress, MeshGroupCallback groupCallback) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        if (LDSMeshUtil.findMeshNode(meshInfo.nodes, macAddress) != null) {
            CustomGroup customGroup = meshInfo.getCustomGroups().get(String.valueOf(groupId));
            if (customGroup != null) {
                customGroup.removeDevice(macAddress);
                if (customGroup.getDevices().size() == 0) {
                    meshInfo.getCustomGroups().remove(customGroup.getGroupId());
                }
            }
            if (groupCallback != null) {
                groupCallback.onSuccess(0, 0, 0);
            }
        } else if (groupCallback != null) {
            groupCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "Device node is not exist", -1, 0, 0);
        }
    }

    public void removeGroupMember2(String macAddress, int groupAddress, MeshGroupCallback groupCallback) {
    }

    public JSONObject controlGroup(int groupId, int modelId, Object value) {
        CustomGroup customGroup = SIGMesh.getInstance().getMeshInfo().getCustomGroups().get(String.valueOf(groupId));
        if (customGroup == null) {
            return BaseResp.generatorFailResp(416, "custom group not exist");
        }
        queueControl(customGroup, groupId, modelId, value);
        return BaseResp.generatorSuccessResp();
    }

    private void queueControl(CustomGroup customGroup, int groupId, int modelId, Object value) {
        Iterator<String> it = customGroup.getDevices().iterator();
        while (it.hasNext()) {
            String mac = it.next();
            NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, mac);
            if (nodeInfo != null && nodeInfo.isOnline()) {
                SIGMesh.getInstance().controlDevice(mac, modelId, value);
            }
        }
    }

    public JSONObject allControl(int modelId, Object value) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        if (meshInfo.nodes.size() <= 0) {
            return null;
        }
        for (NodeInfo nodeInfo : meshInfo.nodes) {
            if (nodeInfo.getOnOff() != -1) {
                SIGMesh.getInstance().controlDevice(nodeInfo, modelId, value);
            }
        }
        return null;
    }

    public void setEffectMode(EffectModeCtrlAdapter ctrl, int effectId, long durationMill, int action, String groupId, MeshCustomcmdCallback customcmdCallback) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        CustomGroup customGroup = meshInfo.getCustomGroups().get(groupId);
        if (customGroup != null) {
            Iterator<String> it = customGroup.getDevices().iterator();
            while (it.hasNext()) {
                String mac = it.next();
                ctrl.setEffectMode(mac, effectId, durationMill, action, LDSMeshUtil.findMeshAddr(meshInfo.nodes, mac), customcmdCallback);
            }
        }
    }

    public void setCustomEffectMode(EffectModeCtrlAdapter ctrl, CustomEffectMode customEffectMode, MeshCustomcmdCallback customcmdCallback) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        CustomGroup customGroup = meshInfo.getCustomGroups().get(customEffectMode.groupId);
        if (customGroup != null) {
            Iterator<String> it = customGroup.getDevices().iterator();
            while (it.hasNext()) {
                String mac = it.next();
                ctrl.setCustomEffectMode(mac, customEffectMode, LDSMeshUtil.findMeshAddr(meshInfo.nodes, mac), customcmdCallback);
            }
        }
    }

    public void setLinkageMode(EffectLinkageCtrlAdapter ctrl, int action, int meshAddr, String groupId, MeshCustomcmdCallback customcmdCallback) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        CustomGroup customGroup = meshInfo.getCustomGroups().get(groupId);
        if (customGroup != null) {
            MeshLog.i("执行mesh本地组联动:" + groupId);
            Iterator<String> it = customGroup.getDevices().iterator();
            while (it.hasNext()) {
                String mac = it.next();
                ctrl.setLinkageMode(action, LDSMeshUtil.findMeshAddr(meshInfo.nodes, mac), 65534, customcmdCallback);
                ((BleMeshService) a.c().g(BleMeshService.class)).postDeviceStatusChange(mac, 4096, 1);
            }
        }
    }

    public void removeMemberByMac(String mac) {
        ConcurrentHashMap<String, CustomGroup> customGroups = SIGMesh.getInstance().getMeshInfo().getCustomGroups();
        if (customGroups.size() > 0) {
            for (CustomGroup group : customGroups.values()) {
                group.removeDevice(mac);
            }
        }
    }
}
