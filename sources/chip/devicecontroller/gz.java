package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class gz implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ gz a = new gz();

    private /* synthetic */ gz() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ContentLauncherCluster) baseChipCluster).readAcceptHeaderAttribute((ChipClusters.ContentLauncherCluster.AcceptHeaderAttributeCallback) obj);
    }
}
