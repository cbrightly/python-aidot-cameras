package com.leedarson.skiprope.player;

import android.media.MediaPlayer;
import android.os.Handler;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: Mp3Player */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public MediaPlayer a;
    private String b;
    /* access modifiers changed from: private */
    public boolean c;

    static /* synthetic */ void c(e x0, float x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Float(x1)}, (Object) null, changeQuickRedirect, true, 9616, new Class[]{e.class, Float.TYPE}, Void.TYPE).isSupported) {
            x0.j(x1);
        }
    }

    public void g(String absoluteName) {
        if (!PatchProxy.proxy(new Object[]{absoluteName}, this, changeQuickRedirect, false, 9609, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (absoluteName.equals(this.b)) {
                com.leedarson.skiprope.util.a.c("重复bgm");
                return;
            }
            this.b = absoluteName;
            try {
                MediaPlayer mediaPlayer = this.a;
                float f = 0.0f;
                if (mediaPlayer == null) {
                    MediaPlayer mediaPlayer2 = new MediaPlayer();
                    this.a = mediaPlayer2;
                    mediaPlayer2.setDataSource(absoluteName);
                    this.a.prepareAsync();
                    this.a.setLooping(true);
                    this.a.setOnPreparedListener(a.c);
                    this.a.setOnCompletionListener(b.c);
                    if (!this.c) {
                        f = 1.0f;
                    }
                    j(f);
                } else if (mediaPlayer.isPlaying()) {
                    com.leedarson.skiprope.util.a.c("播放中切换bgm:" + absoluteName);
                    this.a.stop();
                    new Handler().postDelayed(new a(absoluteName), 500);
                } else {
                    MediaPlayer mediaPlayer3 = new MediaPlayer();
                    this.a = mediaPlayer3;
                    mediaPlayer3.setDataSource(absoluteName);
                    this.a.prepare();
                    this.a.setLooping(true);
                    this.a.start();
                    if (!this.c) {
                        f = 1.0f;
                    }
                    j(f);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static /* synthetic */ void d(MediaPlayer mediaPlayer) {
        if (!PatchProxy.proxy(new Object[]{mediaPlayer}, (Object) null, changeQuickRedirect, true, 9615, new Class[]{MediaPlayer.class}, Void.TYPE).isSupported) {
            mediaPlayer.start();
        }
    }

    static /* synthetic */ void e(MediaPlayer mediaPlayer) {
    }

    /* compiled from: Mp3Player */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        a(String str) {
            this.c = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9617, new Class[0], Void.TYPE).isSupported) {
                try {
                    e.this.a.reset();
                    e.this.a.setDataSource(this.c);
                    e.this.a.prepare();
                    e.this.a.setLooping(true);
                    e.this.a.start();
                    e eVar = e.this;
                    e.c(eVar, eVar.c ? 0.0f : 1.0f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9610, new Class[0], Void.TYPE).isSupported) {
            MediaPlayer mediaPlayer = this.a;
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                this.a.pause();
            }
            this.b = null;
        }
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9612, new Class[0], Void.TYPE).isSupported) {
            MediaPlayer mediaPlayer = this.a;
            if (mediaPlayer != null) {
                mediaPlayer.release();
                this.a = null;
            }
            this.b = null;
        }
    }

    public void i(boolean mute) {
        if (!PatchProxy.proxy(new Object[]{new Byte(mute ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 9613, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.c = mute;
            j(mute ? 0.0f : 1.0f);
        }
    }

    private void j(float vol) {
        MediaPlayer mediaPlayer;
        Object[] objArr = {new Float(vol)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9614, new Class[]{Float.TYPE}, Void.TYPE).isSupported || (mediaPlayer = this.a) == null) {
            return;
        }
        if (vol != 0.0f) {
            mediaPlayer.setVolume(0.8f, 0.8f);
        } else {
            mediaPlayer.setVolume(0.0f, 0.0f);
        }
    }
}
