package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.d;
import com.leedarson.serviceimpl.tcp.TcpServiceImpl;
import java.util.Map;

public class ARouter$$Providers$$LdsTcp implements d {
    public void loadInto(Map<String, a> providers) {
        providers.put("com.leedarson.serviceinterface.TcpService", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, TcpServiceImpl.class, "/tcpservice/tcp", "tcpservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
