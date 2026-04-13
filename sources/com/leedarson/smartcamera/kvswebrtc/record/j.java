package com.leedarson.smartcamera.kvswebrtc.record;

import android.graphics.Matrix;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;
import com.google.android.gms.common.util.GmsVersion;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.nio.ByteBuffer;
import org.webrtc.EglBase;
import org.webrtc.GlRectDrawer;
import org.webrtc.VideoFrame;
import org.webrtc.VideoFrameDrawer;
import org.webrtc.VideoSink;
import org.webrtc.r0;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: GetVideoPTSRenderer */
public class j implements VideoSink {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long A4 = 0;
    private final MediaCodec.BufferInfo a1;
    private boolean a2 = true;
    private final HandlerThread c;
    private final Handler d;
    private int f = -1;
    private MediaCodec p0;
    private int p1 = -1;
    private GlRectDrawer p2;
    private Surface p3;
    private com.leedarson.smartcamera.listener.j p4;
    private int q = -1;
    private EglBase x;
    private final EglBase.Context y;
    private VideoFrameDrawer z;
    private boolean z4 = false;

    public j(EglBase.Context sharedContext) {
        HandlerThread handlerThread = new HandlerThread("VideoFileRendererRenderThread");
        this.c = handlerThread;
        handlerThread.start();
        this.d = new Handler(handlerThread.getLooper());
        this.a1 = new MediaCodec.BufferInfo();
        this.y = sharedContext;
    }

    public void setListener(com.leedarson.smartcamera.listener.j listener) {
        this.p4 = listener;
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9955, new Class[0], Void.TYPE).isSupported) {
            MediaFormat format = MediaFormat.createVideoFormat("video/avc", this.f, this.q);
            format.setInteger("color-format", 2130708361);
            format.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, GmsVersion.VERSION_MANCHEGO);
            format.setInteger("frame-rate", 30);
            format.setInteger("i-frame-interval", 1);
            try {
                MediaCodec createEncoderByType = MediaCodec.createEncoderByType("video/avc");
                this.p0 = createEncoderByType;
                createEncoderByType.configure(format, (Surface) null, (MediaCrypto) null, 1);
                this.d.post(new b(this));
            } catch (Exception e) {
                Log.wtf("VideoFileRenderer", e);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public /* synthetic */ void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9962, new Class[0], Void.TYPE).isSupported) {
            this.x = r0.c(this.y, EglBase.CONFIG_RECORDABLE);
            Surface createInputSurface = this.p0.createInputSurface();
            this.p3 = createInputSurface;
            this.x.createSurface(createInputSurface);
            this.x.makeCurrent();
            this.p2 = new GlRectDrawer();
        }
    }

    public void onFrame(VideoFrame frame) {
        if (!PatchProxy.proxy(new Object[]{frame}, this, changeQuickRedirect, false, 9956, new Class[]{VideoFrame.class}, Void.TYPE).isSupported) {
            frame.retain();
            if (this.f == -1) {
                this.f = frame.getRotatedWidth();
                this.q = frame.getRotatedHeight();
                b();
            }
            this.d.post(new a(this, frame));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public /* synthetic */ void f(VideoFrame frame) {
        if (!PatchProxy.proxy(new Object[]{frame}, this, changeQuickRedirect, false, 9961, new Class[]{VideoFrame.class}, Void.TYPE).isSupported) {
            g(frame);
        }
    }

    private void g(VideoFrame videoFrame) {
        if (!PatchProxy.proxy(new Object[]{videoFrame}, this, changeQuickRedirect, false, 9957, new Class[]{VideoFrame.class}, Void.TYPE).isSupported) {
            VideoFrame frame = videoFrame;
            if (this.z == null) {
                this.z = new VideoFrameDrawer();
            }
            this.z.drawFrame(frame, this.p2, (Matrix) null, 0, 0, this.f, this.q);
            frame.release();
            a();
            this.x.swapBuffers();
        }
    }

    private void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9959, new Class[0], Void.TYPE).isSupported) {
            if (!this.z4) {
                this.p0.start();
                this.z4 = true;
                return;
            }
            while (true) {
                int encoderStatus = this.p0.dequeueOutputBuffer(this.a1, 10000);
                if (encoderStatus != -1) {
                    if (encoderStatus == -2) {
                        MediaFormat newFormat = this.p0.getOutputFormat();
                        Log.e("VideoFileRenderer", "encoder output format changed: " + newFormat);
                    } else if (encoderStatus < 0) {
                        Log.e("VideoFileRenderer", "unexpected result fr om encoder.dequeueOutputBuffer: " + encoderStatus);
                    } else {
                        try {
                            ByteBuffer encodedData = this.p0.getOutputBuffer(encoderStatus);
                            if (encodedData == null) {
                                Log.e("VideoFileRenderer", "encoderOutputBuffer " + encoderStatus + " was null");
                                return;
                            }
                            encodedData.position(this.a1.offset);
                            MediaCodec.BufferInfo bufferInfo = this.a1;
                            encodedData.limit(bufferInfo.offset + bufferInfo.size);
                            if (this.A4 == 0) {
                                long j = this.a1.presentationTimeUs;
                                if (j != 0) {
                                    this.A4 = j;
                                }
                            }
                            com.leedarson.smartcamera.listener.j jVar = this.p4;
                            if (jVar != null) {
                                jVar.c(this.a1.presentationTimeUs / 1000, 0, 0);
                            }
                            this.a2 = this.a2 && (this.a1.flags & 4) == 0;
                            this.p0.releaseOutputBuffer(encoderStatus, false);
                            if ((this.a1.flags & 4) != 0) {
                                return;
                            }
                        } catch (Exception e) {
                            Log.wtf("VideoFileRenderer", e);
                            return;
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }
}
