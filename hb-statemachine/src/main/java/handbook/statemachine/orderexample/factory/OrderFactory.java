package handbook.statemachine.orderexample.factory;

import handbook.statemachine.orderexample.model.OrderProcessRecord;
import org.springframework.stereotype.Service;

@Service
public class OrderFactory {

    public OrderProcessRecord initOrder(){
        return new OrderProcessRecord();
    }

    public void createOrder(OrderProcessRecord orderProcessRecord, int payNum){
        orderProcessRecord.initPayInfo(payNum);
    }
}
