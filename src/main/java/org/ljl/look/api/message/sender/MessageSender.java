package org.ljl.look.api.message.sender;

import org.ljl.look.api.configuration.ConstConfig;
import org.ljl.look.api.entity.Message;
import org.ljl.look.api.message.wrapper.MessageWrapper;
import org.ljl.look.api.message.wrapper.MessageWrapper.MessageMethod;
import org.ljl.look.api.util.JsonTool;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendToAdd(Message message) {
        rabbitTemplate.convertAndSend(
                ConstConfig.QUEUE_MESSAGE,
                JsonTool.toJson(
                        MessageWrapper.builder().method(MessageMethod.POST).body(message).build()
                )
        );
    }

}
