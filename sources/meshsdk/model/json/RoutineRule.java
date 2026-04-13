package meshsdk.model.json;

import android.graphics.Color;
import androidx.core.graphics.ColorUtils;
import com.telink.ble.mesh.entity.Scheduler;
import meshsdk.SIGMesh;
import meshsdk.ctrl.CmdCtrl;
import meshsdk.model.Scene;

public class RoutineRule {
    public static final String THEN_TYPE_DEVICE = "device";
    public static final String THEN_TYPE_SCENE = "scene";
    public static final String TRIGGER_TYPE_SUNRISE = "sunrise";
    public static final String TRIGGER_TYPE_SUNSET = "sunset";
    public static final String TRIGGER_TYPE_TIMER = "timer";
    public int enable = 0;
    public ExecuteAction endAction;
    public ExecuteTime endTime;
    public int intervalTime;
    public float latitude;
    public float longitude;
    public String mac;
    public int protocolVersion = 0;
    public int smartId;
    public ExecuteAction startAction;
    public ExecuteTime startTime;
    public String thenType;
    public int timeZone;
    public String triggerType;

    public static class ExecuteTime {
        public int day;
        public int hour;
        public int minute;
        public int month;
        public int repeat;
        public int second;
        public int year;
    }

    public RoutineRule(String mac2) {
        this.mac = mac2;
        this.startTime = new ExecuteTime();
        this.startAction = new ExecuteAction();
        this.endTime = new ExecuteTime();
        this.endAction = new ExecuteAction();
    }

    public static class ExecuteAction {
        private final int ACT_TYPE_CCT = 4;
        private final int ACT_TYPE_DIM = 3;
        private final int ACT_TYPE_DIM_CCT = 5;
        private final int ACT_TYPE_DIM_HSL = 7;
        private final int ACT_TYPE_EFFECT = 8;
        private final int ACT_TYPE_HSL = 6;
        private final int ACT_TYPE_OFF = 0;
        private final int ACT_TYPE_ON = 1;
        private final int ACT_TYPE_SCENE = 2;
        private final int ACT_TYPE_UNKNOWN = 255;
        private final int ACT_TYPE_WORK_MODE = 9;
        public int CCT = -1;
        public int Dimming = -1;
        public float HSLHue = -1.0f;
        public float HSLLightness = -1.0f;
        public float HSLSaturation = -1.0f;
        private final int NONE = -1;
        public int OnOff = -1;
        public int b;
        public int detectionMode;
        public int effectId;
        public int fading;
        public int g;
        public String mode;
        public int r;
        public int sceneId = -1;
        public int soundWaveType;

        public int getMeshSceneId() {
            Scene scene = SIGMesh.getInstance().getMeshInfo().getSceneByCloudSceneId(this.sceneId);
            if (scene == null) {
                return -1;
            }
            return scene.id;
        }

        public int getAction() {
            boolean hasRGB = this.soundWaveType == 1;
            if (this.sceneId != -1) {
                return 2;
            }
            if (this.Dimming != -1) {
                if (this.CCT != -1) {
                    return 5;
                }
                if (hasRGB) {
                    return 7;
                }
                return 3;
            } else if (this.CCT != -1) {
                return 4;
            } else {
                if (hasRGB) {
                    return 6;
                }
                int i = this.OnOff;
                if (i > 0) {
                    return 1;
                }
                if (i == 0) {
                    return 0;
                }
                return 255;
            }
        }

        public int getActionType() {
            if (this.detectionMode > 1) {
                return 9;
            }
            if (this.effectId != 0) {
                return 8;
            }
            boolean hasHsl = (this.HSLHue == -1.0f || this.HSLSaturation == -1.0f || this.HSLLightness == -1.0f) ? false : true;
            if (this.sceneId != -1) {
                return 2;
            }
            int i = this.OnOff;
            if (i == 0) {
                return 0;
            }
            if (this.Dimming != -1) {
                if (this.CCT != -1) {
                    return 5;
                }
                if (hasHsl) {
                    return 7;
                }
                return 3;
            } else if (this.CCT != -1) {
                return 4;
            } else {
                if (hasHsl) {
                    return 6;
                }
                if (i > 0) {
                    return 1;
                }
                return 255;
            }
        }

        public static int getActionParamLength(int act) {
            switch (act) {
                case 0:
                case 1:
                    return 0;
                case 2:
                case 3:
                case 4:
                    return 2;
                case 5:
                    return 4;
                case 6:
                    return 6;
                case 7:
                    return 8;
                case 8:
                    return 2;
                case 9:
                    return 1;
                default:
                    return 0;
            }
        }

        public byte[] toMeshActionParam() {
            int actionType = getActionType();
            byte[] sActionParam = new byte[getActionParamLength(actionType)];
            switch (actionType) {
                case 2:
                    return CmdCtrl.int2ByteArr((long) getMeshSceneId(), 2);
                case 3:
                    return CmdCtrl.int2ByteArr((long) ((int) ((((float) this.Dimming) / 100.0f) * 65535.0f)), 2);
                case 4:
                    return CmdCtrl.int2ByteArr((long) this.CCT, 2);
                case 5:
                    byte[] light = CmdCtrl.int2ByteArr((long) ((int) ((((float) this.Dimming) / 100.0f) * 65535.0f)), 2);
                    byte[] cct = CmdCtrl.int2ByteArr((long) this.CCT, 2);
                    System.arraycopy(light, 0, sActionParam, 0, light.length);
                    System.arraycopy(cct, 0, sActionParam, 2, cct.length);
                    return sActionParam;
                case 6:
                    int h = (int) ((this.HSLHue / 360.0f) * 65535.0f);
                    int l = (int) ((this.HSLLightness / 100.0f) * 65535.0f);
                    byte[] harr = CmdCtrl.int2ByteArr((long) h, 2);
                    byte[] sarr = CmdCtrl.int2ByteArr((long) ((int) ((this.HSLSaturation / 100.0f) * 65535.0f)), 2);
                    byte[] larr = CmdCtrl.int2ByteArr((long) l, 2);
                    System.arraycopy(harr, 0, sActionParam, 0, harr.length);
                    System.arraycopy(sarr, 0, sActionParam, 2, sarr.length);
                    System.arraycopy(larr, 0, sActionParam, 4, larr.length);
                    return sActionParam;
                case 7:
                    int h2 = (int) ((this.HSLHue / 360.0f) * 65535.0f);
                    byte[] dimArr = CmdCtrl.int2ByteArr((long) ((int) ((((float) this.Dimming) / 100.0f) * 65535.0f)), 2);
                    byte[] harr2 = CmdCtrl.int2ByteArr((long) h2, 2);
                    byte[] sarr2 = CmdCtrl.int2ByteArr((long) ((int) ((this.HSLSaturation / 100.0f) * 65535.0f)), 2);
                    byte[] larr2 = CmdCtrl.int2ByteArr((long) ((int) ((this.HSLLightness / 100.0f) * 65535.0f)), 2);
                    System.arraycopy(dimArr, 0, sActionParam, 0, dimArr.length);
                    System.arraycopy(harr2, 0, sActionParam, 2, harr2.length);
                    System.arraycopy(sarr2, 0, sActionParam, 4, sarr2.length);
                    System.arraycopy(larr2, 0, sActionParam, 6, larr2.length);
                    return sActionParam;
                case 8:
                    byte[] effectByte = CmdCtrl.int2ByteArr((long) this.effectId, 2);
                    System.arraycopy(effectByte, 0, sActionParam, 0, effectByte.length);
                    return sActionParam;
                case 9:
                    byte[] workModelBytes = {(byte) this.detectionMode};
                    System.arraycopy(workModelBytes, 0, sActionParam, 0, workModelBytes.length);
                    return sActionParam;
                default:
                    return sActionParam;
            }
        }

        public int getDetectionMode() {
            return this.detectionMode;
        }

        public void setDetectionMode(int detectionMode2) {
            this.detectionMode = detectionMode2;
        }

        public byte[] toMeshActionParamV2() {
            return new byte[]{(byte) this.Dimming, (byte) this.r, (byte) this.g, (byte) this.b};
        }

        public byte[] toMeshActionParamV2Dim() {
            return new byte[]{(byte) this.Dimming};
        }

        public static String toHexEncoding(int color) {
            String str;
            String str2;
            String B;
            StringBuffer sb = new StringBuffer();
            String R = Integer.toHexString(Color.red(color));
            String G = Integer.toHexString(Color.green(color));
            String B2 = Integer.toHexString(Color.blue(color));
            if (R.length() == 1) {
                str = "0" + R;
            } else {
                str = R;
            }
            String R2 = str;
            if (G.length() == 1) {
                str2 = "0" + G;
            } else {
                str2 = G;
            }
            String G2 = str2;
            if (B2.length() == 1) {
                B = "0" + B2;
            } else {
                B = B2;
            }
            sb.append("#");
            sb.append(R2);
            sb.append(G2);
            sb.append(B);
            return sb.toString();
        }

        public byte getSimpleActionType() {
            return (byte) (getAction() | 128);
        }

        public int hsl2rgb() {
            return ColorUtils.HSLToColor(new float[]{this.HSLHue, this.HSLSaturation, this.HSLLightness});
        }

        public byte getFlag() {
            if ("Party".equals(this.mode)) {
                return 17;
            }
            if ("Dynamic".equals(this.mode)) {
                return 9;
            }
            if ("Calm".equals(this.mode)) {
                return 1;
            }
            return 17;
        }
    }

    public static RoutineRule Schedule2Routine(Scheduler scheduler) {
        RoutineRule rule = new RoutineRule("");
        rule.smartId = scheduler.smartId;
        return rule;
    }

    public byte[] convertTimeAndAction(int actionType1, int n, ExecuteTime startTime2, ExecuteAction startAction2) {
        byte[] valueArr = new byte[(n + 13)];
        byte[] sYear = CmdCtrl.int2ByteArr((long) startTime2.year, 2);
        valueArr[0] = sYear[0];
        valueArr[1] = sYear[1];
        byte[] sMonth = CmdCtrl.int2ByteArr((long) startTime2.month, 2);
        valueArr[2] = sMonth[0];
        valueArr[3] = sMonth[1];
        valueArr[4] = (byte) startTime2.day;
        valueArr[5] = (byte) startTime2.hour;
        valueArr[6] = (byte) startTime2.minute;
        valueArr[7] = (byte) startTime2.repeat;
        valueArr[8] = (byte) actionType1;
        byte[] sFading = CmdCtrl.int2ByteArr((long) startAction2.fading, 4);
        System.arraycopy(sFading, 0, valueArr, 9, sFading.length);
        byte[] sActionParam = startAction2.toMeshActionParam();
        System.arraycopy(sActionParam, 0, valueArr, 13, sActionParam.length);
        return valueArr;
    }

    public byte[] convertTimeAndActionV2(int actionType1, int n, ExecuteTime startTime2, ExecuteAction startAction2, boolean hasRepeat) {
        if (hasRepeat) {
            byte[] valueArr = new byte[(n + 4)];
            valueArr[0] = (byte) startTime2.repeat;
            valueArr[1] = (byte) startTime2.hour;
            valueArr[2] = (byte) startTime2.minute;
            valueArr[3] = (byte) actionType1;
            byte[] sActionParam = startAction2.toMeshActionParam();
            System.arraycopy(sActionParam, 0, valueArr, 4, sActionParam.length);
            return valueArr;
        }
        byte[] valueArr2 = new byte[(n + 3)];
        valueArr2[0] = (byte) startTime2.hour;
        valueArr2[1] = (byte) startTime2.minute;
        valueArr2[2] = (byte) actionType1;
        byte[] sActionParam2 = startAction2.toMeshActionParam();
        System.arraycopy(sActionParam2, 0, valueArr2, 3, sActionParam2.length);
        return valueArr2;
    }
}
