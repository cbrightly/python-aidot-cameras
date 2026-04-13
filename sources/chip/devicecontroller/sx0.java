package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class sx0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ sx0 a = new sx0();

    private /* synthetic */ sx0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ContentLauncherCluster) baseChipCluster).readClusterRevisionAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
