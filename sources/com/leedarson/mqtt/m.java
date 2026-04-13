package com.leedarson.mqtt;

import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import kotlin.Pair;
import kotlin.n;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: MqttConsts */
public class m {
    public static ArrayList<n<Integer, String>> a = new ArrayList<>();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static n<Integer, String> a(String message) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{message}, (Object) null, changeQuickRedirect, true, 1499, new Class[]{String.class}, n.class);
        if (proxy.isSupported) {
            return (n) proxy.result;
        }
        if (a.size() == 0) {
            a.add(new n(1, "Unacceptable protocol version"));
            a.add(new n(2, "Identifier rejected"));
            a.add(new n(3, "Server unavailable"));
            a.add(new n(4, "Bad username or password"));
            a.add(new n(5, "Not authorized"));
            a.add(new n(16, "No matching subscribers"));
            a.add(new n(17, "No subscription existed"));
            a.add(new n(128, "Unspecified error"));
            a.add(new n(Integer.valueOf(NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM), "Malformed Packet"));
            a.add(new n(Integer.valueOf(NeedPermissionEvent.PER_IPC_ALBUM_PERM), "Protocol Error"));
            a.add(new n(131, "Implementation specific error"));
            a.add(new n(132, "Unsupported Protocol Version"));
            a.add(new n(133, "Client Identifier not valid"));
            a.add(new n(134, "Bad User Name or Password"));
            a.add(new n(135, "Not authorized"));
            a.add(new n(136, "Server unavailable"));
            a.add(new n(Integer.valueOf(NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV), "Server busy"));
            a.add(new n(138, "Banned"));
            a.add(new n(Integer.valueOf(NeedPermissionEvent.PER_GET_LOCATION_BLE), "Server shutting down"));
            a.add(new n(Integer.valueOf(NeedPermissionEvent.PER_ANDROID_S_BLE), "Bad authentication method"));
            a.add(new n(Integer.valueOf(NeedPermissionEvent.PER_ANDROID_NOTIFICATION), "Keep Alive timeout"));
            a.add(new n(142, "Session taken over"));
            a.add(new n(143, "Topic Filter invalid"));
            a.add(new n(Integer.valueOf(IjkMediaMeta.FF_PROFILE_H264_HIGH_444), "Topic Name invalid"));
            a.add(new n(145, "Packet identifier in use"));
            a.add(new n(146, "Packet Identifier not found"));
            a.add(new n(Integer.valueOf(Opcodes.LCMP), "Topic Alias invalid"));
            a.add(new n(Integer.valueOf(Opcodes.FCMPL), "Packet too large"));
            a.add(new n(150, "Message rate too high"));
            a.add(new n(Integer.valueOf(Opcodes.DCMPL), "Quota exceeded"));
            a.add(new n(152, "Administrative action"));
            a.add(new n(Integer.valueOf(Opcodes.IFEQ), "Payload format invalid"));
            a.add(new n(Integer.valueOf(Opcodes.IFNE), "Retain not supported"));
            a.add(new n(155, "QoS not supported"));
            a.add(new n(156, "Use another server"));
            a.add(new n(157, "Server moved"));
            a.add(new n(Integer.valueOf(Opcodes.IFLE), "Shared Subscriptions not supported"));
            a.add(new n(Integer.valueOf(Opcodes.IF_ICMPEQ), "Connection rate exceeded"));
            a.add(new n(Integer.valueOf(Opcodes.IF_ICMPNE), "Maximum connect time"));
            a.add(new n(Integer.valueOf(Opcodes.IF_ICMPLT), "Subscription Identifiers not supported"));
            a.add(new n(Integer.valueOf(Opcodes.IF_ICMPGE), "Wildcard Subscriptions not supported"));
        }
        for (int i = 0; i < a.size(); i++) {
            Pair<Integer, String> _item = (n) a.get(i);
            if (message.contains((CharSequence) _item.getSecond())) {
                return new n<>((Integer) _item.getFirst(), ((String) _item.getSecond()) + message);
            }
        }
        return new n<>(-4000015, message);
    }
}
