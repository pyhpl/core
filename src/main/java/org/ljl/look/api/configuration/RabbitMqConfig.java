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

    @Bean
    Queue topicFocusQueue() {
        return new Queue(ConstConfig.QUEUE_TOPIC_FOCUS);
    }

    @Bean
    Queue topicAuditQueue() {
        return new Queue(ConstConfig.QUEUE_TOPIC_AUDIT);
    }

    @Bean
    Queue messageQueue() {
        return new Queue(ConstConfig.QUEUE_MESSAGE);
    }

    @Bean
    Queue activityQueue() {
        return new Queue(ConstConfig.QUEUE_ACTIVITY);
    }

    @Bean
    Queue joinQueue() {
        return new Queue(ConstConfig.QUEUE_JOIN);
    }

}
