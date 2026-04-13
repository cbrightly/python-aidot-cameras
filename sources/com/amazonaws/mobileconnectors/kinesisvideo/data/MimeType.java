package com.amazonaws.mobileconnectors.kinesisvideo.data;

import tv.danmaku.ijk.media.player.misc.IjkMediaFormat;

public enum MimeType {
    H264(IjkMediaFormat.CODEC_NAME_H264, "video/avc"),
    H265("h265", "video/hevc"),
    VP8("vp8", "video/x-vnd.on2.vp8"),
    VP9("vp9", "video/x-vnd.on2.vp9");
    
    private final String mDescription;
    private final String mMimeType;

    private MimeType(String description, String mimeType) {
        this.mDescription = description;
        this.mMimeType = mimeType;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String toString() {
        return this.mDescription + " (" + this.mMimeType + ")";
    }
}
