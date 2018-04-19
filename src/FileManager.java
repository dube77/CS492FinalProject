import java.io.*;
import java.util.ArrayList;

/**
 * Class responsible for reading and writing files
 */
public class FileManager {

    /**
     * Reads contents of file into ArrayList of strings
     * @param path Path to the file to be read
     * @return ArrayList of the line by line data from file
     */
    public ArrayList<String> Read(String path){
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            ArrayList<String> lines = new ArrayList<>();
            for (String line; (line = br.readLine()) != null; ){
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            System.err.println("An IOException was caught : " + e.getMessage());
        }
        return null;
    }

    /**
     * Writes ArrayList of strings given to it into a file
     * @param data ArrayList of strings to be written to a file
     * @param path Path of the file to be created
     */
    public void Write(ArrayList<String> data, String path){
        try (PrintWriter Printer = new PrintWriter(path))
        {
            for (String aData : data) Printer.println(aData);
        } catch (FileNotFoundException e) {
            System.err.println("An FileNotFoundException was caught : " + e.getMessage());
        }
    }

    /**
     * Returns true if file is found within directory
     * @param name Name of the file being searched
     * @param dir Name of directory being searched for file
     * @return True: File is found, False: File is not found
     */
    public boolean isFile(String name, File dir){
        File[] list = dir.listFiles();
        if (list != null){
            for (File f : list){
                if (f.isDirectory()){
                    isFile(name,f);
                } else if (name.equalsIgnoreCase(f.getName())){
                    return true;
                }
            }
        }
        return false;
    }

}
