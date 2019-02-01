package com.example.admin.sample.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.sample.R;
import com.example.admin.sample.models.UserBean;
import com.example.admin.sample.room.User;
import com.example.admin.sample.viewholders.UserView;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UserView> {
    List<UserBean> usersBeanList;
    CustomListeners customListeners;

    public UsersListAdapter(List<UserBean> usersBeanList, CustomListeners customListeners) {
        this.usersBeanList = usersBeanList;
        this.customListeners = customListeners;
    }

    @NonNull
    @Override
    public UserView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserView(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_row,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserView userView, final int i) {
        userView.name.setText(usersBeanList.get(i).getName());
        userView.phno.setText(usersBeanList.get(i).getPhno());
        userView.parent_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customListeners.openBottomSheet(usersBeanList.get(i),i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersBeanList.size();
    }
    public interface CustomListeners{
        public void openBottomSheet(UserBean user,int pos);
    }

}

