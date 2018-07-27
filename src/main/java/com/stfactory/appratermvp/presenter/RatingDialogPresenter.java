package com.stfactory.appratermvp.presenter;

import com.stfactory.appratermvp.contract.AppRaterContract;
import com.stfactory.appratermvp.interactor.Interactor;
import com.stfactory.appratermvp.interactor.InteractorImpl;



public class RatingDialogPresenter implements AppRaterContract.Presenter {

    private AppRaterContract.RatingDialogView mView;
    private Interactor mInteractor;

    public RatingDialogPresenter(AppRaterContract.RatingDialogView view, InteractorImpl interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void start() {

        mInteractor.checkAppRateStatus(new Interactor.Callback() {

            @Override
            public void showRateDialog() {
                mView.showDialog();
            }

            @Override
            public void showRateDialogLaterOrNever() {
                mView.dismissDialog();
            }
        });
    }

    @Override
    public void onSubmitClicked(int rating, String review) {
        mInteractor.onRatingSubmitted(rating, review, new Interactor.OnAppReviewSubmitListener() {

            @Override
            public void showRateOnStoreDialog() {
                showRateOnStoreView();
            }

            @Override
            public void showFeedbackDialog() {
                showFeedbackView();
            }

            @Override
            public void sendFeedBack(int rating, String review) {
                onSendFeedback(rating, review);
            }

        });

    }

    @Override
    public void onCancelClicked() {
        mView.dismissDialog();
        mInteractor.setShowNever();
    }

    @Override
    public void onLaterClicked() {
        mView.dismissDialog();
        mInteractor.setShowLater();
    }


    @Override
    public void onRatingChange(int rating) {
        mView.changeRatingText(mInteractor.getRatingText(rating));
    }


    private void showRateOnStoreView() {
        mView.showPlayStoreView();
    }


    private void showFeedbackView() {
        mView.showFeedbackView();
    }

    // These 2 methods are invoked from second screen

    /**
     * Send rating and review written inside Edittext as email
     *
     * @param rating given by the user
     * @param review of the user
     */
    private void onSendFeedback(int rating, String review) {
        mView.sendFeedbackViaEmail(rating, review);
        mView.dismissDialog();
        mInteractor.setShowNever();
    }

    public void onRateOnStoreClicked() {
        mView.rateOnStore();
        mView.dismissDialog();
        mInteractor.setShowNever();
    }


}
