package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsPlayer implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.IpcService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, IpcServiceImpl.class, "/ipcservice/ipc", "ipcservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
