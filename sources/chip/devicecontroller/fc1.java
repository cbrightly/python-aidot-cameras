package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class fc1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ fc1 a = new fc1();

    private /* synthetic */ fc1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DoorLockCluster) baseChipCluster).writeAutoRelockTimeAttribute((ChipClusters.DefaultClusterCallback) obj, (Long) map.get("value"));
    }
}
