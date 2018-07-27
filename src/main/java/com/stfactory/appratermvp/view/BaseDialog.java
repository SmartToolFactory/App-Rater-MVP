package com.stfactory.appratermvp.view;

import com.stfactory.appratermvp.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseDialog extends DialogFragment implements BaseDialogView {

    @Override
    public void dismissDialog(String tag) {
        try {


            dismiss();

            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                // Fragment is not null even after dismiss is called
                // and FragmentManager fragment size is 1
                Fragment fragment = fragmentManager.findFragmentByTag(tag);
                if (fragment != null) {
                    fragmentManager
                            .beginTransaction()
                            .remove(fragment)
                            .commitNow();
                }

                System.out.println("BaseDialog dismissDialog() fragment: " + fragment );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Use Fragment approach if you get Dialog instead of Alert Dialog from
     * onCreateDialog() method. Dialog dimensions are not resized automatically.
     * Width is set to match_parent, height is set to wrap_content
     *
     * (non-Javadoc)
     *
     * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
     */
/*


    public void show(FragmentManager fragmentManager, String tag) {
//
//		// DialogFragment.show() will take care of adding the fragment
//		// in a transaction. We also want to remove any currently showing
//		// dialog, so make our own transaction and take care of that here.

        System.out.println("BaseDialog show()");

//
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_rate_us_card, container, false);
        System.out.println("BaseDialog onCreateView()");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("BaseDialog onViewCreated()");

        bindViews(view);
    }
*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*
         * Create Dialog
         */

        /*
        // // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // creating the full screen dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        return dialog;
        */

        /*
         * Create Alert Dialog
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rate_us, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        bindViews(dialogView);
        return dialog;
    }

    /**
     * Find child views of dialog and set their properties of dialog's child views.
     *
     * @param view root layout of the dialog
     */
    protected abstract void bindViews(View view);

}
