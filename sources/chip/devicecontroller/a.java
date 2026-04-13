package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;
import java.util.Optional;

/* compiled from: lambda */
public final /* synthetic */ class a implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ a a = new a();

    private /* synthetic */ a() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.NetworkCommissioningCluster) baseChipCluster).scanNetworks((ChipClusters.NetworkCommissioningCluster.ScanNetworksResponseCallback) obj, (Optional) map.get("ssid"), (Optional) map.get("breadcrumb"));
    }
}
