package com.lsc.qq.fragment;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lsc.qq.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * 登录
 */
public class LoginFragment extends Fragment {

    private ConstraintLayout mLayoutContext;
    private LinearLayout mLayoutHistory;

    private EditText mEditTextQQNum;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login,container,false);
        mLayoutContext = rootView.findViewById(R.id.layoutContext);
        mLayoutHistory = rootView.findViewById(R.id.layoutHistory);

        mEditTextQQNum = rootView.findViewById(R.id.editTextQQNum);
        Button buttonLogin = rootView.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,new MainFragment()).
                        addToBackStack("login").
                        commit();
            }
        });

        //响应下拉箭头的点击事件，弹出登录历史记录菜单
        rootView.findViewById(R.id.textViewHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutContext.setVisibility(View.INVISIBLE);
                mLayoutHistory.setVisibility(View.VISIBLE);

                View historyItem = getActivity().getLayoutInflater().inflate(
                        R.layout.login_history_item,null);
                historyItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEditTextQQNum.setText("1234");
                        mLayoutContext.setVisibility(View.VISIBLE);
                        mLayoutHistory.setVisibility(View.INVISIBLE);
                    }
                });
                //创建3条历史记录
                mLayoutHistory.addView(historyItem);
                mLayoutHistory.addView(getActivity().getLayoutInflater().inflate(
                        R.layout.login_history_item,null));
                mLayoutHistory.addView(getActivity().getLayoutInflater().inflate(
                        R.layout.login_history_item,null));

                AnimationSet set = (AnimationSet) AnimationUtils.loadAnimation(
                        getContext(),R.anim.login_history_anim);
                mLayoutHistory.startAnimation(set);

            }
        });

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLayoutHistory.getVisibility() == View.VISIBLE){
                    mLayoutHistory.setVisibility(View.INVISIBLE);
                    mLayoutContext.setVisibility(View.VISIBLE);
                }
            }
        });
        return rootView;
    }

}
