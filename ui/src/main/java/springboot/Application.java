package springboot;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springboot.rabbit.Receiver;

@SpringBootApplication
@EnableJpaRepositories
public class Application {

    public static final String QUEUE_NAME = "ui-response";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter ) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory( connectionFactory );
        container.setQueueNames( QUEUE_NAME );
        container.setMessageListener( listenerAdapter );
        container.setAcknowledgeMode( AcknowledgeMode.AUTO );
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter( Receiver receiver ) {
        if( receiver == null ) {
            throw new IllegalArgumentException( "No receiver specified." );
        }
        MessageListenerAdapter adapter = new MessageListenerAdapter( receiver, "receiveMessage" );
        return adapter;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}