package org.ljl.look.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullSubDiscussion {
    private String fromUser;
    private String fromUserName;
    private String toUserName;
    private String contents;
}