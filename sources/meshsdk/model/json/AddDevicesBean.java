package meshsdk.model.json;

import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.reporters.b;
import com.luck.picture.lib.config.PictureConfig;
import com.telink.ble.mesh.foundation.parameter.ProvisioningParameters;
import meshsdk.ctrl.BindCtrl;
import meshsdk.ctrl.PublishCtrl;
import meshsdk.model.NetworkingDevice;
import meshsdk.model.NodeInfo;
import org.json.JSONObject;

public class AddDevicesBean {
    private static final int accessFlag = 4;
    private String failMessage;
    public transient boolean isUnMatchMacDevice = false;
    public transient BindCtrl mBindCtrl = null;
    public transient ProvisioningParameters mProvisionParams = null;
    public transient PublishCtrl mPublishCtrl = null;
    private String mac;
    public MeshOObResponseBean meshOObResponseBean;
    public MeshDeviceInfoResponseBean meshSumitResponseBean;
    private String modelId;
    public transient NetworkingDevice networkingDevice;
    private NodeInfo nodeInfo;
    private long reportTraceId;
    private int resultCode;
    private int rssi;
    public transient long startToConnectBleTimeStamp;
    private transient b trackReport;

    public String getFailMessage() {
        return this.failMessage;
    }

    public void setFailMessage(String failMessage2) {
        this.failMessage = failMessage2;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac2) {
        this.mac = mac2;
    }

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId2) {
        this.modelId = modelId2;
    }

    public void setTrackReport(b trackReport2) {
        this.trackReport = trackReport2;
    }

    public b getTrackReport() {
        return this.trackReport;
    }

    public int getBleMeshAddr() {
        NodeInfo nodeInfo2 = this.nodeInfo;
        if (nodeInfo2 != null) {
            return nodeInfo2.meshAddress;
        }
        return 0;
    }

    public String getBleMeshDeviceUuid() {
        NodeInfo nodeInfo2 = this.nodeInfo;
        if (nodeInfo2 != null) {
            return e.a(nodeInfo2.deviceUUID).toUpperCase();
        }
        return "";
    }

    public String getBleMeshDeviceKey() {
        NodeInfo nodeInfo2 = this.nodeInfo;
        if (nodeInfo2 != null) {
            return e.a(nodeInfo2.deviceKey).toUpperCase();
        }
        return "";
    }

    public int getBleMeshProtocolVersion() {
        NodeInfo nodeInfo2 = this.nodeInfo;
        if (nodeInfo2 != null) {
            return nodeInfo2.protocolVersion;
        }
        return -1;
    }

    public void setNodeInfo(NodeInfo nodeInfo2) {
        this.nodeInfo = nodeInfo2;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int rssi2) {
        this.rssi = rssi2;
    }

    public int getAccessFlag() {
        return 4;
    }

    public void setResultCode(int resultCode2) {
        this.resultCode = resultCode2;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    private MeshOObResponseBean getMeshOObResponseBean() {
        return this.meshOObResponseBean;
    }

    public void setMeshOObResponseBean(MeshOObResponseBean meshOObResponseBean2) {
        this.meshOObResponseBean = meshOObResponseBean2;
    }

    public MeshDeviceInfoResponseBean getMeshSumitResponseBean() {
        return this.meshSumitResponseBean;
    }

    public void setMeshSumitResponseBean(MeshDeviceInfoResponseBean meshSumitResponseBean2) {
        this.meshSumitResponseBean = meshSumitResponseBean2;
    }

    public String getOObData() {
        if (getMeshOObResponseBean() != null) {
            return getMeshOObResponseBean().getAuthValue();
        }
        return "";
    }

    public String getUuid() {
        if (getMeshOObResponseBean() != null) {
            return getMeshOObResponseBean().getUuid();
        }
        return "";
    }

    public void setReportTraceId(long reportTraceId2) {
        this.reportTraceId = reportTraceId2;
    }

    public long getReportTraceId() {
        return this.reportTraceId;
    }

    public static class MeshOObResponseBean {
        private String authValue;
        private String code;
        private String mac;
        private String uuid;

        public String getCode() {
            return this.code;
        }

        public void setCode(String code2) {
            this.code = code2;
        }

        public String getAuthValue() {
            return this.authValue;
        }

        public void setAuthValue(String authValue2) {
            this.authValue = authValue2;
        }

        public String getUuid() {
            return this.uuid;
        }

        public void setUuid(String uuid2) {
            this.uuid = uuid2;
        }

        public String getMac() {
            return this.mac;
        }

        public void setMac(String mac2) {
            this.mac = mac2;
        }
    }

    public static class MeshDeviceInfoResponseBean {
        private String brandId;
        private String brandName;
        private String devId;
        private String icon;
        private String id;
        private String name;
        private String picture;

        public JSONObject generateJSONObject(JSONObject jsonObject) {
            if (jsonObject == null) {
                jsonObject = new JSONObject();
            }
            try {
                jsonObject.put("id", (Object) this.id);
                jsonObject.put("name", (Object) this.name);
                jsonObject.put(PictureConfig.EXTRA_FC_TAG, (Object) this.picture);
                jsonObject.put("icon", (Object) this.icon);
                jsonObject.put("devId", (Object) this.devId);
            } catch (Exception e) {
            }
            return jsonObject;
        }

        public String getDevId() {
            return this.devId;
        }

        public void setDevId(String devId2) {
            this.devId = devId2;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id2) {
            this.id = id2;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name2) {
            this.name = name2;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String icon2) {
            this.icon = icon2;
        }

        public String getPicture() {
            return this.picture;
        }

        public void setPicture(String picture2) {
            this.picture = picture2;
        }

        public String getBrandId() {
            return this.brandId;
        }

        public void setBrandId(String brandId2) {
            this.brandId = brandId2;
        }

        public String getBrandName() {
            return this.brandName;
        }

        public void setBrandName(String brandName2) {
            this.brandName = brandName2;
        }
    }
}
