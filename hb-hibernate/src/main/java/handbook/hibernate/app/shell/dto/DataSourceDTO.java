package handbook.hibernate.app.shell.dto;

import handbook.hibernate.taskexample.model.type.DataSourceType;
import lombok.ToString;

@ToString
public class DataSourceDTO {

    public Long id;

    public String name;

    public DataSourceType dataSourceType;

}
