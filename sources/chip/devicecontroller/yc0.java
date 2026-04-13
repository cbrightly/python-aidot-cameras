package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class yc0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ yc0 a = new yc0();

    private /* synthetic */ yc0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DoorLockCluster) baseChipCluster).readMaxPINCodeLengthAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
