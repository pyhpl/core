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
public class Discussion {
    private String uuid;
    private String belongToActivity;
    private String belongToDiscussion;
    private String fromUser;
    private String toUser;
    private String contents;
    private int likeCount;
    private int dislikeCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")//存日期时使用
    private Date discussDate;
    private short valid;
}













