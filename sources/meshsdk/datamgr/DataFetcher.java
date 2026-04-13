package meshsdk.datamgr;

import android.content.Context;
import com.leedarson.serviceimpl.elkstrays.a;
import com.leedarson.serviceimpl.reporters.k;
import io.reactivex.l;
import java.util.HashMap;
import java.util.List;
import meshsdk.model.json.AddDevicesBean;
import meshsdk.model.json.CloudDevice;

public abstract class DataFetcher {
    protected Context context;

    public abstract l<FetcherResp> fetchData(String str, String str2);

    public abstract l<List<CloudDevice>> getDevices(String str);

    public abstract l<String> getHouseInfo(String str);

    public abstract l<List<AddDevicesBean>> getMeshOObData(List<AddDevicesBean> list);

    public abstract l<AddDevicesBean> postDevice(AddDevicesBean addDevicesBean);

    public DataFetcher(Context context2) {
        this.context = context2;
    }

    public boolean isCanUpdate(int newVer, String houseId) {
        int curVersion = MeshDataManager.getMeshVersion(this.context, houseId);
        HashMap<String, Object> receiveUpdateByMqtt = new HashMap<>();
        if (curVersion < newVer) {
            return true;
        }
        receiveUpdateByMqtt.put("cause", "ACTION_UPDATE_MESH_DATA_BY_MQTT");
        receiveUpdateByMqtt.put("messageContent", "the same Mesh data version,ignore downloading ");
        a.a(receiveUpdateByMqtt);
        StringBuilder sb = new StringBuilder();
        sb.append("本地meshjson版本(");
        sb.append(curVersion);
        sb.append(")");
        sb.append(curVersion == newVer ? "等于" : "高于");
        sb.append(" 云端版本，跳过更新");
        k.a(sb.toString());
        return false;
    }

    public static class FetcherResp {
        private int errCode;
        private boolean isSuccess;
        private String localPath;
        private String msg;

        public FetcherResp(int errCode2, String msg2) {
            setFail(errCode2, msg2);
        }

        public FetcherResp(String localPath2) {
            setSuccess(localPath2);
        }

        private void setFail(int code, String msg2) {
            this.errCode = code;
            this.msg = msg2;
            this.isSuccess = false;
        }

        private void setSuccess(String localPath2) {
            this.isSuccess = true;
            this.localPath = localPath2;
        }

        public String getLocalPath() {
            return this.localPath;
        }

        public String getMsg() {
            return this.msg;
        }

        public int getErrCode() {
            return this.errCode;
        }

        public boolean isSuccess() {
            return this.isSuccess;
        }
    }
}
