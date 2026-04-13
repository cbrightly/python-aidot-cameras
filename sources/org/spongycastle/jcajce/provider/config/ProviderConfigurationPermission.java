package org.spongycastle.jcajce.provider.config;

import java.security.BasicPermission;
import java.security.Permission;
import java.util.StringTokenizer;
import org.spongycastle.util.Strings;

public class ProviderConfigurationPermission extends BasicPermission {
    private final String actions;
    private final int permissionMask;

    public ProviderConfigurationPermission(String name) {
        super(name);
        this.actions = "all";
        this.permissionMask = 63;
    }

    public ProviderConfigurationPermission(String name, String actions2) {
        super(name, actions2);
        this.actions = actions2;
        this.permissionMask = a(actions2);
    }

    private int a(String actions2) {
        StringTokenizer tok = new StringTokenizer(Strings.h(actions2), " ,");
        int mask = 0;
        while (tok.hasMoreTokens()) {
            String s = tok.nextToken();
            if (s.equals("threadlocalecimplicitlyca")) {
                mask |= 1;
            } else if (s.equals("ecimplicitlyca")) {
                mask |= 2;
            } else if (s.equals("threadlocaldhdefaultparams")) {
                mask |= 4;
            } else if (s.equals("dhdefaultparams")) {
                mask |= 8;
            } else if (s.equals("acceptableeccurves")) {
                mask |= 16;
            } else if (s.equals("additionalecparameters")) {
                mask |= 32;
            } else if (s.equals("all")) {
                mask |= 63;
            }
        }
        if (mask != 0) {
            return mask;
        }
        throw new IllegalArgumentException("unknown permissions passed to mask");
    }

    public String getActions() {
        return this.actions;
    }

    public boolean implies(Permission permission) {
        if (!(permission instanceof ProviderConfigurationPermission) || !getName().equals(permission.getName())) {
            return false;
        }
        int i = this.permissionMask;
        int i2 = ((ProviderConfigurationPermission) permission).permissionMask;
        if ((i & i2) == i2) {
            return true;
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ProviderConfigurationPermission)) {
            return false;
        }
        ProviderConfigurationPermission other = (ProviderConfigurationPermission) obj;
        if (this.permissionMask != other.permissionMask || !getName().equals(other.getName())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return getName().hashCode() + this.permissionMask;
    }
}
