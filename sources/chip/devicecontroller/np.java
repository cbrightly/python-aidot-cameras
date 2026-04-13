package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class np implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ np a = new np();

    private /* synthetic */ np() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readInt48uAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
