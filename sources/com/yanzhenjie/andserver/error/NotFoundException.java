package com.yanzhenjie.andserver.error;

public class NotFoundException extends HttpException {
    public NotFoundException() {
        super(404, String.format("The resource [%s] is not found.", new Object[]{""}));
    }

    public NotFoundException(String path) {
        super(404, String.format("The resource [%s] is not found.", new Object[]{path}));
    }

    public NotFoundException(String path, Throwable cause) {
        super(404, String.format("The resource [%s] is not found.", new Object[]{path}), cause);
    }

    public NotFoundException(Throwable cause) {
        super(404, String.format("The resource [%s] is not found.", new Object[]{""}), cause);
    }
}
