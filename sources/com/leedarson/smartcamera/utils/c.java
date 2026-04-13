package com.leedarson.smartcamera.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: H264Utils */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int a = 0;

    public int d(byte[] pBuff) {
        int i;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{pBuff}, this, changeQuickRedirect, false, 10473, new Class[]{byte[].class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int nZeroNum = 0;
        while (true) {
            i = this.a;
            if (i >= pBuff.length * 8 || (pBuff[i / 8] & (128 >> (i % 8))) != 0) {
                this.a = i + 1;
            } else {
                nZeroNum++;
                this.a = i + 1;
            }
        }
        this.a = i + 1;
        return ((1 << nZeroNum) - 1) + c(nZeroNum, pBuff);
    }

    public int c(int bitIndex, byte[] pBuff) {
        int dwRet = 0;
        for (int i = 0; i < bitIndex; i++) {
            dwRet <<= 1;
            int i2 = this.a;
            if ((pBuff[i2 / 8] & (128 >> (i2 % 8))) != 0) {
                dwRet++;
            }
            this.a = i2 + 1;
        }
        return dwRet;
    }

    public int a(byte[] pBuff) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{pBuff}, this, changeQuickRedirect, false, 10474, new Class[]{byte[].class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int UeVal = d(pBuff);
        int nValue = (int) Math.ceil(((double) UeVal) / 2.0d);
        if (UeVal % 2 == 0) {
            return -nValue;
        }
        return nValue;
    }

    public int b(byte[] bArr) {
        int frameRate;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bArr}, this, changeQuickRedirect, false, 10477, new Class[]{byte[].class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        byte[] buf = bArr;
        this.a = 32;
        int frameRate2 = 0;
        int c = c(1, buf);
        int c2 = c(2, buf);
        if (c(5, buf) == 7) {
            int profile_idc = c(8, buf);
            int c3 = c(1, buf);
            int c4 = c(1, buf);
            int c5 = c(1, buf);
            int c6 = c(1, buf);
            int c7 = c(4, buf);
            int c8 = c(8, buf);
            int d = d(buf);
            if (profile_idc == 100 || profile_idc == 110 || profile_idc == 122 || profile_idc == 144) {
                int chroma_format_idc = d(buf);
                if (chroma_format_idc == 3) {
                    c(1, buf);
                }
                int d2 = d(buf);
                int d3 = d(buf);
                int c9 = c(1, buf);
                int i = chroma_format_idc;
                int[] seq_scaling_list_present_flag = new int[8];
                if (c(1, buf) == 1) {
                    int i2 = 0;
                    while (true) {
                        frameRate = frameRate2;
                        if (i2 >= 8) {
                            break;
                        }
                        seq_scaling_list_present_flag[i2] = c(1, buf);
                        i2++;
                        frameRate2 = frameRate;
                    }
                } else {
                    frameRate = 0;
                }
            } else {
                frameRate = 0;
            }
            int log2_max_frame_num_minus4 = d(buf);
            int pic_order_cnt_type = d(buf);
            if (pic_order_cnt_type == 0) {
                d(buf);
            } else if (pic_order_cnt_type == 1) {
                int c10 = c(1, buf);
                int a2 = a(buf);
                int a3 = a(buf);
                d(buf);
            }
            int d4 = d(buf);
            int c11 = c(1, buf);
            int i3 = log2_max_frame_num_minus4;
            int d5 = (d(buf) + 1) * 16;
            int d6 = (d(buf) + 1) * 16;
            int i4 = pic_order_cnt_type;
            int pic_order_cnt_type2 = c(1, buf);
            if (pic_order_cnt_type2 != 1) {
                c(1, buf);
            }
            int c12 = c(1, buf);
            int i5 = pic_order_cnt_type2;
            int frame_cropping_flag = c(1, buf);
            if (frame_cropping_flag == 1) {
                int d7 = d(buf);
                int d8 = d(buf);
                int d9 = d(buf);
                d(buf);
            }
            int frame_crop_right_offset = frame_cropping_flag;
            int frame_cropping_flag2 = c(1, buf);
            if (frame_cropping_flag2 == 1) {
                int i6 = frame_cropping_flag2;
                int vui_parameter_present_flag = c(1, buf);
                if (vui_parameter_present_flag == 1) {
                    int i7 = vui_parameter_present_flag;
                    if (c(8, buf) == 255) {
                        int c13 = c(16, buf);
                        c(16, buf);
                    }
                } else {
                    int aspect_ratio_info_present_flag = vui_parameter_present_flag;
                }
                int overscan_info_present_flag = c(1, buf);
                if (overscan_info_present_flag == 1) {
                    c(1, buf);
                }
                int i8 = overscan_info_present_flag;
                int video_signal_type_present_flag = c(1, buf);
                if (video_signal_type_present_flag == 1) {
                    int c14 = c(3, buf);
                    int c15 = c(1, buf);
                    int i9 = video_signal_type_present_flag;
                    if (c(1, buf) == 1) {
                        int c16 = c(8, buf);
                        int c17 = c(8, buf);
                        c(8, buf);
                    }
                }
                int chroma_loc_info_present_flag = c(1, buf);
                if (chroma_loc_info_present_flag == 1) {
                    int d10 = d(buf);
                    d(buf);
                }
                int chroma_sample_loc_type_top_field = chroma_loc_info_present_flag;
                if (c(1, buf) == 1) {
                    return (c(32, buf) / 2) * c(32, buf);
                }
            } else {
                int vui_parameter_present_flag2 = frame_cropping_flag2;
            }
        } else {
            frameRate = 0;
        }
        return frameRate;
    }
}
