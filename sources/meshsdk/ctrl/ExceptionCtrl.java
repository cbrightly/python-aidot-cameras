package meshsdk.ctrl;

import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.event.ScanEvent;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.model.TestEvent;
import org.greenrobot.eventbus.c;

public class ExceptionCtrl extends CtrlLifecycle implements EventListener<String> {
    public ExceptionCtrl(SIGMesh sigMesh) {
        super(sigMesh);
        onCreate();
    }

    public void performed(Event<String> event) {
        if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_SCAN_FAIL")) {
            String errCode = "";
            if (event instanceof ScanEvent) {
                errCode = String.valueOf(((ScanEvent) event).b());
            }
            MeshLog.debugInfo("Bluetooth scan fail,errCode:" + errCode);
            c c = c.c();
            c.l(new TestEvent("exception", "Bluetooth scan fail,errCode:" + errCode));
            return;
        }
        event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_SCAN_TIMEOUT");
    }

    public void onCreate() {
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_SCAN_FAIL", this);
    }

    public void onDestroy() {
        MeshEventHandler.getInstance().removeEventListener(this);
    }
}
