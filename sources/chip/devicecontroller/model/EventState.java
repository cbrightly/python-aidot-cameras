package chip.devicecontroller.model;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public final class EventState {
    private static final String TAG = "EventState";
    private long eventNumber;
    private JSONObject json;
    private int priorityLevel;
    private long systemTimeStamp;
    private byte[] tlv;
    private Object valueObject;

    public EventState(long eventNumber2, int priorityLevel2, long systemTimeStamp2, Object valueObject2, byte[] tlv2, String jsonString) {
        this.eventNumber = eventNumber2;
        this.priorityLevel = priorityLevel2;
        this.systemTimeStamp = systemTimeStamp2;
        this.valueObject = valueObject2;
        this.tlv = tlv2;
        try {
            this.json = new JSONObject(jsonString);
        } catch (JSONException ex) {
            Log.e(TAG, "Error parsing JSON string", ex);
        }
    }

    public long getEventNumber() {
        return this.eventNumber;
    }

    public int getPriorityLevel() {
        return this.priorityLevel;
    }

    public long getSystemTimeStamp() {
        return this.systemTimeStamp;
    }

    public Object getValue() {
        return this.valueObject;
    }

    public byte[] getTlv() {
        return this.tlv;
    }

    public JSONObject getJson() {
        return this.json;
    }

    public String toString() {
        return this.valueObject.toString();
    }
}
