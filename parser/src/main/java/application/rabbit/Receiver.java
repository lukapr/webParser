package application.rabbit;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(List<String> message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
