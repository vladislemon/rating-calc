package util;

import main.App;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * slimon
 * 05.07.2014
 */
public class FileUtil {

    public static void writeToFile(String path, Object obj, boolean append) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, append));
            bw.write(obj.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            System.err.println("Error writing to file (" + path + ") some object (" + obj + ")");
            App.onError(e);
        }
    }
}
