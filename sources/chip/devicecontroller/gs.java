package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class gs implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ gs a = new gs();

    private /* synthetic */ gs() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readCharStringAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
