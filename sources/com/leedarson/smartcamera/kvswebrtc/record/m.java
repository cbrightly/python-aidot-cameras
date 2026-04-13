package com.leedarson.smartcamera.kvswebrtc.record;

import android.graphics.Matrix;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;
import com.google.android.gms.common.util.GmsVersion;
import com.leedarson.log.f;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.nio.ByteBuffer;
import org.webrtc.EglBase;
import org.webrtc.GlRectDrawer;
import org.webrtc.VideoFrame;
import org.webrtc.VideoFrameDrawer;
import org.webrtc.VideoSink;
import org.webrtc.audio.JavaAudioDeviceModule;
import org.webrtc.r0;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
import tv.danmaku.ijk.media.player.misc.IMediaFormat;

/* compiled from: VideoFileRenderer */
public class m implements VideoSink, JavaAudioDeviceModule.SamplesReadyCallback {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean A4 = true;
    private GlRectDrawer B4;
    private Surface C4;
    private MediaCodec D4;
    private boolean E4 = false;
    private volatile boolean F4 = false;
    private long G4 = 0;
    private long H4 = 0;
    private volatile VideoFrameDrawer a1;
    private MediaCodec a2;
    private final HandlerThread c;
    private final Handler d;
    private final HandlerThread f;
    private final EglBase.Context p0;
    private final MediaMuxer p1;
    private final MediaCodec.BufferInfo p2;
    private MediaCodec.BufferInfo p3;
    private int p4 = -1;
    private final Handler q;
    private int x = -1;
    private int y = -1;
    private EglBase z;
    private int z4;

    public m(String outputFile, EglBase.Context sharedContext, boolean withAudio) {
        int i = -1;
        HandlerThread handlerThread = new HandlerThread("VideoFileRendererRenderThread");
        this.c = handlerThread;
        handlerThread.start();
        this.d = new Handler(handlerThread.getLooper());
        if (withAudio) {
            HandlerThread handlerThread2 = new HandlerThread("VideoFileRendererAudioThread");
            this.f = handlerThread2;
            handlerThread2.start();
            this.q = new Handler(handlerThread2.getLooper());
        } else {
            this.f = null;
            this.q = null;
        }
        this.p2 = new MediaCodec.BufferInfo();
        this.p0 = sharedContext;
        this.p1 = new MediaMuxer(outputFile, 0);
        this.z4 = !withAudio ? 0 : i;
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9969, new Class[0], Void.TYPE).isSupported) {
            a(" init VideoEncoder start ");
            MediaCodecInfo videoCodecInfo = r("video/avc");
            if (videoCodecInfo == null) {
                b("unable to find an codecvideo/avc");
                return;
            }
            MediaCodecInfo.VideoCapabilities videoCapabilities = videoCodecInfo.getCapabilitiesForType("video/avc").getVideoCapabilities();
            a("initVideoEncoder:=== " + videoCapabilities.getSupportedWidths().getUpper() + "==" + videoCapabilities.getSupportedHeights().getUpper());
            if (this.x > videoCapabilities.getSupportedWidths().getUpper().intValue()) {
                this.y = (int) Math.floor((((double) this.y) * 1.0d) / ((((double) this.x) * 1.0d) / ((double) videoCapabilities.getSupportedWidths().getUpper().intValue())));
                this.x = videoCapabilities.getSupportedWidths().getUpper().intValue();
            }
            a("initVideoEncoder: " + this.x + "==" + this.y);
            MediaFormat format = MediaFormat.createVideoFormat("video/avc", this.x, this.y);
            format.setInteger("color-format", 2130708361);
            format.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, GmsVersion.VERSION_MANCHEGO);
            format.setInteger("frame-rate", 30);
            format.setInteger("i-frame-interval", 1);
            try {
                MediaCodec createEncoderByType = MediaCodec.createEncoderByType("video/avc");
                this.a2 = createEncoderByType;
                createEncoderByType.configure(format, (Surface) null, (MediaCrypto) null, 1);
                this.d.post(new e(this));
            } catch (Exception e) {
                e.printStackTrace();
                b(" init VideoCodec Crash  e=" + e.toString());
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public /* synthetic */ void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9983, new Class[0], Void.TYPE).isSupported) {
            this.z = r0.c(this.p0, EglBase.CONFIG_RECORDABLE);
            Surface createInputSurface = this.a2.createInputSurface();
            this.C4 = createInputSurface;
            this.z.createSurface(createInputSurface);
            this.z.makeCurrent();
            this.B4 = new GlRectDrawer();
        }
    }

    public void onFrame(VideoFrame frame) {
        if (!PatchProxy.proxy(new Object[]{frame}, this, changeQuickRedirect, false, 9970, new Class[]{VideoFrame.class}, Void.TYPE).isSupported) {
            frame.retain();
            if (this.x == -1) {
                this.x = frame.getRotatedWidth();
                this.y = frame.getRotatedHeight();
                e();
            }
            this.d.post(new f(this, frame));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public /* synthetic */ void i(VideoFrame frame) {
        if (!PatchProxy.proxy(new Object[]{frame}, this, changeQuickRedirect, false, 9982, new Class[]{VideoFrame.class}, Void.TYPE).isSupported) {
            q(frame);
        }
    }

    private void q(VideoFrame videoFrame) {
        if (!PatchProxy.proxy(new Object[]{videoFrame}, this, changeQuickRedirect, false, 9971, new Class[]{VideoFrame.class}, Void.TYPE).isSupported) {
            VideoFrame frame = videoFrame;
            if (this.a1 == null) {
                this.a1 = new VideoFrameDrawer();
            }
            try {
                this.a1.drawFrame(frame, this.B4, (Matrix) null, 0, 0, this.x, this.y);
            } catch (Exception ex) {
                ex.printStackTrace();
                b(" frameDrawer.drawFrame 发生异常" + ex.toString());
            }
            frame.release();
            d();
            this.z.swapBuffers();
        }
    }

    public void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9972, new Class[0], Void.TYPE).isSupported) {
            this.A4 = false;
            Handler handler = this.q;
            if (handler != null) {
                handler.post(new c(this));
            }
            this.F4 = false;
            this.d.post(new g(this));
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* renamed from: l */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void m() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 9981(0x26fd, float:1.3986E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.media.MediaCodec r1 = r0.D4
            if (r1 == 0) goto L_0x0028
            r1.stop()
            android.media.MediaCodec r1 = r0.D4
            r1.release()
            android.os.HandlerThread r1 = r0.f
            r1.quit()
        L_0x0028:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.record.m.m():void");
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public /* synthetic */ void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9980, new Class[0], Void.TYPE).isSupported) {
            MediaCodec mediaCodec = this.a2;
            if (mediaCodec != null) {
                mediaCodec.stop();
                this.a2.release();
            }
            EglBase eglBase = this.z;
            if (eglBase != null) {
                eglBase.release();
            }
            try {
                this.p1.release();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            this.c.quit();
        }
    }

    private void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9973, new Class[0], Void.TYPE).isSupported) {
            if (!this.E4) {
                this.a2.start();
                this.E4 = true;
                return;
            }
            while (true) {
                int encoderStatus = this.a2.dequeueOutputBuffer(this.p2, 8000);
                if (encoderStatus != -1) {
                    if (encoderStatus == -2) {
                        this.p4 = this.p1.addTrack(this.a2.getOutputFormat());
                        if (this.z4 != -1 && !this.F4) {
                            this.p1.start();
                            this.F4 = true;
                        }
                        if (!this.F4) {
                            return;
                        }
                    } else if (encoderStatus < 0) {
                        continue;
                    } else {
                        try {
                            ByteBuffer encodedData = this.a2.getOutputBuffer(encoderStatus);
                            if (encodedData != null) {
                                encodedData.position(this.p2.offset);
                                MediaCodec.BufferInfo bufferInfo = this.p2;
                                encodedData.limit(bufferInfo.offset + bufferInfo.size);
                                if (this.G4 == 0) {
                                    long j = this.p2.presentationTimeUs;
                                    if (j != 0) {
                                        this.G4 = j;
                                    }
                                }
                                this.p2.presentationTimeUs -= this.G4;
                                if (this.F4) {
                                    this.p1.writeSampleData(this.p4, encodedData, this.p2);
                                }
                                this.A4 = this.A4 && (this.p2.flags & 4) == 0;
                                this.a2.releaseOutputBuffer(encoderStatus, false);
                                if ((this.p2.flags & 4) != 0) {
                                    return;
                                }
                            } else {
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

    private void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9974, new Class[0], Void.TYPE).isSupported) {
            if (this.p3 == null) {
                this.p3 = new MediaCodec.BufferInfo();
            }
            while (true) {
                int encoderStatus = this.D4.dequeueOutputBuffer(this.p3, 1000);
                if (encoderStatus != -1) {
                    boolean z2 = true;
                    if (encoderStatus == -2) {
                        this.z4 = this.p1.addTrack(this.D4.getOutputFormat());
                        if (this.p4 != -1 && !this.F4) {
                            this.p1.start();
                            this.F4 = true;
                        }
                        if (!this.F4) {
                            return;
                        }
                    } else if (encoderStatus < 0) {
                        continue;
                    } else {
                        try {
                            ByteBuffer encodedData = this.D4.getOutputBuffer(encoderStatus);
                            if (encodedData != null) {
                                encodedData.position(this.p3.offset);
                                MediaCodec.BufferInfo bufferInfo = this.p3;
                                encodedData.limit(bufferInfo.offset + bufferInfo.size);
                                if (this.F4) {
                                    this.p1.writeSampleData(this.z4, encodedData, this.p3);
                                }
                                if (!this.A4 || (this.p3.flags & 4) != 0) {
                                    z2 = false;
                                }
                                this.A4 = z2;
                                this.D4.releaseOutputBuffer(encoderStatus, false);
                                if ((this.p3.flags & 4) != 0) {
                                    return;
                                }
                            } else {
                                return;
                            }
                        } catch (Exception e) {
                            f.d("VideoFileRenderer", "Record Exception: " + e);
                            return;
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    public void onWebRtcAudioRecordSamplesReady(JavaAudioDeviceModule.AudioSamples audioSamples) {
        if (!PatchProxy.proxy(new Object[]{audioSamples}, this, changeQuickRedirect, false, 9975, new Class[]{JavaAudioDeviceModule.AudioSamples.class}, Void.TYPE).isSupported) {
            if (this.A4) {
                this.q.post(new d(this, audioSamples));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public /* synthetic */ void k(JavaAudioDeviceModule.AudioSamples audioSamples) {
        if (!PatchProxy.proxy(new Object[]{audioSamples}, this, changeQuickRedirect, false, 9979, new Class[]{JavaAudioDeviceModule.AudioSamples.class}, Void.TYPE).isSupported) {
            if (this.D4 == null) {
                try {
                    this.D4 = MediaCodec.createEncoderByType("audio/mp4a-latm");
                    MediaFormat format = new MediaFormat();
                    format.setString(IMediaFormat.KEY_MIME, "audio/mp4a-latm");
                    format.setInteger("channel-count", audioSamples.getChannelCount());
                    format.setInteger("sample-rate", audioSamples.getSampleRate());
                    format.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, 65536);
                    format.setInteger("aac-profile", 2);
                    this.D4.configure(format, (Surface) null, (MediaCrypto) null, 1);
                    this.D4.start();
                } catch (Exception exception) {
                    f.d("VideoFileRenderer", "Record IOException: " + exception);
                }
            }
            int bufferIndex = this.D4.dequeueInputBuffer(0);
            if (bufferIndex >= 0) {
                ByteBuffer buffer = this.D4.getInputBuffer(bufferIndex);
                buffer.clear();
                byte[] data = audioSamples.getData();
                buffer.put(data);
                this.D4.queueInputBuffer(bufferIndex, 0, data.length, this.H4, 0);
                this.H4 += (long) ((data.length * 125) / 12);
            }
            c();
        }
    }

    private MediaCodecInfo r(String mimeType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mimeType}, this, changeQuickRedirect, false, 9976, new Class[]{String.class}, MediaCodecInfo.class);
        if (proxy.isSupported) {
            return (MediaCodecInfo) proxy.result;
        }
        int numCodecs = MediaCodecList.getCodecCount();
        for (int i = 0; i < numCodecs; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (codecInfo.isEncoder()) {
                String[] types = codecInfo.getSupportedTypes();
                for (String equalsIgnoreCase : types) {
                    if (equalsIgnoreCase.equalsIgnoreCase(mimeType)) {
                        return codecInfo;
                    }
                }
                continue;
            }
        }
        return null;
    }

    private void a(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 9977, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = a.g("VideoFileRenderer");
            g.a("" + message, new Object[0]);
        }
    }

    private void b(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 9978, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g("VideoFileRenderer").c(message, new Object[0]);
        }
    }
}
