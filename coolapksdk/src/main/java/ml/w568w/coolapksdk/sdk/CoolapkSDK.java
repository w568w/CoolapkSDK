package ml.w568w.coolapksdk.sdk;

import android.app.Activity;
import android.app.Application;

import ml.w568w.coolapksdk.model.LoginInfo;
import ml.w568w.coolapksdk.model.User;
import ml.w568w.coolapksdk.util.LoginUtils;

/**
 * Created by w568w on 18-1-1.
 */

public class CoolapkSDK {
    public interface IUiListener {
        void onComplete(User response);

        void onError(Exception e);
    }

    private LoginUtils coolapkUtils;

    public static CoolapkSDK createInstance(Application application) {
        return new CoolapkSDK(application);
    }

    private CoolapkSDK(Application application) {
        coolapkUtils = new LoginUtils(application);
    }

    public void login(final Activity context, final String username, final String password, final IUiListener callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    LoginInfo loginInfo = coolapkUtils.login(username, password);
                    final User u = coolapkUtils.getUserProfileByUid(loginInfo.uid);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onComplete(u);
                        }
                    });

                } catch (final Exception e) {
                    e.printStackTrace();
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e);
                        }
                    });
                }
            }
        }).start();
    }
}
