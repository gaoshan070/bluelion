package com.bluelion.shared.utils;

import com.bluelion.shared.constants.KeyGroup;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Random;

/**
 * 密码辅助类
 */
public class CodecUtils {


    private CodecUtils() {
    }

    public static String desEncode(String key, String data) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
            return Hex.encodeHexString(cipher.doFinal(data.getBytes("UTF-8")));
        } catch (Exception e) {

        }
        return "";
    }

    public static String aesEncode(String data, KeyGroup keyGroup) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(keyGroup.getEncodeKey());
        SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = data.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);
        return Base64.encodeBase64String(result);
    }

    public static String aesDecode(String data, KeyGroup keyGroup) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(keyGroup.getEncodeKey());
        SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return StringUtils.newStringUtf8(cipher.doFinal(Base64.decodeBase64(data)));
    }

    public static String getMySign(String data, KeyGroup keyGroup) throws Exception {
        return DigestUtils.md5Hex(data + keyGroup.getSignKey());
    }

    public static String getRequestUUID() {
        StringBuilder uuidBuilder = new StringBuilder();
        uuidBuilder.append(DateUtil.format(new Date(), DateUtil.YYYYMMDDHHMMSSSSS))
                .append("_").append(NumberUtil.getRandomNum(100000, 999999));
        return uuidBuilder.toString();
    }

    /**
     * 生成SALT
     */
    public static String generateRandCode() {
        String str = "abcdefghijklmmopqrstuvwxyz0123456789";
        Random random = new Random(System.currentTimeMillis());
        int length = 6;
        char[] tmp = new char[length];
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            tmp[i] = c;
        }
        return new String(tmp);
    }

    public static String generateValidateCode() {
        Random random = new Random();
        int length = 4;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(random.nextInt(9));
        }
        return result.toString();
    }

    public static String rsaSign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decodeBase64(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey generatePrivate = keyFactory.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature
                    .getInstance("SHA1WithRSA");
            signature.initSign(generatePrivate);
            signature.update(content.getBytes("UTF-8"));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
//            LogContext.instance().error(e, "RSA签名失败");
        }
        return "";
    }

    public static boolean verifyRsaSign(String content, String sign, String publicKey) {
        try {
//            LogContext.instance().info("对方提供的签名:" + sign);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("RSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("UTF-8"));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
//            LogContext.instance().error(e, "验证RSA签名失败");
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
// Login body
//                String data = "{\"account\": \"gaoshan070@gmail.com\",\"password\": \"1qaz2wsx\",\"device_info\": {\"mac\": \"xxx\",\"imei\": \"imei\",\"imsi\":\"imsi\",\"os\": 1,\"android_info\": {\"android_id\":\"android_id\",\"android_serial_number\":\"serial_number_12222\"}},\"other_info\": {\"app_id\": \"xxxx\"}}";
        //Register body
//        String data = "{\"account\": \"gaoshan070@gmail.com\",\"password\": \"1234qwer\",\"device_info\": {\"mac\": \"xxx\",\"imei\": \"imei\",\"imsi\":\"imsi\",\"os\": 1,\"android_info\": {\"android_id\":\"android_id\",\"android_serial_number\":\"serial_number_12222\"}},\"other_info\": {\"app_id\": \"xxxx\"}}";
        //Logout body
//        String data = "{\"account\": \"gaoshan070@gmail.com\",\"token\": \"dcb16b2af217dab6254f2a17c2b995dd\",\"device_info\": {\"mac\": \"xxx\",\"imei\": \"imei\",\"imsi\":\"imsi\",\"os\": 1,\"android_info\": {\"android_id\":\"android_id\",\"android_serial_number\":\"serial_number_12222\"}},\"other_info\": {\"app_id\": \"xxxx\"}}";
// Send code lost pass body
//        String data = "{\"account\": \"gaoshan070@gmail.com\",\"type\": \"1\",\"device_info\": {\"mac\": \"xxx\",\"imei\": \"imei\",\"imsi\":\"imsi\",\"os\": 1,\"android_info\": {\"android_id\":\"android_id\",\"android_serial_number\":\"serial_number_12222\"}},\"other_info\": {\"app_id\": \"xxxx\"}}";
// Send code modify pass body
//        String data = "{\"account\": \"gaoshan070@gmail.com\",\"type\": \"2\",\"token\": \"dcb16b2af217dab6254f2a17c2b995dd\",\"device_info\": {\"mac\": \"xxx\",\"imei\": \"imei\",\"imsi\":\"imsi\",\"os\": 1,\"android_info\": {\"android_id\":\"android_id\",\"android_serial_number\":\"serial_number_12222\"}},\"other_info\": {\"app_id\": \"xxxx\"}}";
//Modify pass body
//        String data = "{\"account\": \"gaoshan070@gmail.com\",\"old_password\": \"1234qwer\",\"new_password\": \"1qaz2wsx\",\"validation_code\": \"2800\",\"token\": \"dcb16b2af217dab6254f2a17c2b995dd\",\"device_info\": {\"mac\": \"xxx\",\"imei\": \"imei\",\"imsi\":\"imsi\",\"os\": 1,\"android_info\": {\"android_id\":\"android_id\",\"android_serial_number\":\"serial_number_12222\"}},\"other_info\": {\"app_id\": \"xxxx\"}}";
        //Get User info body
//        String data = "{\"account\": \"gaoshan070@gmail.com\",\"token\": \"dcb16b2af217dab6254f2a17c2b995dd\",\"device_info\": {\"mac\": \"xxx\",\"imei\": \"imei\",\"imsi\":\"imsi\",\"os\": 1,\"android_info\": {\"android_id\":\"android_id\",\"android_serial_number\":\"serial_number_12222\"}},\"other_info\": {\"app_id\": \"xxxx\"}}";

        //Create print order body
//        String data = "{\"user_id\": \"1\",\"plate_number\": \"LBZ806\",\"service\": \"1\",\"next_service_due\": \"68000\",\"print_date\": \"2019-11-07\",\"token\": \"dcb16b2af217dab6254f2a17c2b995dd\",\"device_info\": {\"mac\": \"xxx\",\"imei\": \"imei\",\"imsi\":\"imsi\",\"os\": 1,\"android_info\": {\"android_id\":\"android_id\",\"android_serial_number\":\"serial_number_12222\"}},\"other_info\": {\"app_id\": \"xxxx\"}}";

        //QA list body
//        String data = "{\"user_id\": \"1\",\"token\": \"dcb16b2af217dab6254f2a17c2b995dd\",\"device_info\": {\"mac\": \"xxx\",\"imei\": \"imei\",\"imsi\":\"imsi\",\"os\": 1,\"android_info\": {\"android_id\":\"android_id\",\"android_serial_number\":\"serial_number_12222\"}},\"other_info\": {\"app_id\": \"xxxx\"}}";

        // Init Body
        String data = "{\"is_test\": \"1\",\"device_info\": {\"mac\": \"xxx\",\"imei\": \"imei\",\"imsi\":\"imsi\",\"os\": 1,\"android_info\": {\"android_id\":\"android_id\",\"android_serial_number\":\"serial_number_12222\"}},\"other_info\": {\"app_id\": \"xxxx\"}}";
        String mySign = CodecUtils.getMySign(data, KeyGroup.DEFAULT);
        String encryptionData = aesEncode(data, KeyGroup.DEFAULT);
        System.out.println(mySign);
        System.out.println(encryptionData);
    }

}
