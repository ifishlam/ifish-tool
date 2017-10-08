package com.ifish.tool.util;

import com.ifish.tool.bean.ColumnBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FreeMarkerUtils {

//    private static String ROOT_PATH_TEMPLATE = FreeMarkerUtils.class.getClassLoader().getResource("Templates").getPath();

    public static void createFile(String module, String level, List<ColumnBean> columnList) {

        Map templateParams = new HashMap();

        // get data from column
        ColumnBean column = columnList.get(0);
        String tableName = column.getTableName();
        String tableNameFirstUpper = column.tableNameFirstUpper;

        // get property value
        Properties props = FileUtils.getPropertiesFile();
        String templateProp = "freemarker." + module + ".template.name";
        String newFilePathProp = "freemarker." + module + "." + level + ".file.path";
        String newFileSuffixProp = "freemarker." + module + ".file.suffix";
        String packageProp = "freemarker." + module + "." + level + ".package";

        String templateName = props.getProperty(templateProp);
        String packageName = props.getProperty(packageProp);
        String newFilePathName = props.getProperty(newFilePathProp);
        String newFileName = props.getProperty(newFileSuffixProp);
        String newFileRootPath = props.getProperty("freemarker.file.export.root.path");
        if (newFileRootPath == null) {
            newFileRootPath = FileUtils.getUserDirectory();
        }

        if (templateName == null) {
            ObjectUtils.errorLog("Property value NOT found --> " + templateProp);
            return;
        } else if (newFilePathName == null) {
            ObjectUtils.errorLog("Property value NOT found --> " + newFilePathProp);
            return;
        } else if (newFileName == null) {
            ObjectUtils.errorLog("Property value NOT found --> " + newFileSuffixProp);
            return;
        } else if (packageName == null) {
            ObjectUtils.errorLog("Property value NOT found --> " + packageProp);
            return;
        }

        newFileName = tableNameFirstUpper + newFileName;
        newFilePathName = newFileRootPath + newFilePathName;

        String importBeanPackageName = props.getProperty("freemarker.bean." + level + ".package");

        // module specific parameters
        if (AppConstants.SMART_CODER_MODULE_BEAN.equalsIgnoreCase(module)) {

            templateParams.put("columns", columnList);

        } else if (AppConstants.SMART_CODER_MODULE_DAO.equalsIgnoreCase(module)) {

            templateParams.put("importBeanPackageName", importBeanPackageName);
            String key = null;
            for (ColumnBean col : columnList) {
                if (col.isPrimaryKey()) {
                    key = col.getColumnDataTypeInJava();
                }
            }
            templateParams.put("dataTypeOfKey", key);
        } else if (AppConstants.SMART_CODER_MODULE_LIST_VIEW.equalsIgnoreCase(module)) {

            templateParams.put("importBeanPackageName", importBeanPackageName);
        } else if (AppConstants.SMART_CODER_MODULE_BASIC_VIEW.equalsIgnoreCase(module)) {

            templateParams.put("importBeanPackageName", importBeanPackageName);
            templateParams.put("columns", columnList);
        }

        // template common parameters
        templateParams.put("packageName", packageName);
        templateParams.put("tableName", tableName);
        templateParams.put("tableNameFirstUpper", tableNameFirstUpper);


        try {
            Configuration config = new Configuration(Configuration.VERSION_2_3_26);
            config.setDirectoryForTemplateLoading(new File(FileUtils.USER_TEMPLATE_DIRECTORY));

            Template template = config.getTemplate(templateName);
            template.process(templateParams, FileUtils.createWriter(newFilePathName, newFileName));

            ObjectUtils.debugLog("New file was generated to " + newFilePathName + newFileName);
        } catch (IOException e) {
            ObjectUtils.errorLog("Templates NOT found ! " + e.getMessage());
        } catch (TemplateException e) {
            ObjectUtils.errorLog("Templates Exception ! " + e.getMessage());
        }
    }
}
