package com.lsc.qq.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.lsc.qq.R;
import com.lsc.qq.adapter.ContactsPageListAdapter;
import com.lsc.qq.adapter.MessagePageListAdapter;
import com.lsc.recyclerlisttreeview.ListTree;
import com.lsc.utils.DpPx;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * 主页
 */
public class MainFragment extends Fragment {

    private FrameLayout rootView;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private ImageView headImage;
    private TextView popMenu;

    private View[] listViews = new View[3];

    private RecyclerView messageRecyclerView;
    public MainFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messageRecyclerView = new RecyclerView(getContext());
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messageRecyclerView.setAdapter(new MessagePageListAdapter(getActivity()));
        listViews[0] = messageRecyclerView;
        listViews[1] = createContactsPage();
        listViews[2] = new RecyclerView(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_main, container, false);

        mViewPager = rootView.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new MyViewPagerAdapter());

        mTabLayout = rootView.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        headImage = rootView.findViewById(R.id.headImage);
        //响应左上角的图标点击事件，显示抽屉页面
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建抽屉页面
                final View drawerLayout = getActivity().getLayoutInflater().inflate(R.layout.drawer_layout,rootView,false);
                //计算一下消息页面中左边一排图像的大小。
                int messageImageWidth = DpPx.dip2px(getActivity(),60);
                //计算抽屉叶脉你的宽度，
                final int drawerWidth = rootView.getWidth() - messageImageWidth;
                //设置抽屉页面的宽度
                drawerLayout.getLayoutParams().width = drawerWidth;
                rootView.addView(drawerLayout);

                //动画时间
                ObjectAnimator animatorDrawer = ObjectAnimator.ofFloat(drawerLayout,
                        "translationX",-drawerWidth/2 ,0);

                //获取原内容的根控件
                final View contentLayout = rootView.findViewById(R.id.contentLayout);
                //把它搞到上层，
                contentLayout.bringToFront();
                //创建动画，移动原内容
                ObjectAnimator animatorContent = ObjectAnimator.ofFloat(contentLayout,
                        "translationX",0,drawerWidth);

                //创建移动蒙版
                final View maskView = new View(getContext());
                maskView.setBackgroundColor(Color.GRAY);
                //必须将其初始透明度设为完全透明
                maskView.setAlpha(0);
                rootView.addView(maskView);

                //移动蒙版的动画
                ObjectAnimator animatorMask = ObjectAnimator.ofFloat(maskView,
                        "translationX",0,drawerWidth);
                animatorMask.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //计算当前的进度比例，最后除以2的原因是因为透明度最终只降到一半。
                        float progress = (animation.getCurrentPlayTime()/(float)400)/2;
                        maskView.setAlpha(progress);
                    }
                });

                //创建动画集合，同时播放三个动画
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animatorContent,animatorDrawer,
                        animatorMask);
                animatorSet.setDuration(400);
                animatorSet.start();

                maskView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //动画反着来，让抽屉消失
                        //创建动画，移动原内容，从0位置移动抽屉玉面宽度的距离
                        ObjectAnimator animatorContent = ObjectAnimator.ofFloat(contentLayout,
                                "translationX",drawerWidth,0);

                        //移动蒙版的动画
                        ObjectAnimator animatorMask = ObjectAnimator.ofFloat(maskView,
                                "translationX",drawerWidth,0);
                        animatorMask.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float progress = (animation.getCurrentPlayTime()/400);
                                maskView.setAlpha((1-progress)/2);
                            }
                        });

                        ////创建动画，让抽屉页面向右移。
                        ObjectAnimator animatorDrawer = ObjectAnimator.ofFloat(drawerLayout,
                                "translationX",0,-drawerWidth);
                        //创建动画集合
                        AnimatorSet animatorSet2 = new AnimatorSet();
                        animatorSet2.playTogether(animatorContent,animatorDrawer,animatorMask);
                        animatorSet2.setDuration(400);
                        animatorSet2.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                                rootView.removeView(maskView);
                                rootView.removeView(drawerLayout);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });

                        animatorSet2.start();
                    }
                });
            }
        });
        popMenu = rootView.findViewById(R.id.textViewPopMenu);
        popMenu.setOnClickListener(new View.OnClickListener() {

            PopupWindow popupWindow;
            View mask;
            @Override
            public void onClick(View v) {
                if (mask == null){
                    mask = new View(getContext());
                    mask.setBackgroundColor(Color.DKGRAY);
                    mask.setAlpha(0.5f);

                    mask.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rootView.removeView(mask);
                            popupWindow.dismiss();
                        }
                    });
                }
                rootView.addView(mask,
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
                if (popupWindow == null){
                    popupWindow = new PopupWindow(getActivity());
                    //为窗口添加一个控件
                    popupWindow.setContentView(new View(getActivity()));
                    popupWindow.setWidth(200);
                    popupWindow.setHeight(200);

                    popupWindow.setAnimationStyle(R.style.popMenuAnim);

                    //设置窗口消失的监听器，在窗口消失后把蒙版去掉
                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            rootView.removeView(mask);
                        }
                    });

                    //设置窗口出现时获取焦点，这样在按下返回键是，窗口才会消失
                    popupWindow.setFocusable(true);
                }

                popupWindow.showAsDropDown(v);
            }
        });
        return rootView;
    }

    /*
    * 为Vp创建适配器
    * */
    public class MyViewPagerAdapter extends PagerAdapter{

        public MyViewPagerAdapter(){

        }

        //返回ViewPager中的页数
        @Override
        public int getCount() {
            return listViews.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        //实例画一个子View，container：ViewPager
        //ViewPager在创建页View时调用的方法是instantiateItem()，它返回页View对象，
        // 但实际上我们并不是在此方法中创建的页View，而是在Adapter类的外部类MainFragment的构造方法中就创建了，
        // 在instantiateItem()中只是返回对应的页View就行了，这样做是为了避免多次创建页View。
        // 注意其中container.addView()这一句，你必须在instantiateItem()中把子View加入到容器View中
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View v = listViews[position];
            container.addView(v);
            return v;
        }

        //方法必须实现，它用于在销毁页View时调用，但我们不想销毁，所以我们只是把页View从容器中删除。
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0){
                return "消息";
            }else if (position == 1){
                return "联系人";
            }else if(position == 2){
                return "空间";
            }
            return null;
        }
    }

    /**
     * 创建并初始化联系人页面，返回这个页面
     * @return
     */
    private View createContactsPage(){
        //创建View
        View view = getLayoutInflater().inflate(R.layout.contacts_page_layout,null);
        //创建集合 一棵树
        ListTree tree = new ListTree();
        //向树中添加节点
        //创建组们，组是树的根节点，他们的父节点为null
        ContactsPageListAdapter.GroupInfo groupInfo1 = new ContactsPageListAdapter.GroupInfo("特别关心",0);
        ContactsPageListAdapter.GroupInfo groupInfo2 = new ContactsPageListAdapter.GroupInfo("我的好友",1);
        ContactsPageListAdapter.GroupInfo groupInfo3 = new ContactsPageListAdapter.GroupInfo("朋友",0);
        ContactsPageListAdapter.GroupInfo groupInfo4 = new ContactsPageListAdapter.GroupInfo("家人",0);
        ContactsPageListAdapter.GroupInfo groupInfo5 = new ContactsPageListAdapter.GroupInfo("同学",0);

        ListTree.TreeNode groupNodel1 = tree.addNode(null,groupInfo1,
                R.layout.contacts_group_item);
        ListTree.TreeNode groupNodel2 = tree.addNode(null,groupInfo2,
                R.layout.contacts_group_item);
        ListTree.TreeNode groupNodel3 = tree.addNode(null,groupInfo3,
                R.layout.contacts_group_item);
        ListTree.TreeNode groupNodel4 = tree.addNode(null,groupInfo4,
                R.layout.contacts_group_item);
        ListTree.TreeNode groupNodel5 = tree.addNode(null,groupInfo5,
                R.layout.contacts_group_item);

        //第二层 ，联系人信息
        //头像
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.search_bk);
        //联系人1
        ContactsPageListAdapter.ContactInfo contactInfo1 = new ContactsPageListAdapter.ContactInfo(
                bitmap,"王二","[在线]我是王二"
        );

        //联系人2
        ContactsPageListAdapter.ContactInfo contactInfo2 = new ContactsPageListAdapter.ContactInfo(
                bitmap,"王二三","[离线]我没有状态"
        );

        tree.addNode(groupNodel2,contactInfo1,R.layout.contacts_contact_item);
        tree.addNode(groupNodel2,contactInfo2,R.layout.contacts_contact_item);

        //获取页面里的RecyclerView,为他创建Adapter；
        RecyclerView recyclerView = view.findViewById(R.id.contactRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ContactsPageListAdapter(tree));
        return view;
    }
}
