package com.prototype.shane.shaneprototype.view.chatApp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.ShanePrototypeApplication;
import com.prototype.shane.shaneprototype.view.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by shane on 8/11/16.
 */
public class FragmentChatLogin extends BaseFragment{
    private EditText mUsernameView;

    private String mUsername;

    private Socket mSocket;

    LoginListener loginListener;

    public static FragmentChatLogin create(LoginListener loginListener){
        FragmentChatLogin fragment = new FragmentChatLogin();
        fragment.loginListener = loginListener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_login, container, false);
        mUsernameView = (EditText) rootView.findViewById(R.id.username_input);
        mUsernameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button signInButton = (Button) rootView.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShanePrototypeApplication app = (ShanePrototypeApplication) getActivity().getApplication();
        mSocket = app.getSocket();

        mSocket.on("login", onLogin);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.off("login", onLogin);
    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString().trim();

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            mUsernameView.setError(getString(R.string.error_field_required));
            mUsernameView.requestFocus();
            return;
        }

        mUsername = username;

        // perform the user login attempt.
        mSocket.emit("add user", username);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            int numUsers;
            try {
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }

            if (loginListener!=null){
                loginListener.onLogin(mUsername, numUsers);
            }
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    public interface LoginListener{
        void onLogin(String username, int numUsers);
    }

}
