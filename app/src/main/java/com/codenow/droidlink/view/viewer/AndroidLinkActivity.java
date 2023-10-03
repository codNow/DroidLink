package com.codenow.droidlink.view.viewer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.codenow.droidlink.R;

public class AndroidLinkActivity extends AppCompatActivity {

    private EditText ipText;
    private EditText portText;
    private EditText passwordText;
    private VncSettings settings;
    private EditText textUsername, textNickname;
    private CheckBox checkboxKeepPassword, checkMenuOverlay;
    private ProgressBar progressBar;

    private AutoCompleteTextView editTextFilledExposedDropdown;
    private Button startBtn;
    private Spinner colorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_link);
        ipText = findViewById(R.id.textIP);
        portText = findViewById(R.id.textPORT);
        passwordText = findViewById(R.id.textPASSWORD);
        textUsername = findViewById(R.id.textUsername);
        textNickname = findViewById(R.id.textNickname);
        checkboxKeepPassword = findViewById(R.id.checkboxKeepPassword);
        //editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown);
        startBtn = findViewById(R.id.buttonGO);
        progressBar = findViewById(R.id.progressBar);
        checkMenuOverlay = findViewById(R.id.checkboxUseOverlay);
        colorSpinner = findViewById(R.id.colorformat);

        //editTextFilledExposedDropdown.setInputType(InputType.TYPE_NULL);

        String[] COLORMODES = new String[] {COLORMODEL.C2.toString(),
                COLORMODEL.C4.toString(), COLORMODEL.C8.toString(),
                COLORMODEL.C64.toString(), COLORMODEL.C256.toString(),
                COLORMODEL.C24bit.toString()
        };

        /*ArrayAdapter<String> adapter = new ArrayAdapter<>(AndroidLinkActivity.this,R.layout.dropdown_menu_popup_item, COLORMODES);
        editTextFilledExposedDropdown.setAdapter(adapter);*/


        colorSpinner = (Spinner) findViewById(R.id.colorformat);
        COLORMODEL[] models = COLORMODEL.values();
        ArrayAdapter<COLORMODEL> colorSpinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, models);
        colorSpinner.setAdapter(colorSpinnerAdapter);
        colorSpinner.setSelection(0);

        settings = VncSettings.getPreferences(getApplication());

        boolean automaticLogin = settings.getAutomaticLogin();


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                canvasStart();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.androidvncmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void updateViewFromSelected() {
        if (settings ==null)
            return;
        ipText.setText(settings.getAddress());
        portText.setText(Integer.toString(settings.getPort()));
        if (settings.getKeepPassword() || settings.getPassword().length() > 0) {
            passwordText.setText(settings.getPassword());
        }
        checkboxKeepPassword.setChecked(settings.getKeepPassword());

        textUsername.setText(settings.getUserName());

        //editTextFilledExposedDropdown.setText(COLORMODEL.getModelForId(settings.getColorModel()).toString(), false);
    }

    private void updateSelectedFromView() {
        if (settings == null) {
            return;
        }
        settings.setAddress(ipText.getText().toString());
        try
        {
            settings.setPort(Integer.parseInt(portText.getText().toString()));
        }
        catch (NumberFormatException nfe)
        {

        }
        //settings.setNickname(textNickname.getText().toString());
        settings.setUserName(textUsername.getText().toString());
        // selected.setForceFull(groupForceFullScreen.getCheckedRadioButtonId()==R.id.radioForceFullScreenAuto ? BitmapImplHint.AUTO : (groupForceFullScreen.getCheckedRadioButtonId()==R.id.radioForceFullScreenOn ? BitmapImplHint.FULL : BitmapImplHint.TILE));
        settings.setPassword(passwordText.getText().toString());
        settings.setKeepPassword(checkboxKeepPassword.isChecked());
        //selected.setUseLocalCursor(checkboxLocalCursor.isChecked());
        settings.setMenuOverlay(checkMenuOverlay.isChecked());
        //settings.setColorModel(COLORMODEL.getModelForDesc(editTextFilledExposedDropdown.getText().toString()).getId());
        settings.setColorModel(COLORMODEL.getModelForDesc(colorSpinner.toString()).getId());
        //selected.setUseWakeLock(checkboxWakeLock.isChecked());
		/*if (repeaterTextSet)
		{
			selected.setRepeaterId(repeaterText.getText().toString());
			selected.setUseRepeater(true);
		}
		else
		{
			selected.setUseRepeater(false);
		}*/
    }

    protected void onStart() {
        super.onStart();
        arriveOnPage();
    }

    void arriveOnPage() {
        updateViewFromSelected();
    }

    protected void onStop() {
        super.onStop();
        if ( settings == null ) {
            return;
        }
        updateSelectedFromView();
        settings.save();
    }

    private void canvasStart() {
        if (settings == null) return;
        ActivityManager.MemoryInfo info = Utils.getMemoryInfo(this);
        if (info.lowMemory) {
            // Low Memory situation.  Prompt.
            Utils.showYesNoPrompt(this, "Continue?", "Android reports low system memory.\nContinue with VNC connection?",
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    vnc();
                }
            }, null);
        } else
            vnc();
    }

    private void vnc() {
        updateSelectedFromView();
        Intent intent = new Intent(this, VncCanvasActivity.class);
        intent.putExtra("Ip-Address", ipText.getText().toString());
        startActivity(intent);
        progressBar.setVisibility(View.GONE);
        finish();
    }
}