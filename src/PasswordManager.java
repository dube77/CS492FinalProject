import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class PasswordManager {

    public static void main(String[] args){
        PasswordManager pm = new PasswordManager();

        //Open stored data
        ArrayList<String> encryptedLines = pm.ReadFileToArray("encrypted.txt");

        //Decrypt data
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV
        ArrayList<String> decryptedLines = new ArrayList<String>();
        for (int i = 0; i < encryptedLines.size(); i++) {
        	decryptedLines.add(pm.decrypt(key, initVector, encryptedLines.get(i)));
        }
        
        //Turn ArrayList of strings into ArrayList of account objects
        ArrayList<Account> accounts = pm.SetupAccounts(decryptedLines);
        
        //Encrypt and store data
        try (PrintWriter encryptedPrinter = new PrintWriter("new_encrypted.txt")) 
        {
        	for (int i = 0; i < accounts.size(); i++) {
                encryptedPrinter.println(pm.encrypt(key, initVector, accounts.get(i).toString()));
            }
        } catch (FileNotFoundException e) {
        	System.err.println("An FileNotFoundException was caught : " + e.getMessage());
		}

        for (Account a : accounts){
            System.out.println(a.toString());
        }
    }

    /**
     *
     * @param path This is the path to the file of which you'd like to open and read line by line to an ArrayList
     * @return ArrayList of the lines read from the file
     */
    private ArrayList<String> ReadFileToArray(String path){
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            ArrayList<String> lines = new ArrayList<>();
            for (String line; (line = br.readLine()) != null; ){
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            System.err.println("An IOException was caught : " + e.getMessage());
            return null;
        }
    }

    private ArrayList<String> DeconstructAccounts(ArrayList<Account> accounts){
        return null;
    }

    /**
     *
     * @param accountLines The decrypted lines read from the text file.
     * @return ArrayList of Accounts that contain the information read and decrypted from the text file
     */
    private ArrayList<Account> SetupAccounts(ArrayList<String> accountLines){
        ArrayList<Account> accounts = new ArrayList<>();
        for (String accountLine : accountLines) {
            String[] data = accountLine.split(",");
            accounts.add(new Account(data[0], data[1], data[2]));
        }
        return accounts;
    }

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
