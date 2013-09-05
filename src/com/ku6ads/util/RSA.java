package com.ku6ads.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;

/**
 * RSA��ȫ�������
 *
 * @author z��
 * @version 1.0
 * @since 1.0
 */
public class RSA {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * base64����
     */
    public static byte[] decryptBASE64(String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * base64����
     */
    public static String encryptBASE64(byte [] input) {
        return  Base64.encodeBase64String(input);
    }

    /**
     * ��˽Կ����Ϣ�������ǩ��
     *
     * @param data
     *            �������
     * @param privateKey
     *            ˽Կ
     *
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // ������base64�����˽Կ
        byte[] keyBytes = decryptBASE64(privateKey);
        
        // ����PKCS8EncodedKeySpec����
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        
        // KEY_ALGORITHM ָ���ļ����㷨
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        
        // ȡ˽Կ�׶���
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        
        // ��˽Կ����Ϣ�������ǩ��
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        
        return encryptBASE64(signature.sign());
    }

    /**
     * У������ǩ��
     * 
     * @param data
     *            �������
     * @param publicKey
     *            ��Կ
     * @param sign
     *            ����ǩ��
     *  
     * @return У��ɹ�����true ʧ�ܷ���false
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
            
        // ������base64����Ĺ�Կ
        byte[] keyBytes = decryptBASE64(publicKey);

        // ����X509EncodedKeySpec����
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM ָ���ļ����㷨
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // ȡ��Կ�׶���
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // ��֤ǩ���Ƿ���
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * ����<br>
     * ��˽Կ����
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // ����Կ����
        byte[] keyBytes = decryptBASE64(key);

        // ȡ��˽Կ
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // ����ݽ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * ����<br>
     * �ù�Կ����
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // ����Կ����
        byte[] keyBytes = decryptBASE64(key);

        // ȡ�ù�Կ
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // ����ݽ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * ����<br>
     * �ù�Կ����
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // �Թ�Կ����
        byte[] keyBytes = decryptBASE64(key);

        // ȡ�ù�Կ
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // ����ݼ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * ����<br>
     * ��˽Կ����
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // ����Կ����
        byte[] keyBytes = decryptBASE64(key);

        // ȡ��˽Կ
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // ����ݼ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * ȡ��˽Կ
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return encryptBASE64(key.getEncoded());
    }

    /**
     * ȡ�ù�Կ
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return encryptBASE64(key.getEncoded());
    }

    /**
     * ��ʼ����Կ
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // ��Կ
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // ˽Կ
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


    public static void main(String [] args) throws Exception {
        Map<String, Object> map = RSA.initKey();
        System.out.println(map);

        String pubkey = getPublicKey(map);
        String prikey = getPrivateKey(map);

        System.err.println(pubkey);
        System.err.println(prikey);
    }
}
