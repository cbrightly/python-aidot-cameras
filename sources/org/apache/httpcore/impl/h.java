package org.apache.httpcore.impl;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import java.util.Locale;
import meshsdk.BaseResp;
import org.apache.httpcore.util.a;
import org.apache.httpcore.w;
import org.glassfish.tyrus.spi.UpgradeRequest;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

/* compiled from: EnglishReasonPhraseCatalog */
public class h implements w {
    public static final h a = new h();
    private static final String[][] b = {null, new String[3], new String[8], new String[8], new String[25], new String[8]};

    static {
        b(200, "OK");
        b(201, "Created");
        b(KitWrapItem.TYPE_MODE, "Accepted");
        b(KitWrapItem.TYPE_VERSION, "No Content");
        b(301, "Moved Permanently");
        b(302, "Moved Temporarily");
        b(304, "Not Modified");
        b(BaseResp.ERR_MSG_SEND_FAIL, "Bad Request");
        b(401, "Unauthorized");
        b(403, "Forbidden");
        b(404, "Not Found");
        b(500, "Internal Server Error");
        b(TypedValues.PositionType.TYPE_TRANSITION_EASING, "Not Implemented");
        b(TypedValues.PositionType.TYPE_DRAWPATH, "Bad Gateway");
        b(TypedValues.PositionType.TYPE_PERCENT_WIDTH, "Service Unavailable");
        b(100, "Continue");
        b(307, "Temporary Redirect");
        b(BaseResp.ERR_CONNECT_FAIL, "Method Not Allowed");
        b(BaseResp.ERR_DISCONNECT_FAIL, "Conflict");
        b(BaseResp.ERR_DOWNLOAD_MESH_FAIL, "Precondition Failed");
        b(BaseResp.ERR_DOWNLOAD_IMPORT_FAIL, "Request Too Long");
        b(BaseResp.ERR_OP_FAIL, "Request-URI Too Long");
        b(BaseResp.ERR_PARAM_ERROR, "Unsupported Media Type");
        b(IjkMediaCodecInfo.RANK_SECURE, "Multiple Choices");
        b(303, "See Other");
        b(305, "Use Proxy");
        b(402, "Payment Required");
        b(BaseResp.ERR_WAIT_RESPONSE, "Not Acceptable");
        b(407, "Proxy Authentication Required");
        b(BaseResp.ERR_INVAILD_MODEL_ID, "Request Timeout");
        b(101, UpgradeRequest.RESPONSE_CODE_MESSAGE);
        b(KitWrapItem.TYPE_EXIT, "Non Authoritative Information");
        b(205, "Reset Content");
        b(206, "Partial Content");
        b(TypedValues.PositionType.TYPE_PERCENT_HEIGHT, "Gateway Timeout");
        b(TypedValues.PositionType.TYPE_SIZE_PERCENT, "Http Version Not Supported");
        b(BaseResp.ERR_UPLOAD_EXPORT_FAIL, "Gone");
        b(411, "Length Required");
        b(416, "Requested Range Not Satisfiable");
        b(BaseResp.ERR_GROUP_NOT_EMPTY, "Expectation Failed");
        b(102, "Processing");
        b(207, "Multi-Status");
        b(422, "Unprocessable Entity");
        b(BaseResp.ERR_SCENE_NOT_EXIST, "Insufficient Space On Resource");
        b(420, "Method Failure");
        b(423, "Locked");
        b(TypedValues.PositionType.TYPE_PERCENT_Y, "Insufficient Storage");
        b(424, "Failed Dependency");
    }

    protected h() {
    }

    public String a(int status, Locale loc) {
        boolean z = status >= 100 && status < 600;
        a.a(z, "Unknown category for status code " + status);
        int category = status / 100;
        int subcode = status - (category * 100);
        String[][] strArr = b;
        if (strArr[category].length > subcode) {
            return strArr[category][subcode];
        }
        return null;
    }

    private static void b(int status, String reason) {
        int category = status / 100;
        b[category][status - (category * 100)] = reason;
    }
}
