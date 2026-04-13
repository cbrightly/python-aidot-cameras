package com.leedarson.serviceimpl.business.bean;

import com.leedarson.base.utils.w;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import timber.log.a;

public class ClientEncryptBean {
    public static final char[] HEX_BASIC = "0123456789ABCDEF".toCharArray();
    private static String TAG = "BleC075ServiceImpl#ClientConnection";
    public static ChangeQuickRedirect changeQuickRedirect;
    public String aesKey;
    public String randomChar;

    public ClientEncryptBean(String randomChar2, String aesKey2) {
        this.randomChar = randomChar2;
        this.aesKey = aesKey2;
    }

    public static ClientEncryptBean generateRandomKey(BleBusinessConnectBean connectParams) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{connectParams}, (Object) null, changeQuickRedirect, true, 7106, new Class[]{BleBusinessConnectBean.class}, ClientEncryptBean.class);
        if (proxy.isSupported) {
            return (ClientEncryptBean) proxy.result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(connectParams.modelId);
        sb.append(",");
        sb.append(connectParams.mac.replace(":", "").toLowerCase());
        sb.append(",");
        sb.append(connectParams.deviceId);
        sb.append(",");
        sb.append("nghx8qug3lkl5oyd");
        byte[] sha256 = sha256(sb.toString());
        byte[] keyBytes = new byte[16];
        System.arraycopy(sha256, 0, keyBytes, 0, keyBytes.length);
        String aesKey2 = w.b(keyBytes).toLowerCase();
        a.b g = a.g(TAG);
        g.a("\nmodelId+mac+deviceId+randomChar=>" + sb.toString() + "\nsha256 =>" + w.b(sha256) + "\naesKey:" + aesKey2, new Object[0]);
        a.b g2 = a.g(TAG);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("加密：modelId:=");
        sb2.append(connectParams.modelId);
        g2.a(sb2.toString(), new Object[0]);
        a.b g3 = a.g(TAG);
        g3.a("加密：mac:=" + connectParams.mac.replace(":", "").toLowerCase(), new Object[0]);
        a.b g4 = a.g(TAG);
        g4.a("加密：deviceId:=" + connectParams.deviceId, new Object[0]);
        a.b g5 = a.g(TAG);
        g5.a("加密：randomString:=" + "nghx8qug3lkl5oyd", new Object[0]);
        a.b g6 = a.g(TAG);
        g6.a("加密：before:=" + sb.toString(), new Object[0]);
        a.b g7 = a.g(TAG);
        g7.a("加密：aesKey=" + aesKey2, new Object[0]);
        return new ClientEncryptBean("nghx8qug3lkl5oyd", aesKey2);
    }

    private static byte[] sha256(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, (Object) null, changeQuickRedirect, true, 7107, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static String getRandom() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 7108, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            sb.append(HEX_BASIC[random.nextInt(16)]);
        }
        return sb.toString();
    }

    public byte[] getRandomBytes() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7109, new Class[0], byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : this.randomChar.getBytes();
    }
}
