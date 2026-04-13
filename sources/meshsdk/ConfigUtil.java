package meshsdk;

import android.content.Context;
import android.text.TextUtils;
import com.leedarson.serviceinterface.utils.FileUtils;
import com.telink.ble.mesh.util.FileSystem;
import java.io.File;
import java.util.concurrent.Callable;

public class ConfigUtil {
    public static final String FILE_NAME = "com.leedarson.ble.mesh.STORAGE";
    public static final String VERSION_FILE = "version";

    public static String getConfigPath(Context context, String houseId) {
        if (TextUtils.isEmpty(houseId)) {
            MeshLogNew.e("获取meshjson本地文件路径失败,houseId为空");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("androidMesh");
        String str = File.separator;
        sb.append(str);
        sb.append(houseId);
        String configPath = sb.toString();
        File file = new File(context.getFilesDir() + str + configPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static Object[] readAsObject(Context context, String houseId) {
        MeshLog.d("readAsObject houseId:" + houseId);
        return FileSystem.a(context, getConfigPath(context, houseId), "com.leedarson.ble.mesh.STORAGE");
    }

    public static boolean writeAsObject(Context context, String houseId, Object obj) {
        MeshLog.d("writeAsObject houseId:" + houseId);
        String configPath = getConfigPath(context, houseId);
        if (configPath == null) {
            return false;
        }
        return FileSystem.c(context, configPath, "com.leedarson.ble.mesh.STORAGE", obj);
    }

    public static class DeleteVersionTask implements Callable<Integer> {
        private Context context;
        private String houseId;

        public DeleteVersionTask(Context context2, String houseId2) {
            this.houseId = houseId2;
            this.context = context2;
        }

        public Integer call() {
            FileUtils.deleteDir(new File(ConfigUtil.getConfigPath(this.context, this.houseId)));
            return 0;
        }
    }

    public static class ReadVersionTask implements Callable<Integer> {
        private Context context;
        private String houseId;

        public ReadVersionTask(Context context2, String houseId2) {
            this.houseId = houseId2;
            this.context = context2;
        }

        public Integer call() {
            return Integer.valueOf(ConfigUtil.getCurrentVersion(this.context, this.houseId));
        }
    }

    public static class WriteVersionTask implements Callable<Boolean> {
        private Context context;
        private String houseId;
        private String newVersion;

        public WriteVersionTask(Context context2, String houseId2, String newVersion2) {
            this.houseId = houseId2;
            this.context = context2;
            this.newVersion = newVersion2;
        }

        public Boolean call() {
            return Boolean.valueOf(ConfigUtil.writeCurrentVersion(this.context, this.houseId, this.newVersion));
        }
    }

    /* access modifiers changed from: private */
    public static int getCurrentVersion(Context context, String houseId) {
        Object[] objects = FileSystem.b(new File(getConfigPath(context, houseId), VERSION_FILE));
        int version = 0;
        if (objects != null) {
            try {
                if (objects[1] != null) {
                    version = Integer.parseInt(objects[1].toString());
                }
            } catch (Exception e) {
                MeshLog.e("getCurrentVersion error:" + e.toString());
            }
        }
        MeshLog.e("getCurrentVersion houseId:" + houseId + ",version:" + version);
        return version;
    }

    /* access modifiers changed from: private */
    public static boolean writeCurrentVersion(Context context, String houseId, String version) {
        if (TextUtils.isEmpty(houseId)) {
            MeshLog.w("writeCurrentVersion:houseId=null,version:" + version);
            return true;
        }
        MeshLog.d("writeCurrentVersion:houseId" + houseId + ",version:" + version);
        FileSystem.e(new File(getConfigPath(context, houseId)), VERSION_FILE, version);
        return true;
    }
}
