package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class wj1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ wj1 a = new wj1();

    private /* synthetic */ wj1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.OnOffCluster) baseChipCluster).writeOffWaitTimeAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
