package meshsdk.model.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CloudGroup {
    private List<String> deviceIds;
    @SerializedName("bleMeshGroupAddress")
    private int groupAddress;
    private int id;
    private String roomId;
    private String type;
    private int visible;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public int getVisible() {
        return this.visible;
    }

    public void setVisible(int visible2) {
        this.visible = visible2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public int getGroupAddress() {
        return this.groupAddress;
    }

    public void setGroupAddress(int groupAddress2) {
        this.groupAddress = groupAddress2;
    }

    public List<String> getDeviceIds() {
        return this.deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds2) {
        this.deviceIds = deviceIds2;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String roomId2) {
        this.roomId = roomId2;
    }

    public String toString() {
        return "{id=" + this.id + ",groupAddress=" + this.groupAddress + ", type='" + this.type + '\'' + '}';
    }
}
