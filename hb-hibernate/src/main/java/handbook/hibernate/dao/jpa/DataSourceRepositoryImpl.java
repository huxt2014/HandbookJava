package handbook.hibernate.dao.jpa;

import handbook.hibernate.taskexample.model.DataSource;
import handbook.hibernate.taskexample.repository.DataSourceRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;

@Repository
public class DataSourceRepositoryImpl implements DataSourceRepository {

    @Resource
    private JpaDataSourceRepository jpaDataSourceRepository;

    @Override
    public DataSource save(DataSource dataSource) {
        return jpaDataSourceRepository.save(dataSource);
    }

    @Override
    public DataSource findByName(String name) {
        return jpaDataSourceRepository.findByName(name);
    }
}
