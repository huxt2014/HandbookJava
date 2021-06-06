package handbook.statemachine.orderexample.stm.common.action;

import com.alibaba.cola.statemachine.builder.When;
import handbook.statemachine.orderexample.model.OrderProcessRecord;
import handbook.statemachine.orderexample.model.OrderProcessRecordStatus;
import handbook.statemachine.orderexample.stm.common.OrderEvent;
import handbook.statemachine.orderexample.stm.common.message.PaidMessage;
import handbook.statemachine.dto.PayInfoDTO;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class PaidAction
        implements Action<OrderProcessRecordStatus, OrderEvent>,
                   com.alibaba.cola.statemachine.Action<OrderProcessRecordStatus, OrderEvent, Message<OrderEvent>> {

    @Override
    public void execute(StateContext<OrderProcessRecordStatus, OrderEvent> context) {
        PayInfoDTO payInfoDTO =
                (PayInfoDTO)context.getMessage().getHeaders().get(PaidMessage.KEY_PAY_DTO);
        OrderProcessRecord orderProcessRecord =
                (OrderProcessRecord) context.getMessage().getHeaders().get(PaidMessage.KEY_ORDER);
        this.finish(orderProcessRecord, payInfoDTO);
    }

    @Override
    public void execute(OrderProcessRecordStatus from, OrderProcessRecordStatus to, OrderEvent event, Message<OrderEvent> context) {
        PayInfoDTO payInfoDTO = (PayInfoDTO)context.getHeaders().get(PaidMessage.KEY_PAY_DTO);
        OrderProcessRecord orderProcessRecord = (OrderProcessRecord) context.getHeaders().get(PaidMessage.KEY_ORDER);
        this.finish(orderProcessRecord, payInfoDTO);
        orderProcessRecord.setProcessStatus(to);
    }

    private void finish(OrderProcessRecord orderProcessRecord, PayInfoDTO payInfoDTO){
        OrderProcessRecord.PaymentInfo target = orderProcessRecord.getPaymentInfos().stream()
                .filter(paymentInfo -> paymentInfo.getOrderPayId().equals(payInfoDTO.getOrderPayId()))
                .findFirst().orElse(null);
        target.finish(payInfoDTO.getPaymentPayId());
    }
}
