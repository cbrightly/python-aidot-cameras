package com.king.zxing;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import com.king.zxing.util.b;
import com.leedarson.serviceimpl.camera.R$raw;
import java.io.Closeable;
import java.io.IOException;

/* compiled from: BeepManager */
public final class g implements MediaPlayer.OnErrorListener, Closeable {
    private static final String c = g.class.getSimpleName();
    private final Activity d;
    private MediaPlayer f = null;
    private boolean q;
    private boolean x;

    g(Activity activity) {
        this.d = activity;
        l();
    }

    public void i(boolean vibrate) {
        this.x = vibrate;
    }

    public void g(boolean playBeep) {
        this.q = playBeep;
    }

    /* access modifiers changed from: package-private */
    public synchronized void l() {
        j(PreferenceManager.getDefaultSharedPreferences(this.d), this.d);
        if (this.q && this.f == null) {
            this.d.setVolumeControlStream(3);
            this.f = a(this.d);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void c() {
        MediaPlayer mediaPlayer;
        if (this.q && (mediaPlayer = this.f) != null) {
            mediaPlayer.start();
        }
        if (this.x) {
            try {
                ((Vibrator) this.d.getSystemService("vibrator")).vibrate(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return;
    }

    private static boolean j(SharedPreferences prefs, Context activity) {
        boolean shouldPlayBeep = prefs.getBoolean("preferences_play_beep", false);
        if (!shouldPlayBeep) {
            return shouldPlayBeep;
        }
        try {
            if (((AudioManager) activity.getSystemService("audio")).getRingerMode() != 2) {
                return false;
            }
            return shouldPlayBeep;
        } catch (Exception e) {
            e.printStackTrace();
            return shouldPlayBeep;
        }
    }

    @TargetApi(19)
    private MediaPlayer a(Context activity) {
        AssetFileDescriptor file;
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            file = activity.getResources().openRawResourceFd(R$raw.zxl_beep);
            mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setAudioStreamType(3);
            mediaPlayer.setLooping(false);
            mediaPlayer.setVolume(0.1f, 0.1f);
            mediaPlayer.prepare();
            file.close();
            return mediaPlayer;
        } catch (IOException ioe) {
            b.k(ioe);
            mediaPlayer.release();
            return null;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public synchronized boolean onError(MediaPlayer mp, int what, int extra) {
        if (what == 100) {
            this.d.finish();
        } else {
            close();
            l();
        }
        return true;
    }

    public synchronized void close() {
        MediaPlayer mediaPlayer = this.f;
        if (mediaPlayer != null) {
            mediaPlayer.release();
            this.f = null;
        }
    }
}
