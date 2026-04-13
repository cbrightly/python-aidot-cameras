package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class a5 implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ a5 a = new a5();

    private /* synthetic */ a5() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$53(l, i);
    }
}
