package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import java.util.Map;

public class ARouter$$Group$$ipcservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/ipcservice/ipc", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, IpcServiceImpl.class, "/ipcservice/ipc", "ipcservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
