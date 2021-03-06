package handbook.statemachine.orderexample.stm.cola;

import com.alibaba.cola.statemachine.StateMachine;
import handbook.statemachine.dto.PayInfoDTO;
import handbook.statemachine.orderexample.factory.OrderFactory;
import handbook.statemachine.orderexample.model.OrderProcessRecord;
import handbook.statemachine.orderexample.model.OrderProcessRecordStatus;
import handbook.statemachine.orderexample.repository.OrderProcessRecordRepository;
import handbook.statemachine.orderexample.stm.common.OrderEvent;
import handbook.statemachine.orderexample.stm.common.guard.PaidGuard;
import handbook.statemachine.orderexample.stm.common.message.PaidMessage;
import org.bson.types.ObjectId;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderServiceCola {

    @Resource
    private OrderFactory orderFactory;

    @Resource
    private OrderProcessRecordRepository orderProcessRecordRepository;


    public void processTest(){
        StateMachine<OrderProcessRecordStatus, OrderEvent, Message<OrderEvent>> stateMachine
                = StmBuilder.buildMachine();

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
        PayInfoDTO payInfoDTO = new PayInfoDTO(orderProcessRecord.getPaymentInfos().get(0).getOrderPayId());
        choice = PaidGuard.evaluateChoice(orderProcessRecord, payInfoDTO);
        stateMachine.fireEvent(orderProcessRecord.getProcessStatus(), OrderEvent.PAID,
                new PaidMessage(orderProcessRecord, payInfoDTO, choice));
        orderProcessRecordRepository.save(orderProcessRecord);

        // 4 same paid
        System.out.println("same paid ========================");
        orderProcessRecord = orderProcessRecordRepository.findByOrderProcessId(orderId);
        choice = PaidGuard.evaluateChoice(orderProcessRecord, payInfoDTO);
        stateMachine.fireEvent(orderProcessRecord.getProcessStatus(), OrderEvent.PAID,
                new PaidMessage(orderProcessRecord, payInfoDTO, choice));
        orderProcessRecordRepository.save(orderProcessRecord);

        // 5 unknown paid
        System.out.println("unknown paid ========================");
        orderProcessRecord = orderProcessRecordRepository.findByOrderProcessId(orderId);
        payInfoDTO = new PayInfoDTO(new ObjectId().toString());
        choice = PaidGuard.evaluateChoice(orderProcessRecord, payInfoDTO);
        stateMachine.fireEvent(orderProcessRecord.getProcessStatus(), OrderEvent.PAID,
                new PaidMessage(orderProcessRecord, payInfoDTO, choice));
        orderProcessRecordRepository.save(orderProcessRecord);

        // 6 second paid
        System.out.println("second paid ========================");
        orderProcessRecord = orderProcessRecordRepository.findByOrderProcessId(orderId);
        payInfoDTO = new PayInfoDTO(orderProcessRecord.getPaymentInfos().get(1).getOrderPayId());
        choice = PaidGuard.evaluateChoice(orderProcessRecord, payInfoDTO);
        stateMachine.fireEvent(orderProcessRecord.getProcessStatus(), OrderEvent.PAID,
                new PaidMessage(orderProcessRecord, payInfoDTO, choice));
        orderProcessRecordRepository.save(orderProcessRecord);
    }
}
