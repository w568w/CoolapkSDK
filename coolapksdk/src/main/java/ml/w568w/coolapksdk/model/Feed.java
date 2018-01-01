package ml.w568w.coolapksdk.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by w568w on 17-12-30.
 */

public class Feed {
    public BaseUser user;
    public String message;
    public int rank_score;
    public String type;
    public ArrayList<BaseUser> likeList;

    public Feed(BaseUser user, String message, int rank_score, String type, ArrayList<BaseUser> likeList) {
        this.user = user;
        this.message = message;
        this.rank_score = rank_score;
        this.type = type;
        this.likeList = likeList;
    }

    public static Feed parseFrom(JSONObject originData) throws JSONException {
        
        JSONObject data=originData.optJSONObject("data");
        ArrayList<BaseUser> list=new ArrayList<>();
        JSONArray likeList=data.optJSONArray("userLikeList");
        for(int index=0;index<likeList.length();index++){
            JSONObject user=likeList.optJSONObject(index);
            list.add(new BaseUser(user.optString("username"),
                    user.optInt("uid",-1),
                    user.optString("userAvatar")));
        }
        return new Feed(new BaseUser(data.optString("username"),data.optInt("uid",-1),data.optString("userAvatar")),
                data.optString("message"),
                data.optInt("rank_score"),
                data.optString("feedType"),
                list);
    }
}
