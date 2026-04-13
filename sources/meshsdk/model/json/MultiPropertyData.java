package meshsdk.model.json;

public class MultiPropertyData {
    public int acState = 0;
    public int battery = -1;
    public int chargeState = -1;
    public int dim;
    public int onoff;
    public long timestamp;
    public String version;

    public boolean isExpire() {
        return System.currentTimeMillis() - this.timestamp > 10000;
    }

    public String toString() {
        return "MultiStatusData{version='" + this.version + '\'' + ", onoff=" + this.onoff + ", dim=" + this.dim + ", battery=" + this.battery + '}';
    }
}
