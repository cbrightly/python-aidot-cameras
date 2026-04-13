package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class lq implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ lq a = new lq();

    private /* synthetic */ lq() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BallastConfigurationCluster) baseChipCluster).readLampRatedHoursAttribute((ChipClusters.BallastConfigurationCluster.LampRatedHoursAttributeCallback) obj);
    }
}
