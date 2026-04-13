package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class li implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ li a = new li();

    private /* synthetic */ li() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readFloatSingleAttribute((ChipClusters.FloatAttributeCallback) obj);
    }
}
