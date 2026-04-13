package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class e4 implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ e4 a = new e4();

    private /* synthetic */ e4() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$34(l, i);
    }
}
