package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class su0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ su0 a = new su0();

    private /* synthetic */ su0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.SwitchCluster) baseChipCluster).readCurrentPositionAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
