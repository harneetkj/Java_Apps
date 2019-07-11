package ca.jrvs.apps.twitter.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import sun.security.pkcs.ParsingException;

import java.io.IOException;

public class JsonUtil {

    public static String toPrettyJson(Object object) throws ParsingException, JsonProcessingException {
        return toJson(object, true, false);
    }
    public static String toJson(Object object, boolean prettyJson, boolean includeNullValues)
            throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        if (!includeNullValues) {
            m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        if (prettyJson) {
            m.enable(SerializationFeature.INDENT_OUTPUT);
        }
        return m.writeValueAsString(object);
    }
    public static <T> T toObjectFromJson(String json, Class c) throws IOException {
        ObjectMapper map = new ObjectMapper();

        return (T) map.readValue(json, c);
    }
}
