package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.udp.UdpServiceImpl;
import java.util.Map;

public class ARouter$$Group$$udpservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/udpservice/udp", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, UdpServiceImpl.class, "/udpservice/udp", "udpservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
