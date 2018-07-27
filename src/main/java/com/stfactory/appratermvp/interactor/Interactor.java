package com.stfactory.appratermvp.interactor;

public interface Interactor {

    void checkAppRateStatus(Callback callback);


    interface Callback {
        void showRateDialog();
        void showRateDialogLaterOrNever();
    }

    /**
     * @param rating   given by the user
     * @param review   of the user
     * @param listener submit button opens either feedback or rate on store views and sends feedback
     */
    void onRatingSubmitted(int rating, String review, OnAppReviewSubmitListener listener);


    interface OnAppReviewSubmitListener {

        void showRateOnStoreDialog();

        void showFeedbackDialog();

        void sendFeedBack(int rating, String review);

    }

    /**
     * Setting show never to true stops dialog being shown
     */
    void setShowNever();

    /**
     * Set time to current time and use count of app to zero
     */
    void setShowLater();

    /**
     * Get corresponding rating text when user interacts with rating bar
     *
     * @param rating current rating set by user
     * @return corresponding text from text repo
     */
    String getRatingText(int rating);


}
