package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class z0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ z0 a = new z0();

    private /* synthetic */ z0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WindowCoveringCluster) baseChipCluster).stopMotion((ChipClusters.DefaultClusterCallback) obj);
    }
}
