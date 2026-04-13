package org.webrtc.audio;

import android.media.AudioTrack;
import android.util.Log;
import com.leedarson.log.f;
import com.leedarson.smartcamera.kvswebrtc.record.i;
import java.lang.reflect.Field;
import org.webrtc.audio.JavaAudioDeviceModule;

public class WebRTCAudioTrackUtils {
    private static final String TAG = "WebRtcAudioTrackUtils";

    public static void attachOutputCallback(JavaAudioDeviceModule.SamplesReadyCallback callback, JavaAudioDeviceModule audioDeviceModule) {
        Field audioOutputField = audioDeviceModule.getClass().getDeclaredField("audioOutput");
        audioOutputField.setAccessible(true);
        WebRtcAudioTrack audioOutput = (WebRtcAudioTrack) audioOutputField.get(audioDeviceModule);
        if (audioOutput != null) {
            f.b(TAG, "audioOutput found");
            Field audioTrackField = audioOutput.getClass().getDeclaredField("audioTrack");
            audioTrackField.setAccessible(true);
            AudioTrack audioTrack = (AudioTrack) audioTrackField.get(audioOutput);
            if (audioTrack != null) {
                f.b(TAG, "audioTrack found");
                audioTrackField.set(audioOutput, new i(audioTrack, callback));
                f.b(TAG, "callback attached");
            }
        }
    }

    public static void detachOutputCallback(JavaAudioDeviceModule audioDeviceModule) {
        try {
            Log.i(TAG, "Searching for audioTrack");
            Field audioOutputField = audioDeviceModule.getClass().getDeclaredField("audioOutput");
            audioOutputField.setAccessible(true);
            WebRtcAudioTrack audioOutput = (WebRtcAudioTrack) audioOutputField.get(audioDeviceModule);
            if (audioOutput != null) {
                Field audioTrackField = audioOutput.getClass().getDeclaredField("audioTrack");
                audioTrackField.setAccessible(true);
                AudioTrack audioTrack = (AudioTrack) audioTrackField.get(audioOutput);
                if (audioTrack instanceof i) {
                    audioTrackField.set(audioOutput, ((i) audioTrack).a);
                    Log.i(TAG, "audioTrack found");
                    return;
                }
                Log.w(TAG, "audioTrack lost");
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to detach callback", e);
        }
    }
}
