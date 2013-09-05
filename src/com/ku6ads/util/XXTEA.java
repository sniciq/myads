package com.ku6ads.util;



import java.net.URLDecoder;
import java.util.Date;


/*
 * Project :	ku6ads
 * Author:	zhangyan
 * Company: 	ku6
 * Created Date:2010-12-23
 * Copyright @ 2010 ku6�C Confidential and Proprietary
 * Description:
 */
public class XXTEA {
	private XXTEA() {
	}

	public static void main(String[] args) {
//		Calendar c = Calendar.getInstance();
//		String endday = CommonFuns.DateToString(c.getTime(), "yyyy-MM-dd");
//		c.set(Calendar.DAY_OF_MONTH, 1);
//		String startday = CommonFuns.DateToString(c.getTime(), "yyyy-MM-dd");
//		System.out.println(startday + "  " + endday);
//		System.out.println(230727%16);
//		System.out.println(XXTEA.encryptVid("3060057"));
//		String dekey="QRHpa2ziDxS5rwhsqTJ7m4I8XSnIw3RMR0pMZlKIUQecW-QrQsCwBJHHN63EAHgRA5QjgA..";
//		   String key=XXTEA.decryptVid(dekey);
//		   System.out.println(key);
//		   long time=System.currentTimeMillis();
//		   String[] keys=key.split("-");103000-13229552-3279050-1-0-0-1215667522177
//		   116000-14378014-3279050-492-1-1-1215670821499
//		   String keysub=StringUtils.substringBeforeLast(key, "-");
//		   String newkey=keysub+"-"+keys[2]+"-"+time;
//		   System.out.println(newkey);
//		// key="102000-15368158-1666555-2-1-0-1666555-1213949467914";
//		System.out.println("OIESK7WsHkrX87x9u6esb3E7MOyHzbN8IOF8deH2se95eMKRC-FXfJIYk6RUAjd-G5b6rQ..".length());
//		   String enkey=XXTEA.encryptVid("34137859");
//		   System.out.println(enkey);
//		   enkey=XXTEA.encryptVid("34138084");
//		   System.out.println(enkey);
//		   enkey=XXTEA.encryptVid("34137847");
//		   System.out.println(enkey);
//		   enkey=XXTEA.encryptVid("34137803");
//		   System.out.println(enkey);
//		   enkey=XXTEA.encryptVid("34153085");
//		   System.out.println(enkey);
//		   enkey=XXTEA.encryptVid("34153121");
//		   System.out.println(enkey);
//		   enkey=XXTEA.encryptVid("34153180");
//		   System.out.println(enkey);
//		   enkey=XXTEA.encryptVid("34153184");
//		   System.out.println(enkey);
//		   enkey=XXTEA.encryptVid("34153207");
//		   System.out.println(enkey);
//		   enkey=XXTEA.encryptVid("34153223");
//		   System.out.println(enkey);
//		   enkey=XXTEA.encryptVid("34153228");
//		   System.out.println(enkey);
//		   String[] arr = enkey.split("-");
//		   System.out.println(arr);
		String str = URLDecoder.decode("http\u003a//stat3\u002e888\u002eku6\u002ecom/dostat\u002edo\u003fm\u003ddostat\u0026p\u003dmsyPLvfoS2t5a24SCtaZlvifflvOZmw22ubKxWcgibBxekK83ZpUyYNv_Ygynzlm");
		System.out.println(str);
		   System.out.println(XXTEA.decryptVid("SoFt05H-IWamcLL_PCZNWurU7XwEx4Kt5cUiIFKW1Sp9hp99VJrO6jXWLFTPLRMt"));
		   System.out.println(new Date(1274122305886L));
//		 Calendar c = Calendar.getInstance();
//		 c.set(Calendar.YEAR, 2007);
//		 c.set(Calendar.MONTH, 9);
//		 c.set(Calendar.DATE, 1);
//		 // cid-vid-uid-����-ԭ��-�id-��ǰʱ���
//		 System.out.println(c.getTimeInMillis() / 1000);
	}

	/**
	 * ��XXXTEA���ܣ���Base64����
	 * 
	 * @param vid
	 * @return
	 */
	public static String encryptVid(String deVid) {
		String enVid = "";
		byte[] k = "!@#^~ku6&%(com)T".getBytes();
		// byte[] k = "KU_6com-2007web2.0".getBytes();
		byte[] v = deVid.getBytes();
		enVid = new String(Base64.encode(XXTEA.encrypt(v, k)));
		enVid = enVid.replace('+', '-');
		enVid = enVid.replace('/', '_');
		enVid = enVid.replace('=', '.');
		return enVid;
	}

	/**
	 * ��Base64���ܣ���XXXTEA����
	 * 
	 * @param enVid
	 * @return
	 */
	public static String decryptVid(String enVid) {
		String deVid = "";
		enVid = enVid.replace('-', '+');
		enVid = enVid.replace('_', '/');
		enVid = enVid.replace('.', '=');
		byte[] k = "!@#^~ku6&%(com)T".getBytes();
		// byte[] k = "KU_6com-2007web2.0".getBytes();
		byte[] v = Base64.decode(enVid);
		deVid = new String(XXTEA.decrypt(v, k));
		return deVid;
	}

	/**
	 * Encrypt data with key.
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] encrypt(byte[] data, byte[] key) {
		if (data.length == 0) {
			return data;
		}
		return toByteArray(encrypt(toIntArray(data, true), toIntArray(key, false)), false);
	}

	/**
	 * Decrypt data with key.
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] decrypt(byte[] data, byte[] key) {
		if (data.length == 0) {
			return data;
		}
		return toByteArray(decrypt(toIntArray(data, false), toIntArray(key, false)), true);
	}

	/**
	 * Encrypt data with key.
	 * 
	 * @param v
	 * @param k
	 * @return
	 */
	public static int[] encrypt(int[] v, int[] k) {
		int n = v.length - 1;

		if (n < 1) {
			return v;
		}
		if (k.length < 4) {
			int[] key = new int[4];

			System.arraycopy(k, 0, key, 0, k.length);
			k = key;
		}
		int z = v[n], y = v[0], delta = 0x9E3779B9, sum = 0, e;
		int p, q = 6 + 52 / (n + 1);

		while (q-- > 0) {
			sum = sum + delta;
			e = sum >>> 2 & 3;
			for (p = 0; p < n; p++) {
				y = v[p + 1];
				z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
			}
			y = v[0];
			z = v[n] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
		}
		return v;
	}

	/**
	 * Decrypt data with key.
	 * 
	 * @param v
	 * @param k
	 * @return
	 */
	public static int[] decrypt(int[] v, int[] k) {
		int n = v.length - 1;

		if (n < 1) {
			return v;
		}
		if (k.length < 4) {
			int[] key = new int[4];

			System.arraycopy(k, 0, key, 0, k.length);
			k = key;
		}
		int z = v[n], y = v[0], delta = 0x9E3779B9, sum, e;
		int p, q = 6 + 52 / (n + 1);

		sum = q * delta;
		while (sum != 0) {
			e = sum >>> 2 & 3;
			for (p = n; p > 0; p--) {
				z = v[p - 1];
				y = v[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
			}
			z = v[n];
			y = v[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
			sum = sum - delta;
		}
		return v;
	}

	/**
	 * Convert byte array to int array.
	 * 
	 * @param data
	 * @param includeLength
	 * @return
	 */
	private static int[] toIntArray(byte[] data, boolean includeLength) {
		int n = (((data.length & 3) == 0) ? (data.length >>> 2) : ((data.length >>> 2) + 1));
		int[] result;

		if (includeLength) {
			result = new int[n + 1];
			result[n] = data.length;
		} else {
			result = new int[n];
		}
		n = data.length;
		for (int i = 0; i < n; i++) {
			result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
		}
		return result;
	}

	/**
	 * Convert int array to byte array.
	 * 
	 * @param data
	 * @param includeLength
	 * @return
	 */
	private static byte[] toByteArray(int[] data, boolean includeLength) {
		int n = data.length << 2;

		;
		if (includeLength) {
			int m = data[data.length - 1];

			if (m > n) {
				return null;
			} else {
				n = m;
			}
		}
		byte[] result = new byte[n];

		for (int i = 0; i < n; i++) {
			result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
		}
		return result;
	}
}

