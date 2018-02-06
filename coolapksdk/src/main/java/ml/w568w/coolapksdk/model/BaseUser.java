package ml.w568w.coolapksdk.model;

/**
 * Created by w568w on 17-12-30.
 */

public class BaseUser{
    public String userName;
    public int uid;
    public String avatarUrl;

    public BaseUser(String userName, int uid, String avatarUrl) {
        this.userName = userName;
        this.uid = uid;
        this.avatarUrl = avatarUrl;
    }


}
