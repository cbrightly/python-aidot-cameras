package meshsdk.util;

public interface LDSModel {
    public static final int MODEL_BRIGHTNESS_CTRL = 4864;
    public static final int MODEL_HSL_CTRL = 4871;
    public static final int MODEL_ONLINE_CHANGE = 10000;
    public static final int MODEL_ONOFF_CTRL = 4096;
    public static final int MODEL_TEMP_CTRL = 4867;

    public static class LdsModelName {
        public static String modelName(int modelId) {
            if (4096 == modelId) {
                return " OnOff";
            }
            if (4867 == modelId) {
                return " (TEMP)CCT";
            }
            if (4864 == modelId) {
                return " Dimming";
            }
            if (4871 == modelId) {
                return " HSL";
            }
            if (10000 == modelId) {
                return " onlineChange";
            }
            return String.valueOf(modelId);
        }
    }
}
