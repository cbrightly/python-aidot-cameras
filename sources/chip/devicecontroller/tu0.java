package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class tu0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ tu0 a = new tu0();

    private /* synthetic */ tu0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readTimedWriteBooleanAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
