package handbook.statemachine.orderexample.stm.guard;

import handbook.statemachine.orderexample.model.OrderProcessRecord;
import handbook.statemachine.orderexample.model.OrderProcessRecordStatus;
import handbook.statemachine.orderexample.service.OrderService;
import handbook.statemachine.orderexample.stm.OrderEvent;
import handbook.statemachine.orderexample.stm.message.PaidMessage;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import java.util.List;

public class PaidGuard {

    public enum Choice{

        PartialPaid,

        AllPaid,

        UnknownPaid,

        SamePaid
    }

    public static Choice evaluateChoice(OrderProcessRecord orderProcessRecord, OrderService.PayInfoDTO payInfoDTO){
        List<OrderProcessRecord.PaymentInfo> paymentInfos = orderProcessRecord.getPaymentInfos();

        OrderProcessRecord.PaymentInfo paymentInfo = orderProcessRecord.getPaymentInfos().stream()
                .filter(info -> info.getOrderPayId().equals(payInfoDTO.getOrderPayId()))
                .findFirst().orElse(null);
        long unPaidNum = orderProcessRecord.getPaymentInfos().stream().filter(info -> info.getPaymentPayId() == null)
                .count();
        if (paymentInfo == null){
            return Choice.UnknownPaid;
        } else {
            if (paymentInfo.getPaymentPayId() != null){
                return Choice.SamePaid;
            } else {
                if (unPaidNum == 1){
                    return Choice.AllPaid;
                } else {
                    return Choice.PartialPaid;
                }
            }
        }
    }

    public static class PartialPaid implements Guard<OrderProcessRecordStatus, OrderEvent>{

        @Override
        public boolean evaluate(StateContext<OrderProcessRecordStatus, OrderEvent> context) {
            return context.getMessage().getHeaders().get(PaidMessage.KEY_CHOICE) == Choice.PartialPaid;
        }
    }

    public static class UnknownPaid implements Guard<OrderProcessRecordStatus, OrderEvent>{

        @Override
        public boolean evaluate(StateContext<OrderProcessRecordStatus, OrderEvent> context) {
            return context.getMessage().getHeaders().get(PaidMessage.KEY_CHOICE) == Choice.UnknownPaid;
        }
    }

    public static class SamePaid implements Guard<OrderProcessRecordStatus, OrderEvent>{

        @Override
        public boolean evaluate(StateContext<OrderProcessRecordStatus, OrderEvent> context) {
            return context.getMessage().getHeaders().get(PaidMessage.KEY_CHOICE) == Choice.SamePaid;
        }
    }

    public static class AllPaid implements Guard<OrderProcessRecordStatus, OrderEvent>{

        @Override
        public boolean evaluate(StateContext<OrderProcessRecordStatus, OrderEvent> context) {
            return context.getMessage().getHeaders().get(PaidMessage.KEY_CHOICE) == Choice.AllPaid;
        }
    }
}
