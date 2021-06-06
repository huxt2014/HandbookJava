package handbook.statemachine.dto;

import lombok.Getter;
import org.bson.types.ObjectId;

public class PayInfoDTO {

    @Getter
    private String orderPayId;

    @Getter
    private String paymentPayId;

    public PayInfoDTO(String orderPayId) {
        this.orderPayId = orderPayId;
        this.paymentPayId = "pay_" + new ObjectId();
    }
}
