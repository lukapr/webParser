package springboot.rabbit;

import lombok.Getter;
import messages.RMQMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.Task;
import springboot.TaskRepository;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@Service
@Getter
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);
    @Autowired
    private TaskRepository repository;

    @RabbitListener(queues = "ui-response")
    public void receiveMessage(RMQMessage message) throws Exception {
        System.out.println("Received <" + message + ">");
        final Task task = Optional.ofNullable(this.getRepository().findOne(message.getId()))
                .orElseThrow(() -> new Exception("Can't find task with ID = " + message.getId()));
        task.setStatus(message.getMessage());
        getRepository().save(task);
    }

}
