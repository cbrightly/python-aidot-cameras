package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class rs implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ rs a = new rs();

    private /* synthetic */ rs() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readLongOctetStringAttribute((ChipClusters.OctetStringAttributeCallback) obj);
    }
}
