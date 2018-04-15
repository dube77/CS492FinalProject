import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PasswordManager {

    public static void main(String[] args){
        PasswordManager pm = new PasswordManager();
        ArrayList<String> accountLines = pm.ReadFileToArray("Test");
        //Decrypt Lines
        ArrayList<Account> accounts = pm.SetupAccounts(accountLines);
        for (Account a : accounts){
            System.out.println(a.website + " " + a.username + " " + a.password);
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

    private void StoreArrayToFile(){

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
