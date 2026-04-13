package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;

public final class HttpProbeNotifier {
    HttpProbeNotifier() {
    }

    static void notifyDataReceived(HttpCodecFilter httpFilter, Connection connection, Buffer buffer) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onDataReceivedEvent(connection, buffer);
            }
        }
    }

    static void notifyDataSent(HttpCodecFilter httpFilter, Connection connection, Buffer buffer) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onDataSentEvent(connection, buffer);
            }
        }
    }

    static void notifyHeaderParse(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, int size) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onHeaderParseEvent(connection, header, size);
            }
        }
    }

    static void notifyHeaderSerialize(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer buffer) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onHeaderSerializeEvent(connection, header, buffer);
            }
        }
    }

    static void notifyContentChunkParse(HttpCodecFilter httpFilter, Connection connection, HttpContent content) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onContentChunkParseEvent(connection, content);
            }
        }
    }

    static void notifyContentChunkSerialize(HttpCodecFilter httpFilter, Connection connection, HttpContent content) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onContentChunkSerializeEvent(connection, content);
            }
        }
    }

    static void notifyContentEncodingParse(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer buffer, ContentEncoding contentEncoding) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onContentEncodingParseEvent(connection, header, buffer, contentEncoding);
            }
        }
    }

    static void notifyContentEncodingParseResult(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer result, ContentEncoding contentEncoding) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onContentEncodingSerializeResultEvent(connection, header, result, contentEncoding);
            }
        }
    }

    static void notifyContentEncodingSerialize(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer buffer, ContentEncoding contentEncoding) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onContentEncodingSerializeEvent(connection, header, buffer, contentEncoding);
            }
        }
    }

    static void notifyContentEncodingSerializeResult(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer result, ContentEncoding contentEncoding) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onContentEncodingSerializeResultEvent(connection, header, result, contentEncoding);
            }
        }
    }

    static void notifyTransferEncodingParse(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer buffer, TransferEncoding transferEncoding) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onTransferEncodingParseEvent(connection, header, buffer, transferEncoding);
            }
        }
    }

    static void notifyTransferEncodingSerialize(HttpCodecFilter httpFilter, Connection connection, HttpHeader header, Buffer buffer, TransferEncoding transferEncoding) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (HttpProbe probe : probes) {
                probe.onTransferEncodingSerializeEvent(connection, header, buffer, transferEncoding);
            }
        }
    }

    static void notifyProbesError(HttpCodecFilter httpFilter, Connection connection, HttpPacket httpPacket, Throwable error) {
        HttpProbe[] probes = (HttpProbe[]) httpFilter.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            if (error == null) {
                error = new IllegalStateException("Error in HTTP semantics");
            }
            for (HttpProbe probe : probes) {
                probe.onErrorEvent(connection, httpPacket, error);
            }
        }
    }
}
