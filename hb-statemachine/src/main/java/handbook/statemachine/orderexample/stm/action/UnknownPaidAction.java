package handbook.statemachine.orderexample.stm.action;

import handbook.statemachine.orderexample.model.OrderProcessRecordStatus;
import handbook.statemachine.orderexample.stm.OrderEvent;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class UnknownPaidAction implements Action<OrderProcessRecordStatus, OrderEvent> {
    @Override
    public void execute(StateContext<OrderProcessRecordStatus, OrderEvent> context) {

    }
}
