package meshsdk.model.json;

import android.graphics.Color;
import androidx.core.graphics.ColorUtils;
import java.util.HashMap;
import meshsdk.MeshLog;
import meshsdk.ctrl.CmdCtrl;
import meshsdk.util.LDSModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RhythmStopAttrBean {
    private byte ACT_DIM_CCT = 5;
    private byte ACT_DIM_HSL = 7;
    private byte ACT_OFF = 0;
    public String groupId = "";
    public String mac = "";
    public HashMap<Integer, CtrlModel> modelsMap = new HashMap<>();

    public static RhythmStopAttrBean create(String mac2, String groupId2, JSONArray modelArr) {
        RhythmStopAttrBean bean = new RhythmStopAttrBean();
        bean.mac = mac2;
        bean.groupId = groupId2;
        if (modelArr != null) {
            for (int i = 0; i < modelArr.length(); i++) {
                try {
                    JSONObject jsonObject = modelArr.getJSONObject(i);
                    CtrlModel ctrlModel = new CtrlModel(jsonObject.optInt("modelId"), jsonObject.opt("value"));
                    bean.modelsMap.put(Integer.valueOf(ctrlModel.modelId), ctrlModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    public byte getAction() {
        HashMap<Integer, CtrlModel> hashMap = this.modelsMap;
        Integer valueOf = Integer.valueOf(LDSModel.MODEL_BRIGHTNESS_CTRL);
        if (hashMap.containsKey(valueOf) && ((Integer) this.modelsMap.get(valueOf).value).intValue() == 0) {
            return this.ACT_OFF;
        }
        if (this.modelsMap.containsKey(Integer.valueOf(LDSModel.MODEL_HSL_CTRL))) {
            return this.ACT_DIM_HSL;
        }
        if (this.modelsMap.containsKey(Integer.valueOf(LDSModel.MODEL_TEMP_CTRL))) {
            return this.ACT_DIM_CCT;
        }
        return this.ACT_OFF;
    }

    public int getActionLen() {
        int action = getAction();
        if (action == this.ACT_DIM_HSL) {
            return 4;
        }
        if (action == this.ACT_DIM_CCT) {
            return 3;
        }
        return 0;
    }

    public byte[] getActionParams() {
        int dim;
        int action = getAction();
        byte[] bArr = new byte[getActionLen()];
        if (action == this.ACT_OFF) {
            return new byte[0];
        }
        if (action == this.ACT_DIM_CCT) {
            int dim2 = 100;
            CtrlModel dimModel = this.modelsMap.get(Integer.valueOf(LDSModel.MODEL_BRIGHTNESS_CTRL));
            if (dimModel != null) {
                dim2 = ((Integer) dimModel.value).intValue();
            }
            byte[] tempBytes = CmdCtrl.int2ByteArr((long) ((Integer) this.modelsMap.get(Integer.valueOf(LDSModel.MODEL_TEMP_CTRL)).value).intValue(), 2);
            return new byte[]{(byte) dim2, tempBytes[0], tempBytes[1]};
        }
        if (action == this.ACT_DIM_HSL) {
            CtrlModel dimModel2 = this.modelsMap.get(Integer.valueOf(LDSModel.MODEL_BRIGHTNESS_CTRL));
            if (dimModel2 != null) {
                dim = ((Integer) dimModel2.value).intValue();
            } else {
                dim = 100;
            }
            try {
                JSONObject obj = new JSONObject(this.modelsMap.get(Integer.valueOf(LDSModel.MODEL_HSL_CTRL)).value.toString());
                int h = obj.getInt("HSLHue");
                HSL hsl = new HSL(h, obj.getInt("HSLSaturation"), obj.getInt("HSLLightness"));
                MeshLog.d("hsl:" + hsl.toJson());
                int color = ColorUtils.HSLToColor(new float[]{(float) hsl.HSLHue, (((float) hsl.HSLSaturation) * 1.0f) / 100.0f, (((float) hsl.HSLLightness) * 1.0f) / 100.0f});
                int r = Color.red(color);
                int g = Color.green(color);
                StringBuilder sb = new StringBuilder();
                sb.append("r:");
                sb.append(r);
                sb.append(",g:");
                int g2 = g;
                sb.append(g2);
                int i = h;
                sb.append(",b:");
                int b = Color.blue(color);
                sb.append(b);
                MeshLog.d(sb.toString());
                return new byte[]{(byte) dim, (byte) r, (byte) g2, (byte) b};
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }
}
