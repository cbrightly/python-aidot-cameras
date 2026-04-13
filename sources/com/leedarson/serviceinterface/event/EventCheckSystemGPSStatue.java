package com.leedarson.serviceinterface.event;

public class EventCheckSystemGPSStatue {
    public IOnCheckSystemCheckStatueResultCallback mHandler;

    public interface IOnCheckSystemCheckStatueResultCallback {
        void onGPSStatueCallBack(int i);
    }
}
