package com.manage.tn.auv.util;

import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static android.support.constraint.Constraints.TAG;

/**
 * 基于Modbus CRC16的校验算法工具类
 */
public class Bcc16Util {

    /**
     * @desc 1.0~1之间的BigDecimal小数，格式化后失去前面的0,则前面直接加上0。
     * 2.传入的参数等于0，则直接返回字符串"0.00"
     * 3.大于1的小数，直接格式化返回字符串
     * @param obj传入的小数
     * @return
     */
    public static String formatToNumber(BigDecimal obj) {
        DecimalFormat df = new DecimalFormat("#.00");
        if(obj.compareTo(BigDecimal.ZERO)==0) {
            return "0.00";
        }else if(obj.compareTo(BigDecimal.ZERO)>0&&obj.compareTo(new BigDecimal(1))<0){
            return "0"+df.format(obj).toString();
        }else {
            return df.format(obj).toString();
        }
    }
    /**
     * 获取源数据和验证码的组合byte数组
     * @param strings 可变长度的十六进制字符串
     * @return
     */
    public static byte[] getData(String...strings) {
        byte[] data = new byte[]{};
        for (int i = 0; i<strings.length;i++) {
            int x = Integer.parseInt(strings[i], 16);
            byte n = (byte)x;
            byte[] buffer = new byte[data.length+1];
            byte[] aa = {n};
            System.arraycopy( data,0,buffer,0,data.length);
            System.arraycopy( aa,0,buffer,data.length,aa.length);
            data = buffer;
        }
        return getData(data);
    }
    /**
     * 获取源数据和验证码的组合byte数组
     * @param aa 字节数组
     * @return
     */
    private static byte[] getData(byte[] aa) {
        byte[] bb = getBCC(aa);
        byte[] cc = new byte[aa.length+bb.length];
        System.arraycopy(aa,0,cc,0,aa.length);
        System.arraycopy(bb,0,cc,aa.length,bb.length);
        return cc;
    }

    /**
     * 获取验证码byte数组，基于Modbus BCC的校验算法
     * @param arg7
     * @return
     */
    private static byte[] getBCC(byte[] arg7) {
        String v0="";
        byte[] v2=new byte[1];
        int v3;
        for (v3=0; v3 < arg7.length; ++v3) {
            v2[0]=((byte) (v2[0] ^ arg7[v3]));
        }

        String v3_1=Integer.toHexString(v2[0] & 255);
        if (v3_1.length() == 1) {
            v3_1='0' + v3_1;
        }

        v0=v0 + v3_1.toUpperCase();
        Log.i(TAG,"BCC验证:" + v0);
        return  toBytes(v0);
    }
    /**
     * 获取验证码byte数组，基于Modbus CRC16的校验算法
     */
    private static byte[] getCrc16(byte[] arr_buff) {
        int len = arr_buff.length;

        // 预置 1 个 16 位的寄存器为十六进制FFFF, 称此寄存器为 CRC寄存器。
        int crc = 0xFFFF;
        int i, j;
        for (i = 0; i < len; i++) {
            // 把第一个 8 位二进制数据 与 16 位的 CRC寄存器的低 8 位相异或, 把结果放于 CRC寄存器
            crc = ((crc & 0xFF00) | (crc & 0x00FF) ^ (arr_buff[i] & 0xFF));
            for (j = 0; j < 8; j++) {
                // 把 CRC 寄存器的内容右移一位( 朝低位)用 0 填补最高位, 并检查右移后的移出位
                if ((crc & 0x0001) > 0) {
                    // 如果移出位为 1, CRC寄存器与多项式A001进行异或
                    crc = crc >> 1;
                    crc = crc ^ 0xA001;
                } else
                    // 如果移出位为 0,再次右移一位
                    crc = crc >> 1;
            }
        }
        return intToBytes(crc);
    }
    /**
     * 将int转换成byte数组，低位在前，高位在后
     * 改变高低位顺序只需调换数组序号
     */
    private static byte[] intToBytes(int value)  {
        byte[] src = new byte[2];
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }
    /**
     * 将字节数组转换成十六进制字符串
     */
    public static String byteTo16String(byte[] data) {
        StringBuffer buffer = new StringBuffer();
        for (byte b : data) {
            buffer.append(byteTo16String(b));
        }
        return buffer.toString();
    }
    /**
     * 将字节转换成十六进制字符串
     * int转byte对照表
     * [128,255],0,[1,128)
     * [-128,-1],0,[1,128)
     */
    public static String byteTo16String(byte b) {
        StringBuffer buffer = new StringBuffer();
        int aa = (int)b;
        if (aa<0) {
            buffer.append(Integer.toString(aa+256, 16)+" ");
        }else if (aa==0) {
            buffer.append("00 ");
        }else if (aa>0 && aa<=15) {
            buffer.append("0"+Integer.toString(aa, 16)+" ");
        }else if (aa>15) {
            buffer.append(Integer.toString(aa, 16)+" ");
        }
        return buffer.toString();
    }
    /**

     * 将16进制字符串转换为byte[]

     *

     * @param str

     * @return

     */

    public static byte[] toBytes(String str) {

        if(str == null || str.trim().equals("")) {

            return new byte[0];

        }



        byte[] bytes = new byte[str.length() / 2];

        for(int i = 0; i < str.length() / 2; i++) {

            String subStr = str.substring(i * 2, i * 2 + 2);

            bytes[i] = (byte) Integer.parseInt(subStr, 16);

        }



        return bytes;

    }
}