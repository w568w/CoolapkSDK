package ml.w568w.bcctomesdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BccToMeSDK {

    public String getmMail() {
        return mMail;
    }

    String mMail;
    /**
     * 所有的請求的Header
     */
    public static HashMap<String, String> headers = new HashMap<>();
    protected static HashMap<String, String> headers2 = new HashMap<>();
    String lang,mail,time;
    static {
        headers.put("Host", "mail.bccto.me");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:58.0) Gecko/20100101 Firefox/58.0");
        headers.put("Accept", "*/*; q=0.8");
        headers.put("Accept-Encoding", "deflate");
        headers.put("Referer", "http://mail.bccto.me/");
        headers.put("Connection", "keep-alive");
        headers.put("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3");
    }
    static {
        headers2.put("Host", "mail.bccto.me");
        headers2.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:58.0) Gecko/20100101 Firefox/58.0");
        headers2.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers2.put("Accept-Encoding", "deflate");
        headers2.put("Referer", "http://mail.bccto.me/");
        headers2.put("Connection", "keep-alive");
        headers2.put("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3");
    }
    public static BccToMeSDK init() throws IOException {
        return new BccToMeSDK();

    }

    private BccToMeSDK() throws IOException {
        mMail = getRandomString(8);
        HttpURLConnection httpURLConnection = getConnection("http://mail.bccto.me/?lang=cn");
        httpURLConnection.connect();
        Map<String, List<String>> cookies = httpURLConnection.getHeaderFields();
        lang = getBetween(cookies.get("Set-Cookie").get(0), "lang=", ";");
        mail = getBetween(cookies.get("Set-Cookie").get(1), "mail=", ";");
        httpURLConnection.disconnect();

        String body = "mail=" + mMail + "%40mail.bccto.me";
        httpURLConnection = getConnection("http://mail.bccto.me/applymail");
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Cookie", "lang=" + lang + "; mail=" + mail);
        httpURLConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        httpURLConnection.setRequestProperty("Content-Length", body.length()+"");

        httpURLConnection.setDoOutput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();

        outputStream.write(body.getBytes());
        outputStream.flush();
        outputStream.close();
        cookies = httpURLConnection.getHeaderFields();
        lang = getBetween(cookies.get("Set-Cookie").get(0), "lang=", ";");
        time = getBetween(cookies.get("Set-Cookie").get(1), "time=", ";");


        System.out.println(inputStream2String(httpURLConnection.getInputStream()));
    }
    public String getMail() throws IOException {
        String body = "mail=" + mMail + "%40mail.bccto.me&time=0&_=" + System.currentTimeMillis();

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://mail.bccto.me/getmail").openConnection();
        for (String key : headers2.keySet()) {
            httpURLConnection.setRequestProperty(key, headers2.get(key));
        }
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Cookie", "lang=" + lang + "; mail=" + mail + "; time=" + time);
        httpURLConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpURLConnection.setRequestProperty("Content-Length", body.length()+"");
        httpURLConnection.setDoOutput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();

        outputStream.write(body.getBytes());
        outputStream.flush();
        outputStream.close();
        return inputStream2String(httpURLConnection.getInputStream());
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

    protected static HttpURLConnection getConnection(String url) throws IOException {
        HttpURLConnection httpsURLConnection = (HttpURLConnection) new URL(url).openConnection();
        for (String key : headers.keySet()) {
            httpsURLConnection.setRequestProperty(key, headers.get(key));
        }
        return httpsURLConnection;
    }

    protected static String getBetween(String str, String left, String right) throws StringIndexOutOfBoundsException {
        String sssString = str.substring(left.length() + str.indexOf(left));
        return sssString.substring(0, sssString.indexOf(right));
    }

    //http://www.jb51.net/article/125200.htm
    public static String getRandomString(int length) {
        //定义一个字符串（A-Z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiop1234567890";
        //由Random生成随机数
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(str.length());
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    public static void main(String[] args) {
//        try {
//            BccToMeSDK sdk=BccToMeSDK.init();
//            System.out.println(sdk.getMail());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
    static class Timer{
        private static long time;
        private static long nanoTime;
        public void start(){
            time=System.currentTimeMillis();
            nanoTime=System.nanoTime();

        }
        public void end(){
            System.out.println("Millis:"+(System.currentTimeMillis()-time));
            System.out.println("Nano:"+(System.nanoTime()-nanoTime));
            System.out.println("------------");
        }

    }
}
