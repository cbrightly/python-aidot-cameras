package com.leedarson.skiprope.player;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/* compiled from: AudioTrackPlayer */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private int b = 16000;
    private int c = 4;
    private int d = 2;
    private AudioTrack e;
    int f;
    private boolean g = false;
    private a h;

    /* compiled from: AudioTrackPlayer */
    public interface a {
        void a(String str);
    }

    @RequiresApi(api = 23)
    public c(Context context) {
        this.a = context;
        this.f = AudioTrack.getMinBufferSize(16000, 4, 2);
        a();
    }

    @RequiresApi(api = 23)
    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9593, new Class[0], Void.TYPE).isSupported) {
            this.e = new AudioTrack.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(1).setContentType(2).build()).setAudioFormat(new AudioFormat.Builder().setSampleRate(this.b).setChannelMask(this.c).setEncoding(this.d).build()).setTransferMode(1).setBufferSizeInBytes(this.f).build();
        }
    }

    public void e(String name) {
        if (!PatchProxy.proxy(new Object[]{name}, this, changeQuickRedirect, false, 9594, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (b()) {
                h();
            }
            try {
                c("play start:" + name);
                this.g = true;
                InputStream inputStream = new FileInputStream(new File(name));
                this.e.play();
                byte[] buff = new byte[this.f];
                while (true) {
                    int read = inputStream.read(buff);
                    int len = read;
                    if (read == -1 || !this.g) {
                        c("play finished:" + name);
                        a aVar = this.h;
                    } else if (this.e.getPlayState() == 3) {
                        this.e.write(buff, 0, len);
                    }
                }
                c("play finished:" + name);
                a aVar2 = this.h;
                if (aVar2 != null) {
                    aVar2.a(name);
                }
            } catch (Exception e2) {
                d(e2.toString());
                e2.printStackTrace();
            }
        }
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9595, new Class[0], Void.TYPE).isSupported) {
            c("stop");
            this.g = false;
            this.e.stop();
        }
    }

    public void g(a lister) {
        this.h = lister;
    }

    private void c(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 9597, new Class[]{String.class}, Void.TYPE).isSupported) {
            Log.d("AudioTrack", msg);
        }
    }

    private void d(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 9598, new Class[]{String.class}, Void.TYPE).isSupported) {
            Log.e("AudioTrack", msg);
        }
    }

    public boolean b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9599, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        AudioTrack audioTrack = this.e;
        return audioTrack != null && audioTrack.getPlayState() == 3;
    }

    public void f(float gain) {
        AudioTrack audioTrack;
        Object[] objArr = {new Float(gain)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9600, new Class[]{Float.TYPE}, Void.TYPE).isSupported && (audioTrack = this.e) != null) {
            audioTrack.setVolume(gain);
        }
    }
}
