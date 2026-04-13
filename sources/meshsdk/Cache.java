package meshsdk;

import java.util.HashMap;
import meshsdk.model.NetworkingDevice;

public class Cache {
    private HashMap<String, NetworkingDevice> cacheDeviceMap = new HashMap<>();

    public void putDevice(String mac, NetworkingDevice dev) {
        this.cacheDeviceMap.put(mac, dev);
    }

    public NetworkingDevice findDevice(String mac) {
        return this.cacheDeviceMap.get(mac.toUpperCase());
    }
}
