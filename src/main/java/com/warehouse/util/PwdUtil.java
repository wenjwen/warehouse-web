package com.warehouse.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 密码加密
 * @author liaodg
 *
 */
public class PwdUtil {

	public static String md5Encryption(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().toLowerCase();
            System.out.println("MD5(" + sourceStr + ",32) = " + result);
            System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }
	public static String md5Encryption(String sourceStr,int flag) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().toLowerCase();
            System.out.println("MD5(" + sourceStr + ",32) = " + result);
            System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
            if(flag==16){
            	result	=buf.toString().substring(8, 24);
            }
          
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }
	public static void main(String args[]){
		PwdUtil.md5Encryption("12345");
	}
	
	public static String  trimNull(String str){
		if(str==null||"null".equals(str)||"".equals(str)){
			return "";
		}else{
			return str.trim();
		}
		 
		 
	}
	
}
