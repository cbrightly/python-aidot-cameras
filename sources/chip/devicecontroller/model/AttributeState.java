package chip.devicecontroller.model;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public final class AttributeState {
    private static final String TAG = "AttributeState";
    private JSONObject json;
    private byte[] tlv;
    private Object valueObject;

    public AttributeState(Object valueObject2, byte[] tlv2, String jsonString) {
        this.valueObject = valueObject2;
        this.tlv = tlv2;
        try {
            this.json = new JSONObject(jsonString);
        } catch (JSONException ex) {
            Log.e(TAG, "Error parsing JSON string", ex);
        }
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
}
