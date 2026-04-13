package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class xk0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ xk0 a = new xk0();

    private /* synthetic */ xk0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PowerSourceCluster) baseChipCluster).readBatPresentAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
