package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class pg1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ pg1 a = new pg1();

    private /* synthetic */ pg1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PumpConfigurationAndControlCluster) baseChipCluster).writeLifetimeEnergyConsumedAttribute((ChipClusters.DefaultClusterCallback) obj, (Long) map.get("value"));
    }
}
