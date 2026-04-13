package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class fg0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ fg0 a = new fg0();

    private /* synthetic */ fg0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.FanControlCluster) baseChipCluster).readFanModeAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
