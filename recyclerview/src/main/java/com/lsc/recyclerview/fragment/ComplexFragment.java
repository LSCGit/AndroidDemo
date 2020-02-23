package com.lsc.recyclerview.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lsc.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComplexFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<ComplexInfo> infos = new ArrayList<>();

    public ComplexFragment() {
        // Required empty public constructor
        infos.add(new ComplexInfo("马云","采蘑菇的小姑娘",4));
        infos.add(new ComplexInfo("马化腾","王者荣耀",2));
        infos.add(new ComplexInfo("刘强东","京东",5));
        infos.add(new ComplexInfo("王晓东","同方安全",3));
        infos.add(new ComplexInfo("刘德华","忘情水",4));
        infos.add(new ComplexInfo("张学友","事件",4));
        infos.add(new ComplexInfo("苏有朋","还珠格格",4));
        infos.add(new ComplexInfo("马云","采蘑菇的小姑娘2",4));
        infos.add(1,new ComplexInfo("","",2));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_complex,container,false);
        mRecyclerView = rootView.findViewById(R.id.complex_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new ComplexAdapter());
        return rootView;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class ComplexAdapter extends RecyclerView.Adapter<MyViewHolder>{

        //创建控件
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.complex_item,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        //绑定数据和View
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            //获取要绑定数据的item控件
            View view = holder.itemView;
            //获取行中显示各种数据控件
            TextView singer = view.findViewById(R.id.text_view_singer);
            TextView title = view.findViewById(R.id.textView_Title);
            RatingBar ratingBar = view.findViewById(R.id.ratingBar);
            //获取这一行对应的List项
            ComplexInfo info = infos.get(position);
            //将数据设置到对应的控件中
            singer.setText(info.getSinger());
            title.setText(info.getTitle());
            ratingBar.setRating(info.like);
        }

        //行数
        @Override
        public int getItemCount() {
            return infos.size();
        }
    }

    private class ComplexInfo{
        private String singer;
        private String title;
        private int like;

        public ComplexInfo(String singer, String title, int like) {
            this.singer = singer;
            this.title = title;
            this.like = like;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }
    }
}
