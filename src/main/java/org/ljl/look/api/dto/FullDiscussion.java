package org.ljl.look.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullDiscussion {
    private String uuid;
    private String fromUser;
    private String fromUserName;
    private String fromUserAvatar;
    private String contents;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")//存日期时使用
    private Date discussDate;
    // 下一版本使用
    private int likeCount;
    private int dislikeCount;

    // 下属讨论
    List<FullSubDiscussion> fullSubDiscussions;
}
