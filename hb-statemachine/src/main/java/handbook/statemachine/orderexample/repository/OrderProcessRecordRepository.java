package handbook.statemachine.orderexample.repository;

import handbook.statemachine.orderexample.model.OrderProcessRecord;

public interface OrderProcessRecordRepository {

    void save(OrderProcessRecord order);

    OrderProcessRecord findByOrderProcessId(String orderProcessId);
}
