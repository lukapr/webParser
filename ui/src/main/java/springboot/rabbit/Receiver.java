package springboot.rabbit;

import lombok.Getter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.Status;
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
    public void receiveMessage(String message) throws Exception {
        System.out.println("Received <" + message + ">");
        final Task task = Optional.ofNullable(this.getRepository().findOne(Long.parseLong(message)))
                .orElseThrow(() -> new Exception("Can't find task with ID = " + Long.parseLong(message)));
        task.setStatus(Status.SUCCESS.name());
    }

}
