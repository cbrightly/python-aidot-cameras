package meshsdk.sql;

import org.json.JSONException;
import org.json.JSONObject;

public class DelCacheNode {
    private String mac;
    private int meshAddr;

    public int getMeshAddr() {
        return this.meshAddr;
    }

    public void setMeshAddr(int meshAddr2) {
        this.meshAddr = meshAddr2;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac2) {
        this.mac = mac2;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mac", (Object) this.mac).put("meshAddr", this.meshAddr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
