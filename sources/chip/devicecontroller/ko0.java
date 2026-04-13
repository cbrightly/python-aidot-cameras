package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ko0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ko0 a = new ko0();

    private /* synthetic */ ko0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ColorControlCluster) baseChipCluster).readColorTemperatureAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
