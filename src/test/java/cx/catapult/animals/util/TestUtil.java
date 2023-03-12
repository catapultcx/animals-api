package cx.catapult.animals.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

    private TestUtil() {

    }

    public static String javaToJson(final Object object) throws JsonProcessingException {
	return new ObjectMapper().writeValueAsString(object);
    }

    public static <T> T jsonToJava(String json, final Class<T> valueType) throws JsonProcessingException {
	return new ObjectMapper().readValue(json, valueType);
    }
}
