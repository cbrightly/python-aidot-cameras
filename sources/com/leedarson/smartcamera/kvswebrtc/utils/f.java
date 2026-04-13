package com.leedarson.smartcamera.kvswebrtc.utils;

import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;
import org.webrtc.MediaStreamTrack;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoTrack;
import timber.log.a;

/* compiled from: LdsMediaTrackManager */
public class f {
    public static ChangeQuickRedirect changeQuickRedirect;
    private List<a> a = new ArrayList();
    private SurfaceViewRenderer b;
    private int c = 0;

    /* compiled from: LdsMediaTrackManager */
    public class a {
        public MediaStreamTrack a;
        public int b;

        public a() {
        }
    }

    private a g(int trackId) {
        Object[] objArr = {new Integer(trackId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 10129, new Class[]{Integer.TYPE}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        for (int index = 0; index < this.a.size(); index++) {
            a mediaTrack = this.a.get(index);
            if (mediaTrack.b == trackId) {
                return mediaTrack;
            }
        }
        return null;
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10130, new Class[0], Void.TYPE).isSupported) {
            if (this.b != null) {
                for (int index = 0; index < this.a.size(); index++) {
                    ((VideoTrack) this.a.get(index).a).removeSink(this.b);
                    a.b g = timber.log.a.g("LdsMediaTrackManager");
                    g.h("[移除Render]  render=" + this.b + " index=" + index, new Object[0]);
                }
            }
        }
    }

    private void c(SurfaceViewRenderer render, a track) {
        boolean z = false;
        if (!PatchProxy.proxy(new Object[]{render, track}, this, changeQuickRedirect, false, 10131, new Class[]{SurfaceViewRenderer.class, a.class}, Void.TYPE).isSupported) {
            if (render == null || track == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("[渲染器设置] WebRtc渲染设置失败  render==null?");
                sb.append(render == null);
                sb.append("  track==null?");
                if (track == null) {
                    z = true;
                }
                sb.append(z);
                b(sb.toString());
                return;
            }
            render.setKeyFrameRequired(true);
            VideoTrack videoTrack = (VideoTrack) track.a;
            try {
                videoTrack.setEnabled(true);
                videoTrack.addSink(render);
                a("[渲染器设置] webRtc渲染器设置成功 videoTrackId=" + videoTrack.id() + " trackId=" + track.b);
            } catch (Exception e) {
                e.printStackTrace();
                b("[渲染器设置] WebRtc渲染器设置失败  e=" + e.toString());
            }
        }
    }

    private void a(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 10132, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("KVSWebRTCChannel");
            g.a(message + " LdsMediaTrackManager", new Object[0]);
        }
    }

    private void b(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 10133, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("KVSWebRTCChannel");
            g.c(message + " LdsMediaTrackManager", new Object[0]);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10134(0x2796, float:1.4201E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r8
            java.util.List<com.leedarson.smartcamera.kvswebrtc.utils.f$a> r2 = r1.a
            if (r2 == 0) goto L_0x0059
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x0059
            java.util.List<com.leedarson.smartcamera.kvswebrtc.utils.f$a> r2 = r1.a
            java.lang.Object r0 = r2.get(r0)
            com.leedarson.smartcamera.kvswebrtc.utils.f$a r0 = (com.leedarson.smartcamera.kvswebrtc.utils.f.a) r0
            int r2 = r1.c
            java.util.List<com.leedarson.smartcamera.kvswebrtc.utils.f$a> r3 = r1.a
            int r3 = r3.size()
            if (r2 >= r3) goto L_0x0054
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "[解码器设置] 设置默认解码器 setupDefaultRender TrackId ="
            r2.append(r3)
            int r3 = r1.c
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.a(r2)
            java.util.List<com.leedarson.smartcamera.kvswebrtc.utils.f$a> r2 = r1.a
            int r3 = r1.c
            java.lang.Object r2 = r2.get(r3)
            r0 = r2
            com.leedarson.smartcamera.kvswebrtc.utils.f$a r0 = (com.leedarson.smartcamera.kvswebrtc.utils.f.a) r0
        L_0x0054:
            org.webrtc.SurfaceViewRenderer r2 = r1.b
            r1.c(r2, r0)
        L_0x0059:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.utils.f.m():void");
    }

    public void k(int trackId) {
        Object[] objArr = {new Integer(trackId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10135, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            a("[解码器设置] 设置当前的trackId setCurrentTrackId trackId: " + trackId + " render: " + this.b);
            if (trackId == this.c) {
                b("[解码器设置] 当前要设置的trackId与现有的trackId一致 ");
                return;
            }
            this.c = trackId;
            i();
            a track = g(trackId);
            if (track != null) {
                SurfaceViewRenderer surfaceViewRenderer = this.b;
                if (surfaceViewRenderer != null) {
                    surfaceViewRenderer.setKeyFrameRequired(true);
                }
                c(this.b, track);
            }
        }
    }

    public a f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10136, new Class[0], a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        return g(this.c);
    }

    public void l(SurfaceViewRenderer render) {
        if (!PatchProxy.proxy(new Object[]{render}, this, changeQuickRedirect, false, 10137, new Class[]{SurfaceViewRenderer.class}, Void.TYPE).isSupported) {
            if (this.b != null) {
                i();
            }
            this.b = render;
            m();
        }
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10138, new Class[0], Void.TYPE).isSupported) {
            i();
            this.a.clear();
        }
    }

    public void d(MediaStreamTrack track, int trackIdx) {
        if (!PatchProxy.proxy(new Object[]{track, new Integer(trackIdx)}, this, changeQuickRedirect, false, 10139, new Class[]{MediaStreamTrack.class, Integer.TYPE}, Void.TYPE).isSupported) {
            a mediaTrack = new a();
            mediaTrack.a = track;
            mediaTrack.b = trackIdx;
            this.a.add(mediaTrack);
            Log.i("LdsMediaTrackManager", "addTrack = " + trackIdx);
        }
    }

    public void j(boolean enable) {
        VideoTrack videoTrack;
        Object[] objArr = {new Byte(enable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10140, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            try {
                if (g(this.c) != null && (videoTrack = (VideoTrack) g(this.c).a) != null) {
                    videoTrack.setEnabled(enable);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("LdsMediaTrackManager", "setCurrentTrackEnable: exception=" + ex.toString());
            }
        }
    }

    public List<VideoTrack> h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10141, new Class[0], List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        List<VideoTrack> tracks = new ArrayList<>();
        for (a ldsTrack : this.a) {
            tracks.add((VideoTrack) ldsTrack.a);
        }
        return tracks;
    }
}
