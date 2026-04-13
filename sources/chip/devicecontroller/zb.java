package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class zb implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ zb a = new zb();

    private /* synthetic */ zb() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$46(l, i);
    }
}
