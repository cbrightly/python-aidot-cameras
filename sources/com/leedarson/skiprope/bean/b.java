package com.leedarson.skiprope.bean;

import com.leedarson.base.utils.l;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: VoiceEnum */
public enum b {
    zero("0.pcm"),
    one("1.pcm"),
    two("2.pcm"),
    three("3.pcm"),
    four("4.pcm"),
    five("5.pcm"),
    six("6.pcm"),
    seven("7.pcm"),
    eight("8.pcm"),
    nine("9.pcm"),
    ten("10.pcm"),
    eleven("11.pcm"),
    twelve("12.pcm"),
    thirteen("13.pcm"),
    fourteen("14.pcm"),
    fifteen("15.pcm"),
    sixteen("16.pcm"),
    seventeen("17.pcm"),
    eighteen("18.pcm"),
    nineteen("19.pcm"),
    twenty("20.pcm"),
    thirty("30.pcm"),
    forty("40.pcm"),
    fifty("50.pcm"),
    sixty("60.pcm"),
    seventy("70.pcm"),
    eighty("80.pcm"),
    ninety("90.pcm"),
    and("and.pcm"),
    ExcellentYouJumped("ExcellentYouJumped.pcm"),
    FinishYouJumped("FinishYouJumped.pcm"),
    GetReadyForJumping("GetReadyForJumping.pcm"),
    GoOnJumping("GoOnJumping.pcm"),
    hundred("hundred.pcm"),
    KeepTryingYouJumped("KeepTryingYouJumped.pcm"),
    minute("minute.pcm"),
    remain("remain.pcm"),
    Rest("Rest.pcm"),
    second("second.pcm"),
    StartJumping("StartJumping.pcm"),
    thousand("thousand.pcm"),
    times("times.pcm"),
    tingdun("tingdun.pcm"),
    TrainingFinished("TrainingFinished.pcm"),
    YouJumped("YouJumped.pcm");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    public String fileName;

    private b(String fileName2) {
        this.fileName = fileName2;
    }

    public static b findItem(String fileName2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{fileName2}, (Object) null, changeQuickRedirect, true, 9507, new Class[]{String.class}, b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        for (b item : values()) {
            if (l.b(item.fileName).equals(fileName2)) {
                return item;
            }
        }
        return null;
    }
}
