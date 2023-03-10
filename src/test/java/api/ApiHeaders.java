package api;

import utils.PropertiesUtils;

import java.util.HashMap;
import java.util.Map;

public class ApiHeaders {
    Map<String, String> headers = new HashMap<>();
    PropertiesUtils propertiesUtils = new PropertiesUtils();

    public Map<String, String> gorestHeaders(String token) {
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", token);
        return headers;
    }
}
