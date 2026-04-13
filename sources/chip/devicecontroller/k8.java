package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class k8 implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ k8 a = new k8();

    private /* synthetic */ k8() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$22(l, i);
    }
}
