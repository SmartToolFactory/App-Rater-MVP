package com.stfactory.appratermvp.contract;


import com.stfactory.appratermvp.view.BaseDialogView;

public interface AppRaterContract {

    interface RatingDialogView extends BaseDialogView {



        void start();

        void changeRatingText(String text);

        void showDialog();

        /**
         * This method is invoked if rating is 5 stars
         */
        void showPlayStoreView();
        /**
         * This method is invoked if rating is less than 5 stars
         */
        void showFeedbackView();

        void sendFeedbackViaEmail(int rating, String message);

        void rateOnStore();

        void dismissDialog();

    }

    interface Presenter {
        void start();

        void onRatingChange(int rating);

        void onSubmitClicked(int rating, String review);

        void onCancelClicked();

        void onLaterClicked();

        void onRateOnStoreClicked();



    }

}
