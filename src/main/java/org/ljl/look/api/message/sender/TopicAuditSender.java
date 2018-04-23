package org.ljl.look.api.message.sender;

import org.ljl.look.api.configuration.ConstConfig;
import org.ljl.look.api.entity.TopicAudit;
import org.ljl.look.api.message.wrapper.Message;
import org.ljl.look.api.util.JsonTool;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicAuditSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendToUpdate(TopicAudit topicAudit) {
        rabbitTemplate.convertAndSend(
                ConstConfig.QUEUE_TOPIC_AUDIT,
                JsonTool.toJson(
                        Message.builder().method(Message.MessageMethod.PUT).body(topicAudit).build()
                )
        );
    }
}