package com.telink.ble.mesh.core.message;

import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.FragmentTransaction;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.Serializable;
import meshsdk.util.LDSModel;

public enum MeshSigModel implements Serializable {
    SIG_MD_CFG_SERVER(0, "config server", "", true),
    SIG_MD_CFG_CLIENT(1, "config client", "", true),
    SIG_MD_HEALTH_SERVER(2, "health server", "health server", false),
    SIG_MD_HEALTH_CLIENT(3, "health client", "health client", false),
    SIG_MD_REMOTE_PROV_SERVER(4, "rp", "", true),
    SIG_MD_REMOTE_PROV_CLIENT(5, "rp", "", true),
    SIG_MD_DF_CFG_S(6, "", "cfg server", true),
    SIG_MD_DF_CFG_C(7, "", "cfg client", true),
    SIG_MD_BRIDGE_CFG_SERVER(8, "", "", true),
    SIG_MD_BRIDGE_CFG_CLIENT(9, "", "", true),
    SIG_MD_PRIVATE_BEACON_SERVER(10, "", "", true),
    SIG_MD_PRIVATE_BEACON_CLIENT(11, "", "", true),
    SIG_MD_G_ONOFF_S(4096, "Generic OnOff Server", "Generic"),
    SIG_MD_G_ONOFF_C(4097, "Generic OnOff Client", "Generic"),
    SIG_MD_G_LEVEL_S(4098, "Generic Level Server", "Generic"),
    SIG_MD_G_LEVEL_C(FragmentTransaction.TRANSIT_FRAGMENT_FADE, "Generic Level Client", "Generic"),
    SIG_MD_G_DEF_TRANSIT_TIME_S(4100, "Generic Default Transition Time Server ", "Generic"),
    SIG_MD_G_DEF_TRANSIT_TIME_C(4101, "Generic Default Transition Time Client ", "Generic"),
    SIG_MD_G_POWER_ONOFF_S(4102, "Generic Power OnOff Server", "Generic"),
    SIG_MD_G_POWER_ONOFF_SETUP_S(4103, "Generic Power OnOff Setup Server", "Generic"),
    SIG_MD_G_POWER_ONOFF_C(4104, "Generic Power OnOff Client", "Generic"),
    SIG_MD_G_POWER_LEVEL_S(4105, "Generic Power Level Server", "Generic"),
    SIG_MD_G_POWER_LEVEL_SETUP_S(4106, "Generic Power Level Setup Server", "Generic"),
    SIG_MD_G_POWER_LEVEL_C(4107, "Generic Power Level Client", "Generic"),
    SIG_MD_G_BAT_S(4108, "Generic Battery Server", "Generic"),
    SIG_MD_G_BAT_C(4109, "Generic Battery Client", "Generic"),
    SIG_MD_G_LOCATION_S(4110, "Generic Location Server", "Generic"),
    SIG_MD_G_LOCATION_SETUP_S(4111, "Generic Location Setup Server", "Generic"),
    SIG_MD_G_LOCATION_C(4112, "Generic Location Client", "Generic"),
    SIG_MD_G_ADMIN_PROP_S(4113, "Generic Admin Property Server", "Generic"),
    SIG_MD_G_MFR_PROP_S(4114, "Generic Manufacturer Property Server", "Generic"),
    SIG_MD_G_USER_PROP_S(4115, "Generic User Property Server", "Generic"),
    SIG_MD_G_CLIENT_PROP_S(4116, "Generic Client Property Server", "Generic"),
    SIG_MD_G_PROP_C(4117, "Generic Property Client", "Generic"),
    SIG_MD_SENSOR_S(4352, "Sensor Server", "Sensors"),
    SIG_MD_SENSOR_SETUP_S(4353, "Sensor Setup Server", "Sensors"),
    SIG_MD_SENSOR_C(4354, "Sensor Client", "Sensors"),
    SIG_MD_TIME_S(4608, "Time Server", "Time and Scenes"),
    SIG_MD_TIME_SETUP_S(4609, "Time Setup Server", "Time and Scenes"),
    SIG_MD_TIME_C(4610, "Time Client", "Time and Scenes"),
    SIG_MD_SCENE_S(4611, "Scene Server", "Time and Scenes"),
    SIG_MD_SCENE_SETUP_S(4612, "Scene Setup Server", "Time and Scenes"),
    SIG_MD_SCENE_C(4613, "Scene Client", "Time and Scenes"),
    SIG_MD_SCHED_S(4614, "MeshScheduler Server", "Time and Scenes"),
    SIG_MD_SCHED_SETUP_S(4615, "MeshScheduler Setup Server", "Time and Scenes"),
    SIG_MD_SCHED_C(4616, "MeshScheduler Client", "Time and Scenes"),
    SIG_MD_LIGHTNESS_S(LDSModel.MODEL_BRIGHTNESS_CTRL, "Light Lightness Server", "Lighting"),
    SIG_MD_LIGHTNESS_SETUP_S(4865, "Light Lightness Setup Server", "Lighting"),
    SIG_MD_LIGHTNESS_C(4866, "Light Lightness Client", "Lighting"),
    SIG_MD_LIGHT_CTL_S(LDSModel.MODEL_TEMP_CTRL, "Light CTL Server", "Lighting"),
    SIG_MD_LIGHT_CTL_SETUP_S(4868, "Light CTL Setup Server", "Lighting"),
    SIG_MD_LIGHT_CTL_C(4869, "Light CTL Client", "Lighting"),
    SIG_MD_LIGHT_CTL_TEMP_S(4870, "Light CTL Temperature Server", "Lighting"),
    SIG_MD_LIGHT_HSL_S(LDSModel.MODEL_HSL_CTRL, "Light HSL Server", "Lighting"),
    SIG_MD_LIGHT_HSL_SETUP_S(4872, "Light HSL Setup Server", "Lighting"),
    SIG_MD_LIGHT_HSL_C(4873, "Light HSL Client", "Lighting"),
    SIG_MD_LIGHT_HSL_HUE_S(4874, "Light HSL Hue Server", "Lighting"),
    SIG_MD_LIGHT_HSL_SAT_S(4875, "Light HSL Saturation Server", "Lighting"),
    SIG_MD_LIGHT_XYL_S(4876, "Light xyL Server", "Lighting"),
    SIG_MD_LIGHT_XYL_SETUP_S(4877, "Light xyL Setup Server", "Lighting"),
    SIG_MD_LIGHT_XYL_C(4878, "Light xyL Client", "Lighting"),
    SIG_MD_LIGHT_LC_S(4879, "Light LC Server", "Lighting"),
    SIG_MD_LIGHT_LC_SETUP_S(4880, "Light LC Setup Server", "Lighting"),
    SIG_MD_LIGHT_LC_C(4881, "Light LC Client", "Lighting"),
    SIG_MD_CFG_DF_S(48944, "direct forwarding server", "", true),
    SIG_MD_CFG_DF_C(48945, "direct forwarding client", "", true),
    SIG_MD_CFG_BRIDGE_S(48946, "subnet bridge server", "", true),
    SIG_MD_CFG_BRIDGE_C(48947, "subnet bridge client", "", true),
    SIG_MD_FW_UPDATE_S(65024, "firmware update server", "OTA"),
    SIG_MD_FW_UPDATE_C(65025, "firmware update client", "OTA"),
    SIG_MD_FW_DISTRIBUT_S(65026, "firmware distribute server", "OTA"),
    SIG_MD_FW_DISTRIBUT_C(65027, "firmware distribute client", "OTA"),
    SIG_MD_OBJ_TRANSFER_S(MotionEventCompat.ACTION_POINTER_INDEX_MASK, "object transfer server", "OTA"),
    SIG_MD_OBJ_TRANSFER_C(65281, "object transfer client", "OTA"),
    LDS_CUSTOM_VENDOR_MODEL(529, "leedarson vendor model", "LDS");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public String group;
    public boolean isConfigModel;
    public int modelId;
    public String modelName;
    public boolean selected;

    private MeshSigModel(int modelId2, String modelName2, String group2) {
        this(r8, r9, modelId2, modelName2, group2, false);
    }

    private MeshSigModel(int modelId2, String modelName2, String group2, boolean isConfigModel2) {
        this.isConfigModel = false;
        this.modelId = modelId2;
        this.modelName = modelName2;
        this.group = group2;
        this.isConfigModel = isConfigModel2;
    }

    public static boolean isConfigurationModel(int modelId2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(modelId2)}, (Object) null, changeQuickRedirect, true, 12425, new Class[]{Integer.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        MeshSigModel model = getById(modelId2);
        if (model == null || !model.isConfigModel) {
            return false;
        }
        return true;
    }

    public static MeshSigModel[] getDefaultSubList() {
        return new MeshSigModel[]{SIG_MD_G_ONOFF_S, LDS_CUSTOM_VENDOR_MODEL};
    }

    public static MeshSigModel[] getDefaultSubListAboveProtocalVersion5() {
        return new MeshSigModel[]{SIG_MD_G_ONOFF_S};
    }

    public static MeshSigModel getById(int modelId2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(modelId2)}, (Object) null, changeQuickRedirect, true, 12426, new Class[]{Integer.TYPE}, MeshSigModel.class);
        if (proxy.isSupported) {
            return (MeshSigModel) proxy.result;
        }
        for (MeshSigModel model : values()) {
            if (model.modelId == modelId2) {
                return model;
            }
        }
        return null;
    }
}
