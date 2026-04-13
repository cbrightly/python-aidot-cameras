package smarthome.bean;

import com.google.gson.JsonObject;

public class H5NativeBean {
    private String action;
    private JsonObject data;
    private String service;

    public String getService() {
        return this.service;
    }

    public void setService(String service2) {
        this.service = service2;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }

    public JsonObject getData() {
        return this.data;
    }

    public void setData(JsonObject data2) {
        this.data = data2;
    }
}
