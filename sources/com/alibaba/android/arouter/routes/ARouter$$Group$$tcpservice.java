package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.tcp.TcpServiceImpl;
import java.util.Map;

public class ARouter$$Group$$tcpservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/tcpservice/tcp", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, TcpServiceImpl.class, "/tcpservice/tcp", "tcpservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
