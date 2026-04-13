package meshsdk.model;

import android.graphics.Color;
import androidx.core.graphics.ColorUtils;
import java.util.HashMap;
import meshsdk.MeshLog;
import meshsdk.ctrl.CmdCtrl;
import meshsdk.model.CustomScene;
import meshsdk.model.json.HSL;
import meshsdk.util.LDSModel;
import org.json.JSONObject;

public class SceneRulesWrap {
    private final int TYPE_OFF = 0;
    private final int TYPE_ON_DIM_CCT = 5;
    private final int TYPE_ON_DIM_RGB = 7;
    private HashMap<Integer, CustomScene.SceneRule> rules;

    public SceneRulesWrap(HashMap<Integer, CustomScene.SceneRule> rules2) {
        this.rules = rules2;
    }

    public int getActionType() {
        if (this.rules.containsKey(4096) && ((Integer) this.rules.get(4096).getValue()).intValue() == 0) {
            return 0;
        }
        if (this.rules.containsKey(Integer.valueOf(LDSModel.MODEL_HSL_CTRL))) {
            return 7;
        }
        return 5;
    }

    public int getActionParamLength(int actionType) {
        if (actionType == 0) {
            return 0;
        }
        if (actionType == 5) {
            return 3;
        }
        if (actionType == 7) {
            return 4;
        }
        return 0;
    }

    public byte[] toActionParams() {
        int type = getActionType();
        byte[] params = new byte[getActionParamLength(type)];
        if (type == 0) {
            return params;
        }
        CustomScene.SceneRule rule = this.rules.get(Integer.valueOf(LDSModel.MODEL_BRIGHTNESS_CTRL));
        int lum = rule != null ? ((Integer) rule.getValue()).intValue() : 0;
        if (type == 5) {
            CustomScene.SceneRule tempRule = this.rules.get(Integer.valueOf(LDSModel.MODEL_TEMP_CTRL));
            if (tempRule != null) {
                byte[] tempBytes = CmdCtrl.int2ByteArr((long) ((Integer) tempRule.getValue()).intValue(), 2);
                params[0] = (byte) lum;
                params[1] = tempBytes[0];
                params[2] = tempBytes[1];
            }
        } else if (type == 7) {
            try {
                JSONObject treeMap = (JSONObject) this.rules.get(Integer.valueOf(LDSModel.MODEL_HSL_CTRL)).getValue();
                HSL hsl = new HSL(treeMap.getInt("HSLHue"), treeMap.getInt("HSLSaturation"), treeMap.getInt("HSLLightness"));
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
                sb.append(",b:");
                int b = Color.blue(color);
                sb.append(b);
                MeshLog.d(sb.toString());
                params[0] = (byte) lum;
                params[1] = (byte) r;
                params[2] = (byte) g2;
                params[3] = (byte) b;
            } catch (Exception e) {
                e.printStackTrace();
                MeshLog.e("toActionParams exception:" + e.getMessage());
            }
        }
        return params;
    }
}
