package org.ljl.look.api.util;

import java.util.UUID;

public class UuidTool {
    public static String getValue(){
        return UUID.randomUUID().toString();
    }
}
