package meshsdk.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomGroup implements Serializable {
    private static final long serialVersionUID = -546449849494L;
    private ArrayList<String> devices = new ArrayList<>();
    private String groupId;

    public CustomGroup(String groupId2) {
        this.groupId = groupId2;
    }

    public void addDevice(String mac) {
        if (!this.devices.contains(mac)) {
            this.devices.add(mac);
        }
    }

    public void removeDevice(String mac) {
        this.devices.remove(mac);
    }

    public String getGroupId() {
        return this.groupId;
    }

    public ArrayList<String> getDevices() {
        return this.devices;
    }

    public String toString() {
        return "CustomGroup{devices=" + this.devices + '}';
    }
}
