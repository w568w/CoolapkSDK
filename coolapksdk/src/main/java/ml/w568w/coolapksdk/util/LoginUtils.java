package ml.w568w.coolapksdk.util;

import android.app.Application;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import ml.w568w.coolapksdk.exceptions.LoginFailedException;
import ml.w568w.coolapksdk.model.LoginInfo;

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

    public LoginInfo login(String userName, String password) throws JSONException, IOException,LoginFailedException {
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
            httpsURLConnection = (HttpsURLConnection) new URL("https://api.coolapk.com/v6/account/accessToken?code=" + code).openConnection();
            httpsURLConnection.setRequestMethod("GET");
            for (String key : headers.keySet()) {
                httpsURLConnection.setRequestProperty(key, headers.get(key));
            }
            String loginInfo = inputStream2String(httpsURLConnection.getInputStream());
            mInfo = LoginInfo.parseFrom(new JSONObject(loginInfo), sessionId);
            return mInfo;
        }
        catch (StringIndexOutOfBoundsException e){
            throw new LoginFailedException();
        }
    }

    public void getNotificationList(int page) {
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL("https://api.coolapk.com/v6/notification/list?page=" + page).openConnection();
            for (String key : headers.keySet()) {
                httpsURLConnection.setRequestProperty(key, headers.get(key));
            }
            httpsURLConnection.setRequestProperty("Cookie", "token=" + mInfo.token + ";uid="+ mInfo.uid + ";username=" + mInfo.userName + ";SESSID=" + mInfo.sessionId + ";");
            Map a=httpsURLConnection.getRequestProperties();
            httpsURLConnection.setRequestMethod("GET");
            String loginInfo = inputStream2String(httpsURLConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
