package ${packageName};

import ${importBeanPackageName}.${tableNameFirstUpper}Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ${tableNameFirstUpper}DAO extends CrudRepository<${tableNameFirstUpper}Bean, ${dataTypeOfKey}> {

}
