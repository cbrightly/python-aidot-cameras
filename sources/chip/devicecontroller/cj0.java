package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class cj0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ cj0 a = new cj0();

    private /* synthetic */ cj0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WindowCoveringCluster) baseChipCluster).readInstalledOpenLimitTiltAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
