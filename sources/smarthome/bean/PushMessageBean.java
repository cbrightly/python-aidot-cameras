package smarthome.bean;

public class PushMessageBean {
    private Data data;
    private String subType;
    private String type;

    public Data getData() {
        return this.data;
    }

    public void setData(Data data2) {
        this.data = data2;
    }

    public String getSubType() {
        return this.subType;
    }

    public void setSubType(String subType2) {
        this.subType = subType2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }
}
