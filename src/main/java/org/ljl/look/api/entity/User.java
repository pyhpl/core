package org.ljl.look.api.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String openId;
    private String name;
    private String avatar;
}
