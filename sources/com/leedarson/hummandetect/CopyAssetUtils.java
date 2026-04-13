package com.leedarson.hummandetect;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CopyAssetUtils {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void copyAssets(Context context, String path) {
        if (!PatchProxy.proxy(new Object[]{context, path}, (Object) null, changeQuickRedirect, true, 1131, new Class[]{Context.class, String.class}, Void.TYPE).isSupported) {
            File file = new File(context.getFilesDir().getAbsolutePath(), new File(path).getName());
            if (!file.exists()) {
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    InputStream is = context.getAssets().open(path);
                    byte[] b = new byte[2048];
                    while (true) {
                        int read = is.read(b);
                        int len = read;
                        if (read != -1) {
                            fos.write(b, 0, len);
                        } else {
                            fos.close();
                            is.close();
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
