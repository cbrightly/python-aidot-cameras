package com.didichuxing.doraemonkit.kit.weaknetwork;

import android.os.SystemClock;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.BaseResp;
import meshsdk.cache.CacheHandler;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.d0;
import okhttp3.e0;
import okhttp3.w;

public class WeakNetworkManager {
    public static final int DEFAULT_REQUEST_SPEED = 1;
    public static final int DEFAULT_RESPONSE_SPEED = 1;
    public static final int DEFAULT_TIMEOUT_MILLIS = 2000;
    public static final int TYPE_OFF_NETWORK = 0;
    public static final int TYPE_SPEED_LIMIT = 2;
    public static final int TYPE_TIMEOUT = 1;
    private AtomicBoolean mIsActive = new AtomicBoolean(false);
    private long mRequestSpeed = 1;
    private long mResponseSpeed = 1;
    private long mTimeOutMillis = CacheHandler.delayTime;
    private int mType = 0;

    public static class Holder {
        /* access modifiers changed from: private */
        public static WeakNetworkManager INSTANCE = new WeakNetworkManager();

        private Holder() {
        }
    }

    public static WeakNetworkManager get() {
        return Holder.INSTANCE;
    }

    public boolean isActive() {
        return this.mIsActive.get();
    }

    public void setActive(boolean isActive) {
        this.mIsActive.set(isActive);
    }

    public void setParameter(long timeOutMillis, long requestSpeed, long responseSpeed) {
        if (timeOutMillis > 0) {
            this.mTimeOutMillis = timeOutMillis;
        }
        this.mRequestSpeed = requestSpeed;
        this.mResponseSpeed = responseSpeed;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public int getType() {
        return this.mType;
    }

    public long getTimeOutMillis() {
        return this.mTimeOutMillis;
    }

    public long getRequestSpeed() {
        return this.mRequestSpeed;
    }

    public long getResponseSpeed() {
        return this.mResponseSpeed;
    }

    public d0 simulateOffNetwork(w.a chain) {
        d0 response = chain.a(chain.g());
        return response.v().g(BaseResp.ERR_MSG_SEND_FAIL).m(String.format("Unable to resolve host %s: No address associated with hostname", new Object[]{chain.g().l().j()})).b(e0.create(response.a().contentType(), "")).c();
    }

    public d0 simulateTimeOut(w.a chain) {
        SystemClock.sleep(this.mTimeOutMillis);
        d0 response = chain.a(chain.g());
        return response.v().g(BaseResp.ERR_MSG_SEND_FAIL).m(String.format("failed to connect to %s  after %dms", new Object[]{chain.g().l().j(), Long.valueOf(this.mTimeOutMillis)})).b(e0.create(response.a().contentType(), "")).c();
    }

    public d0 simulateSpeedLimit(w.a chain) {
        b0 request = chain.g();
        c0 body = request.a();
        if (body != null) {
            request = request.i().i(request.h(), this.mRequestSpeed > 0 ? new SpeedLimitRequestBody(this.mRequestSpeed, body) : body).b();
        }
        d0 response = chain.a(request);
        e0 responseBody = response.a();
        return response.v().b(this.mResponseSpeed > 0 ? new SpeedLimitResponseBody(this.mResponseSpeed, responseBody) : responseBody).c();
    }
}
