package handbook.statemachine.orderexample.stm.spring;

import handbook.statemachine.orderexample.model.OrderProcessRecord;
import handbook.statemachine.orderexample.model.OrderProcessRecordStatus;
import handbook.statemachine.orderexample.stm.common.OrderEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

@Configuration
public class StmPersistConfig {

    @Bean
    StateMachinePersister<OrderProcessRecordStatus, OrderEvent, OrderProcessRecord> stateMachinePersister(){
        return new DefaultStateMachinePersister<>(new StmPersistConfig.StmPersist());
    }

    static class StmPersist implements StateMachinePersist<OrderProcessRecordStatus, OrderEvent, OrderProcessRecord>{

        @Override
        public void write(StateMachineContext<OrderProcessRecordStatus, OrderEvent> context, OrderProcessRecord contextObj) throws Exception {

        }

        @Override
        public StateMachineContext<OrderProcessRecordStatus, OrderEvent> read(OrderProcessRecord contextObj) throws Exception {
            return new DefaultStateMachineContext<OrderProcessRecordStatus, OrderEvent>(
                    contextObj.getProcessStatus(), null, null, null);
        }
    }

}
