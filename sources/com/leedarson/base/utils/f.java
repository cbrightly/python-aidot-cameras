package com.leedarson.base.utils;

import android.text.TextUtils;
import com.leedarson.base.utils.b;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.security.SecureRandom;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: BleWriteAndReadEncrypeUtils */
public class f {
    private static String a = "";
    public static ChangeQuickRedirect changeQuickRedirect;

    private static String b(int length) {
        Object[] objArr = {new Integer(length)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 460, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        SecureRandom random = new SecureRandom();
        random.setSeed(System.nanoTime());
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append((char) random.nextInt(NeedPermissionEvent.PER_IPC_SPEAK_PERM));
        }
        return stringBuffer.toString();
    }

    public static byte[] d(String key, String data) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key, data}, (Object) null, changeQuickRedirect, true, 461, new Class[]{cls, cls}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : c(key, data, "");
    }

    public static byte[] c(String key, String data, String cusRamdom) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key, data, cusRamdom}, (Object) null, changeQuickRedirect, true, 462, new Class[]{cls, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (TextUtils.isEmpty(a)) {
            a = b(16);
        }
        String tempRandom = a;
        if (!TextUtils.isEmpty(cusRamdom)) {
            tempRandom = cusRamdom;
        }
        String kv = "【使用带随机偏移量加密方式】encrypt key:" + key + ",data:" + data + ",ivRandom:" + tempRandom;
        a.i("encrypt256WithIvRandom 秘钥=" + key, new Object[0]);
        a.i("encrypt256WithIvRandom 偏移量：" + tempRandom, new Object[0]);
        a.i("encrypt256WithIvRandom 原数据:" + data, new Object[0]);
        a.i("encrypt256WithIvRandom 原数据内容HexString:" + e.a(data.getBytes()), new Object[0]);
        String str = data;
        a.i("encrypt256WithIvRandom  data.replace 原数据内容HexString:" + e.a(str.getBytes()), new Object[0]);
        try {
            byte[] randomBytes = tempRandom.getBytes();
            byte[] encryptBytes = b.g(randomBytes, b.a.AES256, key, str.getBytes());
            a.i("encrypt256WithIvRandom 密文HexString: " + e.a(encryptBytes), new Object[0]);
            byte[] sendBytes = new byte[(randomBytes.length + encryptBytes.length)];
            System.arraycopy(randomBytes, 0, sendBytes, 0, randomBytes.length);
            System.arraycopy(encryptBytes, 0, sendBytes, randomBytes.length, encryptBytes.length);
            a.i("encrypt256WithIvRandom 偏移量+原数据内容HexString:" + e.a(sendBytes), new Object[0]);
            return sendBytes;
        } catch (Exception e) {
            a.c("encrypt err:" + e.toString() + ",kv=" + kv, new Object[0]);
            e.printStackTrace();
            return data.getBytes();
        }
    }

    public static void a(JSONObject json) {
        if (!PatchProxy.proxy(new Object[]{json}, (Object) null, changeQuickRedirect, true, 464, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            String afterDealData = json.toString().replace("\\/", "/");
            int crc = h.a(afterDealData.getBytes());
            a.i("crcDebug crc str:" + json.toString() + ",result:" + crc + "  after:=" + afterDealData, new Object[0]);
            json.put("CRC", crc);
            StringBuilder sb = new StringBuilder();
            sb.append("crcDebug crc str: after deal data=");
            sb.append(afterDealData);
            sb.append("  json=");
            sb.append(json.toString());
            a.i(sb.toString(), new Object[0]);
        }
    }
}
