package meshsdk;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseResp {
    public static final int CODE_FAIL = 401;
    public static final int CODE_NO_HOUSE_ID = 399;
    public static final int CODE_NO_PERMISSION = 401;
    public static final int CODE_SUCCESS = 200;
    public static String DESC_BLUETOOTH_DISABLE = "Bluetooth disable(蓝牙未打开)";
    public static String DESC_NO_PERMISSION = "No ACCESS_FINE_LOCATION permission";
    public static final int ERR_ALLOCATE_ADDR_FAIL = 425;
    public static final int ERR_CONNECT_FAIL = 405;
    public static final int ERR_CONTROL_PARAM_ERROR = 407;
    public static final int ERR_DEVICE_ADDR_ALLOCATE_FAIL = 402;
    public static final int ERR_DEVICE_BIND_FAIL = 403;
    public static final int ERR_DISCONNECT_FAIL = 409;
    public static final int ERR_DOWNLOAD_IMPORT_FAIL = 413;
    public static final int ERR_DOWNLOAD_MESH_FAIL = 412;
    public static final int ERR_GROUP_LIMIT = 426;
    public static final int ERR_GROUP_NOT_EMPTY = 417;
    public static final int ERR_GROUP_NOT_EXIST = 416;
    public static final int ERR_INVAILD_MODEL_ID = 408;
    public static final int ERR_MSG_SEND_FAIL = 400;
    public static final int ERR_NODE_NOT_EXIST = 418;
    public static final int ERR_NO_MESH_NETWORK = 430;
    public static final int ERR_OP_FAIL = 414;
    public static final int ERR_OTA_FAIL = 423;
    public static final int ERR_OTA_PKG_ERROR = 422;
    public static final int ERR_PARAM_ERROR = 415;
    public static final int ERR_SCENE_ACTION_SET_FAIL = 421;
    public static final int ERR_SCENE_LIMIT = 428;
    public static final int ERR_SCENE_NOT_EMPTY = 420;
    public static final int ERR_SCENE_NOT_EXIST = 419;
    public static final int ERR_SCHEDULE_NOT_EXIST = 424;
    public static final int ERR_SMART_LIMIT = 429;
    public static final int ERR_UPLOAD_EXPORT_FAIL = 410;
    public static final int ERR_UPLOAD_FAIL = 411;
    public static final int ERR_VERSION_FAIL = 427;
    public static final int ERR_WAIT_RESPONSE = 406;
    public int code = -1;
    public String msg = "";

    public BaseResp(int code2, String msg2) {
        this.code = code2;
        this.msg = msg2;
    }

    public BaseResp() {
    }

    public static JSONObject generatorFailResp(int code2, String desc) {
        if (desc == null) {
            desc = "";
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("desc", (Object) desc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MeshLog.w("generatorFailResp:code=" + code2 + ",desc=" + desc);
        return generatorResp(code2, obj);
    }

    public static JSONObject generatorSuccessResp() {
        return generatorResp(200, (Object) null);
    }

    public static JSONObject generatorSuccessResp(Object data) {
        return generatorResp(200, data);
    }

    public static JSONObject generatorResp(int code2, Object data) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("code", code2);
            if (data != null) {
                obj.put("data", data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject map2Json(Map<String, Object> map) {
        return new JSONObject((Map<?, ?>) map);
    }
}
