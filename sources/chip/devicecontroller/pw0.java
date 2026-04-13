package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class pw0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ pw0 a = new pw0();

    private /* synthetic */ pw0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WiFiNetworkDiagnosticsCluster) baseChipCluster).readCurrentMaxRateAttribute((ChipClusters.WiFiNetworkDiagnosticsCluster.CurrentMaxRateAttributeCallback) obj);
    }
}
