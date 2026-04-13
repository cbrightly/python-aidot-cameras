package com.leedarson.smartcamera.kvswebrtc.focus;

import android.os.Handler;
import android.os.Message;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.nio.ByteBuffer;
import org.webrtc.VideoFrame;
import org.webrtc.VideoSink;

/* compiled from: FrameGetYUV */
public class b implements VideoSink {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public c c;
    private C0172b d = new C0172b();
    private long f;
    private final long q = 1000;
    byte[] x;

    /* compiled from: FrameGetYUV */
    public interface c {
        void B(byte[] bArr, int i, int i2);
    }

    public void onFrame(VideoFrame videoFrame) {
        if (!PatchProxy.proxy(new Object[]{videoFrame}, this, changeQuickRedirect, false, 9939, new Class[]{VideoFrame.class}, Void.TYPE).isSupported) {
            b(videoFrame);
        }
    }

    private void b(VideoFrame videoFrame) {
        char c2 = 1;
        if (!PatchProxy.proxy(new Object[]{videoFrame}, this, changeQuickRedirect, false, 9940, new Class[]{VideoFrame.class}, Void.TYPE).isSupported) {
            VideoFrame videoFrame2 = videoFrame;
            try {
                if (System.currentTimeMillis() - this.f >= 1000) {
                    this.f = System.currentTimeMillis();
                    if (videoFrame2 != null && this.c != null) {
                        VideoFrame.I420Buffer i420Buffer = videoFrame2.getBuffer().toI420();
                        ByteBuffer y = i420Buffer.getDataY();
                        ByteBuffer u = i420Buffer.getDataU();
                        ByteBuffer v = i420Buffer.getDataV();
                        int width = i420Buffer.getWidth();
                        int height = i420Buffer.getHeight();
                        int[] strides = {i420Buffer.getStrideY(), i420Buffer.getStrideU(), i420Buffer.getStrideV()};
                        byte[] bArr = this.x;
                        if (bArr == null || bArr.length != ((height * width) * 3) / 2) {
                            this.x = new byte[(((height * width) * 3) / 2)];
                        }
                        y.get(this.x, 0, height * width);
                        int uOffset = width * height;
                        int vOffset = ((width * height) * 5) / 4;
                        int i = 0;
                        while (i < height / 2) {
                            u.position(strides[c2] * i);
                            u.get(this.x, uOffset, width / 2);
                            uOffset += width / 2;
                            v.position(strides[2] * i);
                            v.get(this.x, vOffset, width / 2);
                            vOffset += width / 2;
                            i++;
                            c2 = 1;
                        }
                        Message msg2 = Message.obtain();
                        C0172b bVar = this.d;
                        msg2.what = 3;
                        msg2.obj = this.x;
                        msg2.arg1 = width;
                        msg2.arg2 = height;
                        bVar.sendMessage(msg2);
                        i420Buffer.release();
                    }
                }
            } catch (Exception e) {
                timber.log.a.g("FrameGetYUV").a("FrameGetYUV Exception:" + e.getMessage(), new Object[0]);
            }
        }
    }

    public void setOnYUVDataListener(c onYUVDataListener) {
        this.c = onYUVDataListener;
    }

    /* renamed from: com.leedarson.smartcamera.kvswebrtc.focus.b$b  reason: collision with other inner class name */
    /* compiled from: FrameGetYUV */
    public class C0172b extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private C0172b() {
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 9941, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 3:
                        try {
                            byte[] data = (byte[]) msg.obj;
                            int width = msg.arg1;
                            int height = msg.arg2;
                            if (b.this.c != null) {
                                b.this.c.B(data, width, height);
                                return;
                            }
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    default:
                        return;
                }
            }
        }
    }
}
