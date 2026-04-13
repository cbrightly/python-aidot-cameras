package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class wa implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ wa a = new wa();

    private /* synthetic */ wa() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DoorLockCluster) baseChipCluster).getUser((ChipClusters.DoorLockCluster.GetUserResponseCallback) obj, (Integer) map.get("userIndex"));
    }
}
