package org.glassfish.grizzly.http.server.util;

import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.util.DataChunk;

public interface DispatcherHelper {
    void mapName(DataChunk dataChunk, MappingData mappingData);

    void mapPath(HttpRequestPacket httpRequestPacket, DataChunk dataChunk, MappingData mappingData);
}
