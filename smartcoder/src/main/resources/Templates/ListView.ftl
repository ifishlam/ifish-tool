package ${packageName};

import com.ifish.ms.base.view.BaseListView;
import ${importBeanPackageName}.${tableNameFirstUpper}Bean;

public class ${tableNameFirstUpper}ListView extends BaseListView<${tableNameFirstUpper}Bean> {

    private static final long serialVersionUID = 1L;

    public ${tableNameFirstUpper}ListView(Iterable beanList, Class viewClass) {
        super(beanList, viewClass);
    }

    public ${tableNameFirstUpper}ListView(Iterable beanList) {
        super(beanList, ${tableNameFirstUpper}BasicView.class);
    }
}

