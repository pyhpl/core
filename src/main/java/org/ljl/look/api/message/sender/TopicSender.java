package org.ljl.look.api.message.sender;

import org.ljl.look.api.configuration.ConstConfig;
import org.ljl.look.api.entity.Topic;
import org.ljl.look.api.message.wrapper.Message;
import org.ljl.look.api.util.JsonTool;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendToUpdate(Topic topic) {
        rabbitTemplate.convertAndSend(
                ConstConfig.QUEUE_TOPIC,
                JsonTool.toJson(
                        Message.builder().method(Message.MessageMethod.PUT).body(topic).build()
                )
        );
    }

}
