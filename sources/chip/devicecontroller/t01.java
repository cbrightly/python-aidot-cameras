package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class t01 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ t01 a = new t01();

    private /* synthetic */ t01() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BridgedDeviceBasicCluster) baseChipCluster).readSerialNumberAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
