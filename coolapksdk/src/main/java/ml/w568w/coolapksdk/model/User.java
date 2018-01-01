package ml.w568w.coolapksdk.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by w568w on 17-12-30.
 */

public class User extends BaseUser {
    public String bio;
    public String userGroupName;
    public String groupName;
    public String province;
    public String city;
    public String weibo;
    public boolean isDeveloper;
    public int feeds;
    public int fans;
    public int follow;
    public int apkFollowNum;
    public int apkRatingNum;
    public int albumNum;
    public int discoveryNum;

    public User(String userName, int uid, String avatarUrl, String bio, String userGroupName, String groupName, String province, String city, String weibo, boolean isDeveloper, int feeds, int fans, int follow, int apkFollowNum, int apkRatingNum, int albumNum, int discoveryNum) {
        super(userName, uid, avatarUrl);
        this.bio = bio;
        this.userGroupName = userGroupName;
        this.groupName = groupName;
        this.province = province;
        this.city = city;
        this.weibo = weibo;
        this.isDeveloper = isDeveloper;
        this.feeds = feeds;
        this.fans = fans;
        this.follow = follow;
        this.apkFollowNum = apkFollowNum;
        this.apkRatingNum = apkRatingNum;
        this.albumNum = albumNum;
        this.discoveryNum = discoveryNum;
    }

    public static User parseFrom(JSONObject originData) throws JSONException {
        JSONObject data=originData.optJSONObject("data");
        return new User(data.optString("username"),
                data.optInt("uid"),
                data.optString("userAvatar"),
                data.optString("bio"),
                data.optString("userGroupName"),
                data.optString("groupName"),
                data.optString("province"),
                data.optString("city"),
                data.optString("weibo"),
                data.optInt("isDeveloper",0)==1,
                data.optInt("feed"),
                data.optInt("fans"),
                data.optInt("follow"),
                data.optInt("apkFollowNum"),
                data.optInt("apkRatingNum"),
                data.optInt("albumNum"),
                data.optInt("discoveryNum"));
    }
}
