
package com.codenow.droidlink.view.server;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.util.Log;


public class MediaProjectionRequestActivity extends AppCompatActivity {

    private static final String TAG = "MPRequestActivity";
    private static final int REQUEST_MEDIA_PROJECTION = 42;
    private ActivityResultLauncher<Intent> mediaLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MediaProjectionManager mMediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        SharedPreferences mediaPrefs = this.getSharedPreferences("MediaPrefs", Context.MODE_PRIVATE);

                mediaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        Intent intent = new Intent(MediaProjectionRequestActivity.this, MainService.class);
                        intent.setAction(MainService.ACTION_HANDLE_MEDIA_PROJECTION_RESULT);
                        intent.putExtra(MainService.EXTRA_ACCESS_KEY, mediaPrefs.getString(
                                Constants.PREFS_KEY_SETTINGS_ACCESS_KEY, new Defaults(MediaProjectionRequestActivity.this).getAccessKey()));
                        intent.putExtra(MainService.EXTRA_MEDIA_PROJECTION_RESULT_CODE, result.getResultCode());
                        intent.putExtra(MainService.EXTRA_MEDIA_PROJECTION_RESULT_DATA, result.getData());
                        startService(intent);
                        finish();
                    }
                });
        Log.i(TAG, "Requesting confirmation");
        // This initiates a prompt dialog for the user to confirm screen projection.

        if (mMediaProjectionManager != null) {
            Intent screenCaptureIntent = mMediaProjectionManager.createScreenCaptureIntent();
            mediaLauncher.launch(screenCaptureIntent);
        }

    }



}