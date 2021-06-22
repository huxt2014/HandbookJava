package handbook.hibernate.dao.jpa;

import handbook.hibernate.taskexample.model.DataSource;
import org.springframework.data.repository.CrudRepository;


public interface JpaDataSourceRepository extends CrudRepository<DataSource, Long> {

    DataSource findByName(String name);

}
