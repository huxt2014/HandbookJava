package handbook.orm.taskexample.model;

import handbook.orm.taskexample.model.type.TaskConfigStatus;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="jpa_task_config")
public class TaskConfig implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NaturalId
    public String name;

    public String description;

    @Enumerated(EnumType.STRING)
    public TaskConfigStatus taskStatus;

    @Type(type="json")
    @Column(columnDefinition = "json")
    private List<RuleConfig> ruleConfigs = new ArrayList<>();

    @OneToMany(mappedBy = "taskConfig")
    private List<TaskDataSource> dataSources = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskConfig that = (TaskConfig) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    static public class RuleConfig {

        private String name;

        private Long taskConfigId;

        public String code;

        private RuleConfig() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RuleConfig that = (RuleConfig) o;
            return name.equals(that.name) && taskConfigId.equals(that.taskConfigId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, taskConfigId);
        }
    }


}
