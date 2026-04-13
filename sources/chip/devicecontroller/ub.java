package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;
import java.util.Optional;

/* compiled from: lambda */
public final /* synthetic */ class ub implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ub a = new ub();

    private /* synthetic */ ub() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ActionsCluster) baseChipCluster).enableAction((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("actionID"), (Optional) map.get("invokeID"));
    }
}
