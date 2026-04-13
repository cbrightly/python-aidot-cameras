package com.sensorsdata.analytics.android.sdk.advert.oaid.impl;

import android.app.Application;
import android.content.Context;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.advert.oaid.IRomOAID;
import com.sensorsdata.analytics.android.sdk.advert.oaid.OAIDRom;

public final class OAIDFactory {
    private static final String TAG = "SA.OAIDFactory";
    private static IRomOAID ioaid;

    public static IRomOAID create(Context context) {
        if (context != null && !(context instanceof Application)) {
            context = context.getApplicationContext();
        }
        IRomOAID iRomOAID = ioaid;
        if (iRomOAID != null) {
            return iRomOAID;
        }
        IRomOAID createManufacturerImpl = createManufacturerImpl(context);
        ioaid = createManufacturerImpl;
        if (createManufacturerImpl == null || !createManufacturerImpl.isSupported()) {
            DefaultImpl defaultImpl = new DefaultImpl();
            ioaid = defaultImpl;
            return defaultImpl;
        }
        SALog.i(TAG, "Manufacturer interface has been found: " + ioaid.getClass().getName());
        return ioaid;
    }

    private static IRomOAID createManufacturerImpl(Context context) {
        if (OAIDRom.isHuawei() || OAIDRom.isEmui()) {
            return new HuaweiImpl(context);
        }
        if (OAIDRom.isXiaomi() || OAIDRom.isMiui() || OAIDRom.isBlackShark()) {
            return new XiaomiImpl(context);
        }
        if (OAIDRom.isVivo()) {
            return new VivoImpl(context);
        }
        if (OAIDRom.isOppo() || OAIDRom.isOnePlus()) {
            return new OppoImpl(context);
        }
        if (OAIDRom.isMeizu()) {
            return new MeizuImpl(context);
        }
        if (OAIDRom.isSamsung()) {
            return new SamsungImpl(context);
        }
        if (OAIDRom.isNubia()) {
            return new NubiaImpl(context);
        }
        if (OAIDRom.isASUS()) {
            return new AsusImpl(context);
        }
        if (OAIDRom.isLenovo() || OAIDRom.isMotolora()) {
            return new LenovoImpl(context);
        }
        if (OAIDRom.isZTE()) {
            return new ZTEImpl(context);
        }
        if (OAIDRom.isCoolpad(context)) {
            return new CoolpadImpl(context);
        }
        return null;
    }
}
