package com.leedarson.smarthome.robust;

import android.content.Context;
import android.util.Log;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.w;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.Patch;
import com.meituan.robust.PatchManipulate;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.meituan.robust.RobustApkHashUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PatchManipulateImp */
public class f extends PatchManipulate {
    public static ChangeQuickRedirect changeQuickRedirect;

    public List<Patch> fetchPatchList(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 10684, new Class[]{Context.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        String robustApkHash = RobustApkHashUtils.readRobustApkHash(context);
        Log.w("robust", "robustApkHash :" + robustApkHash);
        Patch patch = new Patch();
        patch.setName(new File(b()).getName());
        patch.setLocalPath(b().replace(".jar", ""));
        patch.setPatchesInfoImplClassFullName("com.leedarson.smarthome.robust.PatchesInfoImpl");
        List patches = new ArrayList();
        patches.add(patch);
        return patches;
    }

    public boolean verifyPatch(Context context, Patch patch) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, patch}, this, changeQuickRedirect, false, 10685, new Class[]{Context.class, Patch.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        patch.setTempPath(c());
        try {
            a(patch.getLocalPath(), patch.getTempPath());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("robust", "copy source patch to local patch error, no patch execute in path  Path verifyPatch exception:" + e.toString());
            throw new RuntimeException("copy source patch to local patch error, no patch execute in path " + patch.getTempPath());
        }
    }

    public void a(String srcPath, String dstPath) {
        OutputStream out;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{srcPath, dstPath}, this, changeQuickRedirect, false, 10686, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            File src = new File(srcPath);
            if (src.exists()) {
                File dst = new File(dstPath);
                if (!dst.getParentFile().exists()) {
                    dst.getParentFile().mkdirs();
                }
                InputStream in = new FileInputStream(src);
                try {
                    out = new FileOutputStream(dst);
                    byte[] buf = new byte[1024];
                    while (true) {
                        int read = in.read(buf);
                        int len = read;
                        if (read > 0) {
                            out.write(buf, 0, len);
                        } else {
                            out.close();
                            in.close();
                            return;
                        }
                    }
                } catch (Throwable th) {
                    in.close();
                    throw th;
                }
            } else {
                Log.i("robust", "copy source patch does not exist");
                throw new RuntimeException("source patch does not exist ");
            }
        }
    }

    public boolean ensurePatchExist(Patch patch) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{patch}, this, changeQuickRedirect, false, 10687, new Class[]{Patch.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return new File(patch.getLocalPath()).exists();
    }

    public String b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10688, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String patchesFolderPath = BaseApplication.b().getFilesDir() + File.separator + "robust_patches";
        File patchesFolder = new File(patchesFolderPath);
        if (!patchesFolder.exists()) {
            patchesFolder.mkdir();
        }
        File[] listFile = patchesFolder.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].getName().contains(w.H(BaseApplication.b()) + "_" + "2979")) {
                    return listFile[i].getAbsolutePath();
                }
            }
        }
        return patchesFolderPath + File.separator + "patch";
    }

    public String c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10689, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(BaseApplication.b().getFilesDir());
        String str = File.separator;
        sb.append(str);
        sb.append("robust_patches");
        String patchesFolderPath = sb.toString();
        File patchesFolder = new File(patchesFolderPath);
        if (!patchesFolder.exists()) {
            patchesFolder.mkdir();
        }
        return patchesFolderPath + str + "patch_temp.jar";
    }
}
