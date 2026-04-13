package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class e2 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ e2 a = new e2();

    private /* synthetic */ e2() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GeneralCommissioningCluster) baseChipCluster).setRegulatoryConfig((ChipClusters.GeneralCommissioningCluster.SetRegulatoryConfigResponseCallback) obj, (Integer) map.get("newRegulatoryConfig"), (String) map.get("countryCode"), (Long) map.get("breadcrumb"));
    }
}
