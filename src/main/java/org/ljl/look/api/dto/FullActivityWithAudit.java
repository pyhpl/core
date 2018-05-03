package org.ljl.look.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullActivityWithAudit {

    // full activity
    private FullActivity fullActivity;

    // audit
    private String auditUuid;
    private String suggestion;
    private short state;
}
