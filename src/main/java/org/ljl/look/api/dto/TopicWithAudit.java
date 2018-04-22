package org.ljl.look.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicWithAudit {
    // topic
    private String name;
    private String description;
    private String image;

    // audit
    private String suggestion;
    private short state;
}
