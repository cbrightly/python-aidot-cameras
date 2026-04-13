package com.leedarson.serviceimpl.reporters.beans;

import com.leedarson.log.tracker.BaseStepBean;

public class MatterConfigStepBeans extends BaseStepBean {
    public MatterConfigStepBeans(String step, int code) {
        super(step, code);
    }

    public MatterConfigStepBeans(String step, int code, long duration) {
        super(step, code, duration);
    }
}
