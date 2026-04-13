package com.leedarson.bean;

import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.PointerIconCompat;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import com.leedarson.sender.e;
import com.leedarson.serviceimpl.f;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.Constants;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;
import timber.log.a;

public abstract class IRhyDevice {
    public static final String BASE_VERSION = "1.0.0";
    public static final String RHYTHM_TYPE_ASYNC = "async";
    public static final String RHYTHM_TYPE_SYNC = "sync";
    public static final int TAP_AUTO = 6;
    public static final int TAP_CALM = 5;
    public static final int TAP_CUSTOM = 0;
    public static final int TAP_LIVELY = 3;
    public static final int TAP_MEDIUM = 2;
    public static final int TAP_RAPID = 4;
    public static final int TAP_SMOOTH = 1;
    public static final String TYPE_BLE = "BLE";
    public static final String TYPE_BLE_MESH = "BLEMesh";
    public static final String TYPE_BLE_QHM = "BLE-QHM";
    public static final String TYPE_TCP = "TCP";
    public static final String TYPE_UDP = "UDP";
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean advanceCrest = false;
    private int colorIndex;
    private ConcurrentHashMap<String, Integer> colorIndexMap = new ConcurrentHashMap<>();
    private ArrayList crestRecords = new ArrayList();
    private ArrayList crestTimeRecords = new ArrayList();
    protected JSONArray effectArray = null;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected boolean isGroup = false;
    protected AtomicBoolean isRunning;
    protected boolean isUpdateEffect = false;
    private int largeThanMinVolumeTimes;
    protected int lastColor;
    private long lastColorChangeTime;
    private ConcurrentHashMap<String, Long> lastColorChangeTimeMap = new ConcurrentHashMap<>();
    private int lastGetEffectFading;
    private long lastGetEffectTime;
    private int lastModel2Color = 0;
    protected int[] lastRhythmColors;
    private double lastSlopeRate;
    private int lessThanMaxVolumeTimes;
    protected List<String> macList = new ArrayList();
    private double maxAmplitude = 0.1d;
    private double minAmplitude = 0.5d;
    private int modeMaxDim = 100;
    private int model2ColorIndex = 0;
    private int model3ColorIndex = 0;
    protected JSONObject protocolData;
    protected String protocolType;
    protected String rhyId;
    protected int rhythmChangeColorSpeed;
    protected int[] rhythmColors;
    protected int rhythmFading;
    protected List rhythmLightIndexes;
    protected int rhythmMAXDimming = 100;
    protected int rhythmMINDimming = 30;
    protected int rhythmMicSensitivity = 100;
    protected String rhythmMode;
    protected int rhythmTap = 1;
    protected RhythmThemeType rhythmThemeType;
    protected String rhythmType;
    protected String rhythmVersion = null;
    protected e sender;
    private double sensitivityFactor = 0.48d;
    private ArrayList troughRecords = new ArrayList();
    private ArrayList troughTimeRecords = new ArrayList();
    private ArrayList volumeRecords = new ArrayList();
    private ArrayList volumeTimeRecords = new ArrayList();

    public enum RhythmThemeType {
        rhythmThemeTypeUnknown,
        rhythmThemeTypeDefault,
        rhythmThemeTypeCustomize;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public abstract void send();

    public abstract void start();

    public abstract void stop();

    public void setMacList(List<String> macList2) {
        this.macList = macList2;
    }

    public List<String> getMacList() {
        return this.macList;
    }

    public List getRhythmLightIndexes() {
        return this.rhythmLightIndexes;
    }

    public void setRhythmLightIndexes(List rhythmLightIndexes2) {
        this.rhythmLightIndexes = rhythmLightIndexes2;
    }

    public String getRhythmMode() {
        return this.rhythmMode;
    }

    public void setRhythmMode() {
        int i = this.rhythmTap;
        if (i == 1) {
            this.rhythmMode = "Calm";
        } else if (i == 2) {
            this.rhythmMode = "Dynamic";
        } else {
            this.rhythmMode = "Party";
        }
    }

    public int[] getLastRhythmColors() {
        return this.lastRhythmColors;
    }

    public void setLastRhythmColors(int[] lastRhythmColors2) {
        this.lastRhythmColors = lastRhythmColors2;
    }

    public int getColorIndex() {
        return this.colorIndex;
    }

    public void setColorIndex(int colorIndex2) {
        this.colorIndex = colorIndex2;
    }

    public int getLastColor() {
        return this.lastColor;
    }

    public void setLastColor(int lastColor2) {
        this.lastColor = lastColor2;
    }

    public boolean isUpdateEffect() {
        return this.isUpdateEffect;
    }

    public void setUpdateEffect(boolean updateEffect) {
        this.isUpdateEffect = updateEffect;
    }

    public JSONArray getEffectArray() {
        return this.effectArray;
    }

    public void setEffectArray(JSONArray effectArray2) {
        this.effectArray = effectArray2;
    }

    public int getRhythmTap() {
        return this.rhythmTap;
    }

    public void setRhythmTap(int rhythmTap2) {
        Object[] objArr = {new Integer(rhythmTap2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1011, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.rhythmTap = rhythmTap2;
            setRhythmMode();
        }
    }

    public int getRhythmMicSensitivity() {
        return this.rhythmMicSensitivity;
    }

    public void setRhythmMicSensitivity(int rhythmMicSensitivity2) {
        this.rhythmMicSensitivity = rhythmMicSensitivity2;
    }

    public int getRhythmMAXDimming() {
        return this.rhythmMAXDimming;
    }

    public void setRhythmMAXDimming(int rhythmMAXDimming2) {
        if (rhythmMAXDimming2 < 30) {
            rhythmMAXDimming2 = 30;
        }
        if (rhythmMAXDimming2 > 100) {
            rhythmMAXDimming2 = 100;
        }
        this.rhythmMAXDimming = rhythmMAXDimming2;
    }

    public int[] getRhythmColors() {
        return this.rhythmColors;
    }

    public void setRhythmColors(int[] rhythmColors2) {
        this.rhythmColors = rhythmColors2;
    }

    public String getRhythmVersion() {
        return this.rhythmVersion;
    }

    public void setRhythmVersion(String rhythmVersion2) {
        this.rhythmVersion = rhythmVersion2;
    }

    public boolean isGroup() {
        return this.isGroup;
    }

    public void setGroup(boolean group) {
        this.isGroup = group;
    }

    public int getRhythmMINDimming() {
        return this.rhythmMINDimming;
    }

    public void setRhythmMINDimming(int rhythmMINDimming2) {
        if (rhythmMINDimming2 < 30) {
            rhythmMINDimming2 = 30;
        }
        if (rhythmMINDimming2 > 100) {
            rhythmMINDimming2 = 100;
        }
        this.rhythmMINDimming = rhythmMINDimming2;
    }

    public int getRhythmFading() {
        return this.rhythmFading;
    }

    public void setRhythmFading(int rhythmFading2) {
        this.rhythmFading = rhythmFading2;
    }

    public int getRhythmChangeColorSpeed() {
        return this.rhythmChangeColorSpeed;
    }

    public void setRhythmChangeColorSpeed(int rhythmChangeColorSpeed2) {
        this.rhythmChangeColorSpeed = rhythmChangeColorSpeed2;
    }

    public RhythmThemeType getRhythmThemeType() {
        return this.rhythmThemeType;
    }

    public void setRhythmThemeType(RhythmThemeType rhythmThemeType2) {
        this.rhythmThemeType = rhythmThemeType2;
    }

    public IRhyDevice(String protocolType2, JSONObject protocolData2) {
        this.protocolType = protocolType2;
        this.protocolData = protocolData2;
        this.isRunning = new AtomicBoolean(false);
    }

    public IRhyDevice(String rhythmType2, String protocolType2, JSONObject protocolData2) {
        this.rhythmType = rhythmType2;
        this.protocolType = protocolType2;
        this.protocolData = protocolData2;
        this.isRunning = new AtomicBoolean(false);
    }

    public String getRhyId() {
        return this.rhyId;
    }

    public void setRhyId(String rhyId2) {
        this.rhyId = rhyId2;
    }

    public void setRhythmType(String rhythmType2) {
        this.rhythmType = rhythmType2;
    }

    public String getRhythmType() {
        return this.rhythmType;
    }

    public String getProtocolType() {
        return this.protocolType;
    }

    public JSONObject getProtocolData() {
        return this.protocolData;
    }

    public int changeColorSpeed() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, PointerIconCompat.TYPE_NO_DROP, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int intervalMills = WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS;
        switch (this.rhythmTap) {
            case 0:
                intervalMills = this.rhythmChangeColorSpeed;
                break;
            case 1:
                intervalMills = 2500;
                break;
            case 2:
                intervalMills = WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS;
                break;
            case 3:
                intervalMills = 1500;
                break;
            case 4:
                intervalMills = 1000;
                break;
            case 6:
                intervalMills = f.c().a();
                break;
        }
        if (RHYTHM_TYPE_ASYNC.equals(this.rhythmType)) {
            intervalMills = 0;
        }
        if (RhythmThemeType.rhythmThemeTypeCustomize.equals(this.rhythmThemeType)) {
            return this.rhythmChangeColorSpeed;
        }
        return intervalMills;
    }

    public int changeColorSpeed2() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, PointerIconCompat.TYPE_ALL_SCROLL, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int intervalMills = 2 * WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS;
        switch (this.rhythmTap) {
            case 0:
                intervalMills = this.rhythmChangeColorSpeed;
                break;
            case 1:
                intervalMills = 2 * 2500;
                break;
            case 2:
                intervalMills = 2 * WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS;
                break;
            case 3:
                intervalMills = 2 * 1500;
                break;
            case 4:
                intervalMills = 2 * 1000;
                break;
            case 6:
                intervalMills = f.c().a() * 2;
                break;
        }
        if (RHYTHM_TYPE_ASYNC.equals(this.rhythmType)) {
            intervalMills = 0;
        }
        if (RhythmThemeType.rhythmThemeTypeCustomize.equals(this.rhythmThemeType)) {
            return this.rhythmChangeColorSpeed;
        }
        return intervalMills;
    }

    public void setEffectArray() {
        int[] fadingArray;
        double[] lightArray;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW, new Class[0], Void.TYPE).isSupported) {
            try {
                int i = this.rhythmTap;
                if (i == 1) {
                    lightArray = new double[]{1.0d, 0.5d};
                    fadingArray = new int[]{500, 500};
                } else if (i == 2) {
                    lightArray = new double[]{1.0d, 0.3d};
                    fadingArray = new int[]{200, 200};
                } else if (i == 3) {
                    lightArray = new double[]{1.0d, 0.5d};
                    fadingArray = new int[]{50, 50};
                } else if (i == 4) {
                    lightArray = new double[]{1.0d, 0.0d};
                    fadingArray = new int[]{50, 50};
                } else {
                    lightArray = new double[]{1.0d, 0.5d};
                    fadingArray = new int[]{200, 200};
                }
                JSONArray newEffectArray = new JSONArray();
                for (int i2 = 0; i2 < this.rhythmColors.length; i2++) {
                    JSONObject effectObj = new JSONObject();
                    effectObj.put(TypedValues.Custom.S_COLOR, this.rhythmColors[i2]);
                    effectObj.put("dimming", lightArray[i2 % lightArray.length] * ((double) this.rhythmMAXDimming));
                    effectObj.put("fading", fadingArray[i2 % fadingArray.length]);
                    newEffectArray.put((Object) effectObj);
                }
                this.effectArray = newEffectArray;
                a.b g = a.g("Rhythm");
                g.a("effectArray length:" + this.effectArray.length(), new Object[0]);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public boolean isOldVersion(String version) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{version}, this, changeQuickRedirect, false, PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (TextUtils.isEmpty(this.rhythmVersion)) {
                return true;
            }
            String[] checkVersion = version.split(".");
            String[] currentVersion = this.rhythmVersion.split(".");
            if (Integer.parseInt(currentVersion[0]) > Integer.parseInt(checkVersion[0])) {
                return false;
            }
            if (Integer.parseInt(checkVersion[0]) != Integer.parseInt(currentVersion[0])) {
                return true;
            }
            if (Integer.parseInt(currentVersion[1]) > Integer.parseInt(checkVersion[1])) {
                return false;
            }
            if (Integer.parseInt(checkVersion[1]) != Integer.parseInt(currentVersion[1]) || Integer.parseInt(currentVersion[2]) <= Integer.parseInt(checkVersion[2])) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean isSendCommand(double d) {
        double volumeUpperLimit;
        boolean isCrest;
        float A;
        boolean RhythmState;
        boolean isCrest2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Double(d)}, this, changeQuickRedirect, false, PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, new Class[]{Double.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        double amplitude = d;
        log("###来了个音乐点:" + amplitude);
        if (this.maxAmplitude < amplitude) {
            this.maxAmplitude = amplitude;
        }
        if (this.minAmplitude > amplitude) {
            this.minAmplitude = amplitude;
        }
        double volumeLowerLimit = this.minAmplitude;
        double volumeUpperLimit2 = this.maxAmplitude;
        long nowTimestamp = System.currentTimeMillis();
        if (amplitude >= this.maxAmplitude) {
            this.lessThanMaxVolumeTimes = 0;
        }
        int i = this.lessThanMaxVolumeTimes + 1;
        this.lessThanMaxVolumeTimes = i;
        if (i > 60) {
            this.lessThanMaxVolumeTimes = 0;
            this.maxAmplitude = amplitude;
        }
        if (amplitude <= this.minAmplitude) {
            this.largeThanMinVolumeTimes = 0;
        }
        int i2 = this.largeThanMinVolumeTimes + 1;
        this.largeThanMinVolumeTimes = i2;
        if (i2 > 60) {
            this.largeThanMinVolumeTimes = 0;
            this.minAmplitude = amplitude;
        }
        double Sensitivity = (((double) (100 - this.rhythmMicSensitivity)) / 100.0d) * this.sensitivityFactor;
        double troughVloume = amplitude;
        double lastTroughVolume = 0.0d;
        long lastTroughTime = 0;
        long crestTime = 0;
        long troughTime = 0;
        boolean isTrough = false;
        int crestNum = this.crestRecords.size();
        int crestTimeNum = this.crestTimeRecords.size();
        int volumeNum = this.volumeRecords.size();
        double crestVolume = amplitude;
        int volumeTimeNum = this.volumeTimeRecords.size();
        int troughNum = this.troughRecords.size();
        double volumeLowerLimit2 = volumeLowerLimit;
        int troughTimeNum = this.troughTimeRecords.size();
        long nowTimestamp2 = nowTimestamp;
        if (isCrestWithVolume(amplitude)) {
            if (crestNum > 10) {
                this.crestRecords.clear();
            }
            if (volumeNum > 0) {
                crestVolume = ((Double) this.volumeRecords.get(volumeNum - 1)).doubleValue();
                this.crestRecords.add(Double.valueOf(crestVolume));
            }
            long lastCrestTime = 0;
            if (crestTimeNum > 0) {
                lastCrestTime = ((Long) this.crestTimeRecords.get(crestTimeNum - 1)).longValue();
            }
            if (crestTimeNum > 10) {
                this.crestTimeRecords.remove(0);
            }
            if (volumeTimeNum > 0) {
                crestTime = ((Long) this.volumeTimeRecords.get(volumeTimeNum - 1)).longValue();
            }
            boolean isCrest3 = true;
            if (crestTime - lastCrestTime < 80) {
                log("距离上一个波峰时间小于80ms，过滤该波峰");
                isCrest3 = false;
            }
            if (troughNum > 0) {
                lastTroughVolume = ((Double) this.troughRecords.get(troughNum - 1)).doubleValue();
            }
            if (troughNum <= 1 || this.advanceCrest || crestVolume - lastTroughVolume >= 0.12d * volumeUpperLimit2) {
                isCrest2 = isCrest3;
            } else {
                log("波峰深度小于最近振幅最大值的12%，忽略该波峰");
                isCrest2 = false;
            }
            if (troughTimeNum > 0) {
                lastTroughTime = ((Long) this.troughTimeRecords.get(troughTimeNum - 1)).longValue();
            }
            volumeUpperLimit = volumeUpperLimit2;
            double radian = Math.atan2(crestVolume - lastTroughVolume, ((double) (crestTime - lastTroughTime)) / 1000.0d);
            int i3 = volumeTimeNum;
            double round = ((double) Math.round(radian * 100.0d)) / 100.0d;
            double d2 = radian;
            double slopeRate = (((double) Math.round(Math.toDegrees(radian) * 100.0d)) / 100.0d) / 90.0d;
            this.lastSlopeRate = slopeRate;
            double d3 = slopeRate;
            isCrest = isCrest2;
        } else {
            volumeUpperLimit = volumeUpperLimit2;
            int i4 = volumeTimeNum;
            if (isTroughWithVolume(amplitude)) {
                if (troughNum > 10) {
                    this.troughRecords.clear();
                }
                if (volumeNum > 0) {
                    troughVloume = ((Double) this.volumeRecords.get(volumeNum - 1)).doubleValue();
                    this.troughRecords.add(Double.valueOf(troughVloume));
                }
                if (troughTimeNum > 10) {
                    this.troughTimeRecords.remove(0);
                }
                if (this.volumeTimeRecords.size() > 0) {
                    ArrayList arrayList = this.volumeTimeRecords;
                    troughTime = ((Long) arrayList.get(arrayList.size() - 1)).longValue();
                }
                if (crestNum <= 0 || ((Double) this.crestRecords.get(crestNum - 1)).doubleValue() - troughVloume <= Sensitivity) {
                    isCrest = false;
                } else {
                    isTrough = true;
                    isCrest = false;
                }
            } else {
                isCrest = false;
            }
        }
        if (volumeNum > 3) {
            if (amplitude > ((Double) this.volumeRecords.get(volumeNum - 1)).doubleValue() && amplitude > ((Double) this.volumeRecords.get(volumeNum - 2)).doubleValue() && amplitude > ((Double) this.volumeRecords.get(volumeNum - 3)).doubleValue()) {
            }
            if (amplitude < ((Double) this.volumeRecords.get(volumeNum - 1)).doubleValue() && amplitude < ((Double) this.volumeRecords.get(volumeNum - 2)).doubleValue() && amplitude < ((Double) this.volumeRecords.get(volumeNum - 3)).doubleValue()) {
            }
        }
        this.volumeRecords.add(Double.valueOf(amplitude));
        long nowTimestampms = nowTimestamp2 * 1000;
        this.volumeTimeRecords.add(Long.valueOf(nowTimestampms));
        if (volumeNum > 10) {
            this.volumeRecords.remove(0);
            this.volumeTimeRecords.remove(0);
        }
        if (isCrest) {
            amplitude = crestVolume;
        }
        if (isTrough) {
            amplitude = troughVloume;
        }
        float A2 = (float) ((amplitude - volumeLowerLimit2) / (volumeUpperLimit - volumeLowerLimit2));
        if (A2 < 0.0f) {
            A2 = 0.0f;
        }
        if (A2 > 1.0f) {
            A2 = 1.0f;
        }
        if (amplitude < Sensitivity) {
            log("amplitude<Sensitivity 不符合律动规则");
            A = 0.0f;
            RhythmState = false;
        } else {
            A = A2;
            RhythmState = true;
        }
        if (!RhythmState || !isCrest) {
        } else {
            int i5 = volumeNum;
            this.crestTimeRecords.add(Long.valueOf(crestTime));
        }
        if (RhythmState && isTrough) {
            this.troughTimeRecords.add(Long.valueOf(troughTime));
        }
        int i6 = this.modeMaxDim;
        int newModeMaxDim = (int) ((90.0f * A) + 10.0f);
        if (isCrest) {
            newModeMaxDim = 100;
        }
        double maxDimLowerLimit = 0.3d;
        int maxDimming = this.rhythmMAXDimming;
        double d4 = amplitude;
        int i7 = this.rhythmMAXDimming;
        if (i7 < 30) {
            maxDimming = 30;
        }
        if (i7 > 100) {
            maxDimming = 100;
        }
        if ("Calm".equals(this.rhythmMode)) {
            maxDimLowerLimit = 0.5d;
        }
        float f = A;
        long j = nowTimestampms;
        int newModeMaxDim2 = (int) (((((((double) maxDimming) * 1.0d) - (((double) maxDimming) * maxDimLowerLimit)) * ((double) newModeMaxDim)) / 100.0d) + (((double) maxDimming) * maxDimLowerLimit));
        if (newModeMaxDim2 != this.modeMaxDim) {
            this.modeMaxDim = newModeMaxDim2;
        }
        if (!RhythmState || !isCrest) {
            D("isSendCommand  return  false, RhythmState?" + RhythmState + ",isCrest:" + isCrest);
            log("###不满足发送条件---RhythmState?" + RhythmState + ",isCrest:" + isCrest);
            return false;
        }
        D("  RhythmState=" + RhythmState + "   isCrest=" + isCrest);
        logE("###满足发送条件");
        return true;
    }

    private void D(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = a.g("Data");
            g.m("IRhyDevice:  " + msg, new Object[0]);
        }
    }

    private String formatFloatList(ArrayList list) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, PointerIconCompat.TYPE_ZOOM_IN, new Class[]{ArrayList.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(Constants.ARRAY_TYPE);
        for (int i = 0; i < list.size(); i++) {
            sb.append(String.format(Locale.US, "%.3f", new Object[]{Float.valueOf((float) ((Double) list.get(i)).doubleValue())}));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private boolean isCrestWithVolume(double d) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Double(d)}, this, changeQuickRedirect, false, PointerIconCompat.TYPE_ZOOM_OUT, new Class[]{Double.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        double amplitude = d;
        boolean isCrest = false;
        int recordNum = this.volumeRecords.size();
        if (this.volumeRecords.size() < 2) {
            return false;
        }
        double lastVolume = ((Double) this.volumeRecords.get(recordNum - 1)).doubleValue();
        double secondLastVolume = ((Double) this.volumeRecords.get(recordNum - 2)).doubleValue();
        if (lastVolume > amplitude && lastVolume > secondLastVolume) {
            log("n-2 < n-1 > n -- 波峰");
            isCrest = true;
        }
        if (!isCrest) {
            if (this.advanceCrest || recordNum <= 4) {
                return isCrest;
            }
            boolean isCrest2 = true;
            for (int checkCrestIndex = 0; checkCrestIndex < 4 - 1; checkCrestIndex++) {
                if (((Double) this.volumeRecords.get((recordNum - 1) - checkCrestIndex)).doubleValue() < ((Double) this.volumeRecords.get(((recordNum - 1) - checkCrestIndex) - 1)).doubleValue()) {
                    isCrest2 = false;
                }
                if (((Double) this.volumeRecords.get((recordNum - 1) - checkCrestIndex)).doubleValue() < (((double) (100 - this.rhythmMicSensitivity)) / 100.0d) * this.sensitivityFactor) {
                    log("预判-4个点未达到敏感值, 非波峰");
                    isCrest2 = false;
                }
            }
            if (!isCrest2) {
                return isCrest2;
            }
            log("预判-4个点都是上升, 预判为波峰");
            this.advanceCrest = true;
            return isCrest2;
        } else if (!this.advanceCrest) {
            return isCrest;
        } else {
            log("已经预判了一个波峰，忽略这个波峰");
            this.advanceCrest = false;
            return false;
        }
    }

    private boolean isTroughWithVolume(double amplitude) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Double(amplitude)}, this, changeQuickRedirect, false, PointerIconCompat.TYPE_GRAB, new Class[]{Double.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int volumeNum = this.volumeRecords.size();
        if (volumeNum < 2) {
            return false;
        }
        double lastVolume = ((Double) this.volumeRecords.get(volumeNum - 1)).doubleValue();
        double secondLastVolume = ((Double) this.volumeRecords.get(volumeNum - 2)).doubleValue();
        if (lastVolume >= amplitude || lastVolume >= secondLastVolume) {
            return false;
        }
        return true;
    }

    public JSONArray getSyncRhythmEffects() {
        int[] fadingArray;
        double[] dimRateList;
        IRhyDevice iRhyDevice;
        int i;
        int i2;
        double[] dArr;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, PointerIconCompat.TYPE_GRABBING, new Class[0], JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        JSONArray newEffectArray = new JSONArray();
        try {
            int i3 = this.rhythmTap;
            if (i3 == 1) {
                try {
                    dimRateList = new double[]{0.7d, 1.0d, 0.4d};
                    fadingArray = new int[]{50, 200, 400};
                } catch (Exception e) {
                    e = e;
                    e.getMessage();
                    return newEffectArray;
                }
            } else if (i3 == 2) {
                dimRateList = new double[]{0.3d, 1.0d, 0.2d};
                fadingArray = new int[]{50, 100, 200};
            } else if (i3 == 3) {
                dimRateList = new double[]{0.3d, 1.0d, 0.15d};
                fadingArray = new int[]{30, 80, 150};
            } else if (i3 == 4) {
                dimRateList = new double[]{0.3d, 1.0d, 0.1d};
                fadingArray = new int[]{30, 50, 100};
            } else {
                dimRateList = new double[]{0.3d, 1.0d, 0.1d};
                fadingArray = new int[]{50, 100, 200};
            }
            double d = dimRateList[0];
            int i4 = this.rhythmMAXDimming;
            double[] dimList = {d * ((double) i4), dimRateList[1] * ((double) i4), dimRateList[2] * ((double) i4)};
            if (RhythmThemeType.rhythmThemeTypeCustomize.equals(this.rhythmThemeType)) {
                try {
                    int i5 = this.rhythmFading;
                    fadingArray = new int[]{(int) (((double) i5) * 0.25d), i5, (int) (((double) i5) * 1.5d)};
                    int i6 = this.rhythmMAXDimming;
                    i = this.rhythmMINDimming;
                    i2 = i6;
                    dArr = new double[3];
                    dArr[0] = (((double) (i6 - i)) * 0.3d) + ((double) i);
                    iRhyDevice = this;
                } catch (Exception e2) {
                    e = e2;
                    e.getMessage();
                    return newEffectArray;
                }
                try {
                    dArr[1] = (double) i2;
                    dArr[2] = (double) i;
                    dimList = dArr;
                } catch (Exception e3) {
                    e = e3;
                    IRhyDevice iRhyDevice2 = iRhyDevice;
                    e.getMessage();
                    return newEffectArray;
                }
            } else {
                iRhyDevice = this;
            }
            try {
                JSONObject breakOffEffect = new JSONObject();
                breakOffEffect.put(TypedValues.Custom.S_COLOR, iRhyDevice.getNextColorV2());
                breakOffEffect.put("dimming", dimList[0]);
                breakOffEffect.put("dimmingRate", dimRateList[0]);
                breakOffEffect.put("fading", fadingArray[0]);
                JSONObject upEffect = new JSONObject();
                upEffect.put(TypedValues.Custom.S_COLOR, iRhyDevice.getNextColorV2());
                upEffect.put("dimming", dimList[1]);
                upEffect.put("dimmingRate", dimRateList[1]);
                upEffect.put("fading", fadingArray[1]);
                JSONObject downEffect = new JSONObject();
                downEffect.put(TypedValues.Custom.S_COLOR, iRhyDevice.getNextColorV2());
                downEffect.put("dimming", dimList[2]);
                downEffect.put("dimmingRate", dimRateList[2]);
                downEffect.put("fading", fadingArray[2]);
                long nowTime = System.currentTimeMillis();
                IRhyDevice iRhyDevice3 = iRhyDevice;
                try {
                    if ((nowTime - iRhyDevice3.lastGetEffectTime) * 1000 < ((long) iRhyDevice3.lastGetEffectFading)) {
                        newEffectArray.put((Object) breakOffEffect);
                        newEffectArray.put((Object) upEffect);
                        newEffectArray.put((Object) downEffect);
                        iRhyDevice3.lastGetEffectFading = fadingArray[0] + fadingArray[1] + fadingArray[2];
                    } else {
                        newEffectArray.put((Object) upEffect);
                        newEffectArray.put((Object) downEffect);
                        iRhyDevice3.lastGetEffectFading = fadingArray[1] + fadingArray[2];
                    }
                    iRhyDevice3.lastGetEffectTime = nowTime;
                } catch (Exception e4) {
                    e = e4;
                    e.getMessage();
                    return newEffectArray;
                }
            } catch (Exception e5) {
                e = e5;
                IRhyDevice iRhyDevice4 = iRhyDevice;
                e.getMessage();
                return newEffectArray;
            }
        } catch (Exception e6) {
            e = e6;
            e.getMessage();
            return newEffectArray;
        }
        return newEffectArray;
    }

    private int getNextColorV2() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1022, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        long nowTimestamp = System.currentTimeMillis();
        if (nowTimestamp - this.lastColorChangeTime > ((long) changeColorSpeed())) {
            this.lastColorChangeTime = nowTimestamp;
            int length = this.rhythmColors.length;
            int i = this.colorIndex;
            if (length <= i) {
                this.colorIndex = 0;
            } else {
                this.colorIndex = i + 1;
            }
        }
        return this.rhythmColors[this.colorIndex];
    }

    private int[] getNextColorWithLightId(String lightId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{lightId}, this, changeQuickRedirect, false, 1023, new Class[]{String.class}, int[].class);
        if (proxy.isSupported) {
            return (int[]) proxy.result;
        }
        String colorIndexKey = String.format(Locale.US, "lightId-%s", new Object[]{lightId});
        Integer colorIndexValue = this.colorIndexMap.get(colorIndexKey);
        int currentIndex = colorIndexValue != null ? colorIndexValue.intValue() : 0;
        if (colorIndexValue == null && this.colorIndexMap.size() > 0 && Build.VERSION.SDK_INT >= 24) {
            currentIndex = a.a(this.colorIndexMap.size() * 2, this.rhythmColors.length);
        }
        int[] iArr = this.rhythmColors;
        if (iArr.length <= currentIndex) {
            currentIndex = 0;
        }
        int color1 = iArr[currentIndex];
        int color2Index = currentIndex + 1;
        if (iArr.length <= color2Index) {
            color2Index = 0;
        }
        int color2 = iArr[color2Index];
        long nowTimestamp = System.currentTimeMillis();
        Long lastColorChangeTimeValue = this.lastColorChangeTimeMap.get(colorIndexKey);
        int color12 = color1;
        if (nowTimestamp - (lastColorChangeTimeValue != null ? lastColorChangeTimeValue.longValue() : 0) > ((long) changeColorSpeed())) {
            int currentIndex2 = currentIndex + 2;
            int[] iArr2 = this.rhythmColors;
            if (iArr2.length <= currentIndex2) {
                currentIndex2 -= iArr2.length;
            }
            this.colorIndexMap.put(colorIndexKey, Integer.valueOf(currentIndex2));
            this.lastColorChangeTimeMap.put(colorIndexKey, Long.valueOf(nowTimestamp));
        }
        return new int[]{color12, color2};
    }

    public JSONArray getAsyncRhythmEffectsWithLightId(String str) {
        int[] fadingArray;
        double[] dimRateList;
        double[] dimList;
        IRhyDevice iRhyDevice;
        String lightId;
        double[] dArr;
        int i;
        int i2;
        double[] dArr2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1024, new Class[]{String.class}, JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        String lightId2 = str;
        JSONArray newEffectArray = new JSONArray();
        try {
            int i3 = this.rhythmTap;
            if (i3 == 1) {
                try {
                    dimRateList = new double[]{1.0d, 0.5d};
                    fadingArray = new int[]{500, 500};
                } catch (Exception e) {
                    e = e;
                    String str2 = lightId2;
                    a.b g = a.g("Rhythm");
                    g.c("getAsyncRhythmEffectsWithLightId 异常:" + e.toString(), new Object[0]);
                    return newEffectArray;
                }
            } else if (i3 == 2) {
                dimRateList = new double[]{1.0d, 0.3d};
                fadingArray = new int[]{200, 200};
            } else if (i3 == 3) {
                dimRateList = new double[]{1.0d, 0.5d};
                fadingArray = new int[]{50, 50};
            } else if (i3 == 4) {
                dimRateList = new double[]{1.0d, 0.0d};
                fadingArray = new int[]{50, 50};
            } else if (i3 == 6) {
                int f = f.c().b();
                double[] dArr3 = {1.0d, 0.3d};
                fadingArray = new int[]{f, f};
                dimRateList = dArr3;
            } else {
                dimRateList = new double[]{1.0d, 0.5d};
                fadingArray = new int[]{200, 200};
            }
            double[] dArr4 = new double[2];
            double d = dimRateList[0];
            int i4 = this.rhythmMAXDimming;
            try {
                dArr4[0] = d * ((double) i4);
                dArr4[1] = dimRateList[1] * ((double) i4);
                dimList = dArr4;
            } catch (Exception e2) {
                e = e2;
                String str3 = lightId2;
                a.b g2 = a.g("Rhythm");
                g2.c("getAsyncRhythmEffectsWithLightId 异常:" + e.toString(), new Object[0]);
                return newEffectArray;
            }
            try {
                if (RhythmThemeType.rhythmThemeTypeCustomize.equals(this.rhythmThemeType)) {
                    try {
                        int i5 = this.rhythmFading;
                        fadingArray = new int[]{i5, i5};
                        dArr = new double[2];
                        i = this.rhythmMAXDimming;
                        dArr[0] = ((double) i) / 100.0d;
                        i2 = this.rhythmMINDimming;
                        lightId = lightId2;
                    } catch (Exception e3) {
                        e = e3;
                        String str4 = lightId2;
                        a.b g22 = a.g("Rhythm");
                        g22.c("getAsyncRhythmEffectsWithLightId 异常:" + e.toString(), new Object[0]);
                        return newEffectArray;
                    }
                    try {
                        dArr[1] = ((double) i2) / 100.0d;
                        dimRateList = dArr;
                        dArr2 = new double[2];
                        iRhyDevice = this;
                    } catch (Exception e4) {
                        e = e4;
                        String str5 = lightId;
                        a.b g222 = a.g("Rhythm");
                        g222.c("getAsyncRhythmEffectsWithLightId 异常:" + e.toString(), new Object[0]);
                        return newEffectArray;
                    }
                    try {
                        dArr2[0] = (double) i;
                        dArr2[1] = (double) i2;
                        dimList = dArr2;
                    } catch (Exception e5) {
                        e = e5;
                        String str6 = lightId;
                        a.b g2222 = a.g("Rhythm");
                        g2222.c("getAsyncRhythmEffectsWithLightId 异常:" + e.toString(), new Object[0]);
                        return newEffectArray;
                    }
                } else {
                    lightId = lightId2;
                    iRhyDevice = this;
                }
                IRhyDevice iRhyDevice2 = iRhyDevice;
                try {
                    int[] colors = iRhyDevice2.getNextColorWithLightId(lightId);
                    if (iRhyDevice2.rhythmTap == 5) {
                        dimRateList = new double[]{1.0d, 1.0d};
                        double[] dArr5 = new double[2];
                        int i6 = iRhyDevice2.rhythmMAXDimming;
                        IRhyDevice iRhyDevice3 = iRhyDevice2;
                        try {
                            dArr5[0] = (double) i6;
                            dArr5[1] = (double) i6;
                            dimList = dArr5;
                        } catch (Exception e6) {
                            e = e6;
                            a.b g22222 = a.g("Rhythm");
                            g22222.c("getAsyncRhythmEffectsWithLightId 异常:" + e.toString(), new Object[0]);
                            return newEffectArray;
                        }
                    } else {
                        IRhyDevice iRhyDevice4 = iRhyDevice2;
                    }
                    JSONObject upEffect = new JSONObject();
                    upEffect.put(TypedValues.Custom.S_COLOR, colors[0]);
                    upEffect.put("dimming", dimList[0]);
                    upEffect.put("dimmingRate", dimRateList[0]);
                    upEffect.put("fading", fadingArray[0]);
                    JSONObject downEffect = new JSONObject();
                    downEffect.put(TypedValues.Custom.S_COLOR, colors[1]);
                    downEffect.put("dimming", dimList[1]);
                    downEffect.put("dimmingRate", dimRateList[1]);
                    downEffect.put("fading", fadingArray[1]);
                    newEffectArray.put((Object) upEffect);
                    newEffectArray.put((Object) downEffect);
                } catch (Exception e7) {
                    e = e7;
                    IRhyDevice iRhyDevice5 = iRhyDevice2;
                    a.b g222222 = a.g("Rhythm");
                    g222222.c("getAsyncRhythmEffectsWithLightId 异常:" + e.toString(), new Object[0]);
                    return newEffectArray;
                }
            } catch (Exception e8) {
                e = e8;
                String str7 = lightId2;
                a.b g2222222 = a.g("Rhythm");
                g2222222.c("getAsyncRhythmEffectsWithLightId 异常:" + e.toString(), new Object[0]);
                return newEffectArray;
            }
        } catch (Exception e9) {
            e = e9;
            String str8 = lightId2;
            a.b g22222222 = a.g("Rhythm");
            g22222222.c("getAsyncRhythmEffectsWithLightId 异常:" + e.toString(), new Object[0]);
            return newEffectArray;
        }
        return newEffectArray;
    }

    public JSONObject getModel2NextColor() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1025, new Class[0], JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            long nowTimestamp = System.currentTimeMillis();
            if (nowTimestamp - this.lastColorChangeTime > ((long) changeColorSpeed2())) {
                this.lastColorChangeTime = nowTimestamp;
                int[] iArr = this.rhythmColors;
                int length = iArr.length;
                int i = this.model2ColorIndex;
                if (length <= i) {
                    this.model2ColorIndex = 0;
                } else {
                    int i2 = i + 1;
                    this.model2ColorIndex = i2;
                    if (i2 >= iArr.length) {
                        this.model2ColorIndex = 0;
                    }
                }
            }
            int color = this.rhythmColors[this.model2ColorIndex];
            boolean isChange = false;
            if (this.lastModel2Color != color) {
                this.lastModel2Color = color;
                isChange = true;
            }
            jsonObject.put("isChange", isChange);
            jsonObject.put(TypedValues.Custom.S_COLOR, color);
        } catch (Exception e) {
            e.getMessage();
        }
        return jsonObject;
    }

    public JSONArray getModel2SyncRhythmEffects(int i) {
        int[] fadingArray;
        double[] dimRateList;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_EVENTCONFIG_REQ, new Class[]{Integer.TYPE}, JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        int color = i;
        JSONArray newEffectArray = new JSONArray();
        try {
            int i2 = this.rhythmTap;
            if (i2 == 1) {
                try {
                    dimRateList = new double[]{1.0d, 0.3d};
                    fadingArray = new int[]{500, 500};
                } catch (Exception e) {
                    e = e;
                    e.getMessage();
                    return newEffectArray;
                }
            } else if (i2 == 2) {
                dimRateList = new double[]{1.0d, 0.3d};
                fadingArray = new int[]{200, 200};
            } else if (i2 == 3) {
                dimRateList = new double[]{1.0d, 0.5d};
                fadingArray = new int[]{50, 50};
            } else if (i2 == 4) {
                dimRateList = new double[]{1.0d, 0.0d};
                fadingArray = new int[]{50, 50};
            } else if (i2 == 6) {
                int f = f.c().b();
                double[] dArr = {1.0d, 0.3d};
                fadingArray = new int[]{f, f};
                dimRateList = dArr;
            } else {
                dimRateList = new double[]{1.0d, 0.5d};
                fadingArray = new int[]{200, 200};
            }
            double[] dArr2 = new double[2];
            double d = dimRateList[0];
            int i3 = this.rhythmMAXDimming;
            try {
                dArr2[0] = d * ((double) i3);
                dArr2[1] = dimRateList[1] * ((double) i3);
                double[] dimList = dArr2;
                try {
                    if (RhythmThemeType.rhythmThemeTypeCustomize.equals(this.rhythmThemeType)) {
                        int i4 = this.rhythmFading;
                        fadingArray = new int[]{i4, i4};
                        int i5 = this.rhythmMAXDimming;
                        int i6 = this.rhythmMINDimming;
                        dimRateList = new double[]{((double) i5) / 100.0d, ((double) i6) / 100.0d};
                        dimList = new double[]{(double) i5, (double) i6};
                    }
                    JSONObject upEffect = new JSONObject();
                    upEffect.put(TypedValues.Custom.S_COLOR, color);
                    upEffect.put("dimming", dimList[0]);
                    upEffect.put("dimmingRate", dimRateList[0]);
                    upEffect.put("fading", fadingArray[0]);
                    JSONObject downEffect = new JSONObject();
                    downEffect.put(TypedValues.Custom.S_COLOR, color);
                    downEffect.put("dimming", dimList[1]);
                    downEffect.put("dimmingRate", dimRateList[1]);
                    downEffect.put("fading", fadingArray[1]);
                    newEffectArray.put((Object) upEffect);
                    newEffectArray.put((Object) downEffect);
                } catch (Exception e2) {
                    e = e2;
                    e.getMessage();
                    return newEffectArray;
                }
            } catch (Exception e3) {
                e = e3;
                e.getMessage();
                return newEffectArray;
            }
        } catch (Exception e4) {
            e = e4;
            e.getMessage();
            return newEffectArray;
        }
        return newEffectArray;
    }

    public JSONArray getOnlyChangeColorSyncRhythmEffects(int fading) {
        int color;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(fading)}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_EVENTCONFIG_RESP, new Class[]{Integer.TYPE}, JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        JSONArray effectArray2 = new JSONArray();
        try {
            int[] iArr = this.rhythmColors;
            if (iArr.length <= this.model3ColorIndex) {
                this.model3ColorIndex = 0;
            }
            if (iArr.length == 0) {
                color = Color.parseColor("#ff0000");
            } else {
                color = iArr[this.model3ColorIndex];
            }
            this.model3ColorIndex++;
            JSONObject effect = new JSONObject();
            effect.put(TypedValues.Custom.S_COLOR, color);
            effect.put("dimming", this.rhythmMAXDimming);
            effect.put("dimmingRate", 1);
            effect.put("fading", fading);
            effectArray2.put((Object) effect);
        } catch (Exception e) {
            e.getMessage();
        }
        return effectArray2;
    }

    public void log(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 1028, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g("IRhyDevice-test").h(msg, new Object[0]);
        }
    }

    public void logE(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 1029, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.g("IRhyDevice-test").c(msg, new Object[0]);
        }
    }
}
