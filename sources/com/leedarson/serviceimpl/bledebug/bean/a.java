package com.leedarson.serviceimpl.bledebug.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: BleDebugConfig */
public class a implements Serializable {
    public static final int ENV_CONNECT_SERIAL = 2;
    public static final int ENV_CONNECT_WITH_SCAN_DEV = 4;
    public static final int ENV_MAIN_THREAD_BUSY = 1;
    public static ChangeQuickRedirect changeQuickRedirect;
    public boolean closeWhenConnected = false;
    public int connectInterval = 0;
    public int env = 0;
    public ArrayList<String> macList;
    public boolean opInBackground = false;
    public ReconnectParams reconnectParams = new ReconnectParams(3, 3000);
    public int testCount = 100;

    public ArrayList<String> setMacList(JSONArray array) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{array}, this, changeQuickRedirect, false, 6653, new Class[]{JSONArray.class}, ArrayList.class);
        if (proxy.isSupported) {
            return (ArrayList) proxy.result;
        }
        this.macList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                this.macList.add(array.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this.macList;
    }

    public boolean containEnv(int flag) {
        return (this.env & flag) != 0;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6654, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "{" + "\"env\":" + this.env + ",\"connectInterval\":" + this.connectInterval + ",\"macList\":" + this.macList.size() + '}';
    }
}
