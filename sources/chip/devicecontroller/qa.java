package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class qa implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ qa a = new qa();

    private /* synthetic */ qa() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$64(l, i);
    }
}
