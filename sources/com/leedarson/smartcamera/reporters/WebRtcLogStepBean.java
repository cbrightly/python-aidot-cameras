package com.leedarson.smartcamera.reporters;

import com.leedarson.log.tracker.BaseStepBean;

public class WebRtcLogStepBean extends BaseStepBean {
    public static final String APP_VIDEO_MEDIA_CODEC_CHECK_STEP = "APP_VIDEO_MEDIA_CODEC_CHECK_STEP";
    public static final String CHANGE_LIVING_MODE_REQ = "CHANGE_LIVING_MODE_START";
    public static final String CHANGE_LIVING_TO_PAUSE_REQ = "CHANGE_LIVING_TO_PAUSE_REQ";
    public static final String CHECK_DATA_CHANNEL_STATUE_STEP = "CHECK_DATA_CHANNEL_STATUE_STEP";
    public static final String CHECK_IN_WAITING_IN_LEAVING_ROOM_STEP = "CHECK_IN_WAITING_IN_LEAVING_ROOM_STEP";
    public static final String CHECK_NET_WORK_STEP = "CHECK_NET_WORK_STEP";
    public static final String CHECK_PEER_CON_STATUE_STEP = "CHECK_PEER_CON_STATUE_STEP";
    public static final String DEVICE_NET_WORK_CHANGED_STEP = "DEVICE_NET_WORK_CHANGED_STEP";
    public static final String DEVICE_ONLINE_CHECK_STEP = "DEVICE_ONLINE_CHECK_STEP";
    public static final String ENTER_LIVE_ROOM = "ENTER_LIVE_ROOM";
    public static final String EVENT_PEER_AND_DATACHANNEL_CONNECTED = "EVENT_PEER_AND_DATACHANNEL_CONNECTED";
    public static final String EXCHANGE_CENDIDITE_CHECKING = "EXCHANGE_CENDIDITE_CHECKING";
    public static final String EXCHANGE_CENDIDITE_RECEIVED = "EXCHANGE_CENDIDITE_RECEIVED";
    public static final String EXCHANGE_CENDIDITE_SEND_TO = "EXCHANGE_CENDIDITE_SEND_TO";
    public static final String EXCHANGE_SDP = "EXCHANGE_SDP";
    public static final String EXCHANGE_SDP_CHECKING = "EXCHANGE_SDP_CHECKING";
    public static final String FIGOUT_AND_ANLYZE_WEBRTC_DETAIL_CODE = "FIGOUT_AND_ANLYZE_WEBRTC_DETAIL_CODE";
    public static final String GET_ICECONFIG = "GET_ICECONFIG";
    public static final String IGET_FIRST_FRAME = "IGET_FIRST_FRAME";
    public static final String KVS_CREATE_STURN_SERVER_STEP = "GET_ICECONFIG";
    public static final String KVS_CREATE_VIEWER_ENDPOINT_OFSIGNEDURL = "GET_ICECONFIG";
    public static final String KVS_GET_ICECONFIG_STEP = "GET_ICECONFIG";
    public static final String KVS_SIGNAL_CHECK_WSS_ENDPOINT = "GET_ICECONFIG";
    public static final String KVS_SIGNAL_DATA_ENDPOINT_CHECK = "GET_ICECONFIG";
    public static final String KVS_SIGNAL_PARAMS_CHECK_PRE = "GET_ICECONFIG";
    public static final String KVS_SIGNAL_SERVER_ENDPOINT_CREAT = "GET_ICECONFIG";
    public static final String KVS_WEBSOCKET_CREATE_CONNECT = "SIGNAL_CONNECT";
    public static final String LEAVE_LIVE_ROOM = "LEAVE_LIVE_ROOM";
    public static final String PEER_CONNECTION_CONNECT = "PEER_CONNECTION_CONNECT";
    public static final String PULL_LIVE_TIME_OUT_CHECK_STEP = "PULL_LIVE_TIME_OUT_CHECK_STEP";
    public static final String SIGNAL_CONNECT = "SIGNAL_CONNECT";
    public static final String WAITING_LIVE_FIRST_FRAME_COMING = "WAITING_LIVE_FIRST_FRAME_COMING";
    public String requestParams = "";
    public String responseParams = "";

    public WebRtcLogStepBean(String step, int code) {
        super(step, code);
    }
}
