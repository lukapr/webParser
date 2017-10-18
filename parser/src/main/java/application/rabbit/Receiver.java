package application.rabbit;

import java.util.concurrent.CountDownLatch;

import application.parser.ParserImpl;
import lombok.Getter;
import messages.RMQMessage;
import messages.Status;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);
    @Autowired
    private ParserImpl parser;

    @Autowired
    private Producer producer;

    @RabbitListener(queues = "ui-request")
    public void receiveMessage(RMQMessage message) {
        System.out.println("Received <" + message + ">");
        RMQMessage outputMessage = new RMQMessage();
        outputMessage.setId(message.getId());
        try {
            getParser().parse(message.getMessage());
            outputMessage.setMessage(Status.SUCCESS.name());
        } catch (Exception e) {
            outputMessage.setMessage(Status.FAILED.name());
            e.printStackTrace();
        }
        getProducer().sendMessage(outputMessage);
        latch.countDown();
    }
}
