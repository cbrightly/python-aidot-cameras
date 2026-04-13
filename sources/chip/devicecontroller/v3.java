package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class v3 implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ v3 a = new v3();

    private /* synthetic */ v3() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$62(l, i);
    }
}
