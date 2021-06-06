package handbook.statemachine.orderexample.stm.cola;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import handbook.statemachine.orderexample.model.OrderProcessRecordStatus;
import handbook.statemachine.orderexample.stm.common.OrderEvent;
import handbook.statemachine.orderexample.stm.common.action.PaidAction;
import handbook.statemachine.orderexample.stm.common.action.UnknownPaidAction;
import handbook.statemachine.orderexample.stm.common.guard.PaidGuard;
import org.springframework.messaging.Message;

public class StmBuilder {

    public static StateMachine<OrderProcessRecordStatus, OrderEvent, Message<OrderEvent>> buildMachine(){
        StateMachineBuilder<OrderProcessRecordStatus, OrderEvent, Message<OrderEvent>>
                builder = StateMachineBuilderFactory.create();

        builder.externalTransition()
                .from(OrderProcessRecordStatus.PAY_READY)
                .to(OrderProcessRecordStatus.ALL_PAID)
                .on(OrderEvent.PAID)
                .when(new PaidGuard.AllPaid())
                .perform(new PaidAction());

        builder.externalTransition()
                .from(OrderProcessRecordStatus.PAY_READY)
                .to(OrderProcessRecordStatus.PARTIAL_PAID)
                .on(OrderEvent.PAID)
                .when(new PaidGuard.PartialPaid())
                .perform(new PaidAction());

        builder.internalTransition()
                .within(OrderProcessRecordStatus.PAY_READY)
                .on(OrderEvent.PAID)
                .when(new PaidGuard.SamePaid());

        builder.externalTransition()
                .from(OrderProcessRecordStatus.PARTIAL_PAID)
                .to(OrderProcessRecordStatus.ALL_PAID)
                .on(OrderEvent.PAID)
                .when(new PaidGuard.AllPaid())
                .perform(new PaidAction());

        builder.externalTransition()
                .from(OrderProcessRecordStatus.PARTIAL_PAID)
                .to(OrderProcessRecordStatus.PARTIAL_PAID)
                .on(OrderEvent.PAID)
                .when(new PaidGuard.PartialPaid())
                .perform(new PaidAction());

        return builder.build("cola-stm");
    }
}
