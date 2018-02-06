package ml.w568w.coolapksdk.model;

import org.json.JSONObject;

/**
 * Created by w568w on 18-1-1.
 */

public class Notification {
    /**
     * 消息的ID
     */
    public int id;

    /**
     * 消息来源用户
     */
    public BaseUser from;

    /**
     * 不清楚，应该是hash一类的
     */
    public String slug;

    /**
     * 0为回复，1为关注
     */
    public int list_group;

    /**
     * 值为comment_reply/feed_reply/contacts_follow等
     */
    public String type;

    /**
     * 10位Un*x时间戳
     */
    public long time;

    /**
     * 消息内容
     */
    public String note;

    public Notification(int id, BaseUser from, String slug, int list_group, String type, long time, String note) {
        this.id = id;
        this.from = from;
        this.slug = slug;
        this.list_group = list_group;
        this.type = type;
        this.time = time;
        this.note = note;
    }

    public static Notification parseFrom(JSONObject origin) {
        return new Notification(origin.optInt("id"), new BaseUser(origin.optString("fromusername"), origin.optInt("fromuid"), origin.optString("fromUserAvatar")), origin.optString("slug"), origin.optInt("list_group"), origin.optString("type"), origin.optInt("dateline"), origin.optString("note"));
    }
}
