import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PasswordManager {
    public static void main(String[] args) throws IOException {
        PasswordManager pm = new PasswordManager();
        ArrayList<String> test = pm.ReadFileToArray("Test");
        for (String s : test) {
            System.out.println(s);
        }
    }

    private ArrayList<String> ReadFileToArray(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            ArrayList<String> lines = new ArrayList<>();
            for (String line; (line = br.readLine()) != null; ){
                lines.add(line);
            }
            return lines;
        }
    }
}
