package ${packageName};

import com.ifish.ms.base.view.BaseView;
import ${importBeanPackageName}.${tableNameFirstUpper}Bean;
import com.ifish.ms.core.util.FormatUtils;


public class ${tableNameFirstUpper}BasicView extends BaseView<${tableNameFirstUpper}Bean> {

    private static final long serialVersionUID = 1L;

    <#-- Generate constructor -->
    public ${tableNameFirstUpper}BasicView(${tableNameFirstUpper}Bean detailBean) {
        super(detailBean);
    }

    <#-- Generate getter/setter -->
    <#list columns as col>
    public String get${col.columnNameFirstUpper}() {
        return this.detailBean.get${col.columnNameFirstUpper}();
    }

    </#list>

}