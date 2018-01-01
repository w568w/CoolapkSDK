package m;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import ml.w568w.coolapksdk.model.Feed;
import ml.w568w.coolapksdk.model.User;
import ml.w568w.coolapksdk.sdk.CoolapkSDK;
import ml.w568w.coolapksdk.util.CoolapkUtils;
import ml.w568w.coolapksdk.util.LoginUtils;


public class a extends Activity {

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    protected void onStart() {
        super.onStart();


        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editText = new EditText(this);
        final EditText editText2 = new EditText(this);
        editText.setHint("Input your name");
        editText2.setHint("Input your password");
        Button button = new Button(this);
        button.setText("Fuck signing in!");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoolapkSDK.createInstance(getApplication())
                        .login(a.this, editText.getText().toString(), editText2.getText().toString(), new CoolapkSDK.IUiListener() {
                            @Override
                            public void onComplete(User response) {
                                Toast.makeText(a.this, "Login success! uid->" + response.uid + ",fans num->" + response.fans + ",feeds num->" + response.feeds + ",bio->" + response.bio, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(a.this, "Uuuh,Login failed!", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        });
            }
        });
        linearLayout.addView(editText);
        linearLayout.addView(editText2);
        linearLayout.addView(button);


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

        //finish();

    }

}
