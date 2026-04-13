package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import chip.devicecontroller.ChipStructs;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class d2 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ d2 a = new d2();

    private /* synthetic */ d2() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ApplicationLauncherCluster) baseChipCluster).stopApp((ChipClusters.ApplicationLauncherCluster.LauncherResponseCallback) obj, (ChipStructs.ApplicationLauncherClusterApplication) map.get("application"));
    }
}
