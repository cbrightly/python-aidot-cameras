package meshsdk.model.json;

import com.google.gson.Gson;
import com.leedarson.base.utils.e;
import java.util.ArrayList;
import java.util.List;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.ctrl.CmdCtrl;
import meshsdk.model.GroupInfo;
import meshsdk.util.LDSMeshUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class DetectMode {
    public int duration;
    public int ecoEndHour;
    public int ecoEndMinute;
    public int ecoSlightDimming;
    public int ecoStartHour;
    public int ecoStartMinute;
    public int ecoStatus;
    public List<Integer> ecoTimeSpan = new ArrayList();
    public int ecoTriggerDimming;
    public int endHour;
    public int endMinute;
    public int luminanceEnable;
    public String mac;
    public int mode;
    public int needChangeMode;
    public int pirSensitivity;
    public int repeat;
    public int repeatEnable;
    public int securityAlarmEnable;
    public int slightDimming;
    public int slightStatus;
    public int startHour;
    public int startMinute;
    public List<Integer> timeSpan = new ArrayList();
    public int triggerDimming;
    public TriggerMotion triggerMotion;

    public DetectMode(int mode2) {
        this.mode = mode2;
        this.triggerMotion = new TriggerMotion(0);
    }

    public DetectMode() {
    }

    public void parseModeParams(byte[] valueArr) {
        byte[] bArr = new byte[getModeParamsLen()];
        int i = this.mode;
        if (i == 1) {
            this.duration = CmdCtrl.getBigHex(new byte[]{valueArr[1], valueArr[2]}, 2) * 60;
            this.slightDimming = valueArr[3];
        } else if (i == 2) {
            this.duration = CmdCtrl.getBigHex(new byte[]{valueArr[1], valueArr[2]}, 2);
            this.slightDimming = valueArr[3];
            this.triggerMotion = new TriggerMotion(CmdCtrl.getBigHex(new byte[]{valueArr[4], valueArr[5]}, 2));
        } else if (i == 3) {
            this.triggerDimming = valueArr[1];
            this.duration = CmdCtrl.getBigHex(new byte[]{valueArr[2], valueArr[3]}, 2);
            this.slightStatus = valueArr[4];
            this.slightDimming = valueArr[5];
            this.triggerMotion = new TriggerMotion(CmdCtrl.getBigHex(new byte[]{valueArr[6], valueArr[7]}, 2));
            this.repeat = valueArr[8];
            byte b = valueArr[9];
            this.startHour = b;
            byte b2 = valueArr[10];
            this.startMinute = b2;
            byte b3 = valueArr[11];
            this.endHour = b3;
            byte b4 = valueArr[12];
            this.endMinute = b4;
            setTimespan(b, b2, b3, b4);
            this.ecoStatus = valueArr[13];
            this.ecoTriggerDimming = valueArr[14];
            this.ecoSlightDimming = valueArr[15];
            byte b5 = valueArr[16];
            this.ecoStartHour = b5;
            byte b6 = valueArr[17];
            this.ecoStartMinute = b6;
            byte b7 = valueArr[18];
            this.ecoEndHour = b7;
            byte b8 = valueArr[19];
            this.ecoEndMinute = b8;
            setEcoTimespan(b5, b6, b7, b8);
        } else if (i == 4) {
            this.triggerDimming = valueArr[1];
            this.repeat = valueArr[2];
            byte b9 = valueArr[3];
            this.startHour = b9;
            byte b10 = valueArr[4];
            this.startMinute = b10;
            byte b11 = valueArr[5];
            this.endHour = b11;
            byte b12 = valueArr[6];
            this.endMinute = b12;
            setTimespan(b9, b10, b11, b12);
        } else if (i == 5) {
            this.triggerDimming = valueArr[1];
            this.slightDimming = valueArr[2];
            byte b13 = valueArr[3];
            this.startHour = b13;
            byte b14 = valueArr[4];
            this.startMinute = b14;
            byte b15 = valueArr[5];
            this.endHour = b15;
            byte b16 = valueArr[6];
            this.endMinute = b16;
            setTimespan(b13, b14, b15, b16);
        } else if (i == 6) {
            this.repeat = valueArr[1];
            byte b17 = valueArr[2];
            this.startHour = b17;
            byte b18 = valueArr[3];
            this.startMinute = b18;
            byte b19 = valueArr[4];
            this.endHour = b19;
            byte b20 = valueArr[5];
            this.endMinute = b20;
            setTimespan(b17, b18, b19, b20);
        } else if (i == 7) {
            this.duration = CmdCtrl.getBigHex(new byte[]{valueArr[1], valueArr[2]}, 2) * 60;
            this.slightStatus = valueArr[3];
            this.slightDimming = valueArr[4];
            this.triggerDimming = valueArr[5];
        } else if (i == 8) {
            this.duration = CmdCtrl.getBigHex(new byte[]{valueArr[1], valueArr[2]}, 2);
            this.slightStatus = valueArr[3];
            this.slightDimming = valueArr[4];
            this.triggerMotion = new TriggerMotion(CmdCtrl.getBigHex(new byte[]{valueArr[5], valueArr[6]}, 2));
            this.triggerDimming = valueArr[7];
            this.pirSensitivity = valueArr[8];
        } else if (i == 9) {
            this.triggerDimming = valueArr[1];
            this.duration = CmdCtrl.getBigHex(new byte[]{valueArr[2], valueArr[3]}, 2);
            this.slightDimming = valueArr[4];
            this.triggerMotion = new TriggerMotion(CmdCtrl.getBigHex(new byte[]{valueArr[5], valueArr[6]}, 2));
            this.pirSensitivity = valueArr[7];
            this.securityAlarmEnable = valueArr[8];
            this.repeatEnable = valueArr[9];
            this.repeat = valueArr[10];
            byte b21 = valueArr[11];
            this.startHour = b21;
            byte b22 = valueArr[12];
            this.startMinute = b22;
            byte b23 = valueArr[13];
            this.endHour = b23;
            byte b24 = valueArr[14];
            this.endMinute = b24;
            setTimespan(b21, b22, b23, b24);
        } else if (i == 10) {
            this.triggerDimming = valueArr[1];
            this.repeatEnable = valueArr[2];
            this.repeat = valueArr[3];
            byte b25 = valueArr[4];
            this.startHour = b25;
            byte b26 = valueArr[5];
            this.startMinute = b26;
            byte b27 = valueArr[6];
            this.endHour = b27;
            byte b28 = valueArr[7];
            this.endMinute = b28;
            setTimespan(b25, b26, b27, b28);
        } else if (i == 11) {
            MeshLog.i("getDetectionMode data:" + e.a(valueArr));
            this.triggerDimming = valueArr[1];
            this.repeat = valueArr[2];
            this.startHour = valueArr[3];
            this.startMinute = valueArr[4];
            this.endHour = valueArr[5];
            this.endMinute = valueArr[6];
            MeshLog.e("parse getDetectionMode:" + this.startHour + "," + this.startMinute + "," + this.endHour + "," + this.endMinute);
            setTimespan(this.startHour, this.startMinute, this.endHour, this.endMinute);
        } else if (i == 12) {
            this.triggerDimming = valueArr[1];
            this.duration = CmdCtrl.getBigHex(new byte[]{valueArr[2], valueArr[3]}, 2);
            this.slightDimming = valueArr[4];
            this.triggerMotion = new TriggerMotion(CmdCtrl.getBigHex(new byte[]{valueArr[5], valueArr[6]}, 2));
            this.pirSensitivity = valueArr[7];
            this.repeatEnable = valueArr[8];
            this.repeat = valueArr[9];
            byte b29 = valueArr[10];
            this.startHour = b29;
            byte b30 = valueArr[11];
            this.startMinute = b30;
            byte b31 = valueArr[12];
            this.endHour = b31;
            byte b32 = valueArr[13];
            this.endMinute = b32;
            setTimespan(b29, b30, b31, b32);
        } else if (i == 13) {
            this.triggerDimming = valueArr[1];
            this.luminanceEnable = valueArr[2];
            this.repeatEnable = valueArr[3];
            this.repeat = valueArr[4];
            byte b33 = valueArr[5];
            this.startHour = b33;
            byte b34 = valueArr[6];
            this.startMinute = b34;
            byte b35 = valueArr[7];
            this.endHour = b35;
            byte b36 = valueArr[8];
            this.endMinute = b36;
            setTimespan(b33, b34, b35, b36);
        } else if (i == 14) {
            this.triggerDimming = valueArr[1];
            this.duration = CmdCtrl.getBigHex(new byte[]{valueArr[2], valueArr[3]}, 2);
            this.luminanceEnable = valueArr[4];
            this.repeatEnable = valueArr[5];
            this.repeat = valueArr[6];
            byte b37 = valueArr[7];
            this.startHour = b37;
            byte b38 = valueArr[8];
            this.startMinute = b38;
            byte b39 = valueArr[9];
            this.endHour = b39;
            byte b40 = valueArr[10];
            this.endMinute = b40;
            setTimespan(b37, b38, b39, b40);
        } else if (i == 15) {
            this.repeatEnable = valueArr[1];
            byte b41 = valueArr[2];
            this.startHour = b41;
            byte b42 = valueArr[3];
            this.startMinute = b42;
            byte b43 = valueArr[4];
            this.endHour = b43;
            byte b44 = valueArr[5];
            this.endMinute = b44;
            setTimespan(b41, b42, b43, b44);
        } else if (i == 16) {
            byte b45 = valueArr[1];
            this.startHour = b45;
            byte b46 = valueArr[2];
            this.startMinute = b46;
            byte b47 = valueArr[3];
            this.endHour = b47;
            byte b48 = valueArr[4];
            this.endMinute = b48;
            setTimespan(b45, b46, b47, b48);
        } else if (i == 17) {
            this.duration = valueArr[1];
            this.slightDimming = valueArr[2];
            this.triggerMotion = new TriggerMotion(CmdCtrl.getBigHex(new byte[]{valueArr[3], valueArr[4]}, 2));
        } else if (i != 18) {
            if (i == 19) {
                this.duration = valueArr[1];
            } else if (i == 0) {
                this.duration = 0;
                this.slightDimming = 0;
            }
        }
    }

    public byte[] toModeParams() {
        int i = this.mode;
        if (i == 1) {
            byte[] durationBytes = CmdCtrl.int2ByteArr((long) (this.duration / 60), 2);
            return new byte[]{durationBytes[0], durationBytes[1], (byte) this.slightDimming};
        } else if (i == 2) {
            byte[] durationBytes2 = CmdCtrl.int2ByteArr((long) this.duration, 2);
            byte[] addressBytes = getTriggerTypeAddr();
            return new byte[]{durationBytes2[0], durationBytes2[1], (byte) this.slightDimming, addressBytes[0], addressBytes[1]};
        } else if (i == 3) {
            byte[] durationBytes3 = CmdCtrl.int2ByteArr((long) this.duration, 2);
            byte[] addressBytes2 = getTriggerTypeAddr();
            return new byte[]{(byte) this.triggerDimming, durationBytes3[0], durationBytes3[1], (byte) this.slightStatus, (byte) this.slightDimming, addressBytes2[0], addressBytes2[1], (byte) this.repeat, (byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute, (byte) this.ecoStatus, (byte) this.ecoTriggerDimming, (byte) this.ecoSlightDimming, (byte) this.ecoStartHour, (byte) this.ecoStartMinute, (byte) this.ecoEndHour, (byte) this.ecoEndMinute};
        } else if (i == 4) {
            return new byte[]{(byte) this.triggerDimming, (byte) this.repeat, (byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
        } else if (i == 5) {
            return new byte[]{(byte) this.triggerDimming, (byte) this.slightDimming, (byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
        } else if (i == 6) {
            return new byte[]{(byte) this.repeat, (byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
        } else if (i == 7) {
            byte[] durationBytes4 = CmdCtrl.int2ByteArr((long) (this.duration / 60), 2);
            return new byte[]{durationBytes4[0], durationBytes4[1], (byte) this.slightStatus, (byte) this.slightDimming, (byte) this.triggerDimming};
        } else if (i == 8) {
            byte[] durationBytes5 = CmdCtrl.int2ByteArr((long) this.duration, 2);
            byte[] addressBytes3 = getTriggerTypeAddr();
            return new byte[]{durationBytes5[0], durationBytes5[1], (byte) this.slightStatus, (byte) this.slightDimming, addressBytes3[0], addressBytes3[1], (byte) this.triggerDimming, (byte) this.pirSensitivity};
        } else if (i == 9) {
            byte[] durationBytes6 = CmdCtrl.int2ByteArr((long) this.duration, 2);
            byte[] addressBytes4 = getTriggerTypeAddr();
            return new byte[]{(byte) this.triggerDimming, durationBytes6[0], durationBytes6[1], (byte) this.slightDimming, addressBytes4[0], addressBytes4[1], (byte) this.pirSensitivity, (byte) this.securityAlarmEnable, (byte) this.repeatEnable, (byte) this.repeat, (byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
        } else if (i == 10) {
            return new byte[]{(byte) this.triggerDimming, (byte) this.repeatEnable, (byte) this.repeat, (byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
        } else if (i == 11) {
            return new byte[]{(byte) this.triggerDimming, (byte) this.repeat, (byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
        } else if (i == 12) {
            byte[] durationBytes7 = CmdCtrl.int2ByteArr((long) this.duration, 2);
            byte[] addressBytes5 = getTriggerTypeAddr();
            return new byte[]{(byte) this.triggerDimming, durationBytes7[0], durationBytes7[1], (byte) this.slightDimming, addressBytes5[0], addressBytes5[1], (byte) this.pirSensitivity, (byte) this.repeatEnable, (byte) this.repeat, (byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
        } else if (i == 13) {
            return new byte[]{(byte) this.triggerDimming, (byte) this.luminanceEnable, (byte) this.repeatEnable, (byte) this.repeat, (byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
        } else if (i == 14) {
            byte[] durationBytes8 = CmdCtrl.int2ByteArr((long) this.duration, 2);
            return new byte[]{(byte) this.triggerDimming, durationBytes8[0], durationBytes8[1], (byte) this.luminanceEnable, (byte) this.repeatEnable, (byte) this.repeat, (byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
        } else if (i == 15) {
            return new byte[]{(byte) this.repeatEnable, (byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
        } else if (i == 16) {
            return new byte[]{(byte) this.startHour, (byte) this.startMinute, (byte) this.endHour, (byte) this.endMinute};
        } else if (i == 17) {
            byte[] addressBytes6 = getTriggerTypeAddr();
            return new byte[]{(byte) this.duration, (byte) this.slightDimming, addressBytes6[0], addressBytes6[1]};
        } else if (i == 18) {
            return new byte[0];
        } else {
            if (i != 19) {
                return new byte[0];
            }
            return new byte[]{(byte) this.duration};
        }
    }

    private byte[] getTriggerTypeAddr() {
        if ("group".equals(this.triggerMotion.type)) {
            return CmdCtrl.int2ByteArr((long) this.triggerMotion.meshGroupId, 2);
        }
        if ("all".equals(this.triggerMotion.type)) {
            return new byte[]{-1, -1};
        }
        return new byte[]{0, 0};
    }

    public int getModeParamsLen() {
        int i = this.mode;
        if (i == 1) {
            return 3;
        }
        if (i == 2) {
            return 5;
        }
        if (i == 3) {
            return 19;
        }
        if (i == 4 || i == 5) {
            return 6;
        }
        if (i == 6 || i == 7) {
            return 5;
        }
        if (i == 8) {
            return 8;
        }
        if (i == 9) {
            return 14;
        }
        if (i == 10) {
            return 7;
        }
        if (i == 11) {
            return 6;
        }
        if (i == 12) {
            return 13;
        }
        if (i == 13) {
            return 8;
        }
        if (i == 14) {
            return 10;
        }
        if (i == 15) {
            return 5;
        }
        if (i == 16) {
            return 4;
        }
        if (i == 17) {
            return 3;
        }
        if (i != 18 && i == 19) {
            return 1;
        }
        return 0;
    }

    public class TriggerMotion {
        public int groupId;
        public int meshGroupId;
        public String type = "none";

        public TriggerMotion(int meshGroupId2) {
            this.meshGroupId = meshGroupId2;
            if (meshGroupId2 == 0) {
                this.type = "none";
            } else if (meshGroupId2 == 65535) {
                this.type = "all";
            } else {
                GroupInfo groupInfo = LDSMeshUtil.findGroupByAddress(SIGMesh.getInstance().getMeshInfo().groups, meshGroupId2);
                if (groupInfo != null) {
                    this.groupId = groupInfo.groupId;
                }
                this.type = "group";
            }
        }
    }

    public JSONObject toJson() {
        try {
            return new JSONObject(new Gson().toJson((Object) this));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean hasGroup() {
        TriggerMotion triggerMotion2 = this.triggerMotion;
        if (triggerMotion2 == null || !"group".equals(triggerMotion2.type)) {
            return false;
        }
        return true;
    }

    public void convertTimespan() {
        if (this.timeSpan.size() >= 2) {
            this.startHour = this.timeSpan.get(0).intValue();
            this.startMinute = this.timeSpan.get(1).intValue();
        }
        if (this.timeSpan.size() == 4) {
            this.endHour = this.timeSpan.get(2).intValue();
            this.endMinute = this.timeSpan.get(3).intValue();
        }
    }

    public void convertEcoTimeSpan() {
        if (this.ecoTimeSpan.size() >= 2) {
            this.ecoStartHour = this.ecoTimeSpan.get(0).intValue();
            this.ecoStartMinute = this.ecoTimeSpan.get(1).intValue();
        }
        if (this.ecoTimeSpan.size() == 4) {
            this.ecoEndHour = this.ecoTimeSpan.get(2).intValue();
            this.ecoEndMinute = this.ecoTimeSpan.get(3).intValue();
        }
    }

    public void setTimespan(int... param) {
        ArrayList arrayList = new ArrayList();
        this.timeSpan = arrayList;
        arrayList.clear();
        for (int valueOf : param) {
            this.timeSpan.add(Integer.valueOf(valueOf));
        }
    }

    public void setEcoTimespan(int... param) {
        ArrayList arrayList = new ArrayList();
        this.ecoTimeSpan = arrayList;
        arrayList.clear();
        for (int valueOf : param) {
            this.ecoTimeSpan.add(Integer.valueOf(valueOf));
        }
    }
}
