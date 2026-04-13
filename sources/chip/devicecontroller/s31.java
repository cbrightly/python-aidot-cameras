package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class s31 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ s31 a = new s31();

    private /* synthetic */ s31() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readEnumAttrAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
