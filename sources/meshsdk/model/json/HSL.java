package meshsdk.model.json;

import com.google.gson.Gson;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class HSL implements Serializable {
    private static final long serialVersionUID = -546449841494L;
    public int HSLHue;
    public int HSLLightness;
    public int HSLSaturation;

    public HSL(int HSLHue2, int HSLSaturation2, int HSLLightness2) {
        this.HSLHue = HSLHue2;
        this.HSLSaturation = HSLSaturation2;
        this.HSLLightness = HSLLightness2;
    }

    public JSONObject toJson() {
        try {
            return new JSONObject(new Gson().toJson((Object) this));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
