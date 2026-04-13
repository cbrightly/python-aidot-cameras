package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class g implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ g a = new g();

    private /* synthetic */ g() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$45(l, i);
    }
}
