package org.ljl.look.api.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    Queue topicQueue() {
        return new Queue(ConstConfig.QUEUE_TOPIC);
    }

}
