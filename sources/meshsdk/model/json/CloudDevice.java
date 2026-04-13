package meshsdk.model.json;

public class CloudDevice {
    public static final int APP_ACCESS_FLAG_BLEMESH = 4;
    public int accessFlag;
    public int bleMeshAddr;
    public String bleMeshDeviceKey = "";
    public String bleMeshDeviceUuid = "";
    public String id;
    public String mac;
    public String productName;
    public DeviceProperties properties;
    public String roomId;

    public static class DeviceProperties {
        public int passThroughSupport;
    }

    public String toString() {
        return "CloudDevice{mac='" + this.mac + '\'' + ", id='" + this.id + '\'' + ", productName='" + this.productName + '\'' + ", bleMeshDeviceKey='" + this.bleMeshDeviceKey + '\'' + ", bleMeshDeviceUuid='" + this.bleMeshDeviceUuid + '\'' + ", bleMeshAddr=" + this.bleMeshAddr + ", accessFlag=" + this.accessFlag + ", roomId=" + this.roomId + '}';
    }

    public boolean isMeshHub() {
        DeviceProperties deviceProperties = this.properties;
        if (deviceProperties == null || deviceProperties.passThroughSupport <= 0) {
            return false;
        }
        return true;
    }
}
