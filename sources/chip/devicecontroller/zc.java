package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import chip.devicecontroller.ChipStructs;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class zc implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ zc a = new zc();

    private /* synthetic */ zc() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DoorLockCluster) baseChipCluster).clearCredential((ChipClusters.DefaultClusterCallback) obj, (ChipStructs.DoorLockClusterDlCredential) map.get("credential"), 10000);
    }
}
