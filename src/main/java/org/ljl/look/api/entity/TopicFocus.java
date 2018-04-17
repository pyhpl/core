package org.ljl.look.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicFocus {
    private String uuid;
    private String fromUser;
    private String topicUuid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")//存日期时使用
    private Date focusDate;
    private short valid;
}
