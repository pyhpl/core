package org.ljl.look.api.message.sender;

import org.ljl.look.api.configuration.ConstConfig;
import org.ljl.look.api.entity.TopicFocus;
import org.ljl.look.api.message.wrapper.Message;
import org.ljl.look.api.util.JsonTool;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicFocusSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendToAdd(TopicFocus topicFocus) {
        rabbitTemplate.convertAndSend(
                ConstConfig.QUEUE_TOPIC_FOCUS,
                JsonTool.toJson(
                        Message.builder().method(Message.MessageMethod.POST).body(topicFocus).build()
                )
        );
    }

}

