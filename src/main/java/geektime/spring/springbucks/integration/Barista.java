package geektime.spring.springbucks.integration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Barista {
    String NEW_ORDERS = "newOrders";
    String FINISHED_ORDERS = "finishedOrders";

    @Input(FINISHED_ORDERS)
    SubscribableChannel finishedOrders();

    @Output(NEW_ORDERS)
    MessageChannel newOrders();
}
