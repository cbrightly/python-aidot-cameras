package meshsdk.model.json;

public class BatteryPropertyBean {
    private int acState = 0;
    private int battery;
    private int chargeState = -1;
    private boolean isCache;

    public int getBattery() {
        return this.battery;
    }

    public void setBattery(int battery2) {
        this.battery = battery2;
    }

    public int getChargeState() {
        return this.chargeState;
    }

    public void setChargeState(int chargeState2) {
        this.chargeState = chargeState2;
    }

    public void setAcState(int acState2) {
        this.acState = acState2;
    }

    public int getAcState() {
        return this.acState;
    }

    public boolean isCache() {
        return this.isCache;
    }

    public void setCache(boolean cache) {
        this.isCache = cache;
    }
}
