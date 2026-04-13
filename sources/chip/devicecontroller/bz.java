package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class bz implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ bz a = new bz();

    private /* synthetic */ bz() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readListOctetStringAttribute((ChipClusters.TestClusterCluster.ListOctetStringAttributeCallback) obj);
    }
}
