import java.util.ArrayList;

public class PasswordManager {

    public static void main(String[] args){
        PasswordManager pm = new PasswordManager();
        Encryption crypt = new Encryption();
        FileManager fm = new FileManager();

        //Read data
        ArrayList<String> encryptedLines = fm.Read("encrypted.txt");

        //Decrypt data
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV
        ArrayList<String> decryptedLines = new ArrayList<>();
        for (String encryptedLine : encryptedLines) {
            decryptedLines.add(crypt.decrypt(key, initVector, encryptedLine));
        }
        
        //Turn ArrayList of strings into ArrayList of account objects
        ArrayList<Account> accounts = pm.SetupAccounts(decryptedLines);
        
        //Encrypt data
        encryptedLines.clear();
        for (Account a : accounts){
            encryptedLines.add(a.toString());
        }

        //Write data
        fm.Write(encryptedLines, "new_encrypted.txt");
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
}
