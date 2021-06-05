package handbook.statemachine.orderexample.stm.action;

import handbook.statemachine.orderexample.model.OrderProcessRecord;
import handbook.statemachine.orderexample.model.OrderProcessRecordStatus;
import handbook.statemachine.orderexample.service.OrderService;
import handbook.statemachine.orderexample.stm.OrderEvent;
import handbook.statemachine.orderexample.stm.message.PaidMessage;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class PaidAction implements Action<OrderProcessRecordStatus, OrderEvent>{

    @Override
    public void execute(StateContext<OrderProcessRecordStatus, OrderEvent> context) {
        OrderService.PayInfoDTO payInfoDTO =
                (OrderService.PayInfoDTO)context.getMessage().getHeaders().get(PaidMessage.KEY_PAY_DTO);
        OrderProcessRecord orderProcessRecord =
                (OrderProcessRecord) context.getMessage().getHeaders().get(PaidMessage.KEY_ORDER);
        OrderProcessRecord.PaymentInfo target = orderProcessRecord.getPaymentInfos().stream()
                .filter(paymentInfo -> paymentInfo.getOrderPayId().equals(payInfoDTO.getOrderPayId()))
                .findFirst().orElse(null);
        target.finish(payInfoDTO.getPaymentPayId());
    }

}
