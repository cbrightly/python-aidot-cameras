package meshsdk.model.json;

import com.google.gson.Gson;

public class OTAProgressBean {
    public int countdown = 0;
    public String desc;
    public String mac;
    public int progress;
    public int stage;

    public String parseJsonStr() {
        return new Gson().toJson((Object) this);
    }
}
