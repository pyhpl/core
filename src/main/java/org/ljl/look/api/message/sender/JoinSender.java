package org.ljl.look.api.message.sender;

import org.ljl.look.api.configuration.ConstConfig;
import org.ljl.look.api.entity.Join;
import org.ljl.look.api.message.wrapper.MessageWrapper;
import org.ljl.look.api.util.JsonTool;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JoinSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendToAdd(Join join) {
        rabbitTemplate.convertAndSend(
                ConstConfig.QUEUE_JOIN,
                JsonTool.toJson(
                        MessageWrapper.builder().method(MessageWrapper.MessageMethod.POST).body(join).build()
                )
        );
    }
}
