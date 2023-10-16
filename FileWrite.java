/** FileWrite.Java
 * Simple reusable java program that writes input to a specified file type. Returns FALSE if operation fails
 * Written by Donald Wyand 10/10/23
 * 
 */
import java.io.*;
public class FileWrite{
    /** writeToFile()
     * creates a file with specified file name and accepts a string input
     * should no 
     * @param input Data to write
     * @param name Name of the file
     * @param append Shall we append or overwrite and trample original contents? Appended data is written with a newline
     */
    public static boolean createOrAppendFile(String input, String name, boolean append) throws IOException{
        File File = new File(name);
        FileWriter fileWriter = null; // init the write operations
        BufferedWriter buffWriter = null;
        try {
            fileWriter = new FileWriter(File,append);
            buffWriter = new BufferedWriter(fileWriter);
            buffWriter.write(input);
            buffWriter.flush(); // finish writing
            fileWriter.close(); // close inputs
            buffWriter.close();
        }
        catch (IOException e ) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Write operation to " + name + " succeeded!");
        return true;
    }
}

