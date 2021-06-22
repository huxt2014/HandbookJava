package handbook.hibernate.app.shell;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import handbook.hibernate.app.shell.dto.TestDataDTO;
import handbook.hibernate.taskexample.model.DataSource;
import handbook.hibernate.taskexample.model.TaskConfig;
import handbook.hibernate.taskexample.repository.DataSourceRepository;
import handbook.hibernate.taskexample.repository.TaskConfigRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;

@ShellComponent()
public class ShellCommand {

    private final ObjectMapper mapper = new ObjectMapper();
    {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @Resource
    private DataSourceRepository dataSourceRepository;

    @Resource
    private TaskConfigRepository taskConfigRepository;

    @ShellMethod("print data")
    public void printData() throws URISyntaxException, IOException {

        TestDataDTO dto = mapper.readValue(
                Thread.currentThread().getContextClassLoader().getResource("data1.json"),
                TestDataDTO.class);
        System.out.println(dto);
    }

    @ShellMethod("save data")
    @Transactional
    public void saveData() throws IOException {
        TestDataDTO dto = mapper.readValue(
                Thread.currentThread().getContextClassLoader().getResource("data1.json"),
                TestDataDTO.class);

        DataSource dataSource1 = new DataSource(dto.dataSources.get(0).name, dto.dataSources.get(0).dataSourceType);
        DataSource dataSource2 = new DataSource(dto.dataSources.get(1).name, dto.dataSources.get(1).dataSourceType);
        DataSource dataSource3 = new DataSource(dto.dataSources.get(2).name, dto.dataSources.get(2).dataSourceType);

        dataSourceRepository.save(dataSource1);
        dataSourceRepository.save(dataSource2);
        dataSourceRepository.save(dataSource3);

        TaskConfig taskConfig1 = new TaskConfig(dto.taskConfigs.get(0).name, null,
                dto.taskConfigs.get(0).taskStatus,  dto.taskConfigs.get(0).ruleConfigs);
        TaskConfig taskConfig2 = new TaskConfig(dto.taskConfigs.get(1).name, null,
                dto.taskConfigs.get(1).taskStatus,  dto.taskConfigs.get(1).ruleConfigs);
        System.out.println(taskConfig1);
        System.out.println(taskConfig2);

        taskConfig1.addDataSource(dataSource1, 1, null);
        taskConfig1.addDataSource(dataSource2, 2, null);
        taskConfigRepository.save(taskConfig1);

        taskConfig2.addDataSource(dataSource2, 1, null);
        taskConfig2.addDataSource(dataSource3, 3, null);
        taskConfigRepository.save(taskConfig2);
    }

    /**
     * https://stackoverflow.com/questions/5027013/hibernate-lazy-load-application-design
     */
    @ShellMethod("find data source")
    @Transactional
    public void findDataSource(String name){
        DataSource dataSource = dataSourceRepository.findByName(name);
        System.out.println(dataSource);
        if(dataSource != null){
            System.out.println(dataSource.getTaskConfigs());
        }
    }

    @ShellMethod("find task config")
    @Transactional
    public void findTaskConfig(String name){
        TaskConfig taskConfig = taskConfigRepository.findByName(name);
        System.out.println(taskConfig);
        if(taskConfig != null){
            System.out.println(taskConfig.getDataSources());
            System.out.println(taskConfig.getDataSources());
            System.out.println(taskConfig.getDataSources().get(0).getDataSource());
        }
    }

}
