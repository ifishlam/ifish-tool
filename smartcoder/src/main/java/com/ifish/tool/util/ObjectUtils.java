package com.ifish.tool.util;

public class ObjectUtils {

    public static void debugLog(String msg) {

        System.out.println("[DEBUG] " + msg);
    }

    public static void errorLog(String msg) {

        System.err.println("[ERROR] " + msg);
    }

    public static String convert2JavaDataType(String sqlDataType) {
        String result = "";

        if ("varchar".equalsIgnoreCase(sqlDataType) || "char".equalsIgnoreCase(sqlDataType)) {
            result = "String";
        } else if ("int".equalsIgnoreCase(sqlDataType)) {
            result = "Integer";
        } else if ("timestamp".equalsIgnoreCase(sqlDataType)) {
            result = "Timestamp";
        } else if ("date".equalsIgnoreCase(sqlDataType)) {
            result = "Date";
        } else if ("decimal".equalsIgnoreCase(sqlDataType) || "numeric".equalsIgnoreCase(sqlDataType)
                || "smallmoney".equalsIgnoreCase(sqlDataType)|| "money".equalsIgnoreCase(sqlDataType)) {
            result = "BigDecimal";
        } else {
            result = sqlDataType;
        }

        return result;
    }

    public static String format2CamelName(String name, boolean isUpper) {
        if (name != null) {
            StringBuffer result = new StringBuffer();
            for (String temp : name.split("_")) {
                result.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1).toLowerCase());
            }

            if (isUpper) {
                return result.toString().substring(0, 1).toUpperCase() + result.toString().substring(1);
            } else {
                return result.toString().substring(0, 1).toLowerCase() + result.toString().substring(1);
            }
        }

        return name;
    }
}
