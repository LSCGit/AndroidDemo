package com.lsc.material.chips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.lsc.material.R;

public class ChipsActivity extends AppCompatActivity {

    private static final String TAG = "ChipsActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chips);


        final Chip actionChip = findViewById(R.id.actionChip);
        actionChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"actionChip");
            }
        });

        Chip entryChip = findViewById(R.id.entryChip);
        entryChip.setLayoutDirection(View.LAYOUT_DIRECTION_LOCALE);
        entryChip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"entryChip Close");
            }
        });
        setChip();
    }

    public void setChip(){
        // Inflate from resources.
        ChipDrawable chip = ChipDrawable.createFromResource(this, R.xml.standalone_chip);

// Use it as a Drawable however you want.
        chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(chip);

        EditText editText = findViewById(R.id.editText);
        Editable text = editText.getText();
        text.setSpan(span, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


}
