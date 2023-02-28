package user;

import io.cucumber.messages.internal.com.google.gson.Gson;
import org.json.JSONObject;
import org.json.XML;

public record UsersRecord(String email, String name, String gender, String status) {
    public JSONObject getJson() {
        return new JSONObject(new Gson().toJson(this));
    }

    public String getXml() {
        return XML.toString(getJson());
    }
}
