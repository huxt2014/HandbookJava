package handbook.orm.taskexample.model;

import handbook.orm.taskexample.model.type.DataSourceType;
import lombok.Getter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="jpa_data_source")
public class DataSource implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    @NaturalId
    public String name;

    @Enumerated(EnumType.STRING)
    public DataSourceType dataSourceType;

    @OneToMany(mappedBy = "dataSource")
    private List<TaskDataSource> taskConfigs = new ArrayList<>();

    private DataSource() {
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
