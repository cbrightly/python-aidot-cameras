package org.glassfish.grizzly.streams;

import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.utils.ResultAware;
import org.glassfish.grizzly.utils.conditions.Condition;

public class StreamDecodeCondition<E> implements Condition {
    private final Transformer<Stream, E> decoder;
    private final ResultAware<E> resultAware;
    private final StreamReader streamReader;

    public StreamDecodeCondition(StreamReader streamReader2, Transformer<Stream, E> decoder2, ResultAware<E> resultAware2) {
        this.streamReader = streamReader2;
        this.decoder = decoder2;
        this.resultAware = resultAware2;
    }

    public boolean check() {
        TransformationResult<Stream, E> result = this.decoder.transform(this.streamReader.getConnection(), this.streamReader);
        TransformationResult.Status status = result.getStatus();
        if (status == TransformationResult.Status.COMPLETE) {
            this.resultAware.setResult(result.getMessage());
            return true;
        } else if (status == TransformationResult.Status.INCOMPLETE) {
            return false;
        } else {
            throw new TransformationException(result.getErrorCode() + ": " + result.getErrorDescription());
        }
    }
}
