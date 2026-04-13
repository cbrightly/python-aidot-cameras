package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;
import java.util.Optional;

/* compiled from: lambda */
public final /* synthetic */ class lb implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ lb a = new lb();

    private /* synthetic */ lb() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DoorLockCluster) baseChipCluster).unlockWithTimeout((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("timeout"), (Optional) map.get("pinCode"), 10000);
    }
}
