package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class sd implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ sd a = new sd();

    private /* synthetic */ sd() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BarrierControlCluster) baseChipCluster).barrierControlGoToPercent((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("percentOpen"));
    }
}
