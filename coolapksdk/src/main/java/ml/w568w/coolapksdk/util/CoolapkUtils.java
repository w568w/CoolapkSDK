package ml.w568w.coolapksdk.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.webkit.CookieManager;

import com.coolapk.market.util.AuthUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import ml.w568w.coolapksdk.model.Feed;
import ml.w568w.coolapksdk.model.LoginInfo;
import ml.w568w.coolapksdk.model.User;


/**
 * Created by w568w on 17-12-29.
 * Main is the most complex.
 */
@SuppressLint("HardwareIds")
public class CoolapkUtils {

    /**
     * 軟引用，防止內存泄漏
     */
    private SoftReference<Application> mApp;
    /**
     * 所有的請求的Header
     */
    protected HashMap<String, String> headers = new HashMap<>();

    /**
     * 轉用於網頁請求的header
     */
    protected static HashMap<String, String> webHeaders = new HashMap<>();
    protected static HashMap<String, String> webHeaders2 = new HashMap<>();

    /**
     * @param application Application，通過getApplication()得到
     */
    static {
        webHeaders.put("Host", "account.coolapk.com");
        webHeaders.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:57.0) Gecko/20100101 Firefox/57.0");
        webHeaders.put("Accept", "*/*;");
        webHeaders.put("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3");
        webHeaders.put("Accept-Encoding", "deflate");
        webHeaders.put("Connection", "keep-alive");
        webHeaders.put("Pragma", "no-cache");
        webHeaders.put("Cache-Control", "no-cache");

        webHeaders2.put("Host", "account.coolapk.com");
        webHeaders2.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:57.0) Gecko/20100101 Firefox/57.0");
        webHeaders2.put("Accept", "application/json, text/javascript, */*; q=0.01");
        webHeaders2.put("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3");
        webHeaders2.put("Accept-Encoding", "deflate");
        webHeaders2.put("Referer", "https://account.coolapk.com/auth/login?forward=https%3A%2F%2Fwww.coolapk.com");
        webHeaders2.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        webHeaders2.put("X-Requested-With", "XMLHttpRequest");
        webHeaders2.put("Accept-Encoding", "deflate");
        webHeaders2.put("Connection", "keep-alive");
        webHeaders2.put("Cache-Control", "no-cache");
        webHeaders2.put("Upgrade-Insecure-Requests", "1");
        webHeaders2.put("Pragma", "no-cache");
    }

    public CoolapkUtils(Application application) {
        String token = AuthUtils.getAS(new ApplicationProxy(application), UUID.randomUUID().toString());
        mApp = new SoftReference<>(application);
        headers.put("User-Agent", getUA());
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("X-Sdk-Int", Build.VERSION.SDK_INT + "");
        headers.put("X-Sdk-Locale", Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry());
        headers.put("X-App-Id", "com.coolapk.market");
        headers.put("X-App-Token", token);
        headers.put("X-App-Version", "7.3");
        headers.put("X-App-Code", "1701135");
        headers.put("X-Api-Version", "7");
        headers.put("X-App-Device", getAppDevice());


    }

    /**
     * 根據User Id獲得用戶信息，失敗返回null
     *
     * @param uid User Id
     * @return
     */
    public User getUserProfileByUid(String uid) {
        L.l(uid);
        try {

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL("https://api.coolapk.com/v6/user/profile?uid=" + uid).openConnection();
            httpsURLConnection.setRequestMethod("GET");
            for (String key : headers.keySet()) {
                httpsURLConnection.setRequestProperty(key, headers.get(key));
            }
            InputStream inputStream = httpsURLConnection.getInputStream();
            return User.parseFrom(new JSONObject(inputStream2String(inputStream)));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根據用戶名獲得用戶信息，失敗返回null
     *
     * @param name 用戶名
     * @return 用戶信息
     */
    public User getUserProfileByName(String name) {
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL("https://www.coolapk.com/n/" + name).openConnection();
            String html = inputStream2String(httpsURLConnection.getInputStream());
            return getUserProfileByUid(getBetween(html, "coolmarket://u/", "\">"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Feed getFeedById(String feedId) {
        try {

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL("https://api.coolapk.com/v6/feed/detail?id=" + feedId).openConnection();
            httpsURLConnection.setRequestMethod("GET");
            for (String key : headers.keySet()) {
                httpsURLConnection.setRequestProperty(key, headers.get(key));
            }
            InputStream inputStream = httpsURLConnection.getInputStream();
            return Feed.parseFrom(new JSONObject(inputStream2String(inputStream)));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;

    }



    protected static String getBetween(String str, String left, String right) throws StringIndexOutOfBoundsException{
        String sssString = str.substring(left.length() + str.indexOf(left));
        return sssString.substring(0, sssString.indexOf(right));
    }

    private String getUA() {
        return System.getProperty("http.agent") +
                " (#Build; " +
                Build.BRAND +
                "; " +
                Build.MODEL +
                "; " +
                Build.DISPLAY +
                "; " +
                Build.VERSION.RELEASE +
                ") +CoolMarket/7.3";

    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private String getAppDevice() {
        return new StringBuilder(Base64.encodeToString((getAndroidId() + "; " + o() + "; " + p() + "; " + q() + "; " + Build.MANUFACTURER + "; " + Build.BRAND + "; " + Build.MODEL).getBytes(), 0)).reverse().toString().replaceAll("\\r\\n|\\r|\\n|=", "");
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private String getAndroidId() {
        return Settings.Secure.getString(mApp.get().getContentResolver(), "android_id");
    }

    private String o() {
        return ((TelephonyManager) mApp.get().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    private String p() {

        return ((TelephonyManager) mApp.get().getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
    }

    private String q() {
        return "02:00:00:00:00:00";
    }

    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        is.close();
        return baos.toString();
    }
}
