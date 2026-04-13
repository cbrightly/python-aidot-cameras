package com.leedarson.base.http.exception;

import android.os.NetworkOnMainThreadException;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.utils.g;
import com.leedarson.base.utils.m;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLHandshakeException;
import leedarson.platform.playersdk.PlayerStateDefine;
import retrofit2.HttpException;

/* compiled from: ExceptionEngine */
public class a {
    private static long a = 0;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static ApiException a(Throwable e) {
        String errMsg;
        String str = "";
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{e}, (Object) null, changeQuickRedirect, true, Opcodes.GOTO, new Class[]{Throwable.class}, ApiException.class);
        if (proxy.isSupported) {
            return (ApiException) proxy.result;
        }
        if (e instanceof HttpException) {
            try {
                String str2 = str;
                LinkedTreeMap linkedTreeMap = m.b(((HttpException) e).response().d().string());
                HttpException httpExc = (HttpException) e;
                ApiException ex = new ApiException(e, httpExc.code());
                if (linkedTreeMap != null) {
                    double errCode = ((Double) linkedTreeMap.get("code")).doubleValue();
                    if (linkedTreeMap.containsKey("message")) {
                        str = linkedTreeMap.get("message").toString();
                    }
                    String errMsg2 = str;
                    if (linkedTreeMap.containsKey("desc")) {
                        errMsg = linkedTreeMap.get("desc").toString();
                    } else {
                        errMsg = errMsg2;
                    }
                    ex.setCode((int) errCode);
                    ex.setMsg(errMsg);
                } else {
                    ex.setMsg(httpExc.getMessage());
                }
                return ex;
            } catch (Exception e2) {
                new ApiException(e, -1000).setMsg(e.getMessage());
                return null;
            }
        } else if (e instanceof ServerException) {
            ServerException serverExc = (ServerException) e;
            ApiException ex2 = new ApiException((Throwable) serverExc, serverExc.getCode());
            ex2.setMsg(serverExc.getMsg());
            return ex2;
        } else if (e instanceof ConnectException) {
            ApiException ex3 = new ApiException(e, (int) PlayerStateDefine.EC_CREATE_PLAYER_MAX);
            ex3.setMsg(" The network disconnected.Please Check the network !");
            return ex3;
        } else if (e instanceof UnknownHostException) {
            ApiException ex4 = new ApiException(e, (int) PlayerStateDefine.EC_SYS_ERROR);
            ex4.setMsg(" unknown host .Please Check the network !");
            b();
            return ex4;
        } else if (e instanceof SocketTimeoutException) {
            ApiException ex5 = new ApiException(e, -1001);
            ex5.setMsg("Network Timeout");
            return ex5;
        } else if (e instanceof SSLHandshakeException) {
            ApiException ex6 = new ApiException(e, -1000);
            ex6.setMsg("Unreachable server, please check the network." + e.toString());
            return ex6;
        } else if (e instanceof ApiException) {
            return (ApiException) e;
        } else {
            if (e instanceof NetworkOnMainThreadException) {
                ApiException ex7 = new ApiException(e, (int) PlayerStateDefine.EC_GET_WINDOW_HANDLE_FAILED);
                ex7.setMsg(e.getMessage());
                return ex7;
            }
            ApiException ex8 = new ApiException(e, -1000);
            ex8.setMsg(e.getMessage());
            return ex8;
        }
    }

    private static void b() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 168, new Class[0], Void.TYPE).isSupported && System.currentTimeMillis() - a > 600000) {
            a = System.currentTimeMillis();
            g.a();
        }
    }
}
