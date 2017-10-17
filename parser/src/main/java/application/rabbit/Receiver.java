package application.rabbit;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import application.parser.Parser;
import com.rabbitmq.client.Channel;
import lombok.Getter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Getter
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);
    @Autowired
    private Parser parser;

    @RabbitListener(queues = "ui-request")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");

        try {
            getParser().parse(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
