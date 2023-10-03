

package com.codenow.droidlink.view.server;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.codenow.droidlink.R;

public class InputRequestActivity extends AppCompatActivity {

    private static final String TAG = "InputRequestActivity";
    private static final int REQUEST_INPUT = 43;
    private ActivityResultLauncher<Intent> resultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if VIEW_ONLY is set, bail out early without bothering the user
        if(getIntent().getBooleanExtra(MainService.EXTRA_VIEW_ONLY, new Defaults(this).getViewOnly())) {
            postResultAndFinish(false);
            return;
        }

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        postResultAndFinish(InputService.isConnected());
                    }
                });

        if(!InputService.isConnected()) {

            Dialog requestDialog = new Dialog(this);
            requestDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            requestDialog.setContentView(R.layout.question_dialog);
            final TextView textTile, textMessage;
            final Button yesButton, noButton;


            textTile = requestDialog.findViewById(R.id.textTitle);
            textMessage = requestDialog.findViewById(R.id.text_message);
            yesButton = requestDialog.findViewById(R.id.buttonYes);
            noButton = requestDialog.findViewById(R.id.buttonNo);

            textTile.setText(R.string.input_a11y_title);
            textMessage.setText(R.string.input_a11y_msg);

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);


                    final String EXTRA_FRAGMENT_ARG_KEY = ":settings:fragment_args_key";
                    final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":settings:show_fragment_args";
                    Bundle bundle = new Bundle();
                    String showArgs = getPackageName() + "/" + InputService.class.getName();
                    bundle.putString(EXTRA_FRAGMENT_ARG_KEY, showArgs);
                    intent.putExtra(EXTRA_FRAGMENT_ARG_KEY, showArgs);
                    intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, bundle);

                    if (intent.resolveActivity(getPackageManager()) != null
                            && !intent.resolveActivity(getPackageManager()).toString().contains("Stub"))

                        resultLauncher.launch(intent);
                    requestDialog.dismiss();
                }
            });
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    postResultAndFinish(false);
                    requestDialog.dismiss();
                }
            });
            requestDialog.show();
            requestDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            requestDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            requestDialog.getWindow().setGravity(Gravity.END);


        } else {
            postResultAndFinish(true);
        }

    }


    private void postResultAndFinish(boolean isA11yEnabled) {

        if (isA11yEnabled)
            Log.i(TAG, "a11y enabled");
        else
            Log.i(TAG, "a11y disabled");

        SharedPreferences resultPrefs = this.getSharedPreferences("ResultPrefs", Context.MODE_PRIVATE);

        Intent intent = new Intent(this, MainService.class);
        intent.setAction(MainService.ACTION_HANDLE_INPUT_RESULT);
        intent.putExtra(MainService.EXTRA_INPUT_RESULT, isA11yEnabled);
        intent.putExtra(MainService.EXTRA_ACCESS_KEY, resultPrefs.getString(Constants.PREFS_KEY_SETTINGS_ACCESS_KEY, new Defaults(this).getAccessKey()));
        //intent.putExtra(MainService.EXTRA_ACCESS_KEY, PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREFS_KEY_SETTINGS_ACCESS_KEY, new Defaults(this).getAccessKey()));
        startService(intent);
        finish();
    }

}