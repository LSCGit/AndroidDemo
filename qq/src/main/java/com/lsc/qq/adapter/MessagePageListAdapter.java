package com.lsc.qq.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lsc.qq.R;

/**
 * Created by lsc on 2020-02-28 17:04.
 * E-Mail:2965219926@qq.com
 */
public class MessagePageListAdapter extends RecyclerView.Adapter<MessagePageListAdapter.MyViewHolder> {

    private Activity activity;

    public MessagePageListAdapter(Activity activity){
        this.activity = activity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = null;
        if (viewType == R.layout.message_list_item_search){
            view = inflater.inflate(R.layout.message_list_item_search,parent,false);
        }else {
            view = inflater.inflate(R.layout.message_list_item_normal,parent,false);
        }

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return R.layout.message_list_item_search;
        }
        return R.layout.message_list_item_normal;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
