package org.glassfish.grizzly.nio.transport;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Interceptor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.Reader;
import org.glassfish.grizzly.streams.AbstractStreamReader;
import org.glassfish.grizzly.streams.BufferedInput;

public final class DefaultStreamReader extends AbstractStreamReader {
    public DefaultStreamReader(Connection connection) {
        super(connection, new Input());
        DefaultStreamReader unused = ((Input) this.input).parentStreamReader = this;
    }

    public Input getSource() {
        return (Input) this.input;
    }

    public static final class Input extends BufferedInput {
        private InputInterceptor interceptor;
        /* access modifiers changed from: private */
        public DefaultStreamReader parentStreamReader;

        /* access modifiers changed from: protected */
        public void onOpenInputSource() {
            Connection connection = this.parentStreamReader.getConnection();
            Reader reader = connection.getTransport().getReader(connection);
            InputInterceptor inputInterceptor = new InputInterceptor();
            this.interceptor = inputInterceptor;
            reader.read(connection, (Buffer) null, (CompletionHandler) null, inputInterceptor);
        }

        /* access modifiers changed from: protected */
        public void onCloseInputSource() {
            this.interceptor.isDone = true;
            this.interceptor = null;
        }

        /* access modifiers changed from: protected */
        public void notifyCompleted(CompletionHandler<Integer> completionHandler) {
            if (completionHandler != null) {
                completionHandler.completed(Integer.valueOf(this.compositeBuffer.remaining()));
            }
        }

        /* access modifiers changed from: protected */
        public void notifyFailure(CompletionHandler<Integer> completionHandler, Throwable failure) {
            if (completionHandler != null) {
                completionHandler.failed(failure);
            }
        }

        public class InputInterceptor implements Interceptor<ReadResult<Buffer, ?>> {
            boolean isDone;

            private InputInterceptor() {
                this.isDone = false;
            }

            public int intercept(int event, Object context, ReadResult<Buffer, ?> result) {
                if (event != 1) {
                    return 0;
                }
                Buffer buffer = result.getMessage();
                result.setMessage(null);
                if (buffer == null) {
                    return 2;
                }
                buffer.trim();
                Input.this.append(buffer);
                if (this.isDone) {
                    return 1;
                }
                return 6;
            }
        }
    }
}
