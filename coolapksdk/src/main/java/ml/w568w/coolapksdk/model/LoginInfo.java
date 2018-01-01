package ml.w568w.coolapksdk.model;

import org.json.JSONObject;

/**
 * Created by w568w on 17-12-31.
 */

public class LoginInfo {
    public String token;
    public String uid;
    public String sessionId;
    public String userName;

    public LoginInfo(String token, String uid, String sessionId, String userName) {
        this.token = token;
        this.uid = uid;
        this.sessionId = sessionId;
        this.userName = userName;
    }
    public static LoginInfo parseFrom(JSONObject origin,String sessionId){
        JSONObject data=origin.optJSONObject("data");
        return new LoginInfo(data.optString("token"),data.optString("uid"),sessionId,data.optString("username"));
    }
}
