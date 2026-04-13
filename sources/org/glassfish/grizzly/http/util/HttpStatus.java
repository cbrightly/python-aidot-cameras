package org.glassfish.grizzly.http.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import meshsdk.BaseResp;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.Charsets;
import org.glassfish.tyrus.spi.UpgradeRequest;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

public class HttpStatus {
    public static final HttpStatus ACCEPTED_202 = register(KitWrapItem.TYPE_MODE, "Accepted");
    public static final HttpStatus BAD_GATEWAY_502 = register(TypedValues.PositionType.TYPE_DRAWPATH, "Bad Gateway");
    public static final HttpStatus BAD_REQUEST_400 = register(BaseResp.ERR_MSG_SEND_FAIL, "Bad Request");
    public static final HttpStatus CONFLICT_409 = register(BaseResp.ERR_DISCONNECT_FAIL, "Conflict");
    public static final HttpStatus CONINTUE_100 = register(100, "Continue");
    public static final HttpStatus CREATED_201 = register(201, "Created");
    public static final HttpStatus EXPECTATION_FAILED_417 = register(BaseResp.ERR_GROUP_NOT_EMPTY, "Expectation Failed");
    public static final HttpStatus FORBIDDEN_403 = register(403, "Forbidden");
    public static final HttpStatus FOUND_302 = register(302, "Found");
    public static final HttpStatus GATEWAY_TIMEOUT_504 = register(TypedValues.PositionType.TYPE_PERCENT_HEIGHT, "Gateway Timeout");
    public static final HttpStatus GONE_410 = register(BaseResp.ERR_UPLOAD_EXPORT_FAIL, "Gone");
    public static final HttpStatus HTTP_VERSION_NOT_SUPPORTED_505 = register(TypedValues.PositionType.TYPE_SIZE_PERCENT, "HTTP Version Not Supported");
    public static final HttpStatus INTERNAL_SERVER_ERROR_500 = register(500, "Internal Server Error");
    public static final HttpStatus LENGTH_REQUIRED_411 = register(411, "Length Required");
    public static final HttpStatus METHOD_NOT_ALLOWED_405 = register(BaseResp.ERR_CONNECT_FAIL, "Method Not Allowed");
    public static final HttpStatus MISDIRECTED_REQUEST = register(421, "Misdirected Request");
    public static final HttpStatus MOVED_PERMANENTLY_301 = register(301, "Moved Permanently");
    public static final HttpStatus MULTIPLE_CHOICES_300 = register(IjkMediaCodecInfo.RANK_SECURE, "Multiple Choices");
    public static final HttpStatus NON_AUTHORATIVE_INFORMATION_203 = register(KitWrapItem.TYPE_EXIT, "Not-Authoritative Information");
    public static final HttpStatus NOT_ACCEPTABLE_406 = register(BaseResp.ERR_WAIT_RESPONSE, "Not Acceptable");
    public static final HttpStatus NOT_FOUND_404 = register(404, "Not Found");
    public static final HttpStatus NOT_IMPLEMENTED_501 = register(TypedValues.PositionType.TYPE_TRANSITION_EASING, "Not Implemented");
    public static final HttpStatus NOT_MODIFIED_304 = register(304, "Not Modified");
    public static final HttpStatus NO_CONTENT_204 = register(KitWrapItem.TYPE_VERSION, "No Content");
    public static final HttpStatus OK_200 = register(200, "OK");
    public static final HttpStatus PARTIAL_CONTENT_206 = register(206, "Partial Content");
    public static final HttpStatus PAYMENT_REQUIRED_402 = register(402, "Payment Required");
    public static final HttpStatus PERMANENT_REDIRECT_308 = register(308, "Permanent Redirect");
    public static final HttpStatus PRECONDITION_FAILED_412 = register(BaseResp.ERR_DOWNLOAD_MESH_FAIL, "Precondition Failed");
    public static final HttpStatus PROXY_AUTHENTICATION_REQUIRED_407 = register(407, "Proxy Authentication Required");
    public static final HttpStatus REQUEST_ENTITY_TOO_LARGE_413 = register(BaseResp.ERR_DOWNLOAD_IMPORT_FAIL, "Request Entity Too Large");
    public static final HttpStatus REQUEST_HEADER_FIELDS_TOO_LARGE = register(431, "Request Header Fields Too Large");
    public static final HttpStatus REQUEST_RANGE_NOT_SATISFIABLE_416 = register(416, "Request Range Not Satisfiable");
    public static final HttpStatus REQUEST_TIMEOUT_408 = register(BaseResp.ERR_INVAILD_MODEL_ID, "Request Timeout");
    public static final HttpStatus REQUEST_URI_TOO_LONG_414 = register(BaseResp.ERR_OP_FAIL, "Request-URI Too Long");
    public static final HttpStatus RESET_CONTENT_205 = register(205, "Reset Content");
    public static final HttpStatus SEE_OTHER_303 = register(303, "See Other");
    public static final HttpStatus SERVICE_UNAVAILABLE_503 = register(TypedValues.PositionType.TYPE_PERCENT_WIDTH, "Service Unavailable");
    public static final HttpStatus SWITCHING_PROTOCOLS_101 = register(101, UpgradeRequest.RESPONSE_CODE_MESSAGE);
    public static final HttpStatus TEMPORARY_REDIRECT_307 = register(307, "Temporary Redirect");
    public static final HttpStatus UNAUTHORIZED_401 = register(401, "Unauthorized");
    public static final HttpStatus UNSUPPORTED_MEDIA_TYPE_415 = register(BaseResp.ERR_PARAM_ERROR, "Unsupported Media Type");
    public static final HttpStatus USE_PROXY_305 = register(305, "Use Proxy");
    public static final HttpStatus WEB_SOCKET_PROTOCOL_HANDSHAKE_101 = register(101, "Web Socket Protocol Handshake");
    private static final Map<Integer, HttpStatus> statusMessages = new HashMap();
    private final String reasonPhrase;
    private final byte[] reasonPhraseBytes;
    private final int status;
    private final byte[] statusBytes;

    private static HttpStatus register(int statusCode, String reasonPhrase2) {
        HttpStatus httpStatus = newHttpStatus(statusCode, reasonPhrase2);
        statusMessages.put(Integer.valueOf(statusCode), httpStatus);
        return httpStatus;
    }

    public static HttpStatus newHttpStatus(int statusCode, String reasonPhrase2) {
        return new HttpStatus(statusCode, reasonPhrase2);
    }

    public static HttpStatus getHttpStatus(int statusCode) {
        HttpStatus status2 = statusMessages.get(Integer.valueOf(statusCode));
        if (status2 == null) {
            return new HttpStatus(statusCode, "CUSTOM");
        }
        return status2;
    }

    private HttpStatus(int status2, String reasonPhrase2) {
        this.status = status2;
        this.reasonPhrase = reasonPhrase2;
        Charset charset = Charsets.ASCII_CHARSET;
        this.reasonPhraseBytes = reasonPhrase2.getBytes(charset);
        this.statusBytes = Integer.toString(status2).getBytes(charset);
    }

    public boolean statusMatches(int status2) {
        return status2 == this.status;
    }

    public int getStatusCode() {
        return this.status;
    }

    public byte[] getStatusBytes() {
        return this.statusBytes;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public byte[] getReasonPhraseBytes() {
        return this.reasonPhraseBytes;
    }

    public void setValues(HttpResponsePacket response) {
        response.setStatus(this);
        response.setReasonPhrase(Buffers.wrap((MemoryManager) null, this.reasonPhraseBytes));
    }
}
