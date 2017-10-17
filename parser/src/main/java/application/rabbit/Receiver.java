package application.rabbit;

import java.util.concurrent.CountDownLatch;

import application.parser.Parser;
import lombok.Getter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);
    @Autowired
    private Parser parser;

    @Autowired
    private Producer producer;

    @RabbitListener(queues = "ui-request")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");

        try {
            getParser().parse(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.sendMessage("300");
        latch.countDown();
    }
}
