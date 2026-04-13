package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import chip.devicecontroller.ChipStructs;
import java.util.Map;
import java.util.Optional;

/* compiled from: lambda */
public final /* synthetic */ class b9 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ b9 a = new b9();

    private /* synthetic */ b9() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ApplicationLauncherCluster) baseChipCluster).launchApp((ChipClusters.ApplicationLauncherCluster.LauncherResponseCallback) obj, (ChipStructs.ApplicationLauncherClusterApplication) map.get("application"), (Optional) map.get("data"));
    }
}
