package com.stfactory.appratermvp.interactor;

import android.support.annotation.NonNull;

import com.stfactory.appratermvp.interactor.preferences.RaterPreferences;
import com.stfactory.appratermvp.interactor.repository.TextRepository;


public class InteractorImpl implements Interactor {

    // Min number of days
    private final static int DAYS_UNTIL_PROMPT = 3;
    // Min number of launches
    private final static int LAUNCHES_UNTIL_PROMPT = 3;

    private RaterPreferences mRaterPreferences;
    private TextRepository mRepository;

    private boolean isFirstWindow = true;

    public InteractorImpl(RaterPreferences preferences, TextRepository repository) {
        mRaterPreferences = preferences;
        mRepository = repository;
    }

    @Override
    public void checkAppRateStatus(@NonNull Callback callback) {

        if (mRaterPreferences.isShowNever()) {
            System.out.println("InteractorImpl show " + mRaterPreferences.isShowNever());
            callback.showRateDialogLaterOrNever();
            return;
        }

        // // Increment launch counter
        int useCount = mRaterPreferences.getUseCount() + 1;
        long dateFirstUse = mRaterPreferences.getUseStartDate();

        if (dateFirstUse == 0) dateFirstUse = System.currentTimeMillis();


        if (useCount >= LAUNCHES_UNTIL_PROMPT
                && System.currentTimeMillis() >= dateFirstUse + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
            System.out.println("InteractorImpl checkAppRateStatus() useCount " + useCount
                    + ", dateFirstUse: " + (System.currentTimeMillis() - dateFirstUse));
            callback.showRateDialog();

        } else {
            callback.showRateDialogLaterOrNever();
        }

        mRaterPreferences.saveUseCount(useCount);
        mRaterPreferences.saveFirstStartDate(dateFirstUse);

    }

    @Override
    public void onRatingSubmitted(int rating, String review, @NonNull OnAppReviewSubmitListener listener) {
        if (isFirstWindow) {
            if (rating == 5) {
                listener.showRateOnStoreDialog();
            } else {
                listener.showFeedbackDialog();
            }
            isFirstWindow = false;
        } else {
            listener.sendFeedBack(rating, review);

        }

    }


    @Override
    public String getRatingText(int rating) {
        return mRepository.getRatingString(rating);
    }

    /**
     * Set show never to true when user rates on store/ writes feedback or touches cancel button
     */
    @Override
    public void setShowNever() {
        mRaterPreferences.saveShowNever(true);
    }

    /**
     * Reset time and use parameters to start checking again when user touches later button
     */
    @Override
    public void setShowLater() {
        mRaterPreferences.saveUseCount(0);
        mRaterPreferences.saveFirstStartDate(System.currentTimeMillis());
    }

}
