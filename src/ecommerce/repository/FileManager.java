package ecommerce.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

    private static final String DATA_FOLDER = "data";

    // Save data into a file
    public static void saveToFile(String fileName, String data) throws IOException {

        File folder = new File(DATA_FOLDER);

        // Create data folder if it does not exist
        if (!folder.exists()) {
            folder.mkdir();
        }

        File file = new File(folder, fileName);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.write(data);

        writer.close();
    }

    // Read data from a file
    public static String readFromFile(String fileName) throws IOException {

        File file = new File(DATA_FOLDER, fileName);

        if (!file.exists()) {
            return "";
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));

        StringBuilder data = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            data.append(line);
            data.append("\n");
        }

        reader.close();

        return data.toString();
    }
}