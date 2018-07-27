package com.stfactory.appratermvp;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;

import com.stfactory.appratermvp.interactor.preferences.IPrefs;
import com.stfactory.appratermvp.interactor.preferences.RaterPreferences;


/**
 * This is a wrapper class, it violates MVP but no need to create Rating Dialog Fragment
 */
public class AppRater {


    // Min number of days
    private static int daysUntilPrompt = 3;
    // Min number of launches
    private static int launchesUntilPrompt = 3;


    public static void init(int daysBeforePromt, int usesBeforePromt) {
        daysUntilPrompt = daysBeforePromt;
        launchesUntilPrompt = usesBeforePromt;
    }

    public static void start(FragmentManager fragmentManager, SharedPreferences sharedPreferences) {
        try {
            IPrefs prefs = (IPrefs) new RaterPreferences(sharedPreferences);

            if (prefs.isShowNever()) {
                return;
            }

            // // Increment launch counter
            int useCount = prefs.getUseCount() + 1;
            long dateFirstUse = prefs.getUseStartDate();

            if (dateFirstUse == 0) dateFirstUse = System.currentTimeMillis();

            prefs.saveUseCount(useCount);
            prefs.saveFirstStartDate(dateFirstUse);


            if (useCount >= launchesUntilPrompt
                    && System.currentTimeMillis() >= dateFirstUse + (daysUntilPrompt * 24 * 60 * 60 * 1000)) {

                RateUsDialog dialog = RateUsDialog.newInstance();
                dialog.show(fragmentManager, "rate");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
