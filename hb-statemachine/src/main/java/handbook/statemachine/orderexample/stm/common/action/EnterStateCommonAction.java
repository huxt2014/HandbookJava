package handbook.statemachine.orderexample.stm.common.action;

import handbook.statemachine.orderexample.model.OrderProcessRecord;
import handbook.statemachine.orderexample.model.OrderProcessRecordStatus;
import handbook.statemachine.orderexample.stm.common.OrderEvent;
import handbook.statemachine.orderexample.stm.common.message.PaidMessage;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class EnterStateCommonAction implements Action<OrderProcessRecordStatus, OrderEvent> {

    @Override
    public void execute(StateContext<OrderProcessRecordStatus, OrderEvent> context) {
        OrderProcessRecord orderProcessRecord =
                (OrderProcessRecord)context.getMessage().getHeaders().get(PaidMessage.KEY_ORDER);
        orderProcessRecord.setProcessStatus(context.getStateMachine().getState().getId());
    }

}
