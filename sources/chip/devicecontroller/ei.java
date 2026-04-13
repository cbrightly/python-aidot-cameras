package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ei implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ei a = new ei();

    private /* synthetic */ ei() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readActiveNetworkFaultsListAttribute((ChipClusters.ThreadNetworkDiagnosticsCluster.ActiveNetworkFaultsListAttributeCallback) obj);
    }
}
