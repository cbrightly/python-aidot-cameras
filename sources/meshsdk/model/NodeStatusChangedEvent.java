package meshsdk.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.telink.ble.mesh.foundation.Event;

public class NodeStatusChangedEvent extends Event<String> implements Parcelable {
    public static final Parcelable.Creator<NodeStatusChangedEvent> CREATOR = new Parcelable.Creator<NodeStatusChangedEvent>() {
        public NodeStatusChangedEvent createFromParcel(Parcel in) {
            return new NodeStatusChangedEvent(in);
        }

        public NodeStatusChangedEvent[] newArray(int size) {
            return new NodeStatusChangedEvent[size];
        }
    };
    public static final String EVENT_TYPE_NODE_STATUS_CHANGED = "com.telink.ble.mesh.EVENT_TYPE_NODE_STATUS_CHANGED";
    private int modelId;
    private NodeInfo nodeInfo;

    public NodeStatusChangedEvent(Object sender, String type, NodeInfo nodeInfo2, int modelId2) {
        super(sender, type);
        this.nodeInfo = nodeInfo2;
        this.modelId = modelId2;
    }

    protected NodeStatusChangedEvent(Parcel in) {
    }

    public NodeInfo getNodeInfo() {
        return this.nodeInfo;
    }

    public int getModelId() {
        return this.modelId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }
}
