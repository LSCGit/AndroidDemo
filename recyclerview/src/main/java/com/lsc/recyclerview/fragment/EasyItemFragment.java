package com.lsc.recyclerview.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsc.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EasyItemFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private List<String> datas = new ArrayList<>();

    public EasyItemFragment() {
        // Required empty public constructor
        initData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_easy_item,container,false);
        mRecyclerView = rootView.findViewById(R.id.easy_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new MyAdapter());

        return rootView;
    }

    private void initData(){
        datas.add("我是第0行");
        datas.add("我是第1行");
        datas.add("我是第2行");
        datas.add("我是第3行");
        datas.add("我是第4行");
        datas.add("我是第5行");
        datas.add("我是第6行");
        datas.add("我是第7行");
        datas.add("我是第8行");
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView){
            super(itemView);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        //创建控件
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(getContext());
            MyViewHolder viewHolder = new MyViewHolder(textView);
            return viewHolder;
        }

        //绑定数据
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            TextView textView = (TextView)holder.itemView;
            textView.setText(datas.get(position));
        }

        //显示行数
        @Override
        public int getItemCount() {
            return datas.size();
        }
    }
}
