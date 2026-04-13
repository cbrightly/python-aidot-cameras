package meshsdk.cache;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import meshsdk.MeshLogNew;
import meshsdk.cache.cachemodule.CurrentDetectionModeCacheInstance;
import meshsdk.cache.cachemodule.DetectionModeDetailCacheInstance;
import meshsdk.model.json.DetectMode;

public class CacheHandler extends Handler {
    private static final String TAG = "CacheHandler";
    public static final int WHAT_GET_CURRENT_DETECTION_MODE = 5201;
    public static final int WHAT_GET_DETECTION_MDOE_DETAIL = 5202;
    public static final long delayTime = 2000;

    public CacheHandler(@NonNull Looper looper) {
        super(looper);
    }

    public void handleMessage(@NonNull Message msg) {
        DetectMode detectMode;
        super.handleMessage(msg);
        CacheHanderMsgWrapper wrapper = (CacheHanderMsgWrapper) msg.obj;
        if (wrapper != null) {
            int i = wrapper.whatMsg;
            if (i == 5201) {
                Integer mode = (Integer) CurrentDetectionModeCacheInstance.getInstance().get(wrapper.cacheKey);
                if (wrapper.callback != null && mode != null) {
                    log("getCurrentDetectionMode先显示缓存，key:" + wrapper.cacheKey + ",value:" + mode);
                    wrapper.callback.onSuccess(mode);
                }
            } else if (i == 5202 && (detectMode = (DetectMode) DetectionModeDetailCacheInstance.getInstance().get(wrapper.cacheKey)) != null) {
                log("getDetectionMode 先显示缓存，key:" + wrapper.cacheKey + ",value:" + detectMode);
                wrapper.callback.onSuccess(detectMode);
            }
        }
    }

    public void sendWrapperDelay(CacheHanderMsgWrapper wrapper) {
        Message message = Message.obtain();
        message.what = wrapper.whatMsg;
        message.obj = wrapper;
        sendMessageDelayed(message, delayTime);
    }

    public void removeWrapperMsg(int what) {
        if (hasMessages(what)) {
            log("删除what:" + what);
            removeMessages(what);
        }
    }

    private void log(String msg) {
        MeshLogNew.i("CacheHandler:" + msg);
    }
}
