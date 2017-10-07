package com.ifish.tool.starter;

import com.ifish.tool.bean.ColumnBean;
import com.ifish.tool.util.DataBaseUtils;
import com.ifish.tool.util.FreeMarkerUtils;
import com.ifish.tool.util.ObjectUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SmartCoderMain {
    public static void main(String[] args) {

        ObjectUtils.debugLog("Smart Coder Started...");

        String table = null;
        String module = null;
        String level = null;

        for (int i = 0; i < args.length; i++) {
            if ("-table".equalsIgnoreCase(args[i]) && args[i + 1] != null) {
                table = args[i + 1].trim().toLowerCase();
            } else if ("-module".equalsIgnoreCase(args[i]) && args[i + 1] != null) {
                module = args[i + 1].trim().toLowerCase();
            } else if ("-level".equalsIgnoreCase(args[i]) && args[i + 1] != null) {
                level = args[i + 1].trim().toLowerCase();
            }
        }

        // validate parameters
        if (table == null) {
            ObjectUtils.errorLog("未指定参数(-table)！");
            return;
        }

        if (module == null) {
            ObjectUtils.debugLog("未找到参数(-module)！将采用系统默认配置生成(bean,dao,basicview,listview)！");
            module = "bean,dao,basicview,listview";
        }
        String[] moduleList = module.split(",");

        if (level == null) {
            ObjectUtils.debugLog("未找到参数(-level)！将采用系统默认配置生成(core)！");
            level = "core";
        }

        // Do Generate
        Map<String, List<ColumnBean>> tables = DataBaseUtils.getTableDetails(table);

        Iterator<String> tableNames = tables.keySet().iterator();
        String tempTable;
        List<ColumnBean> columnList;

        while (tableNames.hasNext()) {
            tempTable = tableNames.next();
            columnList = tables.get(tempTable);
            if (columnList != null && columnList.size() > 0) {
                for (String m : moduleList) {
                    doGenerate(m, level, tables.get(tempTable));
                }
            }
        }
    }

    private static void doGenerate(String module, String level, List<ColumnBean> columnList) {
        ObjectUtils.debugLog("Start to generate file for table --> " + columnList.get(0).getTableName() + "; module --> " + module + "; level --> " + level);
        FreeMarkerUtils.createFile(module, level, columnList);
        ObjectUtils.debugLog("END... \n");
    }
}
