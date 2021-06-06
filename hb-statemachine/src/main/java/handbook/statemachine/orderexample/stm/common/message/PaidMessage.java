package handbook.statemachine.orderexample.stm.common.message;

import handbook.statemachine.orderexample.model.OrderProcessRecord;
import handbook.statemachine.orderexample.stm.common.OrderEvent;
import handbook.statemachine.orderexample.stm.common.guard.PaidGuard;
import handbook.statemachine.dto.PayInfoDTO;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.Map;


public class PaidMessage implements Message<OrderEvent> {

    public static final String KEY_PAY_DTO = "key_pay_dto";

    public static final String KEY_ORDER = "order";

    public static final String KEY_CHOICE = "choice";

    private MessageHeaders headers;

    @Override
    public OrderEvent getPayload() {
        return OrderEvent.PAID;
    }

    @Override
    public MessageHeaders getHeaders() {
        return headers;
    }

    public PaidMessage(OrderProcessRecord orderProcessRecord, PayInfoDTO payInfoDTO, PaidGuard.Choice choice){
        Map<String, Object> tmp = new HashMap<>();
        tmp.put(KEY_PAY_DTO, payInfoDTO);
        tmp.put(KEY_ORDER, orderProcessRecord);
        tmp.put(KEY_CHOICE, choice);
        headers = new MessageHeaders(tmp);
    }
}
