package org.ljl.look.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullActivityWithAudit {

    // full activity
    private FullActivity fullActivity;

    // audit
    private String suggestion;
    private short state;
}
