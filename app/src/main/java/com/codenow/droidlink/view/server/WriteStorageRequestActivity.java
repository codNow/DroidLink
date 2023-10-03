
package com.codenow.droidlink.view.server;

import static android.os.Build.VERSION.SDK_INT;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



public class WriteStorageRequestActivity extends AppCompatActivity {

    private static final String TAG = "WriteStorageRequestActivity";
    private static final int REQUEST_WRITE_STORAGE = 44;
    private static final String PREFS_KEY_PERMISSION_ASKED_BEFORE = "write_storage_permission_asked_before";

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 11;
    private ActivityResultLauncher<String> permissionLauncher;
    private String[] requiredPermission = new String[]{
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if file transfer not wanted, bail out early without bothering the user
        if(!getIntent().getBooleanExtra(MainService.EXTRA_FILE_TRANSFER, new Defaults(this).getFileTransfer())) {
            postResultAndFinish(false);
            return;
        }

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                        if (isGranted){
                            askPermissionForAudio();

                        }
                });



        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU){

            permissionLauncher.launch(requiredPermission[0]);

            final SharedPreferences prefs = this.getSharedPreferences("PermissionPrefs", Context.MODE_PRIVATE);

            SharedPreferences.Editor ed = prefs.edit();
            ed.putBoolean(PREFS_KEY_PERMISSION_ASKED_BEFORE, true);
            ed.apply();
        }
        if (SDK_INT <= Build.VERSION_CODES.S_V2){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Has no permission! Ask!");
                final SharedPreferences prefs = this.getSharedPreferences("PermissionPrefs", Context.MODE_PRIVATE);


                if (!prefs.getBoolean(PREFS_KEY_PERMISSION_ASKED_BEFORE, false) || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);

                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putBoolean(PREFS_KEY_PERMISSION_ASKED_BEFORE, true);
                    ed.apply();


                } else {
                    postResultAndFinish(false);
                }
            } else {
                Log.i(TAG, "Permission already given!");
                postResultAndFinish(true);
            }
        }


    }

    private void askPermissionForAudio() {
        audioLauncher.launch(requiredPermission[2]);
    }

    private ActivityResultLauncher<String> audioLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted){
                    postResultAndFinish(InputService.isConnected());
                }
                else {
                    Toast.makeText(this, "Permission is needed to use this App ", Toast.LENGTH_SHORT).show();
                }

            }

    );

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            postResultAndFinish(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }

    private void postResultAndFinish(boolean isPermissionGiven) {

        if (isPermissionGiven)
            Log.i(TAG, "permission granted");
        else
            Log.i(TAG, "permission denied");

        SharedPreferences resultPrefs = this.getSharedPreferences("ResultPrefs", Context.MODE_PRIVATE);
        Intent intent = new Intent(this, MainService.class);
        intent.setAction(MainService.ACTION_HANDLE_WRITE_STORAGE_RESULT);
        intent.putExtra(MainService.EXTRA_ACCESS_KEY, resultPrefs.getString(Constants.PREFS_KEY_SETTINGS_ACCESS_KEY, new Defaults(this).getAccessKey()));
        //intent.putExtra(MainService.EXTRA_ACCESS_KEY, PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREFS_KEY_SETTINGS_ACCESS_KEY, new Defaults(this).getAccessKey()));
        intent.putExtra(MainService.EXTRA_WRITE_STORAGE_RESULT, isPermissionGiven);
        startService(intent);
        finish();
    }

}