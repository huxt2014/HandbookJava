package handbook.statemachine.orderexample.model;

public enum OrderProcessRecordStatus {

    INIT,

    PAY_READY,

    PAID_CHECK,

    PARTIAL_PAID,

    ALL_PAID,

    DELIVER_READY,

    DELIVERING,

    FINISH

}
