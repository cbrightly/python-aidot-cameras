package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class a30 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ a30 a = new a30();

    private /* synthetic */ a30() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WiFiNetworkDiagnosticsCluster) baseChipCluster).readFeatureMapAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
