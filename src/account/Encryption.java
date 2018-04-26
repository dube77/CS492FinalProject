package account;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class responsible for the AES encryption methods used to encrypt/decrypt string values
 */
public class Encryption {

    /**
     *
     * @param key Key value of which encryption is processed with
     * @param initVector Initial Vector value of which encryption is processed with
     * @param value String value of which is encrypted
     * @return Encrypted version of the input string
     */
    public String encrypt(String key, String initVector, String value)
    {
        try
        {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            //System.out.println("encrypted string: " + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param key Key value of which encryption is processed with
     * @param initVector Initial Vector value of which encryption is processed with
     * @param encrypted String value of which is encrypted
     * @return Encrypted version of the input string
     */
    public String decrypt(String key, String initVector, String encrypted)
    {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
