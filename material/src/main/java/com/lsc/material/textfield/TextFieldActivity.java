package com.lsc.material.textfield;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.lsc.material.R;

public class TextFieldActivity extends AppCompatActivity {

    private TextInputLayout mTextInputLayout;
    private TextInputLayout mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_field);

        mTextInputLayout = findViewById(R.id.textField1);

        mTextInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mTextInputLayout.addOnEndIconChangedListener(new TextInputLayout.OnEndIconChangedListener() {
            @Override
            public void onEndIconChanged(@NonNull TextInputLayout textInputLayout, int previousIcon) {

            }
        });

        mTextInputLayout.addOnEditTextAttachedListener(new TextInputLayout.OnEditTextAttachedListener() {
            @Override
            public void onEditTextAttached(@NonNull TextInputLayout textInputLayout) {

            }
        });

        mTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initError();
    }

    private void initError(){

        mError = findViewById(R.id.textField8);
        //set Error信息
        mError.setError("错误");
        //清除Error信息
        //mError.setError(null);
    }
}
