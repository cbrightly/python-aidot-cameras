package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class td implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ td a = new td();

    private /* synthetic */ td() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GeneralDiagnosticsCluster) baseChipCluster).testEventTrigger((ChipClusters.DefaultClusterCallback) obj, (byte[]) map.get("enableKey"), (Long) map.get("eventTrigger"));
    }
}
