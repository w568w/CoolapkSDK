package com.coolapk.market.util;

import android.content.Context;

/**
 * Created by w568w on 17-12-29.
 */

public class AuthUtils {
    static {
        System.loadLibrary("a");
    }

    /**
     * @param paramContext Application上下文
     * @param paramString 一個UUID
     * @return 用於請求的Token
     */
    @SuppressWarnings("JniMissingFunction")
    public static native String getAS(Context paramContext, String paramString);
}
