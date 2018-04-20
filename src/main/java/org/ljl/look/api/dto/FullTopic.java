package org.ljl.look.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createDate;

    // 用户是否已关注
    private boolean isFocused;
    private String topicFocusUuid;
}
