package com.media2359.mediacorpspellinggame.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.media2359.mediacorpspellinggame.R;

/**
 * Created by xijunli on 17/2/17.
 */

public class SessionSelectionFragment extends DialogFragment {


    private SessionSelectCallback callback;

    public static SessionSelectionFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SessionSelectionFragment fragment = new SessionSelectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_session)
                .setItems(R.array.sessions_semi, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        if (callback != null)
                            callback.onSessionSelected(which);
                    }
                });
        return builder.create();
    }

    public void setCallback(SessionSelectCallback callback) {
        this.callback = callback;
    }

    public interface SessionSelectCallback {

        void onSessionSelected(int sessionId);

    }
}
