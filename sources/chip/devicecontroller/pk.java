package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class pk implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ pk a = new pk();

    private /* synthetic */ pk() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readLongCharStringAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
