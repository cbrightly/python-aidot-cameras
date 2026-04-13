package com.meituan.robust;

public class RobustArguments {
    public Object current;
    public boolean isStatic;
    public int methodNumber;
    public Object[] paramsArray;
    public Class[] paramsClassTypes;
    public Class returnType;

    public RobustArguments(Object[] paramsArray2, Object current2, boolean isStatic2, int methodNumber2, Class[] paramsClassTypes2, Class returnType2) {
        this.paramsArray = paramsArray2;
        this.current = current2;
        this.isStatic = isStatic2;
        this.methodNumber = methodNumber2;
        this.paramsClassTypes = paramsClassTypes2;
        this.returnType = returnType2;
    }
}
