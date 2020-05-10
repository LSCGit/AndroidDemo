package com.lsc.downloadablefonts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.core.provider.FontRequest;
import androidx.core.provider.FontsContractCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

import static com.lsc.downloadablefonts.Constants.ITALIC_DEFAULT;
import static com.lsc.downloadablefonts.Constants.WEIGHT_DEFAULT;
import static com.lsc.downloadablefonts.Constants.WEIGHT_MAX;
import static com.lsc.downloadablefonts.Constants.WIDTH_DEFAULT;
import static com.lsc.downloadablefonts.Constants.WIDTH_MAX;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Handler mHandler = null;

    private TextView mDownloadableFontTextView;
    private SeekBar mWidthSeekBar;
    private SeekBar mWeightSeekBar;
    private SeekBar mItalicSeekBar;
    private CheckBox mBestEffort;
    private Button mRequestDownloadButton;

    private ArraySet<String> mFamilyNameSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeSeekBars();
        mFamilyNameSet = new ArraySet<>();
        mFamilyNameSet.addAll(Arrays.asList(getResources().getStringArray(R.array.family_names)));

        mDownloadableFontTextView = findViewById(R.id.textview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.family_names));
        final TextInputLayout familyNameInput = findViewById(R.id.auto_complete_family_name_input);
        final AutoCompleteTextView autoCompleteFamilyName = findViewById(
                R.id.auto_complete_family_name);
        autoCompleteFamilyName.setAdapter(adapter);
        autoCompleteFamilyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (isValidFamilyName(charSequence.toString())) {
                    familyNameInput.setErrorEnabled(false);
                    familyNameInput.setError("");
                } else {
                    familyNameInput.setErrorEnabled(true);
                    familyNameInput.setError(getString(R.string.invalid_family_name));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No op
            }
        });

        mRequestDownloadButton = findViewById(R.id.button_request);
        mRequestDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //得到输入的字体类型
                String familyName = autoCompleteFamilyName.getText().toString();
                //查询是否支持此类型
                if (!isValidFamilyName(familyName)) { //不支持 进入
                    familyNameInput.setErrorEnabled(true);
                    familyNameInput.setError(getString(R.string.invalid_family_name));
                    Toast.makeText(
                            MainActivity.this,
                            R.string.invalid_input,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                requestDownload(familyName);
                mRequestDownloadButton.setEnabled(false);
            }
        });
        mBestEffort = findViewById(R.id.checkbox_best_effort);
    }

    private void requestDownload(String familyName) {
        QueryBuilder queryBuilder = new QueryBuilder(familyName)
                .withWidth(progressToWidth(mWidthSeekBar.getProgress()))
                .withWeight(progressToWeight(mWeightSeekBar.getProgress()))
                .withItalic(progressToItalic(mItalicSeekBar.getProgress()))
                .withBestEffort(mBestEffort.isChecked());
        String query = queryBuilder.build();

        Log.d(TAG, "Requesting a font. Query: " + query);
        FontRequest request = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                query,
                R.array.com_google_android_gms_fonts_certs);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        FontsContractCompat.FontRequestCallback callback = new FontsContractCompat
                .FontRequestCallback() {
            @Override
            public void onTypefaceRetrieved(Typeface typeface) {
                mDownloadableFontTextView.setTypeface(typeface);
                progressBar.setVisibility(View.GONE);
                mRequestDownloadButton.setEnabled(true);
            }

            //因 网络原因 只会走到这一步。
            @Override
            public void onTypefaceRequestFailed(int reason) {
                Toast.makeText(MainActivity.this,
                        getString(R.string.request_failed, reason), Toast.LENGTH_LONG)
                        .show();
                progressBar.setVisibility(View.GONE);
                mRequestDownloadButton.setEnabled(true);
            }
        };
        FontsContractCompat
                .requestFont(MainActivity.this, request, callback,
                        getHandlerThreadHandler());
    }


    private void initializeSeekBars() {
        mWidthSeekBar = findViewById(R.id.seek_bar_width);
        int widthValue = (int) (100 * (float) WIDTH_DEFAULT / (float) WIDTH_MAX);
        mWidthSeekBar.setProgress(widthValue);
        final TextView widthTextView = findViewById(R.id.textview_width);
        widthTextView.setText(String.valueOf(widthValue));
        mWidthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                widthTextView
                        .setText(String.valueOf(progressToWidth(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mWeightSeekBar = findViewById(R.id.seek_bar_weight);
        float weightValue = (float) WEIGHT_DEFAULT / (float) WEIGHT_MAX * 100;
        mWeightSeekBar.setProgress((int) weightValue);
        final TextView weightTextView = findViewById(R.id.textview_weight);
        weightTextView.setText(String.valueOf(WEIGHT_DEFAULT));
        mWeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightTextView
                        .setText(String.valueOf(progressToWeight(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mItalicSeekBar = findViewById(R.id.seek_bar_italic);
        mItalicSeekBar.setProgress((int) ITALIC_DEFAULT);
        final TextView italicTextView = findViewById(R.id.textview_italic);
        italicTextView.setText(String.valueOf(ITALIC_DEFAULT));
        mItalicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromuser) {
                italicTextView
                        .setText(String.valueOf(progressToItalic(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    private boolean isValidFamilyName(String familyName) {
        return familyName != null && mFamilyNameSet.contains(familyName);
    }

    private Handler getHandlerThreadHandler() {
        if (mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("fonts");
            handlerThread.start();
            mHandler = new Handler(handlerThread.getLooper());
        }
        return mHandler;
    }

    /**
     * 将进度转换为宽度。
     */
    private float progressToWidth(int progress) {
        return progress == 0 ? 1 : progress * WIDTH_MAX / 100;
    }

    /**
     * 将进度转换为权重。
     */
    private int progressToWeight(int progress) {
        if (progress == 0) {
            return 1; // The range of the weight is between (0, 1000) (exclusive)
        } else if (progress == 100) {
            return WEIGHT_MAX - 1; // The range of the weight is between (0, 1000) (exclusive)
        } else {
            return WEIGHT_MAX * progress / 100;
        }
    }

    /**
     * 将进度转换为斜体。
     */
    private float progressToItalic(int progress) {
        return (float) progress / 100f;
    }
}