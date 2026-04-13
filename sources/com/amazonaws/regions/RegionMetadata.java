package com.amazonaws.regions;

import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RegionMetadata {
    private final List<Region> regions;

    public RegionMetadata(List<Region> regions2) {
        if (regions2 != null) {
            this.regions = Collections.unmodifiableList(new ArrayList(regions2));
            return;
        }
        throw new IllegalArgumentException("regions cannot be null");
    }

    public List<Region> getRegions() {
        return this.regions;
    }

    public Region getRegion(String name) {
        for (Region region : this.regions) {
            if (region.getName().equals(name)) {
                return region;
            }
        }
        return null;
    }

    public List<Region> getRegionsForService(String service) {
        List<Region> results = new LinkedList<>();
        for (Region region : this.regions) {
            if (region.isServiceSupported(service)) {
                results.add(region);
            }
        }
        return results;
    }

    public Region getRegionByEndpoint(String endpoint) {
        String host = getHost(endpoint);
        for (Region region : this.regions) {
            Iterator<String> it = region.getServiceEndpoints().values().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (host.equals(getHost(it.next()))) {
                        return region;
                    }
                }
            }
        }
        throw new IllegalArgumentException("No region found with any service for endpoint " + endpoint);
    }

    private static String getHost(String endpoint) {
        String host = URI.create(endpoint).getHost();
        if (host != null) {
            return host;
        }
        return URI.create(NetworkManager.MOCK_SCHEME_HTTP + endpoint).getHost();
    }

    public String toString() {
        return this.regions.toString();
    }
}
