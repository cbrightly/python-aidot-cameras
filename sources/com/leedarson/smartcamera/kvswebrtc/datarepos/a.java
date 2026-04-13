package com.leedarson.smartcamera.kvswebrtc.datarepos;

import android.text.TextUtils;
import android.util.Log;
import com.leedarson.base.application.BaseApplication;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tencent.bugly.Bugly;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/* compiled from: HardDecodeCompatMarkRepos */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    String a = "h265flag.conf";

    public void c(boolean flagIsSupport) {
        if (!PatchProxy.proxy(new Object[]{new Byte(flagIsSupport ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 9933, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            File file = new File(BaseApplication.b().getCacheDir(), this.a);
            if (!file.exists()) {
                try {
                    d(flagIsSupport ? "true" : Bugly.SDK_IS_DEV, file.getAbsolutePath());
                } catch (Exception e) {
                    timber.log.a.g("hardDecodeCache").m("fail to create cache file of hardcode", new Object[0]);
                }
            }
        }
    }

    public boolean a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9934, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        File file = new File(BaseApplication.b().getCacheDir(), this.a);
        if (!file.exists()) {
            return true;
        }
        String result = b(file.getAbsolutePath());
        if (TextUtils.isEmpty(result)) {
            return true;
        }
        return result.contains("true");
    }

    /* access modifiers changed from: package-private */
    public String b(String path) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 9935, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String str = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
            while (true) {
                String readLine = br.readLine();
                String mimeTypeLine = readLine;
                if (readLine == null) {
                    break;
                }
                str = str + mimeTypeLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /* access modifiers changed from: package-private */
    public void d(String strcontent, String filePath) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{strcontent, filePath}, this, changeQuickRedirect, false, 9936, clsArr, Void.TYPE).isSupported) {
            String strContent = strcontent;
            try {
                File file = new File(filePath);
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                raf.seek(file.length());
                raf.write(strContent.getBytes());
                raf.close();
            } catch (Exception e) {
                Log.e("TestFile", "Error on write File:" + e);
            }
        }
    }
}
