package com.lsc.material.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.lsc.material.R;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        showDialog();
    }

    private void showDialog(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Title")
                .setMessage("Message")
                .setPositiveButton("Ok", null)
                .show();
    }
}
