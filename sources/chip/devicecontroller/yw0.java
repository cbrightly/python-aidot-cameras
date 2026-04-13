package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class yw0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ yw0 a = new yw0();

    private /* synthetic */ yw0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThreadNetworkDiagnosticsCluster) baseChipCluster).readRxBeaconCountAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
