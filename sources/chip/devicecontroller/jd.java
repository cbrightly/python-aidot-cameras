package chip.devicecontroller;

import chip.clusterinfo.ClusterInfo;
import chip.devicecontroller.ChipClusters;

/* compiled from: lambda */
public final /* synthetic */ class jd implements ClusterInfo.ClusterConstructor {
    public static final /* synthetic */ jd a = new jd();

    private /* synthetic */ jd() {
    }

    public final ChipClusters.BaseChipCluster create(Long l, int i) {
        return ClusterInfoMapping.lambda$initializeClusterMap$5(l, i);
    }
}
