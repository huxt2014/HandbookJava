package handbook.hibernate.taskexample.repository;

import handbook.hibernate.taskexample.model.TaskConfig;


public interface TaskConfigRepository {

    TaskConfig save(TaskConfig taskConfig);

    TaskConfig findByName(String name);

}
