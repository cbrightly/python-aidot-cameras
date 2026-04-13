package org.glassfish.grizzly;

import org.glassfish.grizzly.ThreadCache;

public class TransformationResult<I, O> implements Cacheable {
    private static final ThreadCache.CachedTypeIndex<TransformationResult> CACHE_IDX = ThreadCache.obtainIndex(TransformationResult.class, 2);
    private int errorCode;
    private String errorDescription;
    private I externalRemainder;
    private O message;
    private Status status;

    public enum Status {
        COMPLETE,
        INCOMPLETE,
        ERROR
    }

    public static <I, O> TransformationResult<I, O> createErrorResult(int errorCode2, String errorDescription2) {
        return create(Status.ERROR, (Object) null, (Object) null, errorCode2, errorDescription2);
    }

    public static <I, O> TransformationResult<I, O> createCompletedResult(O message2, I externalRemainder2) {
        return create(Status.COMPLETE, message2, externalRemainder2, 0, (String) null);
    }

    public static <I, O> TransformationResult<I, O> createIncompletedResult(I externalRemainder2) {
        return create(Status.INCOMPLETE, (Object) null, externalRemainder2, 0, (String) null);
    }

    private static <I, O> TransformationResult<I, O> create(Status status2, O message2, I externalRemainder2, int errorCode2, String errorDescription2) {
        TransformationResult<I, O> result = (TransformationResult) ThreadCache.takeFromCache(CACHE_IDX);
        if (result == null) {
            return new TransformationResult(status2, message2, externalRemainder2, errorCode2, errorDescription2);
        }
        result.setStatus(status2);
        result.setMessage(message2);
        result.setExternalRemainder(externalRemainder2);
        result.setErrorCode(errorCode2);
        result.setErrorDescription(errorDescription2);
        return result;
    }

    public TransformationResult() {
        this(Status.COMPLETE, (Object) null, (Object) null);
    }

    public TransformationResult(Status status2, O message2, I externalRemainder2) {
        this.status = status2;
        this.message = message2;
        this.externalRemainder = externalRemainder2;
    }

    public TransformationResult(int errorCode2, String errorDescription2) {
        this.status = Status.ERROR;
        this.errorCode = errorCode2;
        this.errorDescription = errorDescription2;
    }

    protected TransformationResult(Status status2, O message2, I externalRemainder2, int errorCode2, String errorDescription2) {
        this.status = status2;
        this.message = message2;
        this.externalRemainder = externalRemainder2;
        this.errorCode = errorCode2;
        this.errorDescription = errorDescription2;
    }

    public O getMessage() {
        return this.message;
    }

    public void setMessage(O message2) {
        this.message = message2;
    }

    public I getExternalRemainder() {
        return this.externalRemainder;
    }

    public void setExternalRemainder(I externalRemainder2) {
        this.externalRemainder = externalRemainder2;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status2) {
        this.status = status2;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode2) {
        this.errorCode = errorCode2;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    public void setErrorDescription(String errorDescription2) {
        this.errorDescription = errorDescription2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("Transformation result. Status: ");
        sb.append(this.status);
        sb.append(" message: ");
        sb.append(this.message);
        if (this.status == Status.ERROR) {
            sb.append(" errorCode: ");
            sb.append(this.errorCode);
            sb.append(" errorDescription: ");
            sb.append(this.errorDescription);
        }
        return sb.toString();
    }

    public void reset() {
        this.message = null;
        this.status = null;
        this.errorCode = 0;
        this.errorDescription = null;
        this.externalRemainder = null;
    }

    public void recycle() {
        reset();
        ThreadCache.putToCache(CACHE_IDX, this);
    }
}
