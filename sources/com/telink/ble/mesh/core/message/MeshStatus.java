package com.telink.ble.mesh.core.message;

import android.util.SparseArray;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.config.AppKeyStatusMessage;
import com.telink.ble.mesh.core.message.config.BridgingTableStatusMessage;
import com.telink.ble.mesh.core.message.config.CompositionDataStatusMessage;
import com.telink.ble.mesh.core.message.config.ModelAppStatusMessage;
import com.telink.ble.mesh.core.message.config.ModelPublicationStatusMessage;
import com.telink.ble.mesh.core.message.config.ModelSubscriptionListStatusMessage;
import com.telink.ble.mesh.core.message.config.ModelSubscriptionStatusMessage;
import com.telink.ble.mesh.core.message.config.NetKeyStatusMessage;
import com.telink.ble.mesh.core.message.config.NodeIdentityStatusMessage;
import com.telink.ble.mesh.core.message.config.NodeResetStatusMessage;
import com.telink.ble.mesh.core.message.config.SubnetBridgeStatusMessage;
import com.telink.ble.mesh.core.message.fastpv.MeshAddressStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.FirmwareMetadataStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.FirmwareUpdateInfoStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.FirmwareUpdateStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer.BlobBlockStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer.BlobInfoStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer.BlobPartialBlockReportMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer.BlobTransferStatusMessage;
import com.telink.ble.mesh.core.message.generic.LevelStatusMessage;
import com.telink.ble.mesh.core.message.generic.OnOffStatusMessage;
import com.telink.ble.mesh.core.message.lighting.CtlStatusMessage;
import com.telink.ble.mesh.core.message.lighting.CtlTemperatureStatusMessage;
import com.telink.ble.mesh.core.message.lighting.HslStatusMessage;
import com.telink.ble.mesh.core.message.lighting.HslTargetStatusMessage;
import com.telink.ble.mesh.core.message.lighting.LightnessStatusMessage;
import com.telink.ble.mesh.core.message.rp.LinkStatusMessage;
import com.telink.ble.mesh.core.message.rp.ProvisioningPDUOutboundReportMessage;
import com.telink.ble.mesh.core.message.rp.ProvisioningPDUReportMessage;
import com.telink.ble.mesh.core.message.rp.ScanReportStatusMessage;
import com.telink.ble.mesh.core.message.rp.ScanStatusMessage;
import com.telink.ble.mesh.core.message.scene.SceneRegisterStatusMessage;
import com.telink.ble.mesh.core.message.scene.SceneStatusMessage;
import com.telink.ble.mesh.core.message.scheduler.SchedulerActionStatusMessage;
import com.telink.ble.mesh.core.message.scheduler.SchedulerStatusMessage;
import com.telink.ble.mesh.core.message.time.TimeStatusMessage;

public class MeshStatus {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static class Container {
        private static SparseArray<Class> a = new SparseArray<>();
        public static ChangeQuickRedirect changeQuickRedirect;

        static {
            b(Opcode.COMPOSITION_DATA_STATUS.value, CompositionDataStatusMessage.class);
            b(Opcode.MODE_APP_STATUS.value, ModelAppStatusMessage.class);
            b(Opcode.APPKEY_STATUS.value, AppKeyStatusMessage.class);
            b(Opcode.NODE_RESET_STATUS.value, NodeResetStatusMessage.class);
            b(Opcode.CFG_MODEL_SUB_STATUS.value, ModelSubscriptionStatusMessage.class);
            b(Opcode.CFG_MODEL_PUB_STATUS.value, ModelPublicationStatusMessage.class);
            b(Opcode.CFG_SIG_MODEL_SUB_LIST.value, ModelSubscriptionListStatusMessage.class);
            b(Opcode.NODE_ID_STATUS.value, NodeIdentityStatusMessage.class);
            b(Opcode.NETKEY_STATUS.value, NetKeyStatusMessage.class);
            b(Opcode.SUBNET_BRIDGE_STATUS.value, SubnetBridgeStatusMessage.class);
            b(Opcode.BRIDGING_TABLE_STATUS.value, BridgingTableStatusMessage.class);
            b(Opcode.G_ONOFF_STATUS.value, OnOffStatusMessage.class);
            b(Opcode.G_LEVEL_STATUS.value, LevelStatusMessage.class);
            b(Opcode.LIGHTNESS_STATUS.value, LightnessStatusMessage.class);
            b(Opcode.LIGHT_CTL_TEMP_STATUS.value, CtlTemperatureStatusMessage.class);
            b(Opcode.LIGHT_CTL_STATUS.value, CtlStatusMessage.class);
            b(Opcode.LIGHT_HSL_STATUS.value, HslStatusMessage.class);
            b(Opcode.LIGHT_HSL_TARGET_STATUS.value, HslTargetStatusMessage.class);
            b(Opcode.TIME_STATUS.value, TimeStatusMessage.class);
            b(Opcode.SCENE_STATUS.value, SceneStatusMessage.class);
            b(Opcode.SCENE_REG_STATUS.value, SceneRegisterStatusMessage.class);
            b(Opcode.SCHD_STATUS.value, SchedulerStatusMessage.class);
            b(Opcode.SCHD_ACTION_STATUS.value, SchedulerActionStatusMessage.class);
            b(Opcode.FIRMWARE_UPDATE_INFORMATION_STATUS.value, FirmwareUpdateInfoStatusMessage.class);
            b(Opcode.FIRMWARE_UPDATE_FIRMWARE_METADATA_STATUS.value, FirmwareMetadataStatusMessage.class);
            b(Opcode.FIRMWARE_UPDATE_STATUS.value, FirmwareUpdateStatusMessage.class);
            b(Opcode.BLOB_BLOCK_STATUS.value, BlobBlockStatusMessage.class);
            b(Opcode.BLOB_INFORMATION_STATUS.value, BlobInfoStatusMessage.class);
            b(Opcode.BLOB_TRANSFER_STATUS.value, BlobTransferStatusMessage.class);
            b(Opcode.BLOB_PARTIAL_BLOCK_REPORT.value, BlobPartialBlockReportMessage.class);
            b(Opcode.REMOTE_PROV_SCAN_STS.value, ScanStatusMessage.class);
            b(Opcode.REMOTE_PROV_SCAN_REPORT.value, ScanReportStatusMessage.class);
            b(Opcode.REMOTE_PROV_LINK_STS.value, LinkStatusMessage.class);
            b(Opcode.REMOTE_PROV_PDU_REPORT.value, ProvisioningPDUReportMessage.class);
            b(Opcode.REMOTE_PROV_PDU_OUTBOUND_REPORT.value, ProvisioningPDUOutboundReportMessage.class);
            b(Opcode.VD_MESH_ADDR_GET_STS.value, MeshAddressStatusMessage.class);
        }

        public static void b(int opcode, Class statusMessageCls) {
            Object[] objArr = {new Integer(opcode), statusMessageCls};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12428, new Class[]{Integer.TYPE, Class.class}, Void.TYPE).isSupported) {
                a.put(opcode, statusMessageCls);
            }
        }

        public static Class a(int opcode) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(opcode)}, (Object) null, changeQuickRedirect, true, 12429, new Class[]{Integer.TYPE}, Class.class);
            return proxy.isSupported ? (Class) proxy.result : a.get(opcode);
        }
    }
}
