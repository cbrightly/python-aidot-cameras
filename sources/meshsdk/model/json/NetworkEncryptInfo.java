package meshsdk.model.json;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkEncryptInfo {
    public int IVIndex = 0;
    public String appKey = "";
    public int appKeyIndex = 0;
    public String exception;
    public String meshUUID;
    public String netKey = "";
    public int netKeyIndex = 0;

    public JSONObject toJSON() {
        try {
            return new JSONObject(new Gson().toJson((Object) this));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }
}
