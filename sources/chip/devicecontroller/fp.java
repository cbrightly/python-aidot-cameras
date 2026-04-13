package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class fp implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ fp a = new fp();

    private /* synthetic */ fp() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readRouterRoleCountAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
