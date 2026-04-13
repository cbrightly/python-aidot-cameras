package leedarson.platform.playersdk;

import android.util.Log;
import android.view.Surface;
import java.io.PrintStream;

public class PlayerSDK {
    public static final int DECODER_PLAYER = 1;
    public static final int DECODER_PLAYER_SOFT = 4;
    public static final int FORMAT_PLAYER = 3;
    public static final int FULL_PLAYER = 2;
    public static final int PURE_PLAYER = 0;
    static boolean hasInit = false;
    private CallBack mCb = null;
    private int mPlayerHandle = -1;
    private int mPlayerType = -1;

    public interface CallBack {
        int playerCallBack(int i, byte[] bArr, int i2);

        void snapYUVCallback(String str, byte[] bArr, int i, int i2);
    }

    private final native void changeMuteState(int i, boolean z);

    private final native void clearBuffer(int i);

    private final native void clearDisplay(int i);

    private final native int createPlayer(int i);

    private final native int destroyPlayer(int i);

    private final native int getAvSyncMode(int i);

    private final native long getDuration(int i);

    private static final native int getSupportMaxPlayer();

    private static final native int init(int i, String str, int i2);

    private final native int initAudioSound(int i, int i2, int i3, int i4);

    private final native int initVideoSurface(int i, Surface surface, int i2, int i3, int i4);

    private final native int pause(int i);

    private final native int playAudio(int i, byte[] bArr, int i2, long j, int i3);

    private final native int playVideo(int i, byte[] bArr, int i2, long j, int i3, int i4, int i5);

    private final native void removeSurface(int i);

    private final native int resume(int i);

    private final native int seek(int i, long j);

    private final native int sendFormatData(int i, byte[] bArr, int i2);

    private final native int setAVSyncMode(int i, int i2);

    private final native int setCallBack(int i, String str);

    private final native int setDecoderType(int i, int i2);

    private final native int setDisplayMode(int i, int i2);

    private final native int setDisplayRotate(int i, int i2);

    private final native void setMaxBufferTime(int i, int i2);

    private final native int setMinBuffTime(int i, int i2);

    private final native int setMultiThreadDecode(int i, boolean z, int i2);

    private final native int setPolicy(int i, int i2);

    private static final native int setSDKLogLevel(int i);

    private final native int setSurface(int i, Surface surface);

    private final native int setWebrtcAudio(int i, boolean z, boolean z2, boolean z3);

    private final native int snapshot(int i, String str, int i2);

    private final native int snapshot2(int i, String str);

    private final native int start(int i, Surface surface, int i2, String str, int i3);

    private final native int startAudioCapture(int i, int i2, int i3, int i4);

    private final native int startMuxer(int i, String str);

    private final native int startPlay(int i, Surface surface, int i2, int i3);

    private final native int startPlaySoft(int i, Surface surface, int i2, int i3);

    private final native int startRecoder(int i, String str, int i2, long j);

    private final native int stop(int i, boolean z);

    private final native int stopAudioCapture(int i);

    private final native int stopMuxer(int i);

    private final native void stopPlay(int i);

    private final native int stopRecoder(int i, String str);

    private final native int surfaceViewChanged(int i, int i2, int i3);

    private final native int swapSurface(int i, Surface surface);

    private static final native void unInit();

    private final native void unInitAudioSound(int i);

    private final native void unInitVideoSurface(int i);

    private final native byte[] webrtcAudioProcess(int i, byte[] bArr, int i2, int i3);

    private final native int writeAudioData(int i, byte[] bArr, int i2, long j, int i3);

    private final native int writeVideoData(int i, byte[] bArr, int i2, long j, int i3, int i4, int i5);

    private final native int yuvCallBackEnable(int i, boolean z);

    public final native byte[] scaleFFmpeg(byte[] bArr, int i, int i2, int i3, int i4);

    static {
        try {
            System.loadLibrary("xplayer");
        } catch (UnsatisfiedLinkError ule) {
            PrintStream printStream = System.out;
            printStream.println("loadLibrary(xplayer)," + ule.getMessage());
        }
    }

    public static int initPlayerSDK(int max, String logPath, int logSize) {
        hasInit = true;
        return init(max, logPath, logSize);
    }

    public static void unInitPlayerSDK() {
        unInit();
        hasInit = false;
    }

    public static int setLogLevel(int level) {
        return setSDKLogLevel(level);
    }

    public int createPlayerSDK(int type, CallBack callBack) {
        if (callBack == null) {
            return -1;
        }
        int createPlayer = createPlayer(1);
        this.mPlayerHandle = createPlayer;
        if (createPlayer < 0) {
            return createPlayer;
        }
        this.mPlayerType = type;
        this.mCb = callBack;
        return setCallBack(createPlayer, "playerNativeCallBack");
    }

    public final int playerNativeCallBack(int type, byte[] data, int dataLen) {
        CallBack callBack = this.mCb;
        if (callBack == null) {
            return 0;
        }
        callBack.playerCallBack(type, data, dataLen);
        return 0;
    }

    public final void yuvCallBack(String path, byte[] data, int width, int height) {
        CallBack callBack = this.mCb;
        if (callBack != null) {
            callBack.snapYUVCallback(path, data, width, height);
        }
    }

    public int destoryPlayerSDK() {
        if (!hasInit) {
            return -1;
        }
        int ret = destroyPlayer(this.mPlayerHandle);
        this.mPlayerType = -1;
        this.mCb = null;
        return ret;
    }

    public int startDecoderPlay(Surface surface, int videoCodec, int audioCodec) {
        int i = this.mPlayerType;
        if (i == 1) {
            return startPlay(this.mPlayerHandle, surface, videoCodec, audioCodec);
        }
        if (i == 4) {
            return startPlaySoft(this.mPlayerHandle, surface, videoCodec, audioCodec);
        }
        return -1;
    }

    public void stopDecoderPlay() {
        if (hasInit) {
            stopPlay(this.mPlayerHandle);
        }
    }

    public int snapshot(String path, int format) {
        return snapshot(this.mPlayerHandle, path, format);
    }

    public int snapshot2(String path) {
        return snapshot2(this.mPlayerHandle, path);
    }

    public int startRecoder(String path, int format, long maxTime) {
        return startRecoder(this.mPlayerHandle, path, format, maxTime);
    }

    public int stopRecoder(String newName) {
        return stopRecoder(this.mPlayerHandle, newName);
    }

    public int setAVSyncMode(int mode) {
        return setAVSyncMode(this.mPlayerHandle, mode);
    }

    public int pause() {
        return pause(this.mPlayerHandle);
    }

    public int resume() {
        return resume(this.mPlayerHandle);
    }

    public void clearBuffer() {
        clearBuffer(this.mPlayerHandle);
    }

    public void setMaxBuffTime(int buffTime) {
        setMaxBufferTime(this.mPlayerHandle, buffTime);
    }

    public int setMinBuffTime(int bufTime) {
        return setMinBuffTime(this.mPlayerHandle, bufTime);
    }

    public int drawVideo(byte[] data, int len, long timeStamp, int width, int height, int codec) {
        return playVideo(this.mPlayerHandle, data, len, timeStamp, width, height, codec);
    }

    public int soundAudio(byte[] data, int len, long timeStamp, int codec) {
        return playAudio(this.mPlayerHandle, data, len, timeStamp, codec);
    }

    public void changeMuteState(boolean mute) {
        changeMuteState(this.mPlayerHandle, mute);
    }

    public final int setDisplayRotate(int rotate) {
        return setDisplayRotate(this.mPlayerHandle, rotate);
    }

    public final int surfaceChanged(int width, int height) {
        return surfaceViewChanged(this.mPlayerHandle, width, height);
    }

    public final int setSurface(Surface surface) {
        return setSurface(this.mPlayerHandle, surface);
    }

    public final int setMultiThreadDecode(boolean isMulti, int threadCount) {
        return setMultiThreadDecode(this.mPlayerHandle, isMulti, threadCount);
    }

    public int setWebrtcAudio(boolean nsEnable, boolean agcEnable, boolean aecmEnable) {
        return setWebrtcAudio(this.mPlayerHandle, nsEnable, agcEnable, aecmEnable);
    }

    public byte[] webrtcAudioProcess(byte[] indata, int len, int outLen) {
        return webrtcAudioProcess(this.mPlayerHandle, indata, len, outLen);
    }

    public final int startMuxer(String path) {
        int createPlayer = createPlayer(1);
        this.mPlayerHandle = createPlayer;
        return startMuxer(createPlayer, path);
    }

    public final void stopMuxer() {
        stopMuxer(this.mPlayerHandle);
        destroyPlayer(this.mPlayerHandle);
    }

    public final int writeVideoData(byte[] data, int dataLen, long timeStamp, int width, int height, int codec) {
        return writeVideoData(this.mPlayerHandle, data, dataLen, timeStamp, width, height, codec);
    }

    public final int writeAudioData(byte[] data, int dataLen, long timeStamp, int codec) {
        return writeAudioData(this.mPlayerHandle, data, dataLen, timeStamp, codec);
    }

    public final int startAudioCapture(int sampleRate, int channel, int bits) {
        Log.e("TAG", "startAudioCapture: sampleRate= " + sampleRate);
        return startAudioCapture(this.mPlayerHandle, sampleRate, channel, bits);
    }

    public final int stopAudioCapture() {
        return stopAudioCapture(this.mPlayerHandle);
    }

    public final int setYUVCbEnable(boolean enable) {
        return yuvCallBackEnable(this.mPlayerHandle, enable);
    }
}
