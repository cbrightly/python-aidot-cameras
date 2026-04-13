package io.microshow.rxffmpeg.player;

import android.graphics.SurfaceTexture;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import io.microshow.rxffmpeg.player.IMediaPlayer;
import java.lang.ref.WeakReference;

public class SystemMediaPlayerImpl extends SystemMediaPlayer implements TextureView.SurfaceTextureListener {
    private static SurfaceTexture mSurfaceTexture;
    private WeakReference<TextureView> mWeakTextureView;

    public void setTextureView(TextureView textureView) {
        if (textureView != null) {
            this.mWeakTextureView = new WeakReference<>(textureView);
            textureView.setSurfaceTextureListener(this);
        }
    }

    private TextureView getTextureView() {
        TextureView view;
        WeakReference<TextureView> weakReference = this.mWeakTextureView;
        if (weakReference == null || (view = (TextureView) weakReference.get()) == null) {
            return null;
        }
        return view;
    }

    public void play(String path, boolean isLooping) {
        if (!TextUtils.isEmpty(path)) {
            this.mMediaPlayer.reset();
            setDataSource(path);
            setLooping(isLooping);
            setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                public void onPrepared(IMediaPlayer mediaPlayer) {
                    SystemMediaPlayerImpl.this.start();
                }
            });
            prepare();
        }
    }

    public void release() {
        super.release();
        SurfaceTexture surfaceTexture = mSurfaceTexture;
        if (surfaceTexture != null) {
            surfaceTexture.release();
            mSurfaceTexture = null;
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        if (getTextureView() != null) {
            if (mSurfaceTexture == null) {
                mSurfaceTexture = surfaceTexture;
                setSurface(new Surface(mSurfaceTexture));
            } else if (getTextureView() != null) {
                getTextureView().setSurfaceTexture(mSurfaceTexture);
            }
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }
}
