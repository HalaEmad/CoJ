package com.ir.android.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ibm.android.kit.controllers.Controller;
import com.ibm.android.kit.models.ViewModel;
import com.ibm.android.kit.utils.GeneralUtility;
import com.ibm.android.kit.views.activities.Activity;
import com.ir.android.R;

/**
 * Created by emanhassan on 6/12/16.
 */
public class LoginScreen extends Activity {

    private EditText username;
    private EditText password;
    private Button loginButton;


    @Override
    protected Controller getController() {
        return new LoginCtrl();
    }

    @Override
    protected String getControllerTag() {
        return "login.ctrl";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usrname = username.getText().toString();
                String pass = password.getText().toString();
                if (GeneralUtility.isEmptyString(usrname) || GeneralUtility.isEmptyString(pass)) {
                    showOkDialog("", "Error", "Username and password can not be empty..");
                } else
                    ((LoginCtrl) controller).startLogin(username.getText().toString(), password.getText().toString());

            }
        });
    }

    @Override
    protected void bindViews(ViewModel viewModel) {

    }
}
