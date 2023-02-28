package user;

import io.cucumber.messages.internal.com.google.gson.Gson;
import org.json.JSONObject;

public class UsersConstructor {
    private String name;
    private String gender;
    private String email;
    private String status;


    public UsersConstructor(String name, String gender, String email, String status) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.status = status;
    }

    public JSONObject getJson() {
        return new JSONObject(new Gson().toJson(this));
    }
}
