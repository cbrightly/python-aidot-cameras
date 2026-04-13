package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class n11 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ n11 a = new n11();

    private /* synthetic */ n11() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.EthernetNetworkDiagnosticsCluster) baseChipCluster).readFeatureMapAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
