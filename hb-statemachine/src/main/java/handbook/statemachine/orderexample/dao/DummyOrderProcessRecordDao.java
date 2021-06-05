package handbook.statemachine.orderexample.dao;

import handbook.statemachine.orderexample.model.OrderProcessRecord;
import handbook.statemachine.orderexample.repository.OrderProcessRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DummyOrderProcessRecordDao implements OrderProcessRecordRepository {

    private final Map<String, OrderProcessRecord> store = new HashMap<>();

    @Override
    public void save(OrderProcessRecord order) {
        store.put(order.getOrderProcessId(), order);
        System.out.println("save order: " + order);
    }

    @Override
    public OrderProcessRecord findByOrderProcessId(String orderProcessId) {
        System.out.println("find order: " + orderProcessId);
        return store.getOrDefault(orderProcessId, null);
    }
}
