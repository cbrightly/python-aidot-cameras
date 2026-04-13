package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class fk1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ fk1 a = new fk1();

    private /* synthetic */ fk1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ContentLauncherCluster) baseChipCluster).writeSupportedStreamingProtocolsAttribute((ChipClusters.DefaultClusterCallback) obj, (Long) map.get("value"));
    }
}
