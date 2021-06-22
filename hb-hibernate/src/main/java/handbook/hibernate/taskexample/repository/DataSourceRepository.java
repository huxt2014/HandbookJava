package handbook.hibernate.taskexample.repository;

import handbook.hibernate.taskexample.model.DataSource;


public interface DataSourceRepository {

    DataSource save(DataSource dataSource);

    DataSource findByName(String name);

}
