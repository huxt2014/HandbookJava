package handbook.hibernate.taskexample.model;

import handbook.hibernate.taskexample.model.type.DataSourceType;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="jpa_data_source")
@ToString(exclude = "taskConfigs")
public class DataSource implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @NaturalId
    public String name;

    @Enumerated(EnumType.STRING)
    public DataSourceType dataSourceType;

    @OneToMany(mappedBy = "dataSource")
    @Getter
    private List<TaskDataSource> taskConfigs = new ArrayList<>();

    private DataSource() {
    }

    public DataSource(String name, DataSourceType dataSourceType){
        this.name = name;
        this.dataSourceType = dataSourceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSource that = (DataSource) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
