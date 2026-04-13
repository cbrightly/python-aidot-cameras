package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class iw0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ iw0 a = new iw0();

    private /* synthetic */ iw0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ApplicationLauncherCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.ApplicationLauncherCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
