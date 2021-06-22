package handbook.hibernate.taskexample.model;

import handbook.hibernate.taskexample.model.type.TaskConfigStatus;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="jpa_task_config")
@ToString
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

    @OneToMany(mappedBy = "taskConfig", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter
    private List<TaskDataSource> dataSources = new ArrayList<>();

    private TaskConfig(){}

    public TaskConfig(String name, String description, TaskConfigStatus taskStatus, List<RuleConfig> ruleConfigs){
        this.name = name;
        this.description = description;
        this.taskStatus = taskStatus;
        ruleConfigs.forEach(r -> this.ruleConfigs.add(new TaskConfig.RuleConfig(r.name, r.code)));
    }

    public void addDataSource(DataSource dataSource, int dataNumber, TaskDataSource.DataSourceMapper dataSourceMapper){
        TaskDataSource taskDataSource = new TaskDataSource(this, dataSource, dataNumber, dataSourceMapper);
        this.dataSources.add(taskDataSource);
        dataSource.getTaskConfigs().add(taskDataSource);
    }

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

    @ToString
    static public class RuleConfig {

        @Getter
        private String name;

        public String code;

        private RuleConfig() {
        }

        public RuleConfig(String name, String code){
            this.name = name;
            this.code = code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RuleConfig that = (RuleConfig) o;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }


}
