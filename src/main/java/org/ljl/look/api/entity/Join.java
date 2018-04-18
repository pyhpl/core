package org.ljl.look.api.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Join {
    private String uuid;
    private String fromUser;
    private String activityUuid;
    private Date joinDate;
    private short valid;
}
