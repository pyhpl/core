package org.ljl.look.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicWithAudit {
    // topic
    private String topicUuid;
    private String name;
    private String description;
    private String image;
    private String parentTopicUuid;

    // audit
    private String auditUuid;
    private String suggestion;
    private short state;
}
