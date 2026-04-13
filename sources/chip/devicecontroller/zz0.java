package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class zz0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ zz0 a = new zz0();

    private /* synthetic */ zz0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WindowCoveringCluster) baseChipCluster).readPhysicalClosedLimitTiltAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
