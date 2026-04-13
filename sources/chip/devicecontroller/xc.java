package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class xc implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ xc a = new xc();

    private /* synthetic */ xc() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).moveColorTemperature((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("moveMode"), (Integer) map.get("rate"), (Integer) map.get("colorTemperatureMinimumMireds"), (Integer) map.get("colorTemperatureMaximumMireds"), (Integer) map.get("optionsMask"), (Integer) map.get("optionsOverride"));
    }
}
