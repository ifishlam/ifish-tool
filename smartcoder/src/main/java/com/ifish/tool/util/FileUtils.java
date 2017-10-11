package com.ifish.tool.util;

import java.io.*;
import java.util.Properties;

public class FileUtils {

    public static final String FILE_UTILS_FOLDER_SEPARATOR = "\\";
    public static final String USER_CONFIG_DIRECTORY = getUserDirectory() + "\\config\\";
    public static final String USER_TEMPLATE_DIRECTORY = getUserDirectory() + "\\config\\Templates\\";

    public static Properties getPropertiesFile() {

        Properties props = new Properties();

        try {
            InputStream in = getFileInputStream("app.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return props;
    }

    public static Writer createWriter(String path, String name) {

        Writer writer = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            writer = new OutputStreamWriter(new FileOutputStream(path + name, false), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return writer;
    }

    public static boolean isFileExist(String path, String name) {

        String fileDir = getFileDirectory(path, name);
        File file = new File(fileDir);

        return file.isFile();
    }

    public static String getFileDirectory(String path, String file) {

        if (ObjectUtils.isEmpty(file)) {
            return null;
        }

        if (ObjectUtils.isEmpty(path)) {
            return file;
        }

        if (path.endsWith(FILE_UTILS_FOLDER_SEPARATOR)) {
            return path + file;
        } else {
            return path + FILE_UTILS_FOLDER_SEPARATOR + file;
        }
    }

    public static void copyFile(String srcFilePath, String srcFileName, String targetFilePath, String targetFileName) {

        if (ObjectUtils.isEmpty(srcFilePath)) {
            srcFilePath = "";
        }
        String srcContents = readFile(srcFilePath + srcFileName);
        if (!ObjectUtils.isEmpty(srcContents)) {
            // initial value
            if (ObjectUtils.isEmpty(targetFilePath)) {
                targetFilePath = USER_CONFIG_DIRECTORY;
            }
            if (ObjectUtils.isEmpty(targetFileName)) {
                targetFileName = srcFileName;
            }

            File newFile = createFile(targetFilePath, targetFileName);
            writeFile(newFile, srcContents);

            ObjectUtils.debugLog("New file was copied to " + newFile.getPath());
        } else {
            ObjectUtils.errorLog("New file was NOT copy --> " + srcFilePath + srcFileName);
        }
    }

    public static File createFile(String filePath, String fileName) {

        File file = null;

        try {
            // check file path
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            // create file
            file = new File(filePath + "\\" + fileName);
            file.createNewFile();

        } catch (IOException e) {
            ObjectUtils.errorLog("Fail to create file --> " + filePath + "\\" + fileName);
            e.printStackTrace();
            return null;
        }

        return file;
    }

    public static String readFile(String fileName) {

        StringBuffer result = new StringBuffer();
        InputStream in = getFileInputStream(fileName);

        if (in != null) {
            try {
                // read file
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

    public static void writeFile(File file, String contents) {
        if (!ObjectUtils.isEmpty(contents) && file != null) {
            try {
                FileWriter writer = new FileWriter(file, false);
                writer.write(contents.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static InputStream getFileInputStream(String fileName) {

        return FileUtils.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static String getUserDirectory() {

        return System.getProperty("user.dir");
    }
}
