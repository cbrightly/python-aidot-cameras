package com.leedarson.smartcamera.kvswebrtc.record;

import android.annotation.TargetApi;
import android.media.AudioTrack;
import androidx.annotation.NonNull;
import com.leedarson.log.f;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.nio.ByteBuffer;
import org.webrtc.audio.JavaAudioDeviceModule;

/* compiled from: AudioTrackInterceptor */
public final class i extends AudioTrack {
    public static ChangeQuickRedirect changeQuickRedirect;
    public final AudioTrack a;
    private final JavaAudioDeviceModule.SamplesReadyCallback b;

    public i(@NonNull AudioTrack originalTrack, @NonNull JavaAudioDeviceModule.SamplesReadyCallback callback) {
        super(0, 44200, 4, 2, 128, 1);
        f.b("WEBRTC", "AudioTrackInterceptor: ");
        this.a = originalTrack;
        this.b = callback;
    }

    public int write(@NonNull byte[] audioData, int offsetInBytes, int sizeInBytes) {
        Object[] objArr = {audioData, new Integer(offsetInBytes), new Integer(sizeInBytes)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9945, new Class[]{byte[].class, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        f.b("WEBRTC", "write_AudioFormat: " + this.a.getAudioFormat() + " channelCount: " + this.a.getChannelCount() + " sampleRate: " + this.a.getSampleRate());
        this.b.onWebRtcAudioRecordSamplesReady(new JavaAudioDeviceModule.AudioSamples(this.a.getAudioFormat(), this.a.getChannelCount(), this.a.getSampleRate(), audioData));
        return this.a.write(audioData, offsetInBytes, sizeInBytes);
    }

    @TargetApi(21)
    public int write(@NonNull ByteBuffer audioData, int sizeInBytes, int writeMode) {
        Object[] objArr = {audioData, new Integer(sizeInBytes), new Integer(writeMode)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9946, new Class[]{ByteBuffer.class, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        byte[] trimmed = new byte[sizeInBytes];
        int position = audioData.position();
        audioData.get(trimmed, 0, sizeInBytes);
        audioData.position(position);
        this.b.onWebRtcAudioRecordSamplesReady(new JavaAudioDeviceModule.AudioSamples(this.a.getAudioFormat(), this.a.getChannelCount(), this.a.getSampleRate(), trimmed));
        return this.a.write(audioData, sizeInBytes, writeMode);
    }

    public int getPlayState() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9947, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.a.getPlayState();
    }

    public void play() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9948, new Class[0], Void.TYPE).isSupported) {
            this.a.play();
        }
    }

    public void stop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9949, new Class[0], Void.TYPE).isSupported) {
            this.a.stop();
        }
    }

    @TargetApi(24)
    public int getUnderrunCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9950, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.a.getUnderrunCount();
    }

    @TargetApi(24)
    public int getBufferCapacityInFrames() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9951, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.a.getBufferCapacityInFrames();
    }

    @TargetApi(23)
    public int getBufferSizeInFrames() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9952, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.a.getBufferSizeInFrames();
    }

    public void release() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9953, new Class[0], Void.TYPE).isSupported) {
            this.a.release();
        }
    }

    public int getPlaybackHeadPosition() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9954, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.a.getPlaybackHeadPosition();
    }
}
