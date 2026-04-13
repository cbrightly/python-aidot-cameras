package com.leedarson.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.leedarson.view.IpcWebrtcSurfaceView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.webrtc.VideoFrame;

public class IpcSDWebrtcSurfaceView extends IpcWebrtcSurfaceView {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public IpcWebrtcSurfaceView.d T4;
    private b U4 = new b();
    private String V4;

    public IpcSDWebrtcSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IpcSDWebrtcSurfaceView(Context context) {
        super(context);
    }

    public void setOnGetFrameInfoListener(IpcWebrtcSurfaceView.d onGetFrameInfoListener) {
        this.T4 = onGetFrameInfoListener;
    }

    public void setPlayTimeChannelId(String playTimeChannelId) {
        this.V4 = playTimeChannelId;
    }

    public void onFrame(VideoFrame frame) {
        if (!PatchProxy.proxy(new Object[]{frame}, this, changeQuickRedirect, false, 11664, new Class[]{VideoFrame.class}, Void.TYPE).isSupported) {
            try {
                if (!TextUtils.isEmpty(this.V4)) {
                    if (this.T4 == null) {
                        super.onFrame(frame);
                    } else if (!TextUtils.isEmpty(this.V4) && !TextUtils.isEmpty(frame.getChannelId()) && this.V4.equals(frame.getChannelId())) {
                        super.onFrame(frame);
                        Message msg = Message.obtain();
                        b bVar = this.U4;
                        msg.what = 2;
                        msg.obj = frame;
                        bVar.sendMessage(msg);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void release() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11665, new Class[0], Void.TYPE).isSupported) {
            super.release();
            this.T4 = null;
        }
    }

    public class b extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private b() {
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 11666, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 2:
                        VideoFrame frame = (VideoFrame) msg.obj;
                        if (frame != null && IpcSDWebrtcSurfaceView.this.T4 != null) {
                            IpcSDWebrtcSurfaceView.this.T4.a(frame.getLiveType(), frame.getChannelId(), frame.getFrameTimestamp());
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }
}
