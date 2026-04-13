package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.d;

public class MathParseException extends MathIllegalStateException {
    private static final long serialVersionUID = -6024911025449780478L;

    public MathParseException(String wrong, int position, Class<?> type) {
        getContext().addMessage(d.CANNOT_PARSE_AS_TYPE, wrong, Integer.valueOf(position), type.getName());
    }

    public MathParseException(String wrong, int position) {
        getContext().addMessage(d.CANNOT_PARSE, wrong, Integer.valueOf(position));
    }
}
