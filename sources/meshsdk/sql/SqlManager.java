package meshsdk.sql;

import android.text.TextUtils;
import com.leedarson.base.utils.e;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.telink.ble.mesh.core.MeshUtils;
import java.util.ArrayList;
import java.util.List;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.sql.MeshDictBeanDao;
import meshsdk.sql.RandomOffsetBeanDao;
import org.greenrobot.greendao.query.WhereCondition;
import org.json.JSONArray;
import org.json.JSONException;

public class SqlManager {
    public static DaoSession getDaoSession() {
        return DBManager.getInstance(SIGMesh.getInstance().getContext()).getDaoSession();
    }

    public static void insertOrReplace(MeshDictBean bean) {
        getDaoSession().getMeshDictBeanDao().insertOrReplace(bean);
    }

    public static List<MeshDictBean> getAllDictBean() {
        List<MeshDictBean> list = getDaoSession().getMeshDictBeanDao().queryBuilder().list();
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public static MeshDictBean findDictInfoByUUID(String uuid) {
        List<MeshDictBean> list = getDaoSession().getMeshDictBeanDao().queryBuilder().where(MeshDictBeanDao.Properties.MeshUUID.eq(uuid), new WhereCondition[0]).list();
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    public static int getLocalAddress(String meshUUID) {
        MeshDictBean dictInfo = findDictInfoByUUID(meshUUID);
        if (dictInfo == null) {
            return 1;
        }
        return dictInfo.getLocalAddr();
    }

    public static String getLocalProvisionerUUID(String meshUUID) {
        MeshDictBean dictInfo = findDictInfoByUUID(meshUUID);
        return dictInfo == null ? e.a(MeshUtils.g(16)) : dictInfo.getProvisionerUUID();
    }

    public static void updateSeqNum(int addr, int seq, String meshUUID) {
        MeshDictBean bean = (MeshDictBean) getDaoSession().getMeshDictBeanDao().queryBuilder().where(getDaoSession().getMeshDictBeanDao().queryBuilder().and(MeshDictBeanDao.Properties.LocalAddr.eq(Integer.valueOf(addr)), MeshDictBeanDao.Properties.MeshUUID.eq(meshUUID), new WhereCondition[0]), new WhereCondition[0]).unique();
        if (bean != null) {
            MeshLog.debugInfo("update dict SeqNum:\n" + bean.toString());
            bean.setSeqNum(seq);
            getDaoSession().getMeshDictBeanDao().update(bean);
        }
    }

    public static JSONArray getDelNodes() {
        String node = SharePreferenceUtils.getPrefString(SIGMesh.getInstance().getContext(), "delCacheNode", "[]");
        MeshLog.i("getDelNodes 未删除的节点缓存:" + node);
        if (!TextUtils.isEmpty(node)) {
            try {
                return new JSONArray(node);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new JSONArray();
    }

    public static boolean delNodesContains(String mac) {
        JSONArray delNodes = getDelNodes();
        int i = 0;
        while (i < delNodes.length()) {
            try {
                if (delNodes.get(i).toString().equals(mac)) {
                    return true;
                }
                i++;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static void saveDelCacheNode(String mac) {
        MeshLog.i("saveDelCacheNode 新增未删除的节点缓存:" + mac);
        try {
            JSONArray arr = new JSONArray(SharePreferenceUtils.getPrefString(SIGMesh.getInstance().getContext(), "delCacheNode", "[]"));
            int i = 0;
            while (i < arr.length()) {
                if (!mac.equals((String) arr.get(i))) {
                    i++;
                } else {
                    return;
                }
            }
            arr.put((Object) mac);
            SharePreferenceUtils.setPrefString(SIGMesh.getInstance().getContext(), "delCacheNode", arr.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void removeDelCacheNode(String mac) {
        try {
            JSONArray array = getDelNodes();
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    if (((String) array.get(i)).equals(mac)) {
                        MeshLog.i("移除未删除的节点缓存:" + mac);
                        array.remove(i);
                        SharePreferenceUtils.setPrefString(SIGMesh.getInstance().getContext(), "delCacheNode", array.toString());
                        MeshLog.i("removeDelCacheNode 未删除的节点缓存:" + array.toString());
                        return;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static int queryRandomOffset(String meshUUID, String mac, int offset) {
        List<RandomOffsetBean> list = getDaoSession().getRandomOffsetBeanDao().queryBuilder().where(getDaoSession().getRandomOffsetBeanDao().queryBuilder().and(RandomOffsetBeanDao.Properties.MeshUUID.eq(meshUUID), RandomOffsetBeanDao.Properties.Mac.eq(mac), RandomOffsetBeanDao.Properties.Offset.eq(Integer.valueOf(offset))), new WhereCondition[0]).list();
        if (list == null || list.size() <= 0) {
            return -1;
        }
        int resultOffset = list.get(0).getOffset();
        MeshLog.d("11111-本地数据库中查询到相同节点地址随机偏移量,使用已有数据库中的offset:" + resultOffset);
        return resultOffset;
    }

    public static int updateRandomOffset(String meshUUID, String mac) {
        return 0;
    }
}
