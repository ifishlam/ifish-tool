package com.ifish.tool.util;

import java.io.*;
import java.util.Properties;

public class FileUtils {

    public static Properties getPropertiesFile() {

        Properties props = new Properties();

        try {
            InputStream in = getFileDirectory("app.properties");
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

    public static String getUserDirectory() {

        return System.getProperty("user.dir");
    }

    public static InputStream getFileDirectory(String fileName) {

        return FileUtils.class.getClassLoader().getResourceAsStream(fileName);
    }

}
