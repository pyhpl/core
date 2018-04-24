package org.ljl.look.api.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstConfig {
    public static final String DEFAULT_VISITOR_OPEN_ID = "os0pV4xLjlAf20xsQkycdWk-vVe0";
    public static final String DEFAULT_VISITOR_TOKEN = "c13eb7a0-21ab-4e74-b121-970c030f7071";
    public static final short VALID = (short) 1;
    public static final short UNVALID = (short) 0;
    public static final String NONE_USER = "os0pV4xNonEf20xsQkycdWk-vVe0";
    public static final String SINGLE_DISCUSSION = "e2444628-5bea-4944-9867-bf92d6a90b36";

    /** feature */
    public static final String FEATURE = "feature";
    public static final String JOIN_FEATURE = "join";
    public static final String FOCUS_FEATURE = "focus";
    public static final String PUBLISH_FEATURE = "publish";

    /** state */
    public static final String WAITING_AUDITED = "waitingAudited";
    public static final short PASS_AUDIT_STATE = (short) 1;
    public static final String WAITING_AUDIT = "waitingAudit";

    /** queue */
    public static final String QUEUE_TOPIC = "queueTopic";
    public static final String QUEUE_TOPIC_AUDIT = "queueTopicAudit";
    public static final String QUEUE_ACTIVITY_AUDIT = "queueActivityAudit";
    public static final String QUEUE_TOPIC_FOCUS = "queueTopicFocus";
    public static final String QUEUE_MESSAGE = "queueMessage";

    /** message type */
    public static final short AUDIT_MESSAGE = (short) 1;
}
