package com.stfactory.appratermvp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.stfactory.appratermvp.config.Config;


public final class AppUtils {


    private AppUtils() {
        // This class is not publicly instantiable
    }

    /**
     * This method gets package name of the current app
     *
     * @param context required to start activity
     */
    public static void openPlayStoreForApp(@NonNull Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Config.PLAY_STORE_MARKET_LINK + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Config.PLAY_STORE_LINK + appPackageName)));
        }
    }

    /**
     * @param context
     * @param appPackageName package name of the app to be rated on play store
     */
    public static void openPlayStoreForApp(Context context, String appPackageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Config.PLAY_STORE_MARKET_LINK + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Config.PLAY_STORE_LINK + appPackageName)));
        }

    }


}
