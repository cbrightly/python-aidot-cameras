package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class nc1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ nc1 a = new nc1();

    private /* synthetic */ nc1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.UnitLocalizationCluster) baseChipCluster).writeTemperatureUnitAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
