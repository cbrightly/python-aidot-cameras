package meshsdk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scene implements Serializable {
    public int id;
    public String name = "Telink-Scene";
    public int sceneId;
    public List<SceneState> states = new ArrayList();

    public static class SceneState implements Serializable {
        public int address;
        public int lum;
        public String macAddress;
        public int onOff;
        public int temp;

        public SceneState() {
        }

        public SceneState(int address2) {
            this.address = address2;
            this.onOff = -1;
            this.lum = -1;
            this.temp = -1;
        }
    }

    public void saveFromDeviceInfo(NodeInfo deviceInfo) {
        for (SceneState state : this.states) {
            if (state.address == deviceInfo.meshAddress) {
                state.onOff = deviceInfo.getOnOff();
                state.lum = deviceInfo.lum;
                state.temp = deviceInfo.temp;
                return;
            }
        }
        SceneState state2 = new SceneState();
        state2.address = deviceInfo.meshAddress;
        state2.onOff = deviceInfo.getOnOff();
        state2.lum = deviceInfo.lum;
        state2.temp = deviceInfo.temp;
        state2.macAddress = deviceInfo.macAddress;
        this.states.add(state2);
    }

    public void removeByAddress(int address) {
        Iterator<SceneState> iterator = this.states.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().address == address) {
                iterator.remove();
                return;
            }
        }
    }

    public boolean contains(NodeInfo device) {
        for (SceneState inner : this.states) {
            if (inner.address == device.meshAddress) {
                return true;
            }
        }
        return false;
    }
}
