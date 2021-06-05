package handbook.statemachine.orderexample.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ToString
public class OrderProcessRecord {

    @Getter
    private String orderProcessId;

    @Getter
    @Setter
    private OrderProcessRecordStatus processStatus;

    @Getter
    private List<PaymentInfo> paymentInfos;

    public OrderProcessRecord(){
        this.orderProcessId = Long.toString(new Random().nextLong());
        this.processStatus = OrderProcessRecordStatus.INIT;
    }

    public void initPayInfo(int payNum){
        paymentInfos = new ArrayList<>(payNum);
        for(int i = 0; i < payNum; i++){
            paymentInfos.add(new PaymentInfo());
        }
        this.processStatus = OrderProcessRecordStatus.PAY_READY;
    }

    @ToString
    public static class PaymentInfo{

        @Getter
        private String orderPayId;

        @Getter
        private String paymentPayId;

        PaymentInfo(){
            this.orderPayId = new ObjectId().toString();
        }

        public void finish(String paymentPayId){
            this.paymentPayId = paymentPayId;
        }
    }
}
