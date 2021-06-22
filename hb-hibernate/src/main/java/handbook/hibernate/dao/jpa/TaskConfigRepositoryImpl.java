package handbook.hibernate.dao.jpa;

import handbook.hibernate.taskexample.model.TaskConfig;
import handbook.hibernate.taskexample.repository.TaskConfigRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class TaskConfigRepositoryImpl implements TaskConfigRepository {

    @Resource
    private JpaTaskConfigRepository jpaTaskConfigRepository;

    @Override
    public TaskConfig save(TaskConfig taskConfig) {
        return jpaTaskConfigRepository.save(taskConfig);
    }

    @Override
    public TaskConfig findByName(String name) {
        return jpaTaskConfigRepository.findByName(name);
    }
}
