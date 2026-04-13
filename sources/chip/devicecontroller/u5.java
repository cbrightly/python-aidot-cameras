package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class u5 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ u5 a = new u5();

    private /* synthetic */ u5() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).moveToColorTemperature((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("colorTemperature"), (Integer) map.get("transitionTime"), (Integer) map.get("optionsMask"), (Integer) map.get("optionsOverride"));
    }
}
