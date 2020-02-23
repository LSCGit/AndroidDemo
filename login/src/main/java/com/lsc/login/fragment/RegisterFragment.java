package com.lsc.login.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lsc.login.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText mEditTextName;
    private EditText mEditTextPassword;
    private EditText mEditTextSurePass;

    private Button mBtnCancel;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register,container,false);
        mEditTextName = rootView.findViewById(R.id.register_user_name);
        mEditTextPassword = rootView.findViewById(R.id.register_password);
        mEditTextSurePass = rootView.findViewById(R.id.register_sure_password);

        mBtnCancel = rootView.findViewById(R.id.register_cancel);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return  rootView;
    }

}
