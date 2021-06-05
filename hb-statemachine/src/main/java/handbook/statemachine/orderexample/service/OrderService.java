package handbook.statemachine.orderexample.service;

import handbook.statemachine.orderexample.factory.OrderFactory;
import handbook.statemachine.orderexample.model.OrderProcessRecord;
import handbook.statemachine.orderexample.model.OrderProcessRecordStatus;
import handbook.statemachine.orderexample.repository.OrderProcessRecordRepository;
import handbook.statemachine.orderexample.stm.OrderEvent;
import handbook.statemachine.orderexample.stm.StmBuilder;
import handbook.statemachine.orderexample.stm.guard.PaidGuard;
import handbook.statemachine.orderexample.stm.message.PaidMessage;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {

    public static class PayInfoDTO{

        @Getter
        private String orderPayId;

        @Getter
        private String paymentPayId;

        PayInfoDTO(String orderPayId){
            this.orderPayId = orderPayId;
            this.paymentPayId = "pay_" + new ObjectId();
        }
    }

    @Resource
    private OrderFactory orderFactory;

    @Resource
    private OrderProcessRecordRepository orderProcessRecordRepository;

    @Resource
    private StateMachinePersister<OrderProcessRecordStatus, OrderEvent, OrderProcessRecord> stmPersister;

    public void processTest() throws Exception {

        StateMachine<OrderProcessRecordStatus, OrderEvent> stm = StmBuilder.buildMachine();
        stm.start();
        OrderProcessRecord orderProcessRecord;
        PaidGuard.Choice choice;

        // 1 init and save
        orderProcessRecord = orderFactory.initOrder();
        String orderId = orderProcessRecord.getOrderProcessId();
        orderProcessRecordRepository.save(orderProcessRecord);

        // 2 place order
        orderProcessRecord = orderProcessRecordRepository.findByOrderProcessId(orderId);
        orderFactory.createOrder(orderProcessRecord, 2);
        orderProcessRecordRepository.save(orderProcessRecord);

        // 3 paid once
        System.out.println("paid once ========================");
        orderProcessRecord = orderProcessRecordRepository.findByOrderProcessId(orderId);
        stmPersister.restore(stm, orderProcessRecord);
        PayInfoDTO payInfoDTO = new PayInfoDTO(orderProcessRecord.getPaymentInfos().get(0).getOrderPayId());
        choice = PaidGuard.evaluateChoice(orderProcessRecord, payInfoDTO);
        stm.sendEvent(new PaidMessage(orderProcessRecord, payInfoDTO, choice));
        orderProcessRecordRepository.save(orderProcessRecord);
        System.out.println("current state: " + stm.getState().toString());

        // 4 same paid
        System.out.println("same paid ========================");
        orderProcessRecord = orderProcessRecordRepository.findByOrderProcessId(orderId);
        choice = PaidGuard.evaluateChoice(orderProcessRecord, payInfoDTO);
        stm.sendEvent(new PaidMessage(orderProcessRecord, payInfoDTO, choice));
        orderProcessRecordRepository.save(orderProcessRecord);
        System.out.println("current state: " + stm.getState().toString());

        // 5 unknown paid
        System.out.println("unknown paid ========================");
        orderProcessRecord = orderProcessRecordRepository.findByOrderProcessId(orderId);
        payInfoDTO = new PayInfoDTO(new ObjectId().toString());
        choice = PaidGuard.evaluateChoice(orderProcessRecord, payInfoDTO);
        stm.sendEvent(new PaidMessage(orderProcessRecord, payInfoDTO, choice));
        orderProcessRecordRepository.save(orderProcessRecord);
        System.out.println("current state: " + stm.getState().toString());

        // 6 second paid
        System.out.println("second paid ========================");
        orderProcessRecord = orderProcessRecordRepository.findByOrderProcessId(orderId);
        payInfoDTO = new PayInfoDTO(orderProcessRecord.getPaymentInfos().get(1).getOrderPayId());
        choice = PaidGuard.evaluateChoice(orderProcessRecord, payInfoDTO);
        stm.sendEvent(new PaidMessage(orderProcessRecord, payInfoDTO, choice));
        orderProcessRecordRepository.save(orderProcessRecord);
        System.out.println("current state: " + stm.getState().toString());

    }
}
