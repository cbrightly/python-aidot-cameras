package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class gd0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ gd0 a = new gd0();

    private /* synthetic */ gd0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BallastConfigurationCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.BallastConfigurationCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
