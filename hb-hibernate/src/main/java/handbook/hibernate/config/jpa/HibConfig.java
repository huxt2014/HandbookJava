package handbook.hibernate.config.jpa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@Configuration
@EnableJpaRepositories(basePackageClasses = handbook.hibernate.dao.jpa.JpaDataSourceRepository.class)
@ComponentScan(basePackageClasses = handbook.hibernate.dao.jpa.DataSourceRepositoryImpl.class)
public class HibConfig {

    @PostConstruct
    void init(){
        System.out.println("load HibConfig");
    }

}
