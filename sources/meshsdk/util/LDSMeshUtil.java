package meshsdk.util;

import android.bluetooth.BluetoothDevice;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.reporters.k;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.entity.NetworkingDeviceWrapper;
import com.telink.ble.mesh.foundation.MeshController;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.util.MeshLogger;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.ctrl.CmdCtrl;
import meshsdk.model.CustomGroup;
import meshsdk.model.GroupInfo;
import meshsdk.model.MeshInfo;
import meshsdk.model.NetworkingDevice;
import meshsdk.model.NetworkingState;
import meshsdk.model.NodeInfo;
import meshsdk.model.Scene;
import meshsdk.model.json.MeshStorage;
import org.glassfish.grizzly.http.server.Constants;
import org.json.JSONException;
import org.json.JSONObject;

public class LDSMeshUtil {
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static int findMeshAddr(List<NodeInfo> list, String mac) {
        NodeInfo node = findMeshNode(list, mac);
        if (node != null) {
            return node.meshAddress;
        }
        return -1;
    }

    public static String getRemoteMacAddress(String nodeMacAddress) {
        if (nodeMacAddress == null) {
            return "";
        }
        if (nodeMacAddress.contains(":")) {
            return nodeMacAddress;
        }
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < nodeMacAddress.length(); i += 2) {
            if (i > 0) {
                output.append(":");
            }
            output.append(nodeMacAddress.substring(i, i + 2));
        }
        return output.toString();
    }

    public static String printNodes(List<NodeInfo> list) {
        StringBuffer sb = new StringBuffer();
        for (NodeInfo nodeInfo : list) {
            sb.append(nodeInfo.macAddress);
            sb.append(" ");
        }
        return sb.toString();
    }

    public static int getNodeProtocolVersion(String mac) {
        NodeInfo nodeInfo = findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, mac);
        if (nodeInfo != null) {
            return nodeInfo.protocolVersion;
        }
        return 0;
    }

    public static NodeInfo findMeshNode(List<NodeInfo> list, String mac) {
        if (list == null) {
            return null;
        }
        for (NodeInfo n : list) {
            if (mac.equalsIgnoreCase(n.macAddress)) {
                return n;
            }
        }
        return null;
    }

    public static NodeInfo findMeshNode(List<NodeInfo> list, int meshAddr) {
        if (list == null) {
            return null;
        }
        for (NodeInfo n : list) {
            if (meshAddr == n.meshAddress) {
                return n;
            }
        }
        return null;
    }

    public static JSONObject createNodeDetailStatusJson(NodeInfo nodeInfo) {
        JSONObject item = new JSONObject();
        try {
            item.put("name", (Object) "");
            item.put(PlaceTypes.ADDRESS, nodeInfo.meshAddress);
            item.put("mac", (Object) nodeInfo.macAddress);
            int i = 0;
            item.put(MeshConstants.AC_STATE_LOGIN_SUCCESS, nodeInfo.getOnOff() == -1 ? 0 : 1);
            if (nodeInfo.getOnOff() == 1) {
                i = 1;
            }
            item.put("onOff", i);
            item.put("brightness", nodeInfo.lum);
            item.put("temperature", nodeInfo.temp);
            item.put("HSLHue", nodeInfo.hue);
            item.put("HSLSaturation", nodeInfo.sat);
            int i2 = nodeInfo.light;
            if (i2 == 0) {
                i2 = 100;
            }
            item.put("HSLLightness", i2);
            item.put("rhythmsSupport", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static List<NodeInfo> getDevicesInGroup(int groupAddr) {
        List<NodeInfo> localDevices = SIGMesh.getInstance().getMeshInfo().nodes;
        List<NodeInfo> innerDevices = new ArrayList<>();
        if (localDevices == null) {
            return innerDevices;
        }
        for (NodeInfo device : localDevices) {
            List<Integer> list = device.subList;
            if (list != null) {
                Iterator<Integer> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().intValue() == groupAddr) {
                            innerDevices.add(device);
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return innerDevices;
    }

    public static List<String> getDevicesInCustomGroup(int groupId) {
        CustomGroup customGroup = SIGMesh.getInstance().getMeshInfo().getCustomGroups().get(String.valueOf(groupId));
        if (customGroup == null) {
            return null;
        }
        return customGroup.getDevices();
    }

    public static int createNewGroupAddr() {
        List<GroupInfo> groupInfoList = SIGMesh.getInstance().getMeshInfo().groups;
        if (groupInfoList != null && groupInfoList.size() == 256) {
            return -1;
        }
        int newAddr = createNewGroupAddrRandom();
        if (newAddr == -1) {
            int newAddr2 = createNewGroupAddrIncrement();
            MeshLog.i("createNewGroupAddr 建组采用随机地址失败，走递增方式,addr:" + newAddr2 + "(" + String.format("%x", new Object[]{Integer.valueOf(newAddr2)}) + ")");
            return newAddr2;
        }
        MeshLog.i("createNewGroupAddr 建组采用随机地址成功,addr:" + newAddr + "(" + String.format("%x", new Object[]{Integer.valueOf(newAddr)}) + ")");
        return newAddr;
    }

    private static int createNewGroupAddrRandom() {
        int randomAddr = -1;
        for (int tryCount = 0; tryCount < 3; tryCount++) {
            int min = Integer.parseInt("c000", 16);
            randomAddr = new Random().nextInt((Integer.parseInt("c0ff", 16) - min) + 1) + min;
            List<GroupInfo> groupInfoList = SIGMesh.getInstance().getMeshInfo().groups;
            if (groupInfoList == null || groupInfoList.size() <= 0) {
                break;
            }
            boolean exist = false;
            for (GroupInfo groupInfo : groupInfoList) {
                if (groupInfo.address == randomAddr) {
                    exist = true;
                }
            }
            if (!exist) {
                break;
            }
            MeshLog.i("createNewGroupAddrRandom 随机地址:" + randomAddr + " 与现有冲突,继续尝试");
        }
        return randomAddr;
    }

    private static int createNewGroupAddrIncrement() {
        List<GroupInfo> groupInfoList = SIGMesh.getInstance().getMeshInfo().groups;
        if (groupInfoList != null && groupInfoList.size() == 256) {
            return -1;
        }
        if (groupInfoList.size() == 0) {
            return Constants.DEFAULT_HTTP_HEADER_BUFFER_SIZE;
        }
        Collections.sort(groupInfoList, new Comparator<GroupInfo>() {
            public int compare(GroupInfo o1, GroupInfo o2) {
                return o1.address - o2.address;
            }
        });
        int base = 0;
        int i = 0;
        while (true) {
            if (i >= groupInfoList.size()) {
                break;
            }
            if (i >= groupInfoList.size() - 1) {
                base = groupInfoList.get(i).address;
            } else if (groupInfoList.get(i + 1).address - groupInfoList.get(i).address != 1) {
                base = groupInfoList.get(i).address;
                break;
            }
            i++;
        }
        return base + 1;
    }

    public static GroupInfo findGroup(List<GroupInfo> list, int groupId) {
        if (list == null) {
            return null;
        }
        for (GroupInfo g : list) {
            if (groupId == g.groupId) {
                return g;
            }
        }
        return null;
    }

    public static GroupInfo findGroupByAddress(List<GroupInfo> list, int addr) {
        if (list == null) {
            return null;
        }
        for (GroupInfo g : list) {
            if (addr == g.address) {
                return g;
            }
        }
        return null;
    }

    public static Scene findScene(List<Scene> list, int sceneId) {
        if (list == null) {
            return null;
        }
        for (Scene s : list) {
            if (sceneId == s.sceneId) {
                return s;
            }
        }
        return null;
    }

    public static String getBreakMac(String s) {
        char[] result = new char[17];
        char[] chars = s.toCharArray();
        if (chars.length == 17) {
            return s;
        }
        result[0] = chars[0];
        result[1] = chars[1];
        result[2] = ':';
        result[3] = chars[2];
        result[4] = chars[3];
        result[5] = ':';
        result[6] = chars[4];
        result[7] = chars[5];
        result[8] = ':';
        result[9] = chars[6];
        result[10] = chars[7];
        result[11] = ':';
        result[12] = chars[8];
        result[13] = chars[9];
        result[14] = ':';
        result[15] = chars[10];
        result[16] = chars[11];
        return String.valueOf(result);
    }

    public static int initProvisionIndex(List<MeshStorage.Node> nodes, MeshStorage meshStorage) {
        int nodeAddr;
        if (nodes.size() == 1) {
            return MeshUtils.k(nodes.get(0).unicastAddress, ByteOrder.BIG_ENDIAN) + 1;
        }
        int max = 0;
        for (MeshStorage.Node node : nodes) {
            if (!isProvisionerNode(meshStorage, node) && (nodeAddr = MeshUtils.k(node.unicastAddress, ByteOrder.BIG_ENDIAN)) > max) {
                max = nodeAddr;
            }
        }
        return max + 4;
    }

    public static boolean isProvisionerNode(MeshStorage meshStorage, MeshStorage.Node node) {
        for (MeshStorage.Provisioner provisioner : meshStorage.provisioners) {
            if (provisioner.UUID.equals(node.UUID)) {
                return true;
            }
        }
        return false;
    }

    public static String getMeshHWVersion(String meshUUID, String mac) {
        return SharedPreferenceHelper.getMeshHWVersion(SIGMesh.getInstance().getContext(), String.format(Locale.US, "%s#%s", new Object[]{meshUUID, mac}));
    }

    public static void setMeshHWVersion(String meshUUID, String mac, String ver) {
        SharedPreferenceHelper.setMeshHWVersion(SIGMesh.getInstance().getContext(), String.format(Locale.US, "%s#%s", new Object[]{meshUUID, mac}), ver);
        k.a("读取到版本号:" + ver + ",mac:" + mac);
    }

    public static int[] json2Hsl(JSONObject obj) {
        int hue = Math.round((float) ((obj.getInt("HSLHue") * 65535) / 360));
        int sat = Math.round((float) ((obj.getInt("HSLSaturation") * 65535) / 100));
        int lightness = Math.round((float) ((obj.getInt("HSLLightness") * 65535) / 100));
        MeshLog.i("控制色盘,根据web传入的hsl转换得到: hue:" + hue + ",sat:" + sat + ",lightness:" + lightness + ",input:" + obj);
        return new int[]{hue, sat, lightness};
    }

    public static float[] rgbToHsl(int red, int green, int blue) {
        return rgbToHsl(rgbToInt(red, green, blue));
    }

    private static float[] rgbToHsl(int rgb) {
        float d;
        float h;
        float h2;
        float r = ((float) ((rgb >> 16) & 255)) / 255.0f;
        float g = ((float) ((rgb >> 8) & 255)) / 255.0f;
        float b = ((float) (rgb & 255)) / 255.0f;
        float max = Math.max(Math.max(r, g), b);
        float min = Math.min(Math.min(r, g), b);
        float l = (max + min) / 2.0f;
        float f = 0.0f;
        if (max == min) {
            h = 0.0f;
            d = 0.0f;
        } else {
            float h3 = max - min;
            float s = h3 / (l > 0.5f ? (2.0f - max) - min : max + min);
            if (max == r) {
                float f2 = (g - b) / h3;
                if (g < b) {
                    f = 6.0f;
                }
                h2 = f2 + f;
            } else if (max == g) {
                h2 = 2.0f + ((b - r) / h3);
            } else {
                h2 = ((r - g) / h3) + 4.0f;
            }
            d = h2 / 6.0f;
            h = s;
        }
        return new float[]{360.0f * d, h * 100.0f, 100.0f * l};
    }

    public static int rgbToInt(int red, int green, int blue) {
        return (red << 16) | (green << 8) | blue;
    }

    public static String parseOtaVersion(String url) {
        Matcher m = Pattern.compile("V(\\d+.\\d+.\\d+)").matcher(url);
        if (m.find()) {
            return m.group().substring(1);
        }
        return null;
    }

    public static void removeInvalidNode(List<NodeInfo> nodeList, String mac) {
        MeshLog.d("移除垃圾节点数据:" + mac);
        Iterator<NodeInfo> iterator = nodeList.iterator();
        while (iterator.hasNext()) {
            if (mac.equals(iterator.next().macAddress)) {
                iterator.remove();
            }
        }
    }

    public static boolean hasChangedNode(MeshInfo localMesh, MeshInfo newMesh) {
        if (localMesh == null || newMesh == null) {
            return false;
        }
        List<NodeInfo> localNodes = localMesh.nodes;
        List<NodeInfo> newNodes = newMesh.nodes;
        if ((localNodes == null ? 0 : localNodes.size()) != (newNodes == null ? 0 : newNodes.size())) {
            return true;
        }
        for (NodeInfo newNode : newNodes) {
            if (!containsNode(localNodes, newNode)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsNode(List<NodeInfo> list, NodeInfo newNode) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (NodeInfo nodeInfo : list) {
            if (nodeInfo.macAddress.equals(newNode.macAddress) && nodeInfo.meshAddress == newNode.meshAddress) {
                return true;
            }
        }
        return false;
    }

    public static int createAddrByMac(String mac) {
        return (Integer.parseInt(mac.substring(mac.length() - 4), 16) & 4095) * 4;
    }

    public static NodeInfo hasExist(int meshAddr) {
        for (NodeInfo nodeInfo : SIGMesh.getInstance().getMeshInfo().nodes) {
            if (nodeInfo.meshAddress == meshAddr) {
                return nodeInfo;
            }
        }
        return null;
    }

    public static NetworkingDeviceWrapper isMeshProvisionButNotKeybindAdv(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord) {
        MeshUtils.AdvertiseDataUnit dataUnit;
        byte[] bArr;
        byte[] serviceData = MeshUtils.h(scanRecord, false);
        if (serviceData == null || serviceData.length < 9) {
            return null;
        }
        byte type = serviceData[0];
        boolean networkIdCheck = false;
        if (type == 0) {
            byte[] advertisingNetworkId = new byte[8];
            System.arraycopy(serviceData, 1, advertisingNetworkId, 0, 8);
            if (MeshService.k().l().u0() == null) {
                MeshLog.e("isMeshProvisionButNotKeybindAdv 当前mesh网络的networkId为null，忽略广播");
                return null;
            }
            networkIdCheck = e.d(MeshService.k().l().u0(), advertisingNetworkId);
        } else if (type == 1) {
            networkIdCheck = MeshService.k().l().p2(serviceData, new MeshController.ValidateProxyAdvParseListener[0]);
        }
        if (networkIdCheck && (dataUnit = MeshUtils.n(scanRecord).get((byte) -1)) != null && (bArr = dataUnit.c) != null && bArr.length > 0) {
            String advertisingData = bytesToHex(bArr);
            byte[] bArr2 = dataUnit.c;
            if (bArr2.length < 25) {
                MeshLog.e("isMeshProvisionButNotKeybindAdv mesh上线广播，service数据长度不对，忽略广播");
                return null;
            }
            int appkeyStatus = CmdCtrl.getValueByBitPosition(bArr2[23], 1, 1);
            int veryLowBattery = CmdCtrl.getValueByBitPosition(dataUnit.c[23], 2, 1);
            if (appkeyStatus == 1) {
                MeshLog.e("isMeshProvisionButNotKeybindAdv 发现:" + bluetoothDevice.getAddress() + "已使用当前mesh网络配网，且未keybind，搜索页也展示该设备，advdata:" + advertisingData);
                NodeInfo nodeInfo = new NodeInfo();
                nodeInfo.meshAddress = -1;
                nodeInfo.deviceUUID = null;
                nodeInfo.macAddress = bluetoothDevice.getAddress().replace(":", "").toUpperCase();
                nodeInfo.protocolVersion = dataUnit.c[22];
                NetworkingDevice processingDevice = new NetworkingDevice(nodeInfo);
                processingDevice.bluetoothDevice = bluetoothDevice;
                processingDevice.rssi = rssi;
                processingDevice.state = NetworkingState.IDLE;
                processingDevice.isProvisionedAndNotKeyBind = true;
                processingDevice.veryLowBattery = veryLowBattery;
                processingDevice.addLog(NetworkingDevice.TAG_SCAN, "device found");
                SIGMesh.getInstance().getCache().putDevice(processingDevice.nodeInfo.macAddress, processingDevice);
                NetworkingDeviceWrapper wrapper = new NetworkingDeviceWrapper();
                wrapper.a = processingDevice;
                wrapper.b = String.valueOf(processingDevice.nodeInfo.meshAddress);
                wrapper.c = processingDevice.nodeInfo.macAddress;
                wrapper.d = advertisingData.replace(advertisingData.substring(46, 48), "00");
                MeshLog.e("isMeshProvisionButNotKeybindAdv 且未keybind，advdata修改为:" + wrapper.d);
                return wrapper;
            }
        }
        return null;
    }

    public static NetworkingDeviceWrapper isMeshUnProvisionAdv(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord) {
        byte[] bArr;
        byte[] serviceData = MeshUtils.h(scanRecord, true);
        if (serviceData == null) {
            BluetoothDevice bluetoothDevice2 = bluetoothDevice;
            int i = rssi;
        } else if (serviceData.length < 17) {
            BluetoothDevice bluetoothDevice3 = bluetoothDevice;
            int i2 = rssi;
        } else {
            byte[] deviceUUID = new byte[16];
            System.arraycopy(serviceData, 0, deviceUUID, 0, 16);
            int b = MeshUtils.b(serviceData, 16, 2, ByteOrder.LITTLE_ENDIAN);
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.meshAddress = -1;
            nodeInfo.deviceUUID = deviceUUID;
            nodeInfo.macAddress = bluetoothDevice.getAddress().replace(":", "").toUpperCase();
            nodeInfo.protocolVersion = 0;
            NetworkingDevice processingDevice = new NetworkingDevice(nodeInfo);
            processingDevice.bluetoothDevice = bluetoothDevice;
            processingDevice.rssi = rssi;
            processingDevice.state = NetworkingState.IDLE;
            processingDevice.addLog(NetworkingDevice.TAG_SCAN, "device found");
            MeshUtils.AdvertiseDataUnit dataUnit = MeshUtils.n(scanRecord).get((byte) -1);
            if (dataUnit == null || (bArr = dataUnit.c) == null || bArr.length <= 0) {
                return null;
            }
            String advertisingData = bytesToHex(bArr);
            MeshLog.v("发现新协议mesh设备 计算后回传给web的广播包:" + advertisingData);
            byte[] bArr2 = dataUnit.c;
            if (bArr2.length > 22) {
                processingDevice.nodeInfo.protocolVersion = bArr2[22];
                MeshLog.e("协议版本isMeshUnProvisionAdv:新协议mesh设备:" + nodeInfo.macAddress + ",协议版本:" + dataUnit.c[22]);
            }
            byte[] bArr3 = dataUnit.c;
            if (bArr3.length > 23) {
                processingDevice.veryLowBattery = CmdCtrl.getValueByBitPosition(bArr3[23], 2, 1);
            }
            SIGMesh.getInstance().getCache().putDevice(processingDevice.nodeInfo.macAddress, processingDevice);
            NetworkingDeviceWrapper wrapper = new NetworkingDeviceWrapper();
            wrapper.a = processingDevice;
            wrapper.b = String.valueOf(processingDevice.nodeInfo.meshAddress);
            wrapper.c = processingDevice.nodeInfo.macAddress;
            wrapper.d = advertisingData;
            return wrapper;
        }
        MeshLogger.e("发一些设备不符合我们的校验规则  serviceData error address:" + bluetoothDevice.getAddress() + "  deviceName=" + bluetoothDevice.getName(), 0);
        return null;
    }

    public static String bytesToHex(byte[] bytes) {
        int a;
        char[] buf = new char[(bytes.length * 2)];
        int index = 0;
        for (byte b : bytes) {
            if (b < 0) {
                a = b + 256;
            } else {
                a = b;
            }
            int index2 = index + 1;
            char[] cArr = HEX_CHAR;
            buf[index] = cArr[a / 16];
            index = index2 + 1;
            buf[index2] = cArr[a % 16];
        }
        return new String(buf);
    }

    public static String formatDate(long time) {
        return dateFormat.format(new Date(time));
    }
}
