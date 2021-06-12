package handbook.orm.taskexample.model;

import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="jpa_task_data_source")
public class TaskDataSource implements Serializable {

    @Getter
    @Id
    @ManyToOne
    private TaskConfig taskConfig;

    @Getter
    @Id
    @ManyToOne
    private DataSource dataSource;

    private Integer dataNumber;

    @Type(type="json")
    @Column(columnDefinition = "json")
    private DataSourceMapper dataSourceMapper;

    private TaskDataSource() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDataSource that = (TaskDataSource) o;
        return taskConfig.equals(that.taskConfig) && dataSource.equals(that.dataSource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskConfig, dataSource);
    }

    public static class DataSourceMapper{

        public List<String> selectorColumns = new ArrayList<>();

        public String groupIdGenerator;

        public String payLoadFilter;

    }
}
