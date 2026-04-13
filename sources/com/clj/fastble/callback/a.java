package com.clj.fastble.callback;

import android.os.Handler;
import com.leedarson.base.beans.CommonBleReadConfigBean;
import com.leedarson.serviceimpl.blec075.util.d;

/* compiled from: BleBaseCallback */
public abstract class a {
    private Handler handler;
    private String key;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public Handler getHandler() {
        return this.handler;
    }

    public void setHandler(Handler handler2) {
        this.handler = handler2;
    }

    public byte[] decryptDataByConfig(CommonBleReadConfigBean configBean, byte[] datas) {
        return d.a(configBean, datas);
    }
}
