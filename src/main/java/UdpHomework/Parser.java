package UdpHomework;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

@Slf4j
final public class Parser {
    private static ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SneakyThrows
    public  <T> String toJson(T obj) {
        String result = mapper.writeValueAsString(obj);
        return result;
    }

    @SneakyThrows
   public <T> T fromJson(String content, Class<T> clazz) {
        T result = mapper.readValue(content, clazz);
        return result;
    }
}

