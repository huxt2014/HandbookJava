package handbook.statemachine.orderexample.stm.common.action;

import handbook.statemachine.orderexample.model.OrderProcessRecordStatus;
import handbook.statemachine.orderexample.stm.common.OrderEvent;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class UnknownPaidAction
        implements Action<OrderProcessRecordStatus, OrderEvent>,
        com.alibaba.cola.statemachine.Action<OrderProcessRecordStatus, OrderEvent, Message<OrderEvent>>{

    @Override
    public void execute(StateContext<OrderProcessRecordStatus, OrderEvent> context) {

    }

    @Override
    public void execute(OrderProcessRecordStatus from, OrderProcessRecordStatus to, OrderEvent event, Message<OrderEvent> context) {

    }
}
