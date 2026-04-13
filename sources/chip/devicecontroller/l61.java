package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class l61 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ l61 a = new l61();

    private /* synthetic */ l61() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ContentLauncherCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.ContentLauncherCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
