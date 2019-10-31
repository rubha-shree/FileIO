package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

enum Statuses {SUCCESS, FAILURE};

public class Main {

    public static HashMap<String, mapValue> directoryContentMap = new HashMap<String, mapValue>();

    public static void writeToFile (String filePath, String fileName, mapValue fileContent) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) file.mkdirs();
        try(FileWriter fw = new FileWriter(filePath + "/" + fileName, true);
            BufferedWriter bw = new BufferedWriter(fw)) {
            fw.write(fileContent.toString());
            fileContent.reset();
        }
    }

    public static void pushToMap(String path, String line, String fileName) throws IOException {
        if (directoryContentMap.containsKey(path)) {
            mapValue fileContent = directoryContentMap.get(path);
            if (fileContent.appendAndCheckLimit(line, 1)) {
                writeToFile(path, fileName, fileContent);
            }
        } else {
            mapValue fileContent = new mapValue(line, 1);
            directoryContentMap.put(path, fileContent);
            if (fileContent.isLimitReached()) {
                writeToFile(path, fileName, fileContent);
            }
        }
    }

    public static void parseLineAndSelectDirectory(String line, String fileName, String outputDir) throws IOException{
        String[] columns = line.split(",");
        String product = columns[1];
        String status = columns[2];
        String path = "";
        if ((status.equals(Statuses.SUCCESS.name())) || (status.equals(Statuses.FAILURE.name()))) {
            path += "./" + outputDir + "/" + product + "/" + status;
            pushToMap(path, line, fileName);
        }
        else {
            System.out.println("Invalid data in the line, so ignoring!");
        }
    }

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Enter the input file:");
            String inputFile = reader.readLine();
            System.out.println("Enter the base output directory:");
            String outputDir = reader.readLine();
            try(BufferedReader fr = new BufferedReader(new FileReader(inputFile))) {
                String line = fr.readLine();
                while (line != null) {
                    line += (!line.endsWith("\n")) ? "\n" : "";
//                    System.out.println("Line from Input file:" + line);
                    parseLineAndSelectDirectory(line, inputFile, outputDir);
                    line = fr.readLine();
                }
                for(Map.Entry<String, mapValue> entry : directoryContentMap.entrySet()) {
                    String key = entry.getKey();
                    mapValue value = entry.getValue();

                    if (!value.isEmpty()) {
                        writeToFile(key, inputFile, value);
                    }
                }

            } catch (FileNotFoundException e) {
                System.out.println("File not found" + e.getMessage());
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
