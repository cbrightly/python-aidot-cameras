package meshsdk.model;

import android.content.Context;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.elkstrays.a;
import com.leedarson.serviceimpl.reporters.k;
import com.meituan.robust.Constants;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.foundation.MeshConfiguration;
import com.telink.ble.mesh.util.MeshLogger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import meshsdk.ConfigUtil;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.model.json.AddressRange;
import meshsdk.model.json.MeshStorage;
import meshsdk.sql.MeshDictBean;
import meshsdk.sql.SqlManager;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.SharedPreferenceHelper;
import org.json.JSONArray;
import org.json.JSONException;

public class MeshInfo implements Serializable, Cloneable {
    public static final String FILE_NAME = "com.leedarson.ble.mesh.STORAGE";
    private static final long serialVersionUID = -3986640688241304179L;
    public int addressTopLimit = 255;
    public List<MeshAppKey> appKeyList = new ArrayList();
    private ConcurrentHashMap<String, CustomGroup> customGroups = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, CustomScene> customScenes = new ConcurrentHashMap<>();
    public List<GroupInfo> groups = new ArrayList();
    public int ivIndex;
    public int localAddress;
    public List<MeshNetKey> meshNetKeyList = new ArrayList();
    public String meshUUID;
    public List<NodeInfo> nodes = new ArrayList();
    public List<OOBPair> oobPairs = new ArrayList();
    private int provisionIndex = 1;
    public List<MeshStorage.Provisioner> provisionerList = new ArrayList();
    public String provisionerUUID;
    public List<Scene> scenes = new ArrayList();
    public int sequenceNumber;
    public List<AddressRange> unicastRange = new ArrayList();

    public MeshNetKey getDefaultNetKey() {
        return this.meshNetKeyList.get(0);
    }

    public int getDefaultAppKeyIndex() {
        if (this.appKeyList.size() == 0) {
            return 0;
        }
        return this.appKeyList.get(0).index;
    }

    public NodeInfo getDeviceByMeshAddress(int meshAddress) {
        List<NodeInfo> list = this.nodes;
        if (list == null) {
            return null;
        }
        for (NodeInfo info : list) {
            if (info.meshAddress == meshAddress) {
                return info;
            }
        }
        return null;
    }

    public NodeInfo getDeviceByUUID(@NonNull byte[] deviceUUID, String mac) {
        for (NodeInfo info : this.nodes) {
            if (e.d(deviceUUID, info.deviceUUID)) {
                return info;
            }
            if (mac != null && mac.equals(info.macAddress)) {
                return info;
            }
        }
        return null;
    }

    public void insertDevice(NodeInfo deviceInfo) {
        if (getDeviceByUUID(deviceInfo.deviceUUID, deviceInfo.macAddress) != null) {
            removeDeviceByUUID(deviceInfo.deviceUUID, deviceInfo.macAddress);
        }
        this.nodes.add(deviceInfo);
    }

    public boolean removeDeviceByMeshAddress(int address) {
        List<NodeInfo> list = this.nodes;
        if (list == null || list.size() == 0) {
            return false;
        }
        for (Scene scene : this.scenes) {
            scene.removeByAddress(address);
        }
        Iterator<NodeInfo> iterator = this.nodes.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().meshAddress == address) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean removeDeviceByMac(String mac) {
        List<NodeInfo> list = this.nodes;
        if (list == null || list.size() == 0) {
            return false;
        }
        Iterator<NodeInfo> iterator = this.nodes.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().macAddress.equals(mac)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean removeDeviceByUUID(byte[] deviceUUID, String mac) {
        List<NodeInfo> list = this.nodes;
        if (list == null || list.size() == 0) {
            return false;
        }
        Iterator<NodeInfo> iterator = this.nodes.iterator();
        while (iterator.hasNext()) {
            NodeInfo deviceInfo = iterator.next();
            if (e.d(deviceUUID, deviceInfo.deviceUUID) || (mac != null && mac.equals(deviceInfo.macAddress))) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public int getOnlineCountInAll() {
        List<NodeInfo> list = this.nodes;
        if (list == null || list.size() == 0) {
            return 0;
        }
        int result = 0;
        for (NodeInfo device : this.nodes) {
            if (device.getOnOff() != -1) {
                result++;
            }
        }
        return result;
    }

    public int getOnlineCountInGroup(int groupAddress) {
        List<NodeInfo> list = this.nodes;
        if (list == null || list.size() == 0) {
            return 0;
        }
        int result = 0;
        for (NodeInfo device : this.nodes) {
            if (device.getOnOff() != -1) {
                Iterator<Integer> it = device.subList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().intValue() == groupAddress) {
                            result++;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return result;
    }

    public void saveScene(Scene scene) {
        for (Scene local : this.scenes) {
            if (local.id == scene.id) {
                local.states = scene.states;
                return;
            }
        }
        this.scenes.add(scene);
    }

    public Scene getSceneById(int id) {
        for (Scene scene : this.scenes) {
            if (id == scene.id) {
                return scene;
            }
        }
        return null;
    }

    public Scene getSceneByCloudSceneId(int id) {
        for (Scene scene : this.scenes) {
            if (id == scene.sceneId) {
                return scene;
            }
        }
        return null;
    }

    public int allocSceneId() {
        if (this.scenes.size() == 0) {
            return 1;
        }
        List<Scene> list = this.scenes;
        int id = list.get(list.size() - 1).id;
        if (id == 65535) {
            return -1;
        }
        return id + 1;
    }

    public byte[] getOOBByDeviceUUID(byte[] deviceUUID) {
        for (OOBPair pair : this.oobPairs) {
            if (e.d(pair.deviceUUID, deviceUUID)) {
                return pair.oob;
            }
        }
        return null;
    }

    public synchronized boolean saveOrUpdate(Context context, String referceBz) {
        boolean isSucess;
        FutureTask<Boolean> ft = new FutureTask<>(new WriteFileTask(this, context));
        l.m.execute(ft);
        isSucess = false;
        HashMap<String, Object> receiveUpdateByMqtt = new HashMap<>();
        Gson gson = new Gson();
        receiveUpdateByMqtt.put("cause", "INVOKE_TO_SAVE_MESH_INFO");
        receiveUpdateByMqtt.put("referceBz", referceBz);
        try {
            isSucess = ft.get().booleanValue();
            StringBuilder sb = new StringBuilder();
            sb.append("保存MeshInfo对象到持久化文件:");
            sb.append(isSucess ? "成功" : "【失败】");
            MeshLog.debugInfo(sb.toString());
            receiveUpdateByMqtt.put("messageContent", "Update Mesh Config Success   \n" + gson.toJson((Object) SIGMesh.getInstance().getMeshInfo()));
            a.a(receiveUpdateByMqtt);
            if (!isSucess) {
                k.a("更新本地持久化mesh信息【失败】, referceBz:" + referceBz + ",json:" + gson.toJson((Object) this));
            } else {
                k.a("更新本地持久化mesh信息成功, referceBz:" + referceBz + ",json:" + gson.toJson((Object) this));
            }
        } catch (Exception e) {
            try {
                receiveUpdateByMqtt.put("messageContent", "Update Mesh Config  Fail && Refer is " + referceBz + "\n" + gson.toJson((Object) SIGMesh.getInstance().getMeshInfo()) + "\n exception=" + e.toString());
                a.a(receiveUpdateByMqtt);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("更新本地持久化mesh信息【异常】,referceBz:");
                sb2.append(referceBz);
                sb2.append(",exception:");
                sb2.append(e.getMessage());
                k.a(sb2.toString());
            } catch (Exception exception) {
                MeshLog.e("saveOrUpdate report exception:" + exception.getMessage());
            }
        }
        return isSucess;
    }

    public static class WriteFileTask implements Callable<Boolean> {
        private Context ctx;
        private MeshInfo meshInfo;

        public WriteFileTask(MeshInfo meshInfo2, Context ctx2) {
            this.meshInfo = meshInfo2;
            this.ctx = ctx2;
        }

        public Boolean call() {
            String houseId = SharedPreferenceHelper.getHouseId(this.ctx);
            k.b("写入持久化meshinfo:" + this.meshInfo.toString());
            return Boolean.valueOf(ConfigUtil.writeAsObject(this.ctx, houseId, this.meshInfo));
        }
    }

    public void saveLocalUUIDAddress(Context context, int state) {
        MeshDictBean bean = SqlManager.findDictInfoByUUID(this.meshUUID);
        if (bean == null) {
            bean = new MeshDictBean();
            bean.setState(state);
        } else if (bean.getState() != 1) {
            bean.setState(state);
        }
        bean.setMeshUUID(this.meshUUID);
        bean.setSeqNum(this.sequenceNumber);
        bean.setLocalAddr(this.localAddress);
        bean.setProvisionerUUID(this.provisionerUUID);
        SqlManager.insertOrReplace(bean);
    }

    public String toString() {
        List<MeshAppKey> list = this.appKeyList;
        String appKey = (list == null || list.size() <= 0) ? "" : e.a(this.appKeyList.get(0).key);
        return "MeshInfo{\nAppkey" + appKey + "\nmeshUUID=" + this.meshUUID + "\n,nodes=" + getNodeStr() + ", ivIndex=" + Integer.toHexString(this.ivIndex) + ", sequenceNumber=" + this.sequenceNumber + ", localAddress=" + this.localAddress + ", provisionIndex=" + this.provisionIndex + ", scenes=" + this.scenes.size() + ", groups=" + this.groups + ", customgroups=" + this.customGroups + ", netKey=" + getNetKeyStr() + '}';
    }

    private String getNodeStr() {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.ARRAY_TYPE);
        for (NodeInfo node : this.nodes) {
            sb.append("addr:");
            sb.append(node.meshAddress);
            sb.append(",mac:");
            sb.append(node.macAddress);
            sb.append(",sublist:");
            sb.append(node.subList);
            sb.append(",elementCnt:");
            sb.append(node.elementCnt);
            sb.append(Constants.PACKNAME_END);
        }
        sb.append("]");
        sb.append("\n");
        return sb.toString();
    }

    public List getNodeMacs() {
        List<String> macs = new ArrayList<>();
        for (NodeInfo nodeInfo : this.nodes) {
            macs.add(nodeInfo.macAddress);
        }
        return macs;
    }

    public List getDeletedMacs() {
        List<String> deletedMacs = new ArrayList<>();
        for (NodeInfo nodeInfo : this.nodes) {
            String macAddress = nodeInfo.macAddress;
            if (SqlManager.delNodesContains(macAddress)) {
                deletedMacs.add(macAddress);
            }
        }
        return deletedMacs;
    }

    public String getNetKeyStr() {
        StringBuilder strBuilder = new StringBuilder();
        for (MeshNetKey meshNetKey : this.meshNetKeyList) {
            strBuilder.append("\nindex: ");
            strBuilder.append(meshNetKey.index);
            strBuilder.append(" -- ");
            strBuilder.append("key: ");
            strBuilder.append(e.a(meshNetKey.key));
        }
        return strBuilder.toString();
    }

    public String getAppKeyStr() {
        StringBuilder strBuilder = new StringBuilder();
        for (MeshAppKey meshNetKey : this.appKeyList) {
            strBuilder.append("\nindex: ");
            strBuilder.append(meshNetKey.index);
            strBuilder.append(" -- ");
            strBuilder.append("key: ");
            strBuilder.append(e.a(meshNetKey.key));
        }
        return strBuilder.toString();
    }

    public int getProvisionIndex() {
        return this.provisionIndex;
    }

    public void increaseProvisionIndex(int addition) {
        int i = this.provisionIndex + addition;
        this.provisionIndex = i;
        if (i > this.addressTopLimit) {
            MeshLogger.a("");
            int low = this.addressTopLimit + 1;
            int high = low + 1023;
            this.unicastRange.add(new AddressRange(low, high));
            this.addressTopLimit = high;
        }
    }

    public void resetProvisionIndex(int index) {
        this.provisionIndex = index;
    }

    public Object clone() {
        return super.clone();
    }

    public MeshConfiguration convertToConfiguration() {
        MeshConfiguration meshConfiguration = new MeshConfiguration();
        meshConfiguration.g = new SparseArray<>();
        List<NodeInfo> list = this.nodes;
        if (list != null) {
            for (NodeInfo node : list) {
                meshConfiguration.g.put(node.meshAddress, node.deviceKey);
            }
        }
        MeshNetKey netKey = getDefaultNetKey();
        meshConfiguration.a = netKey.index;
        meshConfiguration.b = netKey.key;
        meshConfiguration.c = new SparseArray<>();
        List<MeshAppKey> list2 = this.appKeyList;
        if (list2 != null) {
            for (MeshAppKey appKey : list2) {
                meshConfiguration.c.put(appKey.index, appKey.key);
            }
        }
        meshConfiguration.d = this.ivIndex;
        meshConfiguration.e = this.sequenceNumber;
        meshConfiguration.f = this.localAddress;
        MeshLog.d("setupMeshConfiguration convertToConfiguration localAddress:" + this.localAddress + "(" + String.format("0x%04X", new Object[]{Integer.valueOf(this.localAddress)}) + ")");
        return meshConfiguration;
    }

    public static MeshInfo createNewMesh(Context context) {
        MeshInfo meshInfo = new MeshInfo();
        meshInfo.meshUUID = e.b(MeshUtils.g(16), "").toUpperCase();
        meshInfo.meshNetKeyList = new ArrayList();
        String[] NET_KEY_NAMES = {"Default Net Key", "Sub Net Key 1", "Sub Net Key 2"};
        String[] APP_KEY_NAMES = {"Default App Key", "Sub App Key 1", "Sub App Key 2"};
        byte[] APP_KEY_VAL = MeshUtils.g(16);
        for (int i = 0; i < 3; i++) {
            meshInfo.meshNetKeyList.add(new MeshNetKey(NET_KEY_NAMES[i], i, MeshUtils.g(16)));
            meshInfo.appKeyList.add(new MeshAppKey(APP_KEY_NAMES[i], i, APP_KEY_VAL, i));
        }
        meshInfo.ivIndex = 0;
        meshInfo.sequenceNumber = 0;
        meshInfo.nodes = new ArrayList();
        int localAddress2 = SqlManager.getLocalAddress(meshInfo.meshUUID);
        meshInfo.localAddress = localAddress2;
        meshInfo.provisionIndex = localAddress2 + 1;
        meshInfo.provisionerUUID = SqlManager.getLocalProvisionerUUID(meshInfo.meshUUID);
        meshInfo.groups = new ArrayList();
        ArrayList arrayList = new ArrayList();
        meshInfo.unicastRange = arrayList;
        int i2 = meshInfo.localAddress;
        arrayList.add(new AddressRange(i2, (i2 + 1024) - 1));
        meshInfo.addressTopLimit = 1024;
        return meshInfo;
    }

    public ConcurrentHashMap<String, CustomScene> getCustomScenes() {
        if (this.customScenes == null) {
            this.customScenes = new ConcurrentHashMap<>();
        }
        return this.customScenes;
    }

    public JSONArray customScenesToJSONArray() {
        JSONArray arr = new JSONArray();
        for (CustomScene next : this.customScenes.values()) {
            try {
                arr.put((Object) next.toJSON());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

    public ConcurrentHashMap<String, CustomGroup> getCustomGroups() {
        if (this.customGroups == null) {
            this.customGroups = new ConcurrentHashMap<>();
        }
        return this.customGroups;
    }

    public int getNewNodeAddress(String mac) {
        int addr = LDSMeshUtil.createAddrByMac(mac);
        if (LDSMeshUtil.hasExist(addr) == null) {
            return addr;
        }
        int provisionIndex2 = getProvisionIndex();
        MeshLogNew.i("11111-:" + addr + " 在已有meshjson中存在,返回provisinIndex:" + provisionIndex2);
        return provisionIndex2;
    }
}
