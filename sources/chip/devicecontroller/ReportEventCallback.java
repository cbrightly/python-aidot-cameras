package chip.devicecontroller;

import chip.devicecontroller.model.ChipEventPath;
import chip.devicecontroller.model.NodeState;

public interface ReportEventCallback {
    void onDone();

    void onError(ChipEventPath chipEventPath, Exception exc);

    void onReport(NodeState nodeState);
}
