package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class pe1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ pe1 a = new pe1();

    private /* synthetic */ pe1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BallastConfigurationCluster) baseChipCluster).writeBallastFactorAdjustmentAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
