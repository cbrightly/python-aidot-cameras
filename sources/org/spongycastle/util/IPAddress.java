package org.spongycastle.util;

public class IPAddress {
    public static boolean b(String address) {
        if (address.length() == 0) {
            return false;
        }
        int octets = 0;
        String temp = address + ".";
        int start = 0;
        while (start < temp.length()) {
            int indexOf = temp.indexOf(46, start);
            int pos = indexOf;
            if (indexOf <= start) {
                break;
            } else if (octets == 4) {
                return false;
            } else {
                try {
                    int octet = Integer.parseInt(temp.substring(start, pos));
                    if (octet < 0 || octet > 255) {
                        return false;
                    }
                    start = pos + 1;
                    octets++;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        if (octets == 4) {
            return true;
        }
        return false;
    }

    public static boolean c(String address) {
        int index = address.indexOf("/");
        String mask = address.substring(index + 1);
        if (index <= 0 || !b(address.substring(0, index))) {
            return false;
        }
        if (b(mask) || a(mask, 32)) {
            return true;
        }
        return false;
    }

    public static boolean e(String address) {
        int index = address.indexOf("/");
        String mask = address.substring(index + 1);
        if (index <= 0 || !d(address.substring(0, index))) {
            return false;
        }
        if (d(mask) || a(mask, 128)) {
            return true;
        }
        return false;
    }

    private static boolean a(String component, int size) {
        try {
            int value = Integer.parseInt(component);
            if (value < 0 || value > size) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean d(String address) {
        if (address.length() == 0) {
            return false;
        }
        int octets = 0;
        String temp = address + ":";
        boolean doubleColonFound = false;
        int start = 0;
        while (start < temp.length()) {
            int indexOf = temp.indexOf(58, start);
            int pos = indexOf;
            if (indexOf < start) {
                break;
            } else if (octets == 8) {
                return false;
            } else {
                if (start != pos) {
                    String value = temp.substring(start, pos);
                    if (pos != temp.length() - 1 || value.indexOf(46) <= 0) {
                        try {
                            int octet = Integer.parseInt(temp.substring(start, pos), 16);
                            if (octet < 0 || octet > 65535) {
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    } else if (!b(value)) {
                        return false;
                    } else {
                        octets++;
                    }
                } else if (pos != 1 && pos != temp.length() - 1 && doubleColonFound) {
                    return false;
                } else {
                    doubleColonFound = true;
                }
                start = pos + 1;
                octets++;
            }
        }
        if (octets == 8 || doubleColonFound) {
            return true;
        }
        return false;
    }
}
