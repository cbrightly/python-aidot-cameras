package com.tutk.IOTC;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AVIOCTRLDEFs {
    public static final int AVIOCTRL_AUTO_PAN_LIMIT = 28;
    public static final int AVIOCTRL_AUTO_PAN_SPEED = 27;
    public static final int AVIOCTRL_AUTO_PAN_START = 29;
    public static final byte AVIOCTRL_BRIGHT_HIGH = 2;
    public static final byte AVIOCTRL_BRIGHT_LOW = 4;
    public static final byte AVIOCTRL_BRIGHT_MAX = 1;
    public static final byte AVIOCTRL_BRIGHT_MIDDLE = 3;
    public static final byte AVIOCTRL_BRIGHT_MIN = 5;
    public static final int AVIOCTRL_CLEAR_AUX = 34;
    public static final byte AVIOCTRL_CONTRASTT_MIN = 5;
    public static final byte AVIOCTRL_CONTRAST_HIGH = 2;
    public static final byte AVIOCTRL_CONTRAST_LOW = 4;
    public static final byte AVIOCTRL_CONTRAST_MAX = 1;
    public static final byte AVIOCTRL_CONTRAST_MIDDLE = 3;
    public static final byte AVIOCTRL_CRUISEMODE_HORIZONTAL = 1;
    public static final byte AVIOCTRL_CRUISEMODE_VERTICAL = 0;
    public static final byte AVIOCTRL_DOWNLOAD_FILE = 0;
    public static final int AVIOCTRL_ENVIRONMENT_INDOOR_50HZ = 0;
    public static final int AVIOCTRL_ENVIRONMENT_INDOOR_60HZ = 1;
    public static final int AVIOCTRL_ENVIRONMENT_NIGHT = 3;
    public static final int AVIOCTRL_ENVIRONMENT_OUTDOOR = 2;
    public static final int AVIOCTRL_EVENT_ALL = 0;
    public static final int AVIOCTRL_EVENT_EMERGENCY = 9;
    public static final int AVIOCTRL_EVENT_EXPT_REBOOT = 16;
    public static final int AVIOCTRL_EVENT_FULLTIME_RECORDING = 18;
    public static final int AVIOCTRL_EVENT_IOALARM = 3;
    public static final int AVIOCTRL_EVENT_IOALARMPASS = 6;
    public static final int AVIOCTRL_EVENT_MOTIONDECT = 1;
    public static final int AVIOCTRL_EVENT_MOTIONPASS = 4;
    public static final int AVIOCTRL_EVENT_MOVIE = 7;
    public static final int AVIOCTRL_EVENT_SDFAULT = 17;
    public static final int AVIOCTRL_EVENT_TIME_LAPSE = 8;
    public static final int AVIOCTRL_EVENT_VIDEOLOST = 2;
    public static final int AVIOCTRL_EVENT_VIDEORESUME = 5;
    public static final int AVIOCTRL_LENS_APERTURE_CLOSE = 22;
    public static final int AVIOCTRL_LENS_APERTURE_OPEN = 21;
    public static final int AVIOCTRL_LENS_FOCAL_FAR = 26;
    public static final int AVIOCTRL_LENS_FOCAL_NEAR = 25;
    public static final int AVIOCTRL_LENS_ZOOM_IN = 23;
    public static final int AVIOCTRL_LENS_ZOOM_OUT = 24;
    public static final int AVIOCTRL_MOTOR_RESET_POSITION = 35;
    public static final int AVIOCTRL_PATTERN_RUN = 32;
    public static final int AVIOCTRL_PATTERN_START = 30;
    public static final int AVIOCTRL_PATTERN_STOP = 31;
    public static final byte AVIOCTRL_PHOTO_FILE = 1;
    public static final int AVIOCTRL_PTZ_AUTO = 9;
    public static final int AVIOCTRL_PTZ_CLEAR_POINT = 11;
    public static final int AVIOCTRL_PTZ_DOWN = 2;
    public static final int AVIOCTRL_PTZ_FLIP = 19;
    public static final int AVIOCTRL_PTZ_GOTO_POINT = 12;
    public static final int AVIOCTRL_PTZ_LEFT = 3;
    public static final int AVIOCTRL_PTZ_LEFT_DOWN = 5;
    public static final int AVIOCTRL_PTZ_LEFT_UP = 4;
    public static final int AVIOCTRL_PTZ_MENU_ENTER = 18;
    public static final int AVIOCTRL_PTZ_MENU_EXIT = 17;
    public static final int AVIOCTRL_PTZ_MENU_OPEN = 16;
    public static final int AVIOCTRL_PTZ_MODE_RUN = 15;
    public static final int AVIOCTRL_PTZ_RIGHT = 6;
    public static final int AVIOCTRL_PTZ_RIGHT_DOWN = 8;
    public static final int AVIOCTRL_PTZ_RIGHT_UP = 7;
    public static final int AVIOCTRL_PTZ_SET_MODE_START = 13;
    public static final int AVIOCTRL_PTZ_SET_MODE_STOP = 14;
    public static final int AVIOCTRL_PTZ_SET_POINT = 10;
    public static final int AVIOCTRL_PTZ_START = 20;
    public static final int AVIOCTRL_PTZ_STOP = 0;
    public static final int AVIOCTRL_PTZ_UP = 1;
    public static final int AVIOCTRL_QUALITY_HIGH = 2;
    public static final int AVIOCTRL_QUALITY_LOW = 4;
    public static final int AVIOCTRL_QUALITY_MAX = 1;
    public static final int AVIOCTRL_QUALITY_MIDDLE = 5;
    public static final int AVIOCTRL_QUALITY_MIN = 3;
    public static final int AVIOCTRL_QUALITY_UNKNOWN = 0;
    public static final int AVIOCTRL_RECORD_PLAY_BACKWARD = 5;
    public static final int AVIOCTRL_RECORD_PLAY_END = 7;
    public static final int AVIOCTRL_RECORD_PLAY_FORWARD = 4;
    public static final int AVIOCTRL_RECORD_PLAY_PAUSE = 0;
    public static final int AVIOCTRL_RECORD_PLAY_RESUME = 17;
    public static final int AVIOCTRL_RECORD_PLAY_SEEKTIME = 6;
    public static final int AVIOCTRL_RECORD_PLAY_START = 16;
    public static final int AVIOCTRL_RECORD_PLAY_STEPBACKWARD = 3;
    public static final int AVIOCTRL_RECORD_PLAY_STEPFORWARD = 2;
    public static final int AVIOCTRL_RECORD_PLAY_STOP = 1;
    public static final int AVIOCTRL_SET_AUX = 33;
    public static final byte AVIOCTRL_THUMBNAIL = 2;
    public static final int AVIOCTRL_VIDEOMODE_FLIP = 1;
    public static final int AVIOCTRL_VIDEOMODE_FLIP_MIRROR = 3;
    public static final int AVIOCTRL_VIDEOMODE_MIRROR = 2;
    public static final int AVIOCTRL_VIDEOMODE_NORMAL = 0;
    public static final byte AVIOCTRL_VIDEO_FILE = 0;
    public static final byte AVIOCTRL_VIEW_FILE = 1;
    public static final int AVIOTC_RECORDTYPE_ALAM = 2;
    public static final int AVIOTC_RECORDTYPE_FULLTIME = 1;
    public static final int AVIOTC_RECORDTYPE_MANUAL = 3;
    public static final int AVIOTC_RECORDTYPE_OFF = 0;
    public static final int AVIOTC_WIFIAPENC_INVALID = 0;
    public static final int AVIOTC_WIFIAPENC_NONE = 1;
    public static final int AVIOTC_WIFIAPENC_WEP = 2;
    public static final int AVIOTC_WIFIAPENC_WPA2_AES = 6;
    public static final int AVIOTC_WIFIAPENC_WPA2_PSK_AES = 10;
    public static final int AVIOTC_WIFIAPENC_WPA2_PSK_TKIP = 9;
    public static final int AVIOTC_WIFIAPENC_WPA2_TKIP = 5;
    public static final int AVIOTC_WIFIAPENC_WPA_AES = 4;
    public static final int AVIOTC_WIFIAPENC_WPA_PSK_AES = 8;
    public static final int AVIOTC_WIFIAPENC_WPA_PSK_TKIP = 7;
    public static final int AVIOTC_WIFIAPENC_WPA_TKIP = 3;
    public static final int AVIOTC_WIFIAPMODE_ADHOC = 2;
    public static final int AVIOTC_WIFIAPMODE_MANAGED = 1;
    public static final int AVIOTC_WIFIAPMODE_NULL = 0;
    public static final int CMD_AVIO_CTRL_HEARTHEAT_REQ = 5156;
    public static final int CMD_AVIO_CTRL_HEARTHEAT_RESP = 5157;
    public static final int CMD_AVIO_CTRL_LIVE_PLAY_PAUSED_REQ = 5152;
    public static final int CMD_AVIO_CTRL_LIVE_PLAY_PAUSED_RESP = 5153;
    public static final int CMD_AVIO_CTRL_LIVE_PLAY_START_REQ = 5149;
    public static final int CMD_AVIO_CTRL_LIVE_PLAY_START_RESP = 5150;
    public static final int CMD_AVIO_CTRL_LIVE_PLAY_STOP_REQ = 5154;
    public static final int CMD_AVIO_CTRL_LIVE_PLAY_STOP_RESP = 5155;
    public static final int DEVICESLEEP_SUCCESS = 0;
    public static final int E_CMD_AVIO_CTRL_RT_RADAR_REPORT = 1324;
    public static final int E_CMD_AVIO_CTRL_RT_RADAR_SDCARD_START_REQ = 1326;
    public static final int E_CMD_AVIO_CTRL_RT_RADAR_SDCARD_START_RESP = 1327;
    public static final int E_CMD_AVIO_CTRL_RT_RADAR_START_REQ = 1320;
    public static final int E_CMD_AVIO_CTRL_RT_RADAR_START_RESP = 1321;
    public static final int E_CMD_AVIO_CTRL_RT_RADAR_STOP_REQ = 1322;
    public static final int E_CMD_AVIO_CTRL_RT_RADAR_STOP_RESP = 1323;
    public static final int E_CMD_AVIO_CTRL_SESSION_MODE_REQ = 5376;
    public static final int E_CMD_AVIO_CTRL_SESSION_MODE_RESP = 5377;
    public static final int IIOTYPE_USER_IPCAM_DELLISTEVENT_RESP = 1208;
    public static final int IIOTYPE_USER_TYPE_NOTIFY_TRACK_REQ = 804;
    public static final int IIOTYPE_USER_TYPE_NOTIFY_TRACK_RESP = 805;
    public static final int IOTYPE_BRIGHT_GETBRIGHT_REQ = 1538;
    public static final int IOTYPE_BRIGHT_GETBRIGHT_RESP = 1539;
    public static final int IOTYPE_BRIGHT_SETBRIGHT_REQ = 1540;
    public static final int IOTYPE_BRIGHT_SETBRIGHT_RESP = 1541;
    public static final int IOTYPE_CONTRAST_GETCONTRAST_REQ = 1542;
    public static final int IOTYPE_CONTRAST_GETCONTRAST_RESP = 1543;
    public static final int IOTYPE_CONTRAST_SETCONTRAST_REQ = 1544;
    public static final int IOTYPE_CONTRAST_SETCONTRAST_RESP = 1545;
    public static final int IOTYPE_CRUISEMODE_CRUISE_START = 1536;
    public static final int IOTYPE_CRUISEMODE_CRUISE_STOP = 1537;
    public static final int IOTYPE_PRESET_GETPRESET_REQ = 1090;
    public static final int IOTYPE_PRESET_GETPRESET_RESP = 1091;
    public static final int IOTYPE_PRESET_SETPRESET_REQ = 1088;
    public static final int IOTYPE_PRESET_SETPRESET_RESP = 1089;
    public static final int IOTYPE_USER_IPCAM_AUDIOSTART = 768;
    public static final int IOTYPE_USER_IPCAM_AUDIOSTOP = 769;
    public static final int IOTYPE_USER_IPCAM_CHECKSUPPORTMESSAGETYPE_RESP = 813;
    public static final int IOTYPE_USER_IPCAM_CONNECTION_CHECK_REQ = 496;
    public static final int IOTYPE_USER_IPCAM_CONNECTION_CHECK_RESP = 497;
    public static final int IOTYPE_USER_IPCAM_CURRENT_FLOWINFO = 914;
    public static final int IOTYPE_USER_IPCAM_CUSTOM_COMMAND_REQ = 1209;
    public static final int IOTYPE_USER_IPCAM_CUSTOM_COMMAND_RESP = 1210;
    public static final int IOTYPE_USER_IPCAM_DEVICESLEEP_REQ = 1824;
    public static final int IOTYPE_USER_IPCAM_DEVICESLEEP_RESP = 1825;
    public static final int IOTYPE_USER_IPCAM_DEVINFO_REQ = 816;
    public static final int IOTYPE_USER_IPCAM_DEVINFO_RESP = 817;
    public static final int IOTYPE_USER_IPCAM_DOWNLOAD_FILE_REQ = 2130706436;
    public static final int IOTYPE_USER_IPCAM_DOWNLOAD_FILE_RESP = 2130706437;
    public static final int IOTYPE_USER_IPCAM_EVENT_REPORT = 8191;
    public static final int IOTYPE_USER_IPCAM_FORMATEXTSTORAGE_REQ = 896;
    public static final int IOTYPE_USER_IPCAM_FORMATEXTSTORAGE_RESP = 897;
    public static final int IOTYPE_USER_IPCAM_GETGUARD_REQ = 1056;
    public static final int IOTYPE_USER_IPCAM_GETGUARD_RESP = 1057;
    public static final int IOTYPE_USER_IPCAM_GETMOTIONDETECT_REQ = 806;
    public static final int IOTYPE_USER_IPCAM_GETMOTIONDETECT_RESP = 807;
    public static final int IOTYPE_USER_IPCAM_GETRCD_DURATION_REQ = 790;
    public static final int IOTYPE_USER_IPCAM_GETRCD_DURATION_RESP = 791;
    public static final int IOTYPE_USER_IPCAM_GETRECORD_REQ = 786;
    public static final int IOTYPE_USER_IPCAM_GETRECORD_RESP = 787;
    public static final int IOTYPE_USER_IPCAM_GETSTREAMCTRL_REQ = 802;
    public static final int IOTYPE_USER_IPCAM_GETSTREAMCTRL_RESP = 803;
    public static final int IOTYPE_USER_IPCAM_GETWIFI_REQ = 836;
    public static final int IOTYPE_USER_IPCAM_GETWIFI_RESP = 837;
    public static final int IOTYPE_USER_IPCAM_GETWIFI_RESP_2 = 839;
    public static final int IOTYPE_USER_IPCAM_GET_BATTERY_REQ = 1546;
    public static final int IOTYPE_USER_IPCAM_GET_BATTERY_RESP = 1547;
    public static final int IOTYPE_USER_IPCAM_GET_CLOUDRECORD_REQ = 1842;
    public static final int IOTYPE_USER_IPCAM_GET_CLOUDRECORD_RESP = 1843;
    public static final int IOTYPE_USER_IPCAM_GET_ENVIRONMENT_REQ = 866;
    public static final int IOTYPE_USER_IPCAM_GET_ENVIRONMENT_RESP = 867;
    public static final int IOTYPE_USER_IPCAM_GET_EVENTCONFIG_REQ = 1024;
    public static final int IOTYPE_USER_IPCAM_GET_EVENTCONFIG_RESP = 1025;
    public static final int IOTYPE_USER_IPCAM_GET_FLOWINFO_REQ = 912;
    public static final int IOTYPE_USER_IPCAM_GET_FLOWINFO_RESP = 913;
    public static final int IOTYPE_USER_IPCAM_GET_FTP_REQ = 1372;
    public static final int IOTYPE_USER_IPCAM_GET_FTP_RESP = 1373;
    public static final int IOTYPE_USER_IPCAM_GET_LIVEVIEWTIMESTAMP_REQ = 2052;
    public static final int IOTYPE_USER_IPCAM_GET_LIVEVIEWTIMESTAMP_RESP = 2053;
    public static final int IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_REQ = 1280;
    public static final int IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_RESP = 1281;
    public static final int IOTYPE_USER_IPCAM_GET_SMTP_REQ = 16389;
    public static final int IOTYPE_USER_IPCAM_GET_SMTP_RESP = 16390;
    public static final int IOTYPE_USER_IPCAM_GET_TIMELAPSE_REQ = 1552;
    public static final int IOTYPE_USER_IPCAM_GET_TIMELAPSE_RESP = 1553;
    public static final int IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ = 928;
    public static final int IOTYPE_USER_IPCAM_GET_TIMEZONE_RESP = 929;
    public static final int IOTYPE_USER_IPCAM_GET_VIDEOMODE_REQ = 882;
    public static final int IOTYPE_USER_IPCAM_GET_VIDEOMODE_RESP = 883;
    public static final int IOTYPE_USER_IPCAM_GET_WDR_REQ = 1548;
    public static final int IOTYPE_USER_IPCAM_GET_WDR_RESP = 1549;
    public static final int IOTYPE_USER_IPCAM_GET_WiFiSIGNAL_REQ = 1840;
    public static final int IOTYPE_USER_IPCAM_GET_WiFiSIGNAL_RESP = 1841;
    public static final int IOTYPE_USER_IPCAM_HASLISTEVENT_REQ = 1205;
    public static final int IOTYPE_USER_IPCAM_HASLISTEVENT_RESP = 1206;
    public static final int IOTYPE_USER_IPCAM_HAS_SDCARD_REQ = 1203;
    public static final int IOTYPE_USER_IPCAM_HAS_SDCARD_RESP = 1204;
    public static final int IOTYPE_USER_IPCAM_LISTEVENT_REQ = 792;
    public static final int IOTYPE_USER_IPCAM_LISTEVENT_RESP = 793;
    public static final int IOTYPE_USER_IPCAM_LISTFILE_REQ = 796;
    public static final int IOTYPE_USER_IPCAM_LISTFILE_RESP = 797;
    public static final int IOTYPE_USER_IPCAM_LISTWIFIAP_REQ = 832;
    public static final int IOTYPE_USER_IPCAM_LISTWIFIAP_RESP = 833;
    public static final int IOTYPE_USER_IPCAM_PTZ_COMMAND = 4097;
    public static final int IOTYPE_USER_IPCAM_RECEIVE_FIRST_IFRAME = 4098;
    public static final int IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL = 794;
    public static final int IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL_RESP = 795;
    public static final int IOTYPE_USER_IPCAM_RECORD_TIMESTAMP_RESP = 796;
    public static final int IOTYPE_USER_IPCAM_REMOVE_FILE_REQ = 1556;
    public static final int IOTYPE_USER_IPCAM_REMOVE_FILE_RESP = 1557;
    public static final int IOTYPE_USER_IPCAM_SETGUARD_REQ = 1058;
    public static final int IOTYPE_USER_IPCAM_SETGUARD_RESP = 1059;
    public static final int IOTYPE_USER_IPCAM_SETMOTIONDETECT_REQ = 804;
    public static final int IOTYPE_USER_IPCAM_SETMOTIONDETECT_RESP = 805;
    public static final int IOTYPE_USER_IPCAM_SETPASSWORD_REQ = 818;
    public static final int IOTYPE_USER_IPCAM_SETPASSWORD_RESP = 819;
    public static final int IOTYPE_USER_IPCAM_SETRCD_DURATION_REQ = 788;
    public static final int IOTYPE_USER_IPCAM_SETRCD_DURATION_RESP = 789;
    public static final int IOTYPE_USER_IPCAM_SETRECORD_REQ = 784;
    public static final int IOTYPE_USER_IPCAM_SETRECORD_RESP = 785;
    public static final int IOTYPE_USER_IPCAM_SETSTREAMCTRL_REQ = 800;
    public static final int IOTYPE_USER_IPCAM_SETSTREAMCTRL_RESP = 801;
    public static final int IOTYPE_USER_IPCAM_SETWIFI_REQ = 834;
    public static final int IOTYPE_USER_IPCAM_SETWIFI_REQ_2 = 838;
    public static final int IOTYPE_USER_IPCAM_SETWIFI_RESP = 835;
    public static final int IOTYPE_USER_IPCAM_SET_CLOUDRECORD_REQ = 1844;
    public static final int IOTYPE_USER_IPCAM_SET_CLOUDRECORD_RESP = 1845;
    public static final int IOTYPE_USER_IPCAM_SET_ENVIRONMENT_REQ = 864;
    public static final int IOTYPE_USER_IPCAM_SET_ENVIRONMENT_RESP = 865;
    public static final int IOTYPE_USER_IPCAM_SET_EVENTCONFIG_REQ = 1026;
    public static final int IOTYPE_USER_IPCAM_SET_EVENTCONFIG_RESP = 1027;
    public static final int IOTYPE_USER_IPCAM_SET_FTP_REQ = 1370;
    public static final int IOTYPE_USER_IPCAM_SET_FTP_RESP = 1371;
    public static final int IOTYPE_USER_IPCAM_SET_LIVEVIEWTIMESTAMP_REQ = 2050;
    public static final int IOTYPE_USER_IPCAM_SET_LIVEVIEWTIMESTAMP_RESP = 2051;
    public static final int IOTYPE_USER_IPCAM_SET_SAVE_DROPBOX_REQ = 1282;
    public static final int IOTYPE_USER_IPCAM_SET_SAVE_DROPBOX_RESP = 1283;
    public static final int IOTYPE_USER_IPCAM_SET_SMTP_REQ = 16391;
    public static final int IOTYPE_USER_IPCAM_SET_SMTP_RESP = 16392;
    public static final int IOTYPE_USER_IPCAM_SET_TIMELAPSE_REQ = 1554;
    public static final int IOTYPE_USER_IPCAM_SET_TIMELAPSE_RESP = 1555;
    public static final int IOTYPE_USER_IPCAM_SET_TIMEZONE_REQ = 944;
    public static final int IOTYPE_USER_IPCAM_SET_TIMEZONE_RESP = 945;
    public static final int IOTYPE_USER_IPCAM_SET_VIDEOMODE_REQ = 880;
    public static final int IOTYPE_USER_IPCAM_SET_VIDEOMODE_RESP = 881;
    public static final int IOTYPE_USER_IPCAM_SET_VSAAS_PLAYBACK_INFO_REQ = 65536;
    public static final int IOTYPE_USER_IPCAM_SET_VSAAS_PLAYBACK_INFO_RESP = 65537;
    public static final int IOTYPE_USER_IPCAM_SET_WDR_REQ = 1550;
    public static final int IOTYPE_USER_IPCAM_SET_WDR_RESP = 1551;
    public static final int IOTYPE_USER_IPCAM_SPEAKERSTART = 848;
    public static final int IOTYPE_USER_IPCAM_SPEAKERSTART_RESP = 850;
    public static final int IOTYPE_USER_IPCAM_SPEAKERSTART_WEB_RTC_RESP = 851;
    public static final int IOTYPE_USER_IPCAM_SPEAKERSTOP = 849;
    public static final int IOTYPE_USER_IPCAM_START = 511;
    public static final int IOTYPE_USER_IPCAM_STARTFWUPGRADE_REQ = 2048;
    public static final int IOTYPE_USER_IPCAM_STARTFWUPGRADE_RESP = 2049;
    public static final int IOTYPE_USER_IPCAM_START_RESP = 512;
    public static final int IOTYPE_USER_IPCAM_STOP = 767;
    public static final int IOTYPE_USER_IPCAM_UPLOAD_FILE_REQ = 2130706438;
    public static final int IOTYPE_USER_IPCAM_VSAAS_RECORD_PLAYCONTROL_REQ = 65538;
    public static final int IOTYPE_USER_IPCAM_VSAAS_RECORD_PLAYCONTROL_RESP = 65539;
    public static final int IOTYPE_USER_SETAMBCROPSET_REQ = -16773116;
    public static final int IOTYPE_USER_SETAMBCROPSET_RESP = -16773115;
    public static final int IOTYPE_USER_SETCLIENTRECVREADY_REQ = 2130706434;
    public static final int IOTYPE_USER_SETCLIENTRECVREADY_RESP = 2130706435;
    public static final int IOTYPE_USER_WIFICMD_REQ = 2130706432;
    public static final int IOTYPE_USER_WIFICMD_RESP = 2130706433;
    public static final short MEDIA_CODEC_AUDIO_AAC = 136;
    public static final short MEDIA_CODEC_AUDIO_ADPCM = 139;
    public static final short MEDIA_CODEC_AUDIO_G711A = 138;
    public static final short MEDIA_CODEC_AUDIO_G711U = 137;
    public static final short MEDIA_CODEC_AUDIO_G726 = 143;
    public static final short MEDIA_CODEC_AUDIO_MP3 = 142;
    public static final short MEDIA_CODEC_AUDIO_PCM = 140;
    public static final short MEDIA_CODEC_AUDIO_SPEEX = 141;
    public static final short MEDIA_CODEC_UNKNOWN = 0;
    public static final short MEDIA_CODEC_VIDEO_H263 = 77;
    public static final short MEDIA_CODEC_VIDEO_H264 = 78;
    public static final short MEDIA_CODEC_VIDEO_HEVC = 80;
    public static final short MEDIA_CODEC_VIDEO_MJPEG = 79;
    public static final short MEDIA_CODEC_VIDEO_MPEG4 = 76;
    public static final int OTYPE_USER_IPCAM_DELLISTEVENT_REQ = 1207;
    public static final int TIME_LAPSE_10MIN = 7;
    public static final int TIME_LAPSE_10SEC = 3;
    public static final int TIME_LAPSE_1DAY = 12;
    public static final int TIME_LAPSE_1HR = 9;
    public static final int TIME_LAPSE_1MIN = 5;
    public static final int TIME_LAPSE_1SEC = 1;
    public static final int TIME_LAPSE_2HR = 10;
    public static final int TIME_LAPSE_30MIN = 8;
    public static final int TIME_LAPSE_30SEC = 4;
    public static final int TIME_LAPSE_3HR = 11;
    public static final int TIME_LAPSE_5MIN = 6;
    public static final int TIME_LAPSE_5SEC = 2;
    public static final int TIME_LAPSE_OFF = 0;

    public static class SMsgAVIoctrlAVStream {
        int channel = 0;
        byte[] reserved = new byte[4];

        public static byte[] parseContent(int channel2) {
            byte[] result = new byte[8];
            System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
            return result;
        }
    }

    public static class SFrameInfo {
        byte cam_index;
        short codec_id;
        byte flags;
        byte onlineNum;
        byte[] reserved = new byte[3];
        int reserved2;
        public int timestamp;

        public static byte[] parseContent(short codec_id2, byte flags2, byte cam_index2, byte online_num, int timestamp2) {
            byte[] result = new byte[16];
            System.arraycopy(Packet.shortToByteArray_Little(codec_id2), 0, result, 0, 2);
            result[2] = flags2;
            result[3] = cam_index2;
            result[4] = online_num;
            System.arraycopy(Packet.intToByteArray_Little(timestamp2), 0, result, 12, 4);
            return result;
        }

        public static byte[] parseLastContent(short codec_id2, byte flags2, byte cam_index2, byte online_num, int timestamp2) {
            byte[] result = new byte[16];
            System.arraycopy(Packet.shortToByteArray_Little(codec_id2), 0, result, 0, 2);
            result[2] = flags2;
            result[3] = cam_index2;
            result[4] = online_num;
            result[7] = 1;
            System.arraycopy(Packet.intToByteArray_Little(timestamp2), 0, result, 12, 4);
            return result;
        }
    }

    public static class SMsgAVIoctrlSetStreamCtrlReq {
        int channel;
        byte quality;
        byte[] reserved = new byte[3];

        public static byte[] parseContent(int channel2, byte quality2) {
            byte[] result = new byte[8];
            System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
            result[4] = quality2;
            return result;
        }
    }

    public static class SMsgAVIoctrlGetStreamCtrlReq {
        int channel;
        byte[] reserved = new byte[4];

        public static byte[] parseContent(int channel2) {
            byte[] result = new byte[8];
            System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
            return result;
        }
    }

    public static class SMsgAVIoctrlHasSDCardCtrlReq {
        int channel;
        byte[] reserved = new byte[4];

        public static byte[] parseContent(int channel2) {
            byte[] result = new byte[8];
            System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
            return result;
        }
    }

    public static class STimeDay {
        public byte day;
        public byte hour;
        private byte[] mBuf;
        public byte minute;
        public byte month;
        public byte second;
        public byte wday;
        public short year;

        public STimeDay(byte[] data) {
            byte[] bArr = new byte[8];
            this.mBuf = bArr;
            System.arraycopy(data, 0, bArr, 0, 8);
            this.year = Packet.byteArrayToShort_Little(data, 0);
            this.month = data[2];
            this.day = data[3];
            this.wday = data[4];
            this.hour = data[5];
            this.minute = data[6];
            this.second = data[7];
        }

        public long getTimeInMillis() {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
            cal.set(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
            cal.set(14, 0);
            return cal.getTimeInMillis();
        }

        public String getLocalTimeYearMonth() {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
            calendar.setTimeInMillis(getTimeInMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy");
            dateFormat.setTimeZone(TimeZone.getDefault());
            return dateFormat.format(calendar.getTime());
        }

        public String getLocalTimeSecond() {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
            calendar.setTimeInMillis(getTimeInMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            dateFormat.setTimeZone(TimeZone.getDefault());
            return dateFormat.format(calendar.getTime());
        }

        public String getLocalTime() {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
            calendar.setTimeInMillis(getTimeInMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.setTimeZone(TimeZone.getDefault());
            return dateFormat.format(calendar.getTime());
        }

        public byte[] toByteArray() {
            return this.mBuf;
        }

        public static byte[] parseContent(int year2, int month2, int day2, int wday2, int hour2, int minute2, int second2) {
            byte[] result = new byte[8];
            System.arraycopy(Packet.shortToByteArray_Little((short) year2), 0, result, 0, 2);
            result[2] = (byte) month2;
            result[3] = (byte) day2;
            result[4] = (byte) wday2;
            result[5] = (byte) hour2;
            result[6] = (byte) minute2;
            result[7] = (byte) second2;
            return result;
        }
    }

    public static class SMsgAVIoctrlHasListEventReq {
        int channel;
        byte[] endutctime = new byte[8];
        byte[] startutctime = new byte[8];

        public static byte[] parseConent(int channel2, long startTimeStamp, long endTimeStamp, int eventType) {
            byte[] result = new byte[22];
            try {
                Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
                try {
                    startCal.setTimeInMillis(startTimeStamp);
                    Calendar endCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
                    try {
                        endCal.setTimeInMillis(endTimeStamp);
                        System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
                        System.arraycopy(STimeDay.parseContent(startCal.get(1), startCal.get(2) + 1, startCal.get(5), startCal.get(7), startCal.get(11), startCal.get(12), startCal.get(13)), 0, result, 4, 8);
                        System.arraycopy(STimeDay.parseContent(endCal.get(1), endCal.get(2) + 1, endCal.get(5), endCal.get(7), endCal.get(11), endCal.get(12), endCal.get(13)), 0, result, 12, 8);
                        byte[] reversed = new byte[2];
                        try {
                            reversed[0] = (byte) eventType;
                            System.arraycopy(reversed, 0, result, 20, 2);
                        } catch (Exception e) {
                            e = e;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        int i = eventType;
                        e.printStackTrace();
                        return result;
                    }
                } catch (Exception e3) {
                    e = e3;
                    long j = endTimeStamp;
                    int i2 = eventType;
                    e.printStackTrace();
                    return result;
                }
            } catch (Exception e4) {
                e = e4;
                long j2 = startTimeStamp;
                long j3 = endTimeStamp;
                int i22 = eventType;
                e.printStackTrace();
                return result;
            }
            return result;
        }

        public static byte[] parseConent(int channel2, long startTimeStamp, long endTimeStamp, int eventType, int seq) {
            byte[] result = new byte[26];
            try {
                Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
                try {
                    startCal.setTimeInMillis(startTimeStamp);
                    Calendar endCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
                    try {
                        endCal.setTimeInMillis(endTimeStamp);
                        System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
                        System.arraycopy(STimeDay.parseContent(startCal.get(1), startCal.get(2) + 1, startCal.get(5), startCal.get(7), startCal.get(11), startCal.get(12), startCal.get(13)), 0, result, 4, 8);
                        System.arraycopy(STimeDay.parseContent(endCal.get(1), endCal.get(2) + 1, endCal.get(5), endCal.get(7), endCal.get(11), endCal.get(12), endCal.get(13)), 0, result, 12, 8);
                        byte[] reversed = new byte[2];
                        try {
                            reversed[0] = (byte) eventType;
                            System.arraycopy(reversed, 0, result, 20, 2);
                            System.arraycopy(Packet.intToByteArray_Little(seq), 0, result, 22, 4);
                        } catch (Exception e) {
                            e = e;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        int i = eventType;
                        e.printStackTrace();
                        return result;
                    }
                } catch (Exception e3) {
                    e = e3;
                    long j = endTimeStamp;
                    int i2 = eventType;
                    e.printStackTrace();
                    return result;
                }
            } catch (Exception e4) {
                e = e4;
                long j2 = startTimeStamp;
                long j3 = endTimeStamp;
                int i22 = eventType;
                e.printStackTrace();
                return result;
            }
            return result;
        }

        public static byte[] parseConent(int channel2, String startutctime2, String endutctime2, int eventType) {
            Calendar startCal;
            byte[] result = new byte[22];
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dateSta = sdf.parse(startutctime2);
                    startCal = Calendar.getInstance();
                    startCal.setTime(dateSta);
                } catch (Exception e) {
                    e = e;
                    String str = endutctime2;
                    int i = eventType;
                    e.printStackTrace();
                    return result;
                }
                try {
                    Date dateEnd = sdf.parse(endutctime2);
                    Calendar stopCal = Calendar.getInstance();
                    stopCal.setTime(dateEnd);
                    System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
                    System.arraycopy(STimeDay.parseContent(startCal.get(1), startCal.get(2) + 1, startCal.get(5), startCal.get(7), startCal.get(11), startCal.get(12), startCal.get(13)), 0, result, 4, 8);
                    System.arraycopy(STimeDay.parseContent(stopCal.get(1), stopCal.get(2) + 1, stopCal.get(5), stopCal.get(7), stopCal.get(11), stopCal.get(12), stopCal.get(13)), 0, result, 12, 8);
                    byte[] reversed = new byte[2];
                    try {
                        reversed[0] = (byte) eventType;
                        System.arraycopy(reversed, 0, result, 20, 2);
                    } catch (Exception e2) {
                        e = e2;
                    }
                } catch (Exception e3) {
                    e = e3;
                    int i2 = eventType;
                    e.printStackTrace();
                    return result;
                }
            } catch (Exception e4) {
                e = e4;
                String str2 = startutctime2;
                String str3 = endutctime2;
                int i22 = eventType;
                e.printStackTrace();
                return result;
            }
            return result;
        }
    }

    public static class SMsgAVIoctrlListEventReq {
        int channel;
        byte[] endutctime = new byte[8];
        byte event;
        byte[] startutctime = new byte[8];
        byte status;

        public static byte[] parseConent(int channel2, long startutctime2, long endutctime2, byte event2, byte status2) {
            byte[] result = new byte[24];
            try {
                Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
                try {
                    startCal.setTimeInMillis(startutctime2);
                    Calendar stopCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
                    try {
                        stopCal.setTimeInMillis(endutctime2);
                        PrintStream printStream = System.out;
                        printStream.println("====search from " + startCal.get(1) + "/" + (startCal.get(2) + 1) + "/" + startCal.get(5) + " " + startCal.get(11) + ":" + startCal.get(12) + ":" + startCal.get(13));
                        PrintStream printStream2 = System.out;
                        printStream2.println("====       to   " + stopCal.get(1) + "/" + (startCal.get(2) + 1) + "/" + stopCal.get(5) + " " + stopCal.get(11) + ":" + stopCal.get(12) + ":" + stopCal.get(13));
                        System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
                        System.arraycopy(STimeDay.parseContent(startCal.get(1), startCal.get(2) + 1, startCal.get(5), startCal.get(7), startCal.get(11), startCal.get(12), startCal.get(13)), 0, result, 4, 8);
                        System.arraycopy(STimeDay.parseContent(stopCal.get(1), stopCal.get(2) + 1, stopCal.get(5), stopCal.get(7), stopCal.get(11), stopCal.get(12), stopCal.get(13)), 0, result, 12, 8);
                        result[20] = event2;
                        result[21] = status2;
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Exception e2) {
                    e = e2;
                    long j = endutctime2;
                    e.printStackTrace();
                    return result;
                }
            } catch (Exception e3) {
                e = e3;
                long j2 = startutctime2;
                long j3 = endutctime2;
                e.printStackTrace();
                return result;
            }
            return result;
        }

        public static byte[] parseConent(int channel2, String startutctime2, String endutctime2, byte event2, byte status2) {
            byte[] result = new byte[24];
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dateSta = sdf.parse(startutctime2);
                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(dateSta);
                    try {
                        Date dateEnd = sdf.parse(endutctime2);
                        Calendar stopCal = Calendar.getInstance();
                        stopCal.setTime(dateEnd);
                        System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
                        System.arraycopy(STimeDay.parseContent(startCal.get(1), startCal.get(2) + 1, startCal.get(5), startCal.get(7), startCal.get(11), startCal.get(12), startCal.get(13)), 0, result, 4, 8);
                        System.arraycopy(STimeDay.parseContent(stopCal.get(1), stopCal.get(2) + 1, stopCal.get(5), stopCal.get(7), stopCal.get(11), stopCal.get(12), stopCal.get(13)), 0, result, 12, 8);
                        result[20] = event2;
                        result[21] = status2;
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Exception e2) {
                    e = e2;
                    String str = endutctime2;
                    e.printStackTrace();
                    return result;
                }
            } catch (Exception e3) {
                e = e3;
                String str2 = startutctime2;
                String str3 = endutctime2;
                e.printStackTrace();
                return result;
            }
            return result;
        }

        public static byte[] parseConent(int channel2, long startutctime2, long endutctime2, byte event2, byte status2, int getSDRecordListSeq) {
            byte[] result = new byte[28];
            try {
                Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
                try {
                    startCal.setTimeInMillis(startutctime2);
                    Calendar stopCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
                    try {
                        stopCal.setTimeInMillis(endutctime2);
                        PrintStream printStream = System.out;
                        printStream.println("====search from " + startCal.get(1) + "/" + (startCal.get(2) + 1) + "/" + startCal.get(5) + " " + startCal.get(11) + ":" + startCal.get(12) + ":" + startCal.get(13));
                        PrintStream printStream2 = System.out;
                        printStream2.println("====       to   " + stopCal.get(1) + "/" + (startCal.get(2) + 1) + "/" + stopCal.get(5) + " " + stopCal.get(11) + ":" + stopCal.get(12) + ":" + stopCal.get(13));
                        System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
                        System.arraycopy(STimeDay.parseContent(startCal.get(1), startCal.get(2) + 1, startCal.get(5), startCal.get(7), startCal.get(11), startCal.get(12), startCal.get(13)), 0, result, 4, 8);
                        System.arraycopy(STimeDay.parseContent(stopCal.get(1), stopCal.get(2) + 1, stopCal.get(5), stopCal.get(7), stopCal.get(11), stopCal.get(12), stopCal.get(13)), 0, result, 12, 8);
                        result[20] = event2;
                        result[21] = status2;
                        System.arraycopy(Packet.intToByteArray_Little(getSDRecordListSeq), 0, result, 24, 4);
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Exception e2) {
                    e = e2;
                    long j = endutctime2;
                    e.printStackTrace();
                    return result;
                }
            } catch (Exception e3) {
                e = e3;
                long j2 = startutctime2;
                long j3 = endutctime2;
                e.printStackTrace();
                return result;
            }
            return result;
        }
    }

    public static class SMsgAVIoctrlDelListEventReq {
        public static byte[] parseConent(int channel, int total, byte index, byte endFlag, byte count, byte delType, List<Long> times, int eventType) {
            byte[] result = new byte[((count * 10) + 14)];
            byte[] ch = Packet.intToByteArray_Little(channel);
            System.arraycopy(ch, 0, result, 0, 4);
            System.arraycopy(Packet.intToByteArray_Little(total), 0, result, 4, 4);
            int i = 8;
            System.arraycopy(new byte[]{index}, 0, result, 8, 1);
            System.arraycopy(new byte[]{endFlag}, 0, result, 9, 1);
            System.arraycopy(new byte[]{count}, 0, result, 10, 1);
            System.arraycopy(new byte[]{delType}, 0, result, 11, 1);
            byte[] reserved = new byte[2];
            reserved[0] = (byte) eventType;
            System.arraycopy(reserved, 0, result, 12, 2);
            int i2 = 0;
            while (i2 < total) {
                System.arraycopy(AVIOCTRLDEFs.parseSTimeDay(times.get(i2).longValue()), 0, result, (i2 * 10) + 14, i);
                System.arraycopy(Packet.shortToByteArray_Little((short) i2), 0, result, (i2 * 10) + 22, 2);
                i2++;
                ch = ch;
                i = 8;
            }
            return result;
        }
    }

    /* access modifiers changed from: private */
    public static byte[] parseSTimeDay(long time) {
        Calendar timeCal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
        timeCal.setTimeInMillis(time);
        return parseDeleteContent(timeCal.get(1), timeCal.get(2) + 1, timeCal.get(5), timeCal.get(7), timeCal.get(11), timeCal.get(12), timeCal.get(13));
    }

    private static byte[] parseDeleteContent(int year, int month, int day, int wday, int hour, int minute, int second) {
        byte[] result = new byte[8];
        System.arraycopy(Packet.shortToByteArray_Little((short) year), 0, result, 0, 2);
        result[2] = (byte) month;
        result[3] = (byte) day;
        result[4] = (byte) wday;
        result[5] = (byte) hour;
        result[6] = (byte) minute;
        result[7] = (byte) second;
        return result;
    }

    public static class SMsgAVIoctrlPlayRecord {
        int Param;
        int channel;
        int command;
        byte[] reserved = new byte[4];
        byte[] stTimeDay = new byte[8];

        public static byte[] parseContent(int channel2, int command2, int seekFlag, int freeChannelId, long time, int seekTime, int type) {
            byte[] result = new byte[24];
            System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
            System.arraycopy(Packet.intToByteArray_Little(command2), 0, result, 4, 4);
            System.arraycopy(Packet.intToByteArray_Little(seekFlag), 0, result, 8, 4);
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
            cal.setTimeInMillis(time);
            byte[] timedata = STimeDay.parseContent(cal.get(1), cal.get(2) + 1, cal.get(5), cal.get(7), cal.get(11), cal.get(12), cal.get(13));
            PrintStream printStream = System.out;
            printStream.println("====startPlay Time " + cal.get(1) + "/" + (cal.get(2) + 1) + "/" + cal.get(5) + " " + cal.get(11) + ":" + cal.get(12) + ":" + cal.get(13));
            System.arraycopy(timedata, 0, result, 12, 8);
            byte[] reserved2 = new byte[4];
            reserved2[0] = (byte) freeChannelId;
            reserved2[1] = (byte) type;
            reserved2[2] = (byte) seekTime;
            System.arraycopy(reserved2, 0, result, 20, 4);
            return result;
        }

        public static byte[] parseContent(int channel2, int command2, int param, byte[] time) {
            byte[] result = new byte[24];
            System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
            System.arraycopy(Packet.intToByteArray_Little(command2), 0, result, 4, 4);
            System.arraycopy(Packet.intToByteArray_Little(param), 0, result, 8, 4);
            System.arraycopy(time, 0, result, 12, 8);
            return result;
        }
    }

    public static class SAVStreamRealTimeInfo {
        public int bufDelay = 0;
        public int bufTime = 0;
        public long curVideoTime;
        public int decFps = 0;
        public int decTime = 0;
        public int downFps = 0;
        public int encoderFps;
        public int showFps = 0;
        public int speed = 0;
        public int type = 0;

        public SAVStreamRealTimeInfo(byte[] dataByte) {
            this.downFps = ByteUtil.byte2int(ByteUtil.subBytes(dataByte, 4, 4));
            this.decFps = ByteUtil.byte2int(ByteUtil.subBytes(dataByte, 8, 4));
            this.showFps = ByteUtil.byte2int(ByteUtil.subBytes(dataByte, 12, 4));
            this.encoderFps = ByteUtil.byte2int(ByteUtil.subBytes(dataByte, 16, 4));
            this.decTime = ByteUtil.byte2int(ByteUtil.subBytes(dataByte, 20, 4));
            this.bufTime = ByteUtil.byte2int(ByteUtil.subBytes(dataByte, 24, 4));
            this.bufDelay = ByteUtil.byte2int(ByteUtil.subBytes(dataByte, 28, 4));
            this.speed = ByteUtil.byte2int(ByteUtil.subBytes(dataByte, 32, 4));
            this.curVideoTime = ByteUtil.byte2long(ByteUtil.subBytes(dataByte, 40, 8));
        }

        public String TimeStamp2Date(long timestampString) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(timestampString).longValue()));
        }

        public void print() {
            PrintStream printStream = System.out;
            printStream.println("lsd player down fps=" + this.downFps + ", dec fps=" + this.decFps + ", show fps=" + this.showFps + ", dec time=" + this.decTime + ",bufTime=" + this.bufTime + ",speed=" + this.speed + ",curTime=" + TimeStamp2Date(this.curVideoTime));
        }

        public String testPrint() {
            return "lsd player down fps=" + this.downFps + ", dec fps=" + this.decFps + ", show fps=" + this.showFps + ", dec time=" + this.decTime + ",bufTime=" + this.bufTime + ",speed=" + this.speed + ",curTime=" + TimeStamp2Date(this.curVideoTime);
        }
    }

    public static class SMsgAVIoctrlPtzCmd {
        byte aux;
        byte channel;
        byte control;
        byte limit;
        byte point;
        byte[] reserved = new byte[2];
        byte speed;

        public static byte[] parseContent(byte control2, byte speed2, byte point2, byte limit2, byte aux2, byte channel2) {
            byte[] result = new byte[8];
            result[0] = control2;
            result[1] = speed2;
            result[2] = point2;
            result[3] = limit2;
            result[4] = aux2;
            result[5] = channel2;
            return result;
        }
    }

    public static class AVIO_CTRLCmd {
        int channel;
        byte mode;
        byte[] reserved = new byte[3];

        public static byte[] parseContent(int channel2, int mode2) {
            byte[] result = new byte[8];
            System.arraycopy(Packet.intToByteArray_Little(channel2), 0, result, 0, 4);
            result[4] = (byte) mode2;
            return result;
        }
    }
}
