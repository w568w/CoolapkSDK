package ml.w568w.coolapksdk.util;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import ml.w568w.coolapksdk.exceptions.LoginFailedException;
import ml.w568w.coolapksdk.model.LoginInfo;
import ml.w568w.coolapksdk.model.Notification;

/**
 * Created by w568w on 18-1-1.
 */

public class LoginUtils extends CoolapkUtils {
    private LoginInfo mInfo;

    public LoginUtils(Application application) {
        super(application);
    }

    public LoginInfo getLoginInfo() {
        return mInfo;
    }

    public void setLoginInfo(LoginInfo mInfo) {
        this.mInfo = mInfo;
    }

    public LoginInfo login(String userName, String password) throws JSONException, IOException, LoginFailedException {
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL("https://account.coolapk.com/auth/login?forward=https%3A%2F%2Fdeveloper.coolapk.com").openConnection();
            httpsURLConnection.setRequestMethod("GET");
            for (String key : webHeaders.keySet()) {
                httpsURLConnection.setRequestProperty(key, webHeaders.get(key));
            }
            httpsURLConnection.connect();
            Map<String, List<String>> cookies = httpsURLConnection.getHeaderFields();

            final String sessionId = getBetween(cookies.get("Set-Cookie").get(0), "SESSID=", ";");
            String requestHash = getBetween(inputStream2String(httpsURLConnection.getInputStream()), "data-request-hash=\"", "\">");
            httpsURLConnection.disconnect();

            String body = "submit=1&requestHash=" + requestHash + "&type=&forward=https%3A%2F%2Fdeveloper.coolapk.com&login=" + userName + "&password=" + password;
            httpsURLConnection = (HttpsURLConnection) new URL("https://account.coolapk.com/auth/login").openConnection();
            httpsURLConnection.setRequestMethod("POST");
            for (String key : webHeaders2.keySet()) {
                httpsURLConnection.setRequestProperty(key, webHeaders2.get(key));
            }
            httpsURLConnection.setRequestProperty("Cookie", "SESSID=" + sessionId + "; forward=https%3A%2F%2Fdeveloper.coolapk.comm");
            httpsURLConnection.setDoOutput(true);
            OutputStream outputStream = httpsURLConnection.getOutputStream();
            outputStream.write(body.getBytes("utf8"));
            outputStream.close();
            String response = inputStream2String(httpsURLConnection.getInputStream());

            String code = getBetween(response, "access_token&code=", "&message=");

            String loginInfo = inputStream2String(getConnection("https://api.coolapk.com/v6/account/accessToken?code=" + code).getInputStream());
            mInfo = LoginInfo.parseFrom(new JSONObject(loginInfo), sessionId);
            return mInfo;
        } catch (StringIndexOutOfBoundsException e) {
            throw new LoginFailedException();
        }
    }

    /**
     * @param page 页数，从1开始
     */
    public ArrayList<Notification> getNotificationList(int page) {
        try {
            String json = inputStream2String(getSignedConnection("https://api.coolapk.com/v6/notification/list?page=" + page).getInputStream());
            ArrayList<Notification> arrayList = new ArrayList<>();
            JSONArray array = new JSONObject(json).optJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                arrayList.add(Notification.parseFrom(array.optJSONObject(i)));
            }
            return arrayList;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    private HttpsURLConnection getSignedConnection(String url) throws IOException {
        HttpsURLConnection httpsURLConnection = getConnection(url);
        httpsURLConnection.setRequestProperty("Cookie", "token=" + mInfo.token + ";uid=" + mInfo.uid + ";username=" + mInfo.userName + ";SESSID=" + mInfo.sessionId + ";");
        return httpsURLConnection;
    }


}
