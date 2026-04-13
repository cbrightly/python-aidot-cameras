package meshsdk.model.json;

import com.telink.ble.mesh.entity.Scheduler;
import com.telink.ble.mesh.entity.Smart;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import meshsdk.MeshLogNew;
import meshsdk.model.CustomGroup;

public class MeshStorage {
    public String $schema = Defaults.Schema;
    public List<ApplicationKey> appKeys;
    public String brand;
    public List<CustomGroup> customGroups;
    public List<CustomSceneStorage> customScenes;
    public List<Group> groups;
    public String houseId;
    public String id = Defaults.Id;
    public String ivIndex = String.format(Locale.US, "%08X", new Object[]{0});
    public String meshName = Defaults.MeshName;
    public String meshUUID;
    public List<NetworkKey> netKeys;
    public List<Node> nodes;
    public List<Provisioner> provisioners;
    public List<Scene> scenes;
    public String timestamp;
    public String version = "1.0.0";

    public static class ApplicationKey {
        public int boundNetKey;
        public int index;
        public String key;
        public String name;
        public String oldKey = Defaults.KEY_INVALID;
    }

    public interface Defaults {
        public static final String ADDRESS_INVALID = "0000";
        public static final int IV_INDEX = 0;
        public static final String Id = "http://www.bluetooth.com/specifications/assigned-numbers/mesh-profile/cdb-schema.json#";
        public static final String KEY_INVALID = "00000000000000000000000000000000";
        public static final String LOCAL_DEVICE_KEY = "00112233445566778899AABBCCDDEEFF";
        public static final String MeshName = "Telink-Sig-Mesh-Android";
        public static final String Schema = "http://json-schema.org/draft-04/schema#";
        public static final String Version = "1.0.0";
    }

    public static class Element {
        public int index;
        public String location;
        public List<Model> models;
        public String name;
    }

    public static class Group {
        public String address;
        public int groupId;
        public String groupType;
        public String name;
        public String parentAddress = Defaults.ADDRESS_INVALID;
    }

    public static class HeartbeatPublication {
        public String address;
        public List<String> features;
        public int index;
        public int period;
        public int ttl;
    }

    public static class HeartbeatSubscription {
        public String destination;
        public int period;
        public String source;
    }

    public static class Model {
        public List<Integer> bind;
        public String modelId;
        public Publish publish;
        public List<String> subscribe = new ArrayList();
    }

    public static class NetworkKey {
        public int index;
        public String key;
        public String minSecurity;
        public String name;
        public String oldKey = Defaults.KEY_INVALID;
        public int phase;
        public String timestamp;
    }

    public static class Node {
        public String UUID;
        public List<NodeKey> appKeys;
        public boolean blacklisted;
        public String cid;
        public boolean configComplete;
        public String crpl;
        public int defaultTTL = 10;
        public String deviceKey;
        public List<Element> elements;
        public Features features;
        public HeartbeatPublication heartbeatPub;
        public List<HeartbeatSubscription> heartbeatSub;
        public String macAddress;
        public String name;
        public List<NodeKey> netKeys;
        public Transmit networkTransmit;
        public String pid;
        public int protocolVersion;
        public Transmit relayRetransmit;
        public List<NodeScheduler> schedulers;
        public boolean secureNetworkBeacon = true;
        public String security;
        public List<Smart> smarts;
        public String unicastAddress;
        public String vid;
    }

    public static class Publish {
        public String address;
        public int credentials;
        public int index;
        public PublishPeriod period;
        public Transmit retransmit;
        public int ttl;
    }

    public static class PublishPeriod {
        public int numberOfSteps;
        public int resolution;
    }

    public static class Scene {
        public List<String> addresses;
        public String name;
        public String number;
        public int sceneId;
    }

    public static class Provisioner implements Serializable {
        public String UUID;
        public List<AddressRange> allocatedGroupRange;
        public List<SceneRange> allocatedSceneRange;
        public List<AddressRange> allocatedUnicastRange;
        public String provisionerName;

        public static class AddressRange implements Serializable {
            public String highAddress;
            public String lowAddress;

            public AddressRange(String lowAddress2, String highAddress2) {
                this.lowAddress = lowAddress2;
                this.highAddress = highAddress2;
            }
        }

        public static class SceneRange implements Serializable {
            public String firstScene;
            public String lastScene;

            public SceneRange(String firstScene2, String lastScene2) {
                this.firstScene = firstScene2;
                this.lastScene = lastScene2;
            }
        }
    }

    public static class NodeScheduler {
        public long action;
        public long day;
        public long hour;
        public byte index;
        public long minute;
        public long month;
        public int sceneId;
        public long second;
        public int smartId;
        public long transTime;
        public long week;
        public long year;

        public static NodeScheduler fromScheduler(Scheduler scheduler) {
            NodeScheduler nodeScheduler = new NodeScheduler();
            nodeScheduler.index = scheduler.getIndex();
            Scheduler.Register register = scheduler.getRegister();
            nodeScheduler.year = register.getYear();
            nodeScheduler.month = register.getMonth();
            nodeScheduler.day = register.getDay();
            nodeScheduler.hour = register.getHour();
            nodeScheduler.minute = register.getMinute();
            nodeScheduler.second = register.getSecond();
            nodeScheduler.week = register.getWeek();
            nodeScheduler.action = register.getAction();
            nodeScheduler.transTime = register.getTransTime();
            nodeScheduler.sceneId = register.getSceneId();
            nodeScheduler.smartId = register.getSmartId();
            return nodeScheduler;
        }
    }

    public static class Features {
        public int friend;
        public int lowPower;
        public int proxy;
        public int relay;

        public Features(int relay2, int proxy2, int friend2, int lowPower2) {
            this.relay = relay2;
            this.proxy = proxy2;
            this.friend = friend2;
            this.lowPower = lowPower2;
        }
    }

    public static class Transmit {
        public int count;
        public int interval;

        public Transmit(int count2, int interval2) {
            this.count = count2;
            this.interval = interval2;
        }
    }

    public static class NodeKey {
        public int index;
        public boolean updated;

        public NodeKey(int index2, boolean updated2) {
            this.index = index2;
            this.updated = updated2;
        }
    }

    public void printNode() {
        for (Node node : this.nodes) {
            MeshLogNew.meshJsonLog("meshStore Node:" + node.UUID);
        }
    }
}
