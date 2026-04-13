package org.greenrobot.greendao.async;

import org.greenrobot.greendao.DaoException;

public class AsyncDaoException extends DaoException {
    private static final long serialVersionUID = 5872157552005102382L;
    private final AsyncOperation failedOperation;

    public AsyncDaoException(AsyncOperation failedOperation2, Throwable cause) {
        super(cause);
        this.failedOperation = failedOperation2;
    }

    public AsyncOperation getFailedOperation() {
        return this.failedOperation;
    }
}
