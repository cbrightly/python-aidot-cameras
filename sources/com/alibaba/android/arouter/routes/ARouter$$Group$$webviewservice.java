package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.serviceimpl.webview.WebViewServiceImpl;
import java.util.Map;

public class ARouter$$Group$$webviewservice implements e {
    public void loadInto(Map<String, a> atlas) {
        atlas.put("/webviewservice/webview", a.a(com.alibaba.android.arouter.facade.enums.a.PROVIDER, WebViewServiceImpl.class, "/webviewservice/webview", "webviewservice", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
