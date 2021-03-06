package handbook.statemachine.orderexample.stm.spring;

import handbook.statemachine.orderexample.model.OrderProcessRecordStatus;
import handbook.statemachine.orderexample.stm.common.OrderEvent;
import handbook.statemachine.orderexample.stm.common.action.EnterStateCommonAction;
import handbook.statemachine.orderexample.stm.common.action.PaidAction;
import handbook.statemachine.orderexample.stm.common.action.UnknownPaidAction;
import handbook.statemachine.orderexample.stm.common.guard.PaidGuard;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.configurers.StateConfigurer;

import java.util.EnumSet;

public class StmBuilder {

    static public StateMachine<OrderProcessRecordStatus, OrderEvent> buildMachine() throws Exception {
        StateMachineBuilder.Builder<OrderProcessRecordStatus, OrderEvent> builder = StateMachineBuilder.builder();

        StateConfigurer<OrderProcessRecordStatus, OrderEvent> stateConfigurer = builder.configureStates()
                .withStates();
        stateConfigurer
                .initial(OrderProcessRecordStatus.PAY_READY)
                .choice(OrderProcessRecordStatus.PAID_CHECK);
        EnumSet.allOf(OrderProcessRecordStatus.class).forEach(
                status -> {
                    if (status == OrderProcessRecordStatus.INIT ){
                    } else if (status == OrderProcessRecordStatus.PAY_READY){
                        stateConfigurer.state(status);
                    } else {
                        stateConfigurer.stateEntry(status, new EnterStateCommonAction());
                    }
                }
        );

        builder.configureTransitions()
                .withExternal()
                    .source(OrderProcessRecordStatus.PAY_READY)
                    .target(OrderProcessRecordStatus.PAID_CHECK)
                    .event(OrderEvent.PAID)
                    .and()
                .withChoice()
                    .source(OrderProcessRecordStatus.PAID_CHECK)
                    .first(OrderProcessRecordStatus.ALL_PAID, new PaidGuard.AllPaid(), new PaidAction())
                    .then(OrderProcessRecordStatus.PARTIAL_PAID, new PaidGuard.PartialPaid(), new PaidAction())
                    .then(OrderProcessRecordStatus.PAY_READY, new PaidGuard.SamePaid())
                    .last(OrderProcessRecordStatus.PAY_READY, new UnknownPaidAction());

        builder.configureTransitions()
                .withExternal()
                    .source(OrderProcessRecordStatus.PARTIAL_PAID)
                    .target(OrderProcessRecordStatus.PAID_CHECK)
                    .event(OrderEvent.PAID)
                    .and()
                .withChoice()
                    .source(OrderProcessRecordStatus.PAID_CHECK)
                    .first(OrderProcessRecordStatus.ALL_PAID, new PaidGuard.AllPaid(), new PaidAction())
                    .then(OrderProcessRecordStatus.PARTIAL_PAID, new PaidGuard.PartialPaid(), new PaidAction())
                    .then(OrderProcessRecordStatus.PARTIAL_PAID, new PaidGuard.SamePaid())
                    .last(OrderProcessRecordStatus.PARTIAL_PAID, new UnknownPaidAction());

        return builder.build();
    }
}
