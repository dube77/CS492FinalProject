import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
    private String salt = "_CS492_";

    /**
     *
     * @param s String value to be evaluated to a hash
     * @return Encoded hash
     */
    public String hash(String s){
        try {
            s += salt;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
            return toHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param hash Bytecode of the hash selected to turn into hex
     * @return Hexadecimal formatted hash
     */
    private String toHex(byte[] hash){
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
