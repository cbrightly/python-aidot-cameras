package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class d80 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ d80 a = new d80();

    private /* synthetic */ d80() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readListStructOctetStringAttribute((ChipClusters.TestClusterCluster.ListStructOctetStringAttributeCallback) obj);
    }
}
