package org.ljl.look.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.ljl.look.api.entity.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullActivity {
    private String uuid;
    private String title;
    private String detail;
    private String school;
    private String place;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")  //取日期时使用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")//存日期时使用
    private Date deadline;
    private int limitUserCount;
    private short contactType;
    private String contactRepresent;
    private int likeCount; // 点赞数

    // 所属主题
    private String topicUuid;
    private String topicName;

    // 发布用户数据
    private String publishUserName;
    private String publishUserAvatar;

    // 额外属性
    private List<String> activityImageUrls;
}
