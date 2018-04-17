package org.ljl.look.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullTopic {
    private String uuid;
    private String name;
    private String description;
    private String image;

    // 用户是否已关注
    private boolean isFocused;
    private String topicFocusUuid;
}
