package com.leedarson.bean;

import android.content.Context;
import androidx.core.view.PointerIconCompat;
import com.leedarson.module_base.R$string;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

public class IPCLiveAction extends Observable {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean TurnOnOff = true;
    private Context context;
    int countTime = 0;
    private boolean isAlarm = false;
    private boolean isFocusing = false;
    private boolean isFullScreen = false;
    private boolean isLightOn = false;
    private boolean isPathOn = false;
    private boolean isPlaying = false;
    private boolean isRecording = false;
    private boolean isTalking = false;
    private boolean isTrackingMode = false;
    private boolean isWide = false;
    private boolean mute = true;
    Timer timer;

    private IPCLiveAction() {
    }

    public IPCLiveAction(Context context2) {
        this.context = context2;
    }

    public boolean isAlarm() {
        return this.isAlarm;
    }

    public void setAlarm(boolean alarm) {
        this.isAlarm = alarm;
    }

    public boolean isMute() {
        return this.mute;
    }

    public void setMute(boolean mute2) {
        this.mute = mute2;
    }

    public boolean isRecording() {
        return this.isRecording;
    }

    public void setRecording(boolean recording) {
        this.isRecording = recording;
    }

    public boolean isTalking() {
        return this.isTalking;
    }

    public void setTalking(boolean talking) {
        this.isTalking = talking;
    }

    public boolean isLightOn() {
        return this.isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        this.isLightOn = lightOn;
    }

    public boolean isTrackingMode() {
        return this.isTrackingMode;
    }

    public void setTrackingMode(boolean trackingMode) {
        this.isTrackingMode = trackingMode;
    }

    public boolean isFullScreen() {
        return this.isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.isFullScreen = fullScreen;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setPlaying(boolean playing) {
        this.isPlaying = playing;
    }

    public boolean isTurnOnOff() {
        return this.TurnOnOff;
    }

    public void setTurnOnOff(boolean turnOnOff) {
        this.TurnOnOff = turnOnOff;
    }

    public boolean isWide() {
        return this.isWide;
    }

    public void setWide(boolean wide) {
        this.isWide = wide;
    }

    public boolean isFocusing() {
        return this.isFocusing;
    }

    public void setFocusing(boolean focusing) {
        this.isFocusing = focusing;
    }

    public void setPathOn(boolean pathOn) {
        this.isPathOn = pathOn;
    }

    public int getCountTime() {
        return this.countTime;
    }

    public void startRecordTimer() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1003, new Class[0], Void.TYPE).isSupported) {
            stopRecordTimer(false);
            Timer timer2 = new Timer();
            this.timer = timer2;
            this.isRecording = true;
            timer2.schedule(new TimerTask() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void run() {
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1010, new Class[0], Void.TYPE).isSupported) {
                        IPCLiveAction.this.notifyChangeObservers();
                        IPCLiveAction iPCLiveAction = IPCLiveAction.this;
                        int i = iPCLiveAction.countTime + 1;
                        iPCLiveAction.countTime = i;
                        if (i == 3600000) {
                            iPCLiveAction.stopRecordTimer(true);
                        }
                    }
                }
            }, 10, 1000);
        }
    }

    public void stopRecordTimer(boolean refreshUI) {
        if (!PatchProxy.proxy(new Object[]{new Byte(refreshUI ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 1004, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            Timer timer2 = this.timer;
            if (timer2 != null) {
                timer2.cancel();
            }
            this.timer = null;
            this.countTime = 0;
            this.isRecording = false;
            OpItem opItem = OpItem.record;
            opItem.setWorking(false);
            opItem.setDescName(PubUtils.getString(this.context, R$string.record));
            if (refreshUI) {
                notifyChangeObservers();
            }
        }
    }

    public String formatTime(int seconds) {
        String min;
        String se;
        String str;
        StringBuilder sb;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(seconds)}, this, changeQuickRedirect, false, 1005, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (seconds < 60) {
            if (seconds < 10) {
                sb = new StringBuilder();
                str = "00:0";
            } else {
                sb = new StringBuilder();
                str = "00:";
            }
            sb.append(str);
            sb.append(seconds);
            return sb.toString();
        }
        int minute = seconds / 60;
        int sec = seconds % 60;
        if (minute < 10) {
            min = "0" + minute;
        } else {
            min = String.valueOf(minute);
        }
        if (sec < 10) {
            se = "0" + sec;
        } else {
            se = String.valueOf(sec);
        }
        return min + ":" + se;
    }

    private JSONObject createStateJson() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1006, new Class[0], JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("isTalking", this.isTalking);
            json.put("isRecording", this.isRecording);
            json.put("recordTime", (Object) formatTime(this.countTime));
            json.put("isLightOn", this.isLightOn);
            json.put(H5ActionName.ACTION_MUTE, this.mute);
            json.put("isAlarm", this.isAlarm);
            json.put("isTrackingMode", this.isTrackingMode);
            json.put("isFullScreen", this.isFullScreen);
            json.put("TurnOnOff", this.TurnOnOff);
            json.put("isWide", this.isWide);
            json.put("isPathOn", this.isPathOn);
            json.put("isFocusing", this.isFocusing);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private JSONObject createPlayStateJson() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1007, new Class[0], JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("isPlaying", this.isPlaying);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void notifyChangeObservers() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, PointerIconCompat.TYPE_TEXT, new Class[0], Void.TYPE).isSupported) {
            a.g("NewLive").a("notifyChangeObservers...", new Object[0]);
            setChanged();
            notifyObservers(createStateJson());
        }
    }

    public void notifyPlayChangeObservers() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1009, new Class[0], Void.TYPE).isSupported) {
            a.g("NewLive").a("notifyChangeObservers...", new Object[0]);
            setChanged();
            notifyObservers(createPlayStateJson());
        }
    }
}
