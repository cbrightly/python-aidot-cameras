package org.glassfish.grizzly;

public class ProcessorResult {
    private static final ProcessorResult COMPLETE_RESULT = new ProcessorResult(Status.COMPLETE, (Object) null);
    private static final ProcessorResult ERROR_RESULT = new ProcessorResult(Status.ERROR, (Object) null);
    private static final ProcessorResult LEAVE_RESULT = new ProcessorResult(Status.LEAVE, (Object) null);
    private static final ProcessorResult NOT_RUN_RESULT = new ProcessorResult(Status.NOT_RUN, (Object) null);
    private static final ProcessorResult REREGISTER_RESULT = new ProcessorResult(Status.REREGISTER, (Object) null);
    private static final ProcessorResult TERMINATE_RESULT = new ProcessorResult(Status.TERMINATE, (Object) null);
    private Object data;
    private Status status;

    public enum Status {
        COMPLETE,
        LEAVE,
        REREGISTER,
        RERUN,
        ERROR,
        TERMINATE,
        NOT_RUN
    }

    private static ProcessorResult create() {
        return new ProcessorResult();
    }

    public static ProcessorResult createComplete() {
        return COMPLETE_RESULT;
    }

    public static ProcessorResult createComplete(Object data2) {
        return create().setStatus(Status.COMPLETE).setData(data2);
    }

    public static ProcessorResult createLeave() {
        return LEAVE_RESULT;
    }

    public static ProcessorResult createReregister(Context context) {
        return create().setStatus(Status.REREGISTER).setData(context);
    }

    public static ProcessorResult createError() {
        return ERROR_RESULT;
    }

    public static ProcessorResult createError(Object description) {
        return create().setStatus(Status.ERROR).setData(description);
    }

    public static ProcessorResult createRerun(Context context) {
        return create().setStatus(Status.RERUN).setData(context);
    }

    public static ProcessorResult createTerminate() {
        return TERMINATE_RESULT;
    }

    public static ProcessorResult createNotRun() {
        return NOT_RUN_RESULT;
    }

    private ProcessorResult() {
        this((Status) null, (Object) null);
    }

    private ProcessorResult(Status status2) {
        this(status2, (Object) null);
    }

    private ProcessorResult(Status status2, Object context) {
        this.status = status2;
        this.data = context;
    }

    public Status getStatus() {
        return this.status;
    }

    /* access modifiers changed from: protected */
    public ProcessorResult setStatus(Status status2) {
        this.status = status2;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    /* access modifiers changed from: protected */
    public ProcessorResult setData(Object context) {
        this.data = context;
        return this;
    }
}
