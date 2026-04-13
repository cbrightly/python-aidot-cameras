package com.sensorsdata.analytics.android.sdk.internal.beans;

public enum EventType {
    TRACK("track", true, false),
    TRACK_SIGNUP("track_signup", true, false),
    TRACK_ID_BIND("track_id_bind", true, false),
    TRACK_ID_UNBIND("track_id_unbind", true, false),
    PROFILE_SET("profile_set", false, true),
    PROFILE_SET_ONCE("profile_set_once", false, true),
    PROFILE_UNSET("profile_unset", false, true),
    PROFILE_INCREMENT("profile_increment", false, true),
    PROFILE_APPEND("profile_append", false, true),
    PROFILE_DELETE("profile_delete", false, true),
    ITEM_SET("item_set", false, false),
    ITEM_DELETE("item_delete", false, false),
    DEFAULT("default", false, false),
    ALL("all", false, false);
    
    private String eventType;
    private boolean profile;
    private boolean track;

    private EventType(String eventType2, boolean isTrack, boolean isProfile) {
        this.eventType = eventType2;
        this.track = isTrack;
        this.profile = isProfile;
    }

    public String getEventType() {
        return this.eventType;
    }

    public boolean isTrack() {
        return this.track;
    }

    public boolean isProfile() {
        return this.profile;
    }
}
