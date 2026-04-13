package com.leedarson.utils;

import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.io.RandomAccessFile;

/* compiled from: LDSFileUtils */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void c(String strcontent, String filePath, String fileName) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{strcontent, filePath, fileName}, (Object) null, changeQuickRedirect, true, 11497, new Class[]{cls, cls, cls}, Void.TYPE).isSupported) {
            a(filePath, fileName);
            String strFilePath = filePath + fileName;
            String strContent = strcontent + "\r\n";
            try {
                File file = new File(strFilePath);
                if (!file.exists()) {
                    Log.d("TestFile", "Create the file:" + strFilePath);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                raf.seek(file.length());
                raf.write(strContent.getBytes());
                raf.close();
            } catch (Exception e) {
                Log.e("TestFile", "Error on write File:" + e);
            }
        }
    }

    private static File a(String filePath, String fileName) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath, fileName}, (Object) null, changeQuickRedirect2, true, 11498, new Class[]{cls, cls}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        File file = null;
        b(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private static void b(String filePath) {
        if (!PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 11499, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    file.mkdir();
                }
            } catch (Exception e) {
                Log.i("error:", e + "");
            }
        }
    }
}
