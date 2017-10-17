package application.rabbit;

import lombok.Getter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    public static final String QUEUE_NAME = "ui-response";
    public void sendMessage(String message) {
        System.out.println("Sending message...");
        getRabbitTemplate().convertAndSend(QUEUE_NAME, message);
    }
}
