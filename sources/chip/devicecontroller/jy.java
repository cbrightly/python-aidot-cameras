package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class jy implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ jy a = new jy();

    private /* synthetic */ jy() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DoorLockCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.DoorLockCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
