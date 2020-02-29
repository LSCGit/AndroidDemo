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
import android.widget.Toast;

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

        TextView singer;
        TextView title;
        RatingBar ratingBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            singer = itemView.findViewById(R.id.text_view_singer);
            title = itemView.findViewById(R.id.textView_Title);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            //让行的根View形影点击事件来实现选择item的效果
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"Position: " + getAdapterPosition(),Toast.LENGTH_LONG).show();
                }
            });
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

            //获取这一行对应的List项
            ComplexInfo info = infos.get(position);
            //将数据设置到对应的控件中
            holder.singer.setText(info.getSinger());
            holder.title.setText(info.getTitle());
            holder.ratingBar.setRating(info.like);
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
