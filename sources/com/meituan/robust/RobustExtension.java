package com.meituan.robust;

public interface RobustExtension {
    Object accessDispatch(RobustArguments robustArguments);

    String describeSelfFunction();

    boolean isSupport(RobustArguments robustArguments);

    void notifyListner(String str);
}
