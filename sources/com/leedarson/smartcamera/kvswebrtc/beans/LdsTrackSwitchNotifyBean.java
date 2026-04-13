package com.leedarson.smartcamera.kvswebrtc.beans;

import android.annotation.SuppressLint;
import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.tutk.IOTC.ByteUtil;

public class LdsTrackSwitchNotifyBean extends LdsBaseDataChannelBean {
    protected static final String TAG = "LdsTrackSwitchNotifyBean";
    public static ChangeQuickRedirect changeQuickRedirect;
    private int channel;
    private byte[] reserved = new byte[3];
    private char trackId;

    @SuppressLint({"LongLogTag"})
    public void processBytes(byte[] bytes) {
        if (!PatchProxy.proxy(new Object[]{bytes}, this, changeQuickRedirect, false, 9928, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            super.processBytes(bytes);
            if (bytes.length < 5) {
                Log.e(TAG, "processBytes Size Error");
                return;
            }
            this.channel = ByteUtil.byte2int(ByteUtil.subBytes(bytes, 0, 4));
            this.trackId = (char) bytes[0 + 4];
            Log.d(TAG, "TrackId=" + this.trackId);
        }
    }

    public LdsTrackSwitchNotifyBean(byte[] bytes) {
        processBytes(bytes);
    }

    public char getTrackId() {
        return this.trackId;
    }
}
