package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class fz0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ fz0 a = new fz0();

    private /* synthetic */ fz0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WindowCoveringCluster) baseChipCluster).readPhysicalClosedLimitLiftAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
