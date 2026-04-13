package meshsdk.strategy.group;

import android.content.Context;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.serviceinterface.BleMeshService;
import java.util.List;
import meshsdk.BaseResp;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.callback.MeshGroupCallback;
import meshsdk.ctrl.EffectLinkageCtrlAdapter;
import meshsdk.ctrl.EffectModeCtrlAdapter;
import meshsdk.ctrl.GroupCtrlAdapter;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.GroupInfo;
import meshsdk.model.MeshInfo;
import meshsdk.model.NodeInfo;
import meshsdk.model.json.CustomEffectMode;
import meshsdk.util.LDSMeshUtil;
import org.json.JSONObject;

public class MeshGroupStrategy extends GroupStrategy {
    private static final String TAG = "MeshGroupStrategy";
    private GroupCtrlAdapter groupCtrlAdapter;

    public MeshGroupStrategy(Context mContext, GroupCtrlAdapter groupCtrlAdapter2) {
        super(mContext);
        this.groupCtrlAdapter = groupCtrlAdapter2;
    }

    public boolean isExist(int groupId) {
        return LDSMeshUtil.findGroup(SIGMesh.getInstance().getMeshInfo().groups, groupId) != null;
    }

    public synchronized int addGroup(int groupId, String groupType) {
        if (isExist(groupId)) {
            MeshLogNew.i("addGroup groupId:" + groupId + " 已存在");
            return groupId;
        }
        MeshDataManager.flagAddGroup = true;
        MeshLogNew.i("addGroup flagAddGroup true");
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        int newGroupAddr = LDSMeshUtil.createNewGroupAddr();
        if (newGroupAddr == -1) {
            MeshDataManager.flagAddGroup = false;
            MeshLogNew.i("addGroup flagAddGroup false");
            return -1;
        }
        GroupInfo info = new GroupInfo();
        info.address = newGroupAddr;
        info.groupId = groupId;
        info.name = "group" + groupId;
        info.groupType = groupType;
        meshInfo.groups.add(info);
        meshInfo.saveOrUpdate(this.mContext, "MeshGroupStrategy.java addGroup");
        return newGroupAddr;
    }

    public synchronized JSONObject removeGroup(int groupId) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        GroupInfo group = LDSMeshUtil.findGroup(meshInfo.groups, groupId);
        if (group == null) {
            MeshLog.w("MeshGroupStrategy LDSGroupAPi group:" + groupId + " not exist, remove group fail");
            return BaseResp.generatorFailResp(416, "Group:" + groupId + " is not exist");
        }
        List<NodeInfo> list = LDSMeshUtil.getDevicesInGroup(group.address);
        if (list == null || list.size() <= 0) {
            MeshLog.w("MeshGroupStrategy remove group :" + group.toString() + " success ");
            meshInfo.groups.remove(group);
            meshInfo.saveOrUpdate(this.mContext, "MeshGroupStrategy.java removeGroup");
            return BaseResp.generatorSuccessResp();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (NodeInfo nodeInfo : list) {
            stringBuilder.append(nodeInfo.macAddress + ",");
        }
        MeshLog.w("MeshGroupStrategy LDSGroupAPi Group member is not empty : " + stringBuilder.toString() + ",remove group fail");
        return BaseResp.generatorFailResp(BaseResp.ERR_GROUP_NOT_EMPTY, "Group member is not empty");
    }

    public synchronized void addGroupMember(int groupId, String macAddress, MeshGroupCallback groupCallback) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        GroupInfo group = LDSMeshUtil.findGroup(meshInfo.groups, groupId);
        NodeInfo node = LDSMeshUtil.findMeshNode(meshInfo.nodes, macAddress);
        if (group == null) {
            MeshLog.e("这边加组成员时，发现meshjson中没有这个组，丢了，尝试先去补组");
            int createGroupAddr = addGroup(groupId, GroupInfo.TYPE_NORMAL);
            group = LDSMeshUtil.findGroup(meshInfo.groups, groupId);
            if (group == null) {
                groupCallback.onFail(416, "addGroupMember Group is not exist, try to createGroupAddr=" + createGroupAddr, -1, 0, 0);
                return;
            }
        }
        if (node == null) {
            groupCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "addGroupMember Device node is not exist", -1, 0, 0);
        } else {
            this.groupCtrlAdapter.addGroupMember(node, group.address, node.meshAddress, groupCallback);
        }
    }

    public synchronized void removeGroupMember(int groupId, String macAddress, MeshGroupCallback groupCallback) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        GroupInfo group = LDSMeshUtil.findGroup(meshInfo.groups, groupId);
        NodeInfo node = LDSMeshUtil.findMeshNode(meshInfo.nodes, macAddress);
        if (group == null) {
            groupCallback.onFail(416, "removeGroupMember Group is not exist", -1, 0, 0);
        } else if (node == null) {
            groupCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "removeGroupMember Device node is not exist", -1, 0, 0);
        } else {
            this.groupCtrlAdapter.removeGroupMember(node, group.address, node.meshAddress, groupCallback);
        }
    }

    public synchronized void removeGroupMember2(String macAddress, int groupAddress, MeshGroupCallback groupCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, macAddress);
        if (node == null) {
            groupCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "Device node is not exist", -1, 0, 0);
        } else {
            this.groupCtrlAdapter.removeGroupMember(node, groupAddress, node.meshAddress, groupCallback);
        }
    }

    public JSONObject controlGroup(int groupId, int modelId, Object value) {
        GroupInfo group = LDSMeshUtil.findGroup(SIGMesh.getInstance().getMeshInfo().groups, groupId);
        if (group == null) {
            return BaseResp.generatorFailResp(416, "Group is not exist");
        }
        this.groupCtrlAdapter.controlGroup(group.address, modelId, value, groupId);
        return BaseResp.generatorSuccessResp();
    }

    public JSONObject allControl(int modelId, Object value) {
        this.groupCtrlAdapter.controlGroup(65535, modelId, value, 0);
        return BaseResp.generatorSuccessResp();
    }

    public void setEffectMode(EffectModeCtrlAdapter ctrl, int effectId, long durationMill, int action, String groupId, MeshCustomcmdCallback customcmdCallback) {
        GroupInfo group = LDSMeshUtil.findGroup(SIGMesh.getInstance().getMeshInfo().groups, Integer.parseInt(groupId));
        if (group != null) {
            ctrl.setEffectMode("", effectId, durationMill, action, group.address, customcmdCallback);
        }
    }

    public void setCustomEffectMode(EffectModeCtrlAdapter ctrl, CustomEffectMode customEffectMode, MeshCustomcmdCallback customcmdCallback) {
        GroupInfo group = LDSMeshUtil.findGroup(SIGMesh.getInstance().getMeshInfo().groups, Integer.parseInt(customEffectMode.groupId));
        if (group != null) {
            ctrl.setCustomEffectMode("", customEffectMode, group.address, customcmdCallback);
        }
    }

    public void setLinkageMode(EffectLinkageCtrlAdapter ctrl, int action, int meshAddr, String groupId, MeshCustomcmdCallback customcmdCallback) {
        GroupInfo groupInfo = LDSMeshUtil.findGroup(SIGMesh.getInstance().getMeshInfo().groups, Integer.parseInt(groupId));
        if (groupInfo != null) {
            MeshLog.i("执行mesh标准组联动:" + groupId);
            ctrl.setLinkageMode(action, meshAddr, groupInfo.address, customcmdCallback);
            List<NodeInfo> nodeList = LDSMeshUtil.getDevicesInGroup(groupInfo.address);
            if (nodeList != null && nodeList.size() > 0) {
                for (NodeInfo info : nodeList) {
                    ((BleMeshService) a.c().g(BleMeshService.class)).postDeviceStatusChange(info.macAddress, 4096, 1);
                }
            }
        }
    }
}
