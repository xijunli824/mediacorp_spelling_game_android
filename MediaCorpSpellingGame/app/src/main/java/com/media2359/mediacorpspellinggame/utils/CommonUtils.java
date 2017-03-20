package com.media2359.mediacorpspellinggame.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.widget.PasswordDialogFragment;

/**
 * Created by xijunli on 19/12/16.
 */

public class CommonUtils {

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static Snackbar showSnackMessage(View view, String message, boolean isError){
        return showSnackMessage(view, message, isError, null, null);
    }

    public static Snackbar showSnackMessage(View view, String message, boolean isError, String action, View.OnClickListener listener){
        Snackbar snack;
        if(action == null || listener == null){
            snack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        }
        else {
            snack = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction(action, listener);
        }

        View snackView= snack.getView();
        snackView.setBackgroundResource(isError ? android.R.color.holo_red_dark : R.color.colorPrimaryDark);
        TextView tv = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snack.show();
        return snack;
    }

    public static AlertDialog makeHoldOnAlertDialog(Context context, DialogInterface.OnClickListener onPositiveClick) {
        return makeHoldOnAlertDialog(context, context.getString(R.string.hold_on_message), "Hold On", onPositiveClick);
    }

    public static AlertDialog makeHoldOnAlertDialog(Context context, String message, String title, DialogInterface.OnClickListener onPositiveClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("YES", onPositiveClick);
        builder.setNegativeButton("CANCEL", null);
        //builder.setCancelable(false);
        return builder.create();
    }

    public static void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String convertSecondsIntegerToClockFormatString(int elapsedTime) {

        int minutes = elapsedTime / 60;
        int seconds = elapsedTime % 60;

        if (seconds < 10) {
            return minutes + ":0" + seconds;
        } else {
            return minutes + ":" + seconds;
        }
    }

    public static String getQuestionCountString(Context context, int currentCount, int totalCount) {
        return context.getString(R.string.question_count, currentCount, totalCount);
    }

    public static void showPasswordDialogFragment(FragmentManager fragmentManager, PasswordDialogFragment.PasswordListener listener) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        PasswordDialogFragment newFragment = PasswordDialogFragment.newInstance();

        newFragment.setListener(listener);

        newFragment.show(ft, "dialog");
    }

}
