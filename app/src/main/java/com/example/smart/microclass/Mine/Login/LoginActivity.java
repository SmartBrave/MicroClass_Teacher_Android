package com.example.smart.microclass.Mine.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smart.microclass.OtherClass.Teacher;
import com.example.smart.microclass.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static com.example.smart.microclass.OtherClass.GlobalVariable.gson;
import static com.example.smart.microclass.OtherClass.GlobalVariable.httpUtil;
import static com.example.smart.microclass.OtherClass.GlobalVariable.teacher;
import static com.example.smart.microclass.OtherClass.GlobalVariable.urlBase;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> ,OnClickListener{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    //smart
    LinearLayout login_form;
    LinearLayout register_form;
    private TextView gotoRegister;
    private TextView thirdLogin;
    private AutoCompleteTextView phone;
    private AutoCompleteTextView code;
    private EditText password;
    private Button register;
    private TextView gotoLogin;
    private LinearLayout gotoRegisterLayout;
    private LinearLayout thirdLoginLayout;
    private RegisterTask registerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        //smart
        gotoRegisterLayout=(LinearLayout)findViewById(R.id.go_to_register_layout);
        thirdLoginLayout=(LinearLayout)findViewById(R.id.third_login_layout);
        login_form=(LinearLayout)findViewById(R.id.email_login_form);
        register_form=(LinearLayout)findViewById(R.id.email_register_form);

        gotoRegister=(TextView)findViewById(R.id.go_to_register);
        thirdLogin=(TextView)findViewById(R.id.third_login);
        phone=(AutoCompleteTextView)findViewById(R.id.phone);
        code=(AutoCompleteTextView)findViewById(R.id.code);
        password=(EditText)findViewById(R.id.password_register);
        register=(Button)findViewById(R.id.register);
        gotoLogin=(TextView)findViewById(R.id.go_to_login);

        gotoRegister.setOnClickListener(this);
        thirdLogin.setOnClickListener(this);
        gotoLogin.setOnClickListener(this);
        register.setOnClickListener(this);
        //register_form.setOnClickListener(this);

    }

    //auther:smart
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.go_to_register:
            case R.id.go_to_register_layout:
                login_form.setVisibility(View.GONE);
                register_form.setVisibility(View.VISIBLE);
                break;
            case R.id.third_login:
            case R.id.third_login_layout:
                break;
            case R.id.go_to_login:
                register_form.setVisibility(View.GONE);
                login_form.setVisibility(View.VISIBLE);
                break;
            case R.id.register:
                String account_text=phone.getText().toString();
                String code_text=code.getText().toString();
                String password_text=password.getText().toString();
                registerTask=new RegisterTask(account_text,code_text,password_text);
                registerTask.execute();
                break;
            default:
                break;
        }
    }

    //auther:smart
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.login_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //auther:smart
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                finish();
                return true;
            case R.id.login_item:
                Toast.makeText(LoginActivity.this,"login_item",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        //return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private String response;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String data="{\"account\":\""+mEmail+"\",\"password\":\""+mPassword+"\"}";
            String address=urlBase+"/login";
            try{
                //login ==>post
                response=httpUtil.post(address,data);
            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
            //System.out.println("1111111111111111111111111");
            if(response.equals("")||response.equals("failed")){
                //System.out.println("2222222222222222222222222");
                return false;
            }else {
                //System.out.println("3333333333333333333333333");
                teacher=gson.fromJson(response,Teacher.class);
                //System.out.println("teacher.userID: "+teacher.userID);
                //System.out.println("teacher.userAccount: "+teacher.userAccount);
                //System.out.println("teacher.userName: "+teacher.userName);
                //System.out.println("teacher.password: "+teacher.password);
                //System.out.println("teacher.registerTime: "+teacher.registerTime);
                Intent intent=new Intent();
                intent.putExtra("isSuccess","success");
                setResult(RESULT_OK,intent);
                finish();
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
    public class RegisterTask extends AsyncTask<Void,Void,Boolean>{

        String account_text;
        String code_text;
        String password_text;
        String response;

        RegisterTask(String a,String c,String p){
            account_text=a;
            code_text=c;
            password_text=p;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String data="{\"account\":\""+account_text+"\",\"password\":\""+password_text+"\"}";
            String address=urlBase+"/register";
            try{
                response=httpUtil.post(address,data);
            }catch(IOException e){
                //Toast.makeText(LoginActivity.this,R.string.net_error, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return false;
            }
            if(response.equals("true")){
                //go to login
                address=urlBase+"/login";
                try{
                    //login ==>post
                    response=httpUtil.post(address,data);
                }catch(IOException e){
                    e.printStackTrace();
                    return false;
                }
                if(response.equals("")||response.equals("failed")){
                    //Toast.makeText(LoginActivity.this,R.string.server_error, Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    teacher=gson.fromJson(response,Teacher.class);
                    Intent intent=new Intent();
                    intent.putExtra("isSuccess","success");
                    setResult(RESULT_OK,intent);
                    return true;
                }
            }else if(response.equals("registered")){
                //registered before
                //Toast.makeText(LoginActivity.this,R.string.registered, Toast.LENGTH_SHORT).show();
                return false;
            }else{
                return false;
                ////////////////////////////////////////////////////////
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                finish();
            }else{
                Toast.makeText(LoginActivity.this,R.string.some_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

