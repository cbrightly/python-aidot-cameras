package com.google.android.gms.internal.clearcut;

public final class zzet {
    static String zzc(zzbb zzbb) {
        String str;
        zzeu zzeu = new zzeu(zzbb);
        StringBuilder sb = new StringBuilder(zzeu.size());
        for (int i = 0; i < zzeu.size(); i++) {
            int zzj = zzeu.zzj(i);
            switch (zzj) {
                case 7:
                    str = "\\a";
                    break;
                case 8:
                    str = "\\b";
                    break;
                case 9:
                    str = "\\t";
                    break;
                case 10:
                    str = "\\n";
                    break;
                case 11:
                    str = "\\v";
                    break;
                case 12:
                    str = "\\f";
                    break;
                case 13:
                    str = "\\r";
                    break;
                case 34:
                    str = "\\\"";
                    break;
                case 39:
                    str = "\\'";
                    break;
                case 92:
                    str = "\\\\";
                    break;
                default:
                    if (zzj < 32 || zzj > 126) {
                        sb.append('\\');
                        sb.append((char) (((zzj >>> 6) & 3) + 48));
                        sb.append((char) (((zzj >>> 3) & 7) + 48));
                        zzj = (zzj & 7) + 48;
                    }
                    sb.append((char) zzj);
                    continue;
            }
            sb.append(str);
        }
        return sb.toString();
    }
}
