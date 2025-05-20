package task14_4_1.zadanye3.services;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Component
public class TestService {

    public String appleName = "Apple"; // Объявление переменной appleName

    Map<String, Object> body;

    public boolean setAppleName(String appleName) {
        this.appleName = appleName.toUpperCase();
        return true;
    }

    public boolean setObject(@RequestBody() Map<String, Object> body) {
        this.body = body;
        return true;
    }

    public Map<String, Object> getObject() {
        return body;
    }

    public Map<String, Object> getLower() {
        Map<String, Object> ret = new HashMap<>();
        for (final Map.Entry<String, Object> pair : body.entrySet()) {
            Object value = pair.getValue();
            if (value instanceof String) {
                value = ((String) value).toLowerCase();
            }
            ret.put(pair.getKey(), value);
        }
        return ret;
    }

    public Map<String, Object> getUpper() {
        Map<String, Object> ret = new HashMap<>();
        for (final Map.Entry<String, Object> pair : body.entrySet()) {
            Object value = pair.getValue();
            if (value instanceof String) {
                value = ((String) value).toUpperCase();
            }
            ret.put(pair.getKey(), value);
        }
        return ret;
    }

}