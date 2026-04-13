package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class cx0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ cx0 a = new cx0();

    private /* synthetic */ cx0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BasicCluster) baseChipCluster).readHardwareVersionAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
