package com.stfactory.appratermvp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.stfactory.appratermvp.contract.AppRaterContract;
import com.stfactory.appratermvp.interactor.InteractorImpl;
import com.stfactory.appratermvp.interactor.preferences.RaterPreferences;
import com.stfactory.appratermvp.interactor.repository.TextRepository;
import com.stfactory.appratermvp.presenter.RatingDialogPresenter;
import com.stfactory.appratermvp.utils.AppUtils;
import com.stfactory.appratermvp.view.BaseDialog;


public class RateUsDialog extends BaseDialog implements AppRaterContract.RatingDialogView {

    private static final String TAG = RateUsDialog.class.getName();

    // Presenter
    private AppRaterContract.Presenter mPresenter;



    /*
     * Views
     */
    private RatingBar mRatingBar;

    // Buttons at the bottom
    private Button mButtonCancel;
    private Button mButtonLater;
    private Button mButtonSubmit;

    // Rate on store button
    private Button mButtonRateOnStore;

    // EditText for user feedback
    private EditText mEditTextUserFeedback;

    private View mViewFeedback;
    private View mViewStoreRating;

    private TextView mTextViewDescription;
    private TextView mTextViewStarDescription;

    private static FragmentManager mFragmentManger;

    /**
     * This method does check use count and use date to show dialog fragment. InteractorImpl class checks conditions and
     * invokes showDialog method if conditions are met
     *
     * @param context         required to create sharedPreferences and resources
     * @param fragmentManager is required as a parameter for Dialog's show(FragmentManger, String) method
     */
    public void start(Context context, FragmentManager fragmentManager) {

        try {
            mFragmentManger = fragmentManager;

            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(context.getApplicationContext());

            Resources resources = context.getApplicationContext().getResources();

            mPresenter = new RatingDialogPresenter(this,
                    new InteractorImpl(new RaterPreferences(sharedPreferences), new TextRepository(resources)));
            mPresenter.start();
        } catch (Exception e) {
            e.printStackTrace();
            dismissDialog();
        }
    }

    public static RateUsDialog newInstance() {
        RateUsDialog f = new RateUsDialog();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (mPresenter == null) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(getActivity().getApplicationContext());

            Resources resources = getActivity().getApplicationContext().getResources();

            mPresenter = new RatingDialogPresenter(this,
                    new InteractorImpl(new RaterPreferences(sharedPreferences), new TextRepository(resources)));
        }
    }

    // Base Dialog Abstract Method

    @Override
    protected void bindViews(View view) {
        mRatingBar = view.findViewById(R.id.app_rater_rating_bar);

        LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();

        stars.getDrawable(0).setColorFilter(ContextCompat.getColor(getContext(), R.color.shadow),
                PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(ContextCompat.getColor(getContext(), R.color.shadow),
                PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow),
                PorterDuff.Mode.SRC_ATOP);

        mButtonCancel = view.findViewById(R.id.app_rater_button_cancel);
        mButtonLater = view.findViewById(R.id.app_rater_button_later);
        mButtonSubmit = view.findViewById(R.id.app_rater_button_submit);

        // EditText for user feedback
        mEditTextUserFeedback = view.findViewById(R.id.app_rater_dialog_comment_edit_text);

        // LinearLayouts for feedback and to display rate on store
        mViewFeedback = view.findViewById(R.id.app_rater_view_feedback);
        mViewStoreRating = view.findViewById(R.id.app_rater_view_store_rating);
        mViewFeedback.setVisibility(View.GONE);
        mViewStoreRating.setVisibility(View.GONE);

        mTextViewDescription = view.findViewById(R.id.app_rater_description);
        mTextViewStarDescription = view.findViewById(R.id.app_rater_star_description);

        mRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 1) {
                    rating = 1;
                    ratingBar.setRating(rating);
                }
                if (mPresenter != null) mPresenter.onRatingChange((int) rating);
            }
        });

        mButtonCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPresenter.onCancelClicked();
            }
        });

        mButtonLater.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPresenter.onLaterClicked();
            }
        });

        mButtonSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                mPresenter.onSubmitClicked((int) mRatingBar.getRating(), mEditTextUserFeedback.getText().toString());
            }
        });

        mButtonRateOnStore = view.findViewById(R.id.btn_rate_on_play_store);

        mButtonRateOnStore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPresenter.onRateOnStoreClicked();
            }
        });
    }


    // RatingDialogView Interface Methods

    @Override
    public void start() {
        mPresenter.start();
    }


    @Override
    public void showDialog() {
        show(mFragmentManger, TAG);
    }

    @Override
    public void dismissDialog() {

        super.dismissDialog(TAG);


    }

    /**
     * Open intent to send email after user touches submit in feedback screen
     */
    @Override
    public void sendFeedbackViaEmail(int rating, String feedback) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("text/plain");
        emailIntent.setData(Uri.parse("mailto:" + getString(R.string.app_email_address)));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name) + " (" + rating + ") stars");
        emailIntent.putExtra(Intent.EXTRA_TEXT, feedback);
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.app_send_email)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), R.string.app_no_email_clients, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * Open desired store(play, amazon, etc) or web page to rate app online
     */
    @Override
    public void rateOnStore() {
        AppUtils.openPlayStoreForApp(getActivity());
    }

    /**
     * Show rate on store view after submit button with 5 stars is clicked.
     */
    @Override
    public void showPlayStoreView() {
        mRatingBar.setIsIndicator(true);
        mViewStoreRating.setVisibility(View.VISIBLE);
        mTextViewDescription.setVisibility(View.GONE);
        mTextViewStarDescription.setVisibility(View.GONE);
        mButtonSubmit.setVisibility(View.INVISIBLE);
    }

    /**
     * Show feedback view if user has rated with less than 5 stars.
     */
    @Override
    public void showFeedbackView() {
        mRatingBar.setIsIndicator(true);
        mViewFeedback.setVisibility(View.VISIBLE);
        mTextViewDescription.setVisibility(View.GONE);
        mTextViewStarDescription.setVisibility(View.GONE);
    }

    /**
     * Change text below rating bar when user interacts with rating bar
     */
    @Override
    public void changeRatingText(String text) {
        mTextViewStarDescription.setText(text);
    }

    // TODO FINISH BUILDER and set properties with builder pattern

    /**
     * This class is required to apply builder pattern to set properties of app
     * rater dialog
     */
    public static class Builder {

        public Builder() {

        }

        public RateUsDialog build() {
            return new RateUsDialog();
        }

    }

}
