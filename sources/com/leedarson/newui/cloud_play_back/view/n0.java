package com.leedarson.newui.cloud_play_back.view;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.io.IOException;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* compiled from: IJKPlayerRadarParser */
public class n0 {
    public static ChangeQuickRedirect changeQuickRedirect;
    private IjkMediaPlayer a;

    public n0() {
        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
        this.a = ijkMediaPlayer;
        ijkMediaPlayer.setOption(4, "only-obtain-radar", 1);
    }

    public final void setOnVideoRadarDataListener(IMediaPlayer.OnVideoRadarDataListener listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 3951, new Class[]{IMediaPlayer.OnVideoRadarDataListener.class}, Void.TYPE).isSupported) {
            this.a.setOnVideoRadarDataListener(listener);
        }
    }

    public void a(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 3952, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                this.a.setDataSource(path);
                this.a.prepareAsync();
                this.a.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3953(0xf71, float:5.54E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            tv.danmaku.ijk.media.player.IjkMediaPlayer r1 = r0.a
            if (r1 == 0) goto L_0x0026
            r1.stop()
            tv.danmaku.ijk.media.player.IjkMediaPlayer r1 = r0.a
            r1.release()
            r1 = 0
            r0.a = r1
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.view.n0.b():void");
    }
}
