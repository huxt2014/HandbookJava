package handbook.hibernate.app.shell.dto;

import handbook.hibernate.app.shell.dto.DataSourceDTO;
import handbook.hibernate.app.shell.dto.TaskConfigDTO;
import lombok.ToString;

import java.util.List;

@ToString
public class TestDataDTO {

    public List<DataSourceDTO> dataSources;

    public List<TaskConfigDTO> taskConfigs;

}
