package org.ljl.look.api.message.sender;

import org.ljl.look.api.configuration.ConstConfig;
import org.ljl.look.api.entity.ActivityAudit;
import org.ljl.look.api.message.wrapper.MessageWrapper;
import org.ljl.look.api.util.JsonTool;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityAuditSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendToUpdate(ActivityAudit activityAudit) {
        rabbitTemplate.convertAndSend(
                ConstConfig.QUEUE_ACTIVITY_AUDIT,
                JsonTool.toJson(
                        MessageWrapper.builder().method(MessageWrapper.MessageMethod.PUT).body(activityAudit).build()
                )
        );
    }
}
