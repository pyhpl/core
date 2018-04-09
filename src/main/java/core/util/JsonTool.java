package core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class JsonTool {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromJson(String value, Class<T> type) {
        try {
            return (T)  mapper.readValue(value, type);
        } catch (Exception e) {
            log.warn("parse json string error:" + value, e);
            return null;
        }
    }

    public static <T> T fromJson(String value, TypeReference<T> type) {
        try {
            return (T) mapper.readValue(value, type);
        } catch (Exception e) {
            log.warn("parse json string error:" + value, e);
            return null;
        }
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("write to json string error:" + obj, e);
            return null;
        }
    }
}
