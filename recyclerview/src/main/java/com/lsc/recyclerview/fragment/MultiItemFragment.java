package com.lsc.recyclerview.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lsc.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * 多种条目
 */
public class MultiItemFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Object> musicInfos = new ArrayList<>();

    public MultiItemFragment() {
        // Required empty public constructor
        musicInfos.add(new MusicInfo("马云","采蘑菇的小姑娘",4));
        musicInfos.add(new MusicInfo("马化腾疼痛","王者风范",3));
        musicInfos.add(new MusicInfo("王健林林","万达广场珍贵",2));
        musicInfos.add(new MusicInfo("曹德旺网","玻璃真结实",4));

        //插入一条广告
        musicInfos.add(new Advertising("蓝翔","中国挖掘机，指定生产厂家"));
        musicInfos.add(new MusicInfo("刘强东懂","京东自营真快",4));
        musicInfos.add(new MusicInfo("任正非非","华为别再绑架爱过了",3));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_multi_item,container,false);
        mRecyclerView = rootView.findViewById(R.id.multiply_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new MultiAdapter());
        return rootView;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder{

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MusicInfoViewHolder extends BaseViewHolder{
        TextView singer;
        TextView title;
        RatingBar ratingBar;
        public MusicInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            singer = itemView.findViewById(R.id.text_view_singer);
            title = itemView.findViewById(R.id.textView_Title);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"音乐信息："+ getAdapterPosition(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public class AdvertisingViewHolder extends BaseViewHolder{
        TextView advertiser;
        TextView advertContent;
        public AdvertisingViewHolder(@NonNull View itemView) {
            super(itemView);
            advertiser = itemView.findViewById(R.id.advertiser);
            advertContent = itemView.findViewById(R.id.adverContent);
        }
    }

    public class MultiAdapter extends RecyclerView.Adapter<BaseViewHolder>{


        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = getLayoutInflater();
            //ViewType就是layout的ID
            View view = inflater.inflate(viewType,parent,false);
            BaseViewHolder viewHolder;
            if (viewType == R.layout.multi_music_item){
                viewHolder = new MusicInfoViewHolder(view);
            }else {
                viewHolder = new AdvertisingViewHolder(view);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

            Object item = musicInfos.get(position);
            if (item instanceof MusicInfo){
                MusicInfo musicInfo = (MusicInfo) item;

                MusicInfoViewHolder viewHolder = (MusicInfoViewHolder) holder;
                viewHolder.singer.setText(musicInfo.getSinger());
                viewHolder.title.setText(musicInfo.getTitle());
                viewHolder.ratingBar.setRating(musicInfo.getRatingBar());
            }else {
                Advertising advertising = (Advertising) item;
                AdvertisingViewHolder viewHolder = (AdvertisingViewHolder) holder;
                viewHolder.advertiser.setText(advertising.getAdvertiser());
                viewHolder.advertContent.setText(advertising.getContent());
            }
        }

        @Override
        public int getItemCount() {
            return musicInfos.size();
        }

        @Override
        public int getItemViewType(int position) {
            //根据参数position，返回每行对应的ViewType的值，为了方便
            //我们直接将行layout的ID作为ViewType的值
            if (musicInfos.get(position) instanceof MusicInfo){
                //这条数据对应的是MusicInfo
                return R.layout.multi_music_item;
            }else {
                return R.layout.multi_adver_item;
            }
        }
    }


    //在列表控件中显示广告,数据类
    public class Advertising{
        private String advertiser;//广告主
        private String content;//广告内容

        public Advertising(String advertiser, String content) {
            this.advertiser = advertiser;
            this.content = content;
        }

        public String getAdvertiser() {
            return advertiser;
        }

        public void setAdvertiser(String advertiser) {
            this.advertiser = advertiser;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
    //在item中显示音乐信息 数据类
    public class MusicInfo{
        private String singer;
        private String title;
        private int ratingBar;

        public MusicInfo(String singer, String title, int ratingBar) {
            this.singer = singer;
            this.title = title;
            this.ratingBar = ratingBar;
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

        public int getRatingBar() {
            return ratingBar;
        }

        public void setRatingBar(int ratingBar) {
            this.ratingBar = ratingBar;
        }
    }
}
