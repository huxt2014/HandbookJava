package handbook.hibernate.dao.jpa;

import handbook.hibernate.taskexample.model.TaskConfig;
import org.springframework.data.repository.CrudRepository;

public interface JpaTaskConfigRepository extends CrudRepository<TaskConfig, Long> {

    TaskConfig findByName(String name);

}
