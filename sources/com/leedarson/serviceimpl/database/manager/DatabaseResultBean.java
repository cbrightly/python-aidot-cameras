package com.leedarson.serviceimpl.database.manager;

import com.google.gson.JsonArray;
import com.meituan.robust.ChangeQuickRedirect;

public class DatabaseResultBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int code;
    private JsonArray data;
    private String errorStr;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public String getErrorStr() {
        return this.errorStr;
    }

    public void setErrorStr(String errorStr2) {
        this.errorStr = errorStr2;
    }

    public JsonArray getData() {
        return this.data;
    }

    public void setData(JsonArray data2) {
        this.data = data2;
    }
}
