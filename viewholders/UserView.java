package com.example.admin.sample.viewholders;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.admin.sample.R;

public class UserView extends RecyclerView.ViewHolder {
    public TextView name,phno;
    public ConstraintLayout parent_li;
    public UserView(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        phno = itemView.findViewById(R.id.phno);
        parent_li = itemView.findViewById(R.id.parent_li);
    }
}
