package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ma0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ma0 a = new ma0();

    private /* synthetic */ ma0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WiFiNetworkDiagnosticsCluster) baseChipCluster).readBssidAttribute((ChipClusters.WiFiNetworkDiagnosticsCluster.BssidAttributeCallback) obj);
    }
}
