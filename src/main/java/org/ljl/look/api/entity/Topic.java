package org.ljl.look.api.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    private String uuid;
    private String name;
    private String description;
    private String image;
    private String parentTopicUuid;
    private String createUser;
    private Date createDate;
    private short valid;
}
