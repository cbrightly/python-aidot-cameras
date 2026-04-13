package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ut0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ut0 a = new ut0();

    private /* synthetic */ ut0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.FanControlCluster) baseChipCluster).readRockSettingAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
