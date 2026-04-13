package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ib implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ib a = new ib();

    private /* synthetic */ ib() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DoorLockCluster) baseChipCluster).setUser((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("operationType"), (Integer) map.get("userIndex"), (String) map.get("userName"), (Long) map.get("userUniqueId"), (Integer) map.get("userStatus"), (Integer) map.get("userType"), (Integer) map.get("credentialRule"), 10000);
    }
}
