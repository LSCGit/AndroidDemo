package com.lsc.login.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.lsc.login.MainActivity;
import com.lsc.login.R;
import com.lsc.login.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText mEditTextName;
    private EditText mEditTextPassword;
    private Button mBtnLogin;
    private Button mBtnRegister;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login,container,false);
        mEditTextName = rootView.findViewById(R.id.login_user_name);
        mEditTextPassword = rootView.findViewById(R.id.login_user_password);

        mBtnLogin = rootView.findViewById(R.id.login_btn);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v,"what can you doing ?",
                        Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        mBtnRegister = rootView.findViewById(R.id.login_register_btn);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RegisterFragment registerFragment = new RegisterFragment();
                fragmentTransaction.replace(R.id.fragment_container,registerFragment);
                fragmentTransaction.addToBackStack("login");
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }

}
