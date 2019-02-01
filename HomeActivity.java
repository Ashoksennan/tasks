package com.example.admin.sample;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.sample.adapters.UsersListAdapter;
import com.example.admin.sample.models.UserBean;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, UsersListAdapter.CustomListeners {

    // BottomSheetBehavior variable
    private BottomSheetBehavior bottomSheetBehavior;

    // TextView variable
    private TextView bottomSheetHeading;

    // Button variables
    private RecyclerView userDetails;
    private FloatingActionButton fab;
    private EditText et_name,et_phno;
    private Button edit_btn,save_btn,delete_btn;
    UsersListAdapter usersListAdapter;
    int currentpos;
    MenuItem menuItemFilter;
    List<UserBean> userBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        initViews();
        initListeners();
    }
    /**
     * method to initialize the views
     */
    private void initViews() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        userDetails = (RecyclerView) findViewById(R.id.userDetails);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phno = (EditText) findViewById(R.id.et_phno);
        edit_btn = (Button) findViewById(R.id.edit_btn);
        save_btn = (Button) findViewById(R.id.save_btn);
        delete_btn = (Button) findViewById(R.id.delete_btn);
        userDetails.setLayoutManager(new LinearLayoutManager(this));
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }


    /**
     * method to initialize the listeners
     */
    private void initListeners() {
        // register the listener for button click

        fab.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        edit_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        // Capturing the callbacks for bottom sheet
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {





                // Check Logs to see how bottom sheets behaves
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.e("Bottom Sheet Behaviour", "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.e("Bottom Sheet Behaviour", "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.e("Bottom Sheet Behaviour", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.e("Bottom Sheet Behaviour", "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.e("Bottom Sheet Behaviour", "STATE_SETTLING");
                        break;
                }
            }


            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                edit_btn.setVisibility(View.GONE);
                delete_btn.setVisibility(View.GONE);
                save_btn.setVisibility(View.VISIBLE);
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }else if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.save_btn:
                if(!TextUtils.isEmpty(et_name.getText().toString()) && !TextUtils.isEmpty(et_phno.getText().toString())){
                    userBeanList.add(new UserBean(et_name.getText().toString(),et_phno.getText().toString()));
                    usersListAdapter = new UsersListAdapter(userBeanList,this);
                    userDetails.setAdapter(usersListAdapter);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                break;
            case R.id.edit_btn:
                userBeanList.remove(currentpos);
                usersListAdapter.notifyDataSetChanged();

                updateUserDetail(new UserBean(et_name.getText().toString(),et_phno.getText().toString()));
                break;
            case R.id.delete_btn:
                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                if(userBeanList.size()>= currentpos){
                    userBeanList.remove(currentpos);
                    usersListAdapter.notifyDataSetChanged();
                }
                et_name.getText().clear();
                et_phno.getText().clear();
                break;
        }
    }

    @Override
    public void openBottomSheet(UserBean user,int pos) {
        if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        et_name.setText(user.getName());
        et_phno.setText(user.getPhno());
        edit_btn.setVisibility(View.VISIBLE);
        delete_btn.setVisibility(View.VISIBLE);
        save_btn.setVisibility(View.GONE);
        currentpos = pos;
    }


    public void updateUserDetail(UserBean user) {
        if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        userBeanList.add(new UserBean(user.getName(),user.getPhno()));
        usersListAdapter.notifyDataSetChanged();
        et_name.getText().clear();
        et_phno.getText().clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuItemFilter = menu.findItem(R.id.action_logout);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent movetoLogin = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(movetoLogin);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
