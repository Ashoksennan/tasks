package com.example.admin.sample.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.sample.HomeActivity;
import com.example.admin.sample.MainActivity;
import com.example.admin.sample.R;
import com.example.admin.sample.room.DatabaseClient;
import com.example.admin.sample.room.User;

import java.util.regex.Pattern;


public class TemplatePreviewFragment extends DialogFragment {
    EditText et_mailid,et_password,et_cnfrm_password,et_username;
    Button sigup_btn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog,container);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.my_toolbar);
         et_mailid = (EditText) v.findViewById(R.id.et_mailid);
         et_password = (EditText) v.findViewById(R.id.et_password);
         et_cnfrm_password = (EditText) v.findViewById(R.id.et_cnfrm_password);
         et_username = (EditText) v.findViewById(R.id.et_username);
         sigup_btn = (Button) v.findViewById(R.id.sigup_btn);

        sigup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValidate(et_mailid.getText().toString())){
                    if(isUsernameValidate(et_username.getText().toString())){
                       if(isValidPassword(et_password.getText().toString())){
                            if(et_password.getText().toString().equals(et_cnfrm_password.getText().toString())){
                               saveToDb(new User(0,et_username.getText().toString(),et_mailid.getText().toString(),et_password.getText().toString()));
                            }else{
                                et_cnfrm_password.setError("Confirm password should be Equal to Password!");
                            }
                       }else{
                           et_password.setError("Must have One Uppercase,One lowercase and one Numerical Value!");
                       }
                    }else{
                        et_username.setError("Please Enter a valid Username!");
                    }
                }else{
                    et_mailid.setError("Please Enter a valid EmailId!");
                }
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
                return true;
            }
        });
        toolbar.setTitle("SignUp");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return v;
    }

    private void saveToDb(final User user) {
        class SaveUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                //adding to database
                DatabaseClient.getInstance(requireActivity().getApplicationContext()).getAppDatabase()
                        .userDao()
                        .insert(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getDialog().dismiss();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(requireActivity(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveUser st = new SaveUser();
        st.execute();
    }

    private boolean isEmailValidate(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    private boolean isUsernameValidate(String username){
        return (!TextUtils.isEmpty(username));
    }
    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

}
