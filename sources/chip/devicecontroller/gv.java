package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class gv implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ gv a = new gv();

    private /* synthetic */ gv() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DoorLockCluster) baseChipCluster).readLockStateAttribute((ChipClusters.DoorLockCluster.LockStateAttributeCallback) obj);
    }
}
