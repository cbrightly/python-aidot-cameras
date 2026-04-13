package com.leedarson.mqtt.beans;

import android.content.Context;

public class LdsMqttConfigOptionBean {
    public String _clientId;
    public Context _context;
    public String _password;
    public String _serverUrl = "";
    public boolean _useSsl = false;
    public String _userName;
    public String _willLeaveMsg;
    public String _willLeaveTopic = "";
}
