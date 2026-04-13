package meshsdk.model.json;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SecuAlarmRule {
    public int anytime;
    public int enable;
    public int endHour;
    public int endMinute;
    public String mac;
    public int repeat;
    public int startHour;
    public int startMinute;
    public List<Integer> timeSpan = new ArrayList();

    public byte[] getTimeRange() {
        if (this.anytime == 1) {
            return new byte[]{-1, -1, -1, -1};
        }
        return new byte[]{(byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
    }

    public void fixAnyTime() {
        if (this.startMinute == -1 && this.startHour == -1 && this.endHour == -1 && this.endMinute == -1) {
            this.anytime = 1;
        } else {
            this.anytime = 0;
        }
    }

    public void convertTimespan() {
        if (this.timeSpan.size() >= 2) {
            this.startHour = this.timeSpan.get(0).intValue();
            this.startMinute = this.timeSpan.get(1).intValue();
        }
        if (this.timeSpan.size() == 4) {
            this.endHour = this.timeSpan.get(2).intValue();
            this.endMinute = this.timeSpan.get(3).intValue();
        }
    }

    public void setTimespan(int... param) {
        ArrayList arrayList = new ArrayList();
        this.timeSpan = arrayList;
        arrayList.clear();
        for (int valueOf : param) {
            this.timeSpan.add(Integer.valueOf(valueOf));
        }
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
