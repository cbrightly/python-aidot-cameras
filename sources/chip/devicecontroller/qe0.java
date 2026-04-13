package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class qe0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ qe0 a = new qe0();

    private /* synthetic */ qe0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readNullableFloatSingleAttribute((ChipClusters.TestClusterCluster.NullableFloatSingleAttributeCallback) obj);
    }
}
