package com.media2359.mediacorpspellinggame.widget;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xijunli on 16/2/17.
 */

public class PasswordDialogFragment extends DialogFragment {

    private static final String PASSWORD_TEXT = "password";

    @BindView(R.id.etPassword)
    EditText etPassword;

//    @BindView(R.id.tvProceed)
//    TextView tvProceed;

    private PasswordListener listener;

    public static PasswordDialogFragment newInstance() {

        Bundle args = new Bundle();

        PasswordDialogFragment fragment = new PasswordDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_password_dialog, container, false);
        ButterKnife.bind(this, v);

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        setCancelable(true);
        getDialog().setTitle("Edit Answer");

        return v;
    }

    @OnClick(R.id.tvProceed)
    public void onProceedClick() {
        CommonUtils.dismissKeyboard(etPassword);

        if (validPassword() && listener != null) {
            getDialog().dismiss();
            listener.onPasswordMatch();
        }
    }

    private boolean validPassword() {
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Empty password");
            return false;
        }

        if (password.equalsIgnoreCase(PASSWORD_TEXT))
            return true;

        etPassword.setError("Wrong password");
        return false;
    }

    public void setListener(PasswordListener listener) {
        this.listener = listener;
    }

    public interface PasswordListener {

        void onPasswordMatch();

    }
}
