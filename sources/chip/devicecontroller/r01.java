package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class r01 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ r01 a = new r01();

    private /* synthetic */ r01() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readNullableFloatDoubleAttribute((ChipClusters.TestClusterCluster.NullableFloatDoubleAttributeCallback) obj);
    }
}
