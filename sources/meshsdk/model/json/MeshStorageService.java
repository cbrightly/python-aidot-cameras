package meshsdk.model.json;

import android.os.Build;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.reporters.k;
import com.leedarson.serviceimpl.strategys.g;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.entity.CompositionData;
import com.telink.ble.mesh.entity.Scheduler;
import com.telink.ble.mesh.entity.TransitionTime;
import com.telink.ble.mesh.util.FileSystem;
import com.telink.ble.mesh.util.MeshLogger;
import java.io.File;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.model.CustomGroup;
import meshsdk.model.CustomScene;
import meshsdk.model.GroupInfo;
import meshsdk.model.MeshAppKey;
import meshsdk.model.MeshInfo;
import meshsdk.model.MeshNetKey;
import meshsdk.model.NodeInfo;
import meshsdk.model.PublishModel;
import meshsdk.model.Scene;
import meshsdk.model.json.CustomSceneStorage;
import meshsdk.model.json.MeshStorage;
import meshsdk.sql.MeshDictBean;
import meshsdk.sql.SqlManager;
import meshsdk.util.LDSMeshUtil;
import org.glassfish.grizzly.http.server.util.MappingData;

public class MeshStorageService {
    public static final String JSON_FILE = "mesh.json";
    private static final byte[] VC_TOOL_CPS = {0, 0, 1, 1, 51, 49, -24, 3, 4, 0, 0, 0, 23, 1, 0, 0, 1, 0, 2, 0, 3, 0, 5, 0, 0, -2, 1, -2, 2, -2, 3, -2, 0, -1, 1, -1, 2, 18, 1, MappingData.PATH, 3, MappingData.PATH, 5, MappingData.PATH, 8, MappingData.PATH, 5, 18, 8, 18, 2, 19, 5, 19, 9, 19, 17, 19, 21, MappingData.PATH, 17, 2, 1, 0};
    private static MeshStorageService instance = new MeshStorageService();
    public MeshStorage.Node localNode;
    private Gson mGson = new GsonBuilder().setPrettyPrinting().create();

    private MeshStorageService() {
    }

    public static MeshStorageService getInstance() {
        return instance;
    }

    public MeshStorage.Node getLocalNode() {
        return this.localNode;
    }

    public MeshInfo importExternal(String jsonStr, MeshInfo localMesh) {
        MeshStorage tempStorage = (MeshStorage) this.mGson.fromJson(jsonStr, MeshStorage.class);
        if (tempStorage == null) {
            k.a("导入meshjson 转换为MeshStorage【失败】,meshjson:" + jsonStr);
        }
        if (!validStorageData(tempStorage)) {
            k.a("导入meshjson 校验MeshStorage【失败】,provisioners 为空, meshjson:" + jsonStr);
            return null;
        }
        MeshInfo tmpMesh = (MeshInfo) localMesh.clone();
        if (!updateLocalMesh(tempStorage, tmpMesh)) {
            return null;
        }
        k.a("导入meshjson updateLocalMesh成功");
        return tmpMesh;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:1:0x0002, code lost:
        r0 = r2.provisioners;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean validStorageData(meshsdk.model.json.MeshStorage r2) {
        /*
            r1 = this;
            if (r2 == 0) goto L_0x000e
            java.util.List<meshsdk.model.json.MeshStorage$Provisioner> r0 = r2.provisioners
            if (r0 == 0) goto L_0x000e
            int r0 = r0.size()
            if (r0 == 0) goto L_0x000e
            r0 = 1
            goto L_0x000f
        L_0x000e:
            r0 = 0
        L_0x000f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: meshsdk.model.json.MeshStorageService.validStorageData(meshsdk.model.json.MeshStorage):boolean");
    }

    public File exportMeshToJsonFile(File dir, String filename, MeshInfo mesh, List<MeshNetKey> selectedNetKeys, String timeStamp, String houseId) {
        return exportMeshToJsonFile(dir, filename, meshToJson(mesh, selectedNetKeys, timeStamp, houseId));
    }

    public File exportMeshToJsonFile(File dir, String filename, MeshStorage meshStorage) {
        return FileSystem.e(dir, filename, this.mGson.toJson((Object) meshStorage));
    }

    public String meshToJsonString(MeshInfo meshInfo, String houseId) {
        return this.mGson.toJson((Object) meshToJson(meshInfo, meshInfo.meshNetKeyList, "", houseId));
    }

    public String meshToJsonString(MeshStorage meshStorage) {
        return this.mGson.toJson((Object) meshStorage);
    }

    private MeshStorage meshToJson(MeshInfo mesh, List<MeshNetKey> selectedNetKeys, String timeStamp, String houseId) {
        char c;
        int i;
        MeshInfo meshInfo = mesh;
        String str = timeStamp;
        String str2 = houseId;
        MeshStorage meshStorage = new MeshStorage();
        meshStorage.meshUUID = meshInfo.meshUUID;
        MeshLogger.a("meshToJson timeStamp : " + str + ",houseId:" + str2);
        meshStorage.timestamp = str;
        meshStorage.houseId = str2;
        meshStorage.brand = Build.BRAND;
        meshStorage.netKeys = new ArrayList();
        meshStorage.appKeys = new ArrayList();
        Iterator<MeshNetKey> it = selectedNetKeys.iterator();
        while (true) {
            c = 0;
            if (!it.hasNext()) {
                break;
            }
            MeshNetKey meshNetKey = it.next();
            MeshStorage.NetworkKey netKey = new MeshStorage.NetworkKey();
            netKey.name = meshNetKey.name;
            netKey.index = meshNetKey.index;
            netKey.phase = 0;
            netKey.minSecurity = "secure";
            netKey.timestamp = meshStorage.timestamp;
            netKey.key = e.b(meshNetKey.key, "").toUpperCase();
            MeshLogNew.v("mesh to json : netkey.key:" + netKey.key);
            meshStorage.netKeys.add(netKey);
            Iterator<MeshAppKey> it2 = meshInfo.appKeyList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                MeshAppKey ak = it2.next();
                if (ak.boundNetKeyIndex == meshNetKey.index) {
                    MeshStorage.ApplicationKey appKey = new MeshStorage.ApplicationKey();
                    appKey.name = ak.name;
                    appKey.index = ak.index;
                    appKey.key = e.b(ak.key, "").toUpperCase();
                    appKey.boundNetKey = ak.boundNetKeyIndex;
                    meshStorage.appKeys.add(appKey);
                    break;
                }
            }
        }
        meshStorage.groups = new ArrayList();
        List<GroupInfo> groups = meshInfo.groups;
        int i2 = 0;
        while (true) {
            i = 1;
            if (i2 >= groups.size()) {
                break;
            }
            MeshStorage.Group group = new MeshStorage.Group();
            group.address = String.format(Locale.US, "%04X", new Object[]{Integer.valueOf(groups.get(i2).address)});
            group.name = groups.get(i2).name;
            group.groupId = groups.get(i2).groupId;
            group.groupType = TextUtils.isEmpty(groups.get(i2).groupType) ? GroupInfo.TYPE_NORMAL : groups.get(i2).groupType;
            meshStorage.groups.add(group);
            i2++;
        }
        MeshStorage.Provisioner provisioner = new MeshStorage.Provisioner();
        provisioner.UUID = meshInfo.provisionerUUID;
        provisioner.provisionerName = "Telink Android Provisioner";
        provisioner.allocatedUnicastRange = new ArrayList();
        for (AddressRange range : meshInfo.unicastRange) {
            List<MeshStorage.Provisioner.AddressRange> list = provisioner.allocatedUnicastRange;
            Locale locale = Locale.US;
            Object[] objArr = new Object[i];
            objArr[c] = Integer.valueOf(range.low);
            list.add(new MeshStorage.Provisioner.AddressRange(String.format(locale, "%04X", objArr), String.format(locale, "%04X", new Object[]{Integer.valueOf(range.high)})));
            c = 0;
            i = 1;
        }
        ArrayList arrayList = new ArrayList();
        provisioner.allocatedGroupRange = arrayList;
        arrayList.add(new MeshStorage.Provisioner.AddressRange("C000", "C0FF"));
        ArrayList arrayList2 = new ArrayList();
        provisioner.allocatedSceneRange = arrayList2;
        Locale locale2 = Locale.US;
        arrayList2.add(new MeshStorage.Provisioner.SceneRange(String.format(locale2, "%04X", new Object[]{1}), String.format(locale2, "%04X", new Object[]{15})));
        ArrayList arrayList3 = new ArrayList();
        meshStorage.provisioners = arrayList3;
        arrayList3.addAll(meshInfo.provisionerList);
        saveOrUpdateProvisioner(meshStorage.provisioners, provisioner);
        MeshStorage.Node localNode2 = new MeshStorage.Node();
        localNode2.UUID = provisioner.UUID;
        localNode2.unicastAddress = String.format(locale2, "%04X", new Object[]{Integer.valueOf(meshInfo.localAddress)});
        MeshLogger.d("alloc address: " + localNode2.unicastAddress);
        localNode2.name = "Android Provisioner Node";
        ArrayList arrayList4 = new ArrayList();
        localNode2.netKeys = arrayList4;
        arrayList4.add(new MeshStorage.NodeKey(0, false));
        ArrayList arrayList5 = new ArrayList();
        localNode2.appKeys = arrayList5;
        arrayList5.add(new MeshStorage.NodeKey(0, false));
        localNode2.deviceKey = MeshStorage.Defaults.LOCAL_DEVICE_KEY;
        localNode2.security = MeshSecurity.Secure.getDesc();
        getLocalElements(localNode2, mesh.getDefaultAppKeyIndex());
        if (meshStorage.nodes == null) {
            meshStorage.nodes = new ArrayList();
        }
        meshStorage.nodes.add(localNode2);
        this.localNode = localNode2;
        List<NodeInfo> list2 = meshInfo.nodes;
        if (list2 != null) {
            for (NodeInfo deviceInfo : list2) {
                meshStorage.nodes.add(convertDeviceInfoToNode(deviceInfo, mesh.getDefaultAppKeyIndex()));
            }
        }
        meshStorage.ivIndex = String.format(Locale.US, "%08X", new Object[]{Integer.valueOf(meshInfo.ivIndex)});
        meshStorage.scenes = new ArrayList();
        List<Scene> list3 = meshInfo.scenes;
        if (list3 != null) {
            for (Scene meshScene : list3) {
                MeshStorage.Scene scene = new MeshStorage.Scene();
                scene.number = String.format(Locale.US, "%04X", new Object[]{Integer.valueOf(meshScene.id)});
                scene.name = meshScene.name;
                scene.sceneId = meshScene.sceneId;
                if (meshScene.states != null) {
                    scene.addresses = new ArrayList();
                    for (Scene.SceneState state : meshScene.states) {
                        scene.addresses.add(String.format(Locale.US, "%04X", new Object[]{Integer.valueOf(state.address)}));
                        MeshInfo meshInfo2 = mesh;
                    }
                }
                meshStorage.scenes.add(scene);
                MeshInfo meshInfo3 = mesh;
            }
        }
        meshStorage.customScenes = new ArrayList();
        if (mesh.getCustomScenes() != null && mesh.getCustomScenes().size() > 0) {
            for (CustomScene scene2 : mesh.getCustomScenes().values()) {
                CustomSceneStorage css = new CustomSceneStorage(scene2.getSceneId());
                if (scene2.getDevices() != null && scene2.getDevices().size() > 0) {
                    for (CustomScene.SceneDevice device : scene2.getDevices().values()) {
                        CustomSceneStorage.SceneDeviceStorage sds = new CustomSceneStorage.SceneDeviceStorage(device.mac);
                        sds.addRules(device.toRuleList());
                        css.addSceneDevice(sds);
                    }
                }
                meshStorage.customScenes.add(css);
            }
        }
        meshStorage.customGroups = new ArrayList();
        if (mesh.getCustomGroups() != null && mesh.getCustomGroups().size() > 0) {
            meshStorage.customGroups.addAll(mesh.getCustomGroups().values());
        }
        return meshStorage;
    }

    public boolean updateLocalMesh(MeshStorage meshStorage, MeshInfo mesh) {
        int maxRangeHigh;
        MeshStorage.Provisioner localProvisioner;
        int maxRangeHigh2;
        MeshStorage.Provisioner localProvisioner2;
        g provisionHighAddrCompat;
        MeshDictBean lastDictInfo;
        Iterator<MeshStorage.Node> it;
        MeshDictBean lastDictInfo2;
        MeshStorage.Element element;
        Iterator<MeshStorage.Element> it2;
        int maxRangeHigh3;
        MeshStorage.Provisioner localProvisioner3;
        g provisionHighAddrCompat2;
        MeshStorage meshStorage2 = meshStorage;
        MeshInfo meshInfo = mesh;
        MeshLog.debugInfo("updateLocalMesh 导入 配置，mesh.meshUUID=" + meshStorage2.meshUUID);
        meshInfo.meshUUID = meshStorage2.meshUUID;
        meshInfo.meshNetKeyList = new ArrayList();
        for (MeshStorage.NetworkKey networkKey : meshStorage2.netKeys) {
            MeshLogger.a("import netkey : " + networkKey.key);
            meshInfo.meshNetKeyList.add(new MeshNetKey(networkKey.name, networkKey.index, e.g(networkKey.key)));
        }
        meshInfo.appKeyList = new ArrayList();
        for (MeshStorage.ApplicationKey applicationKey : meshStorage2.appKeys) {
            meshInfo.appKeyList.add(new MeshAppKey(applicationKey.name, applicationKey.index, e.g(applicationKey.key), applicationKey.boundNetKey));
        }
        List<MeshStorage.Provisioner> list = meshStorage2.provisioners;
        if (list == null || list.size() == 0) {
            k.a("updateLocalMesh 【失败】 provisioners 为空");
            return false;
        }
        meshInfo.provisionerList.clear();
        meshInfo.provisionerList.addAll(meshStorage2.provisioners);
        MeshDictBean lastDictInfo3 = SqlManager.findDictInfoByUUID(meshStorage2.meshUUID);
        if (lastDictInfo3 == null) {
            MeshLog.debugInfo("findDictInfoByUUID返回空，新建 mesh provisioner UUID");
            meshInfo.provisionerUUID = e.a(MeshUtils.g(16));
            meshInfo.sequenceNumber = 0;
        } else {
            MeshLog.debugInfo("findDictInfoByUUID成功，mesh provisioner UUID=" + lastDictInfo3.getProvisionerUUID() + ",seqNum=" + lastDictInfo3.getSeqNum());
            meshInfo.provisionerUUID = lastDictInfo3.getProvisionerUUID();
            meshInfo.sequenceNumber = lastDictInfo3.getSeqNum();
        }
        g provisionHighAddrCompat3 = new g();
        int maxRangeHigh4 = -1;
        Iterator<MeshStorage.Provisioner> it3 = meshStorage2.provisioners.iterator();
        while (true) {
            if (!it3.hasNext()) {
                maxRangeHigh = maxRangeHigh4;
                localProvisioner = null;
                break;
            }
            MeshStorage.Provisioner provisioner = it3.next();
            if (provisioner.UUID.equals(meshInfo.provisionerUUID)) {
                maxRangeHigh = -1;
                localProvisioner = provisioner;
                break;
            } else if (!provisionHighAddrCompat3.c(provisioner)) {
                for (MeshStorage.Provisioner.AddressRange unRange : provisioner.allocatedUnicastRange) {
                    int tmpHigh = MeshUtils.k(unRange.highAddress, ByteOrder.BIG_ENDIAN);
                    if (maxRangeHigh4 == -1 || maxRangeHigh4 < tmpHigh) {
                        maxRangeHigh4 = tmpHigh;
                    }
                }
            }
        }
        if (localProvisioner == null) {
            int low = (maxRangeHigh + 1 + (provisionHighAddrCompat3.a(meshStorage2.provisioners) ? 319 : 0) + new Random().nextInt(20)) | 16384;
            MeshLog.debugInfo("localProvisioner 为空，新建短地址 low=" + low);
            meshInfo.sequenceNumber = 0;
            if (lastDictInfo3 != null && lastDictInfo3.getState() == 0) {
                low = lastDictInfo3.getLocalAddr();
                meshInfo.sequenceNumber = lastDictInfo3.getSeqNum();
            }
            if (low + 255 > 32767) {
                MeshLogNew.meshJsonLog("新建短地址无效:" + low + ",区间超过最大地址0x7FF,尝试递减");
                while (true) {
                    low -= 70;
                    if (low + 255 <= 32767) {
                        break;
                    }
                }
                boolean isAddressInNodes = true;
                while (isAddressInNodes) {
                    boolean exist = false;
                    List<MeshStorage.Node> list2 = meshStorage2.nodes;
                    if (list2 != null && list2.size() > 0) {
                        for (MeshStorage.Node node : meshStorage2.nodes) {
                            if (MeshUtils.k(node.unicastAddress, ByteOrder.BIG_ENDIAN) == low) {
                                exist = true;
                                low -= 70;
                            }
                        }
                        boolean isAddressInNodes2 = exist;
                        StringBuilder sb = new StringBuilder();
                        sb.append("新建短地址:");
                        sb.append(exist ? "存在" : "不存在");
                        sb.append(" nodes节点中，有效");
                        MeshLogNew.meshJsonLog(sb.toString());
                        isAddressInNodes = isAddressInNodes2;
                    }
                }
                if (low <= 0) {
                    k.a("updateLocalMesh【失败】,localProvisioner为空，新建短地址达到上线");
                    return false;
                }
                MeshLogNew.meshJsonLog("超出最大0x7FF后，经过计算得到的:low:" + low);
            }
            int high = low + 319;
            ArrayList arrayList = new ArrayList();
            meshInfo.unicastRange = arrayList;
            arrayList.add(new AddressRange(low, high));
            meshInfo.localAddress = low;
            meshInfo.resetProvisionIndex(low + 1);
            meshInfo.addressTopLimit = high;
            provisionHighAddrCompat3.d(meshInfo.localAddress, high, meshInfo.provisionerUUID, meshInfo);
        } else {
            MeshStorage.Node provisionNode = findProvisionNode(meshStorage2, localProvisioner.UUID);
            if (provisionNode != null) {
                int shortAddr = MeshUtils.k(provisionNode.unicastAddress, ByteOrder.BIG_ENDIAN);
                MeshLog.debugInfo("localProvisioner 匹配到短地址 localAddress=" + shortAddr);
                meshInfo.localAddress = shortAddr;
            }
        }
        if (TextUtils.isEmpty(meshStorage2.ivIndex)) {
            meshInfo.ivIndex = 0;
        } else {
            meshInfo.ivIndex = MeshUtils.k(meshStorage2.ivIndex, ByteOrder.BIG_ENDIAN);
        }
        meshInfo.groups = new ArrayList();
        List<MeshStorage.Group> list3 = meshStorage2.groups;
        if (list3 != null) {
            for (MeshStorage.Group gp : list3) {
                GroupInfo group = new GroupInfo();
                group.name = gp.name;
                group.groupId = gp.groupId;
                group.address = MeshUtils.k(gp.address, ByteOrder.BIG_ENDIAN);
                group.groupType = gp.groupType;
                meshInfo.groups.add(group);
            }
        }
        List<NodeInfo> cloneNodeInfos = meshInfo.nodes;
        meshInfo.nodes = new ArrayList();
        List<MeshStorage.Node> list4 = meshStorage2.nodes;
        if (list4 != null) {
            meshInfo.resetProvisionIndex(LDSMeshUtil.initProvisionIndex(list4, meshStorage2));
            Iterator<MeshStorage.Node> it4 = meshStorage2.nodes.iterator();
            while (it4.hasNext()) {
                MeshStorage.Node node2 = it4.next();
                if (LDSMeshUtil.isProvisionerNode(meshStorage2, node2) || node2.macAddress == null) {
                    it = it4;
                    lastDictInfo = lastDictInfo3;
                    provisionHighAddrCompat = provisionHighAddrCompat3;
                    localProvisioner2 = localProvisioner;
                    maxRangeHigh2 = maxRangeHigh;
                } else {
                    NodeInfo deviceInfo = new NodeInfo();
                    deviceInfo.meshAddress = MeshUtils.k(node2.unicastAddress, ByteOrder.BIG_ENDIAN);
                    String str = node2.UUID;
                    if (str == null) {
                        MeshLog.e("导入mesh json时node UUID为空，但不影响使用,mac =" + node2.macAddress);
                    } else {
                        deviceInfo.deviceUUID = e.g(str.replace(":", "").replace("-", ""));
                    }
                    List<MeshStorage.Element> list5 = node2.elements;
                    deviceInfo.elementCnt = list5 == null ? 0 : list5.size();
                    deviceInfo.deviceKey = e.g(node2.deviceKey);
                    List<Integer> subList = new ArrayList<>();
                    List<MeshStorage.Element> list6 = node2.elements;
                    if (list6 != null) {
                        Iterator<MeshStorage.Element> it5 = list6.iterator();
                        while (it5.hasNext()) {
                            MeshStorage.Element element2 = it5.next();
                            List<MeshStorage.Model> list7 = element2.models;
                            if (list7 != null) {
                                for (MeshStorage.Model model : list7) {
                                    Iterator<MeshStorage.Node> it6 = it4;
                                    List<String> list8 = model.subscribe;
                                    if (list8 != null) {
                                        Iterator<String> it7 = list8.iterator();
                                        while (it7.hasNext()) {
                                            Iterator<String> it8 = it7;
                                            String sub = it7.next();
                                            MeshDictBean lastDictInfo4 = lastDictInfo3;
                                            int subAdr = MeshUtils.k(sub, ByteOrder.BIG_ENDIAN);
                                            String str2 = sub;
                                            if (!subList.contains(Integer.valueOf(subAdr))) {
                                                subList.add(Integer.valueOf(subAdr));
                                            }
                                            lastDictInfo3 = lastDictInfo4;
                                            it7 = it8;
                                        }
                                        lastDictInfo2 = lastDictInfo3;
                                    } else {
                                        lastDictInfo2 = lastDictInfo3;
                                    }
                                    if (model.publish != null) {
                                        MeshStorage.Publish publish = model.publish;
                                        String str3 = publish.address;
                                        provisionHighAddrCompat2 = provisionHighAddrCompat3;
                                        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
                                        int pubAddress = MeshUtils.k(str3, byteOrder);
                                        if (pubAddress != 0) {
                                            int i = pubAddress;
                                            if (publish.period != null) {
                                                localProvisioner3 = localProvisioner;
                                                int elementAddress = element2.index + MeshUtils.k(node2.unicastAddress, byteOrder);
                                                MeshStorage.Transmit transmit = publish.retransmit;
                                                maxRangeHigh3 = maxRangeHigh;
                                                int interval = (transmit.interval / 50) - 1;
                                                int i2 = interval;
                                                MeshStorage.PublishPeriod publishPeriod = publish.period;
                                                it2 = it5;
                                                element = element2;
                                                deviceInfo.setPublishModel(new PublishModel(elementAddress, MeshUtils.k(model.modelId, byteOrder), MeshUtils.k(publish.address, byteOrder), publishPeriod.numberOfSteps * publishPeriod.resolution, publish.ttl, publish.credentials, transmit.count | (interval << 3)));
                                            } else {
                                                localProvisioner3 = localProvisioner;
                                                maxRangeHigh3 = maxRangeHigh;
                                                it2 = it5;
                                                element = element2;
                                            }
                                        } else {
                                            localProvisioner3 = localProvisioner;
                                            maxRangeHigh3 = maxRangeHigh;
                                            it2 = it5;
                                            element = element2;
                                        }
                                    } else {
                                        provisionHighAddrCompat2 = provisionHighAddrCompat3;
                                        localProvisioner3 = localProvisioner;
                                        maxRangeHigh3 = maxRangeHigh;
                                        it2 = it5;
                                        element = element2;
                                    }
                                    it4 = it6;
                                    lastDictInfo3 = lastDictInfo2;
                                    provisionHighAddrCompat3 = provisionHighAddrCompat2;
                                    localProvisioner = localProvisioner3;
                                    maxRangeHigh = maxRangeHigh3;
                                    it5 = it2;
                                    element2 = element;
                                }
                                Iterator<MeshStorage.Node> it9 = it4;
                                MeshDictBean meshDictBean = lastDictInfo3;
                                g gVar = provisionHighAddrCompat3;
                                MeshStorage.Provisioner provisioner2 = localProvisioner;
                                int i3 = maxRangeHigh;
                                Iterator<MeshStorage.Element> it10 = it5;
                                MeshStorage.Element element3 = element2;
                            }
                        }
                        it = it4;
                        lastDictInfo = lastDictInfo3;
                        provisionHighAddrCompat = provisionHighAddrCompat3;
                        localProvisioner2 = localProvisioner;
                        maxRangeHigh2 = maxRangeHigh;
                    } else {
                        it = it4;
                        lastDictInfo = lastDictInfo3;
                        provisionHighAddrCompat = provisionHighAddrCompat3;
                        localProvisioner2 = localProvisioner;
                        maxRangeHigh2 = maxRangeHigh;
                    }
                    deviceInfo.macAddress = node2.macAddress;
                    deviceInfo.protocolVersion = node2.protocolVersion;
                    deviceInfo.subList = subList;
                    List<MeshStorage.NodeKey> list9 = node2.appKeys;
                    deviceInfo.bound = (list9 == null || list9.size() == 0) ? false : true;
                    deviceInfo.compositionData = convertNodeToNodeInfo(node2);
                    if (node2.schedulers != null) {
                        deviceInfo.schedulers = new ArrayList();
                        for (MeshStorage.NodeScheduler nodeScheduler : node2.schedulers) {
                            deviceInfo.schedulers.add(parseNodeScheduler(nodeScheduler));
                        }
                    }
                    if (node2.smarts != null) {
                        ArrayList arrayList2 = new ArrayList();
                        deviceInfo.smarts = arrayList2;
                        arrayList2.addAll(node2.smarts);
                    }
                    if (cloneNodeInfos != null && SIGMesh.getInstance().hasConnected()) {
                        for (NodeInfo nodeInfo : cloneNodeInfos) {
                            String str4 = nodeInfo.macAddress;
                            if (str4 != null && str4.equals(deviceInfo.macAddress)) {
                                deviceInfo.setOnOff(nodeInfo.getOnOff());
                            }
                        }
                    }
                    meshInfo.nodes.add(deviceInfo);
                }
                it4 = it;
                lastDictInfo3 = lastDictInfo;
                provisionHighAddrCompat3 = provisionHighAddrCompat;
                localProvisioner = localProvisioner2;
                maxRangeHigh = maxRangeHigh2;
            }
            g gVar2 = provisionHighAddrCompat3;
            MeshStorage.Provisioner provisioner3 = localProvisioner;
            int i4 = maxRangeHigh;
        } else {
            g gVar3 = provisionHighAddrCompat3;
            MeshStorage.Provisioner provisioner4 = localProvisioner;
            int i5 = maxRangeHigh;
        }
        meshInfo.scenes = new ArrayList();
        List<MeshStorage.Scene> list10 = meshStorage2.scenes;
        if (!(list10 == null || list10.size() == 0)) {
            for (MeshStorage.Scene outerScene : meshStorage2.scenes) {
                Scene scene = new Scene();
                scene.id = MeshUtils.k(outerScene.number, ByteOrder.BIG_ENDIAN);
                scene.name = outerScene.name;
                scene.sceneId = outerScene.sceneId;
                if (outerScene.addresses != null) {
                    scene.states = new ArrayList(outerScene.addresses.size());
                    for (String adrInScene : outerScene.addresses) {
                        scene.states.add(new Scene.SceneState(MeshUtils.k(adrInScene, ByteOrder.BIG_ENDIAN)));
                    }
                }
                meshInfo.scenes.add(scene);
            }
        }
        List<CustomSceneStorage> list11 = meshStorage2.customScenes;
        if (list11 != null && list11.size() > 0) {
            for (CustomSceneStorage css : meshStorage2.customScenes) {
                CustomScene customScene = new CustomScene(css.getSceneId());
                List<CustomSceneStorage.SceneDeviceStorage> devices = css.getDevices();
                if (devices != null && devices.size() > 0) {
                    for (CustomSceneStorage.SceneDeviceStorage sds : devices) {
                        CustomScene.SceneDevice sceneDevice = new CustomScene.SceneDevice(sds.mac);
                        for (CustomScene.SceneRule rule : sds.getRules()) {
                            if (rule.getModelId() == 4871) {
                                LinkedTreeMap treeMap = (LinkedTreeMap) rule.getValue();
                                int h = ((Double) treeMap.get("HSLHue")).intValue();
                                int s = ((Double) treeMap.get("HSLSaturation")).intValue();
                                LinkedTreeMap linkedTreeMap = treeMap;
                                int i6 = s;
                                rule.setValue(rule.getModelId(), new HSL(h, s, ((Double) treeMap.get("HSLLightness")).intValue()));
                            } else {
                                try {
                                    rule.setValue(rule.getModelId(), Integer.valueOf((int) ((Double) rule.getValue()).doubleValue()));
                                } catch (Exception e) {
                                    MeshLogNew.meshJsonLog("导入meshjson内部有点异常:" + e.getMessage());
                                }
                            }
                            sceneDevice.putRule(rule);
                            MeshInfo meshInfo2 = mesh;
                        }
                        customScene.putSceneDevice(sceneDevice);
                        MeshInfo meshInfo3 = mesh;
                    }
                }
                mesh.getCustomScenes().put(customScene.getSceneId(), customScene);
                MeshInfo meshInfo4 = mesh;
            }
        }
        List<CustomGroup> list12 = meshStorage2.customGroups;
        if (list12 == null || list12.size() <= 0) {
            return true;
        }
        for (CustomGroup cg : meshStorage2.customGroups) {
            mesh.getCustomGroups().put(cg.getGroupId(), cg);
        }
        return true;
    }

    public MeshStorage.Node convertDeviceInfoToNode(NodeInfo deviceInfo, int appKeyIndex) {
        List<CompositionData.Element> elements;
        int features;
        List<Integer> list;
        List<Integer> list2;
        List<CompositionData.Element> elements2;
        int features2;
        NodeInfo nodeInfo = deviceInfo;
        MeshStorage.Node node = new MeshStorage.Node();
        node.UUID = e.a(nodeInfo.deviceUUID).toUpperCase();
        node.macAddress = nodeInfo.macAddress;
        Locale locale = Locale.US;
        int i = 1;
        char c = 0;
        node.unicastAddress = String.format(locale, "%04X", new Object[]{Integer.valueOf(nodeInfo.meshAddress)});
        node.protocolVersion = nodeInfo.protocolVersion;
        byte[] bArr = nodeInfo.deviceKey;
        if (bArr != null) {
            node.deviceKey = e.b(bArr, "").toUpperCase();
        }
        node.elements = new ArrayList(nodeInfo.elementCnt);
        if (nodeInfo.compositionData != null) {
            node.deviceKey = e.b(nodeInfo.deviceKey, "").toUpperCase();
            node.cid = String.format(locale, "%04X", new Object[]{Integer.valueOf(nodeInfo.compositionData.cid)});
            node.pid = String.format(locale, "%04X", new Object[]{Integer.valueOf(nodeInfo.compositionData.pid)});
            node.vid = String.format(locale, "%04X", new Object[]{Integer.valueOf(nodeInfo.compositionData.vid)});
            node.crpl = String.format(locale, "%04X", new Object[]{Integer.valueOf(nodeInfo.compositionData.crpl)});
            int features3 = nodeInfo.compositionData.features;
            int i2 = 2;
            int i3 = (features3 & 1) == 0 ? 2 : 1;
            int i4 = (features3 & 2) == 0 ? 2 : 1;
            int i5 = (features3 & 4) == 0 ? 2 : 1;
            if ((features3 & 8) != 0) {
                i2 = 1;
            }
            node.features = new MeshStorage.Features(i3, i4, i5, i2);
            PublishModel publishModel = deviceInfo.getPublishModel();
            CompositionData compositionData = nodeInfo.compositionData;
            if (compositionData.elements != null) {
                List<CompositionData.Element> elements3 = compositionData.elements;
                int i6 = 0;
                while (i6 < elements3.size()) {
                    CompositionData.Element ele = elements3.get(i6);
                    MeshStorage.Element element = new MeshStorage.Element();
                    element.index = i6;
                    Locale locale2 = Locale.US;
                    Object[] objArr = new Object[i];
                    objArr[c] = Integer.valueOf(ele.location);
                    element.location = String.format(locale2, "%04X", objArr);
                    element.models = new ArrayList();
                    if (ele.sigNum == 0 || (list2 = ele.sigModels) == null) {
                        features = features3;
                        elements = elements3;
                    } else {
                        for (Integer intValue : list2) {
                            int modelId = intValue.intValue();
                            MeshStorage.Model model = new MeshStorage.Model();
                            Locale locale3 = Locale.US;
                            Object[] objArr2 = new Object[i];
                            objArr2[c] = Integer.valueOf(modelId);
                            model.modelId = String.format(locale3, "%04X", objArr2);
                            ArrayList arrayList = new ArrayList();
                            model.bind = arrayList;
                            arrayList.add(Integer.valueOf(appKeyIndex));
                            model.subscribe = new ArrayList();
                            if (inDefaultSubModel(modelId)) {
                                for (Integer intValue2 : nodeInfo.subList) {
                                    int subAdr = intValue2.intValue();
                                    List<String> list3 = model.subscribe;
                                    int features4 = features3;
                                    Locale locale4 = Locale.US;
                                    List<CompositionData.Element> elements4 = elements3;
                                    Object[] objArr3 = new Object[i];
                                    objArr3[0] = Integer.valueOf(subAdr);
                                    list3.add(String.format(locale4, "%04X", objArr3));
                                    features3 = features4;
                                    elements3 = elements4;
                                }
                                features2 = features3;
                                elements2 = elements3;
                            } else {
                                features2 = features3;
                                elements2 = elements3;
                            }
                            if (publishModel != null && publishModel.modelId == modelId) {
                                MeshStorage.Publish publish = new MeshStorage.Publish();
                                Locale locale5 = Locale.US;
                                Object[] objArr4 = new Object[i];
                                objArr4[0] = Integer.valueOf(publishModel.address);
                                publish.address = String.format(locale5, "%04X", objArr4);
                                publish.index = 0;
                                publish.ttl = publishModel.ttl;
                                TransitionTime transitionTime = TransitionTime.a((long) publishModel.period);
                                MeshStorage.PublishPeriod period = new MeshStorage.PublishPeriod();
                                period.numberOfSteps = transitionTime.b() & 255;
                                period.resolution = transitionTime.c();
                                publish.period = period;
                                publish.credentials = publishModel.credential;
                                TransitionTime transitionTime2 = transitionTime;
                                publish.retransmit = new MeshStorage.Transmit(publishModel.getTransmitCount(), (publishModel.getTransmitInterval() + 1) * 50);
                                model.publish = publish;
                            }
                            element.models.add(model);
                            features3 = features2;
                            elements3 = elements2;
                            i = 1;
                            c = 0;
                        }
                        features = features3;
                        elements = elements3;
                    }
                    if (!(ele.vendorNum == 0 || (list = ele.vendorModels) == null)) {
                        for (Integer intValue3 : list) {
                            int modelId2 = intValue3.intValue();
                            MeshStorage.Model model2 = new MeshStorage.Model();
                            model2.modelId = String.format(Locale.US, "%08X", new Object[]{Integer.valueOf(modelId2)});
                            ArrayList arrayList2 = new ArrayList();
                            model2.bind = arrayList2;
                            arrayList2.add(Integer.valueOf(appKeyIndex));
                            element.models.add(model2);
                        }
                    }
                    node.elements.add(element);
                    i6++;
                    features3 = features;
                    elements3 = elements;
                    i = 1;
                    c = 0;
                }
                int i7 = features3;
                List<CompositionData.Element> list4 = elements3;
            } else {
                int i8 = features3;
            }
        } else {
            for (int i9 = 0; i9 < nodeInfo.elementCnt; i9++) {
                node.elements.add(new MeshStorage.Element());
            }
        }
        ArrayList arrayList3 = new ArrayList();
        node.netKeys = arrayList3;
        arrayList3.add(new MeshStorage.NodeKey(0, false));
        node.configComplete = true;
        node.name = "Common Node";
        if (nodeInfo.bound) {
            ArrayList arrayList4 = new ArrayList();
            node.appKeys = arrayList4;
            arrayList4.add(new MeshStorage.NodeKey(0, false));
        }
        node.security = MeshSecurity.Secure.getDesc();
        if (nodeInfo.schedulers != null) {
            node.schedulers = new ArrayList();
            for (Scheduler deviceScheduler : nodeInfo.schedulers) {
                node.schedulers.add(MeshStorage.NodeScheduler.fromScheduler(deviceScheduler));
            }
        }
        if (nodeInfo.smarts != null) {
            ArrayList arrayList5 = new ArrayList();
            node.smarts = arrayList5;
            arrayList5.addAll(nodeInfo.smarts);
        }
        return node;
    }

    private void getLocalElements(MeshStorage.Node node, int appKeyIndex) {
        List<Integer> list;
        List<Integer> list2;
        MeshStorage.Node node2 = node;
        node2.elements = new ArrayList();
        List<CompositionData.Element> elements = CompositionData.from(VC_TOOL_CPS).elements;
        for (int i = 0; i < elements.size(); i++) {
            CompositionData.Element ele = elements.get(i);
            MeshStorage.Element element = new MeshStorage.Element();
            element.index = i;
            element.location = String.format(Locale.US, "%04X", new Object[]{Integer.valueOf(ele.location)});
            element.models = new ArrayList();
            if (!(ele.sigNum == 0 || (list2 = ele.sigModels) == null)) {
                for (Integer intValue : list2) {
                    int modelId = intValue.intValue();
                    MeshStorage.Model model = new MeshStorage.Model();
                    model.modelId = String.format(Locale.US, "%04X", new Object[]{Integer.valueOf(modelId)});
                    ArrayList arrayList = new ArrayList();
                    model.bind = arrayList;
                    arrayList.add(Integer.valueOf(appKeyIndex));
                    model.subscribe = new ArrayList();
                    element.models.add(model);
                }
            }
            if (!(ele.vendorNum == 0 || (list = ele.vendorModels) == null)) {
                for (Integer intValue2 : list) {
                    int modelId2 = intValue2.intValue();
                    MeshStorage.Model model2 = new MeshStorage.Model();
                    model2.modelId = String.format(Locale.US, "%08X", new Object[]{Integer.valueOf(modelId2)});
                    ArrayList arrayList2 = new ArrayList();
                    model2.bind = arrayList2;
                    arrayList2.add(Integer.valueOf(appKeyIndex));
                    element.models.add(model2);
                }
            }
            node2.elements.add(element);
        }
    }

    private boolean inDefaultSubModel(int modelId) {
        for (MeshSigModel model : MeshSigModel.getDefaultSubList()) {
            if (model.modelId == modelId) {
                return true;
            }
        }
        return false;
    }

    public CompositionData convertNodeToNodeInfo(MeshStorage.Node node) {
        MeshStorage.Node node2 = node;
        CompositionData compositionData = new CompositionData();
        String str = node2.cid;
        compositionData.cid = (str == null || str.equals("")) ? 0 : MeshUtils.k(node2.cid, ByteOrder.BIG_ENDIAN);
        String str2 = node2.pid;
        compositionData.pid = (str2 == null || str2.equals("")) ? 0 : MeshUtils.k(node2.pid, ByteOrder.BIG_ENDIAN);
        String str3 = node2.vid;
        compositionData.vid = (str3 == null || str3.equals("")) ? 0 : MeshUtils.k(node2.vid, ByteOrder.BIG_ENDIAN);
        String str4 = node2.crpl;
        compositionData.crpl = (str4 == null || str4.equals("")) ? 0 : MeshUtils.k(node2.crpl, ByteOrder.BIG_ENDIAN);
        int relaySpt = 0;
        int proxySpt = 0;
        int friendSpt = 0;
        int lowPowerSpt = 0;
        MeshStorage.Features features = node2.features;
        int i = 4;
        if (features != null) {
            relaySpt = features.relay == 1 ? 1 : 0;
            proxySpt = features.proxy == 1 ? 2 : 0;
            friendSpt = features.friend == 1 ? 4 : 0;
            lowPowerSpt = features.lowPower == 1 ? 8 : 0;
        }
        compositionData.features = relaySpt | proxySpt | friendSpt | lowPowerSpt;
        compositionData.elements = new ArrayList();
        List<MeshStorage.Element> list = node2.elements;
        if (list != null) {
            for (MeshStorage.Element element : list) {
                CompositionData.Element infoEle = new CompositionData.Element();
                infoEle.sigModels = new ArrayList();
                infoEle.vendorModels = new ArrayList();
                List<MeshStorage.Model> list2 = element.models;
                if (list2 == null || list2.size() == 0) {
                    infoEle.sigNum = 0;
                    infoEle.vendorNum = 0;
                } else {
                    for (MeshStorage.Model model : element.models) {
                        String str5 = model.modelId;
                        if (str5 != null && !str5.equals("")) {
                            int modelId = MeshUtils.k(model.modelId, ByteOrder.BIG_ENDIAN);
                            if (model.modelId.length() > i) {
                                infoEle.vendorModels.add(Integer.valueOf(modelId));
                            } else {
                                infoEle.sigModels.add(Integer.valueOf(modelId));
                            }
                        }
                        i = 4;
                    }
                    infoEle.sigNum = infoEle.sigModels.size();
                    infoEle.vendorNum = infoEle.vendorModels.size();
                }
                String str6 = element.location;
                infoEle.location = (str6 == null || str6.equals("")) ? 0 : MeshUtils.k(element.location, ByteOrder.BIG_ENDIAN);
                compositionData.elements.add(infoEle);
                i = 4;
            }
        }
        return compositionData;
    }

    private MeshStorage.Node findProvisionNode(MeshStorage meshStorage, String uuid) {
        for (MeshStorage.Node node : meshStorage.nodes) {
            if (uuid.equals(node.UUID)) {
                return node;
            }
        }
        return null;
    }

    private Scheduler parseNodeScheduler(MeshStorage.NodeScheduler nodeScheduler) {
        return new Scheduler.Builder().e(nodeScheduler.index).m((byte) ((int) nodeScheduler.year)).g((short) ((int) nodeScheduler.month)).c((byte) ((int) nodeScheduler.day)).d((byte) ((int) nodeScheduler.hour)).f((byte) ((int) nodeScheduler.minute)).i((byte) ((int) nodeScheduler.second)).l((byte) ((int) nodeScheduler.week)).b((byte) ((int) nodeScheduler.action)).k((byte) ((int) nodeScheduler.transTime)).h((short) nodeScheduler.sceneId).j(nodeScheduler.smartId).a();
    }

    private void saveOrUpdateProvisioner(List<MeshStorage.Provisioner> list, MeshStorage.Provisioner p) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).UUID.equals(p.UUID)) {
                list.set(i, p);
                return;
            }
        }
        list.add(p);
    }
}
