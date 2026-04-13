package com.tutk.IOTC;

public class TUTKGlobalAPIs {
    public static final int TUTK_ER_ALREADY_INITIALIZED = -1001;
    public static final int TUTK_ER_INVALID_ARG = -1002;
    public static final int TUTK_ER_INVALID_LICENSE_KEY = -1004;
    public static final int TUTK_ER_MEM_INSUFFICIENT = -1003;
    public static final int TUTK_ER_NoERROR = 0;

    public static native int TUTK_SDK_Set_License_Key(String str);
}
