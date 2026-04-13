package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class yk0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ yk0 a = new yk0();

    private /* synthetic */ yk0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readGeneralErrorBooleanAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
