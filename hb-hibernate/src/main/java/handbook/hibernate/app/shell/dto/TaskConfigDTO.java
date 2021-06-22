package handbook.hibernate.app.shell.dto;

import handbook.hibernate.taskexample.model.TaskConfig;
import handbook.hibernate.taskexample.model.type.TaskConfigStatus;
import lombok.ToString;

import java.util.List;

@ToString
public class TaskConfigDTO {

    public Long id;

    public String name;

    public TaskConfigStatus taskStatus;

    public List<TaskConfig.RuleConfig> ruleConfigs;


}
