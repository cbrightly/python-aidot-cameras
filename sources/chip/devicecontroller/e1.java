package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class e1 implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ e1 a = new e1();

    private /* synthetic */ e1() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$31(l, i);
    }
}
