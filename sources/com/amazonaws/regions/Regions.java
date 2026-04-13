package com.amazonaws.regions;

public enum Regions {
    GovCloud("us-gov-west-1"),
    US_GOV_EAST_1("us-gov-east-1"),
    US_EAST_1("us-east-1"),
    US_EAST_2("us-east-2"),
    US_WEST_1("us-west-1"),
    US_WEST_2("us-west-2"),
    EU_WEST_1("eu-west-1"),
    EU_WEST_2("eu-west-2"),
    EU_WEST_3("eu-west-3"),
    EU_CENTRAL_1("eu-central-1"),
    EU_NORTH_1("eu-north-1"),
    AP_EAST_1("ap-east-1"),
    AP_SOUTH_1("ap-south-1"),
    AP_SOUTHEAST_1("ap-southeast-1"),
    AP_SOUTHEAST_2("ap-southeast-2"),
    AP_NORTHEAST_1("ap-northeast-1"),
    AP_NORTHEAST_2("ap-northeast-2"),
    SA_EAST_1("sa-east-1"),
    CA_CENTRAL_1("ca-central-1"),
    CN_NORTH_1("cn-north-1"),
    CN_NORTHWEST_1("cn-northwest-1"),
    ME_SOUTH_1("me-south-1");
    
    public static final Regions DEFAULT_REGION = null;
    private final String name;

    static {
        Regions regions;
        DEFAULT_REGION = regions;
    }

    private Regions(String name2) {
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }

    public static Regions fromName(String regionName) {
        for (Regions region : values()) {
            if (regionName.equals(region.getName())) {
                return region;
            }
        }
        throw new IllegalArgumentException("Cannot create enum from " + regionName + " value!");
    }
}
