package com.leedarson.serviceimpl.blec075.util;

import com.leedarson.base.beans.CommonBleReadConfigBean;
import com.leedarson.base.utils.b;
import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.blec075.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import timber.log.a;

/* compiled from: BleDecryptDatasFromDevice */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static byte[] a(CommonBleReadConfigBean configBean, byte[] datas) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{configBean, datas}, (Object) null, changeQuickRedirect, true, 6604, new Class[]{CommonBleReadConfigBean.class, byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] recdeC = null;
        try {
            if (CommonBleReadConfigBean.ENCRYPT_AES_128.equals(configBean.encrypt)) {
                recdeC = b.a(configBean.encryptKey, datas);
            } else if (!CommonBleReadConfigBean.ENCRYPT_AES_256_CTR.equals(configBean.encrypt)) {
                recdeC = h.e(configBean.encryptKey, datas);
            } else if (datas.length >= 16) {
                String rawHexString = e.a(datas);
                a.i("====================================================\nBleDecryptDatasFromDevice  原数据转化为HexString:" + rawHexString, new Object[0]);
                String offsetHexString = e.a(datas).substring(0, 32);
                a.i("BleDecryptDatasFromDevice  HexString前32位数据：" + offsetHexString, new Object[0]);
                String offsetString = b(offsetHexString);
                a.i("BleDecryptDatasFromDevice  偏移量：" + offsetString, new Object[0]);
                a.i("BleDecryptDatasFromDevice  密码：" + configBean.encryptKey, new Object[0]);
                a.i("BleDecryptDatasFromDevice  加密类型：" + configBean.encrypt, new Object[0]);
                String contentHexString = rawHexString.substring(32, rawHexString.length());
                a.i("BleDecryptDatasFromDevice  数据内容HexString密文：" + contentHexString, new Object[0]);
                a.i("BleDecryptDatasFromDevice  加密前数据 =" + e.a(datas), new Object[0]);
                recdeC = h.f(e.g(offsetHexString), b.a.AES256, configBean.encryptKey, e.g(contentHexString));
            }
            if (recdeC != null) {
                a.i("BleDecryptDatasFromDevice 解密后数据 =" + b(e.a(recdeC)), new Object[0]);
            }
        } catch (Exception e) {
            a.b g = a.g("BleBaseCallback");
            g.c("decryerror=" + e.getMessage(), new Object[0]);
            e.printStackTrace();
        }
        return recdeC;
    }

    public static String b(String s) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, (Object) null, changeQuickRedirect, true, 6605, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (s == null || s.equals("")) {
            return null;
        }
        String s2 = s.replace(" ", "");
        byte[] baKeyword = new byte[(s2.length() / 2)];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (Integer.parseInt(s2.substring(i * 2, (i * 2) + 2), 16) & 255);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s2 = new String(baKeyword, "utf-8");
            new String();
            return s2;
        } catch (Exception e1) {
            e1.printStackTrace();
            return s2;
        }
    }
}
