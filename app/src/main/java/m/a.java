package m;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.coolapk.market.util.AuthUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import ml.w568w.coolapksdk.util.ApplicationProxy;
import ml.w568w.coolapksdk.util.CoolapkUtils;


public class a extends Activity {
    boolean fuck = true;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    protected void onStart() {
        super.onStart();


        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        final EditText editText = new EditText(this);
//        final EditText editText2 = new EditText(this);
//        editText.setHint("Input your name");
//        editText2.setHint("Input your password");
        Button button = new Button(this);
        button.setText("Fuck stop!");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ml.w568w.coolapksdk.util.L.l(new CoolapkUtils(getApplication()).apkDownloaded("149408"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
//                CoolapkSDK.createInstance(getApplication())
//                        .login(a.this, editText.getText().toString(), editText2.getText().toString(), new CoolapkSDK.IUiListener() {
//                            @Override
//                            public void onComplete(User response) {
//                                Toast.makeText(a.this, "Login success! uid->" + response.uid + ",fans num->" + response.fans + ",feeds num->" + response.feeds + ",bio->" + response.bio, Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onError(Exception e) {
//                                Toast.makeText(a.this, "Uuuh,Login failed!", Toast.LENGTH_SHORT).show();
//                                e.printStackTrace();
//                            }
//                        });
            }
        });
//        linearLayout.addView(editText);
//        linearLayout.addView(editText2);
        linearLayout.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(a.this, "已停止。想重新开始，请重启程序，", Toast.LENGTH_SHORT).show();
                fuck = false;
            }
        });

//        editText2.setHint("Input a feed");
//        Button button2=new Button(this);
//        button2.setText("Fuck it too!");
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        CoolapkUtils util=new CoolapkUtils(getApplication());
//                        final Feed feed;
//                        try {
//                            feed = util.getFeedById(editText2.getText().toString());
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(a.this, feed.user.userName+" 的評論: "+feed.message, Toast.LENGTH_LONG).show();
//                                }
//                            });
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//            }
//        });
//
//        linearLayout.addView(editText2);
//        linearLayout.addView(button2);
        setContentView(linearLayout);
        new Thread(new Runnable() {
            ApplicationProxy proxy = new ApplicationProxy(getApplication());

            private String get() {
                return AuthUtils.getAS(proxy, UUID.randomUUID().toString());
            }

            @SuppressWarnings("InfiniteLoopStatement")
            @Override
            public void run() {
                try {
                    new CoolapkUtils(getApplication()).signUp("w568w_testing11","wwwwww");
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                while (fuck) {
//                    try {
//                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://l.w568w.ml/api/coolapk.php").openConnection();
//                        httpURLConnection.setRequestMethod("POST");
//                        httpURLConnection.setDoOutput(true);
//                        OutputStream outputStream = httpURLConnection.getOutputStream();
//                        outputStream.write(("token=" + get()).getBytes("utf8"));
//                        outputStream.close();
//                        CoolapkUtils.inputStream2String(httpURLConnection.getInputStream());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }).start();
        Toast.makeText(this, "Start fucking,desudesudesu~", Toast.LENGTH_SHORT).show();
        //finish();

    }

}
